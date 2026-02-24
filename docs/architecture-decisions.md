# Architecture Decisions

## 1. The Problem

The original GildedRose class contains all item update logic inside a single deeply nested
conditional block. Every item type - Normal, Aged Brie, Sulfuras, Backstage Passes, and
Conjured - is handled inline with interleaved conditions.

This creates two concrete risks. First, changing one item type's behaviour risks accidentally
breaking others because all logic shares one execution path. Second, adding a new item type
like Conjured requires modifying the existing conditional block, which is fragile by design.

The problem is not messy code - it is tight behavioural coupling. Each item type has its own
independent update rules, but the original implementation hides this structure entirely.

---

## 2. Testing Strategy

Before touching any production code, characterization tests were written to record exactly
what the original system does. These act as a safety net - if any refactoring accidentally
changes behaviour, the tests catch it immediately.

Tests cover each item type individually and an integration-style test runs all items together
across multiple days to capture overall system behaviour.

The Java upgrade from version 8 to 21 was applied after characterization tests were in place,
so any unexpected behaviour change from the tooling upgrade would be caught immediately.

Following the refactoring, unit tests were added per strategy class. Each strategy is pure
logic with no dependencies - no mocks needed. Tests cover the key boundary conditions for
each item type: degradation rates, quality floor, quality cap, and sell date thresholds.

---

## Build & Tooling

- Switched from Java 8 to Java 21
- Updated JUnit to latest stable version (5.10.2)
- Added AssertJ library for cleaner, more readable test assertions
- Simplified how JUnit versions are managed - one central version declaration
  now keeps all JUnit libraries in sync automatically
- Updated Gradle build script to use modern syntax

---

## 4. Refactoring Journey

The refactoring was done incrementally - the design emerged from understanding the code
deeply, not from imposing a pattern upfront.

### Step 1 - Simplify GildedRose

The original deeply nested conditional block was simplified using IntelliJ refactoring tools.
Each item type was given its own dedicated method. The main update loop was reduced to a
clean switch expression delegating to per-item methods. This made the distinct behaviours
visible and isolated - a natural precursor to extracting Strategy classes.

### Step 2 - Extract Strategy Pattern

Each item type was extracted into its own Strategy class implementing a common
`ItemUpdateStrategy` interface with a single `update(Item item)` method. This made the
coupling explicit: each class owns its behaviour entirely and changes to one type cannot
affect others.

Strategy implementations follow a consistent pattern - advance `sellIn` first, calculate
the rate of change, then apply it with the appropriate floor or cap. This consistency makes
all five strategies immediately readable by inspection.

### Step 3 - ItemStrategyFactory

A factory class resolves the correct strategy by item name. This keeps the mapping logic
in one place and keeps `GildedRose` itself free of conditional logic. Adding a new item
type requires one new strategy class and one line in the factory - nothing else changes.

### Step 4 - Application Service Layer

`UpdateQualityUseCase` was introduced as a thin application service layer sitting between
the outside world and the domain strategies. `GildedRose` delegates to it entirely. Today
the system is a CLI tool. If it became a REST API or batch job tomorrow, the domain
strategies would not change - only a new adapter calling the use case would be added.

---

## 5. Key Design Decisions and Trade-offs

### Conjured as an independent strategy

Conjured items degrade at double the normal rate. Rather than composing `ConjuredStrategy`
on top of `NormalStrategy`, Conjured was implemented as an independent strategy. This keeps
behaviour isolated - if Conjured rules change tomorrow, only `ConjuredStrategy` changes.
The relationship to Normal degradation is documented in the architecture rather than encoded
as a dependency.

### Item class left untouched

The kata rules require `Item` to remain unchanged, and this fits naturally with the design.
Behaviour lives in the strategies, not in the item. `Item` is a plain data carrier.

### Complexity trade-off

The design introduces more classes compared to the original implementation. This slightly
increases structural complexity but significantly reduces behavioural coupling and future
modification risk. The decision favours long-term maintainability over minimising class count.

### Positioning for Hexagonal Architecture

The `UpdateQualityUseCase` establishes a clean application service boundary, positioning
the design for Hexagonal Architecture if the system grows. Adding a REST or CLI adapter
tomorrow would mean implementing a new inbound port calling the use case - the domain
strategies would not change at all.

---

## 6. Proof of Design - Adding Conjured

Adding Conjured item support required one new `ConjuredStrategy` class and one line in
`ItemStrategyFactory`. Zero changes to existing strategies. This demonstrates the
Open/Closed Principle in practice - the system is open for extension and closed
for modification.

---

## 7. SOLID Principles in Practice

- **S** - each class has one job, one reason to change
- **O** - new item type requires only a new strategy, nothing existing changes
- **L** - any strategy is substitutable behind the `ItemUpdateStrategy` interface
- **I** - strategy interface is focused, one method only
- **D** - `GildedRose` depends on abstractions rather than concrete strategies

---

## 8. Future Extensions

The current structure supports the following without touching domain logic:

- Add a REST or batch adapter by calling `UpdateQualityUseCase` from a new delivery layer
- Support new item types by adding one strategy class and one factory line
- Introduce persistence by wrapping the use case with a repository layer
- Exception handling and structured logging were not added at this stage as the system has
  no external dependencies or failure modes that require it. If a persistence or messaging
  layer were introduced, the use case layer is the natural place to add error handling
  and observability.

---

## 9. Lessons Learned

**Small reversible steps reduce risk.**
Characterization tests first, then one change at a time. Each commit left the system in a
working state. This made the refactoring safe to pause, review, and course-correct at any point.

**Use tooling - don't refactor by hand.**
IntelliJ's refactoring tools and SonarLint were used throughout. Automated refactoring
eliminates transcription errors and keeps the feedback loop tight. The goal was to let tools
do mechanical work so decisions could focus on design.

**The pattern emerged from the code, not from design instinct.**
Strategy was not chosen upfront. Simplifying GildedRose first made the distinct behaviours
visible. The pattern became obvious once the structure was clear. Imposing a pattern before
understanding the problem would have risked the wrong abstraction.
