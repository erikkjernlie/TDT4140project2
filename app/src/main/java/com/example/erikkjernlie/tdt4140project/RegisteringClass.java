package com.example.erikkjernlie.tdt4140project;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Jørgen on 09.02.2017.
 */

public class RegisteringClass {

    private double averageGrade;
    private HashMap<String, Double> fagbase = new HashMap<>();
    private List<String> yes_words = Arrays.asList("yes", "yeah", "ja", "japp", "jepp");
    private List<String> no_words = Arrays.asList("no", "nopp", "nope", "nop", "noo","nei");
    public Date today = new Date();
    private char gender;

    public RegisteringClass() {
        init_fagbase();
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    // Regner ut alderspoeng
    protected int agePoints(int birthYear) {
        if (birthYear <= 1900) {
            throw new IllegalArgumentException();
        }
        int points = 0;
        points = 2 * ((today.getYear() - birthYear + 1900) - 19);
        System.out.println(points);
        if (points > 8) {
            points = 8;
        } else if (points < 0) {
            points = 0;
        }
        return points;
    }

    protected void validGrade(int grade) {
        if (grade < 1 || grade > 6) {
            throw new IllegalArgumentException();
        }
    }

    protected void validGender(char gender) {
        if (!(gender == 'M' || gender == 'F')) {
            throw new IllegalArgumentException("Unvalid gender!");
        }
    }

    public Map<String, Double> getFagbase() {
        return fagbase;
    }

    // Initialiserer fagbasen
    protected void init_fagbase() {
        //1. VGS(fellesfag)
        fagbase.put("Engelsk", (double)0);
        fagbase.put("Naturfag", (double)0);
        fagbase.put("Geografi", (double)0);
        fagbase.put("Matematikk 1T", (double)0);
        fagbase.put("Matematikk 1P", (double)0);
        fagbase.put("Samfunnsfag", (double)0);

        //2. VGS (fellesfag)
        fagbase.put("Fremmedspråk nivå 1", (double)0);

        //3.VGS (fellesfag)
        fagbase.put("Historie", (double)0);
        fagbase.put("Norsk muntlig", (double)0);
        fagbase.put("Norsk skriftlig", (double)0);
        fagbase.put("Nynorsk", (double)0);
        fagbase.put("Religion og etikk", (double)0);
        fagbase.put("Kroppsøving", (double)0);

        //Valgfag
        fagbase.put("Matematikk S1", (double)0.5);
        fagbase.put("Matematikk S2", (double)0.5);
        fagbase.put("Matematikk R1", (double)0.5);
        fagbase.put("Matematikk R2", (double)1);
        fagbase.put("Fysikk 1", (double)0.5);
        fagbase.put("Fysikk 2", (double)1);
        fagbase.put("Kjemi 1", (double)0.5);
        fagbase.put("Kjemi 2", (double)0.5);
        fagbase.put("Biologi 1", (double)0.5);
        fagbase.put("Biologi 2", (double)0.5);
        fagbase.put("Geofag 1", (double)0.5);
        fagbase.put("Geofag 2", (double)0.5);
        fagbase.put("Informasjonsteknologi 1", (double)0.5);
        fagbase.put("Informasjonsteknologi 2", (double)0.5);
        fagbase.put("Teknologi og forskningslære 1", (double)0.5);
        fagbase.put("Teknologi og forskningslære 2", (double)0.5);
        fagbase.put("Teknologi og forskningslære 2", (double)0.5);
        fagbase.put("Fremmedspråk morsmål", (double)0.5);
        fagbase.put("Gresk", (double)0.5);
        fagbase.put("Latin", (double)0.5);
        fagbase.put("Fremmedspråk nivå 3", (double)1);



    }

//    private void listOfSubjects() {
//        System.out.println("Here is a list of subjects: ");
//        for (Map.Entry<String,Double> entry : fagbase.entrySet()) {
//            String key = entry.getKey();
//            double value = entry.getValue();
//            System.out.println(key);
//        }
//    }

    private void welcome_message() {
        System.out.println("Hey and welcome to uniBOT - The interactive bot who can help you with" +
                "your choice of education!\n\n" +
                "At first, I need you to submit your grades for me, so that i can calculate your average grade and" +
                "find possible study lines for you");
    }

    // TODO, implement agePoints and girl points in input_scan

    // TODO, change the validGender-method to handle lower cases also

    // Skanner inn info om brukeren med tanke på karaktersnitt
    public void input_scan() {
        welcome_message();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type in your gender('M' for male and 'F' for female):");
        boolean yeah = false;
        String input = scanner.nextLine();
        while (!yeah) {
            try {
                validGender(input.charAt(0));
                setGender(input.charAt(0));
                yeah = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid gender!");
            } finally {
                if (!yeah) {
                    input = scanner.nextLine();
                }
            }
        }
        System.out.println("Have you calculated your average grade in beforehand?");
        input = scanner.nextLine();
        if (yes_words.contains(input.toLowerCase().trim())) {
            System.out.println("Please write your average grade");
            input = scanner.nextLine();
            this.averageGrade = Double.valueOf(input);
        } else {
            System.out.println("Write in your grades with linebreak between, end by writing SUBMIT");
            input = scanner.nextLine();
            int counter = 0;
            while (!input.toLowerCase().trim().equals("submit")) {
                try {
                    validGrade(Integer.valueOf(input));
                    averageGrade += Integer.valueOf(input);
                    counter++;
                } catch (NumberFormatException e) {
                    System.err.println("You have to write a grade from 1 to 6 or SUBMIT if you are done!");
                } catch (IllegalArgumentException f) {
                    System.err.println("You have to write a grade from 1 to 6 or SUBMIT if you are done!");
                }
                finally {
                    input = scanner.nextLine();
                }
            }
            System.out.println("Do you have any extra points?");
            input = scanner.nextLine();
            if (yes_words.contains(input.toLowerCase().trim())) {
                System.out.println("Which 'extra-points'-subjects did you take? Write SUBMIT when you are done:");
                input = scanner.nextLine();
                averageGrade = averageGrade / counter;
                while (!input.toLowerCase().trim().equals("submit")) {
                    String fag = input;
                    try {
                        averageGrade += fagbase.get(fag.trim())/10;
                    } catch (NullPointerException e){
                        System.err.println("You have to write a subject!");
//                        listOfSubjects();
                    }
                    input = scanner.nextLine();
                }
            } else {
                averageGrade = averageGrade / counter;
            }
            System.out.println("Have you completed any of the following?:");
            System.out.println("County college\nMilitary duty\nCivilian service\n"
                    + "Higher education");
            input = scanner.nextLine();
            if (yes_words.contains(input.toLowerCase().trim())) {
                averageGrade += 2/10;
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        RegisteringClass one = new RegisteringClass();
        System.out.println(one.agePoints(1901));
        one.input_scan();
        System.out.println("Your average grade is:" + one.getAverageGrade());
    }
}
