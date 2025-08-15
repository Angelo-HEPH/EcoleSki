package be.iannel.iannelloecoleski.JFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import be.iannel.iannelloecoleski.models.*;

public class InstructorFrame extends JFrame {

    private static final long serialVersionUID = 1L;


    public InstructorFrame() {

        setTitle("Gestion des instructeurs");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setContentPane(panel);

        JButton addButton = new JButton("Ajouter un instructeur");
        JButton searchButton = new JButton("Rechercher par ID");
        JButton getAllButton = new JButton("Afficher tous les instructeurs");
        JButton deleteButton = new JButton("Supprimer un instructeur");
        JButton mainPageButton = new JButton("Page principale");


        panel.add(addButton);   
        panel.add(searchButton);
        panel.add(getAllButton); 
        panel.add(deleteButton);
        panel.add(mainPageButton);

        addButton.addActionListener(e -> showAddInstructorForm());

        searchButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID de l'instructeur:");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Instructor instructor = Instructor.getInstructorById(id);
                    if (instructor != null) {
                        JOptionPane.showMessageDialog(this, instructor.toString(), "Instructeur trouvé", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Aucun instructeur trouvé avec l'ID " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });

        getAllButton.addActionListener(e -> {
            List<Instructor> instructors = Instructor.getAllInstructors();

            if (instructors.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun instructeur trouvé");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Instructor i : instructors) {
                    sb.append(i.toString()).append("\n----------------------\n");
                }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));

                JOptionPane.showMessageDialog(this, scrollPane, "Tous les instructeurs", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            List<Instructor> instructors = Instructor.getAllInstructors();

            if (instructors.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun instructeur à supprimer");
                return;
            }

            JComboBox<Instructor> instructorCombo = new JComboBox<>();
            for (Instructor i : instructors) {
                instructorCombo.addItem(i);
            }

            int result = JOptionPane.showConfirmDialog(this, instructorCombo, 
                    "Sélectionnez l'instructeur à supprimer", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                Instructor selectedInstructor = (Instructor) instructorCombo.getSelectedItem();
                if (selectedInstructor != null) {
                    try {
                        if (selectedInstructor.deleteInstructorById(selectedInstructor.getId())) {
                            JOptionPane.showMessageDialog(this, "Instructeur supprimé avec succès");
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

    private void showAddInstructorForm() {
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField emailField = new JTextField(15);
        JTextField streetField = new JTextField(15);
        JTextField streetNumberField = new JTextField(5);
        JTextField cityField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField ageField = new JTextField(3);

        List<Accreditation> accreditations = Accreditation.getAllAccreditations();
        JPanel accPanel = new JPanel();
        accPanel.setLayout(new BoxLayout(accPanel, BoxLayout.Y_AXIS));
        List<JCheckBox> accCheckboxes = new ArrayList<>();
        for (Accreditation acc : accreditations) {
            JCheckBox checkBox = new JCheckBox(acc.toString());
            accCheckboxes.add(checkBox);
            accPanel.add(checkBox);
        }
        JScrollPane accreditationScroll = new JScrollPane(accPanel);
        accreditationScroll.setPreferredSize(new Dimension(200, 80));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        formAddRow(form, gbc, y++, "Prénom:", firstNameField);
        formAddRow(form, gbc, y++, "Nom:", lastNameField);
        formAddRow(form, gbc, y++, "Email:", emailField);
        formAddRow(form, gbc, y++, "Rue:", streetField);
        formAddRow(form, gbc, y++, "Numéro:", streetNumberField);
        formAddRow(form, gbc, y++, "Ville:", cityField);
        formAddRow(form, gbc, y++, "Téléphone:", phoneField);
        formAddRow(form, gbc, y++, "Âge:", ageField);
        formAddRow(form, gbc, y++, "Accréditations:", accreditationScroll);

        int result = JOptionPane.showConfirmDialog(
                this, form, "Ajouter un instructeur", JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                List<Accreditation> selectedAccreditations = new ArrayList<>();
                for (int i = 0; i < accCheckboxes.size(); i++) {
                    if (accCheckboxes.get(i).isSelected()) {
                        selectedAccreditations.add(accreditations.get(i));
                    }
                }

                Accreditation firstAcc = selectedAccreditations.isEmpty() ? null : selectedAccreditations.get(0);

                Instructor instructor = new Instructor(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        streetField.getText(),
                        Integer.parseInt(streetNumberField.getText()),
                        cityField.getText(),
                        phoneField.getText(),
                        Integer.parseInt(ageField.getText()),
                        firstAcc
                );

                for (int i = 1; i < selectedAccreditations.size(); i++) {
                    instructor.addAccreditation(selectedAccreditations.get(i));
                }

                instructor.isValid();

                if (instructor.addInstructor()) {
                    JOptionPane.showMessageDialog(this, "Instructeur ajouté avec succès");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        }
    }

    private void formAddRow(JPanel panel, GridBagConstraints gbc, int y, String label, Component field) {
        gbc.gridx = 0; gbc.gridy = y; gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(field, gbc);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InstructorFrame().setVisible(true));
    }
}
