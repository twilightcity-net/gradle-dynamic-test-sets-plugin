package org.betterdevxp.gradle.testkit

import org.gradle.api.Project
import org.gradle.api.Task

class ProjectValidator {

    private Project project

    ProjectValidator(Project project) {
        this.project = project
    }

    void assertPluginApplied(String pluginName) {
        assert project.plugins.getPlugin(pluginName) != null
    }

    List<String> getDependencyNamesForTask(String taskName) {
        Task task = project.tasks.getByName(taskName)
        task.taskDependencies.getDependencies(task).toList().collect { it.name }
    }

    void assertTaskDependency(String taskName, String... expectedDependencyNames) {
        expectedDependencyNames.each { String expectedDependencyName ->
            assertTaskDependency(taskName, expectedDependencyName)
        }
    }

    void assertTaskDependency(String taskName, String expectedDependencyName) {
        List<String> dependencyNames = getDependencyNamesForTask(taskName)
        assert dependencyNames.contains(expectedDependencyName)
        "Expected task ${taskName} to declare dependency on ${expectedDependencyName}, actual dependencies: ${dependencyNames}"
    }

    void assertNoTaskDependency(String taskName, String expectedMissingDependencyName) {
        List<String> dependencyNames = getDependencyNamesForTask(taskName)
        assert !dependencyNames.contains(expectedMissingDependencyName)
        "Expected task ${taskName} to NOT declare dependency on ${expectedMissingDependencyName}, actual dependencies: ${dependencyNames}"
    }

    void assertTasksDefined(String... names) {
        names.each { String name ->
            assert project.tasks.findByName(name)
        }
    }

    void assertTasksNotDefined(String... names) {
        names.each { String name ->
            assert !project.tasks.findByName(name)
        }
    }
}
