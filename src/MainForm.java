import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JDialog{
    private JLabel jiTitre;
    private JPanel jpMain;
    private JLabel jiNom;
    private JTextField tfNom;
    private JLabel jiPrenom;
    private JTextField tfPrenom;
    private JTextField tfEmail;
    private JLabel jiPwd;
    private JPasswordField pfPwd;
    private JButton jbtAdd;
    private JButton jbtCancel;
    private JLabel jiEmail;
    private JPasswordField pfVerify;
   
    private JLabel jiVerify;
    private JButton jbtUpdate;

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
                register();
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
    public void register(){
        String nom = tfNom.getText();
        String prenom = tfPrenom.getText();
        String email = tfEmail.getText();
        String password = String.valueOf(pfPwd.getPassword());
        String verify = String.valueOf(pfVerify.getPassword());
        //vérification si les champs sont bien remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs du formulaire",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!password.equals(verify)){
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correpondent pas",
                    "Essaie encore",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        User user = new User(nom,prenom,email,password);
    }
}