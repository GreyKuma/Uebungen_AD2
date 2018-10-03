/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Sep 24 12:08:35 CEST 2018
 */

package uebung02.as.aufgabe01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class BinarySearchTreeIterative<K extends Comparable<? super K>, V> {

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

    protected class Node {

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
        // Check if Tree exists
        if(root == null){
            root = newNode(entry);
            return root.getEntry();
        }
        // Find where to insert
        Node parentNode = null;
        // Insert
        if(parentNode.getEntry().getKey().compareTo(key) < 0){
            parentNode.setRightChild(newNode(entry));
            return parentNode.getRightChild().getEntry();
        }else{
            parentNode.setLeftChild(newNode(entry));
            return parentNode.getLeftChild().getEntry();
        }
    }

    private Node findChild(Node parent, Entry<K, V> entry){
        if(parent.getLeftChild() != null && parent.getLeftChild().getEntry() == entry){
            return parent.getLeftChild();
        }else if(parent.getRightChild() != null && parent.getRightChild().getEntry() == entry){
            return parent.getRightChild();
        }else{
            return null;
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
        Node currentNode = root;
        while(currentNode != null){
            K currentKey = currentNode.getEntry().getKey();
            if(currentKey.compareTo(key) > 0){
                currentNode = currentNode.getLeftChild();
            }else if(currentKey.compareTo(key) < 0){
                currentNode = currentNode.getRightChild();
            }else{
                return currentNode.getEntry();
            }
        }
        return null;
    }

    /**
     * Returns a collection with all entries with key.
     *
     * @param key The key to be searched.
     * @return Collection of all entries found. An empty collection is returned if
     * no entry with key is found.
     */
    public Collection<Entry<K, V>> findAll(K key) {
        Collection <Entry<K, V>> collection = new ArrayList<>();
        for (Entry<K, V> entry:inorder()) {
            if(entry.getKey() == key){
                collection.add(entry);
            }
        }
        return collection;
    }

    /**
     * Returns a collection with all entries in inorder.
     *
     * @return Inorder-Collection of all entries.
     */
    public Collection<Entry<K, V>> inorder() {
        Collection<Entry<K, V>> collection = new LinkedList<>();
        LinkedList<Node> toDoList = new LinkedList<>();
        LinkedList<Node> visitedList = new LinkedList<>();
        if(root == null){return collection;}

        toDoList.add(root);

        while(toDoList.size() > 0){
            Node currentNode = toDoList.getLast();
            if(currentNode.getLeftChild() != null && !visitedList.contains(currentNode.getLeftChild())){
                toDoList.add(currentNode.getLeftChild());
            }else{
                collection.add(currentNode.getEntry());
                visitedList.add(currentNode);
                toDoList.removeLast();
                if(currentNode.getRightChild() != null && !visitedList.contains(currentNode.getRightChild())){
                    toDoList.add(currentNode.getRightChild());
                }
            }
        }
        return collection;
    }

    /**
     * Prints the entries of the tree as a list in inorder to the console.
     */
    public void printInorder() {
        Collection<Entry<K,V>> collection = inorder();
        for (Entry<K,V> entry:collection) {
            System.out.print(entry.toString()+ " ");
        }
        System.out.print("\n");
    }

    public Entry<K, V> remove(Entry<K, V> entry) {
        // TODO Implement here...

        return entry;
    }
    private Node findNodeParent(Node node, Entry<K,V> entry){
        Node parentNode, entryNode;
        parentNode = null;

        if(node == null){
            return null;
        }else if(entry.getKey().compareTo(node.getEntry().getKey()) < 0){
            entryNode = findNodeParent(node.getLeftChild(), entry);
        }else if(entry.getKey().compareTo(node.getEntry().getKey()) > 0){
            entryNode = findNodeParent(node.getRightChild(), entry);
        }else{
            if(node.getEntry().equals(entry)){
                return parentNode;
            }else{
                entryNode = findNodeParent(node.getRightChild(), entry);
            }
        }


        while(entryNode != null){
            K currentKey = entryNode.getEntry().getKey();
            if(currentKey.compareTo(entry.getKey()) < 0){
                parentNode = entryNode;
                entryNode = entryNode.getRightChild();
            }else{
                parentNode = entryNode;
                entryNode = entryNode.getLeftChild();
            }
        }
        return parentNode;
    }

    private Node findNodeWithSameKeyParent(Entry<K,V> entry){
        Node currentNode = root;
        Node previousNode = currentNode;
        while(currentNode != null){
            K currentKey = currentNode.getEntry().getKey();
            if(currentKey.compareTo(entry.getKey()) < 0){
                previousNode = currentNode;
                currentNode = currentNode.getRightChild();
            }else{
                previousNode = currentNode;
                currentNode = currentNode.getLeftChild();
            }
        }
        return previousNode;
    }

    /**
     * The height of the tree.
     *
     * @return The actual height. -1 for an empty tree.
     */
    public int getHeight() {
        LinkedList<Node> toDoList = new LinkedList<>();
        LinkedList<Node> visitedList = new LinkedList<>();
        if(root == null){return -1;}
        int maxheight = 0;
        int tempheight = 0;

        toDoList.add(root);

        while(toDoList.size() > 0){
            Node currentNode = toDoList.getLast();
            if(currentNode.getLeftChild() != null && !visitedList.contains(currentNode.getLeftChild()) ){
                toDoList.add(currentNode.getLeftChild());
                tempheight++;
            }else if (currentNode.getRightChild() != null && !visitedList.contains(currentNode.getRightChild())) {
                toDoList.add(currentNode.getRightChild());
                tempheight++;
            }else{
                visitedList.add(currentNode);
                toDoList.removeLast();
                tempheight--;
            }
            maxheight = Math.max(maxheight,tempheight);
        }
        return maxheight;
    }

    public int size() {
        return inorder().size();
    }

    public boolean isEmpty() {
        return root == null;
    }

    public static void main(String[] args) {

        // Example from lecture "Löschen (IV/IV)":
        BinarySearchTreeIterative<Integer, String> bst = new BinarySearchTreeIterative<>();
        //BinarySearchTree<Integer, String> bst = new BinarySearchTreeGVS<>();
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

        //if (bst instanceof BinarySearchTreeGVS) {
        //  ((BinarySearchTreeGVS<Integer, String>)bst).gvsTree.disconnect();
        //}

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
 
