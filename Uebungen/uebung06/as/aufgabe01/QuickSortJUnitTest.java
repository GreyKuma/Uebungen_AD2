/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct 22 12:14:37 CEST 2018
 */

package uebung06.as.aufgabe01;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuickSortJUnitTest {
  
  private QuickSort qs = new QuickSort();
  private Comparator<Point> comp = new PointComparator();

  @Test
  public void test01() {
    
    // Test-Points(x*,y*):
    int[] x = { 7, 5, 5, 1, 5, 3 };
    int[] y = { 7, 6, 5, 9, 4, 3 };
    // Sorted:
    int[] xSorted = { 1, 3, 5, 5, 5, 7 };
    int[] ySorted = { 9, 3, 4, 5, 6, 7 };
    
    test(x, y, xSorted, ySorted);
  }
  
  
  @Test
  public void test02() {
    
    // Test-Points(x*,y*):
    int[] x = { 1, 2, 3 };
    int[] y = { 1, 2, 3 };
    // Sorted:
    int[] xSorted = { 1, 2, 3 };
    int[] ySorted = { 1, 2, 3 };
    
    test(x, y, xSorted, ySorted);
  }
  
  @Test
  public void test03() {
    
    // Test-Points(x*,y*):
    int[] x = { 3, 2, 1 };
    int[] y = { 3, 2, 1 };
    // Sorted:
    int[] xSorted = { 1, 2, 3 };
    int[] ySorted = { 1, 2, 3 };
    
    test(x, y, xSorted, ySorted);
  }
  
  @Test
  public void test04() {
    
    // Test-Points(x*,y*):
    int[] x = { 2, 2 };
    int[] y = { 2, 2 };
    // Sorted:
    int[] xSorted = { 2, 2 };
    int[] ySorted = { 2, 2 };
    
    test(x, y, xSorted, ySorted);
  }

  @Test
  public void test05StressTest() {
    IntStream.range(1, 200).forEach(
      len -> IntStream.range(0, len).forEach(i -> {
        Point[] sequence = new Point[len];
        IntStream.range(0, sequence.length).forEach(
          j -> sequence[j] = new Point((int) (Math.random() * len/2),
                                       (int) (Math.random() * len/2)));
      
        qs.inPlaceQuickSort(sequence, comp, 0, sequence.length - 1);
          
        Point minValuePoint = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Arrays.stream(sequence).reduce(minValuePoint, (a, b) -> {
          assertTrue(a.getX() <= b.getX());
          if (a.getX() == b.getX()) {
            assertTrue(a.getY() <= b.getY());
          }
          return b;
        });
      })
    );
  }
  
  
  private void test(int[] x, int[] y, int[] xSorted, int[] ySorted) {
    Point[] sequence = new Point[x.length];
    IntStream.range(0, x.length).forEach(
      i -> sequence[i] = new Point(x[i], y[i]));
    System.out.println(Arrays.toString(sequence));
    Point[] sequenceSorted = new Point[ySorted.length];
    IntStream.range(0, xSorted.length).forEach(
      i -> sequenceSorted[i] = new Point(xSorted[i], ySorted[i]));
    
    qs.inPlaceQuickSort(sequence, comp, 0, sequence.length - 1);
  
    assertArrayEquals(sequenceSorted, sequence);
  }

}
 
