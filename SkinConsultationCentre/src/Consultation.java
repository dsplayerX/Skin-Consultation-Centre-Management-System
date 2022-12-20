import java.time.LocalDateTime;

public class Consultation {
    private Doctor allocDoc;
    private Patient allocPatient;
    private LocalDateTime consultDateTime;
    private double consultCost;
    private String consultNotes;

    // No argument constructor
    public Consultation(){
    }

    // All argument constructor
    public Consultation(Doctor doctor, Patient patient, LocalDateTime dateTime){
        this.allocDoc = doctor;
        this.allocPatient = patient;
        this.consultDateTime = dateTime;
    }

    // Getters for consultation class

    public Doctor getAllocDoc() {
        return allocDoc;
    }

    public Patient getAllocPatient() {
        return allocPatient;
    }

    public LocalDateTime getConsultDateTime() {
        return consultDateTime;
    }

    public double getConsultCost() {
        return consultCost;
    }

    public String getConsultNotes() {
        return consultNotes;
    }

    // Setters for consultation class

    public void setAllocDoc(Doctor allocDoc) {
        this.allocDoc = allocDoc;
    }

    public void setAllocPatient(Patient allocPatient) {
        this.allocPatient = allocPatient;
    }

    public void setConsultDateTime(LocalDateTime consultDateTime) {
        this.consultDateTime = consultDateTime;
    }

    public void setConsultCost(double consultCost) {
        this.consultCost = consultCost;
    }

    public void setConsultNotes(String consultNotes) {
        this.consultNotes = consultNotes;
    }
}
