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
<%if (p.indexOf(p2.getIdprivilege())!=-1){%>
<html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"/>
        <link href="css/style.css" rel="stylesheet" type="text/css"></link>
        <!-- jquery JavaScript Files -->
        <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        <!-- Page Fonctionalitie JavaScript File -->
        <script type="text/javascript" src="js/script_roles.js"></script>
        <!-- jquery CSS File -->
        <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"></link>
        <style type="text/css">
                .ui-widget-header, .ui-state-default, ui-button {
                    background: crimson;
                    border: 2px solid brown;
                    color: white;
                    font-weight: bold;
                }
            </style>
        <script type="text/javascript">
              $(function () {
                  $('#menu > ul').dropotron( {
                      mode : 'fade', globalOffsetY : 11, offsetY :  - 15
                  });

              });
        </script>
    </head>
    <body>
        <%
      RoleDao extractdao = new RoleDao();
      PrivilegeDao pdao = new PrivilegeDao();
      request.setAttribute("roles", extractdao.getAllRoles());
     %>
         
        <jsp:include page="/PageHeader.jsp"/><!-- End: page-top -->
         
        <main id="main" class="main">
            <div class="pagetitle">
                <h1>Role</h1>
                 
                <nav style="--bs-breadcrumb-divider: '>';">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">
                            <a href="PageAccueil.jsp">
                                <i class="bi bi-house-door"></i></a>
                        </li>
                         
                        <li class="breadcrumb-item">Administration</li>
                         
                        <li class="breadcrumb-item active">Gestion des Roles</li>
                    </ol>
                </nav>
            </div>
            <%if (extractdao.getAllRoles().isEmpty()){%>
            <h1>Rien &agrave; afficher!</h1>
            <%}else{%>
            <section class="section">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col col-lg-10 col-sm-8">
                                        <h2 class="card-title">Roles</h2>
                                    </div>
                                    <div class="col col-lg-2 col-sm-4 card-title"
                                         style="text-align: center;">
                                        <button type="button"
                                                class="btn btn-primary"
                                                data-bs-toggle="modal"
                                                data-bs-target="#dialogForm">Ajouter
                                                                             un
                                                                             R&ocirc;le</button>
                                    </div>
                                </div>
                                <table id="table"
                                       class="table cell-border hover"
                                       style="width:100%">
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Libelle du r&ocirc;le</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                    </thead>
                                     
                                    <tbody>
                                        <c:forEach items="${roles}" var="role">
                                            <tr>
                                                <th scope="row">
                                                    ${role.idrole}
                                                </th>
                                                <td>
                                                    ${role.librole}
                                                </td>
                                                <c:choose>
                                                    <c:when test="${role.idrole=='2'}">
                                                        <td>
                                                            <a type="button"
                                                               disabled="disabled"
                                                               class="btn btn-warning rounded-pill"
                                                               style="background-color: #fff3cd; cursor: not-allowed;">
                                                                <i class="bi bi-pencil-square"></i></a>
                                                             
                                                            <a title="Supprimer"
                                                               type="button"
                                                               class="btn btn-danger rounded-pill"
                                                               style="background-color: #f8d7da;cursor: not-allowed;"
                                                               disabled="disabled">
                                                                <i class="bx bxs-trash"></i></a>
                                                            
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td>
                                                            <a type="button"
                                                               class="btn btn-warning rounded-pill"
                                                               data-bs-toggle="modal"
                                                               data-bs-target="#dialogForm2"
                                                               onclick="modifier('${role.idrole}')">
                                                                <i class="bi bi-pencil-square"></i></a>
                                                             
                                                            <a title="Supprimer"
                                                               type="button"
                                                               class="btn btn-danger rounded-pill"
                                                               style="cursor: pointer;"
                                                               onclick="supprimer('${role.idrole}');">
                                                                <i class="bx bxs-trash"></i></a>
                                                        </td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <%}%>
        </main>
         
        <jsp:include page="/PageFooter.jsp"/><!-- End: page-top -->
         
        <%
            request.setAttribute("privilegesAjout", pdao.getAllPrivileges());
            request.setAttribute("privilegesModif", pdao.getAllPrivileges());
        %>
        <div class="modal fade" id="dialogForm" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Ajouter un r&ocirc;le :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">R&ocirc;le :</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control"
                                       id="role" name="role"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <legend class="col-form-label col-sm-3 pt-0">Privil&egrave;ges :</legend>
                            <div class="col-sm-10">
                                <c:forEach items="${privilegesAjout}" var="pr">
                                    <div class="form-check">
                                        <input class="form-check-input"
                                               type="checkbox"
                                               value="${pr.idprivilege}"
                                               id="${pr.idprivilege}"
                                               name="privileges"></input>
                                         
                                        <label class="form-check-label"
                                               for="${pr.idprivilege}">
                                            ${pr.libprivilege}
                                        </label>
                                    </div>
                                </c:forEach>
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
        <div class="modal fade" id="dialogForm2" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modifier un r&ocirc;le :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <input type="hidden" id="idroleUtil" value="${role}"></input>
                     
                    <input type="hidden" id="txtid"></input>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">R&ocirc;le :</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control"
                                       id="role2" name="role"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <legend class="col-form-label col-sm-3 pt-0">Privil&egrave;ges :</legend>
                            <div class="col-sm-10">
                                <c:forEach items="${privilegesAjout}" var="pr">
                                    <div class="form-check">
                                        <input class="form-check-input"
                                               type="checkbox"
                                               value="${pr.idprivilege}"
                                               id="${pr.idprivilege}Modif"
                                               name="privileges2"></input>
                                         
                                        <label class="form-check-label"
                                               for="${pr.idprivilege}Modif">
                                            ${pr.libprivilege}
                                        </label>
                                    </div>
                                </c:forEach>
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