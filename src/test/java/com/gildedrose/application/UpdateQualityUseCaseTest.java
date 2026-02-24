package com.gildedrose.application;

import com.gildedrose.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class UpdateQualityUseCaseTest {

    private final UpdateQualityUseCase useCase = new UpdateQualityUseCase();

    @Test
    @DisplayName("updates all items in the list")
    void updatesAllItems() {
        Item[] items = {new Item("Elixir of the Mongoose", 10, 20), new Item("Widget", 5, 10)};
        useCase.updateQuality(items);
        assertThat(items[0].quality).isEqualTo(19);
        assertThat(items[1].quality).isEqualTo(9);
    }

    @Test
    @DisplayName("updates empty item list without error")
    void updatesEmptyList() {
        Item[] items = {};
        assertThatCode(() -> useCase.updateQuality(items)).doesNotThrowAnyException();
    }
}
