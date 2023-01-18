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
 * View booked consultations page of GUI application
 */
public class ViewConsultationsGUI extends JFrame{
    private ArrayList<Consultation> consultationList;
    private JTable consultTable;
    private DefaultTableModel consultTableModel;
    private JLabel title;
    private JButton sortBtn, goBackBtn;
    private JPanel titlePanel, consultListPanel, buttonsPanel;

    /**
     * Creates a new ViewConsultationsGUI
     */
    public ViewConsultationsGUI(){

        // Fonts and Colors used in this frame
        Font fontTitle = new Font("Inter-Black", Font.BOLD, 42);
        Font fontBold = new Font("Inter", Font.BOLD, 12);
        Font fontBigBold = new Font("Inter", Font.BOLD, 13);
        Color brightOrange = new Color(235, 100, 64);
        Color darkGreen = new Color(73, 113,116);
        Color whiteShade = new Color(214, 228, 229);
        Color whiteSmoke = new Color(239, 245, 245);
        Color lightTeal = new Color(234, 253, 252);

        consultationList = UserApplication.consultations; // getting the consultation arraylist and assigning it to a local variable

        titlePanel = new JPanel();
        titlePanel.setBackground(whiteShade);

        consultListPanel = new JPanel();
        consultListPanel.setBackground(whiteShade);
        consultListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        consultListPanel.setLayout(new BorderLayout());

        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(whiteShade);

        // Creating the JFrame
        new JFrame("Booked Consultations");
        setLayout(new BorderLayout());
        setSize(750, 500);
        setResizable(false);

        add(titlePanel, BorderLayout.NORTH);
        add(consultListPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        title = new JLabel("Booked Consultations");
        title.setFont(fontTitle);
        title.setForeground(darkGreen);
        titlePanel.add(title);

        // consultations table model
        String[] col = {"Doctor", "Patient", "Date", "Time", "Duration"};
        consultTableModel = new DefaultTableModel(null, col);

        consultTable = new JTable(consultTableModel);
        consultTable.getTableHeader().setBackground(darkGreen);
        consultTable.setBackground(lightTeal);
        consultTable.getTableHeader().setForeground(Color.white);
        consultTable.setFont(fontBold);
        consultTable.getTableHeader().setPreferredSize(new Dimension(80,30));
        consultTable.setRowHeight(30);
        consultTable.getTableHeader().setFont(fontBigBold);
        consultTable.setAutoCreateRowSorter(true);

        JScrollPane consultPane = new JScrollPane(consultTable); // making sure the table is scrollable if it exceeds the given dimensions
        consultPane.setPreferredSize(new Dimension(900,330));
        consultPane.getViewport().setBackground(lightTeal);
        consultListPanel.add(consultPane, BorderLayout.CENTER);

        goBackBtn = new JButton("Back");
        buttonsPanel.add(goBackBtn);
        goBackBtn.setIcon(new ImageIcon("images/back.png"));
        goBackBtn.setFont(fontBold);
        goBackBtn.setForeground(whiteSmoke);
        goBackBtn.setPreferredSize(new Dimension(120, 50));
        goBackBtn.setBackground(brightOrange);
        goBackBtn.addActionListener(new goBackListener());

        initConsultListTable(); // initialising consultation table

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Initialising the consultations table
     */
    private void initConsultListTable(){
        for (Consultation consultation : consultationList) {
            String doctorSurname = consultation.getAllocDoc().getSurname();
            String patientSurname = consultation.getAllocPatient().getSurname();
            LocalDateTime datetime = consultation.getConsultDateTime();
            String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(datetime);
            String time = DateTimeFormatter.ofPattern("HH:mm").format(datetime);
            String duration = consultation.getDuration() + " hour(s)";

            Object[] data = {doctorSurname, patientSurname, date, time, duration};
            consultTableModel.addRow(data); // adding consultation data to a row
        }
        consultTable.repaint();
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
