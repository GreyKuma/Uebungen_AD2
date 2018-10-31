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
public class BoyerMooreJUnitTest {

  @Before
  public void setUp() {
    System.setOut(new PrintStream(new ByteArrayOutputStream()));
  }

  @Test
  public void test01BmMatch() {
    String text = "abacaabadcabacabaabb";
    String pattern = "abacab";
    int[] last = BoyerMoore.buildLastFunction(pattern);
    int pos = BoyerMoore.bmMatch(text, 0, last, pattern);
    assertEquals(10, pos);
  }

  @Test
  public void test02StressTestBM() {

    final int NUMBER_OF_TESTS = 10000;

    for (int nr = 0; nr < NUMBER_OF_TESTS; nr++) {
      int textLen = random(1, 500);
      String text = randomText(textLen);
      int patternLen = random(1, 5);
      String pattern = randomText(patternLen);
      int pos = 0;
      while (pos <= textLen - patternLen) {
        int[] last = BoyerMoore.buildLastFunction(pattern);
        int bmPos = BoyerMoore.bmMatch(text, pos, last, pattern);
        int strPos = text.indexOf(pattern, pos);
        if (bmPos != strPos) {
          System.err.println("Bad position : " + bmPos);
          System.err.println("Expected     : " + strPos);
          System.err.println("Text         : >" + text + "<");
          System.err.println("Pattern      : >" + pattern + "<");
          fail("Unexpected result!");
        }
        if (bmPos == -1) {
          break;
        }
        pos = bmPos + 1;
      }
    }
  }

  /**
   * Returns a random-number in the range from..to.
   * 
   * @param from
   *          The lower-bound (inclusive).
   * @param to
   *          The upper-bound (inclusive).
   * @return The generated random-number.
   */
  private int random(int from, int to) {
    return from + (int) (Math.random() * (to - from + 1));
  }
  
  /**
   * Returns a random-text.
   * 
   * @param length
   *          The length of the text to be generated.
   * @return The generated random-text.
   */
  private String randomText(int length) {
    return new Random().ints('a', 'z' + 1)
      .limit(length)
      .collect(StringBuilder::new, (sb, i) -> sb.append((char) i),
       StringBuilder::append)
      .toString();
  }

}
 
 
 
