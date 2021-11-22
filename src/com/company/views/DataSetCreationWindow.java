package com.company.views;

import com.company.app.Kernel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

public class DataSetCreationWindow extends JFrame {
    JPanel rootPanel;
    Kernel kernel;
    JLabel studLabel;
    JTextField studCount;
    JLabel univLabel;
    JTextField univCount;
    JButton validate;

    public DataSetCreationWindow(Kernel kernel) {
        this.kernel = kernel;
        this.rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        addStudFields();
        addUnivFields();
        addValidationButton();
        start();
    }

    private void addStudFields() {
        studLabel = new JLabel("How many students ?");
        studCount = new JTextField();
        rootPanel.add(studLabel);
        rootPanel.add(studCount);
    }

    private void addUnivFields() {
        univLabel = new JLabel("How many universities ?");
        univCount = new JTextField();
        rootPanel.add(univLabel);
        rootPanel.add(univCount);
    }

    private void addValidationButton() {
        validate = new JButton("Create random dataset");
        validate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int studs = Integer.parseInt(studCount.getText());
                int univs = Integer.parseInt(univCount.getText());
                kernel.createRandomDataset(studs, univs);
                dispose();
            }
        });
        rootPanel.add(validate);
    }

    private void start() {
        setTitle("Dataset Creation");
        rootPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setContentPane(rootPanel);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        setSize(400, 300);
        setLocationRelativeTo(null);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
