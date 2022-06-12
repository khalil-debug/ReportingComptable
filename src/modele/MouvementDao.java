package modele;


import DB.DbUtil;

import Entities.Annexe;
import Entities.Banque;
import Entities.Colonne;
import Entities.Declaration;
import Entities.Mouvement;

import Entities.Rubrique;

import Entities.Variation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.DecimalFormat;

import java.util.ArrayList;


public class MouvementDao {
    private Connection connection;

    public ArrayList<Mouvement> getAllMouvement() {


        ArrayList<Mouvement> listMVTs = new ArrayList<Mouvement>();
        String query = " SELECT * FROM STG_MOUVEMENT";

        try {
            connection = DbUtil.checkConnection(connection);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Mouvement mvt = new Mouvement();
                mvt.setIdmouvement(rs.getInt("ID_MOUVEMENT"));
                mvt.setMontant(rs.getLong("MONTANT_SIT"));
                mvt.setIdannexe(rs.getInt("ID_ANNEXE"));
                mvt.setIdcolonne(rs.getInt("ID_COLONNE"));
                mvt.setIdrubrique(rs.getInt("ID_RUBRIQUE"));
                mvt.setDateMVT(rs.getDate("DATE_SIT"));
                mvt.setIdbanque(rs.getInt("ID_BANQUE"));
                listMVTs.add(mvt);
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode getAllMouvements " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
            System.out.println("close connection reussi");
        }
        return listMVTs;
    }
    //ajout mouvement

