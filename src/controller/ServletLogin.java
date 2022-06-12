package controller;


import Entities.LoginBean;
import Entities.Privilege;
import Entities.Utilisateur;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modele.LoginDao;
import modele.PrivilegeDao;
import modele.UtilisateurDao;


public class ServletLogin extends HttpServlet {

    @SuppressWarnings("compatibility:8413583573761728623")
    private static final long serialVersionUID = 1L;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String action = request.getParameter("action");
        if (action.equals("enregistrer")){
            String nom = request.getParameter("name");
            String prenom = request.getParameter("surname");
            String email = request.getParameter("email");
            String matricule = request.getParameter("matricule");
            Utilisateur u = new Utilisateur();
            u.setMatricule(matricule);
            u.setEmail(email);
            u.setPrenom(prenom);
            u.setNom(nom);
            
            UtilisateurDao dao = new UtilisateurDao();
            u.setIdutilisateur(dao.getUserByMatricule(matricule).getIdutilisateur());
            
            dao.updateUtilisateurProfile(u);
            
            PrivilegeDao pdao = new PrivilegeDao();
            
            u = dao.getUserByMatricule(matricule);
            
            HttpSession session = request.getSession();
            session.removeAttribute("prenom");
            session.removeAttribute("nom");
            session.removeAttribute("matricule");
            session.removeAttribute("email");
            session.removeAttribute("departement");
            session.removeAttribute("role");
            session.removeAttribute("iduser");
            session.removeAttribute("privileges");
            
            session.setAttribute("prenom", u.getPrenom().toUpperCase());
            session.setAttribute("nom", u.getNom());
            session.setAttribute("matricule", matricule);
            session.setAttribute("email", u.getEmail());
            session.setAttribute("departement", u.getDepartement());
            session.setAttribute("role", u.getLibrole());
            session.setAttribute("iduser", Integer.toString(u.getIdutilisateur()));
            session.setAttribute("privileges", pdao.getIdPrivilegesByRole(u.getIdrole()));
            
            
            request.getRequestDispatcher("users-profile.jsp").include(request, response);
            
        }else{
        LoginDao loginDao = new LoginDao();
        String matricule = request.getParameter("matricule");
        String motAcces = request.getParameter("mdp");

        LoginBean user = new LoginBean();
        UtilisateurDao dao = new UtilisateurDao();
        PrivilegeDao pdao = new PrivilegeDao();
        Utilisateur u = new Utilisateur();

        user.setMotAcces(motAcces);
        user.setPseudo(matricule);
        u = dao.getUserByMatricule(matricule);
        ArrayList<Privilege> pl = pdao.getAllPrivileges();
        Privilege p1 = pl.get(0);
        Privilege p2 = pl.get(1);
        Privilege p3 = pl.get(2);
        Privilege p4 = pl.get(3);
        Privilege p5 = pl.get(4);
        Privilege p6 = pl.get(5);
        Privilege p7 = pl.get(6);
        Privilege p8 = pl.get(7);
        Privilege p9 = pl.get(8);
        Privilege p10 = pl.get(9);
        Privilege p11 = pl.get(10);
        Privilege p12 = pl.get(11);

        if (loginDao.validate(user)) {
            
            HttpSession session = request.getSession();
            session.setAttribute("prenom", u.getPrenom().toUpperCase());
            session.setAttribute("nom", u.getNom());
            session.setAttribute("matricule", matricule);
            session.setAttribute("email", u.getEmail());
            session.setAttribute("departement", u.getDepartement());
            session.setAttribute("role", u.getLibrole());
            session.setAttribute("iduser", Integer.toString(u.getIdutilisateur()));
            session.setAttribute("privileges", pdao.getIdPrivilegesByRole(u.getIdrole()));
            session.setAttribute("p1", p1);
            session.setAttribute("p2", p2);
            session.setAttribute("p3", p3);
            session.setAttribute("p4", p4);
            session.setAttribute("p5", p5);
            session.setAttribute("p6", p6);
            session.setAttribute("p7", p7);
            session.setAttribute("p8", p8);
            session.setAttribute("p9", p9);
            session.setAttribute("p10", p10);
            session.setAttribute("p11", p11);
            session.setAttribute("p12", p12);
            
            session.setMaxInactiveInterval(900);
            
            request.getRequestDispatcher("PageAccueil.jsp").include(request, response);
        } else
            response.sendRedirect("Error.jsp");
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String action = request.getParameter("action");
        if (action.equals("logout")) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("PageFormLogin.jsp");
            
        }

    }
}
