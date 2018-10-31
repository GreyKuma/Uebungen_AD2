/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct 22 12:14:37 CEST 2018
 */

package uebung06.as.aufgabe01;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * InPlaceQuickSort from "Data Structures and Algorithms" implemented to use a
 * comparator instead. This allows the usage of multiple keys.
 *
 * @author tbeeler
 */
public class QuickSort {

    /**
     * @param <T>      Type of elements to be sorted.
     * @param sequence The sequence to be sorted.
     * @param comp     The comperator to be used.
     * @param a        The leftbound of the part that shall be sorted.
     * @param b        The rightbound of the part that shall be sorted.
     */
    public <T> void inPlaceQuickSort(T[] sequence, Comparator<T> comp, int a, int b) {
        // TODO Implement here...
        if(a >= b){return;}
        int orgA = a;
        int orgB = b;
        T pivot = sequence[b];
        b--;
        while(a <= b){
            while((a <= b) && (comp.compare(sequence[a], pivot) <= 0)) {
                a++;
            }
            while((b >= a) && (comp.compare(sequence[b], pivot) >= 0)){
                b--;
            }
            if(a < b){
                T temp = sequence[a];
                sequence[a] = sequence[b];
                sequence[b] = temp;
            }
        }
        T temp = sequence[a];
        sequence[a] = sequence[orgB];
        sequence[orgB] = temp;
        inPlaceQuickSort(sequence, comp, orgA, a-1);
        inPlaceQuickSort(sequence, comp, a+1, orgB);
    }

    enum SequenceType {RANDOM, EQUAL, SORTED}

    public static void main(String[] args) {
        Comparator<Point> comp = new PointComparator();
        QuickSort qs = new QuickSort();
        Random random = new Random(0);
        int nSequence = 50;
        if (args.length > 0)
            nSequence = Integer.parseInt(args[0]);
        final Point s1[] = new Point[nSequence];
        for (int i = 0; i < s1.length; i++) {
            s1[i] = new Point((int) (random.nextDouble() * 100),
                    (int) (random.nextDouble() * 100));
        }
        printArray(s1);
        System.out.print("Quick sort...");
        long then = System.currentTimeMillis();
        qs.inPlaceQuickSort(s1, comp, 0, nSequence - 1);
        long now = System.currentTimeMillis();
        long d1 = now - then;
        System.out.println("done.");
        printArray(s1);
        System.out.println("Time quick sort [ms]: " + d1);

        System.out.println("\nRuntime:");
        final int INITIAL_SIZE = 64;
        final int NR_OF_DOUBLING = 4;
        for (SequenceType seqType : SequenceType.values()) {
            System.out.println("Sequence-Type: " + seqType);
            int arraySize = INITIAL_SIZE;
            long lastTime = Long.MAX_VALUE;
            for (int j = 0; j <= NR_OF_DOUBLING; j++) {
                final int MEASUREMENTS = 200;
                long minimalTime = Long.MAX_VALUE;
                Point[] s2 = new Point[arraySize];
                for (int m = 0; m < MEASUREMENTS; m++) {
                    for (int i = 0; i < s2.length; i++) {
                        switch (seqType) {
                            case RANDOM:
                                s2[i] = new Point((int) (Math.random() * 100),
                                        (int) (Math.random() * 100));
                                break;
                            case EQUAL:
                                s2[i] = new Point(1, 1);
                                break;
                            case SORTED:
                                s2[i] = new Point(i, i);
                                break;
                        }
                    }
                    long startTime = System.nanoTime();
                    qs.inPlaceQuickSort(s2, comp, 0, arraySize - 1);
                    long endTime = System.nanoTime();
                    long time = endTime - startTime;
                    if (time < minimalTime) {
                        minimalTime = time;
                    }
                    Point[] test = s2.clone();
                    Arrays.sort(test, comp);
                    if (!Arrays.equals(s2, test)) {
                        System.err.println("ERROR:");
                        System.err.println(Arrays.toString(s2));
                        System.err.println(Arrays.toString(test));
                        System.exit(1);
                    }
                }

                System.out.format(
                        "Array-Size: %,6d   Time: %,7.0f us   Ratio to last: %2.1f\n",
                        arraySize, (double) minimalTime / (long) 1e3,
                        (double) minimalTime / lastTime);
                lastTime = minimalTime;
                arraySize = arraySize * 2;
            }
        }
    }

