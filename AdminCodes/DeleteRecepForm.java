import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DeleteRecepForm extends BaseForm {

    private JTable recepTable;
    private DefaultTableModel tableModel;
    private JButton btnDelete;

    public DeleteRecepForm() {
        super("Delete Receptionist");

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

        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        Font bold = new Font("Times New Roman", Font.BOLD, 16);

        //Header
        gbc.gridy = 0;
        JLabel header = new JLabel("Delete Receptionist");
        header.setFont(bold);
        content.add(header, gbc);

        //Table
        gbc.gridy++;
        tableModel = new DefaultTableModel(new String[]{"Receptionist ID", "Name"}, 0);
        recepTable = new JTable(tableModel);
        recepTable.setFont(font);
        recepTable.getTableHeader().setFont(bold);
        JScrollPane tableScroll = new JScrollPane(recepTable);
        tableScroll.setPreferredSize(new Dimension(400, 200));
        content.add(tableScroll, gbc);

        //Button panel
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnDelete = new JButton("Delete Selected Receptionist");
        styleButton(btnDelete, font, new Color(220, 53, 69));
        styleButton(btnBack, font, new Color(108, 117, 125));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnDelete);
        content.add(buttonPanel, gbc);

        //Load Receptionists
        loadRecepsToTable();

        //Action Listener
        btnDelete.addActionListener(e -> deleteSelectedRecep());
        btnBack.addActionListener(e -> {
            dispose();
        });

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        DeleteRecepForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            DeleteRecepForm.this,
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

    private void styleButton(JButton button, Font font, Color color) {
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button.setBackground(color);
            }
        });

    }

    private void loadRecepsToTable() {
        List<PersonalInfo> receptionists = Main.recepList;
        tableModel.setRowCount(0); //Clear table

        for (PersonalInfo recep : receptionists) {
            tableModel.addRow(new String[]{recep.getID(), recep.getAttributes()[1]});
        }
    }

    private void deleteSelectedRecep() {
        int selectedRow = recepTable.getSelectedRow();
        if (selectedRow == -1) {
            JLabel msg = new JLabel("Please select a receptionist to delete.");
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, msg,
                    "Select A Receptionist",JOptionPane.WARNING_MESSAGE);
            return;
        }

        String recepIdToDelete = (String) tableModel.getValueAt(selectedRow, 0);
        String recepNameToDelete = (String) tableModel.getValueAt(selectedRow, 1);

        JLabel confirmLabel = new JLabel("Are you sure you want to delete receptionist: " + recepNameToDelete + "?");
        confirmLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

        int confirm = JOptionPane.showConfirmDialog(
                this,
                confirmLabel,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );


        if (confirm == JOptionPane.YES_OPTION) {
            List<PersonalInfo> receptionists = Main.recepList;
            receptionists.removeIf(recep -> recep.getAttributes()[0].equals(recepIdToDelete));
            RecepManagement.saveRecep(receptionists);
            loadRecepsToTable();
            JLabel msg = new JLabel("Receptionist deleted successfully.");
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }
    }

    @Override
    protected void onBack() {
        dispose();
    }
}
