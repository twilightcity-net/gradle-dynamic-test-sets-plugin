package org.betterdevxp.gradle.api

import org.gradle.api.Project
import org.gradle.util.GradleVersion

trait GradleVersionComparer {

    abstract Project getProject()

    boolean isGradleVersionLessThan(String version) {
        GradleVersion.version(project.gradle.gradleVersion) < GradleVersion.version(version)
    }
}
