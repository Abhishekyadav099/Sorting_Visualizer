package com.fatin.sortSpectra;

/**
 * Implements Merge Sort algorithm with visual feedback using a frame.
 */
public class MergeSort implements Runnable {

    private final Integer[] arr;  // Array to be sorted
    private final Frame frame;    // Frame for visualizing sorting process

    public MergeSort(Integer[] arr, Frame frame) {
        this.arr = arr;
        this.frame = frame;
    }

    @Override
    public void run() {
        mergeSort(arr, 0, arr.length - 1);  // Start merge sort
        drawSortedArray();  // Draw final sorted array
        Sort_Spectra.isSorting = false;  // Mark sorting as completed
    }

    // Recursively sorts array using Merge Sort algorithm
    private void mergeSort(Integer[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;  // Avoids overflow

            mergeSort(arr, left, mid);      // Sort first half
            mergeSort(arr, mid + 1, right); // Sort second half

            merge(arr, left, mid, right);   // Merge sorted halves
        }
    }

    // Merges two sorted subarrays into one
    private void merge(Integer[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        Integer[] leftArr = new Integer[n1];
        Integer[] rightArr = new Integer[n2];

        System.arraycopy(arr, left, leftArr, 0, n1);        // Copy to left subarray
        System.arraycopy(arr, mid + 1, rightArr, 0, n2);    // Copy to right subarray

        int i = 0, j = 0, k = left;

        // Merge subarrays while sorting
        while (i < n1 && j < n2) {
            arr[k] = (leftArr[i] <= rightArr[j]) ? leftArr[i++] : rightArr[j++];
            visualize(k++);
        }

        // Copy remaining elements from left subarray
        while (i < n1) {
            arr[k] = leftArr[i++];
            visualize(k++);
        }

        // Copy remaining elements from right subarray
        while (j < n2) {
            arr[k] = rightArr[j++];
            visualize(k++);
        }
    }

    // Visualize array during sorting
    private void visualize(int index) {
        frame.reDraw(arr, index, index + 1);
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
