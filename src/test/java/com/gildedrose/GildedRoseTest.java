package com.gildedrose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class GildedRoseTest {

    @Test
    @DisplayName("updates all items on each day")
    void updatesAllItems() {
        Item[] items = {new Item("Elixir of the Mongoose", 10, 20)};
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        assertThat(items[0].quality).isEqualTo(19);
    }

    @Test
    @DisplayName("updateQuality delegates to use case")
    void delegatesToUseCase() {
        Item[] items = {new Item("Elixir of the Mongoose", 10, 20)};
        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        assertThat(items[0].quality).isEqualTo(18);
    }
}
