package com.gildedrose.domain.strategy;

import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NormalStrategyTest {
    private final NormalStrategy strategy = new NormalStrategy();

    @Test
    @DisplayName("decreases sellIn by 1 each day")
    void decreasesSellInByOne() {
        Item item = new Item("Elixir of the Mongoose", 10, 20);
        strategy.update(item);
        assertThat(item.sellIn).isEqualTo(9);
    }

    @Test
    @DisplayName("decreases quality by 1 before sell date")
    void decreasesQualityByOneBeforeSellDate() {
        Item item = new Item("Elixir of the Mongoose", 5, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(19);
    }

    @Test
    @DisplayName("decreases quality by 2 after sell date")
    void decreasesQualityByTwoAfterSellDate() {
        Item item = new Item("Elixir of the Mongoose", 0, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(18);
    }

    @Test
    @DisplayName("quality never goes below 0")
    void qualityNeverGoesBelowZero() {
        Item item = new Item("Elixir of the Mongoose", 5, 0);
        strategy.update(item);
        assertThat(item.quality).isZero();
    }
}

