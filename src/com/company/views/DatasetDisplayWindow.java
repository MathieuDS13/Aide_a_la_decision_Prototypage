package com.company.views;

import com.company.app.Kernel;
import com.company.problem.AssignementSolution;

import javax.swing.*;
import java.awt.*;

public class DatasetDisplayWindow extends JFrame {

    private JScrollPane scrollablePanel;
    private JPanel rootPanel;
    Kernel kernel;
    JLabel result = new JLabel("Result :");
    JMultilineLabel resultBody = new JMultilineLabel("N/A");

    public DatasetDisplayWindow(Kernel kernel) {
        this.kernel = kernel;
        this.rootPanel = new JPanel();
        this.scrollablePanel = new JScrollPane(rootPanel);
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        init();
        start();
    }

    private void init() {
        rootPanel.setAutoscrolls(true);
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.add(result);
        rootPanel.add(resultBody);
        resultBody.setText(kernel.problem.getDatasetToString());
    }

    private void start() {
        setTitle("Dataset Display");
        scrollablePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setContentPane(scrollablePanel);
        scrollablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

}
