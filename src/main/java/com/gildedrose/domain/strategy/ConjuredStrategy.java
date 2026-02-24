package com.gildedrose.domain.strategy;

import com.gildedrose.Item;

public class ConjuredStrategy implements ItemUpdateStrategy {
    @Override
    public void update(Item item) {
        item.quality = Math.max(0, item.quality - 2);
        item.sellIn--;
        if (item.sellIn < 0) {
            item.quality = Math.max(0, item.quality - 2);
        }
    }
}
