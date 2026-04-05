import javax.swing.*;
import java.awt.*;

public abstract class BaseForm extends JFrame {

    protected JButton btnBack;

    public BaseForm(String title) {
        setTitle(title);
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            dispose(); // Close current window
            onBack();  // Allow subclass to define where to go
        });
    }

    // Subclass must implement this
    protected abstract void onBack();
}
