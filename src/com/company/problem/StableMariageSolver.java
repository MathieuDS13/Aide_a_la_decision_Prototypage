package com.company.problem;

import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StableMariageSolver {
    AssignementProblem problem;

    public StableMariageSolver(AssignementProblem problem) {
        this.problem = problem;
    }

    public AssignementSolution solveWithUnivPrefPriority() throws Exception {
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
        AssignementSolution solution = new AssignementSolution(problem.students, problem.universities);
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
}
