# Contributing to Gradle Dynamic TestSets Plugin

Thank you for your interest in contributing to the Gradle Dynamic TestSets Plugin! This document provides guidelines and instructions for contributing to the project.

## Code of Conduct

By participating in this project, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md).

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/your-username/gradle-dynamic-test-sets-plugin.git`
3. Create a new branch for your feature or bugfix: `git checkout -b feature/your-feature-name`

## Development Environment Setup

1. Ensure you have JDK 11 or higher installed
2. Install Gradle 7.1 or higher
3. Import the project into your IDE (IntelliJ IDEA recommended)
4. Run `./gradlew build` to verify your setup

## Development Workflow

1. Make your changes
2. Write or update tests to cover your changes
3. Run the test suite: `./gradlew test`
4. Ensure all tests pass
5. Update documentation if necessary
6. Commit your changes with a descriptive commit message
7. Push your changes to your fork
8. Create a pull request

## Code Style

- Follow the [Kotlin Style Guide](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4 spaces for indentation
- Keep lines under 120 characters
- Write clear, descriptive commit messages
- Include tests for new features and bug fixes

## Testing

- All new features must include appropriate test coverage
- Run the full test suite before submitting a pull request
- Ensure backward compatibility is maintained
- Add integration tests for significant changes

## Pull Request Process

1. Ensure your PR description clearly describes the problem and solution
2. Include relevant issue numbers in your PR description
3. Update the README.md with details of changes if needed
4. The PR must pass all CI checks
5. At least one maintainer must approve the PR before it can be merged

## Documentation

- Update README.md if your changes affect the public API or usage
- Add or update Javadoc/KDoc comments for public APIs
- Include examples in documentation when adding new features

## Release Process

1. Update version in `gradle.properties`
2. Update CHANGELOG.md with release notes
3. Create a release tag
4. Build and publish the release

## Questions?

If you have any questions about contributing, please:
1. Check the existing documentation
2. Search existing issues
3. Open a new issue if your question hasn't been answered

Thank you for contributing to the Gradle Dynamic TestSets Plugin! 