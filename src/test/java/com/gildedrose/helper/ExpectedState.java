package com.gildedrose.helper;

public class ExpectedState {
    public final int sellIn;
    public final int quality;

    private ExpectedState(int sellIn, int quality) {
        this.sellIn = sellIn;
        this.quality = quality;
    }

    public static ExpectedState sellInThenQuality(int sellIn, int quality) {
        return new ExpectedState(sellIn, quality);
    }
}
