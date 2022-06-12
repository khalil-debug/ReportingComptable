package controller;


import Entities.Colonne;

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

import modele.ColonneDao;


public class ServletColonnes extends HttpServlet {

    @SuppressWarnings("compatibility:-6306033851408589054")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        ArrayList<String> result = new ArrayList<String>();
        String action = request.getParameter("action");
        ColonneDao dao = new ColonneDao();
        if (action.equals("supprimercol")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteColonne(id);
            
            result.add("coldeleted");
            Gson gson = new Gson();
            JsonElement e =
                gson.toJsonTree(result, new TypeToken<List<String>>() {
                }.getType());
            JsonArray array = e.getAsJsonArray();
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(array);
        } else if (action.equals("getcolbyid")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Colonne c = new Colonne();
            c = dao.getColonneByID(id);
            String colonneJson = new Gson().toJson(c);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(colonneJson);
        }
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
                                                            ServletException {
        ArrayList<String> result = new ArrayList<String>();
        ColonneDao dao = new ColonneDao();

        //action du post pour la récupération des colonnes
        String action = request.getParameter("action");


        if (action.equals("amCol")) {
            String id = request.getParameter("id");
            String code_colonne = request.getParameter("code");
            String id_annexe = request.getParameter("idannexe");
            String lib_colonne = request.getParameter("libcolonne");
            if (id == null || id.equals("")) {
                if (dao.CheckCodeColonneExist(Integer.parseInt(code_colonne),
                                              Integer.parseInt(id_annexe))) {
                    result.add("codeexist");
                    Gson gson = new Gson();
                    JsonElement e =
                        gson.toJsonTree(result, new TypeToken<List<String>>() {
                        }.getType());
                    JsonArray array = e.getAsJsonArray();
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().print(array);
                } else {
                    Colonne c = new Colonne();
                    c.setCodecolonne(code_colonne);
                    c.setLibcolonne(lib_colonne);
                    c.setIdannexe(Integer.parseInt(id_annexe));

                    dao.ajoutColonne(c);

                    result.add(code_colonne);
                    Gson gson = new Gson();
                    JsonElement element =
                        gson.toJsonTree(result, new TypeToken<List<String>>() {
                        }.getType());
                    JsonArray jsonArray = element.getAsJsonArray();
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().print(jsonArray);
                }
            } else {
                boolean chkcodeannexenv = false;
                int idcol = Integer.parseInt(id.trim());
                Colonne colonne = dao.getColonneByID(idcol);
                String codeancien = colonne.getCodecolonne();
                if (!codeancien.equals(code_colonne))
                    chkcodeannexenv =
                            dao.CheckCodeColonneExist(Integer.parseInt(code_colonne),
                                                      Integer.parseInt(id_annexe));

                colonne.setCodecolonne(code_colonne);
                colonne.setLibcolonne(lib_colonne);
                colonne.setIdannexe(Integer.parseInt(id_annexe));
                if (chkcodeannexenv == false) {
                    dao.updateColonne(colonne);
                } else {
                    result.add("codeexist");
                }

                Gson gson = new Gson();
                JsonElement element =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray jsonArray = element.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(jsonArray);
            }
        } else if (action.equals("getcolbyax")) {
            //cette action nous permet de récupérer les colonnes par annexe choisi par l'utilisateur
            ArrayList<Colonne> c = new ArrayList<Colonne>();
            int idax = Integer.parseInt(request.getParameter("idannexe"));
            c = dao.recupererColParAnnexe(idax);
            if (c.isEmpty()) {
                result.add("rien");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            } else {
                String lstcolonnes = new Gson().toJson(c);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(lstcolonnes);
            }
        }
    }
}
