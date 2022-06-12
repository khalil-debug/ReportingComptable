package controller;


import Entities.Colonne;
import Entities.Utilisateur;

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
import modele.UtilisateurDao;


public class ServletUtilisateur extends HttpServlet {
    @SuppressWarnings("compatibility:-476800899736910664")
    private static final long serialVersionUID = 2896598271644049799L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        ArrayList<String> result = new ArrayList<String>();
        String action = request.getParameter("action");
        UtilisateurDao dao = new UtilisateurDao();
        if (action.equals("supprimerutil")) 
        {
            int id = Integer.parseInt(request.getParameter("id"));
            if (!dao.deleteUtilisateur(id)) {
                result.add("utilnotdeleted");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            } else {
                dao.deleteUtilisateur(id);
                result.add("utildeleted");
                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);
            }
        }
        else if (action.equals("afficherTousUtil"))
        {
            ArrayList<Utilisateur> c = new ArrayList<Utilisateur>();
            
            if (dao.getAllUsers().isEmpty())
            {
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
                c= dao.getAllUsers();
                String userJson = new Gson().toJson(c);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(userJson);
            }
        } 
        else if (action.equals("afficherUtilRl"))
        {
            int idrole = Integer.parseInt(request.getParameter("idrole"));
            ArrayList<Utilisateur> u = new ArrayList<Utilisateur>();
            
            if (dao.getUsersByRole(idrole).isEmpty())
            {
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
                u= dao.getUsersByRole(idrole);
                String userJson = new Gson().toJson(u);
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(userJson);
            }
        }
        else if (action.equals("getusrbyid")){
            int id = Integer.parseInt(request.getParameter("id"));
            Utilisateur c = new Utilisateur();
            c = dao.getUserById(id);
            String userJson = new Gson().toJson(c);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().print(userJson);
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("Appel doPost ");
        ArrayList<String> result = new ArrayList<String>();
        UtilisateurDao dao = new UtilisateurDao();

        String id = request.getParameter("id");
        String matricule = request.getParameter("matricule");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String MDP = request.getParameter("mdp");
        String departement = request.getParameter("dept");
        int role = Integer.parseInt(request.getParameter("role"));
        String mdpvalide =request.getParameter("mdpvalide");
        String matriculeUsr = request.getParameter("matriculeUsr");
        int idCurrentusr = dao.getUserByMatricule(matriculeUsr).getIdutilisateur();
        if (mdpvalide.equals("false")){
            response.sendRedirect("PageUsers.jsp");
        }

        if (id == null || id.equals("")) {
            if (dao.CheckMatriculeExist(matricule)) {
                result.add("matriculexist");

                Gson gson = new Gson();
                JsonElement e =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray array = e.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(array);

            } else {
                Utilisateur user = new Utilisateur();
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setEmail(email);
                user.setMotAcces(MDP);
                user.setDepartement(departement);
                user.setIdrole(role);
                user.setMatricule(matricule);

                dao.ajoutUtilisateur(user);

                result.add(matricule);
                Gson gson = new Gson();
                JsonElement element =
                    gson.toJsonTree(result, new TypeToken<List<String>>() {
                    }.getType());
                JsonArray jsonArray = element.getAsJsonArray();
                response.setContentType("text/plain;charset=UTF-8");
                response.getWriter().print(jsonArray);
            }
        } else {
            boolean chkmatriculeuser = false;
            int idusr = Integer.parseInt(id.trim());
            Utilisateur user = dao.getUserById(idusr);
            String mtriculeancien = user.getMatricule();
            if (!mtriculeancien.equals(matricule)) {
                chkmatriculeuser = dao.CheckMatriculeExist(matricule);
                result.add("loginRequired");
            }
            user.setIdutilisateur(idusr);
            user.setNom(nom);
            user.setPrenom(prenom);
            user.setEmail(email);
            user.setMotAcces(MDP);
            user.setDepartement(departement);
            user.setIdrole(role);
            user.setMatricule(matricule);
            if (chkmatriculeuser == false) {
                dao.updateUtilisateur(user);
                if (user.getIdutilisateur()==idCurrentusr){
                    result.add("loginRequired");
                }
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
