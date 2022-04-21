import app.Application;
import io.github.humbleui.jwm.App;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public Main() {

        initUI();
    }

    private void initUI() {

        add(new Board());

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        App.start(Application::new);
    }
    EventQueue.invokeLater(() -> {
        JFrame ex = new JFrame();
        ex.setVisible(true);
    });
}

