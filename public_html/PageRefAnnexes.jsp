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
   if (p.indexOf(p4.getIdprivilege())!=-1||p.indexOf(p9.getIdprivilege())!=-1){
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
        <script type="text/javascript" src="js/script_Annexe.js"></script>
        <!-- jquery CSS File -->
        <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"></link>
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
      AnnexeDao extractdao = new AnnexeDao();
      request.setAttribute("annexes", extractdao.getAllAnnexes());
      
      
     %>
         
       <jsp:include page="/PageHeader.jsp"/><!-- End: page-top -->
         
        <main id="main" class="main">
            <div class="pagetitle">
                <h1>Annexe</h1>
                 
                <nav style="--bs-breadcrumb-divider: '>';">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">
                            <a href="PageAccueil.jsp">
                                <i class="bi bi-house-door"></i></a>
                        </li>
                         
                        <li class="breadcrumb-item">R&eacute;f&eacute;rentiels</li>
                         <%if (p.indexOf(p4.getIdprivilege())!=-1){%>
                        <li class="breadcrumb-item active">Gestion des Annexes</li>
                        <%}else {%>
                        <li class="breadcrumb-item active">Consultation des Annexes</li>
                        <%}%>
                    </ol>
                </nav>
            </div>
            <%if (extractdao.getAllAnnexes().isEmpty()){%>
            <h1>Rien &agrave; afficher!</h1>
            <%}else{%>
            <section class="section">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col col-lg-10 col-sm-8">
                                        <h2 class="card-title">Annexes</h2>
                                    </div><%if (p.indexOf(p4.getIdprivilege())!=-1){%>
                                    <div class="col col-lg-2 col-sm-4 card-title"
                                         style="text-align: center;">
                                        <button type="button"
                                                class="btn btn-primary"
                                                data-bs-toggle="modal"
                                                data-bs-target="#dialogForm">Ajouter
                                                                             une
                                                                             Annexe</button>
                                    </div>
                                    <%}else{%>
                                    <div class="col col-lg-2 col-sm-4 card-title"
                                         style="text-align: center;">
                                    </div>
                                    <%}%>
                                </div>
                                <table id="table"
                                       class="table cell-border hover"
                                       style="width:100%">
                                    <thead>
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">CODE</th>
                                            <th scope="col">ABREVIATION</th>
                                            <th scope="col">LIBELLE</th>
                                            <th scope="col">PERIODICITE</th>
                                            <%if (p.indexOf(p4.getIdprivilege())!=-1){%>
                                            <th scope="col">ACTION</th>
                                            <%}%>
                                        </tr>
                                    </thead>
                                     
                                    <tbody>
                                        <c:forEach items="${annexes}"
                                                   var="annexe">
                                            <tr>
                                                <th scope="row">
                                                    <c:out value="${annexe.idannexe}"/>
                                                </th>
                                                <td scope="row">
                                                    <c:out value="${annexe.codeannexe}"/>
                                                </td>
                                                <td scope="row">
                                                    <c:out value="${annexe.abrevAnnexe}"/>
                                                </td>
                                                <td scope="row">
                                                    <c:out value="${annexe.libelle}"/>
                                                </td>
                                                <td scope="row">
                                                    <c:out value="${annexe.periodicite}"/>
                                                </td>
                                                <%if (p.indexOf(p4.getIdprivilege())!=-1){%>
                                                <td scope="row">
                                                    <a type="button"
                                                       class="btn btn-warning rounded-pill"
                                                       data-bs-toggle="modal"
                                                       data-bs-target="#dialogForm2"
                                                       onclick="modifier('${annexe.idannexe}')"><i class="bi bi-pencil-square"></i> </a>
                                                     
                                                    <a title="Supprimer"
                                                           class="btn btn-danger rounded-pill"
                                                           style="cursor: pointer;"
                                                           onclick="supprimer('${annexe.idannexe}')"><i class="bx bxs-trash"></i></a>
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
         
        <jsp:include page="/PageFooter.jsp"/>
         
        <!-- End: page-top -->
        <%if (p.indexOf(p4.getIdprivilege())!=-1){%>
        <div class="modal fade" id="dialogForm" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Ajouter une Annexe :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">CODE :</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control"
                                       id="code_ax" maxlength="2"
                                       name="code_ax"></input>
                            </div>
                            <div class="col-sm-5"></div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">ABR&Eacute;VIATION :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="abrev_annexe" name="abrev_annexe"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">LIBELL&Eacute; :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="lib_ax" name="lib_ax"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">P&Eacute;RIODICIT&Eacute; :</label>
                            <div class="col-sm-8">
                                <select id="periodicite" name="periodicite"
                                        class="listederoulante form-select"
                                        title="periodicite">
                                    <option value="">Choisir une
                                                     p&eacute;riodicit&eacute;</option>
                                    <option value="Mensuelle">MENSUELLE</option>
                                    <option value="Trimestrielle">TRIMESTRIELLE</option>
                                    <option value="Semestrielle">SEMESTRIELLE</option>
                                    <option value="Annuelle">ANNUELLE</option>
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
        <!-- modification annexe-->
        <div class="modal fade" id="dialogForm2" tabindex="-1"
             style="display: none;" aria-hidden="true">
            <div class="modal-dialog modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modifier une Annexe :</h5>
                         
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <input type="hidden" id="txtid"></input>
                    <div class="modal-body">
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">CODE :</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control"
                                       id="code_ax2" maxlength="2"
                                       name="code_ax2"></input>
                            </div>
                            <div class="col-sm-5"></div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">ABR&Eacute;VIATION :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="abrev_annexe2" name="abrev_annexe2"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">LIBELL&Eacute; :</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control"
                                       id="lib_ax2" name="lib_ax2"></input>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-4 col-form-label">P&Eacute;RIODICIT&Eacute; :</label>
                            <div class="col-sm-8">
                                <select id="periodicite2" name="periodicite"
                                        class="listederoulante form-select"
                                        title="periodicite">
                                    <option value="">Choisir une
                                                     p&eacute;riodicit&eacute;</option>
                                    <option value="Mensuelle">MENSUELLE</option>
                                    <option value="Trimestrielle">TRIMESTRIELLE</option>
                                    <option value="Semestrielle">SEMESTRIELLE</option>
                                    <option value="Annuelle">ANNUELLE</option>
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