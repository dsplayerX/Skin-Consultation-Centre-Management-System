package WestminsterSkinConsultationCentre;

import java.io.Serializable;
import java.time.LocalDate;

/** Represents a person
 *
 */
public class Person implements Serializable {

    private String fName;
    private String surname;
    private LocalDate dob;
    private String mobNum;
    private String gender;

    /**
     * Creates a person with basic details
     * @param fName Person's first name
     * @param surname Person's surname
     * @param dob Person's date of birth
     * @param mobNum Person's mobile number
     * @param gender Person's gender
     */
    public Person(String fName, String surname, LocalDate dob, String mobNum, String gender){
        this.fName = fName;
        this.surname = surname;
        this.dob = dob;
        this.mobNum = mobNum;
        this.gender = gender;
    }

    // Getters for Person class

    /**
     * Gets the person's first name
     * @return A string of person's first name
     */
    public String getfName() {
        return fName;
    }

    /**
     * Gets the person's surname
     * @return A string of person's surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the date of birth
     * @return A LocalDate object of person's birthday
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Gets the mobile number
     * @return A string of person's mobile number
     */
    public String getMobNum() {
        return mobNum;
    }

    /**
     * Gets the gender
     * @return A string of person's gender
     */
    public String getGender() {
        return gender;
    }

    // Setters for Person class

    /**
     * Sets the person's first name
     * @param fName A string containing person's first name
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * Sets the person's surname
     * @param surname A String containing person's surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Sets the person's date of birth
     * @param dob A LocalDate object containing the date of birth
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Sets the person's mobile number
     * @param mobNum A string containing person's mobile number
     */
    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }

    /**
     * Sets the person's gender
     * @param gender A String containing person's gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}
