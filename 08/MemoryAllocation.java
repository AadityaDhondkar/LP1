import java.util.*;

class MemoryAllocation {
    private int[] memory;
    private int[] processSizes;

    public MemoryAllocation(int[] memory, int[] processSizes) {
        this.memory = memory;
        this.processSizes = processSizes;
    }

    // First Fit
    public void firstFit() {
        System.out.println("First Fit:");
        int[] allocation = new int[processSizes.length];
        Arrays.fill(allocation, -1); // Initialize allocation array to -1 (indicating no allocation)

        for (int i = 0; i < processSizes.length; i++) {
            for (int j = 0; j < memory.length; j++) {
                if (memory[j] >= processSizes[i]) {
                    allocation[i] = j;
                    memory[j] -= processSizes[i];  // Reduce memory available after allocation
                    break;
                }
            }
        }

        // Display results
        for (int i = 0; i < allocation.length; i++) {
            if (allocation[i] != -1) {
                System.out.println("Process " + (i + 1) + " allocated to block " + (allocation[i] + 1));
            } else {
                System.out.println("Process " + (i + 1) + " could not be allocated.");
            }
        }
    }

    // Best Fit
    public void bestFit() {
        System.out.println("Best Fit:");
        int[] allocation = new int[processSizes.length];
        Arrays.fill(allocation, -1); // Initialize allocation array to -1 (indicating no allocation)

        for (int i = 0; i < processSizes.length; i++) {
            int bestBlock = -1;
            int minDiff = Integer.MAX_VALUE;
            for (int j = 0; j < memory.length; j++) {
                if (memory[j] >= processSizes[i] && (memory[j] - processSizes[i]) < minDiff) {
                    minDiff = memory[j] - processSizes[i];
                    bestBlock = j;
                }
            }

            if (bestBlock != -1) {
                allocation[i] = bestBlock;
                memory[bestBlock] -= processSizes[i]; // Reduce memory available after allocation
            }
        }

        // Display results
        for (int i = 0; i < allocation.length; i++) {
            if (allocation[i] != -1) {
                System.out.println("Process " + (i + 1) + " allocated to block " + (allocation[i] + 1));
            } else {
                System.out.println("Process " + (i + 1) + " could not be allocated.");
            }
        }
    }

    // Worst Fit
    public void worstFit() {
        System.out.println("Worst Fit:");
        int[] allocation = new int[processSizes.length];
        Arrays.fill(allocation, -1); // Initialize allocation array to -1 (indicating no allocation)

        for (int i = 0; i < processSizes.length; i++) {
            int worstBlock = -1;
            int maxDiff = Integer.MIN_VALUE;
            for (int j = 0; j < memory.length; j++) {
                if (memory[j] >= processSizes[i] && (memory[j] - processSizes[i]) > maxDiff) {
                    maxDiff = memory[j] - processSizes[i];
                    worstBlock = j;
                }
            }

            if (worstBlock != -1) {
                allocation[i] = worstBlock;
                memory[worstBlock] -= processSizes[i]; // Reduce memory available after allocation
            }
        }

        // Display results
        for (int i = 0; i < allocation.length; i++) {
            if (allocation[i] != -1) {
                System.out.println("Process " + (i + 1) + " allocated to block " + (allocation[i] + 1));
            } else {
                System.out.println("Process " + (i + 1) + " could not be allocated.");
            }
        }
    }

    // Next Fit
    public void nextFit() {
        System.out.println("Next Fit:");
        int[] allocation = new int[processSizes.length];
        Arrays.fill(allocation, -1); // Initialize allocation array to -1 (indicating no allocation)

        int lastAllocated = 0;

        for (int i = 0; i < processSizes.length; i++) {
            int j = lastAllocated;
            boolean allocated = false;

            do {
                if (memory[j] >= processSizes[i]) {
                    allocation[i] = j;
                    memory[j] -= processSizes[i]; // Reduce memory available after allocation
                    lastAllocated = j; // Update last allocated block
                    allocated = true;
                    break;
                }
                j = (j + 1) % memory.length;
            } while (j != lastAllocated);  // Keep trying until we loop back to last allocated block

            if (!allocated) {
                System.out.println("Process " + (i + 1) + " could not be allocated.");
            }
        }

        // Display results
        for (int i = 0; i < allocation.length; i++) {
            if (allocation[i] != -1) {
                System.out.println("Process " + (i + 1) + " allocated to block " + (allocation[i] + 1));
            }
        }
    }

    public static void main(String[] args) {
        int[] memoryBlocks = {100, 500, 200, 300, 600};  // Example memory blocks
        int[] processSizes = {212, 417, 112, 426};       // Example process sizes

        MemoryAllocation ma = new MemoryAllocation(memoryBlocks.clone(), processSizes);
        ma.firstFit();
        System.out.println();
        ma.bestFit();
        System.out.println();
        ma.worstFit();
        System.out.println();
        ma.nextFit();
    }
}
