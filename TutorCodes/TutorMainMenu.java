import javax.swing.*;
import java.awt.*;

public class TutorMainMenu {
    private JButton profileButton;
    private JButton manageStudentsButton;
    private JButton manageSubjectsButton;
    private JPanel mainPanel;
    private JButton logOutButton;
    private JButton manageAttendanceButton;
    private Container app;



    public TutorMainMenu(Container app) {
        this.app = app;
        JFrame frame = app.getFrame();
        String id = TutorMethods.getTutorID();

        profileButton.addActionListener( e -> {
            frame.setVisible(false);
            CommonMethods.userProfile(frame, "txtTCMS/tutor.txt", id, Main.tutorList, Main.tutorList);

        });

        manageSubjectsButton.addActionListener( e -> {
            app.showCard("ManageSubjects");
        });

        manageStudentsButton.addActionListener(( e ->{
            app.showCard("ManageStudents");
        }));

        manageAttendanceButton.addActionListener(( e ->{
            app.showCard("ManageAttendance");
        }));

        logOutButton.addActionListener( e -> {

            JLabel message = new JLabel("Are you sure you want to log out?");
            message.setFont(new Font("Times New Roman", Font.BOLD, 16));
            int result = JOptionPane.showConfirmDialog(
                    frame,
                    message,
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                frame.setVisible(false);
                SwingUtilities.invokeLater(() -> new Main().showMainMenu());
            }

        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }




}