    private static void printArray(final Point[] array) {
        if (array.length > 300) {
            System.out.println("Too many elements, not printing to stdout.");
        } else {
            for (Point point : array) {
                System.out.print("(" + point.x + "/" + point.y + "), ");
            }
            System.out.println();
        }
    }

}


class PointComparator implements Comparator<Point> {

    /**
     * Total order relation for points:
     * p1 > p2 | p1.x > p2.x
     * p1 > p2 | p1.x = p2.x && p1.y > p2.y
     * p1 = p2 | p1.x = p2.x && p1.y = p2.y
     * else p1 < p2
     *
     * @return p1 > p2  : +1,
     * p1 == p2 :  0,
     * p1 < p2  : -1
     * @author tbeeler
     */
    public int compare(Point p1, Point p2) {

        // TODO Implement here...
        if(p1.x > p2.x){return 1;}
        if(p1.x == p2.x){
            if(p1.y > p2.y){return 1;}
            if(p1.y == p2.y){return 0;}
        }
        return -1;
    }

}

/* Session-Log:

$ java -Xint -Xms10m -Xmx10m uebung06.as.aufgabe01.QuickSort

(73/24), (63/55), (59/33), (38/98), (87/94), (27/12), (14/2), (54/96), (10/62), (41/77), (99/48), (74/73), (81/83), (52/89), (13/8), (97/72), (71/14), (46/0), (7/34), (33/85), (97/86), (61/17), (21/85), (0/69), (77/71), (21/78), (94/1), (39/85), (78/99), (88/17), (96/72), (67/80), (44/46), (85/50), (99/96), (35/4), (7/2), (48/97), (98/76), (50/25), (30/84), (5/1), (35/8), (85/0), (30/53), (91/27), (87/60), (90/4), (64/49), (50/52), 
Quick sort...done.
(0/69), (5/1), (7/2), (7/34), (10/62), (13/8), (14/2), (21/78), (21/85), (27/12), (30/53), (30/84), (33/85), (35/4), (35/8), (38/98), (39/85), (41/77), (44/46), (46/0), (48/97), (50/25), (50/52), (52/89), (54/96), (59/33), (61/17), (63/55), (64/49), (67/80), (71/14), (73/24), (74/73), (77/71), (78/99), (81/83), (85/0), (85/50), (87/60), (87/94), (88/17), (90/4), (91/27), (94/1), (96/72), (97/72), (97/86), (98/76), (99/48), (99/96), 
Time quick sort [ms]: 0

Runtime:
Sequence-Type: RANDOM
Array-Size:     64   Time:     109 us   Ratio to last: 0.0
Array-Size:    128   Time:     256 us   Ratio to last: 2.3
Array-Size:    256   Time:     569 us   Ratio to last: 2.2
Array-Size:    512   Time:   1,294 us   Ratio to last: 2.3
Array-Size:  1,024   Time:   2,853 us   Ratio to last: 2.2
Sequence-Type: EQUAL
Array-Size:     64   Time:      19 us   Ratio to last: 0.0
Array-Size:    128   Time:      37 us   Ratio to last: 2.0
Array-Size:    256   Time:      74 us   Ratio to last: 2.0
Array-Size:    512   Time:     148 us   Ratio to last: 2.0
Array-Size:  1,024   Time:     296 us   Ratio to last: 2.0
Sequence-Type: SORTED
Array-Size:     64   Time:     307 us   Ratio to last: 0.0
Array-Size:    128   Time:   1,180 us   Ratio to last: 3.8
Array-Size:    256   Time:   4,638 us   Ratio to last: 3.9
Array-Size:    512   Time:  18,467 us   Ratio to last: 4.0
Array-Size:  1,024   Time:  74,368 us   Ratio to last: 4.0

*/

