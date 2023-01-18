package WestminsterSkinConsultationCentre;

import javax.crypto.Cipher;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Add consultation page of the GUI application
 */
public class AddConsultationGUI extends JFrame{
    private JPanel titlePanel, docPanel, docInfoPanel, availablePanel, patientPanel, costNotesPanel, buttonPanel,
            costOuterPanel, uploadBtnPnl;
    private JButton docCheckBtn, pCheckBtn, addImageBtn, bookBtn, goBackBtn;
    private JLabel conForm, date, time, patientLbl,
            pFName, pSurname, pDob, pGender, pMobNo, pId, cost, notes; // Labels for every item
    private JTextArea docInfo, inNotes;
    private JTextField inFName, inSurname, inGender, inMobNo, inPId, inCost;
    private JFormattedTextField inDate, inTime, inDob;
    private JComboBox<String> comboDocs;
    private MaskFormatter dateMask;
    private MaskFormatter timeMask;
    private MaskFormatter mobileNoMask;
    private MaskFormatter patientIdMask;

    private Doctor consultDoc;
    private boolean existingPatient = false;
    private Patient consultPatient;
    private LocalDateTime consultDateTime;
    private ArrayList<byte[]> imageFileBytesArr = new ArrayList<byte[]>();

    /**
     * Creates a new AddConsultationGUI
     */
    public AddConsultationGUI(){

        // Fonts and Colors used in this frame
        Font fontTitle = new Font("Inter-Black", Font.BOLD, 42);
        Font fontBold = new Font("Inter", Font.BOLD, 13);
        Color brightOrange = new Color(235, 100, 64);
        Color darkGreen = new Color(73, 113,116);
        Color whiteShade = new Color(214, 228, 229);
        Color whiteSmoke = new Color(239, 245, 245);

        // Mask for date formatting
        try {
            dateMask = new MaskFormatter("####-##-##");
            dateMask.setPlaceholderCharacter('#');
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Mask for time formatting
        try {
            timeMask = new MaskFormatter("##:##");
            timeMask.setPlaceholderCharacter('#');
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Mask for phone number
        try {
            mobileNoMask = new MaskFormatter("###-###-####");
            mobileNoMask.setPlaceholderCharacter('#');
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Mask for patient Id
        try {
            patientIdMask = new MaskFormatter("#####");
            patientIdMask.setPlaceholderCharacter('#');
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Creating the JFrame
        new JFrame("Add Consultation");
        setSize(680, 910);
        setBackground(whiteShade);
        setResizable(false);
        setLayout(new BorderLayout());

        titlePanel = new JPanel();
        titlePanel.setBackground(whiteShade);
        titlePanel.setBorder(new EmptyBorder(20,10,0,10));

        docPanel = new JPanel(new BorderLayout());
        docPanel.setBackground(whiteShade);
        docPanel.setPreferredSize(new Dimension(600,100));
        docPanel.setBorder(new EmptyBorder(10,10,10,10));

        GridLayout docTimeGrid = new GridLayout(1,7);
        docTimeGrid.setHgap(10);
        availablePanel = new JPanel(docTimeGrid);
        availablePanel.setBackground(whiteShade);
        availablePanel.setPreferredSize(new Dimension(550,40));

        GridLayout patientLayout = new GridLayout(6,2);
        patientLayout.setVgap(10);
        patientLayout.setHgap(10);
        patientPanel = new JPanel(patientLayout);
        patientPanel.setBackground(whiteShade);
        patientPanel.setPreferredSize(new Dimension(600,280));
        JPanel patientOuterPanel = new JPanel(new BorderLayout());
        patientOuterPanel.setBackground(whiteShade);
        JPanel checkBtnPanel = new JPanel(new BorderLayout());
        checkBtnPanel.setBackground(whiteShade);
        patientOuterPanel.add(patientPanel, BorderLayout.CENTER);
        patientOuterPanel.add(checkBtnPanel, BorderLayout.SOUTH);
        patientOuterPanel.setBorder(new EmptyBorder(10,10,0,10));
        patientPanel.setBorder(new EmptyBorder(10,10,10,100));
        checkBtnPanel.setBorder(new EmptyBorder(0,10,10,100));

        costNotesPanel = new JPanel(new BorderLayout());
        costNotesPanel.setBackground(whiteShade);
        costNotesPanel.setPreferredSize(new Dimension(600,120));
        costOuterPanel = new JPanel(new BorderLayout());
        uploadBtnPnl = new JPanel(new BorderLayout());
        uploadBtnPnl.setBorder(new EmptyBorder(10,10,20,100));
        costOuterPanel.add(costNotesPanel, BorderLayout.CENTER);
        costOuterPanel.add(uploadBtnPnl, BorderLayout.SOUTH);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(whiteShade);
        buttonPanel.setPreferredSize(new Dimension(600,80));

        // Creating a BoxLayout with 4 boxes
        JPanel userInterPanel = new JPanel();
        userInterPanel.setBackground(whiteShade);
        LayoutManager layout = new BoxLayout(userInterPanel, BoxLayout.X_AXIS);
        Box[] boxes = new Box[4];
        boxes[0] = Box.createHorizontalBox();
        boxes[1] = Box.createHorizontalBox();
        boxes[2] = Box.createHorizontalBox();
        boxes[3] = Box.createHorizontalBox();
        boxes[0].createGlue();
        boxes[1].createGlue();
        boxes[2].createGlue();
        boxes[3].createGlue();
        userInterPanel.add(boxes[0]);
        userInterPanel.add(boxes[1]);
        userInterPanel.add(boxes[2]);
        userInterPanel.add(boxes[3]);

        docPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Adding relevant panels to the BoxLayout
        boxes[0].add(docPanel);
        boxes[1].add(availablePanel);
        boxes[2].add(patientOuterPanel);
        boxes[3].add(costOuterPanel);

        add(titlePanel, BorderLayout.NORTH);
        add(userInterPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // setting title at top
        conForm = new JLabel("Consultation Form");
        conForm.setFont(fontTitle);
        conForm.setForeground(darkGreen);
        titlePanel.add(conForm);

        // Doctor combo box used to select the doctor for consultation
        ArrayList<String> docLabels = new ArrayList<>();
        for (Doctor doc : UserApplication.managerForGUI.getDoctorList()){
            String docLabel = "  Dr. " + doc.getfName() + " " + doc.getSurname();
            docLabels.add(docLabel);
        }
        String[] docStringArray = docLabels.toArray(new String[0]);
        comboDocs = new JComboBox<>(docStringArray); // combo box that holds all the doctors
        comboDocs.setSelectedItem("Select a doctor");
        comboDocs.setFont(fontBold);
        comboDocs.setPreferredSize(new Dimension(200, 40));
        comboDocs.setBackground(brightOrange);
        comboDocs.setForeground(whiteSmoke);
        JPanel docListPanel = new JPanel(new BorderLayout());
        docListPanel.setBackground(whiteShade);
        docListPanel.add(comboDocs, BorderLayout.CENTER);
        docListPanel.setBorder(new EmptyBorder(10,50,10,10));

        JLabel selectDoc = new JLabel("Select a doctor:");
        selectDoc.setFont(fontBold);
        docListPanel.add(selectDoc, BorderLayout.NORTH);
        docPanel.add(docListPanel, BorderLayout.WEST);

        // Display information of selected doctor
        docInfo = new JTextArea(5,20);
        docInfo.setText("Doctor Name: -\nDoctor Specialisation: -\nDoctor Medical Licence No.: -");
        docInfo.setEditable(false);
        docInfo.setBackground(whiteShade);
        docInfo.setForeground(darkGreen);
        docInfo.setFont(fontBold);
        docInfo.setBorder(new EmptyBorder(10,20,20,20));
        docInfo.setPreferredSize(new Dimension(250,50));
        docPanel.add(docInfo, BorderLayout.CENTER);

        // Doctor date time availability panel
        date = new JLabel("Date:     ", SwingConstants.RIGHT);
        date.setFont(fontBold);
        inDate = new JFormattedTextField(dateMask);
        inDate.setFont(fontBold);
        time = new JLabel("Time:     ", SwingConstants.RIGHT);
        time.setFont(fontBold);
        inTime = new JFormattedTextField(timeMask);
        inTime.setFont(fontBold);

        // Doctor availability check button
        docCheckBtn = new JButton("Check");
        docCheckBtn.setIcon(new ImageIcon("images/datesearch.png"));
        docCheckBtn.setFont(fontBold);
        docCheckBtn.setPreferredSize(new Dimension(100, 50));
        docCheckBtn.setBackground(brightOrange);
        docCheckBtn.setForeground(whiteSmoke);

        availablePanel.add(date);
        availablePanel.add(inDate);
        availablePanel.add(time);
        availablePanel.add(inTime);
        availablePanel.add(docCheckBtn);

        // Patient info panel
        patientLbl = new JLabel("Patient Details", SwingConstants.CENTER);
        patientLbl.setFont(new Font("Inter", Font.BOLD, 15));
        patientLbl.setForeground(darkGreen);
        pFName = new JLabel("First Name:           ", SwingConstants.RIGHT);
        pFName.setFont(fontBold);
        pSurname = new JLabel("Surname:           ", SwingConstants.RIGHT);
        pSurname.setFont(fontBold);
        pGender = new JLabel("Gender:           ", SwingConstants.RIGHT);
        pGender.setFont(fontBold);
        pDob = new JLabel("Date of Birth:           ", SwingConstants.RIGHT);
        pDob.setFont(fontBold);
        pMobNo = new JLabel("Mobile No:           ", SwingConstants.RIGHT);
        pMobNo.setFont(fontBold);
        pId = new JLabel("Patient ID:           ", SwingConstants.RIGHT);
        pId.setFont(fontBold);

        inFName = new JTextField(10);
        inSurname = new JTextField(10);
        inGender = new JTextField(5);
        inDob = new JFormattedTextField(dateMask);
        inDob.setColumns(6);
        inMobNo = new JFormattedTextField(mobileNoMask);
        inMobNo.setColumns(8);
        inPId = new JFormattedTextField(patientIdMask);
        inPId.setColumns(4);
        pCheckBtn = new JButton("Check ID");
        pCheckBtn.setIcon(new ImageIcon("images/listsearch.png"));
        pCheckBtn.setFont(fontBold);
        pCheckBtn.setPreferredSize(new Dimension(120, 40));
        pCheckBtn.setBackground(brightOrange);
        pCheckBtn.setForeground(whiteSmoke);

        patientOuterPanel.add(patientLbl, BorderLayout.NORTH);
        patientPanel.add(pFName);
        patientPanel.add(inFName);
        patientPanel.add(pSurname);
        patientPanel.add(inSurname);
        patientPanel.add(pGender);
        patientPanel.add(inGender);
        patientPanel.add(pDob);
        patientPanel.add(inDob);
        patientPanel.add(pMobNo);
        patientPanel.add(inMobNo);
        patientPanel.add(pId);
        patientPanel.add(inPId);
        checkBtnPanel.add(pCheckBtn, BorderLayout.EAST);

        // Cost and notes panel
        cost = new JLabel("Cost:                ", SwingConstants.RIGHT);
        cost.setFont(fontBold);
        notes = new JLabel("Notes:                ", SwingConstants.RIGHT);
        notes.setFont(fontBold);

        inCost = new JTextField(3);
        inNotes = new JTextArea(3,10);

        JPanel costPanel = new JPanel(new GridLayout(1,2));
        costPanel.setBackground(whiteShade);
        costPanel.setPreferredSize(new Dimension(500,45));
        costPanel.add(cost);
        costPanel.add(inCost);
        costPanel.setBorder(new EmptyBorder(0,0,10,0));
        costNotesPanel.add(costPanel, BorderLayout.NORTH);

        JPanel notesPanel = new JPanel(new GridLayout(1,2));
        notesPanel.setBackground(whiteShade);
        notesPanel.add(notes);
        notesPanel.add(inNotes);
        costNotesPanel.add(notesPanel, BorderLayout.CENTER);
        costNotesPanel.setBorder(new EmptyBorder(10,10,0,100));

        addImageBtn = new JButton("Upload Image");
        addImageBtn.setIcon(new ImageIcon("images/uploadphoto.png"));
        addImageBtn.setFont(fontBold);
        addImageBtn.setPreferredSize(new Dimension(160, 40));
        addImageBtn.setBackground(brightOrange);
        addImageBtn.setForeground(whiteSmoke);
        uploadBtnPnl.setBackground(whiteShade);
        uploadBtnPnl.add(addImageBtn, BorderLayout.EAST);

        // Buttons panel
        goBackBtn = new JButton(" Back");
        goBackBtn.setIcon(new ImageIcon("images/back.png"));
        goBackBtn.setFont(fontBold);
        goBackBtn.setPreferredSize(new Dimension(120, 50));
        goBackBtn.setBackground(brightOrange);
        goBackBtn.setForeground(whiteSmoke);

        bookBtn = new JButton("  Book Consultation");
        bookBtn.setIcon(new ImageIcon("images/complete.png"));
        bookBtn.setFont(fontBold);
        bookBtn.setPreferredSize(new Dimension(200, 50));
        bookBtn.setBackground(brightOrange);
        bookBtn.setForeground(whiteSmoke);

        buttonPanel.add(goBackBtn);
        buttonPanel.add(bookBtn);

        // Actions listener for doctor combo box
        comboDocs.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = comboDocs.getSelectedIndex();
                consultDoc = UserApplication.managerForGUI.getDoctorList().get(selectedIndex);
                //System.out.println(consultDoc.toString());
                docInfo.setText("Doctor Name: " + consultDoc.getfName() + " " + consultDoc.getSurname() +
                        "\nDoctor Specialisation:" + consultDoc.getSpecialisation() +
                        "\nDoctor Medical Licence No.: " + consultDoc.getMedLicenceNum());
                docInfo.repaint();
            }
        });

        // coloring input fields
        JTextField[] textFields = {inFName, inSurname, inGender, inMobNo, inPId, inCost};
        for (JTextField textField : textFields){
            textField.setBackground(whiteSmoke);
            textField.setBorder(new EmptyBorder(5,10,5,10));
        }
        inDate.setBackground(whiteSmoke);
        inDate.setBorder(new EmptyBorder(5,10,5,10));
        inTime.setBackground(whiteSmoke);
        inTime.setBorder(new EmptyBorder(5,10,5,10));
        inDob.setBackground(whiteSmoke);
        inDob.setBorder(new EmptyBorder(5,10,5,10));
        inNotes.setBackground(whiteSmoke);
        inNotes.setBorder(new EmptyBorder(5,10,5,10));

        docCheckBtn.addActionListener(new docCheckBtnListener());
        pCheckBtn.addActionListener(new pCheckBtnListener());
        goBackBtn.addActionListener(new goBackListener());
        bookBtn.addActionListener(new bookBtnListener());
        addImageBtn.addActionListener(new addImageBtnListener());

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }


    /**
     * Back button action listener used to go back to the previous menu
     */
    private class goBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new SkinConsultationCentreGUI();
        }
    }


