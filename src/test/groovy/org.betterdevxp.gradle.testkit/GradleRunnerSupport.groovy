package org.betterdevxp.gradle.testkit

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Before

trait GradleRunnerSupport extends ProjectFileSystem {

    GradleRunner runner

    @Before
    def setupGradleRunner() {
        projectDir.deleteDir()
        projectDir.mkdirs()
        new File(projectDir, "settings.gradle").text = ""
        runner = GradleRunner.create()
                .forwardOutput()
                .withPluginClasspath()
                .withProjectDir(projectDir)
    }

    BuildResult run(String... args) {
        runner.withArguments(args).build()
    }

    BuildResult runAndFail(String ... args) {
        runner.withArguments(args).buildAndFail()
    }

}
