import java.util.*;

public class FIFOPageReplacement {
    public static void fifo(int[] pages, int numFrames) {
        int[] frames = new int[numFrames];
        Arrays.fill(frames, -1);  // Initialize frames to -1 (empty)
        int pageFaults = 0;
        int pointer = 0;  // Pointer to keep track of the oldest page

        for (int page : pages) {
            boolean pageFound = false;

            // Check if the page is already in the frames
            for (int i = 0; i < numFrames; i++) {
                if (frames[i] == page) {
                    pageFound = true;
                    break;
                }
            }

            if (!pageFound) {
                // Replace the oldest page
                frames[pointer] = page;
                pageFaults++;
                pointer = (pointer + 1) % numFrames;  // Move pointer to next frame
            }

            // Print current frame contents
            System.out.print("Frames: ");
            for (int i : frames) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        System.out.println("Total Page Faults: " + pageFaults);
    }

    public static void main(String[] args) {
        int[] pages = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3};
        int numFrames = 3;
        fifo(pages, numFrames);
    }
}
