package controller;


import Entities.Colonne;
import Entities.Declaration;

import Entities.Rubrique;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.ColonneDao;
import modele.MouvementDao;
import modele.RubriqueDao;


public class ServletMouvements extends HttpServlet {
    @SuppressWarnings("compatibility:-5199210108027192465")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        ArrayList<String> result = new ArrayList<String>();
        String action = request.getParameter("action");
        ColonneDao dao = new ColonneDao();
        RubriqueDao rdao = new RubriqueDao();
        MouvementDao mdao = new MouvementDao();


        if (action.equals("displayColDecl")) {
            //ont fait appel à l'affichage des colonnes
            int idbanque = Integer.parseInt(request.getParameter("idbanque"));
            int idannexe = Integer.parseInt(request.getParameter("idannexe"));
            String date = request.getParameter("date");
            
            if (dao.recupererColsDeclaration(idannexe, idbanque,
                                             date).isEmpty()) {

                result.add("rien");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);

            } else {

                ArrayList<Colonne> c = new ArrayList<Colonne>();

                c = dao.recupererColParAnnexe(idannexe);


                //on transforme les arrayList sous forme json pour communiquer avec ajax
                String colonnesJson = new Gson().toJson(c);

                //on les affectes aux reponses
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(colonnesJson);

            }

        } else if (action.equals("displayLignesDecl")) {
            //on fait appel à l'action de l'affichage des montants
            int idbanque = Integer.parseInt(request.getParameter("idbanque"));
            int idannexe = Integer.parseInt(request.getParameter("idannexe"));
            String date = request.getParameter("date");
            
            if (dao.recupererColsDeclaration(idannexe, idbanque,
                                             date).isEmpty()) {

                result.add("rien");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);

            } else {
                ArrayList<Declaration> m = new ArrayList<Declaration>();
                int nbcol = dao.recupererNbColonnesParAnnexe(idannexe);
                ArrayList<Rubrique> nbrb =
                    rdao.recupererRubParAnnexe(idannexe);
                ArrayList<Colonne> c = new ArrayList<Colonne>();

                c = dao.recupererColsDeclaration(idannexe, idbanque, date);

                m =
  mdao.AfficherDeclaration(date, idannexe, idbanque, nbcol, nbrb, c);

                String mouvementsJson = new Gson().toJson(m);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(mouvementsJson);
            }
        } else {
            int idax = Integer.parseInt(request.getParameter("idax"));
            int idbq = Integer.parseInt(request.getParameter("idbq"));
            System.out.println(request.getParameter("dateDeclaration"));
            Date date = Date.valueOf(request.getParameter("dateDeclaration"));

            mdao.ajouterMouvementAxBq(idax, idbq, date);

            System.out.println("Ajout Mouvement Successful!");
        }


    }

}
