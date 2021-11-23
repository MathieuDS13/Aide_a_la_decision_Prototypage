package com.company.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class University {
    String name;
    Map<String, Student> assignments;
    int capacity;
    List<Student> prefs;

    public University(String name) {
        this.name = name;
        this.assignments = new HashMap<>();
        this.capacity = 1;
        this.prefs = new ArrayList<>();
    }

    public University(String name, int capacity) {
        this.name = name;
        this.assignments = new HashMap<>();
        this.capacity = capacity;
        this.prefs = new ArrayList<>();
    }

    public boolean hasFreeSpace() {
        return assignments.size() < capacity;
    }

    public void addAssignment(Student student) throws Exception {
        if(assignments.size() >= capacity) throw new Exception("Assignments are already full");
        assignments.put(student.name, student);
    }

    public void removeAssignment(Student student) {
        Student removed = assignments.remove(student.name);
        System.out.println("Successfuly removed " + removed.name + " from " + name);
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addPref(Student student) {
        if (!prefs.contains(student)) {
            prefs.add(student);
        }
    }

    public String getPrefsToString() {
        StringBuilder builder = new StringBuilder();
        for (Student stud :
                prefs) {
            builder.append(stud.name).append(" > ");
        }
        if(builder.length() > 2) builder.delete(builder.length() -2, builder.length() -1);
        return builder.toString();
    }

    public Student getLowerPrefStudent(Student student) {
        int studPlace = prefs.indexOf(student);
        int stud2Ind;
        int max = studPlace;
        Student ret = null;
        if (studPlace == -1 || studPlace == 0) return null;
        for (Student stud :
                assignments.values()) {
            stud2Ind = prefs.indexOf(stud);
            if (stud2Ind > max) {
                max = stud2Ind;
                ret = stud;
            }
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        University that = (University) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
