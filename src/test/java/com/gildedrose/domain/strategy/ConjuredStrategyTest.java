package com.gildedrose.domain.strategy;

import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class ConjuredStrategyTest {

    private final ConjuredStrategy strategy = new ConjuredStrategy();

    @Test
    @DisplayName("decreases sellIn by 1 each day")
    void decreasesSellInByOne() {
        Item item = new Item("Conjured", 10, 20);
        strategy.update(item);
        assertThat(item.sellIn).isEqualTo(9);
    }

    @Test
    @DisplayName("decrease quality by 2 before sell date")
    void decreaseQualityByTwoBeforeSellDate() {
        Item item = new Item("Conjured", 5, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(18);
    }

    @Test
    @DisplayName("decreases quality by 4 after sell date")
    void decreasesQualityByFourAfterSellDate() {
        Item item = new Item("Conjured", 0, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(16);
    }

    @Test
    @DisplayName("quality never goes below 0")
    void qualityNeverGoesBelowZero() {
        Item item = new Item("Conjured", 5, 1);
        strategy.update(item);
        assertThat(item.quality).isZero();
    }
}
