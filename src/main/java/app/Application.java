package app;

import io.github.humbleui.jwm.*;
import io.github.humbleui.jwm.skija.EventFrameSkija;
import io.github.humbleui.skija.Surface;

import java.io.File;
import java.util.function.Consumer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import static app.Colors.APP_BACKGROUND_COLOR;
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static javax.swing.text.StyleConstants.setBackground;

public class Application implements Consumer<Event> {
    
    private final Window window;

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int DELAY = 140;

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    
    public Application() {
        // создаём окно
        window = App.makeWindow();
        // задаём обработчиком событий текущий объект
        window.setEventListener(this);
        // задаём заголовок
        window.setTitle("Java 2D");
        // задаём размер окна
        window.setWindowSize(900, 900);
        // задаём его положение
        window.setWindowPosition(100, 100);
        // задаём иконку

        switch (Platform.CURRENT) {
            case WINDOWS -> window.setIcon(new File("src/main/resources/windows.ico"));
            case MACOS -> window.setIcon(new File("src/main/resources/macos.icns"));
        }

        // названия слоёв, которые будем перебирать
        String[] layerNames = new String[]{
                "LayerGLSkija", "LayerRasterSkija"
        };

        // перебираем слои
        for (String layerName : layerNames) {
            String className = "io.github.humbleui.jwm.skija." + layerName;
            try {
                Layer layer = (Layer) Class.forName(className).getDeclaredConstructor().newInstance();
                window.setLayer(layer);
                break;
            } catch (Exception e) {
                System.out.println("Ошибка создания слоя " + className);
            }
        }

        // если окну не присвоен ни один из слоёв
        if (window._layer == null)
            throw new RuntimeException("Нет доступных слоёв для создания");

        // делаем окно видимым
        window.setVisible(true);
    }
    private void initApplication() {

        addKeyListener(new Board.TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void setBackground(Color black) {
    }

    @Override
    public void accept(Event e) {
        // если событие - это закрытие окна
        if (e instanceof EventWindowClose) {
            // завершаем работу приложения
            App.terminate();
        } else if (e instanceof EventWindowCloseRequest) {
            window.close();
        }else if (e instanceof EventFrameSkija ee) {
            Surface s = ee.getSurface();
            s.getCanvas().clear(APP_BACKGROUND_COLOR);
        }
    }
}
