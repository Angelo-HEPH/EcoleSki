package be.iannel.iannelloecoleski.JFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import be.iannel.iannelloecoleski.models.*;

public class BookingFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public BookingFrame() {
        setTitle("Gestion des bookings");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setContentPane(panel);

        JButton addButton = new JButton("Ajouter un booking");
        JButton searchButton = new JButton("Rechercher par ID");
        JButton getAllButton = new JButton("Afficher tous les bookings");
        JButton deleteButton = new JButton("Supprimer un booking");
        JButton mainPageButton = new JButton("Page principale");

        panel.add(addButton);   
        panel.add(searchButton);
        panel.add(getAllButton); 
        panel.add(deleteButton);
        panel.add(mainPageButton);

        addButton.addActionListener(e -> showAddBookingForm());

        searchButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID du booking:");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Booking booking = Booking.getBookingById(id);
                    if (booking != null) {
                        JOptionPane.showMessageDialog(this, booking.toString(), "Booking trouvé", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Aucun booking trouvé avec l'ID " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide");
                }
            }
        });

        getAllButton.addActionListener(e -> {
            List<Booking> bookings = Booking.getAllBooking();

            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun booking trouvé");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Booking b : bookings) {
                    sb.append(b.toString()).append("\n----------------------\n");
                }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(650, 350));

                JOptionPane.showMessageDialog(this, scrollPane, "Tous les bookings", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            List<Booking> bookings = Booking.getAllBooking();

            if (bookings.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun booking à supprimer");
                return;
            }

            JComboBox<Booking> bookingCombo = new JComboBox<>();
            for (Booking b : bookings) {
                bookingCombo.addItem(b);
            }

            int result = JOptionPane.showConfirmDialog(this, bookingCombo, 
                    "Sélectionnez le booking à supprimer", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                Booking selectedBooking = (Booking) bookingCombo.getSelectedItem();
                if (selectedBooking != null) {
                    if (selectedBooking.deleteBooking(selectedBooking.getId())) {
                        JOptionPane.showMessageDialog(this, "Booking supprimé avec succès");
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la suppression");
                    }
                }
            }
        });

        mainPageButton.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            this.dispose();
        });
    }

    private void showAddBookingForm() {

        JCheckBox discountBox = new JCheckBox("Remise");

        List<Period> periods = Period.getAllPeriods();
        JComboBox<Period> periodCombo = new JComboBox<>();
        for (Period p : periods) periodCombo.addItem(p);
        periodCombo.setPreferredSize(new Dimension(800, periodCombo.getPreferredSize().height));

        List<Lesson> lessons = Lesson.getAllLessons();
        JComboBox<Lesson> lessonCombo = new JComboBox<>();
        for (Lesson l : lessons) lessonCombo.addItem(l);
        lessonCombo.setPreferredSize(new Dimension(800, lessonCombo.getPreferredSize().height));

        List<Skier> skiers = Skier.getAllSkiers();
        JComboBox<Skier> skierCombo = new JComboBox<>();
        for (Skier s : skiers) skierCombo.addItem(s);
        skierCombo.setPreferredSize(new Dimension(800, skierCombo.getPreferredSize().height));

        List<Instructor> instructors = Instructor.getAllInstructors();
        JComboBox<Instructor> instructorCombo = new JComboBox<>();
        for (Instructor i : instructors) instructorCombo.addItem(i);
        instructorCombo.setPreferredSize(new Dimension(800, instructorCombo.getPreferredSize().height));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;


        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Remise:"), gbc);
        gbc.gridx = 1;
        form.add(discountBox, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Période:"), gbc);
        gbc.gridx = 1;
        form.add(periodCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Leçon:"), gbc);
        gbc.gridx = 1;
        form.add(lessonCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Skieur:"), gbc);
        gbc.gridx = 1;
        form.add(skierCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Instructeur:"), gbc);
        gbc.gridx = 1;
        form.add(instructorCombo, gbc);

        int result = JOptionPane.showConfirmDialog(this, form, "Ajouter un booking", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Booking booking = new Booking();
                booking.setHasInsurance(false);
                booking.setDiscount(discountBox.isSelected());
                booking.setPeriod((Period) periodCombo.getSelectedItem());
                booking.setLesson((Lesson) lessonCombo.getSelectedItem());
                booking.setSkier((Skier) skierCombo.getSelectedItem());
                booking.setInstructor((Instructor) instructorCombo.getSelectedItem());

                if (booking.create()) {
                    JOptionPane.showMessageDialog(this, "Booking ajouté avec succès");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingFrame().setVisible(true));
    }
}
