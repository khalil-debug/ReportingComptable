package modele;


import DB.DbUtil;

import Entities.Banque;
import Entities.Colonne;

import Entities.Rubrique;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


public class ColonneDao {

    private Connection connection;

    public ArrayList<Colonne> getAllColonnes() {
        ArrayList<Colonne> listColonnes = new ArrayList<Colonne>();
        String query =
            " select STG_COLONNE.*, stg_annexe.code_annexe as code from STG_COLONNE\n" +
            "inner join stg_annexe on STG_COLONNE.id_annexe= stg_annexe.id_annexe order by stg_annexe.id_annexe, STG_COLONNE.code_col asc";

        try {
            connection = DbUtil.checkConnection(connection);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                Colonne col = new Colonne();
                col.setIdcolonne(rs.getInt("ID_COLONNE"));
                col.setCodecolonne(rs.getString("code_col"));
                col.setLibcolonne(rs.getString("lib_col"));
                col.setIdannexe(rs.getInt("ID_ANNEXE"));
                col.setCodeax(rs.getString("CODE"));
                listColonnes.add(col);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllColonnes " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listColonnes;
    }

    public Colonne getColonneByID(int id) {

        Colonne col = new Colonne();
        String query = " SELECT * FROM STG_COLONNE WHERE ID_COLONNE= " + id + "";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                col.setIdcolonne(rs.getInt("ID_COLONNE"));
                col.setCodecolonne(rs.getString("code_col"));
                col.setLibcolonne(rs.getString("lib_col"));
                col.setIdannexe(rs.getInt("ID_ANNEXE"));
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getColonneByID: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return col;
    }

    public boolean ajoutColonne(Colonne c) {
        boolean status = false;
        String statement =
            "INSERT INTO STG_COLONNE (code_col,lib_col, ID_ANNEXE) values (?,?,?)";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, c.getCodecolonne());
            ps.setString(2, c.getLibcolonne());
            ps.setInt(3, c.getIdannexe());

            ps.executeUpdate();
            status = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return status;
    }

    public Colonne updateColonne(Colonne col) {

        String statement =
            "UPDATE STG_COLONNE SET code_col=?,lib_col=?, ID_ANNEXE=? WHERE ID_COLONNE=?";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, col.getCodecolonne());
            ps.setString(2, col.getLibcolonne());
            ps.setInt(3, col.getIdannexe());
            ps.setInt(4, col.getIdcolonne());
            ps.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return col;
    }

    public void deleteColonne(int id) {
        String statement = "DELETE FROM STG_COLONNE WHERE ID_COLONNE=?";
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

    public ArrayList<Colonne> recupererColParAnnexe(int IdAnnexe) {

        String query =
        " select STG_COLONNE.*, stg_annexe.code_annexe as code from STG_COLONNE\n" + 
        "inner join stg_annexe on STG_COLONNE.id_annexe= stg_annexe.id_annexe where stg_colonne.id_annexe="+IdAnnexe+" order by stg_annexe.id_annexe, STG_COLONNE.code_col asc";
        ArrayList<Colonne> listCol = new ArrayList<Colonne>();

        try {
            connection = DbUtil.checkConnection(connection);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {


                Colonne col = new Colonne();
                col.setIdcolonne(rs.getInt("ID_COLONNE"));
                col.setCodecolonne(rs.getString("code_col"));
                col.setLibcolonne(rs.getString("lib_col"));
                col.setIdannexe(rs.getInt("ID_ANNEXE"));
                col.setCodeax(rs.getString("code"));
                listCol.add(col);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererColParAnnexe: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listCol;
    }

    public boolean CheckCodeColonneExist(int codecol, int idax) {

        boolean more = false;
        String searchQuery =
            "SELECT * FROM STG_COLONNE WHERE  code_col = '" + codecol +
            "' AND ID_ANNEXE=" + idax + "";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);
            more = rs.next();
            if (!more) {
                System.out.println("Colonne doesn't exist");
                more = false;
                return more;

            } else {
                more = true;
                return more;

            }

        } catch (Exception e) {

            System.out.println("Erreur   CheckCodeColonneExist " + e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return more;

    }

    public ArrayList<Colonne> recupererColsDeclaration(int idax, int idbq,
                                                       String date) {

        String query =
            "select distinct c.* from stg_mouvement m, STG_COLONNE c where m.id_banque=" +
            idbq + " and m.id_annexe=" + idax + " and m.date_sit=TO_DATE(?, 'dd/mm/yyyy')" +
            " and m.id_colonne=c.id_colonne order by c.code_col ASC";
        ArrayList<Colonne> listCol = new ArrayList<Colonne>();

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, date);            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {


                Colonne col = new Colonne();
                col.setIdcolonne(rs.getInt("ID_COLONNE"));
                col.setCodecolonne(rs.getString("code_col"));
                col.setLibcolonne(rs.getString("lib_col"));
                col.setIdannexe(rs.getInt("ID_ANNEXE"));
                listCol.add(col);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererColsDeclaration: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return listCol;
    }

    public ArrayList<Colonne> recupererColsParRubrique(int idax, int idbq,
                                                       String date,
                                                       int idrub) {

        String query =
            "select c.* from stg_mouvement m, STG_COLONNE c where m.id_banque="+idbq+" and m.id_annexe="+idax+" and m.date_sit=TO_DATE(?, 'dd/mm/yyyy')" +
            "and m.id_rubrique="+idrub+" and m.id_colonne=c.id_colonne " +
            "order by c.code_col ASC";
        ArrayList<Colonne> listCol = new ArrayList<Colonne>();

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {


                Colonne col = new Colonne();
                col.setIdcolonne(rs.getInt("ID_COLONNE"));
                col.setCodecolonne(rs.getString("code_col"));
                col.setLibcolonne(rs.getString("lib_col"));
                col.setIdannexe(rs.getInt("ID_ANNEXE"));
                listCol.add(col);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererColsParRubrique: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return listCol;
    }
    
    public int recupererNbColonnesParAnnexe(int idax) {

        String query =
            "select count( distinct id_colonne) as nb_col from stg_colonne where id_annexe=?";
        int nbCol=0;

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idax);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nbCol=rs.getInt("nb_col");
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererNbColonnesParAnnexe: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return nbCol;
    }

}
