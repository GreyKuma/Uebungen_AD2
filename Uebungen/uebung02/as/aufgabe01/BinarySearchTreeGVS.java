/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Fri Sep 28 13:46:31 CEST 2018
 */

package uebung02.as.aufgabe01;

import gvs.tree.GVSBinaryTreeNode;
import gvs.tree.GVSTreeWithRoot;
import gvs.typ.node.GVSNodeTyp;

public class BinarySearchTreeGVS<K extends Comparable<? super K>, V> extends
        uebung02.as.aufgabe01.BinarySearchTree<K, V> {

  protected GVSTreeWithRoot gvsTree = new GVSTreeWithRoot("BinaryTreeTestGVS");

  protected class NodeGVS extends Node implements GVSBinaryTreeNode {

    protected NodeGVS(Entry<K, V> entry) {
      super(entry);
    }

    public GVSBinaryTreeNode getGVSLeftChild() {
      return (GVSBinaryTreeNode) getLeftChild();
    }

    public GVSBinaryTreeNode getGVSRightChild() {
      return (GVSBinaryTreeNode) getRightChild();
    }

    public String getNodeLabel() {
      Entry<K, V> e = getEntry();
      return e.getKey().toString() + " / " + e.getValue().toString();
    }

    public GVSNodeTyp getNodeTyp() {
      return null;
    }

  } // class BinaryTreeTestGVS.NodeGVS

  @Override
  protected Node newNode(Entry<K, V> entry) {
    return new NodeGVS(entry);
  }

  @Override
  public Entry<K, V> insert(K key, V value) {
    Entry<K, V> newEntry = super.insert(key, value);
    gvsTree.setRoot((GVSBinaryTreeNode) root);
    gvsTree.display();
    return newEntry;
  }

  @Override
  public Entry<K, V> remove(Entry<K, V> entry) {
    Entry<K, V> deletedEntry = super.remove(entry);
    gvsTree.display();
    return deletedEntry;
  }

}
 
