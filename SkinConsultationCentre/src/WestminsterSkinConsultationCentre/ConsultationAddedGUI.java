package WestminsterSkinConsultationCentre;

import javax.crypto.Cipher;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Consultation review page of the GUI application
 */
public class ConsultationAddedGUI extends JFrame{
    private ArrayList<Consultation> consultationList;
    private JLabel title;
    private JButton menuBtn;
    private JPanel titlePanel, consultInfoPanel, buttonsPanel;
    private Consultation addedConsultation;

    /**
     * Creates a new ConsultationAddedGUI
     */
    public ConsultationAddedGUI(){

        // Fonts and Colors used in this frame
        Font fontTitle = new Font("Inter-Black", Font.BOLD, 42);
        Font fontBold = new Font("Inter", Font.BOLD, 13);
        Font fontBigBold = new Font("Inter", Font.BOLD, 15);
        Color brightOrange = new Color(235, 100, 64);
        Color darkGreen = new Color(73, 113,116);
        Color whiteShade = new Color(214, 228, 229);
        Color whiteSmoke = new Color(239, 245, 245);

        consultationList = UserApplication.consultations;
        addedConsultation = consultationList.get(consultationList.size() - 1); // getting the last added consultation from consultations list

        titlePanel = new JPanel();
        titlePanel.setBackground(whiteShade);

        consultInfoPanel = new JPanel();
        consultInfoPanel.setBackground(whiteShade);

        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(whiteShade);

        // Creating the JFrame
        new JFrame("Review added consultation");
        setLayout(new BorderLayout());
        setSize(550, 850);
        setResizable(true);

        add(titlePanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        title = new JLabel("Consultation Added!");
        title.setFont(fontTitle);
        title.setForeground(darkGreen);
        titlePanel.add(title);

        // creating a boxlayout with 4 boxes
        LayoutManager layout = new BoxLayout(consultInfoPanel, BoxLayout.X_AXIS);
        Box[] boxes = new Box[4];
        boxes[0] = Box.createHorizontalBox();
        boxes[1] = Box.createHorizontalBox();
        boxes[2] = Box.createHorizontalBox();
        boxes[3] = Box.createHorizontalBox();
        boxes[0].createGlue();
        boxes[1].createGlue();
        boxes[2].createGlue();
        boxes[2].createGlue();
        consultInfoPanel.add(boxes[0]);
        consultInfoPanel.add(boxes[1]);
        consultInfoPanel.add(boxes[2]);
        consultInfoPanel.add(boxes[3]);
        boxes[0].setBackground(whiteShade);
        boxes[1].setBackground(whiteShade);
        boxes[2].setBackground(whiteShade);
        boxes[3].setBackground(whiteShade);


        // Showing doctor information
        JLabel docLbl = new JLabel("    Doctor Information:");
        docLbl.setFont(fontBigBold);
        docLbl.setForeground(darkGreen);
        JLabel docNameLbl = new JLabel("                Name: Dr. " + addedConsultation.getAllocDoc().getfName() + " " + addedConsultation.getAllocDoc().getSurname());
        docNameLbl.setFont(fontBold);
        JLabel docSpecLbl = new JLabel("                Specialisation: " + addedConsultation.getAllocDoc().getSpecialisation());
        docSpecLbl.setFont(fontBold);
        JLabel docMedLiNum = new JLabel("                Medical Licence No.: " + addedConsultation.getAllocDoc().getMedLicenceNum());
        docMedLiNum.setFont(fontBold);

        // Showing patient information
        JLabel patLbl = new JLabel("    Patient Information:");
        patLbl.setFont(fontBigBold);
        patLbl.setForeground(darkGreen);
        JLabel patIdLbl = new JLabel("                ID: " + addedConsultation.getAllocPatient().getPatientId());
        patIdLbl.setFont(fontBold);
        JLabel patNameLbl = new JLabel("                Name: " + addedConsultation.getAllocPatient().getfName() + " " + addedConsultation.getAllocPatient().getSurname());
        patNameLbl.setFont(fontBold);
        JLabel patDobLbl = new JLabel("                DOB: " + addedConsultation.getAllocPatient().getDob().toString());
        patDobLbl.setFont(fontBold);
        JLabel patGenderLbl = new JLabel("                Gender: " + addedConsultation.getAllocPatient().getGender());
        patGenderLbl.setFont(fontBold);
        JLabel patMobNoLbl = new JLabel("                Mobile No.: " + addedConsultation.getAllocPatient().getMobNum());
        patMobNoLbl.setFont(fontBold);

        // Showing consultation information
        JLabel consultLbl = new JLabel("    Consultation Information:");
        consultLbl.setFont(fontBigBold);
        consultLbl.setForeground(darkGreen);
        JLabel consultDateLbl = new JLabel("                Date: " + addedConsultation.getConsultDateTime().toLocalDate().toString());
        consultDateLbl.setFont(fontBold);
        JLabel consultTimeLbl = new JLabel("                Time: " + addedConsultation.getConsultDateTime().toLocalTime().toString());
        consultTimeLbl.setFont(fontBold);
        JLabel consultDurationLbl = new JLabel("                Duration: " + addedConsultation.getDuration() + "hour(s)");
        consultDurationLbl.setFont(fontBold);
        JLabel consultCostLbl = new JLabel("                Cost: " + addedConsultation.getConsultCost());
        consultCostLbl.setFont(fontBold);

        JLabel consultNotesLbl = new JLabel();
        consultNotesLbl.setText("                Encrypted Notes: " + Arrays.toString(addedConsultation.getConsultNotes()));
        consultNotesLbl.setFont(fontBold);

        JLabel consultImageLbl = new JLabel();
        if (addedConsultation.getConsultImages() == null){
            consultImageLbl.setText("                Encrypted Images: No images added!");
        } else {
            consultImageLbl.setText("                Encrypted Images: " + addedConsultation.getConsultImages().size() + " image(s)." );
        }

        consultImageLbl.setFont(fontBold);
        GridLayout docPanelLayout = new GridLayout(4,1);
        docPanelLayout.setVgap(10);
        JPanel docPanel = new JPanel(docPanelLayout);
        docPanel.setPreferredSize(new Dimension(500,150));
        docPanel.setBackground(whiteShade);
        docPanel.setBorder(new EmptyBorder(10,10,10,10));

        GridLayout patPanelLayout = new GridLayout(6,1);
        patPanelLayout.setVgap(10);
        JPanel patPanel = new JPanel(patPanelLayout);
        patPanel.setPreferredSize(new Dimension(500,200));
        patPanel.setBackground(whiteShade);
        patPanel.setBorder(new EmptyBorder(10,10,10,10));

        GridLayout consultPanelLayout = new GridLayout(7,1);
        consultPanelLayout.setVgap(10);
        JPanel consultPanel = new JPanel(consultPanelLayout);
        consultPanel.setPreferredSize(new Dimension(500,200));
        consultPanel.setBackground(whiteShade);
        consultPanel.setBorder(new EmptyBorder(10,10,10,10));

        JPanel imagesPanel = new JPanel(new FlowLayout());
        imagesPanel.setBackground(whiteShade);

        // adding doctor, patient, consultation panels to the box layout
        boxes[0].add(docPanel);
        boxes[1].add(patPanel);
        boxes[2].add(consultPanel);
        boxes[3].add(imagesPanel);

        docPanel.add(docLbl);
        docPanel.add(docNameLbl);
        docPanel.add(docSpecLbl);
        docPanel.add(docMedLiNum);

        patPanel.add(patLbl);
        patPanel.add(patIdLbl);
        patPanel.add(patNameLbl);
        patPanel.add(patDobLbl);
        patPanel.add(patGenderLbl);
        patPanel.add(patMobNoLbl);

        consultPanel.add(consultLbl);
        consultPanel.add(consultDateLbl);
        consultPanel.add(consultTimeLbl);
        consultPanel.add(consultDurationLbl);
        consultPanel.add(consultCostLbl);

        consultPanel.add(consultNotesLbl);
        consultPanel.add(consultImageLbl);

        add(consultInfoPanel, BorderLayout.CENTER);

        JButton decryptBtn = new JButton("Decrypt and View");
        decryptBtn.setIcon(new ImageIcon("images/decryption.png"));
        decryptBtn.setFont(fontBold);
        decryptBtn.setPreferredSize(new Dimension(200, 40));
        decryptBtn.setBackground(brightOrange);
        decryptBtn.setForeground(whiteSmoke);

        decryptBtn.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {

                // Decrypt the encrypted note
                String decryptedNoteString = null;
                try{
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, UserApplication.key);
                    byte[] decryptedMessage = cipher.doFinal(addedConsultation.getConsultNotes());
                    decryptedNoteString = new String(decryptedMessage);
                    //System.out.println(decryptedNoteString);
                } catch (Exception ee){
                    ee.printStackTrace();
                }
                consultNotesLbl.setText("                Decrypted Notes: " + decryptedNoteString);
                consultImageLbl.setText("                Decrypted Images:");

                // Decrypt all the encrypted images
                if (addedConsultation.getConsultImages() == null) {
                    consultImageLbl.setText("                Decrypted Images: NONE");
                    decryptBtn.setEnabled(false);
                    return;
                }

                byte[] decryptedImage = new byte[0];
                try{
                    Cipher cipher = Cipher.getInstance("AES");
                    cipher.init(Cipher.DECRYPT_MODE, UserApplication.key);
                    for (byte[] tempBytes : addedConsultation.getConsultImages()){
                        decryptedImage = cipher.doFinal(tempBytes);
                        JLabel consultImageLbl = new JLabel();
                        ImageIcon skinImage = new ImageIcon(decryptedImage);
                        Image scaledImage = skinImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // scaling image, resizing to a smaller size
                        ImageIcon resizedImage = new ImageIcon(scaledImage);
                        consultImageLbl.setIcon(resizedImage);
                        imagesPanel.add(consultImageLbl); // adding the image to the panel for the user to see
                    }

                    // Used temporarily to confirm the decryption was working
                        //System.out.println(Arrays.toString(decryptedImage));

                } catch (Exception ee){
                    ee.printStackTrace();
                }

                decryptBtn.setEnabled(false); // disabling the decrypt button so once the user decrypt the images, button cannot be pressed again

            }
        });

        menuBtn = new JButton(" Home");
        menuBtn.setIcon(new ImageIcon("images/home.png"));
        menuBtn.setFont(fontBold);
        menuBtn.setPreferredSize(new Dimension(130, 40));
        menuBtn.setBackground(brightOrange);
        menuBtn.setForeground(whiteSmoke);
        buttonsPanel.add(menuBtn);
        buttonsPanel.add(decryptBtn);
        menuBtn.addActionListener(new goHomeListener());

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Home button listener used to go back to the main menu
     */
    private class goHomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new SkinConsultationCentreGUI();
        }
    }
}
