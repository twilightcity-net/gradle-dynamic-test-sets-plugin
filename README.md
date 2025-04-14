[![Build Status](https://github.com/betterdevxp/gradle-dynamic-test-sets-plugin/workflows/gradle-build/badge.svg)](https://github.com/betterdevxp/gradle-dynamic-test-sets-plugin/actions)

# Gradle Dynamic TestSets Plugin

A Gradle plugin that automatically configures test sets based on source directory naming conventions, providing a convention-over-configuration approach to test organization. This plugin is part of the Twilight City ecosystem and embodies the testing best practices promoted and evangelized by the Twilight City community.

## Overview

The Gradle Dynamic TestSets Plugin is an opinionated Gradle plugin that builds upon the [Gradle TestSets plugin](https://github.com/unbroken-dome/gradle-testsets-plugin) to automatically configure test sets based on your project's source directory structure. It follows a convention-over-configuration approach, reducing boilerplate configuration while maintaining flexibility. As part of the Twilight City ecosystem, it promotes a standardized approach to test organization and execution, making it easier for teams to follow consistent testing practices across projects.

## Features

- **Automatic Test Set Detection**: Automatically creates test sets based on source directory names ending in "Test"
- **Shared Test Library**: Provides a `sharedTest` library for sharing code and dependencies across all test sets
- **Test Order Configuration**: Configurable test execution order
- **Commit Stage Integration**: Configurable integration with Gradle's `check` task
- **Dependency Management**: Automatic configuration of dependencies between test sets
- **Main Source Set Integration**: Seamless integration with main source set dependencies

## Technical Stack

- Kotlin-based Gradle plugin
- Built on top of the Gradle TestSets plugin
- Compatible with Java and Groovy projects
- Supports Gradle 7.1+ (with backward compatibility for older versions)

## Installation

Add the plugin to your `build.gradle.kts` or `build.gradle` file:

```kotlin
plugins {
    id("net.twilightcity.dynamic-test-sets") version "0.2.0"
}
```

## Usage

### Basic Setup

The plugin automatically detects test source directories and creates corresponding test sets. For example, with the following directory structure:

```
src/
  ├── main/
  ├── test/
  ├── componentTest/
  └── loadTest/
```

The plugin will create:
- `unitTest` (standard Gradle test set)
- `componentTest`
- `loadTest`
- `sharedTest` (for shared test code and dependencies)

### Dependencies Configuration

Configure dependencies for your test sets:

```kotlin
dependencies {
    // Available to all test sets
    sharedTestApi("org.spockframework:spock-core:2.0-groovy-3.0")
    
    // Unit test specific dependencies
    testImplementation("com.statemachinesystems:mock-clock:1.0")
    
    // Component test specific dependencies
    componentTestImplementation("com.github.tomakehurst:wiremock-standalone:2.27.2")
    
    // Load test specific dependencies
    loadTestImplementation("org.codehaus.groovy.modules.http-builder:http-builder:0.7.1")
}
```

### Configuration Options

Customize the plugin behavior using the `dynamicTestSets` extension:

```kotlin
dynamicTestSets {
    // Define the order in which test tasks should be executed
    standardTestTaskOrder = listOf("test", "componentTest", "integrationTest", "functionalTest")
    
    // Define which test tasks should be included in the 'check' task
    commitStageTestTaskNames = listOf("componentTest")
}
```

## Building and Development

### Prerequisites

- JDK 11 or higher
- Gradle 7.1 or higher

### Building the Plugin

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details on how to get started.

## License

[License information to be added]

## Support

For issues, feature requests, or questions, please open an issue in the GitHub repository.
