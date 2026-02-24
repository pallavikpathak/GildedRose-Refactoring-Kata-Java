package com.gildedrose;


import static com.gildedrose.domain.constants.ItemNames.*;

class GildedRose {

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    private static void updateNormal(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
        item.sellIn = item.sellIn - 1;
        if (item.sellIn < 0 && item.quality > 0) {
            item.quality--;
        }
    }

    private static void updateAgedBrie(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
        item.sellIn = item.sellIn - 1;
        if (item.sellIn < 0 && item.quality < 50) {
            item.quality++;

        }
    }

    private static void updateBackStagePass(Item item) {
        if (item.quality < 50) {
            item.quality++;
            if (item.sellIn < 11 && item.quality < 50) {
                item.quality++;
            }
            if (item.sellIn < 6 && item.quality < 50) {
                item.quality++;
            }
        }
        item.sellIn = item.sellIn - 1;
        if (item.sellIn < 0) {
            item.quality = 0;
        }
    }

    public void updateQuality() {
        for (Item item : items) {

            switch (item.name) {
                case SULFURAS -> {
                    //quality and sell in never change
                }
                case BACKSTAGE_PASSES -> updateBackStagePass(item);
                case AGED_BRIE -> updateAgedBrie(item);
                default -> updateNormal(item);
            }
        }
    }
}
