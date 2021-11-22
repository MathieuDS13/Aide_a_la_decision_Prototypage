package com.company;

import com.company.problem.AssignementProblem;
import com.company.problem.AssignementSolution;
import com.company.problem.StableMariageSolver;

public class Main {

    public static void main(String[] args) throws Exception {
        AssignementProblem problem = AssignementProblem.createRandomProblem(20, 3);
        problem.log();
        StableMariageSolver solver = new StableMariageSolver(problem);
        AssignementSolution solution = solver.solveWithUnivPrefPriority();
    }
}
