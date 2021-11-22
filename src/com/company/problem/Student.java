package com.company.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Student {
    String name;
    List<University> prefs;
    University assignment = null;
    int rank = 0;

    public Student(String name) {
        this.name = name;
        this.prefs = new ArrayList<>();
    }

    public void addPref(University university) {
        if (!prefs.contains(university)) {
            prefs.add(university);
        }
    }

    public University getPref(int rank) {
        return prefs.get(rank);
    }

    public String getPrefsToString() {
        StringBuilder builder = new StringBuilder();
        for (University univ :
                prefs) {
            builder.append(univ.name + " > ");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void addAssignment(University university) {
        assignment = university;
    }

    public int getAssignmentRank() {
        return prefs.indexOf(assignment);
    }

    public void incrementRank(){
        rank++;
    }

    public int getCurrentRank() {
        return rank;
    }
}
