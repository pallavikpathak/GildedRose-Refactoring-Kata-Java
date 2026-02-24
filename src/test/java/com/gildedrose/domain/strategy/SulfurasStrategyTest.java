package com.gildedrose.domain.strategy;

import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
class SulfurasStrategyTest {
    private final SulfurasStrategy strategy = new SulfurasStrategy();
    @Test
    @DisplayName("quality and sellIn never change")
    void qualityAndSellInNeverChange() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", 10, 80);
        strategy.update(item);
        assertThat(item.sellIn).isEqualTo(10);
        assertThat(item.quality).isEqualTo(80);
    }
}
