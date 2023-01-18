package WestminsterSkinConsultationCentre;

import java.util.Scanner;

/**
 * Interface class that has all the methods to be used by a manager
 *
 */
public interface SkinConsultationManager {

    /**
     * Adds a new doctor
     */
    void addNewDoctor(Scanner scanner);

    /**
     * Deletes a doctor
     */
    void deleteDoctor(Scanner scanner);

    /**
     * Prints list of doctors sorted alphabetically by surname
     */
    void viewListOfDocs();

    /**
     * Saves doctor data to a file
     */
    void saveToFile();

    /**
     * Loads doctor data from a file
     */
    void loadFromFile();
}