/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct 29 17:32:49 CET 2018
 */

package uebung07.as.aufgabe03;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KnuthMorrisPrattJUnitTest {
  
  @Before
  public void setUp() {
    System.setOut(new PrintStream(new ByteArrayOutputStream()));
  }

  @Test
  public void test01KmpMatch() {
    String text = "abacaabaccabacabaabb";
    String pattern = "abacab";
    int[] fail = KnuthMorrisPratt.buildFailureFunction(pattern);
    int pos = KnuthMorrisPratt.kmpMatch(text, 0, fail, pattern);
    assertEquals(10, pos);
  }

  @Test
  public void test02StressTestKMP() {

    final int NUMBER_OF_TESTS = 10000;

    for (int nr = 0; nr < NUMBER_OF_TESTS; nr++) {
      String text = randomText(1, 500);
      String pattern = randomText(1, 5);
      int pos = 0;
      int kmpPos = 0;
      while (kmpPos >= 0) {
        int[] fail = KnuthMorrisPratt.buildFailureFunction(pattern);
        kmpPos = KnuthMorrisPratt.kmpMatch(text, pos, fail, pattern);
        int strPos = text.indexOf(pattern, pos);
        if (kmpPos != strPos) {
          System.err.println("Bad position : " + kmpPos);
          System.err.println("Expected     : " + strPos);
          System.err.println("Text         : >" + text + "<");
          System.err.println("Pattern      : >" + pattern + "<");
          fail("Unexpected result!");
        }
        pos = kmpPos + 1;
      }
    }
  }

  /**
   * Returns a random-text with length in the range min..max.
   * 
   * @param min
   *          The lower-bound of length (inclusive).
   * @param max
   *          The upper-bound of length (inclusive).
   * @return The generated random-text.
   */
  private String randomText(int min, int max) {
    int length = min + (int) (Math.random() * (max - min + 1));
    return new Random().ints('a', 'z' + 1)
      .limit(length)
      .collect(StringBuilder::new, (sb, i) -> sb.append((char) i),
       StringBuilder::append)
      .toString();
  }

}
 
 
 
