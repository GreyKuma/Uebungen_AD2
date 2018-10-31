/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct 29 17:32:49 CET 2018
 */

package uebung07.as.aufgabe03;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;


public class BoyerMoore {

  private static int totCount = 0;
  private static int task = 0;

  public static int bmMatch(String t, int startIndex, int[] last, String p) {
    
    // TODO Implement here...
    
    return -1;
  }

  static int[] buildLastFunction(String p) {
    
    // TODO Implement here...
    
    return null;
  }
  
  static void printLastFunction(String t, int[] last) {
    char[] charArr = t.toCharArray();
    Arrays.sort(charArr);
    HashSet<Character> set = new LinkedHashSet<>();
    for (int i = 0; i < charArr.length; i++) {
      set.add(charArr[i]);
    }
    System.out.print("last: ");
    for (char c: set) {
      System.out.print(c+"  ");
    }
    System.out.print("\n    ");
    for (char c: set) {
      System.out.format("%3d", last[c]);
    }
    System.out.println('\n');
  }

  public static void main(String[] args) {
    String text;
    String pattern;
    pattern = text = "";
    if (args.length == 0 || ((args.length == 1) && args[0].equals("1"))) {
      text = "Anna Kurnikowa war eine Tennisspielerin. Sie spielte wieder ein wenig nachdem ihre Beinverletzung fast wieder geheilt war.";
      pattern = "ein";
      task = 1;
    } else if ((args.length == 1) && args[0].equals("2")) {
      text = "adbaacaabedacedbccede";
      pattern = "daeda";
      task = 2;
    } else {
      if (args.length != 2) {
        System.err.println("Bad number of arguments: " + args.length + " (expected: 2)!");
        System.exit(2);
      }
      text = args[0];
      pattern = args[1];
    }
    System.out.println("Text    : "+text);
    System.out.println("Pattern : "+pattern);
    int res = 0;
    int pos = 0;
    int[] last = buildLastFunction(pattern);
    printLastFunction(text, last);

    while (res >= 0) {
      //System.out.println(text.substring(pos));
      res = bmMatch(text, pos, last, pattern);
      if (res >= 0) {
        System.out.println("Position: " + res);
        pos = res + 1;
        if (pos > text.length() - pattern.length()) {
          break;
        }
      }
    }
    System.out.println();
    System.out.println("Total of comparison: " + totCount);
  }
}
 
 
