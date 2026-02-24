package com.gildedrose.domain.strategy;

import com.gildedrose.Item;

import static com.gildedrose.domain.constants.QualityConstants.MIN_QUALITY;

public class NormalStrategy implements ItemUpdateStrategy {
    @Override
    public void update(Item item) {
        item.sellIn--;
        int decreaseQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.max(MIN_QUALITY, item.quality - decreaseQuality);
    }
}
