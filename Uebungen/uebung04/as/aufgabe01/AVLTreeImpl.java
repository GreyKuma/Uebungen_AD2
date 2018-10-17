/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct  1 20:09:33 CEST 2018
 */

package uebung04.as.aufgabe01;

import uebung02.as.aufgabe01.BinarySearchTree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class AVLTreeImpl<K extends Comparable<? super K>, V> extends
        BinarySearchTree<K, V> {

    /**
     * After the BST-operation 'insert()':
     * actionNode shall point to the parent of the new inserted node.
     */
    protected AVLNode actionNode;


    protected class AVLNode extends Node {

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
                boolean left = parent.getLeftChild() == this;
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
            return entry.setValue(value);
        } else {
            entry = insert(key, value);
        }
        return entry.getValue();
    }

    public V get(K key) {
        Entry<K, V> entry = find(key);
        if(entry != null){
            return entry.getValue();
        }
        return null;
    }

    @Override
    public Entry<K, V> insert(K key, V value) {
        Entry<K,V> entry = new Entry<>(key, value);
        AVLNode child = avlNode(newNode(entry));
        if(isEmpty()){
            root = child;
            child.setHeight(0);
            return entry;
        }
        actionNode = avlNode(findInsertParent(root, entry));
        if(key.compareTo(actionNode.getEntry().getKey()) < 0){
            actionNode.setLeftChild(child);
            actionNode.getLeftChild().setParent(actionNode);
        }else{
            actionNode.setRightChild(child);
            actionNode.getRightChild().setParent(actionNode);
        }
        assureHeights(child);
        if(size() > 2) {
            rebalance(actionNode);
        }
        return entry;
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
     * Assures the heights of the tree from 'node' up to the root.
     *
     * @param node The node from where to start.
     */
    protected void assureHeights(AVLNode node) {
        AVLNode parent = node.getParent();
        int left = -1;
        int right = -1;

        if(node.getLeftChild() != null){
            left = node.getLeftChild().getHeight();
        }
        if(node.getRightChild() != null){
            right = node.getRightChild().getHeight();
        }
        node.setHeight(Math.max(right, left) + 1);

        if(parent != null) {
            assureHeights(parent);
        }
    }

    /**
     * Assures the correct height for node.
     *
     * @param node The node to assure its height.
     */
    protected void setHeight(AVLNode node) {
        assureHeights(node);
    }

    /**
     * Factory-Method. Creates a new node.
     *
     * @param entry The entry to be inserted in the new node.
     * @return The new created node.
     */
    @Override
    protected Node newNode(Entry<K, V> entry) {
        return new AVLNode(entry);
    }

    /**
     * Generates an inorder-node-list.
     *
     * @param nodeList The node-list to fill in inorder.
     * @param node     The node to start from.
     */
    protected void inorder(Collection<AVLNode> nodeList, AVLNode node) {
        if (node == null)
            return;
        inorder(nodeList, node.getLeftChild());
        nodeList.add(node);
        inorder(nodeList, node.getRightChild());
    }

    // Type-Casting: Node -> AVLNode (Cast-Encapsulation)
    @SuppressWarnings("unchecked")
    protected AVLNode avlNode(Node node) {
        return (AVLNode) node;
    }

    public void print() {
        List<AVLNode> nodeList = new LinkedList<>();
        inorder(nodeList, avlNode(root));
        for (AVLNode node : nodeList) {
            System.out.println(node + "  ");
        }
    }
    protected AVLNode restructure(AVLNode xPos) {
        // TODO Implement here...

        AVLNode yPos = xPos.getParent();
        AVLNode zPos = yPos.getParent();
        if(zPos.getLeftChild() == yPos && yPos.getLeftChild() == xPos){           //left-left
            return rotateWithLeftChild(zPos);
        }else if(zPos.getRightChild() == yPos && yPos.getRightChild() == xPos){     //right-right
            return rotateWithRightChild(zPos);
        }else if(zPos.getLeftChild() == yPos && yPos.getRightChild() == xPos){     //left-right
            return doubleRotateWithLeftChild(zPos);
        }else{          //right-left
            return doubleRotateWithRightChild(zPos);
        }
    }

    protected AVLNode tallerChild(AVLNode node) {
        // TODO Implement here...
        if(node == null){return null;}
        int right = node.getRightChild() == null ? -1 : node.getRightChild().getHeight();
        int left = node.getLeftChild() == null ? -1 : node.getLeftChild().getHeight();
        if(left <= right){
            return node.getRightChild();
        }else{
            return node.getLeftChild();
        }
    }

    protected AVLNode rotateWithLeftChild(AVLNode rotationRoot) {
        // TODO Implement here...
        if(rotationRoot == null){return null;}
        AVLNode leftChild = rotationRoot.getLeftChild();
        rotationRoot.setLeftChild(leftChild.getRightChild());
        leftChild.setRightChild(rotationRoot);
        leftChild.setParent(rotationRoot.getParent());
        if(!rotationRoot.equals(root)){
            if(rotationRoot.getParent().getLeftChild() == rotationRoot){
                rotationRoot.getParent().setLeftChild(leftChild);
            }else{
                rotationRoot.getParent().setRightChild(leftChild);
            }
        }else{
            root = leftChild;
        }
        rotationRoot.setParent(leftChild);
        assureHeights(rotationRoot);
        return leftChild;
    }

    protected AVLNode doubleRotateWithLeftChild(AVLNode rotationRoot) {
        // TODO Implement here...
        rotateWithRightChild(rotationRoot.getLeftChild());
        return rotateWithLeftChild(rotationRoot);

    }

    protected AVLNode rotateWithRightChild(AVLNode rotationRoot) {
        // TODO Implement here...
        if(rotationRoot == null){return null;}
        AVLNode rightChild = rotationRoot.getRightChild();
        rotationRoot.setRightChild(rightChild.getLeftChild());
        rightChild.setLeftChild(rotationRoot);
        rightChild.setParent(rotationRoot.getParent());
        if(!rotationRoot.equals(root)){
            if(rotationRoot.getParent().getLeftChild() != null && rotationRoot.getParent().getLeftChild() == rotationRoot){
                rotationRoot.getParent().setLeftChild(rightChild);
            }else{
                rotationRoot.getParent().setRightChild(rightChild);
            }
        }else{
            root = rightChild;
        }
        rotationRoot.setParent(rightChild);
        assureHeights(rotationRoot);
        return rightChild;
    }

    protected AVLNode doubleRotateWithRightChild(AVLNode rotationRoot) {
        // TODO Implement here...
        rotateWithLeftChild(rotationRoot.getRightChild());
        return rotateWithRightChild(rotationRoot);

    }

    protected boolean isBalanced(AVLNode node) {
        // TODO Implement here...
        if(node == null){return true;}
        if(node.getHeight() == 0){return true;}
        int right = node.getRightChild() == null ? -1 : node.getRightChild().getHeight();
        int left = node.getLeftChild() == null ? -1 : node.getLeftChild().getHeight();
        int balance = right - left;
        return (balance < 2 && balance > -2);
    }

    /**
     * Assures the balance of the tree from 'node' up to the root.
     *
     * @param node
     *          The node from where to start.
     */
    protected void rebalance(AVLNode node) {
        // TODO Implement here...
        if(node == null){return;}
        if(isBalanced(node)){
            rebalance(node.getParent());
        }else{
            restructure(tallerChild(tallerChild(node)));
        }
    }

    public V remove(K key) {
        // TODO Implement here...
        Entry<K, V> entry = find(key);
        if(entry == null){return null;}
        actionNode = avlNode(super.findRemoveParent(root, entry));
        AVLNode replaceNodeParent;
        if(actionNode == null){
            actionNode = getRoot();
            replaceNodeParent = avlNode(findNextInlineNodeParent(actionNode));
        }else{
            if(actionNode.getLeftChild() == null){
                replaceNodeParent = avlNode(findNextInlineNodeParent(actionNode.getRightChild()));
            }else{
                replaceNodeParent =
                        actionNode.getLeftChild().getEntry().equals(entry)?
                                avlNode(findNextInlineNodeParent(actionNode.getLeftChild())):
                                avlNode(findNextInlineNodeParent(actionNode.getRightChild()));
            }
        }

        super.remove(entry);
        if(actionNode.getLeftChild() != null){actionNode.getLeftChild().setParent(actionNode);}
        if(actionNode.getRightChild() != null){actionNode.getRightChild().setParent(actionNode);}

        if(replaceNodeParent.getLeftChild() != null){replaceNodeParent.getLeftChild().setParent(replaceNodeParent);}
        if(replaceNodeParent.getRightChild() != null){replaceNodeParent.getRightChild().setParent(replaceNodeParent);}

        assureHeights(replaceNodeParent);
        rebalance(actionNode);
        return entry.getValue();
    }
}

 
