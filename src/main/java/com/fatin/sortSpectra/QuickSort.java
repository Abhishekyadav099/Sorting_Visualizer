package com.fatin.sortSpectra;

/**
 * Implements Quick Sort algorithm with visual feedback using a frame.
 */
public class QuickSort implements Runnable {

    private final Integer[] arr;  // Array to be sorted
    private final Frame frame;    // Frame for visualizing sorting process

    public QuickSort(Integer[] arr, Frame frame) {
        this.arr = arr;
        this.frame = frame;
    }

    @Override
    public void run() {
        quickSort(arr, 0, arr.length - 1);  // Start Quick Sort
        drawSortedArray();  // Draw final sorted array
        Sort_Spectra.isSorting = false;  // Mark sorting as completed
    }

    // Recursively sorts array using Quick Sort algorithm
    private void quickSort(Integer[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high); // Partition the array

            quickSort(arr, low, pivotIndex - 1);    // Sort left part
            quickSort(arr, pivotIndex + 1, high);   // Sort right part
        }
    }

    // Partitions the array and returns the index of the pivot
    private int partition(Integer[] arr, int low, int high) {
        int pivot = arr[high];  // Choose last element as pivot
        int i = low - 1;        // Index of smaller element

        // Traverse through elements and rearrange based on pivot
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                swap(arr, ++i, j);  // Swap if element is smaller than pivot
                visualize(i, j);    // Visualize the swap
            }
        }
        swap(arr, i + 1, high);  // Place pivot in correct position
        visualize(i + 1, high);  // Visualize the pivot swap
        return i + 1;            // Return pivot index
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
        pause(Sort_Spectra.wait);  // Pause for visualization delay
    }

    // Draw the final sorted array
    private void drawSortedArray() {
        for (int i = 0; i < arr.length; i++) {
            frame.finalDraw(arr, i);
            pause(50);  // Pause for final visualization
        }
    }

    // Pause execution for a specified duration
    private void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore interrupted state
            e.printStackTrace();
        }
    }
}
