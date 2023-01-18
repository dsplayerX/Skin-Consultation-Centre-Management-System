package WestminsterSkinConsultationCentre;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    private static final int MAX_DOCTORS = 10; // maximum number of doctors that can be added
    static boolean isTrue = true; // checks condition used to exit menu loop
    static WestminsterSkinConsultationManager manager; // manager that holds the doctors list

    private ArrayList<Doctor> doctorList; // ArrayList to store added doctors

    /**
     * Represent a manager of westminster skin consultation centre
     */
    public WestminsterSkinConsultationManager(){
        this.doctorList = new ArrayList<Doctor>(MAX_DOCTORS);
    }

    public static void main(String[] args) {
        manager = new WestminsterSkinConsultationManager(); // creating a new manager object

        // Asking whether to load doctors from file
        System.out.println("\n > Do you want to load saved doctors? (y/n)");
        Scanner input = new Scanner(System.in);
        String loadOption = input.next();
        if (loadOption.equalsIgnoreCase("y")){
            manager.loadFromFile();
        } else {
            System.out.println(" > Opening a fresh instance...");
        }

        // Main menu loop
        while (isTrue){
            mainMenu(manager);
        }
    }


    /**
     * Adds a doctor to the consultation centre
     *
     * @param scanner the scanner object that is used to get the user inputs
     */
    @Override
    public void addNewDoctor(Scanner scanner){

        // checks whether the maximum number of doctors are already added
        if (doctorList.size() >= MAX_DOCTORS) {
            System.out.println(" > Error: Maximum number of doctors reached.");
            return;
        }

        System.out.println("------------------------------------------");
        System.out.println("-------------Add a new doctor-------------");

        // getting first and surnames
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter surname: ");
        String surname = scanner.nextLine();

        // getting date of birth
        LocalDate dob = null;
        while (dob == null) {
            System.out.println("Enter date of birth (yyyy-MM-dd): ");
            String dobDate = scanner.nextLine();

            // Verifying the entered string matches the date pattern
            if (!(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", dobDate))){
                System.out.println(" > Date should be formatted as \"yyyy-MM-dd\".\n\tyyyy - Year, MM - month, dd - day");
                dobDate = null; // setting dob to null so it would loop again
            } else {
                try {
                    DateTimeFormatter dFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    dob = LocalDate.parse(dobDate, dFormat);

                    // checking if birthday is in the future and handling it
                    if (dob.isAfter(LocalDate.now())){
                        System.out.println(" > Error! Birthday cannot be in the future!");
                        dob = null; // setting dob to null so it would loop again
                    }
                } catch (DateTimeException dte){
                    System.out.println(" > Error! Enter a valid year, month and/or day for the date of birth.");
                } catch (Exception e){
                    System.out.println(" > Error while adding birthday. Try again!."); // in case there are any unknown errors
                }
            }
        }

        // getting mobile number of doc
        String mobNumber = null;
        while (mobNumber == null){
            System.out.println("Enter mobile number: ");
            mobNumber = scanner.nextLine();

            // checking if the entered string matches the given pattern
            if (!(Pattern.matches("^\\d{3}-\\d{3}-\\d{4}$", mobNumber))){
                System.out.println(" > Not a valid phone number!\n" +
                        " > Phone Number should be formatted as \"XXX-XXX-XXXX\".");
                mobNumber = null; // setting back to null so it would loop again
            }
        }

        // getting gender
        System.out.println("Enter gender: ");
        String gender = scanner.nextLine();

        // getting the medical licence number
        System.out.println("Enter medical licence number: ");
        String medLicenseNum = scanner.nextLine();
        for (Doctor doctor : doctorList){
            if (doctor.getMedLicenceNum().equals(medLicenseNum)) {
                System.out.println(" > Error! A doctor with the same medical licence number already exists.");
                System.out.println(" > Going back to main menu...");
                return;
            }
        }

        // getting doctor's specialisation
        System.out.println("Enter specialisation: ");
        String specialisation = scanner.nextLine();

        // creating a new doctor obj and adding doctor to the doctors arraylist
        Doctor doc = new Doctor(firstName, surname, dob, mobNumber, gender, medLicenseNum, specialisation);
        this.doctorList.add(doc);

        // printing doctor details
        System.out.println("\n-------------Details of doctor------------");
        System.out.println("\tName: Dr." + doc.getfName() + " " + doc.getSurname());
        System.out.println("\tMedical Licence No: " + doc.getMedLicenceNum());
        System.out.println("\tSpecialisation: " + doc.getSpecialisation());
        System.out.println("\tGender: " + doc.getGender());
        System.out.println("\tMobile No.: " + doc.getMobNum());
        System.out.println("\tDate of Birth.: " + doc.getDob());
        System.out.println("\n Dr. " + surname + " was successfully added to the centre.");

        // printing the total number of docs in the centre
        System.out.println("\n Total number of doctors in the centre: " + this.doctorList.size());
    }

    /**
     * Deletes a doctor from the consultation centre given the medical licence number
     *
     * @param scanner the scanner object that is used to get the user inputs
     */
    @Override
    public void deleteDoctor(Scanner scanner){

        // checks whether the doctor arraylist is empty and letting user know
        if (this.doctorList.isEmpty()) {
            System.out.println(" > There are no doctors added.");
            System.out.println(" > Going back to main menu...");
            return;
        }

        // getting medical licence number of doctor to remove
        System.out.println("Enter medical licence number: ");
        String inputMedLicense = scanner.nextLine();

        boolean isFound = false; // boolean variable keep track of whether a doctor with matching medical number is in the list

        for (int i = 0; i < this.doctorList.size(); i++){
            String tempMedLicense = this.doctorList.get(i).getMedLicenceNum();
            if (inputMedLicense.equals(tempMedLicense)){
                System.out.println("\n-------------Details of deleted doctor------------");
                System.out.println("\tName: " + this.doctorList.get(i).getfName() + " "+ this.doctorList.get(i).getSurname());
                System.out.println("\tMedical Licence No: " + this.doctorList.get(i).getMedLicenceNum());
                System.out.println("\tSpecialisation: " + this.doctorList.get(i).getSpecialisation());
                System.out.println("\tGender: " + this.doctorList.get(i).getGender());
                System.out.println("\tMobile No.: " + this.doctorList.get(i).getMobNum());
                System.out.println("\tDate of Birth.: " + this.doctorList.get(i).getDob());
                System.out.println("\n > Dr. " + this.doctorList.get(i).getSurname() + " was removed.");
                this.doctorList.remove(i); // removing doctor from the doctors arraylist
                isFound = true;
                break;
            }
        }

            // if a doctor with the matching licence number is not found letting the user know
        if (!isFound){
            System.out.println("\n > A doctor with matching medical licence number was not found.\n" +
                    "Try again with a medical licence number of an existing doctor.");
            System.out.println(" > Going back to main menu...");
        } else {
            System.out.println("\n Total number of doctors in the centre: " + doctorList.size());
        }
    }

    /**
     * Prints a list of doctors sorted alphabetically by surname with details of each doctor
     */
    @Override
    public void viewListOfDocs() {

        // Letting the user know that there are no doctors in the array
        if (this.doctorList.isEmpty()) {
            System.out.println(" > There are no doctors.");
            System.out.println(" > Going back to main menu...");
        } else {
            // cloning the arraylist to temp var so the original list doesn't get mutated
            ArrayList<Doctor> tempDocList = (ArrayList<Doctor>) this.doctorList.clone();

            Collections.sort(tempDocList); // sorting the temporary arraylist

            // printing only the surnames
            System.out.println("\n-------------Sorted list of doctors------------\n");
            for (Doctor doctor : tempDocList) {
                System.out.println(" #  Dr." + doctor.getSurname());
            }

            // printing each doctor with all the added details
            System.out.println("\n-------Sorted list of doctors with details-------");
            for (Doctor doctor : tempDocList) {
                System.out.println("\n # Dr." + doctor.getSurname());
                System.out.println("\tName: " + doctor.getfName() + " " + doctor.getSurname());
                System.out.println("\tMedical Licence No: " + doctor.getMedLicenceNum());
                System.out.println("\tSpecialisation: " + doctor.getSpecialisation());
                System.out.println("\tGender: " + doctor.getGender());
                System.out.println("\tMobile No.: " + doctor.getMobNum());
                System.out.println("\tDate of Birth.: " + doctor.getDob());
                // if the manager has not added consultation times, letting user know
                if (doctor.getAvailableStartDateTime() != null) {
                    System.out.println("\tAvailable date time: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(doctor.getAvailableStartDateTime()));
                    System.out.println("\tAvailable duration: " + doctor.getAvailableDuration() + "hour(s)");
                } else {
                    System.out.println("\tAvailable date time: NOT ADDED");
                    System.out.println("\tAvailable duration: NOT ADDED");
                }
            }
        }
    }


    /**
     * Saves doctor list to a file
     */
    @Override
    public void saveToFile() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("skinconsultationdoctordata.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.doctorList); // writing doctor list arraylist data to file
            oos.close();
            System.out.println("------------------------------------------");
            System.out.println(" > Doctor data was successfully saved.");
            System.out.println("------------------------------------------");
        } catch (IOException ioe) {
            System.out.println(" > Error! Unable to write to file. Please check for file permission and try again");
        } catch (Exception e) {
            System.out.println(" > Error during the saving process. Try again!\nError message:" + e.getMessage());
        }

    }


    /**
     * Loads doctor list from file
     */
    @Override
    public void loadFromFile() {
        this.doctorList.clear(); // clears doctor list before loading from the saved file
        try {
            FileInputStream fis = new FileInputStream("skinconsultationdoctordata.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object tempDocList = ois.readObject(); // loads data from file to a temporary array
            this.doctorList = (ArrayList<Doctor>) tempDocList; // casting temporary array as an arraylist of doctors
            ois.close();
            System.out.println("------------------------------------------");
            System.out.println(" > Doctor data was successfully loaded.");
            System.out.println("------------------------------------------");
        } catch (IOException ioe){
            System.out.println(" > Error! Unable to locate the data file or read the file. \n" +
                    "Make sure the file exists, check for file permission and try again");
        } catch (Exception e){
            System.out.println(" > Error during the loading process. Try again!\nError message:" + e.getMessage());
        }
    }

    /**
     * Adds consult start time and duration to a selected doctor
     * @param scanner the scanner object that is used to get the user inputs
     */
    public void addDocConsultTime(Scanner scanner){
        // if there are no doctors added letting user know
        if (this.doctorList.isEmpty()) {
            System.out.println(" > There are no doctors added.");
            System.out.println(" > Going back to main menu...");
            return;
        }

        // getting medical licence of the doctor whose consult times needs to be added/updated
        System.out.println("Enter medical licence number: ");
        String inputMedLicense = scanner.nextLine();


        boolean isFound = false; // boolean variable keep track of whether a doctor with matching medical number is in the list

        Doctor doc = null;
        // goes through the whole doctor list and if the medical licence matches doctor is found
        for (int i = 0; i < this.doctorList.size(); i++) {
            String tempMedLicense = this.doctorList.get(i).getMedLicenceNum();
            if (inputMedLicense.equals(tempMedLicense)) {
                doc = this.getDoctorList().get(i);
                isFound = true;
                break;
            }
        }

        if (!isFound){ // if doctor is not found letting user know
            System.out.println("\n > A doctor with matching medical licence number was not found.\n" +
                               "Try again with a medical licence number of an existing doctor.");
            System.out.println(" > Going back to main menu...");
            return;
        }

        LocalDateTime docAvailableDT = null;
        while (docAvailableDT == null){
            // getting the date of the consultation -> same as getting the dob
            System.out.println("Enter doctor available date for consultations (yyyy-MM-dd): ");
            String availableDate = scanner.nextLine();
            if (!(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}$", availableDate))) {
                System.out.println(" > Date should be formatted as \"yyyy-MM-dd\".\n\tyyyy - Year, MM - month, dd - day");
                availableDate = null;
                continue;
            }

            // getting the consult start time
            System.out.println("Enter doctor available time for consultations (HH:mm): ");
            String availableTime = scanner.nextLine();
            if (!(Pattern.matches("^\\d{2}:\\d{2}$", availableTime))) {
                System.out.println(" > Time should be formatted as \"HH:mm\".\n\tHH - hours, mm-minutes");
                availableTime = null;
                continue;
            }

            String dateTime = availableDate + " " + availableTime; // creates one string to be parsed to the LocalDateTime object

            try{
                DateTimeFormatter dtformat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                docAvailableDT = LocalDateTime.parse(dateTime, dtformat); // parse string of date time with the format

                // checking if available time is in the past and handling it
                if (docAvailableDT.isBefore(LocalDateTime.now())){
                    System.out.println(" > Error! Doctor available time cannot be in the past!");
                    docAvailableDT = null;
                }
            } catch (DateTimeException dte){
                System.out.println(" > Error! Enter a valid date and time.");
            } catch (Exception e){
                System.out.println(" > Error while adding the date/time. Try again!."); // in case there are any unknown errors
            }
        }

        // getting the duration doctor is available for consultations
        int duration = 0;
        boolean isCorrect = false; // used to continuously prompt user for correct input.
        while (!isCorrect){
            try{
                System.out.println("Enter duration for consultations: ");
                duration = Integer.parseInt(scanner.nextLine());
                if(duration > 0 && duration <= 8) { // conditions for duration
                    isCorrect = true;
                } else {
                    System.out.println("> Duration cannot be zero, negative or more than 8hours!");
                }
            } catch (NumberFormatException nfe){ // if the user input is not an integer
                System.out.println(" > Error! Enter an integer!");
            }
        }

        assert doc != null;

        // setting datetime and duration for the consultation for selected doctor
        doc.setAvailableTimeSlot(docAvailableDT);
        doc.setAvailableDuration(duration);
        System.out.println(" > Consultation time slot for Dr. " + doc.getSurname() + " added successfully!");
    }

    /**
     * Gets all the added doctors in consultation centre
     *
     * @return An arraylist of doctor objects
     */
    public ArrayList<Doctor> getDoctorList() {
        return doctorList;
    }

    /**
     * Main menu for the console application
     *
     * @param manager WestminsterSkinsConsultationManager object created for the manager
     */
    public static void mainMenu(WestminsterSkinConsultationManager manager) {
        System.out.println("\u001B[32m==========================================\u001B[0m"); // added ANSI escape colors
        System.out.println("                \u001B[36m\u001B[1mMain Menu\u001B[0m                 ");
        System.out.println("              Manage Doctors              ");
        System.out.println("\u001B[32m==========================================\u001B[0m");
        System.out.println(" \u001B[36mA\u001B[0m: Add a new doctor");
        System.out.println(" \u001B[36mD\u001B[0m: Delete a doctor");
        System.out.println(" \u001B[36mV\u001B[0m: View list of doctors");
        System.out.println(" \u001B[36mS\u001B[0m: Save to a file");
        System.out.println(" \u001B[36mL\u001B[0m: Load from a file");
        System.out.println(" \u001B[36mC\u001B[0m: Add/update consultation date/time");

        System.out.println("\u001B[32m------------------------------------------\u001B[0m");
        System.out.println(" \u001B[31mQ\u001B[0m: Quit");

        System.out.println("\u001B[32m------------------------------------------\u001B[0m");

        // getting user input of menu option
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select option: ");
        String menuOption = scanner.nextLine();

        // switch case with all the methods to be user by the manager
        switch (menuOption.toLowerCase()) {
            case "a" -> manager.addNewDoctor(scanner);
            case "d" -> manager.deleteDoctor(scanner);
            case "v" -> manager.viewListOfDocs();
            case "s" -> manager.saveToFile();
            case "l" -> {
                // getting confirmation from user that existing data will be lost
                System.out.println(" > Existing doctors will be erased and the saved file will be loaded.\n   Do you wish to proceed? (y/n):");
                String userChoice = scanner.nextLine();
                if (userChoice.equalsIgnoreCase("y")) {
                    manager.loadFromFile();
                } else {
                    System.out.println(" > Going back to main menu...");
                }
            }
            case "c" -> manager.addDocConsultTime(scanner);

            case "q" -> {
                isTrue = false; // breaks the while loop mainMenu() is in and exits the program.
                System.out.println(" > Do you want to save before exiting? (y/n)"); // confirms to save from user
                String userChoice = scanner.nextLine();
                if (userChoice.equalsIgnoreCase("y")) {
                    manager.saveToFile(); // saving to file so the data isn't lost
                }
                System.out.println(" > Quitting...");
            }

            default -> {
                System.out.println(" > Wrong input. Please try again!");
                mainMenu(manager); // call back to mainMenu()
            }
        }
    }
}

