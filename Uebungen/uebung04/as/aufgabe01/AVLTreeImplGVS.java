/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct  8 16:47:09 CEST 2018
 */

package uebung04.as.aufgabe01;

import gvs.tree.GVSBinaryTreeNode;
import gvs.tree.GVSTreeWithRoot;
import gvs.typ.node.GVSNodeTyp;
import uebung04.as.aufgabe01.AVLTreeImpl;

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
  protected AVLNode rotateWithRightChild(AVLNode k1) {
    gvsTree.setRoot((GVSBinaryTreeNode) root);
    gvsTree.display();
    try {Thread.sleep(DELAY);} catch (InterruptedException e) {} 
    AVLNode newRoot = super.rotateWithRightChild(k1);
    return newRoot;
  }
  
  @Override
  protected AVLNode rotateWithLeftChild(AVLNode k2) {
    gvsTree.setRoot((GVSBinaryTreeNode) root);
    gvsTree.display();
    try {Thread.sleep(DELAY);} catch (InterruptedException e) {} 
    AVLNode newRoot = super.rotateWithLeftChild(k2);
    return newRoot;
  }

  @Override
  public V remove(K key) {
    V result = super.remove(key);
    gvsTree.setRoot((GVSBinaryTreeNode) root);
    gvsTree.display();
    try {Thread.sleep(DELAY);} catch (InterruptedException e) {} 
    return result;
  }

}
 
 
 
