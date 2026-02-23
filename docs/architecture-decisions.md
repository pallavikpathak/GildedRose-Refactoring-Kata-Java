# Architecture Decisions

This document will be updated as the design evolves.

## Testing Approach

Characterization tests added before any refactoring to lock existing behaviour.
Tests cover all item types
Integration test added to verify all items evolve correctly together over multiple days.

## Build & Tooling

- Switched from Java 8 to Java 21
- Updated JUnit to latest stable version (5.10.2)
- Added AssertJ library for cleaner, more readable test assertions
- Simplified how JUnit versions are managed - one central version declaration
  now keeps all JUnit libraries in sync automatically
- Updated Gradle build script to use modern syntax
