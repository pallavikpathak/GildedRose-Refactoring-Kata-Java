package com.gildedrose.domain.factory;

import com.gildedrose.Item;
import com.gildedrose.domain.strategy.*;

import static com.gildedrose.domain.constants.ItemNames.*;

public class ItemStrategyFactory {

    public ItemUpdateStrategy getStrategy(Item item) {

        return switch (item.name) {
            case SULFURAS -> new SulfurasStrategy();
            case BACKSTAGE_PASSES -> new BackstagePassStrategy();
            case AGED_BRIE -> new AgedBrieStrategy();
            case CONJURED -> new ConjuredStrategy();
            default -> new NormalStrategy();
        };
    }

}

