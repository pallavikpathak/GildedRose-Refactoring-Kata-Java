package com.gildedrose.domain.strategy;

import com.gildedrose.Item;

import static com.gildedrose.domain.constants.QualityConstants.MAX_QUALITY;

public class BackstagePassStrategy implements ItemUpdateStrategy {

    private static final int FIVE_DAYS = 5;
    private static final int TEN_DAYS = 10;

    @Override
    public void update(Item item) {

        item.quality = Math.min(MAX_QUALITY, item.quality + increaseQuality(item.sellIn));
        item.sellIn--;
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    private int increaseQuality(int sellIn) {
        if (sellIn <= FIVE_DAYS) return 3;
        if (sellIn <= TEN_DAYS) return 2;
        return 1;
    }
}
