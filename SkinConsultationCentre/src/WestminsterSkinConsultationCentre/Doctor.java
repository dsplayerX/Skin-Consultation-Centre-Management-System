package WestminsterSkinConsultationCentre;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a doctor
 */
public class Doctor extends Person implements Comparable<Doctor>{
    private String medLicenceNum;
    private String specialisation;
    private LocalDateTime availableStartDateTime;
    private LocalDateTime availableEndDateTime;
    private int availableDuration;

    /**
     * Creates a doctor with all relatant details
     *
     * @param fName Doctor's first name
     * @param surname Doctor's surname
     * @param dob Doctor's date of birth
     * @param mobNum Doctor's mobile number
     * @param gender Doctor's gender
     * @param medLicenceNum Doctor's medical licence number
     * @param specialisation Doctor's specialisation
     */
    public Doctor(String fName, String surname, LocalDate dob, String mobNum, String gender, String medLicenceNum, String specialisation){
        super(fName, surname, dob, mobNum, gender);
        this.medLicenceNum = medLicenceNum;
        this.specialisation = specialisation;
    }

    // Getters for Doctor class

    /**
     * Gets doctor's medical licence number
     * @return A string of doctor's medical licence number
     */
    public String getMedLicenceNum() {
        return medLicenceNum;
    }

    /**
     * Gets doctor's specialisation
     * @return A String of doctor's specialisation
     */
    public String getSpecialisation() {
        return specialisation;
    }

    /**
     * Gets doctor's available for consultation start time
     * @return A LocalDAte object of consultation start time
     */
    public LocalDateTime getAvailableStartDateTime() {
        return availableStartDateTime;
    }

    /**
     * Gets doctor's available for consultation end time
     * @return A LocalDate object of consultation end time
     */
    public LocalDateTime getAvailableEndDateTime() {
        return availableEndDateTime;
    }

    /**
     * Gets doctor's avaiable duration for consultaions on a specific date
     * @return An integer of the duration a doctor is available
     */
    public int getAvailableDuration() {
        return availableDuration;
    }

    // Setters for Doctor class

    /**
     * Sets doctor's medical licence number
     * @param medLicenceNum A string containing doctor's medical licence number
     */
    public void setMedLicenceNum(String medLicenceNum) {
        this.medLicenceNum = medLicenceNum;
    }

    /**
     * Sets doctor's specialisation
     * @param specialisation A String containing doctor's specialisation
     */
    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    /**
     * Sets doctor's available start time for consultation
     * @param dt A LocalDate containing doctor's consultation start time
     */
    public void setAvailableTimeSlot(LocalDateTime dt){
        this.availableStartDateTime = dt;
    }

    /**
     * Sets doctor's available duration and calculated end time for consultation
     * @param duration An Integer containg doctor's consultation end time
     */
    public void setAvailableDuration(int duration) {
        this.availableDuration = duration;
        this.availableEndDateTime = availableStartDateTime.plusHours(duration);
    }

    /**
     * Compares two doctor objects for surname
     *
     * @param other The doctor object to be compared to
     * @return An Integer compared value of two doctor surnames
     */
    @Override
    public int compareTo(Doctor other) {
        return (this.getSurname().compareTo(other.getSurname()));
    }

}

