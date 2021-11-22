package com.company.views;

import com.company.app.Kernel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JButton button1;
    private JButton button2;
    private JLabel studDataset;
    private JLabel univDataset;
    private Kernel kernel;
    private JButton displayDataset;

    public MainWindow(Kernel kernel) {
        this.kernel = kernel;
        this.rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        createDatasetLabels();
        createButtons();
        start();
    }

    private void createDatasetLabels() {
        this.studDataset = new JLabel(kernel.getStudDatasetString());
        this.univDataset = new JLabel(kernel.getUnivDatasetString());
        rootPanel.add(studDataset);
        rootPanel.add(univDataset);
    }

    private void createButtons() {
        button1 = new JButton("Create random Dataset");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kernel.createRandomDataset();
            }
        });

        rootPanel.add(Box.createHorizontalGlue());
        rootPanel.add(button1);

        button2 = new JButton("Solve and display");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kernel.solve();
            }
        });
        rootPanel.add(button2);
        button2.setVisible(false);

        displayDataset = new JButton("Display dataset");
        displayDataset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kernel.displayDataset();
            }
        });
        rootPanel.add(displayDataset);
        displayDataset.setVisible(false);
    }

    private void start() {
        setTitle("Association solver");
        rootPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setContentPane(rootPanel);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }


    public void updateDatasetDisplay() {
        studDataset.setText(kernel.getStudDatasetString());
        univDataset.setText(kernel.getUnivDatasetString());
        if (kernel.problem != null) {
            displayDataset.setVisible(true);
            button2.setVisible(true);
        } else {
            displayDataset.setVisible(false);
            button2.setVisible(true);
        }
    }
}
