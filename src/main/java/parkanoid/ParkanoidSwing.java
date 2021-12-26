package parkanoid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ParkanoidSwing extends Parkanoid {

    private final JFrame frame;

    public ParkanoidSwing() {
        frame = new JFrame("Parkanoid");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(640,480);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ParkanoidSwing parkanoid = new ParkanoidSwing();
        parkanoid.init();
        parkanoid.start();
    }

    @Override
    public URL getDocumentBase() {
        try {
            return new URL("classpath:/");
        } catch (MalformedURLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Image getImage(URL url, String name) {
        try {
            return ImageIO.read(new URL(url, name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Container getContentPane() {
        return frame.getContentPane();
    }

    @Override
    public void repaint() {
        frame.repaint();
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        frame.addMouseListener(l);
    }

    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener l) {
        frame.addMouseMotionListener(l);
    }

    @Override
    public int getWidth() {
        return frame.getWidth() - 15;
    }

    @Override
    public int getHeight() {
        return frame.getHeight() - 35;
    }
}
