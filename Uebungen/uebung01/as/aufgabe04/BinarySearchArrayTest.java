/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Sun Sep 16 19:03:53 CEST 2018
 */

package uebung01.as.aufgabe04;

import java.util.ArrayList;
import java.util.Random;

public class BinarySearchArrayTest {

    protected ArrayList<Integer> arrayList;

    public BinarySearchArrayTest() {
        arrayList = new ArrayList<Integer>();
    }

    public void clear() {
        arrayList = new ArrayList<Integer>();
    }

    public void generateTree(int nodes) {
        for (int i : new Random().ints(nodes, 0, Integer.MAX_VALUE).toArray()) {
            if (arrayList.size() == 0)
                arrayList.add(i);
            else
                add(0, arrayList.size() - 1, i);
        }
    }

    /**
     * Adds 'content' into the ArrayList by applying a Binary-Search.
     *
     * @param lower   The lower bound (inclusive) of the range where to insert the content.
     * @param upper   The upper bound (inclusive) of the range where to insert the content.
     * @param content The number to insert into the ArrayList.
     */
    public void add(int lower, int upper, int content) {
        int middleindex = Math.floorDiv(upper + lower, 2);
        //System.out.println(lower + ", " + upper + ", " + middleindex);
        int lowervalue = arrayList.get(lower);
        int uppervalue = arrayList.get(upper);
        int middlevalue = arrayList.get(middleindex);

        if (content == middlevalue) {
            arrayList.add(middleindex, content);
        } else if (lower == upper || upper - lower == 1) {
            if (content < lowervalue) {
                arrayList.add(lower, content);
            } else if (content < uppervalue) {
                arrayList.add(upper, content);
            } else {
                arrayList.add(upper + 1, content);
            }
        } else if (content > middlevalue) {
            add(middleindex, upper, content);
        } else if (content < middlevalue) {
            add(lower, middleindex, content);
        }

    }

    public boolean verify(int size, boolean exiting) {
        int arrayListSize = arrayList.size();
        if (arrayListSize != size) {
            System.err.println("ERROR: bad size: " + arrayListSize);
            if (exiting) {
                System.exit(1);
            } else {
                return false;
            }
        }
        int lhs = Integer.MIN_VALUE;
        boolean failure = false;
        for (int i = 0; i < arrayList.size(); i++) {
            int rhs = arrayList.get(i);
            if (lhs > rhs) {
                System.out.format("ERROR: wrong order at [%d]: %d > %d\n", i, lhs, rhs);
                failure = true;
                break;
            }
            lhs = rhs;
        }
        if (failure) {
            if (arrayListSize < 20) {
                System.out.println(arrayList);
            }
            if (exiting) {
                System.exit(2);
            } else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("ARRAYLIST based TEST");
        System.out.println("Please be patient, the following operations may take some time...");
        final int BEGINSIZE = 10000;
        final int TESTRUNS = 100;
        final int VARYSIZE = 10;

        BinarySearchArrayTest binarySearchArray = new BinarySearchArrayTest();
        double avgTime = 0;
        long startTime;
        for (int i = 0; i < TESTRUNS; i++) {
            binarySearchArray.clear();
            startTime = System.currentTimeMillis();
            int size = BEGINSIZE + i * VARYSIZE;
            binarySearchArray.generateTree(size);
            avgTime = ((avgTime * i) + (System.currentTimeMillis() - startTime))
                    / (i + 1);
            binarySearchArray.verify(size, true);
        }

        System.out.println("Test successful, result is as follows:");
        System.out.println("Average time for generation is: " + avgTime + " ms");
    }

}




/* Session-Log:

ARRAYLIST based TEST
Please be patient, the following operations may take some time...
Test successful, result is as follows:
Average time for generation is: 5.16ms

*/
 
