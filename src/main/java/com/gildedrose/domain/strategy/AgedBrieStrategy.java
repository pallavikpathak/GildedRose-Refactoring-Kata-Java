package com.gildedrose.domain.strategy;

import com.gildedrose.Item;

import static com.gildedrose.domain.constants.QualityConstants.*;

public class AgedBrieStrategy implements ItemUpdateStrategy {

    @Override
    public void update(Item item) {
        item.sellIn--;
        int increaseQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(MAX_QUALITY, item.quality + increaseQuality);
    }
}
