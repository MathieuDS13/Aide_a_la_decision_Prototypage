package com.company.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe représente une université.
 */
public class University {
    //Le rang actuel de préférence traité par l'algorithme d'affectation.
    public int rank;
    //Le nom de l'université.
    String name;
    //La liste des étudiants affectés à l'université.
    Map<String, Student> assignments;
    //La capacité maximale de l'université.
    int capacity;
    //La liste des préférences de l'université. Un étudiant d'index bas est préféré à un étudiant d'index supérieur.
    List<Student> prefs;

    /**
     * Un constructeur.
     * @param name
     */
    public University(String name) {
        this.name = name;
        this.assignments = new HashMap<>();
        this.capacity = 1;
        this.prefs = new ArrayList<>();
        this.rank = 0;
    }

    /**
     * Un constructeur.
     * @param name
     * @param capacity
     */
    public University(String name, int capacity) {
        this.name = name;
        this.assignments = new HashMap<>();
        this.capacity = capacity;
        this.prefs = new ArrayList<>();
        this.rank = 0;
    }

    /**
     * Indique si l'université est déjà pleine ou non, c'est-à-dire si il y a autant d'étudiants que la valeur capacity
     * assignés.
     */
    public boolean hasFreeSpace() {
        return assignments.size() < capacity;
    }

    /**
     * Ajoute l'étudiant passé en paramètre aux affectations de l'université.
     * @param student
     * @throws Exception
     */
    public void addAssignment(Student student) throws Exception {
        if (assignments.size() >= capacity) throw new Exception("Assignments are already full");
        assignments.put(student.name, student);
    }

    /**
     * Retire l'étudiant passé en paramètre des affectations de l'université.
     * @param student
     */
    public void removeAssignment(Student student) {
        Student removed = assignments.remove(student.name);
        System.out.println("Successfuly removed " + removed.name + " from " + name);
    }

    /**
     * Définit la capacité de l'université à la valeur du paramètre donné.
     * @param capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Ajoute une préférence à la fin de la liste des préférences.
     * Seulement si l'étudiant n'appartient pas déjà aux préférences de l'université.
     * @param student
     */
    public void addPref(Student student) {
        if (!prefs.contains(student)) {
            prefs.add(student);
        }
    }

    /**
     * Retourne sous forme de chaine de caractères la liste des préférences de l'université.
     * @return
     */
    public String getPrefsToString() {
        StringBuilder builder = new StringBuilder();
        for (Student stud :
                prefs) {
            builder.append(stud.name).append(" > ");
        }
        if (builder.length() > 2) builder.delete(builder.length() - 2, builder.length() - 1);
        return builder.toString();
    }

    /**
     * Prend en paramètre un étudiant et retourne un étudiant des affectations de l'université moins préféré à celui-ci
     * ou null s'il n'en existe pas.
     * @param student
     * @return
     */
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

    /**
     * Incrémente le rang de traitement de l'algorithme d'affectation.
     */
    public void incrementRank() {
        rank++;
    }

    /**
     * Retourne le rang de traitement actuel de l'algortihme d'affectation.
     * @return
     */
    public int getCurrentRank() {
        return rank;
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

    /**
     * Retourne la préférence de rang rank.
     * @param rank
     * @return
     */
    public Student getPref(int rank) {
        return prefs.get(rank);
    }

    /**
     * Retourne le rang de préférence de l'étudiant passé en paramètre. -1 si l'étudiant n'est pas dans
     * les préférences.
     * @param assignment
     * @return
     */
    public int getRank(Student assignment) {
        return prefs.indexOf(assignment);
    }
}
