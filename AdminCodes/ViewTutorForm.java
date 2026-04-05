import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ViewTutorForm extends BaseForm {

    private JTable tutorTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private JTextField searchField;
    private List<PersonalInfo> tutorList;

    public ViewTutorForm() {
        super("View Tutors");

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30, 8, 30);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        Font tnrFont = new Font("Times New Roman", Font.BOLD, 14);
        Font tnrBold = new Font("Times New Roman", Font.BOLD, 16);
        Font tableFont = new Font("Times New Roman", Font.PLAIN,14 );

        // Header
        gbc.gridy = 0;
        JLabel header = new JLabel("View All Tutors");
        header.setFont(tnrBold);
        content.add(header, gbc);

        // Search Field
        gbc.gridy++;
        JLabel search = new JLabel("Search by ID or Name:");
        search.setFont(tnrFont);
        content.add(search, gbc);


        gbc.gridy++;
        searchField = new JTextField();
        searchField.setFont(tnrFont);
        content.add(searchField, gbc);

        // Table
        gbc.gridy++;
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "IC", "Email", "Phone", "Address"}, 0);
        tutorTable = new JTable(tableModel);
        tutorTable.setEnabled(false);
        rowSorter = new TableRowSorter<>(tableModel);
        tutorTable.setRowSorter(rowSorter);

        tutorTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tutorTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        tutorTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        tutorTable.getColumnModel().getColumn(2).setPreferredWidth(110);
        tutorTable.getColumnModel().getColumn(3).setPreferredWidth(140);
        tutorTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        tutorTable.getColumnModel().getColumn(5).setPreferredWidth(600);

        tutorTable.setFont(tableFont);
        tutorTable.getTableHeader().setFont(tnrFont);



        JScrollPane tableScroll = new JScrollPane(tutorTable);
        tableScroll.setPreferredSize(new Dimension(400, 200));
        content.add(tableScroll, gbc);

        // Back Button
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);
        styleButton(btnBack, tnrFont, new Color(108, 117, 125));
        buttonPanel.add(btnBack);
        content.add(buttonPanel, gbc);

        btnBack.addActionListener(e -> {
            onBack();

        });

        // Live search listener
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        ViewTutorForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            ViewTutorForm.this,
                            exitMsg,
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        });

        loadTutors();
        setVisible(true);
    }

    private void styleButton(JButton button, Font font, Color color) {
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    private void loadTutors() {
        tutorList = Main.tutorList;
        tableModel.setRowCount(0);
        for (PersonalInfo tutor : tutorList) {
            String[] attributes = {tutor.getID(),tutor.getName(),tutor.getIC(),tutor.getEmail(),tutor.getPhoneNo(),
                    tutor.getAddress().replace("\"","")};
            tableModel.addRow(attributes);
        }
    }

    private void filterTable() {
        String text = searchField.getText();
        if (text.trim().isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1)); // Filter ID and Name
        }
    }

    @Override
    protected void onBack() {
        dispose();
    }
}
