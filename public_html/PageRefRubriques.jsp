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
   if (p.indexOf(p6.getIdprivilege())!=-1||p.indexOf(p11.getIdprivilege())!=-1){
%>
<html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"/>
        <link href="css/style.css" rel="stylesheet" type="text/css"></link>
        <!-- jquery JavaScript Files -->
        <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        <!-- Page Fonctionalitie JavaScript File -->
        <script type="text/javascript" src="js/script_rubriques.js"></script>
        <!-- jquery CSS File -->
        <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"></link>
        <title>PageRefRubriques</title>
        <style type="text/css">
                .ui-widget-header, .ui-state-default, ui-button {
                    background: crimson;
                    border: 2px solid brown;
                    color: white;
                    font-weight: bold;
                }
        </style>
        <script type="text/javascript">
          function confirm_delete() {

              return confirm('Etes vous sûre de vouloir supprimer cet élément ?')
          }

          $(function () {
              $('#menu > ul').dropotron( {
                  mode : 'fade', globalOffsetY : 11, offsetY :  - 15
              });

          });
        </script>
    </head>
    <body>
        <%
      AnnexeDao extractdao = new AnnexeDao();
      request.setAttribute("annexes", extractdao.getAllAnnexes());
     %>
         
        <%
      RubriqueDao dao = new RubriqueDao();
      request.setAttribute("rubriques", dao.getAllRubriques());
     %>
         
        <jsp:include page="/PageHeader.jsp"/><!-- End: page-top -->
         
        <main id="main" class="main">
            <div class="pagetitle">
                <h1>Rubrique</h1>
                 
                <nav style="--bs-breadcrumb-divider: '>';">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">
                            <a href="PageAccueil.jsp">
                                <i class="bi bi-house-door"></i></a>
                        </li>
                         
                        <li class="breadcrumb-item">R&eacute;f&eacute;rentiels</li>
                         <%if (p.indexOf(p6.getIdprivilege())!=-1){%>
                        <li class="breadcrumb-item active">Gestion des Rubriques</li>
                        <%}else{%>
                        <li class="breadcrumb-item active">Consultation des Rubriques</li>
                        <%}%>
                    </ol>
                </nav>
            </div>
            <%if (dao.getAllRubriques().isEmpty()){%>
            <h1>Rien &agrave; afficher!</h1>
            <%}else{%>
            <section class="section">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col col-lg-10 col-sm-8">
                                        <h2 class="card-title">Rubriques</h2>
                                    </div>
                                    <div class="col col-lg-2 col-sm-4 card-title"
                                         style="text-align: center;">
                                         <%if (p.indexOf(p6.getIdprivilege())!=-1){%>
                                        <button type="button"
                                                class="btn btn-primary"
                                                data-bs-toggle="modal"
                                                data-bs-target="#dialogForm">Ajouter
                                                                             une
                                                                             Rubrique</button>
                                        <%}%>
                                    </div>
                                </div>
                                <table id="table"
                                       class="table cell-border hover"
                                       style="width:100%">
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">CODE</th>
                                            <th scope="col">LIBELLE</th>
                                            <th scope="col">ORDRE EDITION</th>
                                            <th scope="col">ID ANNEXE</th>
                                            <th scope="col">CODE ANNEXE</th>
                                            <%if (p.indexOf(p6.getIdprivilege())!=-1){%>
                                            <th scope="col">ACTION</th>
                                            <%}%>
                                        </tr>
                                    </thead>
                                     
                                    <tbody>
                                        <c:forEach items="${rubriques}"
                                                   var="rubrique">
                                            <tr>
                                                <th scope="row">
                                                    ${rubrique.idrubrique}
                                                </th>
                                                <td>
                                                    ${rubrique.codeRubrique}
                                                </td>
                                                <td>
                                                    ${rubrique.libelle}
                                                </td>
                                                <td>
                                                    ${rubrique.ordreEdition}
                                                </td>
                                                <td>
                                                    ${rubrique.idannexe}
                                                </td>
                                                <td>
                                                    ${rubrique.codeax}
                                                </td>
                                                <%if (p.indexOf(p6.getIdprivilege())!=-1){%>
                                                <td>
                                                    <a type="button"
                                                       class="btn btn-warning rounded-pill"
                                                       data-bs-toggle="modal"
                                                       data-bs-target="#dialogForm2"
                                                       onclick="modifier('${rubrique.idrubrique}')"><i class="bi bi-pencil-square"></i> </a>
                                                     
                                                    <a title="Supprimer"
                                                           class="btn btn-danger rounded-pill"
                                                           style="cursor: pointer;"
                                                           onclick="supprimer('${rubrique.idrubrique}')"><i class="bx bxs-trash"></i></a>
                                                </td>
                                                <%}%>
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
        <%if (p.indexOf(p6.getIdprivilege())!=-1){%>
        <div class="modal fade" id="dialogForm" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Ajouter une Rubrique :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">CODE :</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control"
                                       id="code_rubrique" name="code_rubrique"></input>
                            </div>
                            <div class="col-sm-5"></div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">LIBELL&Eacute; :</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" style="height: 100px"
                                       id="lib_rubrique" name="lib_rubrique"></textarea>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">ORDRE
                                                                   D'EDITION :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="ordre_edition" name="ordre_edition"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">ANNEXE :</label>
                            <div class="col-sm-8">
                                <select id="ddlAnnexe" name="annexes_ajout"
                                        class="listederoulante form-select">
                                    <option value="">Choisir une Annexe</option>
                                    <c:forEach items="${annexes}" var="annexe">
                                        <option value="${annexe.idannexe}">
                                            ${annexe.codeannexe}
                                            -
                                            ${annexe.libelle}
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
        <!--la division de modification-->
        <div class="modal fade" id="dialogForm2" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modifier une Rubrique :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <input type="hidden" id="txtid"></input>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">CODE :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="code_rubrique2"
                                       name="code_rubrique2"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">LIBELL&Eacute; :</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" style="height: 100px"
                                       id="lib_rubrique2" name="lib_rubrique2"></textarea>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">ORDRE
                                                                   D'EDITION:</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="ordre_edition2"
                                       name="ordre_edition2"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">ANNEXE :</label>
                            <div class="col-sm-8">
                                <select id="ddlAnnexe2" name="annexes_ajout"
                                        class="listederoulante form-select">
                                    <option value="">Choisir une Annexe</option>
                                    <c:forEach items="${annexes}" var="annexe">
                                        <option value="${annexe.idannexe}">
                                            ${annexe.codeannexe}
                                            -
                                            ${annexe.libelle}
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
        <%}%>
    </body>
</html>
<%}else response.sendRedirect("Error404.jsp");%>