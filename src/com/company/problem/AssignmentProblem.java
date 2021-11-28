package com.company.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Une classe représentant un problème d'assignation avec d'un côté les étudiants et de l'autre les universités auxquelles
 * les assigner.
 */
public class AssignmentProblem {
    //La liste des étudiants
    List<Student> students;
    //La liste des universités auxquelles les assigner
    List<University> universities;

    //Permet de générer l'aléatoire pour la génération aléatoire de problème
    Random random = new Random();

    /**
     * Un constructeur privé pour appliquer un design pattern factory afin de géner le problème de
     * différentes manières
     */
    private AssignmentProblem() {
        this.students = new ArrayList<>();
        this.universities = new ArrayList<>();
    }

    /**
     * Un constructeur qui permet de créer un problème d'affectation à partir d'une liste
     * d'étudiants students avec leurs préférences studsPrefs ainsi qu'une liste d'universités universities
     * et leurs préférences univsPrefs prédéfinies.
     *
     * @param students
     * @param universities
     * @param studsPrefs
     * @param univsPrefs
     * @return
     */
    public static AssignmentProblem createProblemFromVals(Map<String, Student> students, Map<String, University> universities, Map<String, List<String>> studsPrefs, Map<String, List<String>> univsPrefs) {
        AssignmentProblem problem = new AssignmentProblem();
        problem.students = new ArrayList<>(students.values());
        problem.universities = new ArrayList<>(universities.values());
        for (String stud :
                studsPrefs.keySet()) {
            Student currentStud = students.get(stud);
            for (String pref :
                    studsPrefs.get(stud)) {
                currentStud.addPref(universities.get(pref));
            }
        }

        for (String univ :
                univsPrefs.keySet()) {
            University currentUniv = universities.get(univ);
            for (String pref :
                    univsPrefs.get(univ)) {
                currentUniv.addPref(students.get(pref));
            }
        }
        return problem;
    }

    /**
     * Permet de créer un problème d'affectation avec un nombre studCount d'étudiants et un nombre
     * univCount d'universités.
     * La capacité de chaque université est générée aléatoirement de manière à ce que le total des capacités
     * de toutes les universités soit égal au nombre d'étudiants.
     * Ainsi chaque étudiant est sûr de pouvoir être assigné à une université.
     *
     * @param studCount
     * @param univCount
     * @return
     */
    public static AssignmentProblem createRandomProblem(int studCount, int univCount) {
        AssignmentProblem problem = new AssignmentProblem();
        problem.createStudents(studCount);
        problem.createUnivs(univCount);
        problem.createPrefAssociations();
        problem.updateCapacitiesToFeatStudents();
        return problem;
    }

    /**
     * Cette fonction permet d'adapter les capacités des universités générées aléatoirement afin que :
     * - chaque université ai une capacité aléatoire d'au minimum 1 place
     * - la somme des capacités de toutes les universités est égale au nombre d'étudiants à y assigner
     * ainsi chaque étudiant peut avoir une place dans une université
     */
    private void updateCapacitiesToFeatStudents() {
        int diff = students.size() - universities.size();
        //Si on a plus d'étudiants que d'universités on augmente la capacité des universités aléatoirement pour avoir de la place pour tous
        int toUp;
        if (diff > 0) {
            if (!universities.isEmpty()) {
                for (int i = 0; i < diff; i++) {
                    toUp = random.nextInt(0, universities.size());
                    University univToUp = universities.get(toUp);
                    univToUp.setCapacity(univToUp.capacity + 1);
                }
            }
        }
    }

    /**
     * Cette fonction permet de générer les préférences des universités et des étudiants aléatoirement.
     * Les préférences générées sont complètes, c'est à dire que chaque université classe tous les étudiants
     * et chaque étudiant classe toutes les universités
     */
    private void createPrefAssociations() {
        List<University> univClone;
        for (Student stud :
                students) {
            univClone = new ArrayList<>(universities);
            int ind;
            while (!univClone.isEmpty()) {
                ind = random.nextInt(0, univClone.size());
                stud.addPref(univClone.get(ind));
                univClone.remove(ind);
            }
        }

        List<Student> studClone;
        for (University univ :
                universities) {
            studClone = new ArrayList<>(students);
            int ind;
            while (!studClone.isEmpty()) {
                ind = random.nextInt(0, studClone.size());
                univ.addPref(studClone.get(ind));
                studClone.remove(ind);
            }
        }
    }

    /**
     * Cette fonction permet de créer un nombre d'universités passé en paramètre
     * @param univCount
     */
    private void createUnivs(int univCount) {
        for (int i = 0; i < univCount; i++) {
            University univ = new University("univ_" + i);
            universities.add(univ);
        }
    }

    /**
     * Cette fonction permet de créer un nombre d'étudiants passé en paramètre
     * @param studCount
     */
    private void createStudents(int studCount) {
        for (int i = 0; i < studCount; i++) {
            Student student = new Student("etu_" + i);
            students.add(student);
        }
    }

    /**
     * Affiche dans la console le problème d'affectation sous forme lisible par l'utilisateur
     */
    public void log() {
        System.out.println(getDatasetToString());
    }

    /**
     * Renvoie une représentation du problème d'affectation sous la forme d'une chaine de caractères
     * @return
     */
    public String getDatasetToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PROBLEM STATS :");
        builder.append("\n" + universities.size() + " Universities");
        builder.append("\n" + students.size() + " Students");

        for (University univ :
                universities) {
            builder.append("\n" + univ.name + " " + univ.capacity + " place(s)");
        }

        builder.append("\n\n\n\n\nSTUDENTS PREFERENCES : \n");
        for (Student stud :
                students) {
            String prefs = stud.getPrefsToString();
            builder.append("\n\n\tStudent --> " + stud.name);
            builder.append("\n\t\t" + prefs);
        }

        builder.append("\n\n\n\n\nUNIVERSITY PREFERENCES :");
        for (University univ :
                universities) {
            String prefs = univ.getPrefsToString();
            builder.append("\n\n\tUniversity --> " + univ.name);
            builder.append("\n\t\t" + prefs);
        }
        builder.append("\n\n");
        return builder.toString();
    }

    /**
     * Permet d'obtenir le nombre d'étudiants au total du problème d'affectation
     * @return
     */
    public int getStudentsCount() {
        return students.size();
    }

    /**
     * Permet d'obtenir le nombre d'universités au total du problème d'affectation
     * @return
     */
    public int getUnivsCount() {
        return universities.size();
    }

}
