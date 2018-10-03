/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct  1 20:09:33 CEST 2018
 */

package uebung03.as.aufgabe03;

import gvs.tree.GVSBinaryTreeNode;
import gvs.tree.GVSTreeWithRoot;
import gvs.typ.node.GVSNodeTyp;

class AVLTreeImplGVS<K extends Comparable<? super K>, V> extends
    AVLTreeImpl<K, V> {

  protected GVSTreeWithRoot gvsTree; 
  
  private final int DELAY = 200;

  protected class AVLNodeGVS extends AVLTreeImpl<K, V>.AVLNode implements GVSBinaryTreeNode {

    protected AVLNodeGVS(Entry<K, V> entry) {
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
      return e.getKey() + " "+ e.getValue();
      //return e.getKey().toString();
    }

    public GVSNodeTyp getNodeTyp() {
      return null;
    }

  } // class BinaryTreeTestGVS.NodeGVS

 
  AVLTreeImplGVS() {
    this("AVLTreeGVS");
  }
  
  AVLTreeImplGVS(String title) {
    gvsTree = new GVSTreeWithRoot(title);
  }
  
  @Override
  protected Node newNode(Entry<K, V> entry) {
    return new AVLNodeGVS(entry);
  }
  
  @Override
  public V put(K key, V value) {
    V result = super.put(key, value);
    gvsTree.setRoot((GVSBinaryTreeNode) root);
    gvsTree.display();
    try {Thread.sleep(DELAY);} catch (InterruptedException e) {} 
    return result;
  }


  @Override
  public Entry<K, V> insert(K key, V value) {
    Entry<K, V> newEntry = super.insert(key, value);
    gvsTree.setRoot((GVSBinaryTreeNode) root);
    gvsTree.display();
    try {Thread.sleep(DELAY);} catch (InterruptedException e) {} 
    return newEntry;
  }
  
  @Override
  public Entry<K, V> remove(Entry<K, V> entry) {
    Entry<K, V> deletedEntry = super.remove(entry);
    gvsTree.display();
    try {Thread.sleep(DELAY);} catch (InterruptedException e) {} 
    return deletedEntry;
  }

}
 
 
 
