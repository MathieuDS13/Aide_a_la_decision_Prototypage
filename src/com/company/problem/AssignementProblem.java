package com.company.problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AssignementProblem {
    List<Student> students;
    List<University> universities;
    Random random = new Random();

    private AssignementProblem() {
        this.students = new ArrayList<>();
        this.universities = new ArrayList<>();
    }

    public static AssignementProblem createRandomProblem(int studCount, int univCount) {
        AssignementProblem problem = new AssignementProblem();
        problem.createStudents(studCount);
        problem.createUnivs(univCount);
        problem.createPrefAssociations();
        problem.updateCapacitiesToFeatStudents();
        return problem;
    }

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

    private void createUnivs(int univCount) {
        for (int i = 0; i < univCount; i++) {
            University univ = new University("univ_" + i);
            universities.add(univ);
        }
    }

    private void createStudents(int studCount) {
        for (int i = 0; i < studCount; i++) {
            Student student = new Student("etu_" + i);
            students.add(student);
        }
    }

    public void log() {
        System.out.println(getDatasetToString());
    }

    public String getDatasetToString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PROBLEM STATS :");
        builder.append("\n" + universities.size() + " Universities");
        builder.append("\n" + students.size() + " Students");

        for (University univ :
                universities) {
            builder.append("\n" + univ.name + " " +univ.capacity+ " place(s)");
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

    public int getStudentsCount() {
        return students.size();
    }

    public int getUnivsCount() {
        return universities.size();
    }

}
