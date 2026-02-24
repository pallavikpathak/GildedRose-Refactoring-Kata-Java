package com.gildedrose.domain.strategy;

import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AgedBrieStrategyTest {
    private final AgedBrieStrategy strategy = new AgedBrieStrategy();

    @Test
    @DisplayName("decreases sellIn by 1 each day")
    void decreasesSellInByOne() {
        Item item = new Item("Aged Brie", 10, 20);
        strategy.update(item);
        assertThat(item.sellIn).isEqualTo(9);
    }

    @Test
    @DisplayName("increases quality by 1 before sell date")
    void increasesQualityByOneBeforeSellDate() {
        Item item = new Item("Aged Brie", 5, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(21);
    }

    @Test
    @DisplayName("increases quality by 2 after sell date")
    void increasesQualityByTwoAfterSellDate() {
        Item item = new Item("Aged Brie", 0, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(22);
    }

    @Test
    @DisplayName("quality never exceeds 50")
    void qualityNeverExceedsFifty() {
        Item item = new Item("Aged Brie", 5, 50);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(50);
    }
}
