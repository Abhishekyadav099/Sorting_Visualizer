package com.fatin.sortSpectra;

/**
 * Implements Heap Sort algorithm with visual feedback using a frame.
 */
public class HeapSort implements Runnable {

    private final Integer[] arr;  // Array to be sorted
    private final Frame frame;    // Frame for visualizing sorting process

    public HeapSort(Integer[] arr, Frame frame) {
        this.arr = arr;
        this.frame = frame;
    }

    @Override
    public void run() {
        heapSort(arr);         // Start Heap Sort
        drawSortedArray();     // Draw final sorted array
        Sort_Spectra.isSorting = false; // Mark sorting as completed
    }

    // Main method to perform Heap Sort
    private void heapSort(Integer[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extract elements from heap one by one
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);    // Move current root to end
            visualize(0, i);    // Visualize the swap
            heapify(arr, i, 0); // Call max heapify on the reduced heap
        }
    }

    // Heapifies a subtree rooted with node i, which is an index in arr[].
    // n is size of heap
    private void heapify(Integer[] arr, int n, int i) {
        int largest = i;       // Initialize largest as root
        int left = 2 * i + 1;  // Left child index
        int right = 2 * i + 2; // Right child index

        // If left child is larger than root
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            swap(arr, i, largest); // Swap and continue heapifying
            visualize(i, largest); // Visualize the swap
            heapify(arr, n, largest); // Recursively heapify the affected sub-tree
        }
    }

    // Swaps two elements in the array
    private void swap(Integer[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Visualize array during sorting
    private void visualize(int i, int j) {
        frame.reDraw(arr, i, j);
        pause(Sort_Spectra.wait); // Pause for visualization delay
    }

    // Draw the final sorted array
    private void drawSortedArray() {
        for (int i = 0; i < arr.length; i++) {
            frame.finalDraw(arr, i);
            pause(50); // Pause for final visualization
        }
    }

    // Pause execution for a specified duration
    private void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted state
            e.printStackTrace();
        }
    }
}
