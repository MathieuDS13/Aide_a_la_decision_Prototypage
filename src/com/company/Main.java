package com.company;

import com.company.problem.AssignmentProblem;
import com.company.problem.AssignmentSolution;
import com.company.problem.StableMariageSolver;

/**
 * Une classe avec une fonction main de test de l'application.
 * Attention il ne s'agit que d'une classe de test et non pas de l'application.
 * Pour le lancement de l'application voir la classe Launcher.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        AssignmentProblem problem = AssignmentProblem.createRandomProblem(20, 3);
        problem.log();
        StableMariageSolver solver = new StableMariageSolver(problem);
        AssignmentSolution solution = solver.solveWithUnivPrefPriority();
    }
}
