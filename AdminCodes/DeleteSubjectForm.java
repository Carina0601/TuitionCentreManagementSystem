import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DeleteSubjectForm extends BaseForm {

    private final JTable subjectTable;
    private final DefaultTableModel tableModel;

    public DeleteSubjectForm() {
        super("Delete Subject");

        //layout
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets  = new Insets(8, 30, 8, 30);
        gbc.anchor  = GridBagConstraints.WEST;
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.gridx   = 0;
        gbc.weightx = 1;

        Font titleF = new Font("Times New Roman", Font.BOLD, 16);
        Font btnF   = new Font("Times New Roman", Font.PLAIN, 14);
        Font subBold = new Font("Times New Roman", Font.BOLD, 14);

        //table
        gbc.gridy = 0;
        JLabel header = new JLabel("Delete Subject", SwingConstants.LEFT);
        header.setFont(titleF);
        content.add(header, gbc);

        //table
        gbc.gridy++;
        tableModel = new DefaultTableModel(new String[]{"Subject ID", "Subject Title"}, 0);
        subjectTable = new JTable(tableModel);
        subjectTable.setFont(btnF);
        subjectTable.getTableHeader().setFont(subBold);
        JScrollPane tableScroll = new JScrollPane(subjectTable);
        tableScroll.setPreferredSize(new Dimension(400, 200));
        content.add(tableScroll, gbc);

        //button
        gbc.gridy++;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(Color.WHITE);

        JButton btnDelete = new JButton("Delete Selected Subject");
        styleButton(btnDelete, btnF, new Color(220, 53, 69));
        styleButton(btnBack,  btnF, new Color(108, 117, 125));

        btnPanel.add(btnBack);
        btnPanel.add(btnDelete);
        content.add(btnPanel, gbc);


        loadSubjectsToTable();

        btnDelete.addActionListener(e -> deleteSelectedSubject());
        btnBack.addActionListener(e -> dispose());

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        DeleteSubjectForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            DeleteSubjectForm.this,
                            exitMsg,
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        });

        setVisible(true);
    }

    //style button
    private void styleButton(JButton btn, Font font, Color color) {
        btn.setFont(font);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(color.darker()); }
            @Override public void mouseExited (MouseEvent e) { btn.setBackground(color); }
        });
    }

    private void loadSubjectsToTable() {
        List<Subject> subjects = Main.subjectList;
        tableModel.setRowCount(0);   // clear
        for (Subject s : subjects) {
            tableModel.addRow(new String[]{s.getID(), s.getTitle()});
        }
    }

    private void deleteSelectedSubject() {
        int row = subjectTable.getSelectedRow();
        if (row == -1) {
            JLabel msg = new JLabel("Please select a subject to delete.");
           msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String id    = (String) tableModel.getValueAt(row, 0);
        String title = (String) tableModel.getValueAt(row, 1);

        JLabel msg = new JLabel("Are you sure you want to delete subject: " + title + "?");
        msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

        int confirm = JOptionPane.showConfirmDialog(
                this,
                msg,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );


        if (confirm != JOptionPane.YES_OPTION) return;

        List<Subject> list = Main.subjectList;
        list.removeIf(s -> s.getID().equals(id));
        SubjectManagement.saveSubjects(list);

        loadSubjectsToTable();   // refresh
        JLabel msg1 = new JLabel("Subject deleted successfully.");
        msg1.setFont(new Font("Times New Roman", Font.BOLD, 14));

        JOptionPane.showMessageDialog(
                this,
                msg1,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

    }

    @Override protected void onBack() {
        dispose();
    }

    //test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeleteSubjectForm::new);
    }
}
