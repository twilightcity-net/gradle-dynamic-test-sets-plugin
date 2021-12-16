package org.betterdevxp.gradle.test

import org.betterdevxp.gradle.testkit.ProjectSupport
import spock.lang.Specification

class DynamicTestSetsPluginSpec extends Specification implements ProjectSupport {

    private void applyPlugin() {
        project.plugins.apply(DynamicTestSetsPlugin.class)
    }

    private void createSrcDirs(String ... names) {
        names.each {String name ->
            projectFile("src/${name}").mkdirs()
        }
    }

    def "should automatically run component tests but not other test sets as part of check, by default"() {
        given:
        createSrcDirs("componentTest", "integrationTest")
        applyPlugin()

        when:
        evaluateProject()
        
        then:
        projectValidator.assertTaskDependency("check", "componentTest")
        projectValidator.assertNoTaskDependency("check", "integrationTest")
    }

    def "should automatically run tasks defined in commitStageTestTaskNames extension"() {
        given:
        createSrcDirs("componentTest", "integrationTest", "functionalTest")
        applyPlugin()

        project.extensions.findByType(DynamicTestSetsExtension).commitStageTestTaskNames = ["integrationTest", "functionalTest"]
        
        when:
        evaluateProject()

        then:
        projectValidator.assertNoTaskDependency("check", "componentTest")
        projectValidator.assertTaskDependency("check", "integrationTest")
        projectValidator.assertTaskDependency("check", "functionalTest")
    }

}
