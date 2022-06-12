package modele;


import DB.DbUtil;

import Entities.Annexe;
import Entities.Banque;

import Entities.Colonne;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


public class BanqueDao {

    private Connection connection;

    public ArrayList<Banque> getAllBanques() {
        ArrayList<Banque> listBanques = new ArrayList<Banque>();
        String query = " SELECT * FROM STG_BANQUE ORDER BY ID_BANQUE ASC";

        try {
            connection = DbUtil.checkConnection(connection);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                Banque bq = new Banque();
                bq.setIdbanque(rs.getInt("ID_BANQUE"));
                bq.setCodebanque(rs.getString("CODE_BANQUE"));
                bq.setLibBanque(rs.getString("LIB_BANQUE"));
                bq.setAbrvbanque(rs.getString("ABRV_BANQUE"));
                listBanques.add(bq);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllBanques " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listBanques;
    }
    
    public Banque getBanqueID(int id) {
        
        Banque bq = new Banque();
        String query = " SELECT * FROM STG_BANQUE WHERE ID_BANQUE= "+id+"";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                bq.setIdbanque(rs.getInt("ID_BANQUE"));
                bq.setCodebanque(rs.getString("CODE_BANQUE"));
                bq.setLibBanque(rs.getString("LIB_BANQUE"));
                bq.setAbrvbanque(rs.getString("ABRV_BANQUE"));
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getBanqueID: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        
        return bq;
    }
    
    public boolean ajoutBanque(Banque b) {
        boolean status = false;
        String statement = "INSERT INTO STG_BANQUE (CODE_BANQUE, LIB_BANQUE,ABRV_BANQUE) values (?,?,?)";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, b.getCodebanque());
            ps.setString(2, b.getLibBanque());
            ps.setString(3, b.getAbrvbanque());

            ps.executeUpdate();
            status = true;
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return status;
    }

    public Banque updateBanque(Banque bq) {

        String statement =
            "UPDATE STG_BANQUE SET ABRV_BANQUE=?,CODE_BANQUE=?,LIB_BANQUE=? WHERE ID_BANQUE=?";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, bq.getAbrvbanque());
            ps.setString(2, bq.getCodebanque());
            ps.setString(3, bq.getLibBanque());
            ps.setInt(4, bq.getIdbanque());
            ps.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return bq;
    }

    public void deleteBanque(int id) {
        String statement = "DELETE FROM STG_BANQUE WHERE ID_BANQUE=?";
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
    
    public boolean CheckCodeBanqueExist(String code) {

        boolean more = false;
        String searchQuery = "SELECT * FROM STG_BANQUE WHERE  CODE_BANQUE = '" + code + "'";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);
            more = rs.next();
            if (!more) {
                System.out.println("code banque doesn't exist");
                more = false;
                return more;

            } else {
                more = true;
                return more;

            }

        } catch (Exception e) {
           
            System.out.println("Erreur   CheckCodeBanqueExist " + e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return more;

    }
    
    public ArrayList<Banque> recupererSerieBanques(int idax, int idcol,
                                                       String date) {

        String query =
            "select distinct b.* from stg_banque b, stg_mouvement m \n" + 
            "where m.id_annexe="+idax+" and m.id_colonne= "+idcol+" and m.date_sit=TO_DATE(?, 'dd/mm/yyyy') \n" + 
            "and m.id_banque=b.id_banque order by b.code_banque asc ";
        ArrayList<Banque> listBanques = new ArrayList<Banque>();

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, date);            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {


                Banque bq = new Banque();
                bq.setIdbanque(rs.getInt("ID_BANQUE"));
                bq.setCodebanque(rs.getString("CODE_BANQUE"));
                bq.setLibBanque(rs.getString("LIB_BANQUE"));
                bq.setAbrvbanque(rs.getString("ABRV_BANQUE"));
                listBanques.add(bq);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererSerieBanques: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return listBanques;
    }
    
    //cette fonction nous permet de récupérer les banques qui n'on pas de montants effectués
    public ArrayList<Banque> recupererBanquesPasMontants(String date){
        String query =
            "select * from stg_banque where id_banque not in (select distinct b.id_banque from stg_mouvement b where b.date_sit=TO_DATE(?, 'dd/mm/yyyy'))";
        ArrayList<Banque> listBanques = new ArrayList<Banque>();

        try {
            connection = DbUtil.checkConnection(connection);
        
            PreparedStatement ps = connection.prepareStatement(query); 
            ps.setString(1, date); 
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {


                Banque bq = new Banque();
                bq.setIdbanque(rs.getInt("ID_BANQUE"));
                bq.setCodebanque(rs.getString("CODE_BANQUE"));
                bq.setLibBanque(rs.getString("LIB_BANQUE"));
                bq.setAbrvbanque(rs.getString("ABRV_BANQUE"));
                listBanques.add(bq);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererSerieBanques: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return listBanques;
    }
    
}
