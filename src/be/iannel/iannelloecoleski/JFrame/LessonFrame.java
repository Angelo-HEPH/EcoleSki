package be.iannel.iannelloecoleski.JFrame;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import be.iannel.iannelloecoleski.models.*;

public class LessonFrame extends JFrame {

    private static final long serialVersionUID = 1L;


    public LessonFrame() {


        setTitle("Gestion des leçons");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setContentPane(panel);

        JButton addButton = new JButton("Ajouter une leçon");
        JButton searchButton = new JButton("Rechercher par ID");
        JButton getAllButton = new JButton("Afficher toutes les leçons");
        JButton deleteButton = new JButton("Supprimer une leçon");
        JButton mainPageButton = new JButton("Page principale");

        panel.add(addButton);
        panel.add(searchButton);
        panel.add(getAllButton);
        panel.add(deleteButton);
        panel.add(mainPageButton);

        addButton.addActionListener(e -> showAddLessonForm());

        searchButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID de la leçon:");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Lesson lesson = Lesson.getLessonById(id);
                    if (lesson != null) {
                        JOptionPane.showMessageDialog(this, lesson.toString(), "Leçon trouvée", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Aucune leçon trouvée avec l'ID " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide !");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });

        getAllButton.addActionListener(e -> {
            List<Lesson> lessons = Lesson.getAllLessons();
            if (lessons.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucune leçon trouvée");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Lesson l : lessons) {
                    sb.append(l.toString()).append("\n----------------------\n");
                }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 400));

                JOptionPane.showMessageDialog(this, scrollPane, "Toutes les leçons", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            List<Lesson> lessons = Lesson.getAllLessons();
            if (lessons.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucune leçon à supprimer");
                return;
            }

            JComboBox<Lesson> lessonCombo = new JComboBox<>();
            for (Lesson l : lessons) {
                lessonCombo.addItem(l);
            }

            int result = JOptionPane.showConfirmDialog(this, lessonCombo,
                    "Sélectionnez la leçon à supprimer", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                Lesson selectedLesson = (Lesson) lessonCombo.getSelectedItem();
                if (selectedLesson != null) {
                    try {
                        if (Lesson.deleteLessonById(selectedLesson.getId())) {
                            JOptionPane.showMessageDialog(this, "Leçon supprimée avec succès");
                        } else {
                            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression");
                        }
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage());
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

    private void showAddLessonForm() {
        JTextField minBookingsField = new JTextField(5);
        JTextField maxBookingsField = new JTextField(5);
        JTextField dateField = new JTextField(10);
        JTextField timeField = new JTextField(5);
        JTextField durationField = new JTextField(3);
        JCheckBox privateCheck = new JCheckBox();

        List<Instructor> instructors = Instructor.getAllInstructors();
        JComboBox<Instructor> instructorCombo = new JComboBox<>();
        for (Instructor i : instructors) {
            instructorCombo.addItem(i);
        }

        List<LessonType> allLessonTypes = LessonType.getAllLessonTypes();
        JComboBox<LessonType> lessonTypeCombo = new JComboBox<>();
        for (LessonType lt : allLessonTypes) {
            lessonTypeCombo.addItem(lt);
        }

        instructorCombo.addActionListener(e -> {
            Instructor selectedInstructor = (Instructor) instructorCombo.getSelectedItem();
            lessonTypeCombo.removeAllItems();

            if (selectedInstructor != null) {
                List<Accreditation> accreditations = selectedInstructor.getAccreditations();

                for (LessonType lt : allLessonTypes) {
                    if (accreditations.contains(lt.getAccreditation())) {
                        lessonTypeCombo.addItem(lt);
                    }
                }
            }
        });

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Min. bookings:"), gbc);
        gbc.gridx = 1;
        form.add(minBookingsField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Max. bookings:"), gbc);
        gbc.gridx = 1;
        form.add(maxBookingsField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        form.add(dateField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Heure (HH:mm):"), gbc);
        gbc.gridx = 1;
        form.add(timeField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Durée (minutes):"), gbc);
        gbc.gridx = 1;
        form.add(durationField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Privée:"), gbc);
        gbc.gridx = 1;
        form.add(privateCheck, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Instructeur:"), gbc);
        gbc.gridx = 1;
        form.add(instructorCombo, gbc);

        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Type de leçon:"), gbc);
        gbc.gridx = 1;
        form.add(lessonTypeCombo, gbc);

        int result = JOptionPane.showConfirmDialog(this, form, "Ajouter une leçon", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                LocalDate lessonDate = LocalDate.parse(dateField.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
                LocalDateTime startTime = LocalDateTime.parse(dateField.getText() + "T" + timeField.getText());

                Lesson lesson = new Lesson(
                    Integer.parseInt(minBookingsField.getText()),
                    Integer.parseInt(maxBookingsField.getText()),
                    lessonDate,
                    startTime,
                    Integer.parseInt(durationField.getText()),
                    privateCheck.isSelected(),
                    (LessonType) lessonTypeCombo.getSelectedItem(),
                    (Instructor) instructorCombo.getSelectedItem()
                );

                if (lesson.addLesson()) {
                    JOptionPane.showMessageDialog(this, "Leçon ajoutée avec succès");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LessonFrame().setVisible(true));
    }
}
