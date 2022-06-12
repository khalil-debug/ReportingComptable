package controller;


import Entities.Annexe;

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

import modele.AnnexeDao;


public class ServletAnnexe extends HttpServlet {

    //https://www.jmdoudoux.fr/java/dej/chap-servlets.htm
    //H�rite de GenericServlet : classe d�finissant une servlet utilisant le protocole http
    //L'usage principal des servlets est la cr�ation de pages HTML dynamiques.
    //L'API fournit une classe qui encapsule une servlet utilisant le protocole http.
    //Cette classe est la classe HttpServlet.
    //Ce type de servlet n'est pas utile seulement pour g�n�rer des pages HTML bien que cela soit son principal usage, elle peut aussi r�aliser un ensemble de traitements
    // tels que mettre � jour une base de donn�es. En r�ponse, elle peut g�n�rer une page html qui indique le succ�s ou non de la mise � jour.
    //Typiquement pour d�finir une servlet, il faut d�finir une classe qui h�rite de la classe HttpServlet et red�finir la m�thode doGet() et/ou doPost() selon les besoins.
    //La m�thode service() h�rit�e de HttpServlet appelle l'une ou l'autre de ces m�thodes en fonction du type de la requ�te http :
    //une requ�te GET : c'est une requ�te qui permet au client de demander une ressource
    //une requ�te POST : c'est une requ�te qui permet au client d'envoyer des informations issues par exemple d'un formulaire


    //Les m�thodes init(), service() et destroy() assurent le cycle de vie de la servlet en �tant respectivement appel�es lors de la cr�ation de la servlet,
    //lors de son appel pour le traitement d'une requ�te et lors de sa destruction.

    @SuppressWarnings("compatibility:4356594146808791915")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    //HttpServletRequest    H�rite de ServletRequest : d�finit un objet contenant une requ�te selon le protocole http
    //HttpServletResponse   H�rite de ServletResponse : d�finit un objet contenant la r�ponse de la servlet selon le protocole http
    //une requ�te GET : c'est une requ�te qui permet au client de demander une ressource


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        //permet au client de demander une ressource
        // faire appel au methodes de r�cup�ration des donn�es
        String action = request.getParameter("action");
        
        ArrayList<String> result = new ArrayList<String>();
        AnnexeDao dao = new AnnexeDao();
        if (action.equals("supprimerax")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteAnnexe(id);
            result.add("axdeleted");
            Gson gson = new Gson();
            JsonElement e =
                gson.toJsonTree(result, new TypeToken<List<String>>() {
                }.getType());
            JsonArray array = e.getAsJsonArray();
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(array);
            
        } else if (action.equals("getaxbyid")){
            int idax = Integer.parseInt(request.getParameter("id"));
            Annexe c = new Annexe();
            c = dao.getAnnexeID(idax);
            String colonneJson = new Gson().toJson(c);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(colonneJson);
            
        }
        
        
    }

    //HttpServletRequest    H�rite de ServletRequest : d�finit un objet contenant une requ�te selon le protocole http
    //HttpServletResponse   H�rite de ServletResponse : d�finit un objet contenant la r�ponse de la servlet selon le protocole http
    //une requ�te POST : c'est une requ�te qui permet au client d'envoyer des informations issues par exemple d'un formulaire

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        // faire appel au methodes de mise � jour de la base de donn�es (Insertion et modification )
        ArrayList<String> result = new ArrayList<String>();
        AnnexeDao dao = new AnnexeDao();
        
        String id = request.getParameter("id");
        String codeannexe = request.getParameter("code");
        String abrevannexe = request.getParameter("abrvannexe");
        String libelle = request.getParameter("libannexe");
        String periodicite = request.getParameter("periodicite");

        if (id == null || id.equals("")) {
            if (dao.CheckCodeAnnexeExist(codeannexe)) {
                result.add("codeexist");
                Gson gson = new Gson();
                JsonElement element =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray jsonArray = element.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(jsonArray);
            }else {
                Annexe a = new Annexe();
                a.setCodeannexe(codeannexe);
                a.setAbrevAnnexe(abrevannexe);
                a.setLibelle(libelle);
                a.setPeriodicite(periodicite);
                dao.ajoutAnnexe(a);
                
                result.add(codeannexe);
                Gson gson = new Gson();
                JsonElement element = gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray jsonArray = element.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(jsonArray);
            }
        }else {
            boolean chkcodeannexenv = false;
            int idax = Integer.parseInt(id.trim());
            Annexe annexe = dao.getAnnexeID(idax);
            String codeancien = annexe.getCodeannexe();
            if (!codeancien.equals(codeannexe))
                chkcodeannexenv = dao.CheckCodeAnnexeExist(codeannexe);

            annexe.setCodeannexe(codeannexe);
            annexe.setLibelle(libelle);
            annexe.setAbrevAnnexe(abrevannexe);
            if (chkcodeannexenv == false)
            {
                dao.updateAnnexe(annexe);
            }
            else{
                result.add("codeexist");
            }
            
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(result, new TypeToken<List<String>>() {
                }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(jsonArray);
        }
        
    }
}
