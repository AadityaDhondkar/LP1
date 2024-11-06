import java.util.*;

public class LRUPageReplacement {
    public static void lru(int[] pages, int numFrames) {
        LinkedHashSet<Integer> frames = new LinkedHashSet<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!frames.contains(page)) {
                if (frames.size() == numFrames) {
                    // Remove the least recently used (first element)
                    Iterator<Integer> iterator = frames.iterator();
                    iterator.next();
                    iterator.remove();
                }
                frames.add(page);
                pageFaults++;
            } else {
                // Move the accessed page to the end (most recently used)
                frames.remove(page);
                frames.add(page);
            }

            // Print current frame contents
            System.out.println("Frames: " + frames);
        }

        System.out.println("Total Page Faults: " + pageFaults);
    }

    public static void main(String[] args) {
        int[] pages = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3};
        int numFrames = 3;
        lru(pages, numFrames);
    }
}
