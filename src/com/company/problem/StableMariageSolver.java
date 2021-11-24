package com.company.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StableMariageSolver {
    AssignmentProblem problem;

    public StableMariageSolver(AssignmentProblem problem) {
        this.problem = problem;
    }

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

    private void addAssignementLink(Student student, University university) throws Exception {
        student.addAssignment(university);
        university.addAssignment(student);
    }

    private void debLog(String str) {
        System.out.println(str);
    }

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
