package com.company.app;

import com.company.problem.AssignmentProblem;
import com.company.problem.AssignmentProblemParser;
import com.company.problem.AssignmentSolution;
import com.company.problem.StableMariageSolver;
import com.company.views.DataSetCreationWindow;
import com.company.views.DatasetDisplayWindow;
import com.company.views.ResultDisplayWindow;
import com.company.views.MainWindow;

import java.io.File;

/**
 * Une classe qui contient les éléments pour une session de lancement de l'application.
 */
public class Kernel {

    public AssignmentProblem problem = null;
    public StableMariageSolver solver = null;
    public AssignmentSolution solution = null;
    MainWindow mainWindow;

    public void lauch() {
        this.mainWindow = new MainWindow(this);
    }

    public void createRandomDataset() {
        new DataSetCreationWindow(this);
    }

    public void setProblem(AssignmentProblem problem) {
        this.problem = problem;
        this.solution = null;
        this.solver = null;
        mainWindow.updateDatasetDisplay();
    }

    public String getStudDatasetString() {
        StringBuilder builder = new StringBuilder();
        if (problem != null) {
            builder.append("Students : ").append(problem.getStudentsCount()).append(" students");
        }
        return builder.toString();
    }

    public String getUnivDatasetString() {
        StringBuilder builder = new StringBuilder();
        if (problem != null) {
            builder.append("Universities : ").append(problem.getUnivsCount()).append(" universities");
        }
        return builder.toString();
    }

    public void createRandomDataset(int studCount, int univCount) {
        AssignmentProblem newProblem = AssignmentProblem.createRandomProblem(studCount, univCount);
        setProblem(newProblem);
    }

    public void solve() {
        if (problem != null) {
            this.solver = new StableMariageSolver(problem);
            try {
                //solution = solver.solveWithUnivPrefPriority();
                //TODO changer ici le bon appel
                solution = solver.solveWithStudPrefPriority();
                displaySolution();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void displaySolution() {
        if (solution != null) new ResultDisplayWindow(solution);
        //TODO implémenter l'affiche de la page de solution d'assignation
    }

    public void displayDataset() {
        if (problem != null) new DatasetDisplayWindow(this);
    }

    public void createProblemFromParsing(File file) throws Exception {
        this.problem = AssignmentProblemParser.parse(file);
        mainWindow.updateDatasetDisplay();
    }


}
