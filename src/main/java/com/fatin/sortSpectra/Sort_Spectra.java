package com.fatin.sortSpectra;

import java.util.*;

public class Sort_Spectra {

    // Sorting thread and control variables
    private static Thread sortingThread;
    public static Frame frame;
    public static Integer[] arr;
    public static boolean isSorting = false;
    public static int cnt = 15;    // Array size
    public static int wait = 30;   // Sorting speed
    public static int barWidth;    // Width of each bar in the visualization

    public static void main(String[] args) {
        frame = new Frame();
        createArr();  // Initialize the array
    }

    // Creates a shuffled array to be sorted
    public static void createArr() {
        if (isSorting) return;  // Prevent array modification during sorting

        arr = new Integer[cnt];
        barWidth = Math.max(1, 500 / cnt);  // Calculate bar width to fit the panel

        // Populate and shuffle the array
        List<Integer> shuff = new ArrayList<>(cnt);
        for (int i = 1; i <= cnt; i++) {
            shuff.add(i);
        }
        Collections.shuffle(shuff);
        shuff.toArray(arr);

        frame.Draw(arr);  // Draw the initial shuffled array
    }

    // Starts the sorting process based on the selected algorithm
    public static void startSort(String type) {
        if (isSorting) return;  // Prevent starting a new sort if one is already in progress

        createArr();  // Reset and shuffle the array before starting a new sort
        isSorting = true;

        sortingThread = new Thread(() -> {
            switch (type) {
                case "Bubble Sort" -> new BubbleSort(arr, frame).run();
                case "Selection Sort" -> new SelectionSort(arr, frame).run();
                case "Insertion Sort" -> new InsertionSort(arr, frame).run();
                case "Merge Sort" -> new MergeSort(arr, frame).run();
                case "Quick Sort" -> new QuickSort(arr, frame).run();
                case "Heap Sort" -> new HeapSort(arr, frame).run();
                default -> isSorting = false;  // Handle invalid sort type
            }
        });

        sortingThread.start();
    }

    // Stops the current sorting process
    public static void stopSort() {
        if (sortingThread != null && isSorting) {
            sortingThread.interrupt();  // Interrupt the sorting thread
            isSorting = false;          // Update sorting status
        }
    }
}
