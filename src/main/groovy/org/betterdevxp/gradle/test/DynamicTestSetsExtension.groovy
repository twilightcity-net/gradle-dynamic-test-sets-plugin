package org.betterdevxp.gradle.test

class DynamicTestSetsExtension {

    static final String NAME = "dynamictestsets"

    /**
     * If defined, these tasks will be executed in index order
     */
    List<String> standardTestTaskOrder = ["test", "componentTest", "integrationTest", "functionalTest"]
    /**
     * These tasks will be automatically added as 'check' task dependencies
     */
    List<String> compileTimeTestTaskNames = ["componentTest"]

}
