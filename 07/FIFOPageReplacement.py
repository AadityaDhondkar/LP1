def fifo(pages, num_frames):
    frames = [-1] * num_frames  # Initialize frames to -1 (empty)
    page_faults = 0
    pointer = 0  # Pointer to track the oldest page

    for page in pages:
        if page not in frames:
            # Page fault occurs, replace the oldest page
            frames[pointer] = page
            page_faults += 1
            pointer = (pointer + 1) % num_frames  # Move pointer to next frame
        
        # Print current frame contents
        print("Frames:", frames)

    print("Total Page Faults:", page_faults)

# Test the function
pages = [7, 0, 1, 2, 0, 3, 0, 4, 2, 3]
num_frames = 3
fifo(pages, num_frames)
