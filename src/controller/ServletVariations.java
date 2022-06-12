package controller;

import Entities.Colonne;
import Entities.Declaration;

import Entities.Rubrique;
import Entities.Variation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import modele.ColonneDao;
import modele.MouvementDao;
import modele.RubriqueDao;

public class ServletVariations extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        ArrayList<String> result = new ArrayList<String>();
        String action = request.getParameter("action");
        ColonneDao dao = new ColonneDao();
        RubriqueDao rdao = new RubriqueDao();
        MouvementDao mdao = new MouvementDao();


        if (action.equals("displayColVaria")) {
            
            int idbanque = Integer.parseInt(request.getParameter("idbanque"));
            int idannexe = Integer.parseInt(request.getParameter("idannexe"));
            String date = request.getParameter("datePres");
            
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

            } 
            else
            {

                ArrayList<Colonne> c = new ArrayList<Colonne>();

                c = dao.recupererColsDeclaration(idannexe, idbanque, date);


                //on transforme les arrayList sous forme json pour communiquer avec ajax
                String colonnesJson = new Gson().toJson(c);

                //on les affectes aux reponses
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(colonnesJson);

            }

        } else if (action.equals("pourcentage")) {
            //on fait appel à l'action de l'affichage des montants
            int idbanque = Integer.parseInt(request.getParameter("idbanque"));
            int idannexe = Integer.parseInt(request.getParameter("idannexe"));
            String datePres = request.getParameter("datePres");
            String dateAnc = request.getParameter("dateAnc");
            
            if (dao.recupererColsDeclaration(idannexe, idbanque,
                                             datePres).isEmpty()) {

                result.add("rien");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);

            } else {
                ArrayList<Declaration> declarationPres = new ArrayList<Declaration>();
                ArrayList<Declaration> declarationAnc = new ArrayList<Declaration>();
                ArrayList<Variation> variation = new ArrayList<Variation>();
                int nbcol = dao.recupererNbColonnesParAnnexe(idannexe);
                ArrayList<Rubrique> nbrb =
                    rdao.recupererRubParAnnexe(idannexe);
                ArrayList<Colonne> c = new ArrayList<Colonne>();

                c = dao.recupererColsDeclaration(idannexe, idbanque, datePres);
                
                declarationAnc = mdao.AfficherDeclaration(dateAnc, idannexe, idbanque, nbcol, nbrb, c);
                
                declarationPres =
        mdao.AfficherDeclaration(datePres, idannexe, idbanque, nbcol, nbrb, c);
                
                variation = mdao.AfficherVariations(declarationPres, declarationAnc, c, action);
                
                String variationJson = new Gson().toJson(variation);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(variationJson);
            }
        }
        else if (action.equals("normalEnMD")) {
                    //on fait appel à l'action de l'affichage des montants
                    int idbanque = Integer.parseInt(request.getParameter("idbanque"));
                    int idannexe = Integer.parseInt(request.getParameter("idannexe"));
                    String datePres = request.getParameter("datePres");
                    String dateAnc = request.getParameter("dateAnc");
                    
                    if (dao.recupererColsDeclaration(idannexe, idbanque,
                                                     datePres).isEmpty()) {

                        result.add("rien");
                        Gson gson = new Gson();
                        JsonElement e =
                            gson.toJsonTree(result, new TypeToken<List<String>>() {
                            }.getType());
                        JsonArray array = e.getAsJsonArray();
                        response.setContentType("text/plain;charset=UTF-8");
                        response.getWriter().print(array);

                    } else {
                        ArrayList<Declaration> declarationPres = new ArrayList<Declaration>();
                        ArrayList<Declaration> declarationAnc = new ArrayList<Declaration>();
                        ArrayList<Variation> variation = new ArrayList<Variation>();
                        int nbcol = dao.recupererNbColonnesParAnnexe(idannexe);
                        ArrayList<Rubrique> nbrb =
                            rdao.recupererRubParAnnexe(idannexe);
                        ArrayList<Colonne> c = new ArrayList<Colonne>();

                        c = dao.recupererColsDeclaration(idannexe, idbanque, datePres);
                        
                        declarationAnc = mdao.AfficherDeclaration(dateAnc, idannexe, idbanque, nbcol, nbrb, c);
                        
                        declarationPres =
                        mdao.AfficherDeclaration(datePres, idannexe, idbanque, nbcol, nbrb, c);
                        
                        variation = mdao.AfficherVariations(declarationPres, declarationAnc, c, action);
                        
                        
                        String variationJson = new Gson().toJson(variation);
                        response.setContentType("text/plain;charset=UTF-8");
                        response.getWriter().print(variationJson);
                    }
                }
    }
}
