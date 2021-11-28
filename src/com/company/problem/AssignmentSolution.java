package com.company.problem;

import java.util.*;

/**
 * Cette classe représente une solution au problème d'affectation
 */
public class AssignmentSolution {
    //Représente les affectations des étudiants, la clé est un étudiant et la valeur associée est son affectation
    Map<Student, University> studAssignments;

    //Représente les affectations des universités, comme une université peut accueillir plusieurs étudiants
    //les étudiants affectés sont représentés par une liste.
    //La clé représente une université, la valeur retournée est la liste des étudiants affectés
    Map<University, List<Student>> univAssignements;

    //La liste des étudiants
    List<Student> students;

    //La liste des universités
    List<University> universities;

    /**
     * Un constructeur qui prend en paramètre une liste d'étudiants et une liste d'universités
     *
     * @param students
     * @param universities
     */
    public AssignmentSolution(List<Student> students, List<University> universities) {
        this.studAssignments = new HashMap<>();
        this.univAssignements = new HashMap<>();
        this.students = students;
        this.universities = universities;
    }

    /**
     * Ajoute le lien d'affectation entre l'étudiant et l'université passés en paramètre.
     * L'étudiant est affecté à l'université et il est ajouté dans la liste des affectations de l'université.
     *
     * @param student
     * @param university
     */
    public void addAssign(Student student, University university) {
        studAssignments.put(student, university);
        univAssignements.putIfAbsent(university, new ArrayList<>());
        univAssignements.get(university).add(student);
    }

    /**
     * Affiche dans la console la solution sous une forme lisible par l'utilisateur.
     */
    public void log() {
        System.out.println(toString());
    }

    /**
     * Renvoie une représentation de la solution sous la forme d'une chaine de caractères.
     *
     * @return
     */
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

    /**
     * Calcule la satisfaction d'un étudiant selon son affectation.
     *
     * @param student
     * @return
     */
    public double computeSatisfaction(Student student) {
        double prefsCount = student.prefs.size();
        double min = 0;
        double max = prefsCount - 1;
        double rank = student.getAssignmentRank();
        double assigned = student.assignment != null ? 1.0 : 0.0;
        double newMax = 1 - (assigned / prefsCount);
        double map = mapBetween(min, max, 0, newMax, rank);
        double newMap = 1 - map;

        return student.assignment == null ? 0.0 : newMap;
    }

    /**
     * Calcule la satisfaction d'une université selon ses affectations d'étudiants
     *
     * @param university
     * @return
     */
    public double computeSatisfaction(University university) {
        //Nombre de préférences dans la liste de l'université
        int prefCount = university.prefs.size();

        //Nombre d'étudiants affectés à l'université
        int assignmentsCount = university.assignments.size();

        //La capacité d'accueil de l'université
        int capacity = university.capacity;

        //Le pourcentage de remplissage de l'université
        double pourcentageRemplissage = (double) assignmentsCount / (double) capacity;

        //La somme des meilleurs rangs d'assignations d'étudiants pour l'université en fonction du nombre d'étudiants
        //qui lui sont affectés. Représente le meilleur cas d'affectation d'étudiants possible avec le nombre
        //d'étudiants qui lui sont déjà affectés.
        double minAcc = fact(0, assignmentsCount);

        //La somme des pires rangs d'assignations d'étudiants pour l'université en fonction du nombre d'étudiants
        //qui lui sont affectés. Représente le pire cas d'affectation d'étudiants possible avec le nombre
        //d'étudiants qui lui sont déjà affectés.
        double maxAcc = fact(prefCount - assignmentsCount, prefCount);

        //La somme des rangs des étudiants assignés à l'université, permet de calculer la satisfaction de l'université
        //en fonction des rangs des étudiants qui lui sont affectés.
        double acc = 0;
        for (Student assign :
                university.assignments.values()) {
            acc += university.getRank(assign);
        }

        //Cette valeur représente la nouvelle valeur maximale de satisfaction de l'université en fonction du nombre
        //d'affectations possibles. Permet de mettre en place une valeur de satisfaction minimale à partir du moment
        //où l'université a des affectations.
        double newMax = 1.0 - ((double) assignmentsCount / (double) prefCount);

        //On map la valeur de satisfaction de l'université en fonction des rangs des affectations entre 0 et
        //la valeur minimale de satisfaction.
        double between0And1 = mapBetween(minAcc, maxAcc, 0.0, newMax, acc);

        //On inverse la valeur de satisfaction pour qu'une valeur proche de 1 soit une satisfaction élevée.
        between0And1 = 1 - between0And1;

        //La valeur de satisfaction finale prend en compte le facteur de remplissage de l'université. On multiplie
        //donc la valeur de satisfaction en fonction des rangs d'affectation par le facteur de remplissage.
        return between0And1 * pourcentageRemplissage;
    }

    private double fact(int start, int end) {
        double acc = 0;
        for (int j = start; j < end; j++) {
            acc += j;
        }
        return acc;
    }

    public double mapBetween(double oldMin, double oldMax, double newMin, double newMax, double value) {
        if (newMax == newMin) return 0;
        return (value - oldMin) / (oldMax - oldMin) * (newMax - newMin) + newMin;
    }

    public double computeStudentsSatisfStat() {
        double acc = 0;
        for (Student stud : students
        ) {
            acc += computeSatisfaction(stud);
        }
        return acc / students.size();
    }

    public double computeUniversitiesSatisfStat() {
        double acc = 0;
        for (University univ : universities
        ) {
            acc += computeSatisfaction(univ);
        }
        return acc / universities.size();
    }

    public String getUnivAssigns() {
        StringBuilder builder = new StringBuilder();
        for (University univ :
                universities) {
            builder.append("\nUniv : " + univ.name + "  --> satisfaction : " + computeSatisfaction(univ));
            for (Student assign :
                    univ.assignments.values()) {
                builder.append("\n\t" + assign.name + " --> rank " + univ.getRank(assign));
            }
        }
        return builder.toString();
    }

    public String getStudAssigns() {
        StringBuilder builder = new StringBuilder();
        for (Student stud :
                students) {
            builder.append("\nStudent : " + stud.name + "  --> satisfaction : " + computeSatisfaction(stud));
            if (stud.assignment != null) {
                builder.append("\n\t" + stud.assignment.name + " --> rank " + stud.getAssignmentRank());
            } else {
                builder.append("\nAssigned to NONE");
            }
        }
        return builder.toString();
    }
}
