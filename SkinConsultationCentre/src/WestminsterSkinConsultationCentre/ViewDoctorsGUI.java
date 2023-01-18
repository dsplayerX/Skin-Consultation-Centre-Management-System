package WestminsterSkinConsultationCentre;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * View doctors page of GUI application
 */
public class ViewDoctorsGUI extends JFrame{
    private ArrayList<Doctor> docListGUI;
    private JTable docTable;
    private DefaultTableModel docTableModel;
    private JLabel title;
    private JButton goBackBtn, sortBtn;
    private JPanel titlePanel, docListPanel, buttonsPanel;

    /**
     * Creates a new ViewDoctorsGUI
     */
    public ViewDoctorsGUI(){

        // Fonts and Colors used in this frame
        Font fontTitle = new Font("Inter-Black", Font.BOLD, 42);
        Font fontBold = new Font("Inter", Font.BOLD, 12);
        Font fontBigBold = new Font("Inter", Font.BOLD, 13);
        Color brightOrange = new Color(235, 100, 64);
        Color darkGreen = new Color(73, 113,116);
        Color whiteShade = new Color(214, 228, 229);
        Color whiteSmoke = new Color(239, 245, 245);
        Color lightTeal = new Color(234, 253, 252);

        docListGUI = (ArrayList<Doctor>) UserApplication.managerForGUI.getDoctorList().clone();

        titlePanel = new JPanel();
        titlePanel.setBackground(whiteShade);

        docListPanel = new JPanel();
        docListPanel.setBackground(whiteShade);
        docListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        docListPanel.setLayout(new BorderLayout());

        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(whiteShade);
        buttonsPanel.setBorder(new EmptyBorder(0,10,10,10));

        // Creating the JFrame
        new JFrame("View List of Doctors");
        setLayout(new BorderLayout());
        setSize(1000, 526);
        setResizable(false);


        add(titlePanel, BorderLayout.NORTH);
        add(docListPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        title = new JLabel("List of Doctors");
        title.setFont(fontTitle);
        title.setForeground(darkGreen);
        titlePanel.add(title);

        // doctors table model
        String[] columnNames = {"First Name", "Surname", "Gender", "Specialisation", "Licence No.", "Available Date/Time", "Available until" };
        docTableModel = new DefaultTableModel(null, columnNames);

        docTable = new JTable(docTableModel);
        docTable.getTableHeader().setBackground(darkGreen);
        docTable.setBackground(lightTeal);
        docTable.getTableHeader().setForeground(whiteShade);
        docTable.setFont(fontBold);
        docTable.getTableHeader().setPreferredSize(new Dimension(85,30));
        docTable.setRowHeight(30);
        docTable.getTableHeader().setFont(fontBigBold);
        docTable.setAutoCreateRowSorter(true);

        // making sure the table is scrollable if it exceeds the given dimensions
        JScrollPane docTablePane = new JScrollPane(docTable);
        docTablePane.setPreferredSize(new Dimension(950,300));
        docTablePane.getViewport().setBackground(lightTeal);
        docListPanel.add(docTablePane, BorderLayout.CENTER);

        goBackBtn = new JButton(" Back");
        goBackBtn.setIcon(new ImageIcon("images/back.png"));
        goBackBtn.setFont(fontBold);
        goBackBtn.setForeground(whiteSmoke);
        goBackBtn.setPreferredSize(new Dimension(120, 50));
        goBackBtn.setBackground(brightOrange);

        sortBtn = new JButton("Sort Alphabetically");
        sortBtn.setIcon(new ImageIcon("images/sort.png"));
        sortBtn.setFont(fontBold);
        sortBtn.setForeground(whiteSmoke);
        sortBtn.setPreferredSize(new Dimension(200, 50));
        sortBtn.setBackground(brightOrange);

        buttonsPanel.add(goBackBtn);
        buttonsPanel.add(sortBtn);

        goBackBtn.addActionListener(new goBackListener());
        sortBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                docTable.getRowSorter().toggleSortOrder(1);
            }
        });

        initDocListTable(); // calling initialising of the table

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialising the doctors table
     */
    private void initDocListTable(){
        for (Doctor doctor : docListGUI) {
            String fName = doctor.getfName();
            String surname = doctor.getSurname();
            String gender = doctor.getGender();
            String specialisation = doctor.getSpecialisation();
            String medLiNo = doctor.getMedLicenceNum();

            LocalDateTime availableStart;
            LocalDateTime availableEnd;
            String formattedStart;
            String formattedEnd;

            if (doctor.getAvailableStartDateTime() != null){
                availableStart = doctor.getAvailableStartDateTime();
                availableEnd = doctor.getAvailableEndDateTime();
                formattedStart = DateTimeFormatter.ofPattern("   yyyy-MM-dd  HH:mm").format(availableStart);
                formattedEnd = DateTimeFormatter.ofPattern("   yyyy-MM-dd  HH:mm").format(availableEnd);
            } else { // if doctor doesn't have a consultation time, shows user that there isn't a timeslot
                formattedStart = "Not Specified!";
                formattedEnd = "Not Specified!";
            }

            // Adding data to the table
            Object[] data = {"    " + fName,"   " + surname,"   " + gender," " + specialisation,"   " + medLiNo," " + formattedStart," " + formattedEnd};
            docTableModel.addRow(data);
        }
        docTable.repaint(); // repainting the table
    }

    /**
     * Back button action listener used to go back to the main menu
     */
    private class goBackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new SkinConsultationCentreGUI();

        }
    }
}
