package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseIntegrationTest {

    @Test
    @DisplayName("Integration: full inventory evolves correctly after multiple days")
    void inventory_evolves_correctly_over_time() {

        Item[] items = {
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20)
        };

        GildedRose app = new GildedRose(items);

        advanceDays(app, 10);

        assertAll(
            () -> assertEquals(0, items[0].sellIn),
            () -> assertEquals(10, items[0].quality),

            () -> assertEquals(-8, items[1].sellIn),
            () -> assertEquals(18, items[1].quality),

            () -> assertEquals(0, items[2].sellIn),
            () -> assertEquals(80, items[2].quality),

            () -> assertEquals(5, items[3].sellIn),
            () -> assertEquals(35, items[3].quality)
        );
    }

    private void advanceDays(GildedRose app, int days) {
        for (int i = 0; i < days; i++) {
            app.updateQuality();
        }
    }
}
