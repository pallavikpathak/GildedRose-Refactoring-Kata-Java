# Gilded Rose - Java Refactoring Kata

Gilded Rose is a classic refactoring kata involving a legacy inventory system
with complex, intertwined item update rules. This implementation takes the
original kata code and refactors it incrementally into a clean, extensible design
without changing any existing behaviour.

---

## Getting Started

### Prerequisites

- Java 21
- Gradle (wrapper included - no installation needed)

### Run the TextTest Fixture

```bash
./gradlew -q text
```

### Specify Number of Days

```bash
./gradlew -q text --args 10
```

### Run All Tests

```bash
./gradlew test
```

### Run a Specific Test Class

```bash
./gradlew test --tests "com.gildedrose.domain.strategy.NormalStrategyTest"
```

---

## Project Structure

```
src/
└── main/java/com/gildedrose/
    ├── GildedRose.java                        # Entry point - delegates to use case
    ├── Item.java                              # Original kata class - unchanged
    ├── application/
    │   └── UpdateQualityUseCase.java          # Orchestrates item updates
    └── domain/
        ├── constants/
        │   ├── ItemNames.java                 # Item name constants
        │   └── QualityConstants.java          # Quality floor and cap constants
        ├── strategy/
        │   ├── ItemUpdateStrategy.java        # Strategy interface
        │   ├── NormalStrategy.java
        │   ├── AgedBrieStrategy.java
        │   ├── SulfurasStrategy.java
        │   ├── BackstagePassStrategy.java
        │   └── ConjuredStrategy.java
        └── factory/
            └── ItemStrategyFactory.java       # Resolves strategy by item name

src/
└── test/java/com/gildedrose/
    ├── GildedRoseCharacterizationTest.java    # Locks original behaviour
    ├── GildedRoseIntegrationTest.java         # All items across multiple days
    ├── GildedRoseTest.java
    ├── application/
    │   └── UpdateQualityUseCaseTest.java
    └── domain/
        ├── factory/
        │   └── ItemStrategyFactoryTest.java
        └── strategy/
            ├── NormalStrategyTest.java
            ├── AgedBrieStrategyTest.java
            ├── SulfurasStrategyTest.java
            ├── BackstagePassStrategyTest.java
            └── ConjuredStrategyTest.java
```

---

## Item Behaviour

| Item | Behaviour |
|------|-----------|
| Normal item | Degrades by 1 per day, by 2 after sell date. Quality floor 0 |
| Aged Brie | Increases by 1 per day, by 2 after sell date. Quality cap 50 |
| Sulfuras | Never changes. Quality fixed at 80 |
| Backstage Passes | Increases by 1 above 10 days, by 2 within 10 days, by 3 within 5 days. Drops to 0 after concert |
| Conjured | Degrades by 2 per day, by 4 after sell date. Quality floor 0 |

---

## Adding a New Item Type

1. Create a new strategy class implementing `ItemUpdateStrategy`
2. Add one line to `ItemStrategyFactory`

No existing code changes required.

---

## Further Reading

See [`docs/architecture-decisions.md`](docs/architecture-decisions.md) for the full
design rationale, refactoring journey, and trade-offs made during this implementation.
