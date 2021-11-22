package com.company.problem;

import java.util.*;

public class AssignementSolution {
    Map<Student, University> studAssignments;
    Map<University, List<Student>> univAssignements;
    List<Student> students;
    List<University> universities;
    public AssignementSolution(List<Student> students, List<University> universities) {
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
            if(student.assignment != null) {
                builder.append("\n"+ student.name + " assigned to " + student.assignment.name + "; assignment rank " + student.getAssignmentRank());
            } else {
                builder.append("\n" + student.name + " assigned to NONE");
            }
        }
        return builder.toString();
    }
}
