/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct  1 20:09:33 CEST 2018
 */

package uebung03.as.aufgabe03;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


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
        " 1 - Str1   : h=0 / parent(key)=2",
        " 2 - Str2   : h=1 ROOT", 
        " 3 - Str3   : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
    assertEquals(1, avlTree.getHeight());
  }
  
  @Test
  public void test02Get() {
    int[] keys = { 2, 1, 4, 5, 3 };
    String[] expected = { 
        " 1 - Str1   : h=0 / parent(key)=2",
        " 2 - Str2   : h=2 ROOT", 
        " 3 - Str3   : h=0 / parent(key)=4", 
        " 4 - Str4   : h=1 \\ parent(key)=2",
        " 5 - Str5   : h=0 \\ parent(key)=4",
    };
    runTest(keys, expected);
    assertEquals(2, avlTree.getHeight());
    assertEquals("Str2", avlTree.get(2));
    assertEquals("Str5", avlTree.get(5));
    assertNull(avlTree.get(0));
    assertNull(avlTree.get(6));
  }

  @Test
  public void test03() {
    int[] keys = { 2, 3, 1 };
    String[] expected = { 
        " 1 - Str1   : h=0 / parent(key)=2",
        " 2 - Str2   : h=1 ROOT", 
        " 3 - Str3   : h=0 \\ parent(key)=2", 
    };
    runTest(keys, expected);
    assertEquals(1, avlTree.getHeight());
    avlTree.put(2, "Str2:2");
    avlTree.put(2, "Str2:3");
    assertEquals(1, avlTree.getHeight());
    expected = new String[] { 
        " 1 - Str1   : h=0 / parent(key)=2",
        " 2 - Str2:3 : h=1 ROOT", 
        " 3 - Str3   : h=0 \\ parent(key)=2", 
    };
    Collection<AVLTreeImpl<Integer, String>.AVLNode> nodes = new LinkedList<>();
    avlTree.inorder(nodes, avlTree.getRoot());
    verify(nodes, expected);
  }
  
  
  private void runTest(int[] keys, String[] expected) {
    for (int key : keys) {
      avlTree.put(key, "Str" + key);
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
 
