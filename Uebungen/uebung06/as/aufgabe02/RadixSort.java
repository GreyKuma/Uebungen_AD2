/*
 * HSR - Uebungen 'Algorithmen & Datenstrukturen 2'
 * Version: Mon Oct 22 12:16:24 CEST 2018
 */

package uebung06.as.aufgabe02;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A Radix-Sort which uses internally a Bucket-Sort to sort a list of arrays of
 * strings.
 *
 * @author mbuehlma
 */
public class RadixSort {

    // buckets used for bucket sort
    private final LinkedList<String>[] buckets;

    @SuppressWarnings("unchecked")
    RadixSort() {
        // create LinkedList for buckets
        buckets = (LinkedList<String>[]) new LinkedList<?>[1 + ('z' - 'a' + 1)];

        // TODO Implement here...

        for(int i = 0; i < buckets.length; i++){
            buckets[i] = new LinkedList<>();
        }
    }

    public void radixSort(String[] data) {

        // TODO Implement here...
        int maxLength = 0;
        for(String string: data){
            maxLength = maxLength<string.length()?string.length():maxLength;
        }

        for(int i = maxLength-1; i >= 0; i--){
            bucketSort(data, i);
            int index = 0;
            for(LinkedList bucket: buckets){
                while(!bucket.isEmpty()){
                    data[index] = bucket.getLast();
                    bucket.removeLast();
                    index++;
                }
            }
        }
    }

    protected void bucketSort(String[] data, int index) {

        // TODO Implement here...
        for (String string : data) {
            if(string.length() > index){
                buckets[string.charAt(index)].add(string);
            }else{
                buckets[0].add(string);
            }
        }
    }

    public static void main(String[] args) {

        // unsorted data
        final String[] data = new String[]{"bruno", "brach", "auto", "auto",
                "autonom", "clown", "bismarck", "autark", "authentisch",
                "authentische", "autobahn", "bleibe", "clan"};

        new RadixSort().radixSort(data);

        // verification array, for test purpose only
        final String[] verification;
        // sort the verification array
        verification = data.clone();
        Arrays.sort(verification);

        // print and verify output
        AtomicInteger i = new AtomicInteger(0);
        Arrays.stream(verification).forEachOrdered(verificationStr -> {
            if (verificationStr.equals(data[i.get()])) {
                System.out.println(data[i.get()]);
            } else {
                System.err.println("test failed: " + data[i.get()]);
            }
            i.incrementAndGet();
        });

    }

}

/* Session-Log:

autark
authentisch
authentische
auto
auto
autobahn
autonom
bismarck
bleibe
brach
bruno
clan
clown

*/

 
 
 
