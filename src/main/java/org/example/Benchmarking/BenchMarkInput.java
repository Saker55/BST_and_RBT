package org.example.Benchmarking;
import java.util.Random;

public class BenchMarkInput  {
    private Random generator = new Random();
    private int[] array = new int[100000];
    private int[] lockup = new int[100000];
    private int[] toBeDeleted = new int[20000];

    public int[] GenerateArray(int x){ // x is the persentage of the distripution
        array[0]=generator.nextInt(10);

        for (int i = 1; i < 100000; i++) {
            array[i] =generator.nextInt(9)+(10 * i);
        }

        for (int i = 1; i < 1000 * x; i++) {
            int a =generator.nextInt(100000);
            int b =generator.nextInt(100000);
            swap(a,b);
        }

        return array;

    }

    public int[] GenerateRandomArray(){

        for (int i = 0; i < 100000; i++) {
            array[i] =generator.nextInt(100000);
        }

        return array;
    }

     public int[] GenerateLockup(boolean isRandom){

        if (isRandom){
            for (int i = 0; i < 50000; i++) {
                int temp = generator.nextInt(100000);
                lockup[i] = array[temp];
            }
            int i = 50000;
            while (i < 100000) {
                int temp = generator.nextInt(100000);
                boolean present = false;
                for (int j = 0; j < 100000; j++) {
                    if (array[j] == temp){
                        present = true;
                        break;
                    }
                }

                if (present){
                    continue;
                }

                for (int j = 0; j < 100; j++) {
                    lockup[i] = array[temp];
                    i++;
                }
            }

        }
        else {
            for (int i = 0; i < 50000; i++) {
                int temp = generator.nextInt(100000);
                if (array[temp] % 2 == 0){
                    lockup[i] = array[temp];
                    lockup[i+50000] = array[temp]+1;
                }
                else {
                    lockup[i] = array[temp];
                    lockup[i+50000] = array[temp]-1;
                }
            }
        }

        return lockup;
    }

    private void swap(int a,int b){
        int temp = array[a];
        array[a]=array[b];
        array[b]=temp;
    }

    public int[] GenerateToBeDeleted(){
        toBeDeleted[0] = array[generator.nextInt(5)];
        for (int i = 1; i < 20000; i++) {
            toBeDeleted[i] = array[(generator.nextInt(5))+(5*i)];
        }
        return toBeDeleted;
    }
}
