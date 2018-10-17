/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct  8 16:47:09 CEST 2018
 */

package uebung04.as.aufgabe01.Original;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import uebung02.ml.aufgabe01.BinarySearchTree;

class AVLTreeImpl<K extends Comparable<? super K>, V> extends
    BinarySearchTree<K, V> {

  /**
   * After a BST-operation, actionNode shall point to where the balance has to
   * be checked. -> rebalance() will then be called with actionNode.
   */
  protected AVLNode actionNode;
  

  protected class AVLNode extends BinarySearchTree<K, V>.Node {

    private int height;
    private Node parent;

    AVLNode(Entry<K, V> entry) {
      super(entry);
    }

    protected AVLNode setParent(AVLNode parent) {
      AVLNode old = avlNode(this.parent);
      this.parent = parent;
      return old;
    }

    protected AVLNode getParent() {
      return avlNode(parent);
    }

    protected int setHeight(int height) {
      int old = this.height;
      this.height = height;
      return old;
    }

    protected int getHeight() {
      return height;
    }

    @Override
    public AVLNode getLeftChild() {
      return avlNode(super.getLeftChild());
    }

    @Override
    public AVLNode getRightChild() {
      return avlNode(super.getRightChild());
    }

    @Override
    public String toString() {
      String result = String.format("%2d - %-6s : h=%d", 
                             getEntry().getKey(), getEntry().getValue(), height);
      if (parent == null) {
        result += " ROOT";
      } else {  
        boolean left = (parent.getLeftChild() == this) ? true : false;
        result += (left ? " / " : " \\ ") + "parent(key)="
            + parent.getEntry().getKey();
      }
      return result;
    }

  } // End of class AVLNode

  
  protected AVLNode getRoot() {
    return avlNode(root);
  }
  
  public V put(K key, V value) {
    Entry<K, V> entry = find(key);
    if (entry != null) { 
      // key already exists in the Tree
      return entry.setValue(value);
    } else { 
      // key does not exist in the Tree yet
      super.insert(key, value);
      rebalance(actionNode);
      actionNode = null;
      return null;
    }
  }
  
  public V get(K key) {
    Entry<K, V> entry = super.find(key);
    if (entry != null) {
      return entry.getValue();
    } else {
      return null;
    }
  }

  @Override
  protected Node insert(Node node, Entry<K, V> entry) {
    if (node != null) { 
      actionNode = avlNode(node);
    } 
    // calling now the BST-insert() which will do the work:
    AVLNode result = avlNode(super.insert(node, entry));
    if (node == null) { 
      // In this case: result of super.insert() is the new node!
      result.setParent(actionNode);
    }
    return result;
  }

  /**
   * The height of the tree.
   * 
   * @return The actual height. -1 for an empty tree.
   */
  @Override
  public int getHeight() {
    return height(avlNode(root));
  }

  /**
   * Returns the height of this node.
   * 
   * @param node
   * @return The height or -1 if null.
   */
  protected int height(AVLNode node) {
    return (node != null) ? node.getHeight() : -1;
  }

  /**
   * Restructures the tree with rotations.
   * 
   * @param xPos
   *          The X-node.
   * @return The new root-node of this subtree.
   */
  protected AVLNode restructure(AVLNode xPos) {
    // TODO Implement here...
    return null;
  }

  protected AVLNode tallerChild(AVLNode node) {
    // TODO Implement here...
    return null;
  }

  protected AVLNode rotateWithLeftChild(AVLNode k2) {
    // TODO Implement here...
    return null;
  }

  protected AVLNode doubleRotateWithLeftChild(AVLNode k3) {
    // TODO Implement here...
    return null;
  }

  protected AVLNode rotateWithRightChild(AVLNode k1) {
    // TODO Implement here...
    return null;
  }

  protected AVLNode doubleRotateWithRightChild(AVLNode k3) {
    // TODO Implement here...
    return null;
  }

  protected boolean isBalanced(AVLNode node) {
    // TODO Implement here...
    return false;
  }
  
  /**
   * Assures the balance of the tree from 'node' up to the root.
   * 
   * @param node
   *          The node from where to start.
   */
  protected void rebalance(AVLNode node) {
    // TODO Implement here...
  }

  /**
   * Assures the correct height for node.
   * 
   * @param node
   *          The node to assure its height.
   */
  protected void setHeight(AVLNode node) {
    if (node == null) {
      return;
    }
    int heightLeftChild = height(node.getLeftChild());
    int heightRightChild = height(node.getRightChild());
    node.setHeight(1 + Math.max(heightLeftChild, heightRightChild));
  }
  
  /**
   * Factory-Method. Creates a new node.
   * 
   * @param entry
   *          The entry to be inserted in the new node.
   * @return The new created node.
   */
  @Override
  protected Node newNode(Entry<K, V> entry) {
    return new AVLNode(entry);
  }
  
  public V remove(K key) {
    // TODO Implement here...
    return null;
  }
 
  /**
   * Generates an inorder-node-list.
   * 
   * @param nodeList
   *          The node-list to fill in inorder.
   * @param node
   *          The node to start from.
   */
  protected void inorder(Collection<AVLNode> nodeList, AVLNode node) {
    if (node == null)
      return;
    inorder(nodeList, node.getLeftChild());
    nodeList.add(node);
    inorder(nodeList, node.getRightChild());
  }
  
  @SuppressWarnings("unchecked")
  protected AVLNode avlNode(Node node) {
    return (AVLNode)node;
  }
  
  public void print() {
    List<AVLNode> nodeList = new LinkedList<>();
    inorder(nodeList, avlNode(root));
    for (AVLNode node: nodeList) {
      System.out.println(node + "  ");
    }
  }
  
}

 
