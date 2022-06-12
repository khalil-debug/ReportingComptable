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
<%if (p.indexOf(p8.getIdprivilege())!=-1){%>
<html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"/>
        <link href="css/style.css" rel="stylesheet" type="text/css"></link>
        <!-- jquery JavaScript Files -->
        <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        
        <!-- Page Fonctionalitie JavaScript File -->
        <script type="text/javascript" src="js/script_consult_variation.js"></script>
        
        <!-- jquery CSS File -->
        <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"></link>
        <script>
          $(function () {
              $("#dateD").datepicker( {
                  changeMonth : true, changeYear : true
              });
              $("#dateD").datepicker("option", "dateFormat", "mm/yy");
              $("#dateD").datepicker("option", "showAnim", "fold");
              
              $("#dateF").datepicker( {
                  changeMonth : true, changeYear : true
              });
              $("#dateF").datepicker("option", "dateFormat", "mm/yy");
              $("#dateF").datepicker("option", "showAnim", "fold");
          });
        </script>
    </head>
    <body>
     <%
      AnnexeDao extractdao = new AnnexeDao();
      request.setAttribute("annexes", extractdao.getAllAnnexes());
      BanqueDao extractB = new BanqueDao();
      request.setAttribute("banques", extractB.getAllBanques());
     %>
            <jsp:include page="/PageHeader.jsp"/><!-- End: page-top -->
            <main id="main" class="main">
            <div class="pagetitle">
                <h1>VARIATION DES D&Eacute;CLARATIONS</h1>
                 
                <nav style="--bs-breadcrumb-divider: '>';">
                    <ol class="breadcrumb">
                        <li class="breadcrumb-item">
                            <a href="PageAccueil.jsp">
                                <i class="bi bi-house-door"></i></a>
                        </li>
                         
                        <li class="breadcrumb-item">Tableau de bord</li>
                         
                        <li class="breadcrumb-item active">Consultation des
                                                           VARIATIONS DES DÉCLARATIONS</li>
                    </ol>
                </nav>
            </div>
            <section class="section">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col col-lg-12 col-sm-12">
                                        <h2 class="card-title">Variation des montants</h2>
                                    </div>
                                </div>
				<div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">Banque :</label>
                                    <div class="col-sm-10">
                                        <select id="ddlBanque"
                                                name="banque"
                                                class="listederoulante form-select">
                                            <option value="">Choisir une Banque</option>
                                            <c:forEach items="${banques}"
                                                       var="banque">
                                                <option value="${banque.idbanque}"
                                                        ${banque.idbanque == selectedBanque ? \'selected=\"selected\"\' : \'\'}>
                                                    ${banque.codebanque}
                                                    :
                                                    ${banque.libBanque}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">Annexe :</label>
                                    <div class="col-sm-10">
                                        <select id="ddlAnnexe" name="annexes"
                                                class="listederoulante form-select">
                                            <option value="">Choisir une Annexe</option>
                                            <c:forEach items="${annexes}"
                                                       var="annexe">
                                                <option value="${annexe.idannexe}"
                                                        ${annexe.idannexe == selectedAnnexe ? \'selected=\"selected\"\' : \'\'}>
                                                    ${annexe.codeannexe}
                                                    -
                                                    ${annexe.libelle}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mb-3">
                                    <label class="col-sm-2 col-form-label">Date Début 
                                                                           (mm/aaaa) :</label>
                                    <div class="col-sm-4">
                                        <input id="dateAncien"
                                               maxlength="7"
                                               placeholder="mm/aaaa"
                                               name="dateDeclaration"
                                               type="text" class="form-control"></input>
                                    </div>
				    <label class="col-sm-2 col-form-label">Date Fin 
                                                                           (mm/aaaa) :</label>
                                    <div class="col-sm-4">
                                        <input id="datePresent"
                                               maxlength="7"
                                               placeholder="mm/aaaa"
                                               name="dateDeclaration"
                                               type="text" class="form-control"></input>
                                    </div>
				    
                                    <div class="col-sm-4"
                                         style="text-align:right;">
                                        
                                    </div>
                                </div>
				<fieldset class="row mb-3">
                  			<legend class="col-form-label col-sm-2 pt-0">Label :</legend>
                  			     <div class="col-sm-4">
                    				<div class="form-check">
                      					<input class="form-check-input" type="radio" name="typeDisplay" id="md" value="normal" checked="" onclick="document.getElementById('btnConsulterVariation').disabled = true;">
                      					<label class="form-check-label" for="md">
                        					En Milles Dinars Tunisien
                      					</label>
                    				</div>
                    				<div class="form-check">
                      					<input class="form-check-input" type="radio" name="typeDisplay" id="pourcentage" value="pourcentage" onclick="document.getElementById('btnConsulterVariation').disabled = false;">
                      					<label class="form-check-label" for="pourcentage">
                        					En Pourcentage
                      					</label>
                    				</div>
                  			     </div>
					     <div class="col-sm-3" style="text-align:right;">
						<button type="button" id="btnConsulterVariation" class="btn btn-success" disabled="disabled">
                                               		<i class="bi bi-check-circle"></i>
                                                        Variations -10%
                                        	</button>
					     </div>
                                             <div class="col-sm-3" style="text-align:right;">
						<button type="button" id="btnConsulter" class="btn btn-secondary">
                                               		<i class="bi bi-collection"></i>
                                               		Toutes les variations
                                        	</button>
					     </div>
                		</fieldset>
                                <table id="tableauVariation"
                                       class="TableRef cell-border hover"
                                       style="width:100%"></table>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <jsp:include page="/PageFooter.jsp"/><!-- End: page-top -->
    </body>
</html>
<%}else response.sendRedirect("Error404.jsp");%>