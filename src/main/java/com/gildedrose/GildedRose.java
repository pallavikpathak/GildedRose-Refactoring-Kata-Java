package com.gildedrose;


import com.gildedrose.domain.factory.ItemStrategyFactory;

class GildedRose {

    Item[] items;
    private final ItemStrategyFactory itemStrategyFactory = new ItemStrategyFactory();

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            itemStrategyFactory.getStrategy(item).update(item);
        }
    }
}
