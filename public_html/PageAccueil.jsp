<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="modele.*"%>
<%@ page import="Entities.Banque"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
String pattern = "MM/yyyy";
String dateInString =new SimpleDateFormat(pattern).format(new Date());
BanqueDao extractB = new BanqueDao();
ArrayList<Banque> b = (ArrayList<Banque>) extractB.recupererBanquesPasMontants("01/"+dateInString);
request.setAttribute("banquesPasmt", b);%>
<html>
    <head>
        <meta http-equiv="Content-Type"
              content="text/html; charset=windows-1252"/>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
        <script type="text/javascript" src="js/jquery-ui.min.js"></script>
        <script type="text/javascript" src="js/jquery.dropotron-1.0.js"></script>
        <script type="text/javascript" src="js/script_dashboard.js"></script>
        <script>
          $(function () {
              $("#datemontsbq").datepicker( {
                  changeMonth : true, changeYear : true
              });
              $("#datemontsbq").datepicker("option", "dateFormat", "mm/yy");
              $("#datemontsbq").datepicker("option", "showAnim", "fold");
              $('#datemontsbq').datepicker('setDate', new Date());
          });
        </script>
    </head>
    <body>
        <div id="wrapper">
            <main id="main" class="main">
                <jsp:include page="/PageHeader.jsp"/>
                <!-- End: page-top -->
                <div class="pagetitle">
                    <h1>Dashboard</h1>
                     
                    <nav>
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a href="PageAccueil.jsp">Accueil</a>
                            </li>
                             
                            <li class="breadcrumb-item active">Dashboard</li>
                        </ol>
                    </nav>
                </div>
                <section class="section dashboard">
                    <div class="row">
                        <div class="col-lg-8">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="card">
                                        <div class="filter">
                                            <a class="icon" href="#"
                                               data-bs-toggle="dropdown">
                                                <i class="bi bi-three-dots"></i></a>
                                            <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                                                <li class="dropdown-header text-start">
                                                    <h6>Filtre des Colonnes</h6>
                                                    <%ColonneDao extractC = new ColonneDao();
      request.setAttribute("colonnes", extractC.recupererColParAnnexe(1));%>
                                                    <c:forEach items="${colonnes}"
                                                               var="col">
                                                        <li>
                                                            <a class="dropdown-item"
                                                               href="#"
                                                               onclick="getColumnsChart('${col.idcolonne}');">
                                                                ${col.libcolonne}</a>
                                                        </li>
                                                    </c:forEach>
                                                </li>
                                                 
                                                <li class="dropdown-header text-start">
                                                    <h6>Filtre des banques</h6>
                                                </li>
                                                 
                                                <%
      request.setAttribute("banques", extractB.getAllBanques());%>
                                                 
                                                <c:forEach items="${banques}"
                                                           var="banque">
                                                    <li>
                                                        <a class="dropdown-item"
                                                           href="#"
                                                           onclick="getBankChart('${banque.idbanque}');">
                                                            ${banque.libBanque}</a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-5">
                                                    <h5 class="card-title">Variation
                                                                           des
                                                                           montants
                                                                           de
                                                                           l'ann&eacute;e</h5>
                                                </div>
                                                <div class="col-4 col-form-label">
                                                    <input id="barDate"
                                                           maxlength="4"
                                                           placeholder="aaaa"
                                                           name="dateDeclaration"
                                                           type="number"
                                                           class="form-control"
                                                           value="2019"
                                                           required="required"></input>
                                                </div>
                                            </div>
                                            <input type="hidden" id="idcol"
                                                   value="1"></input>
                                             
                                            <!-- Bar Chart -->
                                            <div id="canvas"
                                                 style="height: 400px;">
                                                <canvas id="barChart"
                                                        style="max-height: 400px;"></canvas>
                                            </div>
                                            <!-- End Bar CHart -->
                                        </div>
                                    </div>
                                    <!--end card-->
                                </div>
                            </div>
                            <!--end row-->
                        </div>
                        <div class="col-lg-4">
                            <!-- Recent Activity -->
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        Administration
                                        <span>| Banques sans montants</span>
                                    </h5>
                                    <div class="row">
                                        <div class="col-1"></div>
                                        <div class="col-7 col-form-label">
                                            <input id="datemontsbq"
                                                   maxlength="7"
                                                   placeholder="aaaa"
                                                   name="dateDeclaration"
                                                   type="text"
                                                   class="form-control"
                                                   required="required"></input>
                                        </div>
                                        <div class="col-1"></div>
                                        <div class="col-3 col-form-label">
                                            <button type="button"
                                                    id="btnConsulterbqtrv"
                                                    class="btn btn-primary">
                                                <i class="bi bi-collection"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="activity" id="activityBq">
                                        <%if (b.isEmpty()){%>
                                        <div class="activity-item d-flex">
                                            <div class="activite-label">aucune</div>
                                            <i class='bi bi-circle-fill activity-badge text-success align-self-start'></i>
                                            <div class="activity-content">
                                                Toutes les banques on remis leurs montants pour la date 
                                                <a href="#" id="dateRemis" class="fw-bold text-dark"> <%=dateInString%></a>.
                                            </div>
                                        </div>
                                        <!-- End activity item-->
                                        <%}else{%>
                                         
                                        <c:forEach items="${banquesPasmt}"
                                                   var="banque">
                                            <div class="activity-item d-flex">
                                                <div class="activite-label">
                                                    ${banque.abrvbanque}
                                                </div>
                                                <i class='bi bi-circle-fill activity-badge text-danger align-self-start'></i>
                                                <div class="activity-content">
                                                    la banque 
                                                    ${banque.libBanque}
                                                     n'a pas encore de montants
                                                    &agrave; la date 
                                                    <a href="#" id="dateRemis"
                                                       class="fw-bold text-dark">
                                                        <%=dateInString%></a>.
                                                </div>
                                            </div>
                                            <!-- End activity item-->
                                        </c:forEach>
                                         
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <!-- End Recent Activity -->
                        </div>
                    </div>
                </section>
            </main>
        </div>
        <jsp:include page="/PageFooter.jsp"/><!-- End: page-top -->
    </body>
</html>