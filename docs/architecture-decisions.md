# Architecture Decisions

This document will be updated as the design evolves.

## Testing Approach

Characterization tests added before any refactoring to lock existing behaviour.
Integration test added to verify all items evolve correctly together over multiple days.
Tests cover all item types - individually per type and together
in one integration test across multiple days.


## Build & Tooling

- Switched from Java 8 to Java 21
- Updated JUnit to latest stable version (5.10.2)
- Added AssertJ library for cleaner, more readable test assertions
- Simplified how JUnit versions are managed - one central version declaration
  now keeps all JUnit libraries in sync automatically
- Updated Gradle build script to use modern syntax


## Refactoring Progress

### Step 1 - Simplify GildedRose

The original deeply nested conditional block was simplified using
IntelliJ refactoring tools. Each item type now has its own dedicated
method. The main update loop was reduced to a clean switch expression
delegating to per-item methods.

This made the distinct behaviours visible and isolated - a natural
precursor to extracting Strategy classes.

