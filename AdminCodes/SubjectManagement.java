import java.util.ArrayList;
import java.util.List;

public class SubjectManagement{

    private static final String SUBJECT_FILE = "./txtTCMS/subject.txt";


    // Save subjects using CommonMethods
    public static void saveSubjects(List<Subject> subjects) {
        CommonMethods.writeFile(SUBJECT_FILE, new ArrayList<>(subjects));
        System.out.println("Successfully saved " + subjects.size() + " subjects.");


    }
}
