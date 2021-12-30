package org.betterdevxp.gradle.testkit

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner

trait GradleRunnerSupport extends ProjectFileSystem {

    private GradleRunner runner

    GradleRunner getRunner() {
        if (runner == null) {
            TestFile settingsFile = getSettingsFile()
            if (settingsFile.exists() == false) {
                settingsFile.text = ""
            }
            runner = GradleRunner.create()
                    .forwardOutput()
                    .withPluginClasspath()
                    .withProjectDir(projectDir)
        }
        runner
    }

    private GradleRunner runnerWithArgumentsIncludingStackTrace(String... args) {
        if ((args as List).contains("-s") == false) {
            args = args + "-s"
        }
        getRunner().withArguments(args)
    }

    BuildResult run(String... args) {
        runnerWithArgumentsIncludingStackTrace(args).build()
    }

    BuildResult runAndFail(String ... args) {
        runnerWithArgumentsIncludingStackTrace(args).buildAndFail()
    }

}
