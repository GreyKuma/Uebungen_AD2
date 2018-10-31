/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct 29 17:32:49 CET 2018
 */

package uebung07.as.aufgabe03;


public class KnuthMorrisPratt {

    private static int totCount = 0;
    private static int task = 0;

    public static int kmpMatch(String text, int startIndex, int[] fail, String p) {

        // TODO Implement here...
        int i = startIndex; int j = 0;
        while(i < text.length()){
            if(text.charAt(i) == p.charAt(j)){
                if(j == p.length()-1){
                    return i - p.length() + 1;
                }else{
                    i++;
                    j++;
                }
            }else{
                if(j > 0){
                    j = fail[j-1];
                }else{
                    i++;
                }
            }
        }
        return -1;
    }

    static int[] buildFailureFunction(String pattern) {

        // TODO Implement here...
        int plen = pattern.length();
        int[] FFunction = new int[plen];
        int i = 1; int j = 0;
        while(i < plen){
            if(pattern.charAt(i) == pattern.charAt(j)){
                FFunction[i] = j + 1;
                i++;
                j++;
            }else if(j > 0){
                j = FFunction[j-1];
            }else{
                FFunction[i] = 0;
                i++;
            }
        }

        return FFunction;
    }

    static void printFailureFunction(String p, int[] fail) {
        System.out.print("fail: ");
        for (int i = 0; i < p.length(); i++) {
            System.out.print(p.charAt(i) + "  ");
        }
        System.out.print("\n    ");
        for (int i = 0; i < p.length(); i++) {
            System.out.format("%3d", fail[i]);
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
            text = "dcdadaeddaeadaeddadae";
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
        System.out.println("Text    : " + text);
        System.out.println("Pattern : " + pattern);
        int res = 0;
        int pos = 0;
        int[] fail = buildFailureFunction(pattern);
        printFailureFunction(pattern, fail);

        while (res >= 0) {
            //System.out.println(text.substring(pos));
            res = kmpMatch(text, pos, fail, pattern);
            if (res >= 0) {
                pos = res + 1;
                System.out.println("Position: " + res);
            }
        }
        System.out.println();
        System.out.println("Total of comparison: " + totCount);
    }
}
 
 
