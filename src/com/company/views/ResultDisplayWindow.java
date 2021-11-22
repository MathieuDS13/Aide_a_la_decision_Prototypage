package com.company.views;

import com.company.problem.AssignementSolution;

import javax.swing.*;
import java.awt.*;

public class ResultDisplayWindow extends JFrame {
    private JScrollPane scrollablePanel;
    private JPanel rootPanel;
    AssignementSolution solution;
    JMultilineLabel resultBody = new JMultilineLabel("N/A");

    public ResultDisplayWindow(AssignementSolution kernel) {
        this.solution = kernel;
        this.rootPanel = new JPanel();
        this.scrollablePanel = new JScrollPane(rootPanel);
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        init();
        start();
    }

    private void init() {
        rootPanel.setAutoscrolls(true);
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.add(resultBody);
        resultBody.setText(solution.toString());
    }

    private void start() {
        setTitle("Result");
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
