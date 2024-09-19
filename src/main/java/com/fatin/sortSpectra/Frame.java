package com.fatin.sortSpectra;

import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {

    // UI Constants
    private static final Color PRIMARY_COLOR = new Color(0x1B1A55);
    private static final Color SECONDARY_COLOR = new Color(0xFAF0E6);
    private static final Color BUTTON_COLOR = new Color(0xDC5F00);
    private static final Font FONT = new Font("Arial", Font.BOLD, 20);

    // UI Components
    private JPanel graphPanel = new JPanel(new GridBagLayout());
    private JPanel sidebar = new JPanel(new GridLayout(7, 1, 0, 10));
    private JComboBox<String> sortDropdown = new JComboBox<>(new String[]{"Bubble Sort", "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort","Heap Sort"});
    private JSlider speedSlider = createSlider(1, 1000, 30, "Speed: ", "ms");
    private JSlider arrSizeSlider = createSlider(5, 100, 15, "Array Size: ", "");
    private JButton startButton = createButton("Let's Go!", BUTTON_COLOR);
    private JButton cancelButton = createButton("Cancel", Color.RED);  // Cancel button to stop sorting

    public Frame() {
        // Set up the frame
        setTitle("Sort Spectra");
        setIconImage(new ImageIcon(getClass().getResource("resources/logo.png")).getImage());
        setLayout(new BorderLayout());
        setSize(1285, 675);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components to the frame
        setupSidebar();
        add(sidebar, BorderLayout.WEST);
        add(graphPanel, BorderLayout.CENTER);
        graphPanel.setBackground(new Color(0x070F2B));

        // Display the welcome popup
        showWelcomePopup();
        setVisible(true);
    }

    // Setup the sidebar with sliders, dropdown, and buttons
    private void setupSidebar() {
        startButton.addActionListener(e -> Sort_Spectra.startSort((String) sortDropdown.getSelectedItem()));
        cancelButton.addActionListener(e -> Sort_Spectra.stopSort());  // Stop sorting when cancel button is clicked

        sidebar.setBackground(PRIMARY_COLOR);
        sidebar.add(speedSlider);
        sidebar.add(arrSizeSlider);
        sidebar.add(sortDropdown);
        sidebar.add(startButton);
        sidebar.add(cancelButton);  // Add cancel button to the sidebar
    }

    // Create a slider with given parameters and a label showing its value
    private JSlider createSlider(int min, int max, int value, String prefix, String suffix) {
        JLabel label = new JLabel(prefix + value + suffix, SwingConstants.CENTER);
        label.setForeground(SECONDARY_COLOR);
        label.setFont(FONT);

        JSlider slider = new JSlider(min, max, value);
        slider.setPaintTicks(true);
        slider.addChangeListener(e -> {
            label.setText(prefix + slider.getValue() + suffix);
            if (prefix.contains("Speed")) Sort_Spectra.wait = slider.getValue();
            else Sort_Spectra.cnt = slider.getValue();
        });

        sidebar.add(label);
        return slider;
    }

    // Create a button with the specified text and color
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(FONT);
        button.setBackground(color);
        button.setForeground(SECONDARY_COLOR);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(300, 50));
        return button;
    }

    // Show a welcome popup when the application starts
    private void showWelcomePopup() {
        JDialog popup = new JDialog(this, "Welcome!", true);
        popup.setSize(1080, 600);
        popup.setLocationRelativeTo(this);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("resources/splash.png")));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(PRIMARY_COLOR);

        JButton continueButton = createButton("Continue", PRIMARY_COLOR);
        continueButton.addActionListener(e -> popup.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(PRIMARY_COLOR);
        buttonPanel.add(continueButton);

        popup.setLayout(new BorderLayout());
        popup.add(imageLabel, BorderLayout.CENTER);
        popup.add(buttonPanel, BorderLayout.SOUTH);
        popup.setVisible(true);
    }

    // Draw bars representing the array in the sorting visualization
    public void drawBars(Integer[] arr, int highlightIndex, int compareIndex, boolean isFinal) {
        graphPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 2, 0, 2);

        int heightScale = (int) ((getHeight() * 0.9) / arr.length);

        for (int i = 0; i < arr.length; i++) {
            JPanel bar = new JPanel();
            bar.setPreferredSize(new Dimension(Sort_Spectra.barWidth, arr[i] * heightScale));

            if (isFinal && i <= highlightIndex) {
                bar.setBackground(new Color(0x535C91));
            } else if (i == highlightIndex) {
                bar.setBackground(new Color(0x4E9F3D));
            } else if (i == compareIndex) {
                bar.setBackground(new Color(0xA13333));
            } else {
                bar.setBackground(new Color(0x1679AB));
            }

            graphPanel.add(bar, gbc);
        }

        repaint();
        validate();
    }

    // Public methods to update the graph with specific highlighting
    public void Draw(Integer[] arr) {
        drawBars(arr, -1, -1, false);
    }

    public void finalDraw(Integer[] arr, int j) {
        drawBars(arr, j, -1, true);
    }

    public void reDraw(Integer[] arr, int green, int red) {
        drawBars(arr, green, red, false);
    }
}
