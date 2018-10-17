/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Wed Oct 10 14:45:53 CEST 2018
 */

package uebung05.as.aufgabe01;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BubbleSortJUnitTest {

  @Test
  public void test01() {
    Integer[] arr = {3, 1, 2};
    sort(arr);
  }
  
  @Test
  public void test02() {
    Integer[] arr = {2, 3, 1};
    sort(arr);
  }
  
  @Test
  public void test03() {
    Integer[] arr = {2, 1};
    sort(arr);
  }
  
  @Test
  public void test04() {
    Integer[] arr = {1, 2};
    sort(arr);
  }
  
  @Test
  public void test05() {
    Integer[] arr = {1};
    sort(arr);
  }
  
  @Test
  public void test06() {
    Integer[] arr = {};
    sort(arr);
  }

  @Test
  public void test07StressTest() {
    final int NUMBER_OF_TESTS = 10000;
    final int LENGTH = 100;
    for (int n = 0; n < NUMBER_OF_TESTS; n++) {
      Integer[] arr = 
          new Random().ints(LENGTH, 0, 10).boxed().toArray(Integer[]::new);
      sort(arr);
    }
  }
  
  private void sort(Integer[] arr) {
    Integer[] clonedArr = arr.clone();
    BubbleSort.bubbleSort1(arr);
    verify(clonedArr, arr);
    arr = clonedArr.clone();
    BubbleSort.bubbleSort2(arr);
    verify(clonedArr, arr);
  }
  
  private void verify(Integer[] orgArr, Integer[] sortedArr) {
    Integer[] sortedOrgArr = new Integer[orgArr.length];
    System.arraycopy(orgArr, 0, sortedOrgArr, 0, orgArr.length);
    Arrays.sort(sortedOrgArr);
    assertArrayEquals(sortedOrgArr, sortedArr);
  }
  
}
 
