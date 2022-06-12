package modele;


import DB.DbUtil;

import Entities.Colonne;
import Entities.Rubrique;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;


public class RubriqueDao {

    private Connection connection;

    public ArrayList<Rubrique> getAllRubriques() {
        ArrayList<Rubrique> listRubriques = new ArrayList<Rubrique>();
        String query = "select stg_rubrique.*, stg_annexe.code_annexe as code from stg_rubrique\n" + 
        "inner join stg_annexe on stg_rubrique.id_annexe= stg_annexe.id_annexe order by stg_annexe.id_annexe asc";

        try {
            connection = DbUtil.checkConnection(connection);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {


                Rubrique rb = new Rubrique();
                rb.setIdrubrique(rs.getInt("ID_RUBRIQUE"));
                rb.setCodeRubrique(rs.getString("code_rub"));
                rb.setOrdreEdition(rs.getString("ORDRE_EDITION"));
                rb.setLibelle(rs.getString("lib_rubrique"));
                rb.setIdannexe(rs.getInt("ID_ANNEXE"));
                rb.setCodeax(rs.getString("CODE"));
                listRubriques.add(rb);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllRubriques: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listRubriques;
    }


    // recupérer la rubrique avec l'id specifié

    public Rubrique getRubriqueID(int id) {
        
        Rubrique rb = new Rubrique();
        String query = " SELECT * FROM stg_rubrique WHERE ID_RUBRIQUE= "+id+"";
        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            
            while (rs.next()) {
                rb.setIdrubrique(rs.getInt("ID_RUBRIQUE"));
                rb.setCodeRubrique(rs.getString("code_rub"));
                rb.setOrdreEdition(rs.getString("ORDRE_EDITION"));
                rb.setLibelle(rs.getString("lib_rubrique"));
                rb.setIdannexe(rs.getInt("ID_ANNEXE"));

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getRubriqueID: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        
        return rb;
    }

    //l'ajout d'une rubrique
    public boolean ajouterRubrique(Rubrique r) {
        
        boolean status=false; 
        String query =
            " INSERT INTO stg_rubrique (code_rub,ORDRE_EDITION,lib_rubrique,ID_ANNEXE) VALUES (?,?,?,?)";
        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, r.getCodeRubrique());
            statement.setString(2, r.getOrdreEdition());
            statement.setString(3, r.getLibelle());
            statement.setInt(4, r.getIdannexe());

            statement.executeUpdate();
            status=true;
            
        } catch (SQLException e) {
            System.out.println("Erreur au niveau de methode ajouterRubrique " +
                               e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return status;
    }




    //modifier une rubrique

    public Rubrique updateRubrique(Rubrique r) {

        String statement =
            "UPDATE stg_rubrique SET code_rub=?,ORDRE_EDITION=?, lib_rubrique=?, ID_ANNEXE=? WHERE ID_RUBRIQUE=?";
        try {
            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.setString(1, r.getCodeRubrique());
            ps.setString(2, r.getOrdreEdition());
            ps.setString(3, r.getLibelle());
            ps.setInt(4,r.getIdannexe());
            ps.setInt(5, r.getIdrubrique());
            ps.executeUpdate();

        } catch (SQLException ex) {

            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return r;
    }

    //supprimer une rubrique

    public void deleteRubrique(int id) {
        String statement = "DELETE FROM STG_RUBRIQUE WHERE ID_RUBRIQUE=?";
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
    
    public ArrayList<Rubrique> recupererRubParAnnexe(int IdAnnexe){
        
        String query= "select stg_rubrique.*, stg_annexe.code_annexe as code from stg_rubrique \n" +
            "inner join stg_annexe on stg_rubrique.id_annexe= stg_annexe.id_annexe where stg_rubrique.id_annexe="+IdAnnexe+" order by stg_annexe.id_annexe asc";
        ArrayList<Rubrique> listRubriques = new ArrayList<Rubrique>();

        try {
            connection = DbUtil.checkConnection(connection);

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {


                Rubrique rb = new Rubrique();
                rb.setIdrubrique(rs.getInt("ID_RUBRIQUE"));
                rb.setCodeRubrique(rs.getString("code_rub"));
                rb.setOrdreEdition(rs.getString("ORDRE_EDITION"));
                rb.setLibelle(rs.getString("lib_rubrique"));
                rb.setIdannexe(rs.getInt("ID_ANNEXE"));
                rb.setCodeax(rs.getString("CODE"));
                listRubriques.add(rb);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllRubriques: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listRubriques;
    }
    
    
    public boolean CheckCodeRubriqueExist(String code) {

        boolean more = false;
        String searchQuery = "SELECT * FROM stg_rubrique WHERE  code_rub = '" + code +"'" ;
        try {
            connection = DbUtil.checkConnection(connection);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);
            more = rs.next();
            if (!more) {
                System.out.println("Rubrique doesn't exist");
                more = false;
                return more;

            } else {
                more = true;
                return more;

            }

        } catch (Exception e) {
           
            System.out.println("Erreur   CheckCodeRubriqueExist " + e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return more;

    }

    public boolean CheckOrdreAnnexeExist(String ordre, int idax) {

        boolean more = false;
        String searchQuery = "SELECT * FROM stg_rubrique WHERE  ORDRE_EDITION = '" + ordre +"' AND ID_ANNEXE="+idax+"" ;

        try {
            connection = DbUtil.checkConnection(connection);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(searchQuery);
            more = rs.next();
            if (!more) {
                System.out.println("Rubrique doesn't exist");
                more = false;
                return more;

            } else {
                more = true;
                return more;

            }

        } catch (Exception e) {
           
            System.out.println("Erreur   CheckOrdreAnnexeExist " + e);
            e.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }

        return more;

    }
    
    public ArrayList<Rubrique> recupererRubDeclaration(int idax, int idbq,
                                                      Date date) {

        String query =
            "select distinct c.*, m.id_banque from stg_mouvement m, stg_rubrique c where m.id_banque=\n" + 
            idbq+"  and m.id_annexe="+idax+" and m.date_sit=? and m.id_rubrique=c.id_rubrique order by c.code_rub ASC";
        ArrayList<Rubrique> listRub = new ArrayList<Rubrique>();

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDate(1, date);            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {


                Rubrique rub = new Rubrique();
                rub.setIdrubrique(rs.getInt("ID_RUBRIQUE"));
                rub.setCodeRubrique(rs.getString("code_rub"));
                rub.setLibelle(rs.getString("lib_rubrique"));
                rub.setOrdreEdition(rs.getString("ORDRE_EDITION"));
                rub.setIdannexe(rs.getInt("ID_ANNEXE"));
                listRub.add(rub);

            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllRubriques: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return listRub;
    }
    
    public int recupererNbRubriquesParDeclaration(int idax, int idbq, String date) {

        String query =
            "select count (distinct id_rubrique) as nb_rub from stg_mouvement where id_banque="+idbq+" and id_annexe="+idax+" \n" + 
            "and date_sit=TO_DATE(?, 'dd/mm/yyyy')";
        int nbCol=0;

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nbCol=rs.getInt("nb_rub");
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererNbRubriquesParAnnexe: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return nbCol;
    }
    
    public int recupererNbRubriquesParSerieBq(int idax, int idcol, String date) {

        String query =
            "select count (distinct id_rubrique) as nb_rub from stg_mouvement where id_colonne="+idcol+" and id_annexe="+idax+" \n" + 
            "and date_sit=TO_DATE(?, 'dd/mm/yyyy')";
        int nbRub=0;

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nbRub=rs.getInt("nb_rub");
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererNbRubriquesParAnnexe: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return nbRub;
    }
    
    public int recupererNbRubriquesParSerieChrono(int idax, int idcol,int idbq, String dateD, String dateF) {

        String query =
            "select count (distinct id_rubrique) as nb_rub from stg_mouvement where id_colonne="+idcol+" and id_annexe="+idax+" and id_banque="+idbq+" \n" + 
            "and date_sit between TO_DATE(?, 'dd/mm/yyyy') and TO_DATE(?, 'dd/mm/yyyy')";
        int nbRub=0;

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dateD);
            ps.setString(2, dateF);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nbRub=rs.getInt("nb_rub");
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererNbRubriquesParAnnexe: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return nbRub;
    }
    
    
}
