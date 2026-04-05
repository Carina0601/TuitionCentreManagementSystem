import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TutorManagement {

    private static final String FILE_NAME = "./txtTCMS/tutor.txt";
    private static final String SUBJECT_FILE = "./txtTCMS/subject.txt";


    // Save tutor using CommonMethods
    public static void saveTutors(List<PersonalInfo> tutors) {
        CommonMethods.writeFile(FILE_NAME, new ArrayList<>(tutors));
        System.out.println("Successfully saved " + tutors.size() + " tutors.");
    }

    // Save subjects using CommonMethods
    public static void saveSubjects(List<Subject> subjects) {
        CommonMethods.writeFile(SUBJECT_FILE, new ArrayList<>(subjects));
        System.out.println("Successfully saved " + subjects.size() + " subjects.");
    }
}
