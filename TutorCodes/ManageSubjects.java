import javax.swing.*;

public class ManageSubjects {
    private JPanel mainPanel;
    private JButton mySubjectsButton;
    private JButton viewButton;
    private JButton backButton;
    private Container app;

    public ManageSubjects(Container app) {
        this.app = app;

        viewButton.addActionListener(e -> {
            app.showCard("ViewSubject");
        });

        mySubjectsButton.addActionListener(e -> {
            app.showCard("MySubjects");
        });

        backButton.addActionListener( e ->{
            app.showCard("TutorMainMenu");
        });

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
