package com.company.views;

import com.company.app.Kernel;
import com.company.problem.AssignmentSolution;
import com.company.problem.StableMariageSolver;

import javax.swing.*;

public class ResultCompWindow extends JFrame {
    JPanel root;
    JScrollPane scrollPane;
    JPanel studPrioPanel;
    JPanel univPrioPanel;
    Kernel kernel;

    public ResultCompWindow(Kernel kernel) {
        this.kernel = kernel;
        this.root = new JPanel();
        this.scrollPane = new JScrollPane(root);

        root.setAutoscrolls(true);
        root.setLayout(new BoxLayout(root, BoxLayout.X_AXIS));

        this.studPrioPanel = makeStudPrefPanel();
        this.univPrioPanel = makeUnivPrefPanel();

        root.add(studPrioPanel);
        root.add(univPrioPanel);

        start();
    }

    private JPanel makeStudPrefPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Stats with students pref priority :"));

        JMultilineLabel body = new JMultilineLabel("");

        StableMariageSolver solver = new StableMariageSolver(kernel.problem);
        try {
            AssignmentSolution solution = solver.solveWithStudentPriority();
            body.append("\nTotal Students satisfaction :");
            body.append("\n" + solution.computeStudentsSatisfStat());

            body.append("\nTotal Universities satisfaction :");
            body.append("\n" + solution.computeUniversitiesSatisfStat());

            body.append("\n\n\n");
            body.append("\nStudents assignments :");
            body.append("\n");
            body.append(solution.getStudAssigns());
            body.append("\n\n\n");

            body.append("\nUniversities assignments :");
            body.append("\n");
            body.append(solution.getUnivAssigns());
        } catch (Exception e) {
            body.setText(e.getMessage());
            e.printStackTrace();
        }

        panel.add(body);
        return panel;
    }

    private JPanel makeUnivPrefPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Stats with universities pref priority :"));
        JMultilineLabel body = new JMultilineLabel("");


        StableMariageSolver solver = new StableMariageSolver(kernel.problem);
        try {
            AssignmentSolution solution = solver.solveWithUniversityPriority();
            body.append("\nTotal Students satisfaction :");
            body.append("\n" + solution.computeStudentsSatisfStat());

            body.append("\nTotal Universities satisfaction :");
            body.append("\n" + solution.computeUniversitiesSatisfStat());

            body.append("\n\n\n");
            body.append("\nStudents assignments :");
            body.append("\n");
            body.append(solution.getStudAssigns());
            body.append("\n\n\n");
            body.append("\nUniversities assignments :");
            body.append("\n");
            body.append(solution.getUnivAssigns());
        } catch (Exception e) {
            body.setText(e.getMessage());
            e.printStackTrace();
        }
        panel.add(body);
        return panel;
    }

    private void start() {
        setTitle("Algo Comparaison");
        setContentPane(scrollPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
