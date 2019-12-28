import java.util.Arrays;
import java.util.Random;

public class Box {

    /*
     * Puzzle Boxes
     */
    private int[] boxes;

    /*
     * Number of times each box is visited [0 | 1 | 2]
     * Whenever a box is visited, its count is decremented by 1.
     */
    private int[] visited;

    /**
     * Constructor for the puzzle
     *
     * @param size the size of the puzzle in terms of the number of boxes
     */
    public Box(int size) {
        getBuckets(size);
        visited = new int[size];
        Arrays.fill(visited, 2);
    }

    /**
     * Constructor for the puzzle for testing purpose
     *
     * @param buckets array to be used as puzzle boxes
     */
    public Box(int[] boxes) {
        checkBox(boxes);
        this.boxes = boxes;
        boxes[0] = 0;
        visited = new int[boxes.length];
        Arrays.fill(visited, 2);
    }

    private void checkBox(int[] boxes) {
        if (!Arrays.stream(boxes).allMatch(i -> i >= 0 && i < boxes.length))
            throw new IllegalStateException("box number invalid");
    }

    private void getBuckets(int size) {
        boxes = new int[size];
        Random gen = new Random(System.currentTimeMillis());

        for (int i = 0; i < size; i++) {
            int value = 0;
            while ((value = Math.abs(gen.nextInt())) % size == 0)
                ;
            boxes[i] = value % size;
        }
        boxes[0] = 0; // Final destination is always set to 0.
    }

    /**
     * Core of the algorithm.
     *
     * @param current current position
     * @param delta distance to move
     * @return whether it reaches destination or not
     */
    public boolean move(int current, int delta) {
        // Complete here

        if (delta == 0) { return true; }
        if (visited[current] <= 0) { return false; }

        visited[current]--;

        if (current - delta < 0 && (current + delta) < boxes.length) {
            return move(current + delta, boxes[current + delta]); 
        }

        else if (current - delta >= 0 && (current + delta) >= boxes.length) { 
            return move(current - delta, boxes[current - delta]); 
        }

        else if (current - delta < 0 && (current + delta) >= boxes.length) {
            return false;
        }

        else {
            return move(current - delta, boxes[current - delta]) || move(current + delta, boxes[current + delta]);
        }
    }

    public boolean start() {
        return move(boxes.length - 1, boxes[boxes.length - 1]);
    }

    public int[] getVisitingStatus() {
        return visited;
    }

    public int[] getBoxes() {
        return boxes;
    }

    public static void main(String... args) {
        /* actual run */
        Box puzzle = new Box(10);
        System.out.println(Arrays.toString(puzzle.getBoxes()));
        System.out.println(puzzle.start());
        System.out.println(Arrays.toString(puzzle.getVisitingStatus()));

        /* for testing */
        int[] xs = {0, 3, 2, 1, 3, 2, 1};
        Box puzzle1 = new Box(xs);
        System.out.println(puzzle1.start());
        System.out.println(Arrays.toString(puzzle1.getVisitingStatus())); 
    }
}

