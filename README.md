[![main Actions Status](https://github.com/betterdevxp/gradle-dynamic-test-sets-plugin/workflows/gradle-build/badge.svg)](https://github.com/betterdevxp/gradle-dynamic-test-sets-plugin/actions)

# Gradle Dynamic TestSets Plugin

TODO: fill me in

reasons for stuff...
sharedTest - provides a shared codebase for all test source sets.  while the gradle-test-sets plugin will automatically 
extend any test sets from the unit test configuration (meaning, integrationTestImplementation will end from testImplementation), 
it will not make the classes from src/test available to src/integrationTest - rightly so, because we don't want our unit 
tests or resources to be on the integration test classpath.  but we do want shared code to be available, hence the sharedTest
lib
