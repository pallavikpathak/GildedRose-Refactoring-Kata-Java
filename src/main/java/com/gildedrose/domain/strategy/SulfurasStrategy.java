package com.gildedrose.domain.strategy;

import com.gildedrose.Item;

public class SulfurasStrategy implements ItemUpdateStrategy{
    @Override
    public void update(Item item) {
        //quality and sell in never change
    }
}
