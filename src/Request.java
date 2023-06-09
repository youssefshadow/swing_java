import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Request {
    //Attributs paramètres BDD
    static final String DB_URL = "jdbc:mysql://localhost/Java?serverTimezone=UTC";
    static final String USERNAME = "root";
    static final String PASSWORD = "";
    //Connexion à la BDD
    private static Connection connexion;
    static{
        try{
            connexion = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static User addUser(User user){
        //instancier un Objet User null
        User userAdd = null;
        try {
            //1 Connection à la BDD...
            Statement stmt = connexion.createStatement();
            //2 Requête SQL
            String sql = "INSERT INTO users (nom, prenom, email, mdp ) " + "VALUES (?, ?, ?, md5(?))";
            //3 préparer la requête
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            //4 Bind des paramètres
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPwd());
            //5 Exécution de la requête
            int addedRows = preparedStatement.executeUpdate();
            //6 Test si la requête à été effectué
            if(addedRows>0){
                //assigner un user à userAdd
                userAdd = new User(user.getNom(), user.getPrenom(), user.getEmail(), user.getPwd());
            }
            //7 Fermer la connexion à la BDD
            //stmt.close();
            //connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userAdd;
    }
    public static User getUserByMail(User user){
        //instancier un objet User null
        User userGet = null;
        try {
            //1 Connection à la BDD...
            Statement stmt = connexion.createStatement();
            //2 Requête SQL
            String sql = "SELECT id,nom, prenom, email, mdp FROM users WHERE email =?";
            //3 préparer la requête
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            //4 Bind des paramètres
            preparedStatement.setString(1, user.getEmail());
            //5 Exécution de la requête
            ResultSet rs = preparedStatement.executeQuery();
            //6 Test si la requête à été effectué
            //Parcours du résultat
            //Si la réponse est différente de null
            if (rs.next()){
                if (rs.getString(1)!= null){
                    userGet = new User();
                    userGet.setId(Integer.parseInt(rs.getString("id")));
                    //userGet.setId(rs.getInt("id"));
                    userGet.setNom(rs.getString("nom"));
                    userGet.setPrenom(rs.getString("prenom"));
                    userGet.setEmail(rs.getString("email"));
                    userGet.setPwd(rs.getString("mdp"));

                }
            }
            //7 Fermer la connexion à la BDD
            //stmt.close();
            //connexion.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userGet;
    }


    //methoe3 gett all users
    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try {
            // 1. Connection à la BDD...
            Statement stmt = connexion.createStatement();
            // 2. Requête SQL
            String sql = "SELECT id, nom, prenom, email, mdp FROM users";
            // 3. Préparer la requête
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            // 4. Exécution de la requête
            ResultSet rs = preparedStatement.executeQuery();

            // 5. Parcours du résultat
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setPwd(rs.getString("mdp"));


                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userList;
    }
    public static User updateUser(User user, String theName, String newName) {
        User updatedUser = null;
        try {
            String sql = "UPDATE users SET " + theName + " = ? WHERE id = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(sql);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, user.getId());
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                updatedUser = new User(user.getNom(), user.getPrenom(), user.getEmail(), user.getPwd());
                updatedUser.setId(user.getId());
                if (theName.equals("nom")) {
                    updatedUser.setNom(newName);
                } else if (theName.equals("prenom")) {
                    updatedUser.setPrenom(newName);
                } else if (theName.equals("email")) {
                    updatedUser.setEmail(newName);
                } else if (theName.equals("mdp")) {
                    updatedUser.getPwd();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updatedUser;
    }



}

