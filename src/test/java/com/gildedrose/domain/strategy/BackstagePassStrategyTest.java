package com.gildedrose.domain.strategy;

import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BackstagePassStrategyTest {
    private final BackstagePassStrategy strategy = new BackstagePassStrategy();

    @Test
    @DisplayName("decreases sellIn by 1 each day")
    void decreasesSellInByOne() {
        Item item = new Item("Backstage passes", 10, 20);
        strategy.update(item);
        assertThat(item.sellIn).isEqualTo(9);
    }

    @Test
    @DisplayName("increases quality by 1 when more than 10 days remaining")
    void increasesQualityByOneAboveTenDays() {
        Item item = new Item("Backstage passes", 15, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(21);
    }

    @Test
    @DisplayName("increases quality by 2 when 10 days or less remaining")
    void increasesQualityByTwoAtTenDays() {
        Item item = new Item("Backstage passes", 10, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(22);
    }

    @Test
    @DisplayName("increases quality by 3 when 5 days or less remaining")
    void increasesQualityByThreeAtFiveDays() {
        Item item = new Item("Backstage passes", 5, 20);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(23);
    }

    @Test
    @DisplayName("quality drops to 0 after concert")
    void qualityDropsToZeroAfterConcert() {
        Item item = new Item("Backstage passes", 0, 20);
        strategy.update(item);
        assertThat(item.quality).isZero();
    }

    @Test
    @DisplayName("quality never exceeds 50")
    void qualityNeverExceedsFifty() {
        Item item = new Item("Backstage passes", 15, 50);
        strategy.update(item);
        assertThat(item.quality).isEqualTo(50);
    }
}
