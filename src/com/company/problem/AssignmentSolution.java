package com.company.problem;

import java.util.*;

public class AssignmentSolution {
    Map<Student, University> studAssignments;
    Map<University, List<Student>> univAssignements;
    List<Student> students;
    List<University> universities;

    public AssignmentSolution(List<Student> students, List<University> universities) {
        this.studAssignments = new HashMap<>();
        this.univAssignements = new HashMap<>();
        this.students = students;
        this.universities = universities;
    }

    public void addAssign(Student student, University university) {

        studAssignments.put(student, university);
        univAssignements.putIfAbsent(university, new ArrayList<>());
        univAssignements.get(university).add(student);
    }

    public void log() {
        System.out.println(toString());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n\t/// SOLUTION ///\n\n");

        for (Student student :
                students) {
            if (student.assignment != null) {
                builder.append("\n").append(student.name).append(" assigned to ").append(student.assignment.name).append("; assignment rank ").append(student.getAssignmentRank());
                builder.append(" satisfaction ").append(computeSatisfaction(student));
                builder.append("\n").append("Assignment satisfaction " + computeSatisfaction(student.assignment));
            } else {
                builder.append("\n").append(student.name).append(" assigned to NONE");
            }
        }
        return builder.toString();
    }

    public double computeSatisfaction(Student student) {

        double res = ((double) student.prefs.size() - (double) student.getAssignmentRank()) / (double) student.prefs.size();
        System.out.println(student.prefs.size());
        return res;
    }

    public double computeSatisfaction(University university) {
        //TODO parce que pour le moment c'est de la merde
        double acc = 0;
        for (Student assignment : university.assignments.values()) {
            double temp = (((double) university.prefs.size() - (double) university.getRank(assignment)) / (double) university.prefs.size());
            acc += 1 / temp;
        }
        double nmbrAssignments = university.assignments.values().size();
//        acc = acc / nmbrAssignments;
//        return acc * (nmbrAssignments / university.capacity);
        return acc;
    }
}
