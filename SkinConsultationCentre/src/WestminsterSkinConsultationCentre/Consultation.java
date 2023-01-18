package WestminsterSkinConsultationCentre;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Consultation implements Serializable {
    private Doctor allocDoc;
    private Patient allocPatient;
    private LocalDateTime consultDateTime;
    private double consultCost;
    private byte[] consultNotes;
    private ArrayList<byte[]> consultImages;
    private int duration = 3;


    // No argument constructor
    public Consultation(){
    }


    // All argument constructor
    public Consultation(Doctor doctor, Patient patient, LocalDateTime dateTime, double cost, int duration){
        this.allocDoc = doctor;
        this.allocPatient = patient;
        this.consultDateTime = dateTime;
        this.consultCost = cost;
        this.duration = duration;
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

    public byte[] getConsultNotes() {
        return consultNotes;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<byte[]> getConsultImages() {
        return consultImages;
    }

    public LocalDateTime getEndTime(){
        return this.consultDateTime.plusHours(this.duration);
    }

    // Setters for consultation class

    public void setAllocDoc(Doctor allocDoc) {
        this.allocDoc = allocDoc;
    }

    public void setAllocPatient(Patient allocPatient) {
        this.allocPatient = allocPatient;
    }

    public void setConsultDateTime(LocalDateTime dt) {
        this.consultDateTime = dt;
    }

    public void setConsultCost(double cost) {
        this.consultCost = cost;
    }

    public void setConsultNotes(byte[] notes) {
        this.consultNotes = notes;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setConsultImages(ArrayList<byte[]> images) {
        this.consultImages = images;
    }
}

