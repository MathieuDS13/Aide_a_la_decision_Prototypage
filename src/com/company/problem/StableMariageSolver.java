package com.company.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Cette classe représente un solveur de problème d'affectation.
 * Il permet d'assigner des étudiants à des universités en se basant sur l'algorithme de mariage stable.
 */
public class StableMariageSolver {
    //Le problème qui devra être résolu.
    AssignmentProblem problem;

    /**
     * Un constructeur prenant en paramètre le problème qui devra être résolu
     *
     * @param problem
     */
    public StableMariageSolver(AssignmentProblem problem) {
        this.problem = problem;
    }

    /**
     * Résout le problème d'affectation en appliquant l'algorithme de mariage stable
     * en donnant la priorité aux universités. En effet, dans cette fonction les préférences des universités
     * permettent de trancher pour les affectations.
     *
     * @return une solution au problème
     * @throws Exception
     */
    public AssignmentSolution solveWithUnivPrefPriority() throws Exception {
        clear();
        List<Student> input = new ArrayList<>(problem.students);
        debLog("\n\n\t/// SOLVING --> Priority : university preferences ///\n\n");
        while (!input.isEmpty()) {
            List<Student> removeToInput = new ArrayList<>();
            List<Student> addToInput = new ArrayList<>();
            for (int i = 0, inputSize = input.size(); i < inputSize; i++) {
                Student stud = input.get(i);
                debLog("Treating student " + stud.name);
                removeToInput.add(stud);
                University wish = stud.getPref(stud.getCurrentRank());
                debLog("\tWhish n°" + stud.getCurrentRank() + " -> " + wish.name + " (" + wish.assignments.size() + " / " + wish.capacity + " spaces)");
                if (wish.hasFreeSpace()) {
                    debLog("\tWhish " + wish.name + " has free space");
                    debLog("\tAssigning " + wish.name + " to " + stud.name);
                    addAssignementLink(stud, wish);
                } else {
                    debLog("\tWhish " + wish.name + " has NO free space");
                    Student deleted = wish.getLowerPrefStudent(stud);
                    if (deleted != null) {
                        debLog("\tDeleting " + deleted.name + " from " + wish.name + " assignments to benefit " + stud.name);
                        deleted.incrementRank();
                        addToInput.add(deleted);
                        wish.removeAssignment(deleted);
                        deleted.assignment = null;
                        debLog("\tAdding " + stud.name + " to " + wish.name + " assignments");
                        addAssignementLink(stud, wish);
                    } else {
                        debLog("\t" + stud.name + " is not prefered to any student assigned to " + wish.name);
                        stud.incrementRank();
                        addToInput.add(stud);
                    }
                }
                if (stud.getCurrentRank() > stud.prefs.size() - 1) {
                    removeToInput.add((stud));
                    addToInput.remove(stud);
                }
            }
            input.removeAll(removeToInput);
            input.addAll(addToInput);
        }
        AssignmentSolution solution = new AssignmentSolution(problem.students, problem.universities);
        for (Student student :
                problem.students) {
            solution.addAssign(student, student.assignment);
        }
        solution.log();
        return solution;
    }

    /**
     * Cette fonction permet de générer le lien d'affectation entre un étudiant et une université passés en paramètre.
     *
     * @param student
     * @param university
     * @throws Exception
     */
    private void addAssignementLink(Student student, University university) throws Exception {
        student.addAssignment(university);
        university.addAssignment(student);
    }

    /**
     * Cette fonction permet d'afficher dans la console une chaine de caractères passée en argument.
     *
     * @param str une chaine de caractères à afficher
     */
    private void debLog(String str) {
        System.out.println(str);
    }

    /**
     * Réinitialise les affectations des étudiants et des universités afin de pouvoir lancer l'algorithme
     * de résolution sur une base vierge.
     */
    private void clear() {
        for (Student stud :
                problem.students) {
            stud.assignment = null;
            stud.rank = 0;
        }
        for (University univ : problem.universities) {
            univ.assignments = new HashMap<>();
            univ.rank = 0;
            //TODO modifier le rank si généralisation et apparition de rank côté université
        }
    }

    /**
     * Résout le problème d'affectation en appliquant l'algorithme de mariage stable
     * en donnant la priorité aux étudiants. En effet, dans cette fonction les préférences des étudiants
     * permettent de trancher pour les affectations.
     *
     * @return une solution au problème
     * @throws Exception
     */
    public AssignmentSolution solveWithStudPrefPriority() throws Exception {
        clear();
        List<University> input = new ArrayList<>(problem.universities);
        debLog("\n\n\t/// SOLVING --> Priority : students preferences ///\n\n");
        while (!input.isEmpty()) {
            List<University> removeToInput = new ArrayList<>();
            List<University> addToInput = new ArrayList<>();
            for (int i = 0, inputSize = input.size(); i < inputSize; i++) {
                University univ = input.get(i);
                debLog("Treating university " + univ.name);
                if (!univ.hasFreeSpace() || univ.getCurrentRank() >= univ.prefs.size()) {
                    removeToInput.add(univ);
                    continue;
                }
                Student wish = univ.getPref(univ.getCurrentRank());
                debLog("\tWhish n°" + univ.getCurrentRank() + " -> " + wish.name);
                if (wish.assignment == null) {
                    debLog("\tWhish " + wish.name + " has no assignment");
                    debLog("\tAssigning " + wish.name + " to " + univ.name);
                    addAssignementLink(wish, univ);
                } else {
                    debLog("\tWhish " + wish.name + " has ALREADY an assignment");
                    University deleted = wish.getLowerPrefUniv(univ);
                    if (deleted != null) {
                        debLog("\tDeleting " + deleted.name + " from " + wish.name + " assignments to benefit " + univ.name);
                        deleted.incrementRank();
                        addToInput.add(deleted);
                        wish.assignment = null;
                        deleted.removeAssignment(wish);
                        debLog("\tAdding " + univ.name + " to " + wish.name + " assignments");
                        addAssignementLink(wish, univ);
                    } else {
                        debLog("\t" + univ.name + " is not prefered to any universite assigned to " + wish.name);
                        univ.incrementRank();
                        addToInput.add(univ);
                    }
                }
                if (univ.getCurrentRank() > univ.prefs.size() - 1) {
                    removeToInput.add((univ));
                    addToInput.remove(univ);
                }
            }
            input.removeAll(removeToInput);
            input.addAll(addToInput);
        }
        AssignmentSolution solution = new AssignmentSolution(problem.students, problem.universities);
        for (Student student :
                problem.students) {
            solution.addAssign(student, student.assignment);
        }
        solution.log();
        return solution;
    }
}
