package WestminsterSkinConsultationCentre;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runnable class for the user application
 */
public class UserApplication {
    static SecretKey key;
    static WestminsterSkinConsultationManager managerForGUI; // used to get doctor details
    static ArrayList<Consultation> consultations; // store all the consultations
    static ArrayList<Patient> patients; // stores all patients added to the conusltations centre
    static boolean isRunning = true; // used to exit the menu loop on user command

    public static void main(String[] args) {

        // Generating key for encryption of notes and images
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES"); // uses "AES" encryption method
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        assert keyGenerator != null;
        keyGenerator.init(128);
        key = keyGenerator.generateKey();

        // Initializing manager, consultations and patients ArrayLists
        managerForGUI = new WestminsterSkinConsultationManager();
        consultations = new ArrayList<Consultation>();
        patients = new ArrayList<Patient>();

        // Loading doctor list to the manager class to be used in the GUI user application
        managerForGUI.loadFromFile();

        // creating a new instance of the home page gui frame
        new SkinConsultationCentreGUI();

        System.out.println("------------------------------------------");
        System.out.println(" > Loaded GUI application for user...");
        System.out.println("------------------------------------------");

        // running the menu in a loop so user can interact with patient and consult data after the gui loads
        while(isRunning){
            userAppConsoleMenu();
        }
    }


    /**
     * Console main menu used in GUI application
     */
    public static void userAppConsoleMenu() {
        System.out.println("\u001B[32m==========================================\u001B[0m");
        System.out.println("      \u001B[36m\u001B[1mOptions for GUI Applicaation\u001B[0m            ");
        System.out.println("\u001B[32m==========================================\u001B[0m");
        System.out.println(" \u001B[36mG\u001B[0m: Load GUI application");
        System.out.println(" \u001B[36mS\u001B[0m: Save consultations and patients to a file");
        System.out.println(" \u001B[36mL\u001B[0m: Load consultations and patients from file");
        System.out.println(" \u001B[36mC\u001B[0m: Clear patient and consultation data");
        System.out.println("\u001B[32m------------------------------------------\u001B[0m");
        System.out.println(" \u001B[31mQ\u001B[0m: Quit");

        System.out.println("\u001B[32m------------------------------------------\u001B[0m");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select option: ");
        String menuOption = scanner.nextLine();
        switch (menuOption.toLowerCase()) {
            case "s" -> {
                savePatientData();
                saveConsultData();
            }

            case "l" -> {
                loadPatientData();
                loadConsultData();
            }

            case "c" -> {
                consultations.clear();
                patients.clear();
                System.out.println(" > Data cleared successfully!");
            }

            case "q" -> {
                isRunning = false; // breaks the while loop user menu is in and exits the program.
                System.out.println(" > Do you want to save before exiting? (y/n)");
                String userChoice = scanner.nextLine();
                if (userChoice.equalsIgnoreCase("y")) {
                    savePatientData();
                    saveConsultData(); // saving to file so the data isn't lost
                }
                System.out.println(" > Quitting...");
            }

            case "p" -> { // hidden option to view number of patients and consultations
                System.out.println("No of Patients: " + patients.size());
                System.out.println("No of Consultations: " + consultations.size());
            }

            case "g" -> {
                new SkinConsultationCentreGUI();
                System.out.println("------------------------------------------");
                System.out.println(" > Loaded GUI application for user...");
                System.out.println("------------------------------------------");
            }

            default -> {
                System.out.println(" > Wrong input. Please try again!");
                userAppConsoleMenu(); // call back to mainMenu()
            }
        }
    }


    /**
     * Saves patient data to a file
     */
    public static void savePatientData() {
        FileOutputStream fos1 = null;
        try {
            fos1 = new FileOutputStream("patientdata.dat");
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(patients);
            oos1.close();
            System.out.println("------------------------------------------");
            System.out.println(" > Patient data was successfully saved.");
            System.out.println("------------------------------------------");
        } catch (IOException ioe) {
            System.out.println(" > Error! Unable to write to file. Please check for file permission and try again");
        } catch (Exception e) {
            System.out.println(" > Error during the saving process. \nError message:" + e.getMessage());
        }

    }


    /**
     * Loads patient data from file
     */
    public static void loadPatientData() {
        try {
            FileInputStream fis1 = new FileInputStream("patientdata.dat");
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            Object tempPatList = ois1.readObject();
            ois1.close();
            patients = (ArrayList<Patient>) tempPatList;
            System.out.println("------------------------------------------");
            System.out.println(" > Patient data was successfully loaded.");
            System.out.println("------------------------------------------");
        } catch (IOException ioe){
            System.out.println(" > Error! Unable to locate the patients data files or read the files. \n");
        } catch (Exception e){
            System.out.println(" > Error during the loading process. \nError message:" + e.getMessage());
        }
    }


    /**
     * Saves consultations data to file
     */
    public static void saveConsultData() {
        FileOutputStream fos2 = null;
        try {
            fos2 = new FileOutputStream("consultationsdata.dat");
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos2.writeObject(consultations);
            oos2.close();
            System.out.println("------------------------------------------");
            System.out.println(" > Consultations data was successfully saved.");
            System.out.println("------------------------------------------");
        } catch (IOException ioe) {
            System.out.println(" > Error! Unable to write to files. Please check for file permission and try again.");
        } catch (Exception e) {
            System.out.println(" > Error during the saving process. Try again!\nError message:" + e.getMessage());
        }

    }


    /**
     * Loads consultations data from file
     */
    public static void loadConsultData() {
        try {
            FileInputStream fis2 = new FileInputStream("consultationsdata.dat");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            Object tempConsultList = ois2.readObject();
            consultations = (ArrayList<Consultation>) tempConsultList;
            ois2.close();
            System.out.println("------------------------------------------");
            System.out.println(" > Consultations data was successfully loaded.");
            System.out.println("------------------------------------------");
        } catch (IOException ioe){
            System.out.println(" > Error! Unable to locate the consultations data files or read the files. \n");
        } catch (Exception e){
            System.out.println(" > Error during the loading process. Try again!\nError message:" + e.getMessage());
        }
    }
}
