<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="modele.*"%>
<%@ page import="Entities.Privilege"%>
<%@ page import="java.util.ArrayList"%>
<% //on récupère notre utilisateur si le login est effectué
   String nom= (String) session.getAttribute("nom");
   String prenom= (String) session.getAttribute("prenom");
   String role = (String) session.getAttribute("role");
   String matricule = (String) session.getAttribute("matricule");
   ArrayList<Privilege> p = (ArrayList<Privilege>) session.getAttribute("privileges");
   Privilege p1= (Privilege) session.getAttribute("p1");
   Privilege p2= (Privilege) session.getAttribute("p2");
   Privilege p3= (Privilege) session.getAttribute("p3");
   Privilege p4= (Privilege) session.getAttribute("p4");
   Privilege p5= (Privilege) session.getAttribute("p5");
   Privilege p6= (Privilege) session.getAttribute("p6");
   Privilege p7= (Privilege) session.getAttribute("p7");
   Privilege p8= (Privilege) session.getAttribute("p8");
   Privilege p9= (Privilege) session.getAttribute("p9");
   Privilege p10= (Privilege) session.getAttribute("p10");
   Privilege p11= (Privilege) session.getAttribute("p11");  
   Privilege p12= (Privilege) session.getAttribute("p12");
   
%>
<%if (p.indexOf(p1.getIdprivilege())!=-1){%>
<html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"/>
        <link href="css/style.css" rel="stylesheet" type="text/css"></link>
        <!-- jquery JavaScript Files -->
        <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
        <!-- Page Fonctionalitie JavaScript File -->
        <script type="text/javascript" src="js/script_users.js"></script>
        
        <style type="text/css">
            .col-sm-1{
                display: flex;
                align-items: center;
                justify-content: center;
            }
            span{
                
                padding-left: 2px;
                padding-right: 2px;
                margin-left: 2px;
                margin-right: 2px;
            }
            .bi-eye{
                color: #c5d6e7;
		cursor: pointer;
		transition: all .2s ease-in-out;
            }
            .bi-eye.active{
                    color: #428bfa;
            }
        </style>
    </head>
    <body>
        <%
        UtilisateurDao dao = new UtilisateurDao();
      request.setAttribute("utilisateurs",dao.getAllUsers());
      RoleDao extractdao = new RoleDao();
      request.setAttribute("consultrole", extractdao.getAllRoles());
     %>
            <jsp:include page="/PageHeader.jsp"/><!-- End: page-top -->
             
            <main id="main" class="main">
                <div class="pagetitle">
                    <h1>Utilisateur</h1>
                     
                    <nav style="--bs-breadcrumb-divider: '>';">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a href="PageAccueil.jsp">
                                    <i class="bi bi-house-door"></i></a>
                            </li>
                             
                            <li class="breadcrumb-item">Administration</li>
                             
                            <li class="breadcrumb-item active">Gestion des
                                                               Utilisateurs</li>
                        </ol>
                    </nav>
                </div>
                <%if (dao.getAllUsers().isEmpty()){%>
                <h1>Rien &agrave; afficher!</h1>
                <%}else{%>
                <section class="section">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col col-lg-10 col-sm-8">
                                            <h2 class="card-title">Utilisateurs</h2>
                                        </div>
                                        <div class="col col-lg-2 col-sm-4 card-title"
                                             style="text-align: center;">
                                            <button type="button"
                                                    class="btn btn-primary"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#dialogForm">Ajouter
                                                                                 Utilisateur</button>
                                        </div>
                                    </div>
                                    <table id="data"
                                           class="table cell-border hover"
                                           style="width:100%">
                                        <thead>
                                            <tr>
                                                <th scope="col">#</th>
                                                <th scope="col">MATRICULE</th>
                                                <th scope="col">NOM</th>
                                                <th scope="col">PR&Eacute;NOM</th>
                                                <th scope="col">EMAIL</th>
                                                <th scope="col">D&Eacute;PARTEMENT</th>
                                                <th scope="col">MOT DE PASSE</th>
                                                <th scope="col">R&Ocirc;LE</th>
                                                <th scope="col">ACTION</th>
                                            </tr>
                                        </thead>
                                         
                                        <tbody>
                                            <c:forEach items="${utilisateurs}"
                                                       var="utilisateur">
                                                <tr>
                                                    <th scope="row">
                                                        ${utilisateur.idutilisateur}
                                                    </th>
                                                    <td>
                                                        ${utilisateur.matricule}
                                                    </td>
                                                    <td>
                                                        ${utilisateur.nom}
                                                    </td>
                                                    <td>
                                                        ${utilisateur.prenom}
                                                    </td>
                                                    <td>
                                                        ${utilisateur.email}
                                                    </td>
                                                    <td>
                                                        ${utilisateur.departement}
                                                    </td>
                                                    <td>
                                                        *******
                                                    </td>
                                                    <td>
                                                        ${utilisateur.librole}
                                                    </td>
                                                    <td>
                                                        <a type="button"
                                                           class="btn btn-warning rounded-pill"
                                                           data-bs-toggle="modal"
                                                           data-bs-target="#dialogForm2"
                                                           onclick="modifier('${utilisateur.idutilisateur}')"><i class="bi bi-pencil-square"></i></a>
                                                         
                                                        <a title="Supprimer"
                                                           class="btn btn-danger rounded-pill"
                                                           style="cursor: pointer;"
                                                           onclick="supprimer('${utilisateur.idutilisateur}')"> <i class="bx bxs-trash"></i></a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                </div></section>
                <%}%>
            </main>
        </div>
        <jsp:include page="/PageFooter.jsp"/><!-- End: page-top -->
         
        <%
      
      request.setAttribute("roles", extractdao.getAllRoles());
      request.setAttribute("rolesModif", extractdao.getAllRoles());
      %>
        <div class="modal fade" id="dialogForm" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Ajouter un Utilisateur :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <input type="hidden" id="txtid"></input>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">MATRICULE :</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control"
                                       id="Matricule" name="matricule" required></input>
                            </div>
                            <div class="col-sm-5"></div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">NOM :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="Nom"
                                       name="nom" required></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">PR&Eacute;NOM :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="Prenom" name="prenom" required></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">EMAIL :</label>
                            <div class="col-sm-8">
                                <input type="email" class="form-control"
                                       id="email" name="email" required></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">MOT DE PASSE :</label>
                            <div class="col-sm-7">
                                <input type="password" class="form-control" id="MDP"
                                       name="MDP" required></input>
                            </div>
                            <div class ="col-sm-1" >
                                <span type="checkbox" onclick="show('MDP')"><i class="bi bi-eye"></i></span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">D&Eacute;PARTEMENT :</label>
                            <div class="col-sm-8">
                                <select id="Dept" name="departement"
                                        class="listederoulante form-select"
                                        title="departement">
                                    <option value="">Choisir un
                                                     d&eacute;partement</option>
                                    <option value="Informatique">Informatique</option>
                                    <option value="Finance">Finance</option>
                                    <option value="Supervision Bancaire">Supervision
                                                                         Bancaire</option>
                                </select>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">R&Ocirc;LE :</label>
                            <div class="col-sm-8">
                                <select id="role" name="role"
                                        class="listederoulante form-select"
                                        title="role">
                                    <option value="">Choisir un role</option>
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.idrole}">
                                            role
                                            ${role.librole}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Fermer</button>
                             
                            <button id="btnEnregistrer" type="button"
                                    class="btn btn-primary">Enregistrer</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--yeah this is the update div:-->
        <div class="modal fade" id="dialogForm2" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modifier un Utilisateur :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <input type="hidden" id="matriculeUsr" value="${matricule}"></input>
                    <input type="hidden" id="txtid"></input>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">MATRICULE :</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control"
                                       id="Matricule2" name="Matricule2" required></input>
                            </div>
                            <div class="col-sm-5"></div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">NOM :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="Nom2" name="Nom2" required></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">PR&Eacute;NOM :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="Prenom2" name="Prenom2" required></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">EMAIL :</label>
                            <div class="col-sm-8">
                                <input type="email" class="form-control"
                                       id="email2" name="email2" required></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">MOT DE PASSE :</label>
                            <div class="col-sm-7">
                                <input type="password" class="form-control" id="MDP2"
                                       name="MDP" required></input>
                            </div>
                            <div class ="col-sm-1" >
                                <span type="checkbox" onclick="show('MDP2')"><i class="bi bi-eye"></i></span>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">D&Eacute;PARTEMENT :</label>
                            <div class="col-sm-8">
                                <select id="Dept2" name="departement"
                                        class="listederoulante form-select"
                                        title="departement">
                                    <option value="">Choisir un
                                                     d&eacute;partement</option>
                                    <!--ajouter ici un département au choix sous avec value="nom du departement"-->
                                    <option value="Informatique">Informatique</option>
                                    <option value="Finance">Finance</option>
                                    <option value="Supervision Bancaire">Supervision
                                                                         Bancaire</option>
                                </select>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">R&Ocirc;LE :</label>
                            <div class="col-sm-8">
                                <select id="role2" name="role2"
                                        class="listederoulante form-select"
                                        title="role">
                                    <option value="">Choisir un role</option>
                                    <c:forEach items="${roles}" var="role">
                                        <option value="${role.idrole}">
                                            role
                                            ${role.librole}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Fermer</button>
                             
                            <button id="btnEnregistrer2" type="button"
                                    class="btn btn-primary">Enregistrer</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<%}else response.sendRedirect("Error404.jsp");%>