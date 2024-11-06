from collections import deque

def lru(pages, num_frames):
    frames = deque(maxlen=num_frames)
    page_faults = 0

    for page in pages:
        if page not in frames:
            if len(frames) == num_frames:
                frames.popleft()  # Remove least recently used page
            frames.append(page)
            page_faults += 1
        else:
            # Move the accessed page to the end (most recently used)
            frames.remove(page)
            frames.append(page)

        # Print current frame contents
        print("Frames:", list(frames))

    print("Total Page Faults:", page_faults)

# Test the function
pages = [7, 0, 1, 2, 0, 3, 0, 4, 2, 3]
num_frames = 3
lru(pages, num_frames)
