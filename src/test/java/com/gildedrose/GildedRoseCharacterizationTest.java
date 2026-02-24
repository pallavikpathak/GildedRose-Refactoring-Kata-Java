package com.gildedrose;

import com.gildedrose.helper.ExpectedState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseCharacterizationTest {
    private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    private static final String CONJURED = "Conjured Mana Cake";
    private static final String NORMAL_ITEM = "Normal Item";
    private static final int LEGENDARY_QUALITY = 80;
    private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    private static Stream<Arguments> agedBrieObservedStates() {
        return Stream.of(
            Arguments.of(1, ExpectedState.sellInThenQuality(1, 1)),
            Arguments.of(2, ExpectedState.sellInThenQuality(0, 2)),
            Arguments.of(3, ExpectedState.sellInThenQuality(-1, 4)),
            Arguments.of(5, ExpectedState.sellInThenQuality(-3, 8)),
            Arguments.of(10, ExpectedState.sellInThenQuality(-8, 18)),
            Arguments.of(30, ExpectedState.sellInThenQuality(-28, 50)),
            Arguments.of(32, ExpectedState.sellInThenQuality(-30, 50))
        );
    }

    private static Stream<Arguments> backstagePassObservedStates() {
        return Stream.of(
            // 1 DAY — tests all three increase tiers + drop-to-zero
            Arguments.of(1, new ExpectedState[]{
                ExpectedState.sellInThenQuality(14, 21), // >10 → +1
                ExpectedState.sellInThenQuality(9, 50),  // 10 → +2 but capped at 50
                ExpectedState.sellInThenQuality(4, 50),  // 5 → +3 but capped at 50
                ExpectedState.sellInThenQuality(0, 23),  // 1 → +3
                ExpectedState.sellInThenQuality(-1, 0),  // 0 → drop to 0
                ExpectedState.sellInThenQuality(-2, 0)   // <0 → stays 0
            }),

            // 5 DAYS — tests transitions between tiers
            Arguments.of(5, new ExpectedState[]{
                ExpectedState.sellInThenQuality(10, 25), // +5
                ExpectedState.sellInThenQuality(5, 50),  // +10 but capped
                ExpectedState.sellInThenQuality(0, 50),  // +15 but capped
                ExpectedState.sellInThenQuality(-4, 0),  // drop to 0 after day 0
                ExpectedState.sellInThenQuality(-5, 0),
                ExpectedState.sellInThenQuality(-6, 0)
            }),

            // 12 DAYS — long-range behavior, ensures quality stays 0 after concert
            Arguments.of(12, new ExpectedState[]{
                ExpectedState.sellInThenQuality(3, 41),  // +1 for 5 days, +2 for 5 days, +3 for 2 days
                ExpectedState.sellInThenQuality(-2, 0),  // capped then dropped
                ExpectedState.sellInThenQuality(-7, 0),
                ExpectedState.sellInThenQuality(-11, 0),
                ExpectedState.sellInThenQuality(-12, 0),
                ExpectedState.sellInThenQuality(-13, 0)
            })
        );

    }

    private static Stream<Arguments> conjuredObservedStates() {
        return Stream.of(

            Arguments.of(1, new ExpectedState[]{
                ExpectedState.sellInThenQuality(9, 18),
                ExpectedState.sellInThenQuality(4, 5),
                ExpectedState.sellInThenQuality(-2, 16),
                ExpectedState.sellInThenQuality(4, 0)
            }),

            Arguments.of(5, new ExpectedState[]{
                ExpectedState.sellInThenQuality(5, 10),
                ExpectedState.sellInThenQuality(0, 0),
                ExpectedState.sellInThenQuality(-6, 0),
                ExpectedState.sellInThenQuality(0, 0)
            }),

            Arguments.of(10, new ExpectedState[]{
                ExpectedState.sellInThenQuality(0, 0),
                ExpectedState.sellInThenQuality(-5, 0),
                ExpectedState.sellInThenQuality(-11, 0),
                ExpectedState.sellInThenQuality(-5, 0)
            })
        );
    }

    private static Stream<Arguments> normalItemObservedStates() {
        return Stream.of(

            Arguments.of(1, new ExpectedState[]{
                ExpectedState.sellInThenQuality(9, 19),
                ExpectedState.sellInThenQuality(4, 6),
                ExpectedState.sellInThenQuality(-2, 18),
                ExpectedState.sellInThenQuality(4, 0)
            }),

            Arguments.of(5, new ExpectedState[]{
                ExpectedState.sellInThenQuality(5, 15),
                ExpectedState.sellInThenQuality(0, 2),
                ExpectedState.sellInThenQuality(-6, 10),
                ExpectedState.sellInThenQuality(0, 0)
            }),

            Arguments.of(10, new ExpectedState[]{
                ExpectedState.sellInThenQuality(0, 10),
                ExpectedState.sellInThenQuality(-5, 0),
                ExpectedState.sellInThenQuality(-11, 0),
                ExpectedState.sellInThenQuality(-5, 0)
            })
        );
    }

    private static Item sulfuras(int sellIn) {
        return new Item(SULFURAS, sellIn, LEGENDARY_QUALITY);
    }

    @ParameterizedTest
    @MethodSource("agedBrieObservedStates")
    @DisplayName("Characterization: Aged Brie over multiple days")
    void agedBrie_characterization(int days, ExpectedState expected) {
        Item[] items = {new Item("Aged Brie", 2, 0)};
        GildedRose app = new GildedRose(items);
        advanceDays(app, days);
        Item agedBrie = app.items[0];
        assertAll(
            () -> assertEquals(expected.sellIn, agedBrie.sellIn),
            () -> assertEquals(expected.quality, agedBrie.quality)
        );

    }

    private void advanceDays(GildedRose app, int days) {
        for (int i = 0; i < days; i++) {
            app.updateQuality();
        }
    }

    @ParameterizedTest
    @MethodSource("backstagePassObservedStates")
    @DisplayName("Characterization: Backstage Passes increase in tiers and drop to zero after the concert")
    void backstagePass_characterization(int days, ExpectedState[] expectedStates) {

        Item[] items = {
            new Item(BACKSTAGE_PASSES, 15, 20), // >10 days
            new Item(BACKSTAGE_PASSES, 10, 49), // boundary at 10
            new Item(BACKSTAGE_PASSES, 5, 49),  // <=5 days tier
            new Item(BACKSTAGE_PASSES, 1, 20),  // last day before concert
            new Item(BACKSTAGE_PASSES, 0, 20),  // concert day
            new Item(BACKSTAGE_PASSES, -1, 20)  // after concert
        };

        GildedRose app = new GildedRose(items);
        advanceDays(app, days);

        for (int i = 0; i < items.length; i++) {
            Item actual = app.items[i];
            ExpectedState expected = expectedStates[i];
            int index = i;
            assertAll(
                () -> assertEquals(expected.sellIn, actual.sellIn,
                    "sellIn mismatch at index " + index),
                () -> assertEquals(expected.quality, actual.quality,
                    "quality mismatch at index " + index)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("conjuredObservedStates")
    @DisplayName("Characterization: Conjured items behave like normal items in legacy implementation")
    void conjured_characterization(int days, ExpectedState[] expectedStates) {

        Item[] items = {
            new Item(CONJURED, 10, 20),
            new Item(CONJURED, 5, 7),
            new Item(CONJURED, -1, 20),
            new Item(CONJURED, 5, 0)
        };

        GildedRose app = new GildedRose(items);
        advanceDays(app, days);

        for (int i = 0; i < items.length; i++) {
            Item actual = app.items[i];
            ExpectedState expected = expectedStates[i];
            int index = i; // missing this
            assertAll(
                () -> assertEquals(expected.sellIn, actual.sellIn,
                    "sellIn mismatch at index " + index),
                () -> assertEquals(expected.quality, actual.quality,
                    "quality mismatch at index " + index)
            );
        }
    }

    @ParameterizedTest
    @MethodSource("normalItemObservedStates")
    @DisplayName("Characterization: Normal items degrade in quality, faster after sellIn passes")
    void normalItem_characterization(int days, ExpectedState[] expectedStates) {

        Item[] items = {
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item(NORMAL_ITEM, -1, 20),
            new Item(NORMAL_ITEM, 5, 0)
        };

        GildedRose app = new GildedRose(items);
        advanceDays(app, days);

        for (int i = 0; i < items.length; i++) {
            Item actual = app.items[i];
            ExpectedState expected = expectedStates[i];
            int index = i;
            assertAll(
                () -> assertEquals(expected.sellIn, actual.sellIn,
                    "sellIn mismatch at index " + index),
                () -> assertEquals(expected.quality, actual.quality,
                    "quality mismatch at index " + index)
            );
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 30})
    @DisplayName("Characterization: Sulfuras remains constant regardless of days passed")
    void sulfuras_characterization(int days) {

        Item[] items = {
            sulfuras(0),
            sulfuras(-1),
            sulfuras(10),
            sulfuras(100),
        };

        GildedRose app = new GildedRose(items);
        advanceDays(app, days);
        int[] expectedSellIns = Arrays.stream(items).mapToInt(i -> i.sellIn).toArray();

        for (int i = 0; i < items.length; i++) {
            Item actual = app.items[i];
            int expectedSellIn = expectedSellIns[i];
            int index = i;
            assertAll(
                () -> assertEquals(expectedSellIn, actual.sellIn,
                    "sellIn mismatch at index " + index),
                () -> assertEquals(LEGENDARY_QUALITY, actual.quality,
                    "quality mismatch at index " + index)
            );
        }
    }

}
