package org.betterdevxp.gradle.test

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.testing.Test

class DynamicTestSetsPlugin implements Plugin<Project> {

    private Project project
    private DynamicTestSetsExtension extension

    @Override
    void apply(Project project) {
        this.project = project
        this.extension = project.extensions.create(DynamicTestSetsExtension.NAME, DynamicTestSetsExtension)

        project.apply(plugin: "org.unbroken-dome.test-sets")
        configureDynamicTestSets()
        configureTestTaskOrderAndCommitStageDependencies()
    }

    private List<String> configureDynamicTestSets() {
        List<String> availableTestSetNames = getAvailableTestSetNames()

        availableTestSetNames.remove("sharedTest")
        configureSharedTestSetLibrary()

        availableTestSetNames.remove("mainTest")
        configureMainTestSetLibrary()

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

    private void configureMainTestSetLibrary() {
        project.testSets {
            libraries {
                mainTest
            }
            sharedTest {
                imports libraries.mainTest
            }
        }
        extendTestLibraryFromMainConfigurations("mainTest")
    }

    private void configureTestSet(String testSetName) {
        project.testSets {
            "${testSetName}" {
                imports libraries.sharedTest, libraries.mainTest
            }
        }
    }

    private void extendTestLibraryFromMainConfigurations(String name) {
        project.plugins.withType(JavaPlugin) {
            project.configurations."${name}Api".extendsFrom project.configurations.compile
            project.configurations."${name}Implementation".extendsFrom project.configurations.runtime
            project.configurations."${name}CompileOnly".extendsFrom project.configurations.compileOnly
            project.configurations."${name}RuntimeOnly".extendsFrom project.configurations.runtimeOnly
        }

        project.plugins.withType(JavaLibraryPlugin) {
            project.configurations."${name}Api".extendsFrom project.configurations.api
            project.configurations."${name}Implementation".extendsFrom project.configurations.implementation
        }
    }

    private void configureTestTaskOrderAndCommitStageDependencies() {
        project.tasks.withType(Test).configureEach { Test task ->
            int index = extension.standardTestTaskOrder.findIndexOf {it == task.name }
            if (index != 0) {
                int lastIndex = index > 0 ? index - 1 : index
                extension.standardTestTaskOrder[0..lastIndex].findAll {
                    project.tasks.findByName(it) != null
                }.each {
                    task.mustRunAfter(it)
                }
            }
        }

        List<String> availableTestSetNames = getAvailableTestSetNames()
        project.tasks.named("check").configure {Task task ->
            extension.commitStageTestTaskNames.each {
                if (availableTestSetNames.contains(it)) {
                    task.dependsOn(it)
                }
            }
        }
    }

}
