package controller;


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

import modele.RubriqueDao;


//ajout + visualisation done

public class ServletRubrique extends HttpServlet {


    @SuppressWarnings("compatibility:-2710350722360626110")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String action = request.getParameter("action");
        
        ArrayList<String> result = new ArrayList<String>();
        RubriqueDao dao = new RubriqueDao();
        if (action.equals("supprimerrb")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteRubrique(id);
            result.add("rbdeleted");
            Gson gson = new Gson();
            JsonElement e =
                gson.toJsonTree(result, new TypeToken<List<String>>() {
                }.getType());
            JsonArray array = e.getAsJsonArray();
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(array);
        }
        else if (action.equals("getrubbyid")){
            int idrub = Integer.parseInt(request.getParameter("id"));
            Rubrique r = new Rubrique();
            r = dao.getRubriqueID(idrub);
            String rubriqueJson = new Gson().toJson(r);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(rubriqueJson);
            
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
                                                            ServletException {
        ArrayList<String> result = new ArrayList<String>();
        RubriqueDao dao = new RubriqueDao();

        String id = request.getParameter("id");
        String code_rubrique = request.getParameter("code");
        String id_annexe = request.getParameter("idannexe");
        String lib_rubrique = request.getParameter("librubrique");
        String ordreedition = request.getParameter("ordreEdition");


        if (id == null || id.equals("")) {
            if (dao.CheckOrdreAnnexeExist(ordreedition,
                                          Integer.parseInt(id_annexe))) {
                result.add("annexe_ordre_exist");

                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
                
            } else if (dao.CheckCodeRubriqueExist(code_rubrique)) {
                result.add("codeexist");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
                
            } else {
                Rubrique r = new Rubrique();
                r.setCodeRubrique(code_rubrique);
                r.setLibelle(lib_rubrique);
                r.setOrdreEdition(ordreedition);
                r.setIdannexe(Integer.parseInt(id_annexe));

                dao.ajouterRubrique(r);

                result.add(code_rubrique);
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
            int idrb = Integer.parseInt(id.trim());
            Rubrique rubrique = dao.getRubriqueID(idrb);
            String codeancien = rubrique.getCodeRubrique();
            int idannexeancien = rubrique.getIdannexe();
            String ordreancien = rubrique.getOrdreEdition();
            if (!codeancien.equals(code_rubrique)) {
                chkcodeannexenv = dao.CheckCodeRubriqueExist(code_rubrique);
                result.add("codeexist");
            }
            if (!(idannexeancien == Integer.parseInt(id_annexe)) ||
                !ordreancien.equals(ordreedition)) {
                chkcodeannexenv =
                        dao.CheckOrdreAnnexeExist(ordreedition, Integer.parseInt(id_annexe));
                result.add("annexe_ordre_exist");
            }
            rubrique.setIdrubrique(idrb);
            rubrique.setCodeRubrique(code_rubrique);
            rubrique.setLibelle(lib_rubrique);
            rubrique.setOrdreEdition(ordreedition);
            rubrique.setIdannexe(Integer.parseInt(id_annexe));
            if (chkcodeannexenv == false) {
                dao.updateRubrique(rubrique);
            }

            Gson gson = new Gson();
            JsonElement element =
                gson.toJsonTree(result, new TypeToken<List<String>>() {
                }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(jsonArray);
        }
    }


}
