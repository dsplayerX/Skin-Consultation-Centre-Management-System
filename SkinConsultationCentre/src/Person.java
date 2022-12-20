import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {

    private String fName;
    private String surname;
    private LocalDate dob;
    private String mobNum;

    // Constructor
    public Person(String fname, String surname, LocalDate dob, String mobNum){
        this.fName = fname;
        this.surname = surname;
        this.dob = dob;
        this.mobNum = mobNum;
    }

    // Getters for person class
    public String getfName() {
        return fName;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDob() {
//        String dobString = " " + this.dob.getYear() + this.dob.getMonth() + this.dob.getDays() + " " ;
//        return dobString;
        return dob;
    }

    public String getMobNum() {
        return mobNum;
    }

    // Setters for person class
    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setMobNum(String mobNum) {
        this.mobNum = mobNum;
    }
}
