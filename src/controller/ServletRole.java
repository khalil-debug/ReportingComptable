package controller;


import Entities.Privilege;
import Entities.Role;

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

import modele.PrivilegeDao;
import modele.RoleDao;


/* ajout + visualisation done */
public class ServletRole extends HttpServlet {


    @SuppressWarnings("compatibility:3368696811998892225")
    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }
    transient ArrayList<Privilege> lstprv = new ArrayList<Privilege>();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        RoleDao dao = new RoleDao();
        String action = request.getParameter("action");
        int idrole = Integer.parseInt(request.getParameter("id"));
        ArrayList<String> result = new ArrayList<String>();
        if (action.equals("supprimerRl")) {
            if (dao.deleteRole(idrole) == 0) {
                result.add("usrexist");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            } else {
                result.add("rolesup");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            }
        } else if (action.equals("getrlbyid")) {
            Role r = new Role();
            r = dao.getRoleById(idrole);
            String roleJson = new Gson().toJson(r);
            response.setContentType("text/plain;charset=UTF-8");
            System.out.println(roleJson);
            response.getWriter().print(roleJson);
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        ArrayList<String> result = new ArrayList<String>();
        RoleDao dao = new RoleDao();

        String id = request.getParameter("id");
        String role = request.getParameter("role");
        String action = request.getParameter("action");
        String roleUtil = request.getParameter("roleUtil");
        int idrlut=dao.getRoleByLibelle(roleUtil).getIdrole();
        
        String actionAjout = request.getParameter("actionAjout");
        String actionModif = request.getParameter("actionModif");

        if (action.equals("") || action.equals(null)) {

            //l'initialisation de la liste des privilèges
            if (actionModif.equals("setprivilegesModif") && actionAjout.equals("")) {
                String privilegeJson = request.getParameter("jsonPostRequest");
                Gson gson = new Gson();
                Privilege p = gson.fromJson(privilegeJson, Privilege.class);
                lstprv.add(p);
            }
            if (actionAjout.equals("setprivilegesAjout") && actionModif.equals("")) {
                String privilegeJson = request.getParameter("jsonPostRequest");
                Gson gson = new Gson();
                Privilege p = gson.fromJson(privilegeJson, Privilege.class);
                lstprv.add(p);
            }
        } else if (action.equals("ajoutRole")) {
            if (dao.CheckNomRoleExist(role)) {
                result.add("roleexist");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            } else {
                PrivilegeDao pdao= new PrivilegeDao();
                Role r = new Role();
                r.setLibrole(role);
                r.setSections(lstprv);
                dao.ajoutRole(r);
                r.setIdrole(dao.getRoleByLibelle(r.getLibrole()).getIdrole());
                for (int i = 0 ; i<lstprv.size(); i++){
                    pdao.addPrivileges(lstprv.get(i).getIdprivilege(), r.getIdrole());
                }
                lstprv.clear();
                result.add("");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);

            }
        } else if (action.equals("modifRole")) {
            boolean chkcodeannexenv = false;
            int idrole = Integer.parseInt(id.trim());
            Role role1 = dao.getRoleById(idrole);
            String roleancien = role1.getLibrole();
            if (!roleancien.equals(role))
                chkcodeannexenv = dao.CheckNomRoleExist(role);

            role1.setLibrole(role);

            if (chkcodeannexenv == false) {

                role1.setSections(lstprv);
                dao.updateRole(role1);
                lstprv.clear();
                System.out.println(role1.getIdrole()+" "+idrlut);
                if (role1.getIdrole() == idrlut){
                    result.add("a_se_re_authentifier");
                    System.out.println(role1.getIdrole()+" "+idrlut);
                }
                
            } else {
                result.add("roleexist");
                
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
