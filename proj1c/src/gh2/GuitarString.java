package gh2;

import deque.ArrayDeque61B;
import deque.Deque61B;


//Note: This file will not compile until you complete the Deque61B implementations
public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque61B<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) (Math.round(SR / frequency));
        buffer = new ArrayDeque61B<>();
        for (int i = 0; i < capacity; i += 1) {
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        int size = buffer.size();
        ;
        for (int i = 0; i < size; i += 1) {
            buffer.removeLast();
        }

        for (int j = 0; j < size; j += 1) {
            buffer.addLast(Math.random() - 0.5);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double nextItem = buffer.get(1);
        double firstItem = buffer.removeFirst();
        double newItem = 1.0 / 2.0 * DECAY * (firstItem + nextItem);
        buffer.addLast(newItem);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}
