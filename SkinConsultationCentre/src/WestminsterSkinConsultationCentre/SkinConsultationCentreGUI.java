package WestminsterSkinConsultationCentre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Home page of the GUI application
 */
public class SkinConsultationCentreGUI extends JFrame{
    private JPanel titlePanel, imagePanel, buttonPanel;
    private JButton viewDoctorBtn, addConsultationBtn, viewConsultationBtn;

    /**
     * Creates a new SkinConsultationCentreGUI
     */
    public SkinConsultationCentreGUI() {

        // Fonts and Colors used in this frame
        Font fontTitle = new Font("Inter-Black", Font.BOLD, 42);
        Font fontBold = new Font("Inter", Font.BOLD, 12);
        Color brightOrange = new Color(235, 100, 64);
        Color darkGreen = new Color(73, 113,116);
        Color whiteShade = new Color(214, 228, 229);
        Color whiteSmoke = new Color(239, 245, 245);

        // Creating the JFrame
        new JFrame("Skin Consultation Centre");
        setSize(700, 600);
        setResizable(false);
        setLayout(new BorderLayout());

        titlePanel = new JPanel();
        titlePanel.setBackground(whiteShade);
        titlePanel.setBorder(new EmptyBorder(20,10,10,10));

        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(whiteShade);
        imagePanel.setBorder(new EmptyBorder(10,0,10,0));

        GridLayout bPanelLayout = new GridLayout(1,3);
        bPanelLayout.setHgap(20);
        buttonPanel = new JPanel(bPanelLayout);
        buttonPanel.setBackground(whiteShade);
        buttonPanel.setBorder(new EmptyBorder(10,10,20,10));

        add(titlePanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        JLabel title = new JLabel("Skin Consultation Center");
        title.setFont(fontTitle);
        title.setForeground(darkGreen);
        titlePanel.add(title);

        JLabel image = new JLabel();
        ImageIcon bgImage = new ImageIcon("images/background.jpg");
        image.setIcon(bgImage);
        image.setPreferredSize(imagePanel.getSize());
        image.setHorizontalAlignment(JLabel.CENTER);
        image.setVerticalAlignment(JLabel.CENTER);
        imagePanel.add(image, BorderLayout.CENTER);

        addConsultationBtn = new JButton(" Add Consultation");
        addConsultationBtn.setIcon(new ImageIcon("images/plus.png"));
        addConsultationBtn.setFont(fontBold);
        addConsultationBtn.setForeground(whiteSmoke);
        addConsultationBtn.setPreferredSize(new Dimension(200, 50));
        addConsultationBtn.setBackground(brightOrange);
        addConsultationBtn.setBorder(new EmptyBorder(0,20,0,20));

        viewDoctorBtn = new JButton(" View Doctors");
        viewDoctorBtn.setIcon(new ImageIcon("images/doctor.png"));
        viewDoctorBtn.setFont(fontBold);
        viewDoctorBtn.setForeground(whiteSmoke);
        viewDoctorBtn.setPreferredSize(new Dimension(200, 50));
        viewDoctorBtn.setBackground(brightOrange);
        viewDoctorBtn.setBorder(new EmptyBorder(0,20,0,20));

        viewConsultationBtn = new JButton(" View Consultations");
        viewConsultationBtn.setIcon(new ImageIcon("images/consultation.png"));
        viewConsultationBtn.setFont(fontBold);
        viewConsultationBtn.setForeground(whiteSmoke);
        viewConsultationBtn.setPreferredSize(new Dimension(200, 50));
        viewConsultationBtn.setBackground(brightOrange);
        viewConsultationBtn.setBorder(new EmptyBorder(0,20,0,20));

        buttonPanel.add(addConsultationBtn);
        buttonPanel.add(viewDoctorBtn);
        buttonPanel.add(viewConsultationBtn);

        addConsultationBtn.addActionListener(new addConsultationBtnListener());
        viewDoctorBtn.addActionListener(new viewDoctorsBtnListener());
        viewConsultationBtn.addActionListener(new viewConsultationBtnListener());

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    /**
     * Listener for the view doctors button
     */
    private class viewDoctorsBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new ViewDoctorsGUI();
        }
    }


    /**
     * Listener for the view consultations button
     */
    private class viewConsultationBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new ViewConsultationsGUI();
        }
    }


    /**
     * Listener for the add consultations button
     */
    private class addConsultationBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new AddConsultationGUI();
        }
    }

}
