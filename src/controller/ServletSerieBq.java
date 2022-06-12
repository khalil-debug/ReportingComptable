package controller;


import Entities.Banque;
import Entities.Declaration;

import Entities.Rubrique;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.BanqueDao;
import modele.MouvementDao;
import modele.RubriqueDao;


public class ServletSerieBq extends HttpServlet {
    @SuppressWarnings("compatibility:1586127727179972999")
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
        RubriqueDao rdao = new RubriqueDao();
        MouvementDao mdao = new MouvementDao();
        BanqueDao dao= new BanqueDao();
        
        int idcolonne = Integer.parseInt(request.getParameter("idcolonne"));
        int idannexe = Integer.parseInt(request.getParameter("idannexe"));
        String date = request.getParameter("date");

        
        ArrayList<Banque> b = new ArrayList<Banque>();
        b = dao.recupererSerieBanques(idannexe, idcolonne, date);
        
        if (action.equals("displayBqSerie")){
            if (dao.recupererSerieBanques(idannexe, idcolonne, date).isEmpty()){
                
                result.add("rien");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            }else {
                
                
                String banquesJson = new Gson().toJson(b);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(banquesJson);

            }
        }else if (action.equals("displayLigneSerie")){
            
            if (dao.recupererSerieBanques(idannexe, idcolonne, date).isEmpty()){
                
                result.add("rien");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            }else {
                ArrayList<Declaration> serie = new ArrayList<Declaration>();
                
                int nbBq = dao.recupererSerieBanques(idannexe, idcolonne, date).size();
                ArrayList<Rubrique> nbRub= rdao.recupererRubParAnnexe(idannexe);
                
                
                serie = mdao.AfficherSerieBanque(idannexe, idcolonne, date, nbBq, nbRub, b);
                
                String serieJson = new Gson().toJson(serie);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(serieJson);
                
            }
        }
        
    }
}
