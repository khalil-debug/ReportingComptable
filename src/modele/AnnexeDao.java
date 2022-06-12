package modele;


import DB.DbUtil;
import Entities.Annexe;

import Entities.Colonne;
import Entities.Rubrique;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


//http://blog.paumard.org/cours/jdbc/chap02-apercu-exemple.html#d0e324

public class AnnexeDao {

    //La connexion à la base consiste à demander au DriverManager un objet de type Connection, par l’appel de la méthode getConnection().
    //Là il n’y a qu’une seule façon de le faire : passer l’URL de la base, un nom de connexion et un mot de passe valides.
    //L’appel de cette méthode renvoie un objet instance d’une classe qui implémente l’interface Connection.

    private Connection connection;

    public ArrayList<Annexe> getAllAnnexes() {


        ArrayList<Annexe> listAnnexes = new ArrayList<Annexe>();
        String query = " SELECT * FROM STG_ANNEXE ORDER BY CODE_ANNEXE ASC ";

        try {
            connection = DbUtil.checkConnection(connection);
            //Pour pouvoir passer une requête à la base, il faut un objet de type Statement.
            //Cet objet s’obtient en faisant appel à la méthode createStatement() de notre objet de type Connection.
            Statement statement = connection.createStatement();
            //On peut envoyer ensuite trois types de requêtes :
            // Des sélections : smt.executeQuery(chaîneSQL) ;
            // Des mises à jour : smt.executeUpdate(chaîneSQL) ;
            // Des exécutions de procédures stockées : smt.execute(chaîneSQL).

            //On récupère le résultat dans un objet de type ResultSet. Dans le cas d’une mise à jour de la base,
            //ce qui correspond aux commandes SQL insert, update, create, drop, delete, on utilise la commande :
            ResultSet rs = statement.executeQuery(query);
            //L’objet resultSet contient les lignes renvoyées par la requête.
            // Enfin, pour parcourir ces lignes, il faut utiliser les méthodes de l’interface ResultSet. .
            
            while (rs.next()) {

                //Les champs des lignes sélectionnées sont maintenant disponibles sous forme d’objets Java,
                Annexe ax = new Annexe();
                ax.setIdannexe(rs.getInt("ID_ANNEXE"));
                ax.setCodeannexe(rs.getString("CODE_ANNEXE"));
                ax.setLibelle(rs.getString("LIB_ANNEXE"));
                ax.setAbrevAnnexe(rs.getString("ABRV_ANNEXE"));
                ax.setPeriodicite(rs.getString("PERIODICITE"));
                listAnnexes.add(ax);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllAnnexes " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return listAnnexes;
    }
    
    public Annexe getAnnexeID(int id) {
        
        Annexe ax = new Annexe();
        String query = " SELECT * FROM STG_ANNEXE WHERE ID_ANNEXE= "+id+"";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                ax.setIdannexe(rs.getInt("ID_ANNEXE"));
                ax.setCodeannexe(rs.getString("CODE_ANNEXE"));
                ax.setLibelle(rs.getString("LIB_ANNEXE"));
                ax.setAbrevAnnexe(rs.getString("ABRV_ANNEXE"));
                ax.setPeriodicite(rs.getString("PERIODICITE"));

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAnnexeID: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        
        return ax;
    }


    public boolean ajoutAnnexe(Annexe a) {
        boolean status = false;
        String statement =
            "INSERT INTO STG_ANNEXE (CODE_ANNEXE,LIB_ANNEXE,ABRV_ANNEXE,PERIODICITE) values (?,?,?,?)";
        
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, a.getCodeannexe());
            ps.setString(2, a.getLibelle());
            ps.setString(3, a.getAbrevAnnexe());
            ps.setString(4, a.getPeriodicite());
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


    public Annexe updateAnnexe(Annexe anx) {

        String statement =
            "UPDATE STG_ANNEXE SET CODE_ANNEXE=?,LIB_ANNEXE=?,ABRV_ANNEXE=?,PERIODICITE=? WHERE ID_ANNEXE=?";
        
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, anx.getCodeannexe());
            ps.setString(2, anx.getLibelle());
            ps.setString(3, anx.getAbrevAnnexe());
            ps.setString(4, anx.getPeriodicite());
            ps.setInt(5, anx.getIdannexe());
            ps.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
            
        }

        return anx;
    }


    public void deleteAnnexe(int id) {
        String statement = "DELETE FROM STG_ANNEXE WHERE ID_ANNEXE=?";
        
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
            
        }
    }

    public boolean CheckCodeAnnexeExist(String code) {

        boolean more = false;
        String searchQuery = "SELECT * FROM STG_ANNEXE WHERE CODE_ANNEXE = '" + code + "'";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);
            more = rs.next();
            if (!more) {
                System.out.println("code annexe doesn't exist");
                more = false;
                return more;

            } else {
                more = true;
                return more;

            }

        } catch (Exception e) {
           
            System.out.println("Erreur   CheckCodeAnnexeExist " + e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return more;

    }
    
    //declarations
    public ArrayList<Annexe> recupererAnnexeparBanque(int idbanque){
        
        String query= "select id_annexe from STG_ANNEXE where id_annexe=(SELECT DISTINCT\n" + 
        "    mouvement.id_annexe FROM mouvement where mouvement.id_annexe = STG_ANNEXE.id_annexe and mouvement.id_banque="+idbanque+")";
        ArrayList<Annexe> listAnnexes = new ArrayList<Annexe>();

        try {
            connection = DbUtil.checkConnection(connection);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {


                Annexe ax = new Annexe();
                ax.setIdannexe(rs.getInt("ID_ANNEXE"));
                ax.setCodeannexe(rs.getString("CODE_ANNEXE"));
                ax.setLibelle(rs.getString("LIB_ANNEXE"));
                ax.setAbrevAnnexe(rs.getString("ABRV_ANNEXE"));
                ax.setPeriodicite(rs.getString("PERIODICITE"));
                listAnnexes.add(ax);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllRubriques: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listAnnexes;
    }
    
    
    //tri e la table d'annexe
    public ArrayList<Annexe> recupererAnnexeParPeriodicite(String periodicite){
        ArrayList<Annexe> lstax = new ArrayList<Annexe>();
        String query = " SELECT * FROM STG_ANNEXE WHERE PERIODICITE= '"+periodicite+"'";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                Annexe ax = new Annexe();
                ax.setIdannexe(rs.getInt("ID_ANNEXE"));
                ax.setCodeannexe(rs.getString("CODE_ANNEXE"));
                ax.setLibelle(rs.getString("LIB_ANNEXE"));
                ax.setAbrevAnnexe(rs.getString("ABRV_ANNEXE"));
                ax.setPeriodicite(rs.getString("PERIODICITE"));
                
                lstax.add(ax);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAnnexeID: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        
        return lstax;
    }
    
}


