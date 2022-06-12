package controller;


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

import modele.MouvementDao;
import modele.RubriqueDao;


public class ServletSerieChrono extends HttpServlet {
    @SuppressWarnings("compatibility:4431770996147003802")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        ArrayList<String> result = new ArrayList<String>();
        String action = request.getParameter("action");
        RubriqueDao rdao = new RubriqueDao();
        MouvementDao mdao = new MouvementDao();
        
        int idcolonne = Integer.parseInt(request.getParameter("idcolonne"));
        int idannexe = Integer.parseInt(request.getParameter("idannexe"));
        int idbanque = Integer.parseInt(request.getParameter("idbanque"));
        //date début:
        String dateD = request.getParameter("dateDebut");
        //date fin:
        String dateF = request.getParameter("dateFin");
        ArrayList<String> dates=new ArrayList<String>();
        dates = mdao.getNombreDateSerieChrono(dateD, dateF);
        
        if (action.equals("displayDate")){
            if (dates.isEmpty()){
                
                result.add("rien");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            }else {
                
                
                String banquesJson = new Gson().toJson(dates);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(banquesJson);

            }
        }else if (action.equals("displayLigneChrono")){
                ArrayList<Declaration> serie = new ArrayList<Declaration>();
                
                ArrayList<Rubrique> nbRub= rdao.recupererRubParAnnexe(idannexe);
                
                
                serie = mdao.AfficherSerieChrono(idannexe, idcolonne, idbanque, dateD,dateF , nbRub, dates);
                System.out.println(serie);
                    
                if (serie.isEmpty()){
                    
                    result.add("rien");
                    Gson gson = new Gson();
                    JsonElement e =
                        gson.toJsonTree(result, new TypeToken<List<String>>() {
                        }.getType());
                    JsonArray array = e.getAsJsonArray();
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().print(array);
                }else{
                
                String serieJson = new Gson().toJson(serie);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(serieJson);
                
                
            }
        }
    }
    
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        
    }
}
