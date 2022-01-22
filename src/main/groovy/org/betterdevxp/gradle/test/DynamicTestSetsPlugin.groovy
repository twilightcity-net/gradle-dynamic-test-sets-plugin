package org.betterdevxp.gradle.test

import org.betterdevxp.gradle.api.SourceSetAccessor
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.unbrokendome.gradle.plugins.testsets.TestSetsPlugin

class DynamicTestSetsPlugin implements Plugin<Project>, SourceSetAccessor {

    private Project project
    private DynamicTestSetsExtension extension

    @Override
    void apply(Project project) {
        this.project = project
        this.extension = project.extensions.create(DynamicTestSetsExtension.NAME, DynamicTestSetsExtension)

        project.pluginManager.apply(TestSetsPlugin)
        configureDynamicTestSets()
        configureTestTaskOrderAndCommitStageDependencies()
    }

    @Override
    Project getProject() {
        project
    }

    private List<String> configureDynamicTestSets() {
        List<String> availableTestSetNames = getAvailableTestSetNames()

        availableTestSetNames.remove("sharedTest")
        configureSharedTestSetLibrary()

        availableTestSetNames.each { String testSetName ->
            configureTestSet(testSetName)
        }
    }

    private List<String> getAvailableTestSetNames() {
        List<String> availableTestSetNames = []
        // imports the shared and published test set libraries to the 'test' test set - https://github.com/unbroken-dome/gradle-testsets-plugin#predefined-unit-test-set
        availableTestSetNames.add("unitTest")
        project.file("src").listFiles({ File file ->
            file.isDirectory() && file.name.endsWith("Test")
        } as FileFilter).each {
            availableTestSetNames.add(it.name)
        }
        availableTestSetNames
    }

    private void configureSharedTestSetLibrary() {
        project.testSets {
            libraries {
                sharedTest
            }
        }
        extendTestLibraryFromMainConfigurations("sharedTest")
    }

    private void configureTestSet(String testSetName) {
        project.testSets {
            "${testSetName}" {
                imports libraries.sharedTest
            }
        }
    }

    private void extendTestLibraryFromMainConfigurations(String name) {
        SourceSet mainSourceSet = getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME)
        SourceSet libSourceSet = getSourceSets().getByName(name)

        project.plugins.withType(JavaPlugin) {
            project.configurations.with {
                it[libSourceSet.apiConfigurationName].extendsFrom(it[mainSourceSet.compileClasspathConfigurationName])
                it[libSourceSet.implementationConfigurationName].extendsFrom(it[mainSourceSet.implementationConfigurationName])
                it[libSourceSet.compileOnlyConfigurationName].extendsFrom(it[mainSourceSet.compileOnlyConfigurationName])
                it[libSourceSet.runtimeOnlyConfigurationName].extendsFrom(it[mainSourceSet.runtimeOnlyConfigurationName])
            }
        }

        project.plugins.withType(JavaLibraryPlugin) {
            project.configurations.with {
                it[libSourceSet.apiConfigurationName].extendsFrom(it[mainSourceSet.apiConfigurationName])
                it[libSourceSet.implementationConfigurationName].extendsFrom(it[mainSourceSet.implementationConfigurationName])
            }
        }
    }

    private void configureTestTaskOrderAndCommitStageDependencies() {
        project.afterEvaluate {
            List<Task> standardTestTaskOrderTasks = extension.standardTestTaskOrder.collect {
                project.tasks.findByName(it)
            }.findAll {
                it != null
            }
            for (int i = standardTestTaskOrderTasks.size() - 1; i > 0; i--) {
                standardTestTaskOrderTasks[0 .. i - 1].each {
                    standardTestTaskOrderTasks[i].mustRunAfter(it)
                }
            }

            List<String> availableTestSetNames = getAvailableTestSetNames()
            project.tasks.named("check").configure { Task task ->
                extension.commitStageTestTaskNames.each {
                    if (availableTestSetNames.contains(it)) {
                        task.dependsOn(it)
                    }
                }
            }
        }
    }

}
