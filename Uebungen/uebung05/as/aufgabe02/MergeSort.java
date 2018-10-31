/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Wed Oct 10 14:52:58 CEST 2018
 */

package uebung05.as.aufgabe02;

import java.lang.reflect.Array;
import java.util.Random;

public class MergeSort {
  
  /**
   * Sorts an Array with the Merge-Sort Algorithm.
   * Precondition: Length must be 2^x.
   * @param s Sequence (Array) to be sorted.
   * @return The sorted Sequence (Array).
   */
  public static <T extends Comparable<? super T>> T[] mergeSort(T[] s) {  
    
    // TODO Implement here...
    if(s.length > 1){
      T[] s1 = newInstance(s, s.length/2);
      T[] s2 = s1.clone();
      System.arraycopy(s, 0, s1, 0,s.length/2);
      System.arraycopy(s, s.length/2, s2, 0, s.length/2);
      s1 = mergeSort(s1);
      s2 = mergeSort(s2);
      s = merge(s1, s2);
    }
    return s;
  }
  
  /**
   * Merges the two Sequences (Arrays) 'a' and 'b' in ascending Order.
   * @param a Sequence A.
   * @param b Sequence B.
   * @return The merged Sequence.
   */
  static <T extends Comparable<? super T>> T[] merge(T[] a, T[] b) {
    T[] s = newInstance(a, a.length * 2);
    int ai = 0; // First Element in 'Sequence' A
    int bi = 0; // First Element in 'Sequence' B
    int si = 0; // First Element in 'Sequence' S
    
    // TODO Implement here...
    for(; si < s.length; si++){
      if(bi >= b.length || (ai < a.length && a[ai].compareTo(b[bi])<=0)){
        s[si] = a[ai];
        ai++;
      }else if(ai >= a.length || a[ai].compareTo(b[bi])>0){
        s[si] = b[bi];
        bi++;
      }
    }
    return s;
  }
  
  /**
   * Utility-Method to create a <T>-Array.
   * 
   * @param array
   *          An Array with the same Type as the new one (only used to get the
   *          correct Type for the new Array).
   * @param length
   *          The Length of the new Array.
   * @return The new created Array.
   */
  @SuppressWarnings("unchecked")
  static <T> T[] newInstance(T[] array, int length) {
    return (T[]) Array.newInstance(array[0].getClass(), length);
  }
  
  
  public static void main(String[] args) {
    
    Integer[] array = {7, 2, 9, 4, 3, 8, 6, 1};
    Integer[] orginalArray = array.clone();
    printArray(array);
    
    array = mergeSort(array);
    
    printArray(array);
    verify(orginalArray, array);
    
    /* Makeing some Test to measure the Time needed of mergeSort().
     * Creating int-Arrays, beginning with Length of 2^minExponent
     * until the last Array with Length of 2^maxExponent.
     */
    final int minExponent = 10;
    final int maxExponent = 15;
    int n = (int)Math.round(Math.pow(2, maxExponent));
    array = new Integer[n];
    Random rand = new Random(0);    // a Random-Generator
    for (int i = 0; i < n; i++) {
      array[i] = rand.nextInt(101); // generating Numbers: 0..100
    }
    long lastTime = Long.MAX_VALUE;
    for (int exp = minExponent; exp <= maxExponent; exp++) {
      int len = (int)Math.round(Math.pow(2, exp));
      Integer[] arr = new Integer[len];     
      final int MEASUREMENTS = 10;
      long minTime = Long.MAX_VALUE;
      for (int m = 0; m < MEASUREMENTS; m++) {
        System.arraycopy(array, 0, arr, 0, len);
        long start = System.nanoTime();  
        arr = mergeSort(arr);
        long end = System.nanoTime();
        long time = end - start;
        if (time < minTime) { 
          minTime = time;
        }
        verify(array, arr);
      }
      System.out.format("Array-Size: %,7d       Time: %,6.1f ms       "
                         + "Ratio to last: %2.1f\n", 
                         len, (double) minTime / (long) 1e6, 
                         (double) minTime / lastTime);
      lastTime = minTime;
    }
  }
  
  /**
   * Prints an int-Array to the Console.
   * @param array The int-Array.
   */
  static <T> void printArray(T[] array) {
    System.out.print("Array["+array.length+"]: ");
    for (T i: array) {
      System.out.print(i + " ");  
    }
    System.out.println("");
  }

  
  /**
   * Verifies that sortedArray is a correctly sorted based on originalArray.
   * @param originalArray The original array.
   * @param sortedArray The sorted array, based on originalArray.
   *                     Can be shorter than originalArray.
   */
  static <T extends Comparable<? super T>> void verify(T[] originalArray, 
      T[] sortedArray) {
    T[] originalSortedArray = newInstance(originalArray, sortedArray.length);     
    System.arraycopy(originalArray, 0, originalSortedArray, 0, sortedArray.length);
    java.util.Arrays.sort(originalSortedArray);
    if ( ! java.util.Arrays.equals(originalSortedArray, sortedArray)) {
      try {Thread.sleep(200);} catch(Exception e) {}
      System.err.println("ERROR: wrong sorted!");
      System.exit(1);
    }
  }
 
  
}



/* Session-Log:
 
$ java -Xint -Xms100M -Xmx100M uebung05/ml/aufgabe02/MergeSort
Array[8]: 7 2 9 4 3 8 6 1 
Array[8]: 1 2 3 4 6 7 8 9 
Array-Size:   1,024       Time:    5.7 ms       Ratio to last: 0.0
Array-Size:   2,048       Time:   12.2 ms       Ratio to last: 2.2
Array-Size:   4,096       Time:   26.0 ms       Ratio to last: 2.1
Array-Size:   8,192       Time:   57.1 ms       Ratio to last: 2.2
Array-Size:  16,384       Time:  122.2 ms       Ratio to last: 2.1
Array-Size:  32,768       Time:  249.8 ms       Ratio to last: 2.0

*/
