package com.gildedrose.application;

import com.gildedrose.Item;
import com.gildedrose.domain.factory.ItemStrategyFactory;

public class UpdateQualityUseCase {
    private final ItemStrategyFactory itemStrategyFactory = new ItemStrategyFactory();

    public void updateQuality(Item[] items) {
        for (Item item : items) {
            itemStrategyFactory.getStrategy(item).update(item);
        }
    }
}
