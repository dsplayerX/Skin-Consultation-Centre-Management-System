import java.time.LocalDate;

public class Doctor extends Person implements Comparable<Doctor>{
    private String medLicenceNum;
    private String specialisation;


    // All argument constructor
    public Doctor(String fName, String surname, LocalDate dob, String mobNum, String medLicenceNum, String specialisation){
        super(fName, surname, dob, mobNum);
        this.medLicenceNum = medLicenceNum;
        this.specialisation = specialisation;
    }

    // Getters for Doctor class

    public String getMedLicenceNum() {
        return medLicenceNum;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    // Setters for Doctor class

    public void setMedLicenceNum(String medLicenceNum) {
        this.medLicenceNum = medLicenceNum;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public int compareTo(Doctor other) {
        return (this.getSurname().compareTo(other.getSurname()));
    }
}

