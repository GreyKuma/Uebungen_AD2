/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Sep 24 12:08:35 CEST 2018
 */

package uebung02.as.aufgabe01;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import uebung02.as.aufgabe01.BinarySearchTree.Entry;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BinarySearchTreeJUnitTest {

  BinarySearchTree<Integer, String> bst;

  @Before
  public void setUp() {
    bst = new BinarySearchTree<Integer, String>();
  }

  @Test
  public void test01EmptySizeInsertClear() {
    assertTrue(bst.isEmpty());
    assertEquals(0, bst.size());
    bst.insert(1, "String_1");
    assertEquals(1, bst.size());
    assertFalse(bst.isEmpty());
    bst.insert(2, "String_2");
    assertEquals(2, bst.size());
    bst.insert(2, "String_2");
    assertEquals(3, bst.size());
    bst.clear();
    assertTrue(bst.isEmpty());
    assertEquals(0, bst.size());
  }

  @Test
  public void test02Find() {
    Entry<Integer, String> entry;
    entry = bst.find(1);
    assertNull(entry);
    Entry<Integer, String> insertedEntry = bst.insert(1, "String_1");
    entry = bst.find(1);
    assertNotNull(entry);
    assertEquals(Integer.valueOf(1), entry.getKey());
    assertEquals("String_1", entry.getValue());
    assertSame(insertedEntry, entry);
  }
  
  @Test
  public void test03FindAll() { 
    Collection<Entry<Integer, String>> col;
    col = bst.findAll(1);
    assertEquals(0, col.size());
    bst.insert(1, "String_1");
    col = bst.findAll(2);
    assertEquals(0, col.size());
    bst.insert(2, "String_2");
    col = bst.findAll(2);
    assertEquals(1, col.size());
    bst.insert(2, "String_2");
    col = bst.findAll(2);
    assertEquals(2, col.size());
  }

  @Test
  public void test04GetHeight() {
    assertEquals(-1, bst.getHeight());
    bst.insert(1, "String_1");
    assertEquals(0, bst.getHeight());
    bst.insert(2, "String_2");
    assertEquals(1, bst.getHeight());
  }

  @Test
  public void test05Remove() {
    Entry<Integer, String> entry = new Entry<>(1, "String_1");
    entry = bst.remove(entry);
    assertNull(entry);
    final Entry<Integer, String> entry1 = bst.insert(1, "String_1");
    entry = bst.remove(entry1);
    assertSame(entry, entry1);
    assertEquals(0, bst.size());
    final Entry<Integer, String> entry1a = bst.insert(1, "String_1a");
    final Entry<Integer, String> entry1b = bst.insert(1, "String_1b");
    assertEquals(2, bst.size());
    entry = bst.remove(entry1a);
    assertSame(entry1a, entry);
    assertEquals(1, bst.size());
    entry = bst.remove(entry1b);
    assertSame(entry1b, entry);
    assertEquals(0, bst.size());
  }
  
  @Test
  public void test06RemoveCase3() {
    bst.insert(1, "String_1");
    Entry<Integer, String> entryToRemove = bst.insert(3, "String_3");
    bst.insert(2, "String_2");
    bst.insert(8, "String_8");
    bst.insert(6, "String_6");
    bst.insert(9, "String_9");
    bst.insert(5, "String_5");
    assertEquals(7, bst.size()); 
    assertEquals(4, bst.getHeight());
    Entry<Integer, String> removedEntry =  bst.remove(entryToRemove);
    assertSame(entryToRemove, removedEntry);
    assertEquals(6, bst.size()); 
    assertEquals(3, bst.getHeight());
    bst.remove(bst.find(6));
    assertEquals(5, bst.size()); 
    assertEquals(3, bst.getHeight());
    bst.remove(bst.find(9));
    assertEquals(4, bst.size()); 
    assertEquals(2, bst.getHeight());
  }
  
  @Test
  public void test07RemoveCase3Special() {
    bst.insert(2, "String_2");
    bst.insert(1, "String_1");
    bst.insert(3, "String_3.1");
    bst.insert(3, "String_3.2");
    Collection<Entry<Integer, String>> col;
    col = bst.findAll(3);
    assertEquals(2, col.size());
    Entry<Integer, String> removedEntry = bst.remove(bst.find(2));
    assertNotNull(removedEntry);
    assertEquals("String_2", removedEntry.getValue());
    col = bst.findAll(3);
    assertEquals(2, col.size());
  }

  @Test
  public void test09StressTest() {
    final int SIZE = 10000;
    Random randomGenerator = new Random();
    List<Entry<Integer, String>> entriesList = new LinkedList<>();
    // key-Counters: count for every key how many time it was generated
    Map<Integer, Integer> keyCounters = new HashMap<>();
    // fill the Tree
    System.out.println("Filling Tree");
    for (int i = 0; i < SIZE; i++) {
      int key = (int) (randomGenerator.nextFloat() * SIZE / 3);
      Integer numberOfKeys = keyCounters.get(key);
      if (numberOfKeys == null) {
        numberOfKeys = 1;
      } else {
        numberOfKeys++;
      }
      keyCounters.put(key, numberOfKeys);
      Entry<Integer, String> entry = bst.insert(key, "String_" + i);
      entriesList.add(entry);
//      System.out.println(bst.size());
      assertEquals(i + 1, bst.size());
    }
    // verify the number of entries per key
    for (Map.Entry<Integer, Integer> keyEntry : keyCounters.entrySet()) {
      int key = keyEntry.getKey();
      int numberOfKeys = keyEntry.getValue();
      assertEquals(numberOfKeys, bst.findAll(key).size());
    }
    System.out.println("Deleting Entries");
    // remove all entries
    int size = bst.size();
    for (Entry<Integer, String> entry : entriesList) {
//      System.out.println("deleting: " + entry);
      Entry<Integer, String> deletedEntry = bst.remove(entry);
//      System.out.println("deleted: " + deletedEntry + ", \t" + size);
      assertSame(entry, deletedEntry);
      assertEquals(--size, bst.size());
    }
  }

}
 
