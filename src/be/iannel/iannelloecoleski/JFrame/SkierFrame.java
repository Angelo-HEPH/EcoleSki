package be.iannel.iannelloecoleski.JFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import be.iannel.iannelloecoleski.models.Skier;

public class SkierFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public SkierFrame() {

        setTitle("Gestion des skieurs");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        setContentPane(panel);

        JButton addButton = new JButton("Ajouter un skieur");
        JButton searchButton = new JButton("Rechercher par ID");
        JButton getAllButton = new JButton("Afficher tous les skieurs");
        JButton deleteButton = new JButton("Supprimer un skieur");
        JButton mainPageButton = new JButton("Page principale");


        panel.add(addButton);
        panel.add(searchButton);
        panel.add(getAllButton);
        panel.add(deleteButton);
        panel.add(mainPageButton);

        addButton.addActionListener(e -> showAddSkierForm());

        searchButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "Entrez l'ID du skieur:");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Skier skier = Skier.getSkierById(id);
                    if (skier != null) {
                        JOptionPane.showMessageDialog(this, skier.toString(), "Skieur trouvé", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Aucun skieur trouvé avec l'ID " + id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID invalide");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });


        getAllButton.addActionListener(e -> {
            List<Skier> skiers = Skier.getAllSkiers();

            if (skiers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun skieur trouvé");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Skier s : skiers) {
                    sb.append(s.toString()).append("\n----------------------\n");
                }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));

                JOptionPane.showMessageDialog(this, scrollPane, "Tous les skieurs", JOptionPane.INFORMATION_MESSAGE);
            }
        });


        deleteButton.addActionListener(e -> {
            List<Skier> skiers = Skier.getAllSkiers();
            if (skiers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun skieur à supprimer");
                return;
            }

            JComboBox<Skier> skierCombo = new JComboBox<>();
            for (Skier s : skiers) {
                skierCombo.addItem(s);
            }

            int result = JOptionPane.showConfirmDialog(this, skierCombo,
                    "Sélectionnez le skieur à supprimer", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                Skier selectedSkier = (Skier) skierCombo.getSelectedItem();
                if (selectedSkier != null && selectedSkier.deleteSkierById(selectedSkier.getId())) {
                    JOptionPane.showMessageDialog(this, "Skieur supprimé avec succès");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression");
                }
            }
        });
        
        mainPageButton.addActionListener(e -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            this.dispose();
        });

    }

    private void showAddSkierForm() {
        JTextField firstNameField = new JTextField(10);
        JTextField lastNameField = new JTextField(10);
        JTextField emailField = new JTextField(15);
        JTextField streetField = new JTextField(15);
        JTextField streetNumberField = new JTextField(5);
        JTextField cityField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField ageField = new JTextField(3);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Prénom:")); form.add(firstNameField);
        form.add(new JLabel("Nom:")); form.add(lastNameField);
        form.add(new JLabel("Email:")); form.add(emailField);
        form.add(new JLabel("Rue:")); form.add(streetField);
        form.add(new JLabel("Numéro:")); form.add(streetNumberField);
        form.add(new JLabel("Ville:")); form.add(cityField);
        form.add(new JLabel("Téléphone:")); form.add(phoneField);
        form.add(new JLabel("Âge:")); form.add(ageField);

        int result = JOptionPane.showConfirmDialog(this, form, "Ajouter un skieur", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String street = streetField.getText();
                int streetNumber = Integer.parseInt(streetNumberField.getText());
                String city = cityField.getText();
                String phone = phoneField.getText();
                int age = Integer.parseInt(ageField.getText());

                Skier skier = new Skier(firstName, lastName, email, street, streetNumber, city, phone, age);

                if (skier.addSkier()) {
                    JOptionPane.showMessageDialog(this, "Skieur ajouté avec succès");
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new SkierFrame().setVisible(true));
    }
}
