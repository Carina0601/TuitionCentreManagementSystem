import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Container {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private MySubjects mySubjects;
    private TutorMainMenu tutorMainMenu;
    private ManageSubjects manageSubjects;
    private ViewSubject viewSubject;
    private SubjectModifications subjectModifications;
    private ManageStudents manageStudents;
    private StudentForm studentForm;
    private ManageAttendance manageAttendance;
    private AttendanceForm attendanceForm;
    private AttendanceFunction attendanceFunction;
    private ViewAttendance viewAttendance;
    private AttendanceModification attendanceModification;

    public Container() {
        frame = new JFrame("Advanced Tuition Centre (ATC)");
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        tutorMainMenu = new TutorMainMenu(this);
        manageSubjects = new ManageSubjects(this);
        viewSubject = new ViewSubject(this);
        mySubjects = new MySubjects(this);
        subjectModifications = new SubjectModifications(this);
        manageStudents = new ManageStudents(this);
        studentForm = new StudentForm(this);
        manageAttendance = new ManageAttendance(this);
        attendanceForm = new AttendanceForm(this);
        attendanceFunction = new AttendanceFunction(this);
        viewAttendance = new ViewAttendance(this);
        attendanceModification = new AttendanceModification(this);

        cardPanel.add(tutorMainMenu.getMainPanel(), "TutorMainMenu");
        cardPanel.add(manageSubjects.getMainPanel(), "ManageSubjects");
        cardPanel.add(viewSubject.getMainPanel(), "ViewSubject");
        cardPanel.add(mySubjects.getMainPanel(), "MySubjects");
        cardPanel.add(subjectModifications.getMainPanel(), "SubjectModifications");
        cardPanel.add(manageStudents.getMainPanel(), "ManageStudents");
        cardPanel.add(studentForm.getMainPanel(), "StudentForm");
        cardPanel.add(manageAttendance.getMainPanel(), "ManageAttendance");
        cardPanel.add(attendanceForm.getMainPanel(), "AttendanceForm");
        cardPanel.add(attendanceFunction.getMainPanel(),"AttendanceFunction");
        cardPanel.add(viewAttendance.getMainPanel(),"ViewAttendance");
        cardPanel.add(attendanceModification.getMainPanel(),"AttendanceModification");

        frame.setContentPane(cardPanel);
        frame.setSize(550, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JLabel confirmMessage = new JLabel("Confirm to exit the system?");
                confirmMessage.setFont(new Font("Times New Roman", Font.BOLD, 14));

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        confirmMessage,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (result == JOptionPane.OK_OPTION) {
                    JLabel exitMessage = new JLabel("Thank you for using this system!");
                    exitMessage.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(
                            frame,
                            exitMessage,
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }

            }
        });



    }

    public void showCard(String name) {
        switch (name) {
            case "TutorMainMenu":
                break;

            case "MySubjects":
                if (mySubjects != null) mySubjects.refresh();
                break;

            case "ViewSubject":
                if (viewSubject != null) viewSubject.refresh();
                break;

            case "ManageSubjects":
                break;

            case "SubjectModifications":
                if (subjectModifications != null) subjectModifications.refresh();
                break;

            case "ManageStudents":
                if(manageStudents != null) manageStudents.refresh();

            case "StudentForm":
                if(studentForm !=null) studentForm.refresh();
                break;

            case "ManageAttendance":
                if (manageAttendance !=null) manageAttendance.refresh();
                break;

            case"AttendanceForm":
                if (attendanceForm !=null) attendanceForm.refresh();

            case "AttendanceFunction":
                if (attendanceFunction !=null) attendanceFunction.refresh();
                break;

            case "ViewAttendance":
                if (viewAttendance !=null) viewAttendance.refresh();

            case "AttendanceModification":
                if (attendanceModification !=null) attendanceModification.refresh();

        }
        cardLayout.show(cardPanel, name);
    }
    public JFrame getFrame() {
        return frame;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(Container::new);
    }
}


