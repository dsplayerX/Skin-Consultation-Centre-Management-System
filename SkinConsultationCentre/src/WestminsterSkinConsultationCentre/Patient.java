package WestminsterSkinConsultationCentre;

import java.time.LocalDate;

/**
 * Represents a person
 */
public class Patient extends Person {
    private String patientId;

    /**
     * Creates a patient with a unique patient id
     * @param fName Patient's firstname
     * @param surname Patient's surname
     * @param dob Patient's date of birth
     * @param mobNum Patient's mobile number
     * @param gender Patient's gender
     * @param patientId Patient's patient ID
     */
    public Patient(String fName, String surname, LocalDate dob, String mobNum, String gender, String patientId){
        super(fName, surname, dob, mobNum, gender);
        this.patientId = patientId;
    }

    // Getters for Patient class

    /**
     * Gets patient's ID
     * @return A String representing the patient's id
     */
    public String getPatientId() {
        return patientId;
    }

    // Setters for Patient class

    /**
     * Sets the patient's ID
     * @param patientId A string containing the patient's id
     */
    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
