package be.iannel.iannelloecoleski.JFrame;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainFrame() {
        setTitle("École de Ski - Gestion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton lessonsButton = new JButton("Gérer les cours");
        JButton skiersButton = new JButton("Gérer les élèves");
        JButton instructorsButton = new JButton("Gérer les moniteurs");
        JButton bookingsButton = new JButton("Gérer les réservations");
        JButton exitButton = new JButton("Quitter");

        mainPanel.add(lessonsButton);
        mainPanel.add(skiersButton);
        mainPanel.add(instructorsButton);
        mainPanel.add(bookingsButton);
        mainPanel.add(exitButton);

        add(mainPanel);

        exitButton.addActionListener(e -> System.exit(0));

        lessonsButton.addActionListener(e -> {
            LessonFrame lessonFrame = new LessonFrame();
            lessonFrame.setVisible(true);
            this.dispose();
        });

        bookingsButton.addActionListener(e -> {
            BookingFrame bookingFrame = new BookingFrame();
            bookingFrame.setVisible(true);
            this.dispose();
        });


        instructorsButton.addActionListener(e -> {
            InstructorFrame instructorFrame = new InstructorFrame();
            instructorFrame.setVisible(true);
            this.dispose();
        });
        
        skiersButton.addActionListener(e -> {
            SkierFrame skierFrame = new SkierFrame();
            skierFrame.setVisible(true);
            this.dispose();
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
