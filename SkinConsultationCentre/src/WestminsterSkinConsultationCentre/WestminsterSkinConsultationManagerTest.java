package WestminsterSkinConsultationCentre;

import org.junit.Test;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterSkinConsultationManagerTest{

    @Test
    public void test1_AddDoctor() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testInput = "John\n" +
                           "Doe\n" +
                           "1999-01-01\n" +
                           "011-223-3445\n" +
                           "Male\n" +
                           "11111\n" +
                           "MedicalDermatology\n";

        LocalDate dob = LocalDate.parse("1999-01-01");
        Scanner scanner = new Scanner(testInput);

        // Act
        int numDoctorsBefore = manager.getDoctorList().size();
        manager.addNewDoctor(scanner);
        int numDoctorsAfter = manager.getDoctorList().size();

        Doctor doctor = manager.getDoctorList().get(manager.getDoctorList().size()-1); // getting the last added doc

        // Assert
        Assert.assertEquals(numDoctorsBefore + 1, numDoctorsAfter); // testing whether the num of doctors went up by 1

        Assert.assertEquals("John",doctor.getfName());
        Assert.assertEquals("Doe", doctor.getSurname());
        Assert.assertEquals(dob, doctor.getDob());
        Assert.assertEquals("011-223-3445", doctor.getMobNum());
        Assert.assertEquals("Male",doctor.getGender());
        Assert.assertEquals("11111",doctor.getMedLicenceNum());
        Assert.assertEquals("MedicalDermatology", doctor.getSpecialisation());
    }


    @Test
    public void test2_DeleteDoctor() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testSample = """
                John
                Doe
                1999-01-01
                011-223-3445
                Male
                11111
                Medical Dermatology
                """;

        manager.addNewDoctor(new Scanner(testSample));
        //
        String testMedLiInput = "11111";
        Scanner scanner = new Scanner(testMedLiInput);

        // Act
        int numDoctorsBefore = manager.getDoctorList().size();
        manager.deleteDoctor(scanner);
        int numDoctorsAfter = manager.getDoctorList().size();

        // Assert
        Assert.assertEquals(numDoctorsBefore - 1, numDoctorsAfter); // testing whether the num of doctors went down by 1
    }


    @Test
    public void test3_DoctorsListAlphabeticallyOrderedBySurname() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testSample1 = """
                John
                Zeera
                1999-01-01
                011-111-1111
                Male
                11111
                Paediatric Dermatology
                """;
        String testSample2 = """
                Jane
                Abba
                1999-02-02
                022-222-2222
                Female
                22222
                Cosmetic Dermatology
                """;
        String testSample3 = """
                John
                Frita
                1999-03-03
                033-333-3333
                Male
                33333
                Medical Dermatology
                """;

        manager.addNewDoctor(new Scanner(testSample1));
        manager.addNewDoctor(new Scanner(testSample2));
        manager.addNewDoctor(new Scanner(testSample3));

        // Initial doctors arrangement
        Doctor initDoc1 = manager.getDoctorList().get(0);
        Doctor initDoc2 = manager.getDoctorList().get(1);
        Doctor initDoc3 = manager.getDoctorList().get(2);

        // Act
        Collections.sort(manager.getDoctorList());

        // Doctors after sorting
        Doctor expectDoc1 = manager.getDoctorList().get(0);
        Doctor expectDoc2 = manager.getDoctorList().get(1);
        Doctor expectDoc3 = manager.getDoctorList().get(2);

        // Expected results
        //  > Abra, Frita, Zeera (in order, after sorting)

        // Asserting by matching initial doctors with the doctors after the sorting
        Assert.assertEquals(expectDoc1, initDoc2);
        Assert.assertEquals(expectDoc2, initDoc3);
        Assert.assertEquals(expectDoc3, initDoc1);
    }


    @Test
    public void test4_ViewListOfDocsSorted() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        String testSample1 = """
                John
                Zeera
                1999-01-01
                011-111-1111
                Male
                11111
                Paediatric Dermatology
                """;
        String testSample2 = """
                Jane
                Abba
                1999-02-02
                022-222-2222
                Female
                22222
                Cosmetic Dermatology
                """;
        String testSample3 = """
                John
                Frita
                1999-03-03
                033-333-3333
                Male
                33333
                Medical Dermatology
                """;

        manager.addNewDoctor(new Scanner(testSample1));
        manager.addNewDoctor(new Scanner(testSample2));
        manager.addNewDoctor(new Scanner(testSample3));

        // Act
        // redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        manager.viewListOfDocs(); // running the method to print list of docs to console

        System.out.flush();
        System.setOut(old);

        String expectedString = """
                -------------Sorted list of doctors------------

                 #  Dr.Abba
                 #  Dr.Frita
                 #  Dr.Zeera

                -------Sorted list of doctors with details-------

                 # Dr.Abba
                \tName: Jane Abba
                \tMedical Licence No: 22222
                \tSpecialisation: Cosmetic Dermatology
                \tGender: Female
                \tMobile No.: 022-222-2222
                \tDate of Birth.: 1999-02-02
                \tAvailable date time: NOT ADDED
                \tAvailable duration: NOT ADDED

                 # Dr.Frita
                \tName: John Frita
                \tMedical Licence No: 33333
                \tSpecialisation: Medical Dermatology
                \tGender: Male
                \tMobile No.: 033-333-3333
                \tDate of Birth.: 1999-03-03
                \tAvailable date time: NOT ADDED
                \tAvailable duration: NOT ADDED

                 # Dr.Zeera
                \tName: John Zeera
                \tMedical Licence No: 11111
                \tSpecialisation: Paediatric Dermatology
                \tGender: Male
                \tMobile No.: 011-111-1111
                \tDate of Birth.: 1999-01-01
                \tAvailable date time: NOT ADDED
                \tAvailable duration: NOT ADDED""";


        // Assert
        Assert.assertEquals(expectedString, baos.toString().trim().replace("\r",""));
    }


    @Test
    public void test5_SaveToFile() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testSample1 = """
                John
                Zeera
                1999-01-01
                011-111-1111
                Male
                11111
                Paediatric Dermatology
                """;
        String testSample2 = """
                Jane
                Abba
                1999-02-02
                022-222-2222
                Female
                22222
                Cosmetic Dermatology
                """;
        String testSample3 = """
                John
                Frita
                1999-03-03
                033-333-3333
                Male
                33333
                Medical Dermatology
                """;

        // Adding doctors to be saved
        manager.addNewDoctor(new Scanner(testSample1));
        manager.addNewDoctor(new Scanner(testSample2));
        manager.addNewDoctor(new Scanner(testSample3));

        // Act
        // redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        manager.saveToFile(); // running the method to save doctor data

        System.out.flush();
        System.setOut(old); // setting back the out to default, System.out

        String expectedResult = "------------------------------------------\n" +
                                " > Doctor data was successfully saved.\n" +
                                "------------------------------------------";
        // Assert
        Assert.assertEquals(expectedResult, baos.toString().trim().replace("\r",""));
    }


    @Test
    public void test6_LoadFromFile() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        // Act
          // redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        manager.loadFromFile(); // running the method to save doctor data

        System.out.flush();
        System.setOut(old); // setting back the out to default, System.out

        String expectedResult = "------------------------------------------\n" +
                                " > Doctor data was successfully loaded.\n" +
                                "------------------------------------------";
        // Assert
        Assert.assertEquals(expectedResult, baos.toString().trim().replace("\r",""));
    }


    @Test
    public void test8_EnterInvalidDob() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testInput = "John\n" +
                           "Doe\n" +
                           "APPLE\n" + // Entering invalid year
                           "40\n" + // Entering invalid month
                           "tomato\n" + // Entering invalid day
                           "1999-01-01\n" + // Entering valid birthday
                           "011-223-3445\n" +
                           "Male\n" +
                           "11111\n" +
                           "Medical Dermatology\n";

        LocalDate dob = LocalDate.parse("1999-01-01");
        Scanner scanner = new Scanner(testInput);

        // Act
        manager.addNewDoctor(scanner);

        // Assert
        Assert.assertEquals(manager.getDoctorList().get(0).getDob(), dob);
    }


    @Test
    public void test9_EnterAFutureDateForDob() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testInput = "John\n" +
                           "Doe\n" +
                           "2070-07-07\n" + // Entering a future date
                           "1999-01-01\n" + // Entering valid date
                           "011-223-3445\n" +
                           "Male\n" +
                           "11111\n" +
                           "Medical Dermatology\n";

        LocalDate dob = LocalDate.parse("1999-01-01");
        Scanner scanner = new Scanner(testInput);

        // Act
        manager.addNewDoctor(scanner);

        // Assert
        Assert.assertEquals(manager.getDoctorList().get(0).getDob(), dob);
    }


    @Test
    public void test10_EnterInvalidPhoneNumber() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testInput = "John\n" +
                           "Doe\n" +
                           "1999-01-01\n" +
                           "PHONENUMBERHERE\n" +  // Entering invalid phone number (Letters and less than 10 digits)
                           "ABCDEFGHIJ\n" +       // Entering invalid phone number (Exactly 10 digits but letters)
                           "1234\n" +             // Entering a number with less than 10 digits
                           "0111111111\n" +       // Entering a phone number without the dashes (incorrect format0
                           "011-111-1111\n" +     // Entering valid phone number
                           "Male\n" +
                           "11111\n" +
                           "Medical Dermatology\n";

        String mobNumber = "011-111-1111";
        Scanner scanner = new Scanner(testInput);

        // Act
        manager.addNewDoctor(scanner);

        // Assert
        Assert.assertEquals(manager.getDoctorList().get(0).getMobNum(), mobNumber);
    }


    @Test
    public void test11_AddNewDocWhenMaxDocsReached() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        int maxNoOfDocs = 10;
        // Adding the max number of doctors
        for (int i = 0; i < maxNoOfDocs; i++){
            String testSample = "John\n" +
                                "Doe\n" +
                                "1999-01-01\n" +
                                "011-223-3445\n" +
                                "Male\n" +
                                "0000" + i + "\n" + // no duplicate medical licence numbers
                                "Medical Dermatology\n";

            manager.addNewDoctor(new Scanner(testSample));
        }

        // Act
        int numDoctorsBefore = manager.getDoctorList().size();

          // Trying to add a new doctor
        String testNewDoctor = "John\n" +
                            "Doe\n" +
                            "1999-01-01\n" +
                            "011-223-3445\n" +
                            "Male\n" +
                            "11111\n" +
                            "Medical Dermatology\n";

        // redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        manager.addNewDoctor(new Scanner(testNewDoctor));

        System.out.flush();
        System.setOut(old);
        System.out.println(baos); // Printing caught print stream to visualize output

        int numDoctorsAfter = manager.getDoctorList().size();

        String expectedResult = "> Error: Maximum number of doctors reached.";
        // Assert
        Assert.assertEquals(numDoctorsBefore, numDoctorsAfter); // testing that the number of docs didn't change
        Assert.assertEquals(expectedResult, baos.toString().trim().replace("\r",""));
    }


    @Test
    public void test12_DeleteDocWhenNoDocsAdded() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testMedLiInput = "11111";
        Scanner scanner = new Scanner(testMedLiInput);


        // Act
        int numDoctorsBefore = manager.getDoctorList().size();

        manager.deleteDoctor(scanner);

        int numDoctorsAfter = manager.getDoctorList().size();

        // Assert
        Assert.assertEquals(numDoctorsBefore, numDoctorsAfter); // testing that the number of docs didn't change
    }


    @Test
    public void test13_EnterWrongMedLiNumWhenDeleting() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testNewDoctor = "John\n" +
                               "Doe\n" +
                               "1999-01-01\n" +
                               "011-223-3445\n" +
                               "Male\n" +
                               "11111\n" +
                               "Medical Dermatology\n";
        manager.addNewDoctor(new Scanner(testNewDoctor));

        String testMedLiInput = "22222"; // giving a wrong/non-existent medical licence number as input
        Scanner scanner = new Scanner(testMedLiInput);


        // Act
        int numDoctorsBefore = manager.getDoctorList().size();

        manager.deleteDoctor(scanner);

        int numDoctorsAfter = manager.getDoctorList().size();

        // Assert
        Assert.assertEquals(numDoctorsBefore, numDoctorsAfter); // testing that the number of docs didn't change
    }


    @Test
    public void test14_EnterDuplicateMedLiNumberWhenAdding() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

           // Adding the initial doctor
        String testDoctor1 = "John\n" +
                               "Doe\n" +
                               "1999-01-01\n" +
                               "011-223-3445\n" +
                               "Male\n" +
                               "11111\n" +
                               "Medical Dermatology\n";
        manager.addNewDoctor(new Scanner(testDoctor1));

        // Act
        int numDoctorsBefore = manager.getDoctorList().size();

        String testDoctor2 = "Jane\n" +
                             "Doe\n" +
                             "1995-09-09\n" +
                             "011-987-6543\n" +
                             "Female\n" +
                             "11111\n" + // Same medical licence number as the already added doctor.
                             "Cosmetic Dermatology\n";

          // redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        manager.addNewDoctor(new Scanner(testDoctor2));

        System.out.flush();
        System.setOut(old);
        System.out.println(baos); // Printing caught print stream to visualize output

        int numDoctorsAfter = manager.getDoctorList().size();

        String expectedResult = "------------------------------------------\n" +
                                "-------------Add a new doctor-------------\n" +
                                "Enter first name:\n" +
                                "Enter surname: \n" +
                                "Enter date of birth (yyyy-MM-dd): \n" +
                                "Enter mobile number: \n" +
                                "Enter gender: \n" +
                                "Enter medical licence number: \n" +
                                " > Error! A doctor with the same medical licence number already exists.\n" +
                                " > Going back to main menu...";
        // Assert
        Assert.assertEquals(numDoctorsBefore, numDoctorsAfter); // testing that the number of docs didn't change
        Assert.assertEquals(expectedResult, baos.toString().trim().replace("\r",""));

    }


    @Test
    public void test15_PrintListWithNoDocs() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        // Act
          // redirect console output to a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        manager.viewListOfDocs(); // running the method without adding any doctors

        System.out.flush();
        System.setOut(old);
        System.out.println(baos); // Printing caught print stream to visualize output

        String expectedResult = "> There are no doctors.\n" +
                                " > Going back to main menu...";
        // Assert
        Assert.assertEquals(expectedResult, baos.toString().trim().replace("\r",""));
    }

    @Test
    public void test16_AddingConsultationDateTimes() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testDoctor = "John\n" +
                "Doe\n" +
                "1999-01-01\n" +
                "011-223-3445\n" +
                "Male\n" +
                "11111\n" +
                "Medical Dermatology\n";
        manager.addNewDoctor(new Scanner(testDoctor));

        String dateTimeInput = """
                11111
                2023-03-03
                10:30
                5
                """;

        LocalDateTime dateTime = LocalDateTime.of(2023,03,03,10,30);
        int duration = 5;

        // Act
        manager.addDocConsultTime(new Scanner(dateTimeInput));

        // Assert
        Doctor doc = manager.getDoctorList().get(manager.getDoctorList().size()-1);
        Assert.assertEquals(dateTime, doc.getAvailableStartDateTime());
        Assert.assertEquals(duration, doc.getAvailableDuration());
    }

    @Test
    public void test17_WrongInputsConsultationDateTimes() {
        // Arrange
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

        String testDoctor = "John\n" +
                "Doe\n" +
                "1999-01-01\n" +
                "011-223-3445\n" +
                "Male\n" +
                "11111\n" +
                "Medical Dermatology\n";
        manager.addNewDoctor(new Scanner(testDoctor));

        String dateTimeInput = "11111\n" + // CORRECT MED LICENCE NO
                               "AAAA-03-99\n" + // invalid date
                               "20230303\n" + // invalid date format
                               "2020-01-01\n" + // past date as consultation date
                               "2023-03-03\n" + // CORRECT DATE
                               "1030\n" + // invalid time format
                               "2023-03-03\n" + // > entering correct date again
                               "99:99\n" + // invalid time
                               "2023-03-03\n" + // > entering correct date again
                               "10:30\n" + // CORRECT TIME
                               "5\n"; // CORRECT DURATION

        LocalDateTime dateTime = LocalDateTime.of(2023,03,03,10,30);
        int duration = 5;

        // Act
        manager.addDocConsultTime(new Scanner(dateTimeInput));

        // Assert
        Doctor doc = manager.getDoctorList().get(manager.getDoctorList().size()-1);
        Assert.assertEquals(dateTime, doc.getAvailableStartDateTime());
        Assert.assertEquals(duration, doc.getAvailableDuration());
    }


}
