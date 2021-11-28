package com.company.views;

import com.company.app.Kernel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * La classe représentant la page principale de l'application.
 */
public class MainWindow extends JFrame {
    private JPanel rootPanel;
    private JButton createRandomDatasetButton;
    private JButton solveAndDisplayButton;
    private JButton parseButton;
    private JButton displayDataset;
    private JButton displayComparaison;
    private JLabel studDataset;
    private JLabel univDataset;
    private JLabel errorLabel;
    private JFileChooser fileChooser;

    private Kernel kernel;

    public MainWindow(Kernel kernel) {
        this.kernel = kernel;
        this.rootPanel = new JPanel();
        this.fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.add(makeDatasetInfoPanel());
        rootPanel.add(makeCreateRandomDataSetButton());
        rootPanel.add(makeParseErrorLabel());
        rootPanel.add(makeParseFromFileButton());
        rootPanel.add(makeDisplayDatasetButton());
        rootPanel.add(makeSolveAndDisplayButton());
        rootPanel.add(makeCompButton());

        start();
    }

    private JButton makeCompButton() {
        this.displayComparaison = new JButton("Display algo comp");
        displayComparaison.setVisible(false);
        displayComparaison.addActionListener(e -> {
            new ResultCompWindow(kernel);
        });
        return displayComparaison;
    }

    private JPanel makeDatasetInfoPanel() {
        JPanel pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        this.univDataset = new JLabel(kernel.getUnivDatasetString());
        this.studDataset = new JLabel(kernel.getStudDatasetString());
        pan.setSize(Math.max(univDataset.getWidth(), studDataset.getWidth()), univDataset.getHeight());
        pan.add(studDataset);
        pan.add(univDataset);
        return pan;
    }

    private JButton makeCreateRandomDataSetButton() {
        createRandomDatasetButton = new JButton("Create random Dataset");
        createRandomDatasetButton.addActionListener(e -> kernel.createRandomDataset());

        return createRandomDatasetButton;
    }

    private JButton makeSolveAndDisplayButton() {
        solveAndDisplayButton = new JButton("Solve and display");
        solveAndDisplayButton.addActionListener(e -> kernel.solve());
        solveAndDisplayButton.setVisible(false);
        return solveAndDisplayButton;
    }

    private JButton makeDisplayDatasetButton() {
        displayDataset = new JButton("Display dataset");
        displayDataset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kernel.displayDataset();
            }
        });
        displayDataset.setVisible(false);
        return displayDataset;
    }

    private JLabel makeParseErrorLabel() {
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.red);
        errorLabel.setVisible(false);
        return errorLabel;
    }

    private JButton makeParseFromFileButton() {
        parseButton = new JButton("Dataset From File");
        parseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(MainWindow.this);

                try {
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        errorLabel.setVisible(false);
                        File file = fileChooser.getSelectedFile();
                        kernel.createProblemFromParsing(file);
                    } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                        return;
                    } else {
                        errorLabel.setText("Error : no file found");
                        errorLabel.setVisible(true);
                    }
                } catch (Exception exc) {
                    errorLabel.setText(exc.getMessage());
                    errorLabel.setVisible(true);
                    exc.printStackTrace();
                }
            }
        });
        return parseButton;
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
            solveAndDisplayButton.setVisible(true);
            displayComparaison.setVisible(true);
        } else {
            displayDataset.setVisible(false);
            solveAndDisplayButton.setVisible(false);
            displayComparaison.setVisible(false);

        }
    }
}
