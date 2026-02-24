package com.gildedrose;


import com.gildedrose.application.UpdateQualityUseCase;

class GildedRose {

    private final UpdateQualityUseCase updateQualityUseCase = new UpdateQualityUseCase();
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        updateQualityUseCase.updateQuality(items);
    }
}
