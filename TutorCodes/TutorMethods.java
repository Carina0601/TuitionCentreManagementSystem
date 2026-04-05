import javax.swing.*;


public class TutorMethods {

    public static String tutorID;

    public static void mainMenu(String id){
        tutorID = id;

        SwingUtilities.invokeLater(() -> new Container());
    }

    public static String getTutorID(){
        return tutorID;
    }
}
