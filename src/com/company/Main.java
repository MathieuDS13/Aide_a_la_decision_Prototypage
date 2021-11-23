package com.company;

import com.company.problem.AssignmentProblem;
import com.company.problem.AssignmentSolution;
import com.company.problem.StableMariageSolver;

public class Main {

    public static void main(String[] args) throws Exception {
        AssignmentProblem problem = AssignmentProblem.createRandomProblem(20, 3);
        problem.log();
        StableMariageSolver solver = new StableMariageSolver(problem);
        AssignmentSolution solution = solver.solveWithUnivPrefPriority();
    }
}
