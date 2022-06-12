package controller;


import Entities.Annexe;
import Entities.Banque;

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


public class ServletBanque extends HttpServlet {
    @SuppressWarnings("compatibility:7000318552688144298")
    private static final long serialVersionUID = 1L;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String action = request.getParameter("action");
        ArrayList<String> result = new ArrayList<String>();
        BanqueDao dao = new BanqueDao();
        if (action.equals("supprimerbq")) {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.deleteBanque(id);
            result.add("bqdeleted");
            Gson gson = new Gson();
            JsonElement e =
                gson.toJsonTree(result, new TypeToken<List<String>>() {
                }.getType());
            JsonArray array = e.getAsJsonArray();
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(array);
        } else if(action.equals("getbqbyid")){
            int idbq = Integer.parseInt(request.getParameter("id"));
            Banque b = new Banque();
            b = dao.getBanqueID(idbq);
            String banqueJson = new Gson().toJson(b);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(banqueJson);
        }else if (action.equals("getbqwithnomont")){
            
            String date = request.getParameter("datemontsbq1");
            ArrayList<Banque> b = new ArrayList<Banque>();
            b = dao.recupererBanquesPasMontants(date);
            
            if (b.isEmpty()){
                result.add("nobq");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            }else {
            String banqueJson = new Gson().toJson(b);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(banqueJson);
            }
        }
    }


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        ArrayList<String> result = new ArrayList<String>();
        BanqueDao dao = new BanqueDao();

        String id = request.getParameter("id");
        String code_banque = request.getParameter("code");
        String abrev_banque = request.getParameter("abrvbanque");
        String lib_banque = request.getParameter("libbanque");


        if (id == null || id.equals("")) {
            if (dao.CheckCodeBanqueExist(code_banque)) {
                result.add("codeexist");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            } else {
                Banque b = new Banque();
                b.setCodebanque(code_banque);
                b.setAbrvbanque(abrev_banque);
                b.setLibBanque(lib_banque);

                dao.ajoutBanque(b);
                
                result.add(code_banque);
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
            int idbq = Integer.parseInt(id.trim());
            Banque banque = dao.getBanqueID(idbq);
            String codeancien = banque.getCodebanque();
            if (!codeancien.equals(code_banque))
                chkcodeannexenv = dao.CheckCodeBanqueExist(code_banque);

            banque.setCodebanque(code_banque);
            banque.setLibBanque(lib_banque);
            banque.setAbrvbanque(abrev_banque);
            if (chkcodeannexenv == false) {
                dao.updateBanque(banque);
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


    }

}

