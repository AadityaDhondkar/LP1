import java.util.*;

public class OptimalPageReplacement {
    public static void optimal(int[] pages, int numFrames) {
        int pageFaults = 0;
        int[] frames = new int[numFrames];
        Arrays.fill(frames, -1);  // Initialize frames to -1 (empty)

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            if (!contains(frames, page)) {
                pageFaults++;
                if (getEmptySlot(frames) != -1) {
                    frames[getEmptySlot(frames)] = page;  // Place page in empty slot
                } else {
                    int indexToReplace = getOptimalPageToReplace(frames, pages, i);
                    frames[indexToReplace] = page;  // Replace the optimal page
                }
            }

            // Print current frame contents
            System.out.print("Frames: ");
            for (int j : frames) {
                System.out.print(j + " ");
            }
            System.out.println();
        }

        System.out.println("Total Page Faults: " + pageFaults);
    }

    private static boolean contains(int[] frames, int page) {
        for (int frame : frames) {
            if (frame == page) {
                return true;
            }
        }
        return false;
    }

    private static int getEmptySlot(int[] frames) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    private static int getOptimalPageToReplace(int[] frames, int[] pages, int currentIndex) {
        int farthest = currentIndex;
        int pageToReplace = -1;
        for (int i = 0; i < frames.length; i++) {
            int j;
            for (j = currentIndex + 1; j < pages.length; j++) {
                if (frames[i] == pages[j]) {
                    break;
                }
            }
            if (j == pages.length) {
                return i;  // The page is not needed anymore
            }
            if (j > farthest) {
                farthest = j;
                pageToReplace = i;
            }
        }
        return pageToReplace;
    }

    public static void main(String[] args) {
        int[] pages = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3};
        int numFrames = 3;
        optimal(pages, numFrames);
    }
}
