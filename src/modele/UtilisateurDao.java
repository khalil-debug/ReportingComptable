package modele;


import DB.Crypto;
import DB.DbUtil;

import Entities.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


public class UtilisateurDao {
    private Connection connection;
    
    
    public Utilisateur getUserByMatricule(String matricule){
        String query = "select u.*, r.lib_role from utilisateur u, role r where u.matricule=? and r.id_role=u.id_role order by u.matricule asc";
        Utilisateur u = new Utilisateur();
        try{
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setString(1,matricule);
            
            ResultSet rs= ps.executeQuery();
            
            while(rs.next()){
                u.setIdutilisateur(rs.getInt("ID_UTILISATEUR"));
                u.setNom(rs.getString("NOM"));
                u.setPrenom(rs.getString("PRENOM"));
                u.setEmail(rs.getString("EMAIL"));
                u.setDepartement(rs.getString("DEPARTEMENT"));
                u.setIdrole(rs.getInt("ID_ROLE"));
                u.setMatricule(rs.getString("MATRICULE"));
                u.setLibrole(rs.getString("LIB_ROLE"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur au niveau de methode getAllUsers " + e);
            e.printStackTrace();
        }
        return u;
    }
    
    
    public Utilisateur getUserById(int id){
        Crypto crypto = new Crypto();
        String query = "select u.*, r.lib_role from utilisateur u, role r where u.id_utilisateur=? and r.id_role=u.id_role order by u.matricule asc";
        Utilisateur u = new Utilisateur();
        try{
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,id);
            
            ResultSet rs= ps.executeQuery();
            
            while(rs.next()){
                u.setIdutilisateur(rs.getInt("ID_UTILISATEUR"));
                u.setNom(rs.getString("NOM"));
                u.setPrenom(rs.getString("PRENOM"));
                u.setEmail(rs.getString("EMAIL"));
                u.setMotAcces(crypto.decryptWord(rs.getString("MOT_DE_PASSE")));
                u.setDepartement(rs.getString("DEPARTEMENT"));
                u.setIdrole(rs.getInt("ID_ROLE"));
                u.setMatricule(rs.getString("MATRICULE"));
                u.setLibrole(rs.getString("LIB_ROLE"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur au niveau de methode getAllUsers " + e);
            e.printStackTrace();
        }
        return u;
    }

    public ArrayList<Utilisateur> getAllUsers() {

        //we convert the image into a Base64 String to set it in the resultset object,
        //the following code reads the image column into a byte array,
        //and then converts it back to a String in Base64

        Crypto crypto = new Crypto();
        ArrayList<Utilisateur> listUsers = new ArrayList<Utilisateur>();
        String query = " select utilisateur.*, role.LIB_ROLE from utilisateur\n" + 
        "inner join ROLE on utilisateur.id_role=role.id_role";

        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setIdutilisateur(rs.getInt("ID_UTILISATEUR"));
                user.setNom(rs.getString("NOM"));
                user.setPrenom(rs.getString("PRENOM"));
                user.setEmail(rs.getString("EMAIL"));
                user.setMotAcces(crypto.decryptWord(rs.getString("MOT_DE_PASSE")));
                user.setDepartement(rs.getString("DEPARTEMENT"));
                user.setIdrole(rs.getInt("ID_ROLE"));
                user.setMatricule(rs.getString("MATRICULE"));
                user.setLibrole(rs.getString("LIB_ROLE"));
                listUsers.add(user);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllUsers " + e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listUsers;
    }

    public boolean ajoutUtilisateur(Utilisateur u) {
        Crypto crypto = new Crypto();
        boolean status = false;
        String statement =
            "INSERT INTO UTILISATEUR (NOM,PRENOM,EMAIL,MOT_DE_PASSE,DEPARTEMENT,ID_ROLE,MATRICULE) values (?,?,?,?,?,?,?)";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, crypto.encryptWord(u.getMotAcces()));
            ps.setString(5, u.getDepartement());

            ps.setInt(6, u.getIdrole());
            ps.setString(7, u.getMatricule());
            ps.executeUpdate();
            
            status= true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return status;
    }

    public Utilisateur updateUtilisateur(Utilisateur u) {
        Crypto crypto = new Crypto();
        String statement =
            "UPDATE UTILISATEUR SET NOM=?,PRENOM=?,EMAIL=?,MOT_DE_PASSE=?,DEPARTEMENT=?,ID_ROLE=?,MATRICULE=? WHERE ID_UTILISATEUR=?";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, crypto.encryptWord(u.getMotAcces()));
            ps.setString(5, u.getDepartement());
            ps.setInt(6, u.getIdrole());
            ps.setString(7, u.getMatricule());
            ps.setInt(8, u.getIdutilisateur());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return u;
    }
    
    public Utilisateur updateUtilisateurProfile(Utilisateur u) {
        String statement =
            "UPDATE UTILISATEUR SET NOM=?,PRENOM=?,EMAIL=? WHERE ID_UTILISATEUR=?";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getIdutilisateur());
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return u;
    }

    public boolean deleteUtilisateur(int id) {
        boolean result = false;
        String statement = "DELETE FROM UTILISATEUR WHERE ID_UTILISATEUR=?";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, id);
            ps.executeUpdate();
            result = true;
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return result;
    }
    
    public boolean CheckMatriculeExist(String matricule) {
        boolean more = false;
        String searchQuery =
            "SELECT * FROM UTILISATEUR WHERE  MATRICULE = '" + matricule + "'";

        try {
            connection = DbUtil.checkConnection(connection);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);
            more = rs.next();
            if (!more) {
                System.out.println("Matricule doesn't exist");
                more = false;
                return more;

            } else {
                more = true;
                return more;

            }

        } catch (Exception e) {

            System.out.println("Erreur   CheckMatriculeExist " + e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return more;

    }
    
    public ArrayList<Utilisateur> getUsersByRole(int idrole){
        
        Crypto crypto = new Crypto();
        
        ArrayList<Utilisateur> listUsers = new ArrayList<Utilisateur>();
        String query = "select u.*, r.lib_role from utilisateur u, role r where u.id_role="+idrole+" and r.id_role=u.id_role order by u.matricule asc";
        
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Utilisateur user = new Utilisateur();
                user.setIdutilisateur(rs.getInt("ID_UTILISATEUR"));
                user.setNom(rs.getString("NOM"));
                user.setPrenom(rs.getString("PRENOM"));
                user.setEmail(rs.getString("EMAIL"));
                user.setMotAcces(crypto.decryptWord(rs.getString("MOT_DE_PASSE")));
                user.setDepartement(rs.getString("DEPARTEMENT"));
                user.setIdrole(rs.getInt("ID_ROLE"));
                user.setMatricule(rs.getString("MATRICULE"));
                user.setLibrole(rs.getString("LIB_ROLE"));
                listUsers.add(user);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllUsers " + e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listUsers;
    }
    
}
