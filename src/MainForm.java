import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JDialog{
    private JLabel jlTitre;
    private JPanel jpMain;
    private JLabel jlNom;
    private JTextField tfNom;
    private JLabel jlPrenom;
    private JTextField tfPrenom;
    private JTextField tfEmail;
    private JLabel jlPwd;
    private JPasswordField pfPwd;
    private JButton jbtAdd;
    private JButton jbtCancel;
    private JLabel jlEmail;
    private JPasswordField pfVerify;
   
    private JLabel jlVerify;
    private JButton jbtUpdate;
    private User user;

    public MainForm(JFrame parent){
        super(parent);
        setTitle("Gestion des utilisateurs");
        setMinimumSize(new Dimension(500, 500));
        setContentPane(jpMain);
        setLocationRelativeTo(parent);
        setModal(false);
        setVisible(true);
        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = tfNom.getText();
                String prenom = tfPrenom.getText();
                String email = tfEmail.getText();
                String pwd = String.valueOf(pfPwd.getPassword());

                register();

                User user = new User(nom, prenom, email, pwd);
                User existingUser = Request.getUserByMail(user);

                if (existingUser != null) {
                    JOptionPane.showMessageDialog(MainForm.this,
                            "Un compte avec cette adresse e-mail existe déjà",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Arrêter l'ajout de l'utilisateur
                }

                User addedUser = Request.addUser(user);

                if (addedUser != null) {
                    JOptionPane.showMessageDialog(MainForm.this,
                            "Utilisateur ajouté avec succès",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(MainForm.this,
                            "Erreur lors de l'ajout de l'utilisateur",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        jbtUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("clic sur update");
            }
        });
    }
    public void register() {
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPwd.getPassword());
        String verify = String.valueOf(pfVerify.getPassword());

        // Verify if the fields are filled correctly
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs du formulaire",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(verify)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Initialize the instance variable "user" with the provided values
        user = new User(nom, prenom, email, password);
    }

}
