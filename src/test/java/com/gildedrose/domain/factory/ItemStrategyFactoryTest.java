package com.gildedrose.domain.factory;

import com.gildedrose.domain.strategy.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static com.gildedrose.domain.constants.ItemNames.*;
class ItemStrategyFactoryTest {

    private final ItemStrategyFactory factory = new ItemStrategyFactory();

    @Test
    @DisplayName("returns AgedBrieStrategy for Aged Brie")
    void returnsAgedBrieStrategy() {
        assertThat(factory.getStrategy(AGED_BRIE)).isInstanceOf(AgedBrieStrategy.class);
    }

    @Test
    @DisplayName("returns SulfurasStrategy for Sulfuras")
    void returnsSulfurasStrategy() {
        assertThat(factory.getStrategy(SULFURAS)).isInstanceOf(SulfurasStrategy.class);
    }

    @Test
    @DisplayName("returns BackstagePassStrategy for Backstage Passes")
    void returnsBackstagePassStrategy() {
        assertThat(factory.getStrategy(BACKSTAGE_PASSES)).isInstanceOf(BackstagePassStrategy.class);
    }

    @Test
    @DisplayName("returns ConjuredStrategy for Conjured")
    void returnsConjuredStrategy() {
        assertThat(factory.getStrategy(CONJURED)).isInstanceOf(ConjuredStrategy.class);
    }

    @Test
    @DisplayName("returns NormalStrategy for unknown item")
    void returnsNormalStrategyForUnknownItem() {
        assertThat(factory.getStrategy("Unknown Item")).isInstanceOf(NormalStrategy.class);
    }
}
