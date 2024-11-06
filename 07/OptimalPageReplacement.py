def optimal(pages, num_frames):
    frames = [-1] * num_frames  # Initialize frames to -1 (empty)
    page_faults = 0

    def contains(frames, page):
        return page in frames

    def get_empty_slot(frames):
        try:
            return frames.index(-1)
        except ValueError:
            return -1

    def get_optimal_page_to_replace(frames, pages, current_index):
        farthest = current_index
        page_to_replace = -1
        for i in range(len(frames)):
            for j in range(current_index + 1, len(pages)):
                if frames[i] == pages[j]:
                    break
            if j == len(pages):
                return i  # The page is not needed anymore
            if j > farthest:
                farthest = j
                page_to_replace = i
        return page_to_replace

    for i in range(len(pages)):
        page = pages[i]
        if not contains(frames, page):
            page_faults += 1
            empty_slot = get_empty_slot(frames)
            if empty_slot != -1:
                frames[empty_slot] = page
            else:
                replace_index = get_optimal_page_to_replace(frames, pages, i)
                frames[replace_index] = page

        # Print current frame contents
        print("Frames:", frames)

    print("Total Page Faults:", page_faults)

# Test the function
pages = [7, 0, 1, 2, 0, 3, 0, 4, 2, 3]
num_frames = 3
optimal(pages, num_frames)
