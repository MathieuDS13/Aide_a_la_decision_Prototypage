package com.company.app;

import com.company.problem.AssignementProblem;
import com.company.problem.AssignementSolution;
import com.company.problem.StableMariageSolver;
import com.company.views.DataSetCreationWindow;
import com.company.views.DatasetDisplayWindow;
import com.company.views.ResultDisplayWindow;
import com.company.views.MainWindow;

public class Kernel {

    public AssignementProblem problem = null;
    public StableMariageSolver solver = null;
    public AssignementSolution solution = null;
    MainWindow mainWindow;

    public void lauch() {
        this.mainWindow = new MainWindow(this);
    }

    public void createRandomDataset() {
        new DataSetCreationWindow(this);
    }

    public void setProblem(AssignementProblem problem) {
        this.problem = problem;
        this.solution = null;
        this.solver = null;
        mainWindow.updateDatasetDisplay();
    }

    public String getStudDatasetString() {
        StringBuilder builder = new StringBuilder();
        if(problem != null) {
            builder.append("Students : " + problem.getStudentsCount() + " students");
        }
        return builder.toString();
    }

    public String getUnivDatasetString() {
        StringBuilder builder = new StringBuilder();
        if(problem != null) {
            builder.append("Universities : " + problem.getUnivsCount() + " universities");
        }
        return builder.toString();
    }

    public void createRandomDataset(int studCount, int univCount) {
        AssignementProblem newProblem = AssignementProblem.createRandomProblem(studCount, univCount);
        setProblem(newProblem);
    }

    public void solve() {
        if(problem != null){
            this.solver = new StableMariageSolver(problem);
            try {
                solution = solver.solveWithUnivPrefPriority();
                displaySolution();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else return;
    }

    public void displaySolution() {
        if(solution != null) new ResultDisplayWindow(solution);
        //TODO implémenter l'affiche de la page de solution d'assignation
    }

    public void displayDataset() {
        if(problem != null) new DatasetDisplayWindow(this);
    }
}
