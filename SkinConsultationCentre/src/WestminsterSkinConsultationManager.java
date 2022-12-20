import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    static ArrayList<Doctor> doctorList = new ArrayList<Doctor>(10);

    static boolean isTrue = true; // checks condition for mainMenu() while loop exit

    public static void main(String[] args) {

        while (isTrue) {
            mainMenu();
        }
    }


    public static void addNewDoctor(){
        System.out.println("------------------------------------------");
        System.out.println("-------------Add a new doctor-------------");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.next();
        System.out.print("Enter surname: ");
        String surname = scanner.next();

        System.out.println("Enter date of birth. ");
        LocalDate dob = null;
        while (dob == null) {
            System.out.println("Year: ");
            String dobYear = scanner.next();
            System.out.println("Month: ");
            String dobMonth = scanner.next();
            System.out.println("Day: ");
            String dobDay = scanner.next();
            try {
                dob = LocalDate.of(Integer.parseInt(dobYear), Integer.parseInt(dobMonth), Integer.parseInt(dobDay));
            } catch (Exception e) {
                System.out.println(" > Enter a valid year, month and day for the date of birth.");
            }
        }

        System.out.println("Enter mobile number: ");
        String mobNumber = scanner.next();

        System.out.print("Enter medical licence number: ");
        String medLicenseNum = scanner.next();

        System.out.print("Enter specialisation: ");
        String specialisation = scanner.next();

        Doctor doc = new Doctor(firstName, surname, dob, mobNumber, medLicenseNum, specialisation);
        doctorList.add(doc);
        System.out.println("\n-------------Details of doctor------------");
        System.out.println("\tName: Dr." + firstName + " " + surname);
        System.out.println("\tMedical Licence No: " + medLicenseNum);
        System.out.println("\tSpecialisation: " + specialisation);
        System.out.println("\tMobile No.: " + mobNumber);
        System.out.println("\tDate of Birth.: " + dob);
        System.out.println("\n Dr. " + surname + " was added to the centre.");
        System.out.println("\n Total number of doctors in the centre: " + doctorList.size());
    }


    public static void deleteDoctor(){
        System.out.println("Enter medical licence number: ");
        Scanner scanner = new Scanner(System.in);
        String inputMedLicense = scanner.next();

        boolean isFound = false; // boolean variable keep track of whether a doctor with matching medical number is in the list

        for (int i = 0; i < doctorList.size(); i++){
            String tempMedLicense = doctorList.get(i).getMedLicenceNum();
            if (inputMedLicense.equals(tempMedLicense)){
                System.out.println("\n-------------Details of doctor------------");
                System.out.println("\tName: Dr." + doctorList.get(i).getfName() + " "+ doctorList.get(i).getSurname());
                System.out.println("\tMedical Licence No: " + doctorList.get(i).getMedLicenceNum());
                System.out.println("\tSpecialisation: " + doctorList.get(i).getSpecialisation());
                System.out.println("\tMobile No.: " + doctorList.get(i).getMobNum());
                System.out.println("\tDate of Birth.: " + doctorList.get(i).getDob());
                System.out.println("\n Dr. " + doctorList.get(i).getSurname() + " was removed.");
                doctorList.remove(i); // removing doctor from the doctors arraylist
                isFound = true;
                break;
            }
        }
        if (!isFound){
            System.out.println("\n > A doctor with matching medical licence number was not found.");
            System.out.println(" > Going back to main menu...");
        } else {
            System.out.println("\n Total number of doctors in the centre: " + doctorList.size());
        }
    }


    public static void printListOfDocs(){
        Collections.sort(doctorList);
        System.out.println("\n-------------Sorted list of doctors------------");
        for (int i = 0; i < doctorList.size(); i++){
            System.out.println(" #" + (i+1) + " Dr." + doctorList.get(i).getSurname());
        }
        System.out.println("\n-------Sorted list of doctors with details-------");
        for (int i = 0; i < doctorList.size(); i++){
            System.out.println("\n #" + (i+1));
            System.out.println("\tName: Dr." + doctorList.get(i).getfName() + " " + doctorList.get(i).getSurname());
            System.out.println("\tMedical Licence No: " + doctorList.get(i).getMedLicenceNum());
            System.out.println("\tSpecialisation: " + doctorList.get(i).getSpecialisation());
            System.out.println("\tMobile No.: " + doctorList.get(i).getMobNum());
            System.out.println("\tDate of Birth.: " + doctorList.get(i).getDob());
        }
    }


    public static void saveToFile() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("skinconsultationdoctordata.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(doctorList);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("------------------------------------------");
        System.out.println(" > Doctor data was successfully saved.");
        System.out.println("------------------------------------------");
    }


    public static void loadFromFile() {
        System.out.println(" > Existing doctors will be erased and the saved file will be loaded.\n   Do you wish to proceed? (y/n):");
        Scanner scanner = new Scanner(System.in);
        String userChoice = scanner.next();
        if (userChoice.equalsIgnoreCase("y")) {
            doctorList.clear();
            try {
                FileInputStream fis = new FileInputStream("skinconsultationdoctordata.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object tempDocList = ois.readObject();
                doctorList = (ArrayList<Doctor>) tempDocList;
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("------------------------------------------");
            System.out.println(" > Doctor data was successfully loaded.");
            System.out.println("------------------------------------------");
        } else {
            System.out.println(" > Going back to main menu...");
        }

    }


    public static void mainMenu() {
        System.out.println("==========================================");
        System.out.println("                Main Menu                 ");
        System.out.println("------------------------------------------");
        System.out.println("              Manage Doctors              ");
        System.out.println("==========================================");
        System.out.println(" A: Add a new doctor");
        System.out.println(" D: Delete a doctor");
        System.out.println(" P: Print the list of the doctors");
        System.out.println(" S: Save to a file");
        System.out.println(" L: Load from a file");
        System.out.println(" Q: Quit");
        System.out.println("------------------------------------------");

        Scanner input = new Scanner(System.in);
        System.out.print("Select option: ");
        String menuOption = input.next();
        switch (menuOption.toLowerCase()) {
            case "a" -> addNewDoctor();
            case "d" -> deleteDoctor();
            case "p" -> printListOfDocs();
            case "s" -> saveToFile();
            case "l" -> loadFromFile();
            case "q" -> {
                isTrue = false; // breaks the while loop mainMenu() is in and exits the program.
                System.out.println(" > Quitting...");
            }
            default -> {
                System.out.println(" > Wrong input. Please try again!");
                mainMenu(); // call back to mainMenu()
            }
        }
    }
}