    /**
     * Check button action listener used to check availability of a doctor in a given time
     */
    private class docCheckBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Getting user inputs
            String userInDate = inDate.getText();
            String userInTime = inTime.getText();

            // Letting the user know that the fields cannot be empty
            if (userInDate.equals("####-##-##")){
                String message = "Please enter a date to check for availability!";
                JOptionPane.showMessageDialog(null, message, "Enter Date!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (userInTime.equals("##:##")){
                String message = "Please enter a time to check for availability!";
                JOptionPane.showMessageDialog(null, message, "Enter Time!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            consultDoc = UserApplication.managerForGUI.getDoctorList().get(comboDocs.getSelectedIndex()); // getting the selected combo box doctor from doctors arraylist

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); // pattern used for date/time

            LocalDateTime dateTime = null;
            try {
                dateTime = LocalDateTime.parse(userInDate + " " + userInTime, dateTimeFormatter); // creating a LocalDateTime object from the entered
            } catch (Exception timeE) {
                String message = "Please enter a valid date according to the format given!";
                JOptionPane.showMessageDialog(null, message, "Invalid Date!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // checking if the entered date and time is in the future or not
            if (dateTime.isBefore(LocalDateTime.now())){
                String message = "Please select a future date and time!";
                JOptionPane.showMessageDialog(null, message, "Invalid Date!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if manager has not added a date/time for consultation for a doctor, asking user to select another doctor.
            if (consultDoc.getAvailableStartDateTime() == null){
                String message = "Dr. " + consultDoc.getSurname() + " doesn't have an allocated consultation timeslot.\nSelect a different doctor!";
                JOptionPane.showMessageDialog(null, message, "No consultations!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            boolean isDocUnavailable = false; // to check whether the doc is unavailable

            // see if the selected doctor is available in that specific date and time
            if (dateTime.isEqual(consultDoc.getAvailableStartDateTime()) || (dateTime.isAfter(consultDoc.getAvailableStartDateTime()) && dateTime.isBefore(consultDoc.getAvailableEndDateTime()))){
                // if the consultation list is empty, all doctors are available
                if (UserApplication.consultations.isEmpty()){
                    isDocUnavailable = false;
                }
                // going through all the consultations to see if the selected doctor has consultations at the given time and marking doctor availability accordingly
                for (Consultation consultation : UserApplication.consultations) {
                    if (consultDoc.getMedLicenceNum().equals(consultation.getAllocDoc().getMedLicenceNum())) {
                        if (!(dateTime.isEqual(consultation.getConsultDateTime()) || (dateTime.isAfter(consultation.getConsultDateTime()) && dateTime.isBefore(consultation.getEndTime())))) {
                            isDocUnavailable = false;
                            break;
                        } else {
                            isDocUnavailable = true;
                        }
                    } else {
                        isDocUnavailable = false;
                    }
                }
            } else {
                isDocUnavailable = true;
            }

            if (!isDocUnavailable){
                String message = "Dr. " + consultDoc.getSurname() + " is available for consultation.";
                JOptionPane.showMessageDialog(null, message, "Doctor available!", JOptionPane.INFORMATION_MESSAGE);
            } else { // if the doctor is unavailable letting user know a random doc will be selected
                String message = "Dr. " + consultDoc.getSurname() + " is unavailable. Selecting a random doctor.";
                JOptionPane.showMessageDialog(null, message, "Doctor unavailable!", JOptionPane.INFORMATION_MESSAGE);
            }

            //
            if (isDocUnavailable) {
                boolean docsAvailableAtTime = false; // used to check whether there are random doctors available at a given time
                ArrayList<Doctor> availableDoctors = new ArrayList<Doctor>(); // arraylist to store all the doctors available at the given date/time
                for (Doctor doc : UserApplication.managerForGUI.getDoctorList()){
                    if (dateTime.equals(doc.getAvailableStartDateTime()) || (dateTime.isAfter(doc.getAvailableStartDateTime()) && dateTime.isBefore(doc.getAvailableEndDateTime()))){
                        if (!(doc.getMedLicenceNum().equals(consultDoc.getMedLicenceNum()))){
                            availableDoctors.add(doc); // if doctor is available adding him to the temporary arraylist of available doctor
                        }
                    }
                }

                // if the arraylist of available doctors isn't empty, there are avaiable doctors
                if (!availableDoctors.isEmpty()){
                    docsAvailableAtTime = true;
                }

                if (docsAvailableAtTime){
                    Random randomGenerator = new Random();
                    int randNum = randomGenerator.nextInt(availableDoctors.size()); // getting a random doctor from the available doctors arraylist
                    Doctor randomDoc = availableDoctors.get(randNum);
                    int docIndex = 0;
                    for (Doctor tempDoc : UserApplication.managerForGUI.getDoctorList()){
                        if(tempDoc.getMedLicenceNum().equals(randomDoc.getMedLicenceNum())){
                            docIndex = UserApplication.managerForGUI.getDoctorList().indexOf(tempDoc); // getting the original arraylist index of the selected random doctor
                        }
                    }
                    comboDocs.setSelectedIndex(docIndex); // setting random doctor as the selected combo box item
                    docInfo.setText("Random Doctor Selected!\nDoctor Name: " + randomDoc.getfName() + " " + randomDoc.getSurname() +
                            "\nDoctor Specialisation:" + randomDoc.getSpecialisation() +
                            "\nDoctor Medical Licence No.: " + randomDoc.getMedLicenceNum());
                    consultDoc = randomDoc; // setting the random doctor as the consultation doctor
                } else { // letting user know if there aren't any doctors available at that given time
                    docInfo.setText("Doctor Name: -\nDoctor Specialisation: -\nDoctor Medical Licence No.: -");
                    String message = "There are no doctors available at that time.\nPlease select a different date/time.";
                    JOptionPane.showMessageDialog(null, message, "No available doctors!", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            consultDateTime = dateTime;
        }
    }


    /**
     * Check ID button used to add new patient or see if patient is an existing patient
     */
    private class pCheckBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // If user hasn't checked doctor availability, letting user know
            if (consultDateTime == null){
                JOptionPane.showMessageDialog(null, "Check doctor availability first!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // getting all the user inputs
            String fName = inFName.getText();
            String surName = inSurname.getText();
            String gender = inGender.getText();
            String dob = inDob.getText();
            String mobNo = inMobNo.getText();
            String patientId = inPId.getText();

            // informing user of empty fields
            if (fName.isEmpty() || surName.isEmpty() || gender.isEmpty() || dob.equals("####-##-##") || mobNo.equals("###-###-####")){
                JOptionPane.showMessageDialog(null, "Fill required fields!", "Empty fields", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Adding birthday of the patient
            DateTimeFormatter dobFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dobDate = null;
            try {
                dobDate = LocalDate.parse(dob, dobFormat);
            } catch (Exception dateEx){
                JOptionPane.showMessageDialog(null, "Enter a valid date for birthday!", "Invalid DoB", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (patientId.equals("#####")){ // if the patientId is equal to the placeholder mask value, that means the patient ID is empty.
                JOptionPane.showMessageDialog(null, "Patient ID cannot be empty!", "Empty field!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                Integer.parseInt(patientId); // seeing if the patient id is an integer or not
            } catch (Exception parseErr){
                JOptionPane.showMessageDialog(null, "Enter a valid Patient ID!", "Invalid Patient ID", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // creating a new consult patient
            consultPatient = new Patient(fName, surName, dobDate, mobNo, gender, patientId);

            // seeing if the newly added patient is an existing patient or a new one
            for (Patient patient : UserApplication.patients){
                if (patient.getPatientId().equals(consultPatient.getPatientId())) {
                    existingPatient = true;
                    consultPatient = patient; // if patient is an existing patient setting the existing patient as the consultant patient
                    break;
                }
            }

            if (!existingPatient){
                JOptionPane.showMessageDialog(null, "New patient! First consultation will be £15 per hour.", "New Patient", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Welcome back! Your consultation will be £25 per hour.", "Existing Patient", JOptionPane.INFORMATION_MESSAGE);
            }
            pCheckBtn.setEnabled(false);
        }
    }


    /**
     * Upload images button listener used to upload multiple images to the consultation
     */
    private class addImageBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Select either JPG or PNG image", "jpg", "jpeg", "png");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                //System.out.println("You chose to open this file: "  + fileChooser.getSelectedFile());
            }

            // turning the uploaded image to an array of bytes (this will be encrypted at the time of booking)
            byte[] fileBytes = new byte[0];
            try {
                fileBytes = new byte[(int) fileChooser.getSelectedFile().length()];
                FileInputStream fileInput = new FileInputStream(fileChooser.getSelectedFile());
                fileInput.read(fileBytes);
                fileInput.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error adding the image! Try again!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //System.out.println(Arrays.toString(fileBytes));
            imageFileBytesArr.add(fileBytes); // adding image to the arraylist of byte[]
            JOptionPane.showMessageDialog(null, "Image added successfully!\nClick button again to add more.", "Imaged Added!", JOptionPane.INFORMATION_MESSAGE);
        }
        // user can click on the button multiple times to add more photos to the consultation

    }


    /**
     * Book consultation button action listener used to book the consultation
     */
    private class bookBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Making sure the consultation date/time is not null. if null, letting the user know and aborting the booking process
            if (consultDateTime == null){
                JOptionPane.showMessageDialog(null, "Check consultation date/time with a doctor before booking!", "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Making sure consultation doctor is selected. if null, letting the user know and aborting the booking process
            if (consultDoc == null){
                JOptionPane.showMessageDialog(null, "Check consultation date/time with a doctor before booking!", "Warning!", JOptionPane.WARNING_MESSAGE);
                consultDoc = UserApplication.managerForGUI.getDoctorList().get(comboDocs.getSelectedIndex());
                return;
            }

            // Just in case the user doesn't click the check patient button. if null, letting the user know and aborting the booking process
            if (consultPatient == null){
                JOptionPane.showMessageDialog(null, "Enter correct patient details and check before booking!", "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // getting the cost for the consultation
            String consultCostText = inCost.getText();

            // if the consult cost is empty, letting the user know
            if (consultCostText == null || consultCostText.equals("")){
                JOptionPane.showMessageDialog(null, "Enter the cost for the required duration!", "Empty Field!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double consultCost; // variable to store the consultation cost

            // parsing the cost text to a double
            try{
                consultCost = Double.parseDouble(consultCostText);
            } catch (Exception parseEr){
                JOptionPane.showMessageDialog(null, "Enter a double for cost!", "Wrong input type!", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Calculating the duration of consultation based on cost entered.
            int hours;
            if (!existingPatient) { // if a new patient cost is 15 euros per hour
                if (consultCost%15 == 0){
                    hours = (int) (consultCost / 15);
                } else {
                    JOptionPane.showMessageDialog(null, "Cost must be a multiplication of £15 per hours you need!", "Invalid cost!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else { // if existing patient consultation cost is 25 euros per hour
                if (consultCost % 25 == 0){
                    hours = (int) (consultCost / 25);
                } else {
                    JOptionPane.showMessageDialog(null, "Cost must be a multiplication of £25 per hours you need!", "Invalid cost!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // the duration is calculated by getting the cost from the user instead of vice-versa due to the strict mentioning of the coursework

            if (hours <= 0) {  // making sure the hours is not negative or zero.
                JOptionPane.showMessageDialog(null, "Cost cannot be £0. Enter a valid cost!!", "Invalid cost!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime consultEndDateTime = consultDateTime.plusHours(hours); // getting the end time with the cost to duration calculation

            // if the doctor is unavailable for given time, letting the user know.
            if (consultEndDateTime.isAfter(consultDoc.getAvailableEndDateTime())) {
                String message = "Dr. " + consultDoc.getSurname() + " cannot be booked for " + hours + " hour(s)." +
                        "\nPlease select a different doctor or shorten the consultation time.";
                JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // if the doctor is already booked for consultation, letting the user know
            for (Consultation consultation : UserApplication.consultations) {
                if (consultDoc.getMedLicenceNum().equals(consultation.getAllocDoc().getMedLicenceNum())) {
                    if (consultEndDateTime.isAfter(consultation.getConsultDateTime())) {
                        String message = "Dr. " + consultDoc.getSurname() + " cannot be booked for " + hours + " hour(s)." +
                                "\nPlease select a different doctor or shorten the consultation time.";
                        JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }


            // Encrypting the notes entered by the user using the key created in "UserApplication".
            Cipher cipher = null;
            byte[] encryptedNotes = new byte[0];
            if (!inNotes.getText().equals("")) {
                try {
                    String notesText = inNotes.getText(); // getting notes from the notes field
                    cipher = Cipher.getInstance("AES"); // AES encryption algorithm used
                    cipher.init(Cipher.ENCRYPT_MODE, UserApplication.key);
                    encryptedNotes = cipher.doFinal(notesText.getBytes());
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }

            // Encrypt the photos entered by the user
            ArrayList<byte[]> encryptedImages = new ArrayList<byte[]>();
            if (imageFileBytesArr != null){
                try{
                    cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.ENCRYPT_MODE, UserApplication.key);
                    for (byte[] byteArr : imageFileBytesArr){
                        byte[] tempEncryBytes = new byte[0];
                        tempEncryBytes = cipher.doFinal(byteArr);
                        encryptedImages.add(tempEncryBytes);
                    }
                } catch (Exception ee){
                    ee.printStackTrace();
                }
                // Used temporarily to confirm the encryption was working
                    //System.out.println(Arrays.toString(imageFileBytes));
                    //System.out.println(Arrays.toString(encryptedImage));
            }

            // Creating a new consultation ba
            Consultation consultation = new Consultation(consultDoc, consultPatient, consultDateTime, consultCost, hours);

            // if user has entered notes setting consultation notes in a consultation
            if (encryptedNotes != null) {
                consultation.setConsultNotes(encryptedNotes);
            }

            // if encrypted images array is not empty setting the encrypted images in a consultation
            if (!encryptedImages.isEmpty()) {
                consultation.setConsultImages(encryptedImages);
            }

            // adding the consultation to the consultations arraylist
            UserApplication.consultations.add(consultation);


            if (!existingPatient){
                UserApplication.patients.add(consultPatient); // adding the patient to the patients list if the patient is new.
            }

            // Closing current frame and showing details of the consultation
            dispose();
            new ConsultationAddedGUI();
        }
    }
}
