/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Sep 24 12:08:35 CEST 2018
 */

package uebung02.as.aufgabe01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import gvs.tree.GVSBinaryTreeNode;
import gvs.tree.GVSTreeWithRoot;
import gvs.typ.node.GVSNodeTyp;

public class BinarySearchTree<K extends Comparable<? super K>, V> {

    protected Node root;

    public static class Entry<K, V> {

        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        protected K setKey(K key) {
            K oldKey = this.key;
            this.key = key;
            return oldKey;
        }

        public K getKey() {
            return key;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("[").append(key).append("/").append(value).append("]");
            return result.toString();
        }

    } // End of class Entry

    protected class Node{

        private Entry<K, V> entry;
        private Node leftChild;
        private Node rightChild;

        public Node(Entry<K, V> entry) {
            this.entry = entry;
        }

        public Node(Entry<K, V> entry, Node leftChild, Node rightChild) {
            this.entry = entry;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public Entry<K, V> getEntry() {
            return entry;
        }

        public Entry<K, V> setEntry(Entry<K, V> entry) {
            Entry<K, V> oldEntry = entry;
            this.entry = entry;
            return oldEntry;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

    } // End of class Node

    public Entry<K, V> insert(K key, V value) {
        Entry<K,V> entry = new Entry<>(key, value);
        if(isEmpty()){
            root = newNode(entry);
            return entry;
        }
        Node parent = findInsertParent(root, entry);
        if(key.compareTo(parent.getEntry().getKey()) < 0){
            parent.setLeftChild(newNode(entry));
        }else{
            parent.setRightChild(newNode(entry));
        }
        return entry;
    }

    protected Node findInsertParent(Node startNode, Entry<K,V> entry){
        if(entry.getKey().compareTo(startNode.getEntry().getKey()) < 0 && startNode.getLeftChild() != null){
            return findInsertParent(startNode.getLeftChild(), entry);
        }else if(entry.getKey().compareTo(startNode.getEntry().getKey()) >= 0 && startNode.getRightChild() != null){
            return findInsertParent(startNode.getRightChild(), entry);
        }else{
            return startNode;
        }
    }

    /**
     * Factory-Method: Creates a new node.
     *
     * @param entry The entry to be inserted in the new node.
     * @return The new created node.
     */
    protected Node newNode(Entry<K, V> entry) {
        return new Node(entry);
    }

    public void clear() {
        root = null;
    }

    public Entry<K, V> find(K key) {
        Node entryNode = findNextNode(root, key);
        if(entryNode == null){return null;}
        return entryNode.getEntry();
    }

    Node findNextNode(Node startNode, K key){
        if(startNode == null){return null;}
        K startKey = startNode.getEntry().getKey();
        if(key.compareTo(startKey) < 0 && startNode.getLeftChild() != null){
            return findNextNode(startNode.getLeftChild(), key);
        }else if(key.compareTo(startKey) > 0 && startNode.getRightChild() != null){
            return findNextNode(startNode.getRightChild(), key);
        }else if(key.compareTo(startKey) == 0){
            return startNode;
        }else{
            return null;
        }
    }

    /**
     * Returns a collection with all entries with key.
     *
     * @param key The key to be searched.
     * @return Collection of all entries found. An empty collection is returned if
     * no entry with key is found.
     */
    public Collection<Entry<K, V>> findAll(K key) {
        LinkedList<Entry<K,V>> collection = new LinkedList<>();
        if(isEmpty()){return collection;}
        return findAllRec(root, key);
    }

    private Collection<Entry<K, V>> findAllRec(Node startNode, K key){
        LinkedList<Entry<K,V>> collection = new LinkedList<>();
        startNode = findNextNode(startNode,key);
        if(startNode == null){return collection;}
        collection.add(startNode.getEntry());
        collection.addAll(findAllRec(startNode.getLeftChild(), key));
        collection.addAll(findAllRec(startNode.getRightChild(), key));
        return collection;
    }

    /**
     * Returns a collection with all entries in inorder.
     *
     * @return Inorder-Collection of all entries.
     */
    public Collection<Entry<K, V>> inorder() {
        return inorderRec(root);
    }

    private LinkedList<Entry<K,V>> inorderRec(Node startNode){
        LinkedList<Entry<K,V>> collection = new LinkedList<>();
        if(startNode == null){return collection;}

        collection.addAll(inorderRec(startNode.getLeftChild()));
        collection.add(startNode.getEntry());
        collection.addAll(inorderRec(startNode.getRightChild()));

        return collection;
    }

    /**
     * Prints the entries of the tree as a list in inorder to the console.
     */
    public void printInorder() {
        Collection<Entry<K,V>> collection = inorder();
        for (Entry<K,V> entry: collection) {
            System.out.print(entry.toString());
        }
        System.out.println();
    }

    public Entry<K, V> remove(Entry<K, V> entry) {
        if(isEmpty()){return null;}
        Node entryNode = newNode(entry);
        if(entry.equals(root.getEntry())){
            Node tempRoot = newNode(null);
            tempRoot.setLeftChild(root);
            root = tempRoot;
            remove(entry);
            root = tempRoot.getLeftChild();
            return entry;
        }
        Node removeParent = findRemoveParent(root, entry);
        if(removeParent == null){return null;}
        Node toRemove = removeParent.getLeftChild();
        boolean leftChild = true;       //is "toRemove" the left child of "removeParent"?
        if(removeParent.getRightChild() != null && entry.getKey().equals(removeParent.getRightChild().getEntry().getKey())){
            leftChild = !leftChild;
            toRemove = removeParent.getRightChild();
        }
        // 1. Case, "toRemove" has no children
        if(toRemove.getRightChild() == null && toRemove.getLeftChild() == null){
            if(leftChild){
                removeParent.setLeftChild(null);
            }else{
                removeParent.setRightChild(null);
            }
        }else if(toRemove.getLeftChild() != null && toRemove.getRightChild() != null){ // 2. Case, "toRemove" has exactly 2 Children
            Node replaceNodeParent = findNextInlineNodeParent(toRemove);

            Node replaceNode = replaceNodeParent.getLeftChild() != null?replaceNodeParent.getLeftChild():replaceNodeParent;
            toRemove.setEntry(replaceNode.getEntry());

            if(replaceNode != replaceNodeParent){
                replaceNodeParent.setLeftChild(replaceNodeParent.getLeftChild().getRightChild());
            }else{
                toRemove.setRightChild(replaceNode.getRightChild());
            }



//            Node replaceNodeParent = findNextInlineNodeParent(toRemove);
//            Node replaceNode;
//
//            if(replaceNodeParent.getLeftChild() == null){
//                replaceNode = replaceNodeParent;
//                if(leftChild){
//                    removeParent.setLeftChild(replaceNode);
//                }else{
//                    removeParent.setRightChild(replaceNode);
//                }
//                replaceNode.setLeftChild(toRemove.getLeftChild());
//                replaceNode.setRightChild(toRemove.getRightChild().getRightChild());
//
//                return entry;
//            }
//            replaceNode = replaceNodeParent.getLeftChild();
//            replaceNodeParent.setLeftChild(replaceNodeParent.getLeftChild().getRightChild());
//            if(leftChild){
//                removeParent.setLeftChild(replaceNode);
//            }else{
//                removeParent.setRightChild(replaceNode);
//            }
//            replaceNode.setLeftChild(toRemove.getLeftChild());
//            replaceNode.setRightChild(toRemove.getRightChild());
        }else if(toRemove.getLeftChild() != null){ // 3. Case, "toRemove" has exactly 1 Child
            if(leftChild){
                removeParent.setLeftChild(toRemove.getLeftChild());
            }else{
                removeParent.setRightChild(toRemove.getLeftChild());
            }
        }else if(toRemove.getRightChild() != null){
            if(leftChild){
                removeParent.setLeftChild(toRemove.getRightChild());
            }else{
                removeParent.setRightChild(toRemove.getRightChild());
            }
        }
        return entry;
    }

    public Node findNextInlineNodeParent(Node startNode){
        if(startNode == null){return null;}
        if(startNode.getRightChild() == null){return startNode;}
        startNode = startNode.getRightChild();
        if(startNode.getLeftChild() == null){return startNode;}
        while(startNode.getLeftChild().getLeftChild() != null){
            startNode = startNode.getLeftChild();
        }
        return startNode;
    }

    public Node findRemoveParent(Node startNode, Entry<K, V> entry){
        if(startNode == null){return null;}
        if(startNode.getLeftChild() != null && startNode.getLeftChild().getEntry().equals(entry)){
            return startNode;
        }
        if(startNode.getRightChild() != null && startNode.getRightChild().getEntry().equals(entry)){
            return startNode;
        }
        if(entry.getKey().compareTo(startNode.getEntry().getKey()) < 0){
            return findRemoveParent(startNode.getLeftChild(), entry);
        }else{
            return findRemoveParent(startNode.getRightChild(), entry);
        }

    }

    /**
     * The height of the tree.
     *
     * @return The actual height. -1 for an empty tree.
     */
    public int getHeight() {
        return getHeightRec(root);
    }

    private int getHeightRec(Node startNode){
        if(startNode == null){
            return -1;
        }
        return Math.max(getHeightRec(startNode.getLeftChild()),getHeightRec(startNode.getRightChild())) + 1;
    }

    public int size() {
        if(isEmpty()){return 0;}
        return getSizeRec(root);
    }

    private int getSizeRec(Node startNode){
        if(startNode == null){return 0;}
        return getSizeRec(startNode.getRightChild()) + getSizeRec(startNode.getLeftChild()) + 1;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public static void main(String[] args) {

        // Example from lecture "Löschen (IV/IV)":
//        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        BinarySearchTreeGVS<Integer, String> bst = new BinarySearchTreeGVS<>();
        System.out.println("Inserting:");
        bst.insert(1, "Str1");
        bst.printInorder();
        bst.insert(3, "Str3");
        bst.printInorder();
        bst.insert(2, "Str2");
        bst.printInorder();
        bst.insert(8, "Str8");
        bst.printInorder();
        bst.insert(9, "Str9");
        bst.insert(6, "Str6");
        bst.insert(5, "Str5");
        bst.printInorder();

        System.out.println("Removeing 3:");
        Entry<Integer, String> entry = bst.find(3);
        System.out.println(entry);
        bst.remove(entry);
        bst.printInorder();

        if (bst instanceof BinarySearchTreeGVS) {
          bst.gvsTree.disconnect();
        }

    }

  /* Session-Log:

  Inserting:
  [1/Str1] 
  [1/Str1] [3/Str3] 
  [1/Str1] [2/Str2] [3/Str3] 
  [1/Str1] [2/Str2] [3/Str3] [8/Str8] 
  [1/Str1] [2/Str2] [3/Str3] [5/Str5] [6/Str6] [8/Str8] [9/Str9] 
  Removeing 3:
  [3/Str3]
  [1/Str1] [2/Str2] [5/Str5] [6/Str6] [8/Str8] [9/Str9] 

  */


} // End of class BinarySearchTree
 
