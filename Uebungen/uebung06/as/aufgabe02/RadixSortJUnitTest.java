/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct 22 12:16:24 CEST 2018
 */

package uebung06.as.aufgabe02;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.Test;

public class RadixSortJUnitTest {

  @Test
  public void testRadixSort() {

    final int LOOPS = 100;
    final int MIN_STRING_LEN = 1;
    final int MAX_STRING_LEN = 10;
    final int ARRAY_LEN = 100;

    IntStream.range(0, LOOPS).forEach(loop -> {
      String[] strArr = new String[ARRAY_LEN];
      IntStream.range(0, ARRAY_LEN).forEach(i -> {
        int len = random(MIN_STRING_LEN, MAX_STRING_LEN);
        char[] charArr = new char[MAX_STRING_LEN];
        IntStream.range(0, len).forEach(
          j -> charArr[j] = (char) random('a', 'z'));
        String str = new String(charArr, 0, len);
        strArr[i] = str;
      });
      String[] strArrSorted = strArr.clone();
      Arrays.sort(strArrSorted);
      new RadixSort().radixSort(strArr);
      assertArrayEquals(strArrSorted, strArr);
    });
  }

  /**
   * Returns a random-number in the range from..to.
   * 
   * @param from
   *          The Lower-Bound (inclusive).
   * @param to
   *          The Upper-Bound (inclusive).
   * @return The generated random-number.
   */
  private int random(int from, int to) {
    return from + (int) (Math.random() * (to - from + 1));
  }

}
 
 
 
