package com.gildedrose.domain.strategy;

import com.gildedrose.Item;

public class AgedBrieStrategy implements ItemUpdateStrategy{
    @Override
    public void update(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
        item.sellIn--;
        if (item.sellIn < 0 && item.quality < 50) {
            item.quality++;
        }
    }
}
