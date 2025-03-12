package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

class GildedRoseTest {
	
	@Test
	@DisplayName("Test del toString")
	void test_itemsToString() {
        Item[] items = new Item[] { new Item("foo", 1, 20) };
        
        assertEquals("foo, 1, 20", items[0].toString());

	}
    
    @DisplayName("Test de varios productos normales")
	@ParameterizedTest(name = "{0} = {3}; {1} = {4};  {2} = {5}") 
	@CsvSource({
		"Potion, 1, 50, Potion, 0, 49", 
		"Bread, -1, 1, Bread, -2, 0", 
		"Wand, 0, 2, Wand, -1, 0"})
    void test_producto_normal(String name, int sellIn, int quality, String nameExpected, int sellInExpected, int qualityExpected) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        
        assertNotNull(items[0]);
        
        GildedRose gildedRose = new GildedRose(items);
        
        gildedRose.updateQuality();
        
        assertAll("Producto normal",
        		() -> assertTrue(items[0].name.contains(nameExpected)),
        		() -> 	assertEquals(sellInExpected, items[0].sellIn),
        		() ->   assertEquals(qualityExpected, items[0].quality)
        		);
    }
    
    
    @DisplayName("Test de varios Aged Brie")
	@ParameterizedTest(name = "{0} = {3}; {1} = {4};  {2} = {5}") 
	@CsvSource({
		"Aged Brie, 1, -2, Aged Brie, 0, -1", 
		"Aged Brie, -1, 1, Aged Brie, -2, 3", 
		"Aged Brie, 2, 2, Aged Brie, 1, 3",
		"Aged Brie, 1, 50, Aged Brie, 0, 50",
		"Aged Brie, 0, 50, Aged Brie, -1, 50",
		})
    void test_aged_brie(String name, int sellIn, int quality, String nameExpected, int sellInExpected, int qualityExpected) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        
        assertNotNull(items[0]);
        
        GildedRose gildedRose = new GildedRose(items);
        
        gildedRose.updateQuality();
        
        assertAll("Aged Brie",
        		() ->  assertTrue(items[0].name.contains(nameExpected)),
        		() -> 	assertEquals(sellInExpected, items[0].sellIn),
        		() ->   assertEquals(qualityExpected, items[0].quality)
        		);
    }
    
    @DisplayName("Test de varios Sulfuras, Hand of Ragnaros")
	@ParameterizedTest(name = "{0} = {3}; {1} = {4};  {2} = {5}") 
	@CsvSource({ "'Sulfuras, Hand of Ragnaros', -1, 80, 'Sulfuras, Hand of Ragnaros', -1, 80", "'Sulfuras, Hand of Ragnaros', 0, 80, 'Sulfuras, Hand of Ragnaros', 0, 80", "'Sulfuras, Hand of Ragnaros', 12, 80, 'Sulfuras, Hand of Ragnaros', 12, 80"})
    void test_sulfuras(String name, int sellIn, int quality, String nameExpected, int sellInExpected, int qualityExpected) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        
        assertNotNull(items[0]);
        
        GildedRose gildedRose = new GildedRose(items);
        
        gildedRose.updateQuality();
        
        assertAll("Aged Brie",
        		() ->  assertTrue(items[0].name.contains(nameExpected)),
        		() -> 	assertEquals(sellInExpected, items[0].sellIn),
        		() ->   assertEquals(qualityExpected, items[0].quality)
        		);
    }
    
    @DisplayName("Test de varios Backstage passes")
	@ParameterizedTest(name = "{0} = {3}; {1} = {4};  {2} = {5}") 
	@CsvSource({ 
		"'Backstage passes to a TAFKAL80ETC concert', 10, 47, 'Backstage passes to a TAFKAL80ETC concert', 9, 49", 
		"'Backstage passes to a TAFKAL80ETC concert', 5, 45, 'Backstage passes to a TAFKAL80ETC concert', 4, 48", 
		"'Backstage passes to a TAFKAL80ETC concert', 0, 50, 'Backstage passes to a TAFKAL80ETC concert', -1, 0",
		"'Backstage passes to a TAFKAL80ETC concert', 12, 49, 'Backstage passes to a TAFKAL80ETC concert', 11, 50",
		"'Backstage passes to a TAFKAL80ETC concert', 10, 50, 'Backstage passes to a TAFKAL80ETC concert', 9, 50"})
    void test_backstage(String name, int sellIn, int quality, String nameExpected, int sellInExpected, int qualityExpected) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        
        assertNotNull(items[0]);
        
        GildedRose gildedRose = new GildedRose(items);
        
        gildedRose.updateQuality();
        
        assertAll("Aged Brie",
        		() ->  assertTrue(items[0].name.contains(nameExpected)),
        		() -> 	assertEquals(sellInExpected, items[0].sellIn),
        		() ->   assertEquals(qualityExpected, items[0].quality)
        		);
    }
    
    @DisplayName("Test de varios Conjured")
	@ParameterizedTest(name = "{0} = {3}; {1} = {4};  {2} = {5}") 
	@CsvSource({ 
		"Conjured Mana Cake, 1, 50, 'Conjured', 0, 48", 
		"Conjured Mana Cake, 0, 45, 'Conjured', -1, 41", 
		"Conjured Mana Cake, 1, 1, 'Conjured', 0, 0",
		"Conjured Mana Cake, 0, 2, 'Conjured', -1, 0"})
    void test_conjured(String name, int sellIn, int quality, String nameExpected, int sellInExpected, int qualityExpected) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        
        assertNotNull(items[0]);
        
        GildedRose gildedRose = new GildedRose(items);
        
        gildedRose.updateQuality();
        
        assertAll("Conjured",
        		() ->  assertTrue(items[0].name.contains(nameExpected)),
        		() -> 	assertEquals(sellInExpected, items[0].sellIn),
        		() ->   assertEquals(qualityExpected, items[0].quality)
        		);
    }
    
    
    
    
    
    
    //Opción con archivo CSV
    @DisplayName("Todos los test de una")
	@ParameterizedTest(name = "{0} = {3}; {1} = {4};  {2} = {5}") 
	@CsvFileSource(resources = "/casos-de-prueba.csv", numLinesToSkip = 1)
    void test_dataSource(String name, int sellIn, int quality, String nameExpected, int sellInExpected, int qualityExpected) {
        Item[] items = new Item[] { new Item(name, sellIn, quality) };
        
        assertNotNull(items[0]);
        
        GildedRose gildedRose = new GildedRose(items);
        
        gildedRose.updateQuality();
        
        assertAll("Conjured",
        		() ->  assertTrue(items[0].name.contains(nameExpected)),
        		() -> 	assertEquals(sellInExpected, items[0].sellIn),
        		() ->   assertEquals(qualityExpected, items[0].quality)
        		);
    }
    
    

    
    
    
  

}