    public boolean ajoutMouvement(Mouvement m) {
        boolean status = false;
        String statement =
            "INSERT INTO STG_MOUVEMENT (MONTANT_SIT,ID_ANNEXE,ID_COLONNE,ID_RUBRIQUE,ID_BANQUE,DATE_SIT) values (?,?,?,?,?,?)";
        try {

            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(statement);

            ps.setDouble(1, m.getMontant());
            ps.setInt(2, m.getIdannexe());
            ps.setInt(3, m.getIdcolonne());
            ps.setInt(4, m.getIdrubrique());
            ps.setInt(5, m.getIdbanque());
            ps.setDate(6, m.getDateMVT());
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

    //une fonction pour récupérer l'index de la colonne qui a le libellé demandé

    private int getValueIndexColonneByLibelle(String colonne,
                                              ArrayList<Colonne> c) {
        int indexInsertion = -1;

        for (int i = 0; i < c.size(); i++) {
            if (colonne.equals(c.get(i).getLibcolonne()))
                indexInsertion = i;
        }
        return indexInsertion;
    }


    private int getValueIndexRubriqueByLibelle(String rubrique,
                                               ArrayList<Rubrique> r){
        int indexInsertion = -1;

        for (int i = 0; i < r.size(); i++) {
            if (rubrique.equals(r.get(i).getLibelle()))
                indexInsertion = i;
        }
        return indexInsertion;
    }
    
    /**
     * @param dateD
     * @param idax
     * @param idbq
     * @param nbCol
     * @param r
     * @param c
     * @return
     */
    public ArrayList<Declaration> AfficherDeclaration(String dateD, int idax,
                                                      int idbq, int nbCol,
                                                      ArrayList<Rubrique> r,
                                                      ArrayList<Colonne> c) {
        //on crée une variable de sauvegarde pour le mouvement
        Declaration olddec = new Declaration();

        ArrayList<Declaration> lstMvt = new ArrayList<Declaration>();
        String query =
            "select c.code_rub, c.lib_rubrique, m.montant_sit, sc.lib_col from stg_mouvement m, stg_rubrique c, stg_colonne sc where m.id_banque=" +
            idbq + "\n" +
            "and m.id_annexe=" + idax +
            " and m.date_sit=TO_DATE(?, 'dd/mm/yyyy') and m.id_rubrique=c.id_rubrique AND m.id_colonne= sc.id_colonne order by c.code_rub, sc.code_col  ASC";
        
        for (int i = 0; i < r.size(); i++) {
                Declaration dec = new Declaration();
                double[] montants = new double[nbCol];
                dec.setCodeRub(r.get(i).getCodeRubrique()); 
                dec.setLibRub(r.get(i).getLibelle());
                dec.setMontants(montants);
                lstMvt.add(dec);
        }
        
        try {

            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dateD);
            ResultSet rs = ps.executeQuery();

            //on initialise un tableau de montants
            double[] montants = new double[nbCol];
            //on initialise l'index des colonnes
            int indexInsers = 0;
            int indexRub = 0;
            //on initialize l'indexe du curseur qui va nous aider auparavant à l'affichage de la dernière declaration
            int curseur = 0;
            while (rs.next()) {
                //on initialise une nouvelle declaration
                Declaration newdec = new Declaration();
                //on affecte le code de la rubrique et son libellé à la nouvelle Declaration
                newdec.setCodeRub(rs.getString("code_rub"));
                newdec.setLibRub(rs.getString("lib_rubrique"));

                //on initialise la premiere declaration pour recupérer son montant
                if (rs.isFirst()) {
                    olddec = newdec;
                }
                //on verifie si la rubrique est celle d'avant pour enregistrer les montants:
                String colonne = rs.getString("lib_col");
                indexInsers = getValueIndexColonneByLibelle(colonne, c);
                if (newdec.getCodeRub().equals(olddec.getCodeRub())) {
                    if (indexInsers != -1) {
                        montants[indexInsers] = rs.getDouble("montant_sit");
                    }
                } else {
                    //sinon, on ajoute la declaration à la listeArray de mouvements
                    olddec.setMontants(montants);
                    String rubrique = olddec.getLibRub();
                    indexRub = getValueIndexRubriqueByLibelle(rubrique, r);
                    lstMvt.get(indexRub).setMontants(olddec.getMontants());
                    olddec = newdec;
                    curseur++;
                    //on réinitialise le tableau de montants et son index.
                    indexInsers = 0;
                    montants = new double[nbCol];
                    //et on ajoute le montant auquel le resultSet pointe sur pour la nouvelle declaration.
                    montants[indexInsers] = rs.getDouble("montant_sit");
                }
                //à la fin de la ResultSet, on doit enregistrer la dernière declaration pour l'afficher
                if (curseur == (r.size() - 1)) {
                    //on affecte la dernière valeur au tableau montant.
                    olddec.setMontants(montants);
                    //on ajoute la dernière declaration à la liste
                    String rubrique = olddec.getLibRub();
                    indexRub = getValueIndexRubriqueByLibelle(rubrique, r);
                    lstMvt.get(indexRub).setMontants(olddec.getMontants());
                    curseur++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
        }

        return lstMvt;
    }


    private int getValueIndexBanqueByLibelle(String banque,
                                             ArrayList<Banque> b) {
        int indexInsertion = -1;

        for (int i = 0; i < b.size(); i++) {
            if (banque.equals(b.get(i).getAbrvbanque()))
                indexInsertion = i;
        }
        return indexInsertion;
    }

    //fonction affichage consultation série de banques

    public ArrayList<Declaration> AfficherSerieBanque(int idax, int idcol,
                                                      String date, int nbBq,
                                                      ArrayList<Rubrique> r,
                                                      ArrayList<Banque> b) {
        //on crée une variable de sauvegarde pour le mouvement
        Declaration olddec = new Declaration();

        ArrayList<Declaration> lstMvt = new ArrayList<Declaration>();
        String query =
            "select r.code_rub, r.lib_rubrique, m.montant_sit, b.abrv_banque, m.id_colonne \n" +
            "from stg_mouvement m, stg_rubrique r, stg_banque b \n" +
            "where m.id_colonne=" + idcol + " and m.id_annexe=" + idax +
            " and m.date_sit=TO_DATE(?, 'dd/mm/yyyy') and m.id_rubrique=r.id_rubrique\n" +
            "AND b.id_banque=m.id_banque order by r.code_rub, b.abrv_banque  ASC";
        
        for (int i = 0; i < r.size(); i++) {
                Declaration dec = new Declaration();
                double[] montants = new double[nbBq];
                dec.setCodeRub(r.get(i).getCodeRubrique()); 
                dec.setLibRub(r.get(i).getLibelle());
                dec.setMontants(montants);
                lstMvt.add(dec);
        }
        try {

            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();

            //on initialise un tableau de montants
            double[] montants = new double[nbBq];
            //on initialise l'index des colonnes
            int indexInsers = 0;
            int indexRub = 0;
            //on initialize l'indexe du curseur qui va nous aider auparavant à l'affichage de la dernière declaration
            int curseur = 0;
            while (rs.next()) {
                //on initialise une nouvelle declaration
                Declaration newdec = new Declaration();
                //on affecte le code de la rubrique et son libellé à la nouvelle Declaration
                newdec.setCodeRub(rs.getString("code_rub"));
                newdec.setLibRub(rs.getString("lib_rubrique"));

                //on initialise la premiere declaration pour recupérer son montant
                if (rs.isFirst()) {
                    olddec = newdec;
                }
                //on verifie si la rubrique est celle d'avant pour enregistrer les montants:
                String banque = rs.getString("ABRV_BANQUE");
                indexInsers = getValueIndexBanqueByLibelle(banque, b);
                if (newdec.getCodeRub().equals(olddec.getCodeRub())) {
                    if (indexInsers != -1) {
                        montants[indexInsers] = rs.getDouble("montant_sit");
                    }
                } else {
                    //sinon, on ajoute la declaration à la listeArray de mouvements
                    olddec.setMontants(montants);
                    String rubrique = olddec.getLibRub();
                    indexRub = getValueIndexRubriqueByLibelle(rubrique, r);
                    lstMvt.get(indexRub).setMontants(olddec.getMontants());
                    olddec = newdec;
                    curseur++;
                    //on réinitialise le tableau de montants et son index.
                    indexInsers = 0;
                    montants = new double[nbBq];
                    //et on ajoute le montant auquel le resultSet pointe sur pour la nouvelle declaration.
                    montants[indexInsers] = rs.getDouble("montant_sit");
                }
                //à la fin de la ResultSet, on doit enregistrer la dernière declaration pour l'afficher
                if (curseur == (r.size() - 1)) {
                    //on affecte la dernière valeur au tableau montant.
                    olddec.setMontants(montants);
                    //on ajoute la dernière declaration à la liste
                    String rubrique = olddec.getLibRub();
                    indexRub = getValueIndexRubriqueByLibelle(rubrique, r);
                    lstMvt.get(indexRub).setMontants(olddec.getMontants());
                    curseur++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
        }

        return lstMvt;
    }

    //fonction pour récupérer le nombre de dates pour l'alignement des valeurs

    public ArrayList<String> getNombreDateSerieChrono(String dateD,
                                                      String dateF) {
        String query =
            "select distinct date_sit  From stg_mouvement where date_sit between TO_DATE(?, 'dd/mm/yyyy') and TO_DATE(?, 'dd/mm/yyyy')";
        ArrayList<String> dates = new ArrayList<String>();

        try {
            connection = DbUtil.checkConnection(connection);

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dateD);
            ps.setString(2, dateF);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dates.add(rs.getString("date_sit"));
            }
        } catch (SQLException e) {


            System.out.println("Erreur au niveau de methode recupererNbRubriquesParAnnexe: " +
                               e);
            e.printStackTrace();

        } finally {
            DbUtil.cleanup(connection);
        }
        return dates;
    }

    //fonction pour récupérer l'indexe de la date pour la serie chrono

    private int getValueIndexDateByDate(String date, ArrayList<String> d) {
        int indexInsertion = -1;

        for (int i = 0; i < d.size(); i++) {
            if (date.equals(d.get(i)))
                indexInsertion = i;
        }
        return indexInsertion;
    }


    //fonction affichage série chronologique

    public ArrayList<Declaration> AfficherSerieChrono(int idax, int idcol,
                                                      int idbq, String dateD,
                                                      String dateF, ArrayList<Rubrique> r,
                                                      ArrayList<String> dates) {
        //on crée une variable de sauvegarde pour le mouvement
        Declaration olddec = new Declaration();
        ArrayList<Declaration> lstMvt = new ArrayList<Declaration>();
        String query =
            "select c.code_rub, c.lib_rubrique, m.montant_sit, sc.lib_col , m.date_sit \n" +
            "from stg_mouvement m, stg_rubrique c, stg_colonne sc \n" +
            "where m.id_banque=" + idbq + " and m.id_colonne=" + idcol +
            " and m.id_annexe=" + idax + " \n" +
            "and m.date_sit between TO_DATE(?, 'dd/mm/yyyy') AND TO_DATE(?, 'dd/mm/yyyy') \n" +
            "and m.id_rubrique=c.id_rubrique AND m.id_colonne= sc.id_colonne \n" +
            "order by c.code_rub, m.date_sit  ASC";
        
        for (int i = 0; i < r.size(); i++) {
                Declaration dec = new Declaration();
                double[] montants = new double[dates.size()];
                dec.setCodeRub(r.get(i).getCodeRubrique()); 
                dec.setLibRub(r.get(i).getLibelle());
                dec.setMontants(montants);
                lstMvt.add(dec);
        }

        try {

            connection = DbUtil.checkConnection(connection);
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dateD);
            ps.setString(2, dateF);
            ResultSet rs = ps.executeQuery();

            //on initialise un tableau de montants
            double[] montants = new double[dates.size()];
            //on initialise l'index des colonnes
            int indexInsers = 0;
            int indexRub = 0;
            //on initialize l'indexe du curseur qui va nous aider auparavant à l'affichage de la dernière declaration
            int curseur = 0;
            while (rs.next()) {
                //on initialise une nouvelle declaration
                Declaration newdec = new Declaration();
                //on affecte le code de la rubrique et son libellé à la nouvelle Declaration
                newdec.setCodeRub(rs.getString("code_rub"));
                newdec.setLibRub(rs.getString("lib_rubrique"));

                //on initialise la premiere declaration pour recupérer son montant
                if (rs.isFirst()) {
                    olddec = newdec;
                }
                //on verifie si la rubrique est celle d'avant pour enregistrer les montants:
                String date = rs.getString("DATE_SIT");
                indexInsers = getValueIndexDateByDate(date, dates);
                if (newdec.getCodeRub().equals(olddec.getCodeRub())) {
                    if (indexInsers != -1) {
                        montants[indexInsers] = rs.getDouble("montant_sit");
                    }
                } else {
                    //sinon, on ajoute la declaration à la listeArray de mouvements
                    olddec.setMontants(montants);
                    String rubrique = olddec.getLibRub();
                    indexRub = getValueIndexRubriqueByLibelle(rubrique, r);
                    lstMvt.get(indexRub).setMontants(olddec.getMontants());
                    olddec = newdec;
                    curseur++;
                    //on réinitialise le tableau de montants et son index.
                    indexInsers = 0;
                    montants = new double[dates.size()];
                    //et on ajoute le montant auquel le resultSet pointe sur pour la nouvelle declaration.
                    montants[indexInsers] = rs.getDouble("montant_sit");
                }
                //à la fin de la ResultSet, on doit enregistrer la dernière declaration pour l'afficher
                if (curseur == (r.size() - 1)) {
                    //on affecte la dernière valeur au tableau montant.
                    olddec.setMontants(montants);
                    //on ajoute la dernière declaration à la liste
                    String rubrique = olddec.getLibRub();
                    indexRub = getValueIndexRubriqueByLibelle(rubrique, r);
                    lstMvt.get(indexRub).setMontants(olddec.getMontants());
                    curseur++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbUtil.cleanup(connection);
        }

        return lstMvt;
    }


    public ArrayList<Variation> AfficherVariations(ArrayList<Declaration> dPres,
                                                   ArrayList<Declaration> dAnc,
                                                   ArrayList<Colonne> c,
                                                   String action) {
        ArrayList<Variation> lstVar = new ArrayList<Variation>();
        double ancienMontant = 0;
        double montantPresent = 0;
        double montantInserer = 0;

        for (int i = 0; i < dAnc.size(); i++) {
            //on initialise un tableau de doubles pour l'enregistrement des montants
            String[] montants = new String[c.size()];

            Variation d = new Variation();
            if (dPres.get(i).getCodeRub().equals(dAnc.get(i).getCodeRub())) {

                d.setCodeRub(dPres.get(i).getCodeRub());
                d.setLibRub(dPres.get(i).getLibRub());

                for (int j = 0; j < c.size(); j++) {
                    ancienMontant = dAnc.get(i).getMontants()[j];
                    montantPresent = dPres.get(i).getMontants()[j];

                    if (action.equals("pourcentage")) {
                        if (ancienMontant != 0) {
                            montantInserer =
                                    ((montantPresent - ancienMontant) /
                                     ancienMontant) * 100;
                        }else montantInserer = montantPresent;
                            
                        if (montantInserer != 0) {
                            montants[j] = String.format("%.3f",montantInserer).replace(",", ".");
                        } else
                            montants[j] = "0.0";
                    }

                    else {
                        montantInserer = montantPresent - ancienMontant;
                        if (montantInserer != 0) {
                            montants[j] = String.format("%.3f",montantInserer).replace(",", ".");
                        } else
                            montants[j] = "0.0";
                    }
                }
                d.setMontants(montants);
                lstVar.add(d);
            }
        }
        return lstVar;
    }

    //fonction ajout mvt:

    public void ajouterMouvementAxBq(int ax, int bq, Date date) {
        //initialisation des valeurs données par l'utilisateur.

        //initialisation des DAO:
        RubriqueDao rdao = new RubriqueDao();
        ColonneDao cdao = new ColonneDao();
        AnnexeDao adao = new AnnexeDao();
        BanqueDao bdao = new BanqueDao();

        //création d'un nouveau mouvement
        Mouvement mvt = new Mouvement();
        MouvementDao dao = new MouvementDao();

        //création d'une liste de Rubriques et de colonnes par l'annexe et la banque pour les parcourir
        ArrayList<Rubrique> rubriques = new ArrayList<Rubrique>();
        ArrayList<Colonne> colonnes = new ArrayList<Colonne>();

        //recupérer l'annexe et la banque choisis:
        Annexe annexe = new Annexe();
        Banque banque = new Banque();
        banque = bdao.getBanqueID(bq);

        annexe = adao.getAnnexeID(ax);

        float montant = 0;
        mvt.setIdbanque(bq);

        //recupérer les rubriques et les colonnes par annexe:
        rubriques = rdao.recupererRubParAnnexe(annexe.getIdannexe());
        colonnes = cdao.recupererColParAnnexe(annexe.getIdannexe());

        mvt.setIdannexe(annexe.getIdannexe());

        //Parcourir la liste des rubriques
        for (int rub = 0; rub < rubriques.size(); rub++) {

            //Récupérer l'identifiant de la rubrique
            mvt.setIdrubrique(rubriques.get(rub).getIdrubrique());

            //Parcourir la liste des colonnes
            for (int col = 0; col < colonnes.size(); col++) {

                //Récupérer l'identifiant de la colonne
                mvt.setIdcolonne(colonnes.get(col).getIdcolonne());

                //Générer la valeur numérique aléatoire
                montant = (float)Math.floor(Math.random() * (1000000 - 1) + 1);
                mvt.setMontant(montant);
                mvt.setDateMVT(date);

                //Insérer (date, idbanque, idannexe, idrubrique, idcolonne,  valeur numérique aléatoire);
                dao.ajoutMouvement(mvt);
            }
        }
    }

}
