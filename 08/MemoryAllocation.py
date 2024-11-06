class MemoryAllocation:
    def __init__(self, memory, process_sizes):
        self.memory = memory
        self.process_sizes = process_sizes

    # First Fit
    def first_fit(self):
        print("First Fit:")
        allocation = [-1] * len(self.process_sizes)
        for i in range(len(self.process_sizes)):
            for j in range(len(self.memory)):
                if self.memory[j] >= self.process_sizes[i]:
                    allocation[i] = j
                    self.memory[j] -= self.process_sizes[i]  # Reduce memory available
                    break

        # Display results
        for i in range(len(allocation)):
            if allocation[i] != -1:
                print(f"Process {i + 1} allocated to block {allocation[i] + 1}")
            else:
                print(f"Process {i + 1} could not be allocated.")

    # Best Fit
    def best_fit(self):
        print("Best Fit:")
        allocation = [-1] * len(self.process_sizes)
        for i in range(len(self.process_sizes)):
            best_block = -1
            min_diff = float('inf')
            for j in range(len(self.memory)):
                if self.memory[j] >= self.process_sizes[i] and (self.memory[j] - self.process_sizes[i]) < min_diff:
                    min_diff = self.memory[j] - self.process_sizes[i]
                    best_block = j

            if best_block != -1:
                allocation[i] = best_block
                self.memory[best_block] -= self.process_sizes[i]  # Reduce memory available

        # Display results
        for i in range(len(allocation)):
            if allocation[i] != -1:
                print(f"Process {i + 1} allocated to block {allocation[i] + 1}")
            else:
                print(f"Process {i + 1} could not be allocated.")

    # Worst Fit
    def worst_fit(self):
        print("Worst Fit:")
        allocation = [-1] * len(self.process_sizes)
        for i in range(len(self.process_sizes)):
            worst_block = -1
            max_diff = float('-inf')
            for j in range(len(self.memory)):
                if self.memory[j] >= self.process_sizes[i] and (self.memory[j] - self.process_sizes[i]) > max_diff:
                    max_diff = self.memory[j] - self.process_sizes[i]
                    worst_block = j

            if worst_block != -1:
                allocation[i] = worst_block
                self.memory[worst_block] -= self.process_sizes[i]  # Reduce memory available

        # Display results
        for i in range(len(allocation)):
            if allocation[i] != -1:
                print(f"Process {i + 1} allocated to block {allocation[i] + 1}")
            else:
                print(f"Process {i + 1} could not be allocated.")

    # Next Fit
    def next_fit(self):
        print("Next Fit:")
        allocation = [-1] * len(self.process_sizes)
        last_allocated = 0
        for i in range(len(self.process_sizes)):
            j = last_allocated
            allocated = False
            while True:
                if self.memory[j] >= self.process_sizes[i]:
                    allocation[i] = j
                    self.memory[j] -= self.process_sizes[i]  # Reduce memory available
                    last_allocated = j  # Update last allocated block
                    allocated = True
                    break
                j = (j + 1) % len(self.memory)  # Loop back to the first block if needed
                if j == last_allocated:  # If we looped back to the start, we stop
                    break

            if not allocated:
                print(f"Process {i + 1} could not be allocated.")

        # Display results
        for i in range(len(allocation)):
            if allocation[i] != -1:
                print(f"Process {i + 1} allocated to block {allocation[i] + 1}")


# Driver Code
if __name__ == "__main__":
    memory_blocks = [100, 500, 200, 300, 600]  # Example memory blocks
    process_sizes = [212, 417, 112, 426]       # Example process sizes

    ma = MemoryAllocation(memory_blocks.copy(), process_sizes)

    # Execute each strategy and display results
    ma.first_fit()
    print()  # Separate the outputs for readability
    ma.best_fit()
    print()
    ma.worst_fit()
    print()
    ma.next_fit()
