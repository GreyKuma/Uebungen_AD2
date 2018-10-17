/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct  8 16:47:09 CEST 2018
 */

package uebung04.as.aufgabe01;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import uebung02.as.aufgabe01.BinarySearchTree.Entry;
import uebung04.as.aufgabe01.AVLTreeImpl;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AVLTreeJUnitTest {
  
  AVLTreeImpl<Integer, String> avlTree;

  @Before
  public void setUp() {
    //System.setProperty("NoGVS", "true");
    avlTree = new AVLTree<Integer, String>().getImpl();
  }
  
  @After
  public void tearDown() {
    if (avlTree instanceof AVLTreeImplGVS) {
      ((AVLTreeImplGVS<Integer, String>)avlTree).gvsTree.disconnect();
    }
  }
  
  @Test
  public void test01Put() {
    int[] keys = { 2, 1, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
  }
  
  @Test
  public void test02Get() {
    int[] keys = { 2, 1, 5, 4, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=2 ROOT", 
        " 3 - Str_3  : h=0 / parent(key)=4", 
        " 4 - Str_4  : h=1 \\ parent(key)=2",
        " 5 - Str_5  : h=0 \\ parent(key)=4",
    };
    runTest(keys, expected);
    assertEquals("Str_2", avlTree.get(2));
    assertEquals("Str_5", avlTree.get(5));
    assertNull(avlTree.get(0));
    assertNull(avlTree.get(6));
  }

  @Test
  public void test03SingleRotationLeftInRoot() {
    int[] keys = { 1, 2, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
  }
  
  @Test
  public void test04SingleRotationLeftBelowRoot() {
    int[] keys = { 5, 6, 1, 2, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 / parent(key)=5", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
        " 5 - Str_5  : h=2 ROOT",
        " 6 - Str_6  : h=0 \\ parent(key)=5",
    };
    runTest(keys, expected);
  }

  @Test
  public void test05SingleRotationRightInRoot() {
    int[] keys = { 3, 2, 1 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
  }
  
  @Test
  public void test06SingleRotationRightBelowRoot() {
    int[] keys = { 2, 1, 5, 4, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=2 ROOT", 
        " 3 - Str_3  : h=0 / parent(key)=4", 
        " 4 - Str_4  : h=1 \\ parent(key)=2",
        " 5 - Str_5  : h=0 \\ parent(key)=4",
    };
    runTest(keys, expected);
  }
  
  @Test
  public void test07DoubleRotationLeftInRoot() {
    int[] keys = { 1, 3, 2 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
  }
  
  @Test
  public void test08DoubleRotationLeftBelowRoot() {
    int[] keys = { 2, 1, 3, 5, 4 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=2 ROOT", 
        " 3 - Str_3  : h=0 / parent(key)=4", 
        " 4 - Str_4  : h=1 \\ parent(key)=2", 
        " 5 - Str_5  : h=0 \\ parent(key)=4", 
    };
    runTest(keys, expected);
  }
  
  @Test
  public void test09DoubleRotationRightinRoot() {
    int[] keys = { 3, 1, 2 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
  }

  @Test
  public void test10DoubleRotationRightBelowRoot() {
    int[] keys = { 4, 3, 5, 1, 2 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 / parent(key)=4",
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
        " 4 - Str_4  : h=2 ROOT", 
        " 5 - Str_5  : h=0 \\ parent(key)=4", 
    };
    runTest(keys, expected);
  }
  
  @Test
  public void test11MultipleSameKeys() {
    int[] keys = { 3, 1, 2 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
    avlTree.put(2, "Str_22");
    avlTree.put(2, "Str_23");
    expected = new String[] { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_23 : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  @Test
  public void test12RemovingCase1() {
    // Löschen Fall 1 gem. BST-Folie 12:
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    int[] keys = { 6, 2, 9, 1, 4, 8 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 / parent(key)=6", 
        " 4 - Str_4  : h=0 \\ parent(key)=2", 
        " 6 - Str_6  : h=2 ROOT",
        " 8 - Str_8  : h=0 / parent(key)=9",
        " 9 - Str_9  : h=1 \\ parent(key)=6",
    };
    runTest(keys, expected);
    assertEquals("Str_4", avlTree.remove(4));
    expected = new String[] {
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 / parent(key)=6", 
        " 6 - Str_6  : h=2 ROOT",
        " 8 - Str_8  : h=0 / parent(key)=9",
        " 9 - Str_9  : h=1 \\ parent(key)=6",
    };
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  @Test
  public void test13RemovingCase2() {
    // Löschen Fall 2 gem. BST-Folie 13:
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    int[] keys = { 6, 2, 9, 1, 4, 8, 5 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=2 / parent(key)=6", 
        " 4 - Str_4  : h=1 \\ parent(key)=2", 
        " 5 - Str_5  : h=0 \\ parent(key)=4", 
        " 6 - Str_6  : h=3 ROOT",
        " 8 - Str_8  : h=0 / parent(key)=9",
        " 9 - Str_9  : h=1 \\ parent(key)=6",
    };
    runTest(keys, expected);
    assertEquals("Str_4", avlTree.remove(4));
    expected = new String[] {
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 / parent(key)=6", 
        " 5 - Str_5  : h=0 \\ parent(key)=2", 
        " 6 - Str_6  : h=2 ROOT",
        " 8 - Str_8  : h=0 / parent(key)=9",
        " 9 - Str_9  : h=1 \\ parent(key)=6",
    };
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  @Test
  public void test14RemovingCase3() {
    // Löschen Fall 3 gem. BST-Folie 14:
    // Hinweis: Baum entsprechend 'aufgefüllt' (wegen AVL!)
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    int[] keys = { 1, -10, 4, -15, -5, 2, 9, -18, -12, -7, -3, 3, 7, 10, 6 };
    String[] expected = { 
        "-18 - Str_-18 : h=0 / parent(key)=-15",
        "-15 - Str_-15 : h=1 / parent(key)=-10",
        "-12 - Str_-12 : h=0 \\ parent(key)=-15",
        "-10 - Str_-10 : h=2 / parent(key)=1",
        "-7 - Str_-7 : h=0 / parent(key)=-5",
        "-5 - Str_-5 : h=1 \\ parent(key)=-10",
        "-3 - Str_-3 : h=0 \\ parent(key)=-5",
        " 1 - Str_1  : h=4 ROOT",
        " 2 - Str_2  : h=1 / parent(key)=4", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
        " 4 - Str_4  : h=3 \\ parent(key)=1", 
        " 6 - Str_6  : h=0 / parent(key)=7", 
        " 7 - Str_7  : h=1 / parent(key)=9", 
        " 9 - Str_9  : h=2 \\ parent(key)=4", 
        "10 - Str_10 : h=0 \\ parent(key)=9", 
    };
    runTest(keys, expected);
    assertEquals("Str_4", avlTree.remove(4));
    expected = new String[] {
        "-18 - Str_-18 : h=0 / parent(key)=-15",
        "-15 - Str_-15 : h=1 / parent(key)=-10",
        "-12 - Str_-12 : h=0 \\ parent(key)=-15",
        "-10 - Str_-10 : h=2 / parent(key)=1",
        "-7 - Str_-7 : h=0 / parent(key)=-5",
        "-5 - Str_-5 : h=1 \\ parent(key)=-10",
        "-3 - Str_-3 : h=0 \\ parent(key)=-5",
        " 1 - Str_1  : h=3 ROOT",
        " 2 - Str_2  : h=1 / parent(key)=6", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
        " 6 - Str_6  : h=2 \\ parent(key)=1", 
        " 7 - Str_7  : h=0 / parent(key)=9", 
        " 9 - Str_9  : h=1 \\ parent(key)=6", 
        "10 - Str_10 : h=0 \\ parent(key)=9", 
    };
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  @Test
  public void test15RemovingAtRoot1() {
    int[] keys = { 1, 2, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
    assertEquals("Str_1", avlTree.remove(1));
    assertEquals(2, avlTree.size());
    assertEquals("Str_3", avlTree.remove(3));
    assertEquals(1, avlTree.size());
    assertEquals("Str_2", avlTree.remove(2));
    assertEquals(0, avlTree.size());
  }
  
  @Test
  public void test16RemovingAtRoot2() {
    int[] keys = { 1, 2, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
    assertEquals("Str_1", avlTree.remove(1));
    assertEquals(2, avlTree.size());
    assertEquals("Str_2", avlTree.remove(2));
    assertEquals(1, avlTree.size());
    assertEquals("Str_3", avlTree.remove(3));
    assertEquals(0, avlTree.size());
  }
  
  @Test
  public void test17RemovingAtRoot3() {
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    int[] keys = { 1, 2, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
    assertEquals("Str_2", avlTree.remove(2));
    expected = new String[] { 
        " 1 - Str_1  : h=0 / parent(key)=3",
        " 3 - Str_3  : h=1 ROOT", 
    };
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
    assertEquals(2, avlTree.size());
    assertEquals("Str_3", avlTree.remove(3));
    assertEquals(1, avlTree.size());
    assertEquals("Str_1", avlTree.remove(1));
    assertEquals(0, avlTree.size());
  }
  
  @Test
  public void test18RemovingAtRoot4() {
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    int[] keys = { 3, 2, 6, 4 };
    String[] expected = { 
        " 2 - Str_2  : h=0 / parent(key)=3",
        " 3 - Str_3  : h=2 ROOT", 
        " 4 - Str_4  : h=0 / parent(key)=6", 
        " 6 - Str_6  : h=1 \\ parent(key)=3", 
    };
    runTest(keys, expected);
    assertEquals("Str_3", avlTree.remove(3));
    expected = new String[] { 
        " 2 - Str_2  : h=0 / parent(key)=4",
        " 4 - Str_4  : h=1 ROOT", 
        " 6 - Str_6  : h=0 \\ parent(key)=4", 
    };
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  @Test
  public void test19RemovingAtRoot5() {
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    int[] keys = { 3, 2, 6, 1, 4, 7, 5 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 / parent(key)=3",
        " 3 - Str_3  : h=3 ROOT", 
        " 4 - Str_4  : h=1 / parent(key)=6", 
        " 5 - Str_5  : h=0 \\ parent(key)=4", 
        " 6 - Str_6  : h=2 \\ parent(key)=3", 
        " 7 - Str_7  : h=0 \\ parent(key)=6", 
    };
    runTest(keys, expected);
    assertEquals("Str_3", avlTree.remove(3));
    expected = new String[] { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 / parent(key)=4",
        " 4 - Str_4  : h=2 ROOT", 
        " 5 - Str_5  : h=0 / parent(key)=6", 
        " 6 - Str_6  : h=1 \\ parent(key)=4", 
        " 7 - Str_7  : h=0 \\ parent(key)=6", 
    };
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  @Test
  public void test20RemovingAtRoot6() {
    int[] keys = { 1 };
    String[] expected = { 
        " 1 - Str_1  : h=0 ROOT", 
    };
    runTest(keys, expected);
    assertEquals(null, avlTree.remove(8888));
    assertEquals(1, avlTree.size());
    runTest(keys, expected);
    assertEquals("Str_1", avlTree.remove(1));
    assertEquals(0, avlTree.size());
  }
  
  @Test
  public void test21RemovingEntryNotInTree() {
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    int[] keys = { 1, 2, 3 };
    String[] expected = { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
    assertNull(avlTree.remove(4));
    expected = new String[] { 
        " 1 - Str_1  : h=0 / parent(key)=2",
        " 2 - Str_2  : h=1 ROOT", 
        " 3 - Str_3  : h=0 \\ parent(key)=2", 
    };
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  @Test
  public void test22StressTest() {
    final int SIZE = 10000;
    Random randomGenerator = new Random(1);
    // a Map to compare:
    Map<Integer, String> map = new Hashtable<Integer, String>();
    // key-Counters: count for every key how many time it was generated
    Map<Integer, Integer> keyCounters = new Hashtable<Integer, Integer>();
    // fill the Tree
    for (int i = 0; i < SIZE; i++) {
      int key = (int) (randomGenerator.nextFloat() * SIZE / 3);
      Integer numberOfKeys = keyCounters.get(key);
      if (numberOfKeys == null) {
        numberOfKeys = 1;
      } else {
        numberOfKeys++;
      }
      keyCounters.put(key, numberOfKeys);
      avlTree.put(key, "_" + i);
      map.put(key, "_" + i);
      assertEquals(keyCounters.size(), avlTree.size());
      assertEquals(map.size(), avlTree.size());
    }
    verifyInorder();
    // remove all Keys
    Integer[] keyArr = new Integer[1];
    keyArr = map.keySet().toArray(keyArr);
    for (int key : keyArr) { 
      assertEquals(map.remove(key), avlTree.remove(key));
      assertEquals(map.size(), avlTree.size());
      verifyInorder();
    }
    assertEquals(0, avlTree.size());
  }

  private void verifyInorder() {
    Collection<Entry<Integer, String>> inorderList = avlTree.inorder();
    int last = Integer.MIN_VALUE;
    for (Entry<Integer, String> entry: inorderList) {
      Integer key = entry.getKey();
      assertTrue(key.compareTo(last) >= 0);
      last = key;
    }
  }
  
  private void runTest(int[] keys, String[] expected) {
    for (int key : keys) {
      avlTree.put(key, "Str_" + key);
    }
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    avlTree.inorder(nodes, avlTree.getRoot());
    assertEquals(expected.length, nodes.size());
    verify(nodes, expected);
  }

  private void verify(Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes, String[] expected) {
    int i = 0;
    for (AVLTreeImpl<Integer, String>.AVLNode node: nodes) {
      String nodeStr = node.toString();
      String expectedStr = expected[i];
      assertEquals(expectedStr, nodeStr);
      i++;
    }
  }
  
}
 
