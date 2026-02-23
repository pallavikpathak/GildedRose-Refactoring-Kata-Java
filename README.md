# Gilded Rose in Java

Gilded Rose is a classic refactoring kata involving a legacy inventory
system with complex, intertwined item update rules.

## Gilded Rose Kata – Baseline Setup

This is the initial setup for my Gilded Rose kata implementation.

At this stage, I am only preparing the project structure, so I can evolve the solution step by step. No refactoring or
behaviour changes have been made yet.

## How to run the project

### Run the TextTest Fixture from Command-Line

```
./gradlew -q text
```

### Specify Number of Days

For e.g. 10 days:

```
./gradlew -q text --args 10
```

## How to run tests

```
./gradlew
```

## What's included in this baseline

- Original kata source files kept completely untouched
- Project structure prepared for incremental evolution
- docs/ folder added for architecture decisions and requirements reference
- No logic changes — existing behaviour is preserved exactly as-is

## How I plan to approach the kata

I’m treating this exercise the same way I would handle a small feature request in a real project:

- start with a clean baseline
- add characterization tests to lock in the current behaviour
- refactor safely
- introduce structure only where it adds clarity

I’ll evolve the design gradually as I work through the rules.

## Next steps

- Add characterization tests
- Introduce a clear separation between domain logic and orchestration
- Start shaping the update rules in a way that keeps the code readable and easy to extend
