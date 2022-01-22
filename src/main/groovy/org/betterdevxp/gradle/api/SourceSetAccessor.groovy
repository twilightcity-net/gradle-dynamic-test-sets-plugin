package org.betterdevxp.gradle.api

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

trait SourceSetAccessor implements GradleVersionComparer {

    abstract Project getProject()

    SourceSetContainer getSourceSets() {
        isGradleVersionLessThan("7.1") ? getSourceSetsFromConvention() : getSourceSetsFromExtension()
    }

    private SourceSetContainer getSourceSetsFromExtension() {
        project.extensions.getByType(org.gradle.api.plugins.JavaPluginExtension).getSourceSets()
    }

    private SourceSetContainer getSourceSetsFromConvention() {
        project.getConvention().getPlugin(org.gradle.api.plugins.JavaPluginConvention).getSourceSets()
    }

}
