import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class IncomeReportForm extends BaseForm {

    private JTable incomeTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> monthCombo;
    private final JButton btnGenerate;

    public IncomeReportForm() {
        super("Monthly Income Report");

        //frame design
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

        Font font     = new Font("Times New Roman", Font.PLAIN, 14);
        Font bold     = new Font("Times New Roman", Font.BOLD, 16);
        Font subBold  = new Font("Times New Roman", Font.BOLD, 14);

        // Header
        gbc.gridy = 0;
        JLabel header = new JLabel("Monthly Income Report");
        header.setFont(bold);
        content.add(header, gbc);

        // Month selector
        gbc.gridy++;
        JLabel monthLbl = new JLabel("Select Month (MM/YYYY):");
        monthLbl.setFont(subBold);
        content.add(monthLbl, gbc);

        gbc.gridy++;
        monthCombo = new JComboBox<>();
        populateMonthCombo();                 // ← fills it from Main.billList
        JPanel monthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        monthPanel.add(monthCombo);
        content.add(monthPanel, gbc);

        // Table
        gbc.gridy++;
        tableModel = new DefaultTableModel(
                new String[]{"Subject", "Unit Price", "Student Count", "Total"}, 0);
        incomeTable = new JTable(tableModel);
        incomeTable.setFont(font);
        incomeTable.getTableHeader().setFont(subBold);
        JScrollPane tableScroll = new JScrollPane(incomeTable);
        tableScroll.setPreferredSize(new Dimension(400, 200));
        content.add(tableScroll, gbc);

        // Buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnGenerate = new JButton("Generate Report");
        styleButton(btnGenerate, font, new Color(0, 123, 255));
        styleButton(btnBack,     font, new Color(108, 117, 125));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnGenerate);
        content.add(buttonPanel, gbc);

        //actions
        btnGenerate.addActionListener(e -> generateReport());
        btnBack.addActionListener(e -> dispose());

        // close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 14));

                int res = JOptionPane.showConfirmDialog(
                        IncomeReportForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION) {
                    JLabel bye = new JLabel("Thank you for using this system!");
                    bye.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(
                            IncomeReportForm.this,
                            bye,
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        });

        setVisible(true);
    }

    //shows only available month in combo box
    private void populateMonthCombo() {
        Set<String> months = new TreeSet<>();

        for (Bill bill : Main.billList) {
            String[] attr = bill.getAttributes();
            if (!"P".equalsIgnoreCase(attr[7])) continue;            // keep only paid bills

            String mmYYYY = attr[2].trim();
            if (mmYYYY.matches("\\d{2}/\\d{4}")) months.add(mmYYYY);
        }

        if (months.isEmpty()) {
            monthCombo.addItem("No data");
            monthCombo.setEnabled(false);
            JLabel message = new JLabel("No income data found.");
            message.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, message);
        } else {
            for (String m : months) monthCombo.addItem(m);
        }
    }

    //subject map
    private Map<String, Subject> buildSubjectMap() {
        Map<String, Subject> map = new HashMap<>();
        for (Subject s : Main.subjectList) map.put(s.getID(), s);
        return map;
    }

    //generate report
    private void generateReport() {
        String selected = (String) monthCombo.getSelectedItem();
        if (selected == null || selected.equals("No data")) return;

        YearMonth target = YearMonth.parse(selected, DateTimeFormatter.ofPattern("MM/yyyy"));

        Map<String, IncomeDetail> data =
                calculateRangeIncome(target, target, buildSubjectMap());

        tableModel.setRowCount(0);
        int grandTotal = 0;

        for (Map.Entry<String, IncomeDetail> e : data.entrySet()) {
            IncomeDetail d = e.getValue();
            int subTotal = (int) d.total;
            tableModel.addRow(new Object[]{
                    e.getKey(), "RM " + d.fee, d.count, "RM " + subTotal
            });
            grandTotal += subTotal;
        }
        tableModel.addRow(new Object[]{"", "", "Total", "RM " + grandTotal});

        if (data.isEmpty()) {
            JLabel message = new JLabel("No data found for selected month.");
            message.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, message);
        }
    }

    //calculate range income
    private Map<String, IncomeDetail> calculateRangeIncome(
            YearMonth from, YearMonth to, Map<String, Subject> subjectMap) {

        Map<String, IncomeDetail> result = new HashMap<>();

        for (Bill b : Main.billList) {
            String[] a = b.getAttributes();

            if (!"P".equalsIgnoreCase(a[7].trim())) continue;           // only paid bills

            YearMonth billMonth;
            try {  billMonth = YearMonth.parse(a[2].trim(), DateTimeFormatter.ofPattern("MM/yyyy")); }
            catch (Exception ex) { continue; }

            if (billMonth.isBefore(from) || billMonth.isAfter(to)) continue;

            // up to 3 subjects per bill (attr[3], attr[4], attr[5])
            for (int i = 3; i <= 5; i++) {
                String subID = a[i].trim();
                if (subID.isEmpty() || subID.equals("*")) continue;

                Subject s = subjectMap.get(subID);
                if (s == null) continue;

                double fee = s.getFees();

                result.compute(s.getTitle(), (k, v) ->
                        v == null
                                ? new IncomeDetail(fee, 1)
                                : new IncomeDetail(v.total + fee, v.count + 1, fee));
            }
        }
        return result;
    }

    private void styleButton(JButton b, Font f, Color c) {
        b.setFont(f);
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(c.darker()); }
            public void mouseExited  (MouseEvent e) { b.setBackground(c);         }
        });
    }

    //data holder
    static class IncomeDetail {
        double total;
        int    count;
        double fee;      // unit price

        IncomeDetail(double total, int count, double fee) {
            this.total = total;
            this.count = count;
            this.fee   = fee;
        }
        IncomeDetail(double fee, int count) {
            this(fee, count, fee);
        }
    }

    @Override protected void onBack() {
        dispose();
        new AdminMainMenu();
    }
}
