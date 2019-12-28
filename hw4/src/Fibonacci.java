import java.math.BigInteger;
import java.util.Arrays;

import java.util.stream.IntStream;

public class Fibonacci {

    /**
     * Iterative Version of Fibonacci numbers.
     */
    public static int fibIter(int n) {
        if (n <= 1)
            return n;

        int acc = 1;
        int prev = 0;

        while (n-- > 1) {
            int temp = acc;
            acc += prev;
            prev = temp;
        }
        return acc;
    }

    /**
     * Public API for tail-recursive version of Fibonacci function.
     */
    public static int fib(int n) {
        return fibTailRec(n, 1, 0);
    }

    /**
     * Tail-recursive version of Fibonacci numbers.
     */
    private static int fibTailRec(int n, int  acc, int prev) {
        // if (n <= 1)
        //     return acc;
        // if (acc < 1)
        //     return 0;
        
        // int temp = acc;
        // acc += prev;
        // prev = temp;

        // return fibTailRec(n-1, acc, prev);
        
        if (n == 0)
            return prev;
        else
            return fibTailRec(n - 1, prev + acc, acc);
    }
    
    public static void main(String... args) {
        System.out.println(
                Arrays.toString(IntStream.rangeClosed(0, 20).map(Fibonacci::fibIter).toArray())
        );

        System.out.println(
                Arrays.toString(IntStream.rangeClosed(0, 20).map(Fibonacci::fib).toArray())
        );

        fib(10);
        fib(50000);

    }
}
