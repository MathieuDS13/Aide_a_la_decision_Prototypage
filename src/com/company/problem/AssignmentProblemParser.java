package com.company.problem;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe permet de lire un format de fichier particulier représentant un problème d'affectation
 * et les différentes parties du problème (étudiants et leurs préférences ainsi que les universités avec leur capacités
 * et leurs préférences)
 *
 * Le format doit être le suivant :
 *
 * $NOM_DE_L'ETUDIANT
 * nom de la préférence de rang 0 > nom de la préférence suivante > nom de la préférence suivante > ...
 * $NOM_DE_L'ETUDIANT_SUIVANT
 * nom de la préférence de rang 0 > nom de la préférence suivante > nom de la préférence suivante > ...
 * &
 * $NOM_DE_L'UNIVERSITE;CAPACITE
 * nom de la préférence de rang 0 > nom de la préférence suivante > nom de la préférence suivante > ...
 *
 * Dans les préférences les noms doivent faire référence aux noms des universités pour les étudiants
 * et inversement pour les universités.
 * $NOM réprésente le symbole $ suivi du nom de l'étudiant (sans espace).
 * le signe & indique le passage à l'autre partie, donc les universités.
 * Pour les universités il est important de préciser sa capacité en suivant son nom du symbole ; et de la valeur de
 * la capacité accolée.
 * Attention il est important de commencer par les étudiants puis de faire les universités
 */

public class AssignmentProblemParser {

    /**
     * La fonction qui permet de lire le fichier passé en paramètre et d'en extraire un problème
     * d'affectation qui est retourné.
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static AssignmentProblem parse(File file) throws Exception {
        Map<String, University> universityMap = new HashMap<>();
        Map<String, Student> studentMap = new HashMap<>();
        Map<String, List<String>> studentsPrefs = new HashMap<>();
        Map<String, List<String>> univsPrefs = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        Student currentStud = null;
        University currentUniversity = null;
        boolean second = false;
        while (line != null) {
            line = line.trim();
            if (line.equals("&")) {
                second = true;
                line = reader.readLine();
                continue;
            }

            if (!second) {
                //On traite le premier élément de l'association == étudiants
                if (line.startsWith("$")) {
                    String name = line.substring(1);
                    Student stud = new Student(name);
                    currentStud = stud;
                    studentMap.put(stud.name, stud);
                    studentsPrefs.putIfAbsent(stud.name, new ArrayList<>());
                } else {
                    String[] prefs = line.split(">");
                    for (String pref :
                            prefs) {
                        if (currentStud != null) studentsPrefs.get(currentStud.name).add(pref);
                    }
                }
            } else {
                //On traite le second élément de l'association == les univresitées
                if (line.startsWith("$")) {
                    String[] vals = line.split(";");
                    int capacity = Integer.parseInt(vals[1]);
                    String name = vals[0].substring(1);
                    University univ = new University(name, capacity);
                    currentUniversity = univ;
                    universityMap.put(univ.name, univ);
                    univsPrefs.putIfAbsent(univ.name, new ArrayList<>());
                } else {
                    String[] prefs = line.split(">");
                    for (String pref :
                            prefs) {
                        if (currentUniversity != null) univsPrefs.get(currentUniversity.name).add(pref);
                    }
                }
            }
            line = reader.readLine();
        }

        return AssignmentProblem.createProblemFromVals(studentMap, universityMap, studentsPrefs, univsPrefs);
    }

}
