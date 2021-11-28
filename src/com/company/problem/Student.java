package com.company.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe représentant un étudiant.
 */
public class Student {
    //Le nom de l'étudiant
    String name;
    //La liste des préférences de l'étudiant. Une université d'indice bas est préférée à une université d'indice haut.
    List<University> prefs;
    //L'université à laquelle est affecté l'étudiant.
    University assignment = null;
    //Un entier représentant le rang de préférence actuellement traité par l'algorithme de mariage stable
    int rank = 0;

    /**
     * Constructeur
     *
     * @param name
     */
    public Student(String name) {
        this.name = name;
        this.prefs = new ArrayList<>();
    }

    /**
     * Ajoute une préférence à la fin de la liste de préférences de l'étudiant.
     * Si la préférence existe déjà elle n'est pas ajoutée.
     *
     * @param university
     */
    public void addPref(University university) {
        if (!prefs.contains(university)) {
            prefs.add(university);
        }
    }

    /**
     * Retourne la préférence pour le rang donné en paramètre.
     * @param rank
     * @return
     */
    public University getPref(int rank) {
        return prefs.get(rank);
    }

    /**
     * Retourne une chaine de caractères représentant les préférences de l'étudiant.
     * @return
     */
    public String getPrefsToString() {
        StringBuilder builder = new StringBuilder();
        for (University univ :
                prefs) {
            builder.append(univ.name + " > ");
        }
        if (builder.length() > 2) builder.delete(builder.length() - 2, builder.length() - 1);
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

    /**
     * Assigne l'étudiant à l'université passée en paramètre.
     * @param university
     */
    public void addAssignment(University university) {
        assignment = university;
    }

    /**
     * Retourne le rang de préférence de l'affection de l'étudiant. -1 si l'étudiant n'a pas d'affectation.
     * @return
     */
    public int getAssignmentRank() {
        return prefs.indexOf(assignment);
    }

    /**
     * Incrémente le rang de traitement actuel.
     */
    public void incrementRank() {
        rank++;
    }

    /**
     * Retourne le rang de traitement actuel.
     * @return
     */
    public int getCurrentRank() {
        return rank;
    }

    /**
     * Cette fonction calcule quelle université est préférée entre celle couramment affectée à l'étudiant
     * et celle passée en paramètre et retourne la moins préférée des deux, ou null si l'étudiant
     * n'a pas encore d'affectation.
     * @param other
     * @return
     * @throws Exception
     */
    public University getLowerPrefUniv(University other) throws Exception {
        int otherIndex = prefs.indexOf(other);
        int assignRank = getAssignmentRank();
        if (otherIndex == -1) throw new Exception("Student.get LowerPrefStudent() : Prefs not in prefs list");
        if (otherIndex < assignRank) {
            return assignment;
        }
        return null;
    }


}
