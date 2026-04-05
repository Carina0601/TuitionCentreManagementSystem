import javax.swing.*;
import java.awt.*;

public class AttendanceFunction {
    private JButton addAttendanceRecordButton;
    private JButton modifyAttendanceRecordButton;
    private JButton backButton;
    private JButton homeButton;
    private Container app;
    private JPanel mainPanel;
    private JButton viewAttendanceRecordsButton;
    private JLabel title;


    public AttendanceFunction(Container app) {
        this.app = app;


        viewAttendanceRecordsButton.addActionListener( e -> {
            app.showCard("ViewAttendance");
        });

        addAttendanceRecordButton.addActionListener(e -> {
            app.showCard("AttendanceForm");
        });

        modifyAttendanceRecordButton.addActionListener(e -> {
            app.showCard("AttendanceModification");
        });

        backButton.addActionListener( e ->{
            app.showCard("ManageAttendance");
        });

        homeButton.addActionListener( e ->{
            app.showCard("TutorMainMenu");
        });

    }
    public void refresh(){
        Subject subject = AdminSelectedSubject.get();

        if (subject != null) {
            title.setText("Attendance Records for " + subject.getTitle());
        } else {
            title.setText("Attendance Records - No Subject Selected");
        }

        title.setFont(new Font("Times New Roman", Font.BOLD, 18));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
