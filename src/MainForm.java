import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


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
    private JButton btShow;
    private JTextPane tpShower;
    private User user;
    private static boolean state = false;

    public MainForm(JFrame parent){
        super(parent);
        setTitle("Gestion des utilisateurs");
        setMinimumSize(new Dimension(500, 500));
        setContentPane(jpMain);
        setLocationRelativeTo(parent);
        setModal(false);
        setVisible(true);
        tpShower.setVisible(false);

        jbtAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = tfNom.getText();
                String prenom = tfPrenom.getText();
                String email = tfEmail.getText();
                String pwd = String.valueOf(pfPwd.getPassword());

                register();

                User user = new User(nom, prenom, email, pwd);
                User exist = Request.getUserByMail(user);

                if (exist != null) {
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
                updateUser();
            }
        });
        btShow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!state){
                    state = true;
                }
                else{
                    state = false;
                }
                //gérer l'affichage du composant tpShowUsers
                JComponent tpShowUsers;
                tpShower.setVisible(state);
                //récupération de la liste des utilisateurs
                getAllUsers();
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


    //Update user
    public void updateUser() {
        // Récupérer les valeurs des champs du formulaire
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPwd.getPassword());
        String verify = String.valueOf(pfVerify.getPassword());

        // Vérifier si les champs sont bien remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs du formulaire",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si les mots de passe correspondent
        if (!password.equals(verify)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User();
        user.setEmail(email);

        // Vérifier si le compte existe
        User exist = Request.getUserByMail(user);
        if (exist == null) {
            JOptionPane.showMessageDialog(this,
                    "Le compte n'existe pas",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mettre à jour les champs de l'utilisateur
        if (!exist.getNom().equals(nom)) {
            exist.setNom(nom);
            Request.updateUser(exist, "nom", nom);
        }
        if (!exist.getPrenom().equals(prenom)) {
            exist.setPrenom(prenom);
            Request.updateUser(exist, "prenom", prenom);
        }

        JOptionPane.showMessageDialog(this,
                "Le compte a été mis à jour en BDD",
                "Succès",
                JOptionPane.INFORMATION_MESSAGE);
    }


    //Afficher la liste
    public void getAllUsers() {
        // Récupération de la liste des utilisateurs
        List<User> allUsers = Request.getAllUsers();
        // Chaine à afficher dans tpShowUsers
        String listeUsers = "";

        // Vérifier si la liste des utilisateurs est null
        if (allUsers.size() == 0) {

            jlTitre.setText("Aucun utilisateur");
            jlTitre.setForeground(Color.RED);
            tpShower.setVisible(false);

        } else {
            // Construire la liste des utilisateurs
            for (User user : allUsers) {
                listeUsers += user.getId() + ", " + user.getNom() + ", " + user.getEmail() + "\n";
            }
            // Injecter la liste dans le composant
            tpShower.setText(listeUsers);
            tpShower.setForeground(Color.BLACK);
            jlTitre.setText("Liste des utilisateurs");
            tpShower.setVisible(true);
        }
    }








}
