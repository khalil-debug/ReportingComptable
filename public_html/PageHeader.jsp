<!DOCTYPE html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Entities.Privilege"%>
<%@ page import="java.util.ArrayList"%>
<% //on récupère notre utilisateur si le login est effectué
   String nom= (String) session.getAttribute("nom");
   String prenom= (String) session.getAttribute("prenom");
   String role = (String) session.getAttribute("role");
   ArrayList<Privilege> p = (ArrayList<Privilege>) session.getAttribute("privileges");
   String dep = (String) session.getAttribute("departement");
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
<html lang="en">
  <head>
    <meta charset="utf-8"></meta>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"></meta>
    <title>Header</title>
    <meta content="" name="description"></meta>
    <meta content="" name="keywords"></meta>
    <!-- Favicons -->
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <link href="css/jquery-ui.css" rel="stylesheet" type="text/css"></link>
    <script type="text/javascript" src="js/jquery-1.8.0.js"></script>
    <script type="text/javascript" src="js/jquery-ui.js"></script>
    <script type="text/javascript" src="js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="js/jquery.dropotron-1.0.js"></script>
    <script type="text/javascript" src="js/sweetalert2.min.js"></script>
    <link rel="stylesheet" href="css/sweetalert2.min.css"></link>
    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect"></link>
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet"></link>
    <!-- Vendor CSS Files -->
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet"></link>
    <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css"
          rel="stylesheet"></link>
    <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet"></link>
    <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet"></link>
    <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet"></link>
    <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet"></link>
    <link href="assets/vendor/simple-datatables/DataTables-1.11.5/css/dataTables.bootstrap5.css"
          rel="stylesheet"></link>
    <link href="assets/vendor/simple-datatables/DataTables-1.11.5/css/jquery.dataTables.css"
          rel="stylesheet"></link>
    <link href="assets/vendor/simple-datatables/Buttons-2.2.2/css/buttons.bootstrap5.css"
          rel="stylesheet"></link>
    <!-- sweetAlert2 Files -->
    <script type="text/javascript" src="js/sweetalert2.min.js"></script>
    <link rel="stylesheet" href="css/sweetalert2.min.css"></link>
    <!-- Template Main CSS File -->
    <link href="assets/css/style.css" rel="stylesheet"></link>
  </head>
  <body>
    <!-- ======= Header ======= -->
    <header id="header" class="header fixed-top d-flex align-items-center">
      <div class="d-flex align-items-center justify-content-between">
        <i class="bi bi-list toggle-sidebar-btn" style="margin-left: 5%;"></i>
      </div>
      <a href="PageAccueil.jsp"
         class="align-items-center"
         style="margin-left: 30%; width: 30%; text-align: center;">
        <img src="css/images/logo.png" alt="" style="width: 45%;"></img></a>
      <!-- End Logo -->
      <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">
          <li class="nav-item dropdown pe-3">
            <a class="nav-link nav-profile d-flex align-items-center pe-0"
               href="#" data-bs-toggle="dropdown">
              <img src="assets/img/profile-img.jpg" alt="Profile"
                   class="rounded-circle"></img><span class="d-none d-md-block dropdown-toggle ps-2">
                <%=prenom.charAt(0)%>.<%= nom%></span></a>
            <!-- End Profile Image Icon -->
            <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
              <li class="dropdown-header">
                <h6>
                  <%=prenom.charAt(0)%><%=prenom.substring(1).toLowerCase()%>
                  <%=nom%>
                </h6>
                <span>
                  <%=dep%></span>
              </li>
               
              <li>
                <hr class="dropdown-divider"></hr>
              </li>
               
              <li>
                <a class="dropdown-item d-flex align-items-center"
                   href="users-profile.jsp">
                  <i class="bi bi-person"></i><span>Mon Profile</span></a>
              </li>
              
               
              <li>
                <hr class="dropdown-divider"></hr>
              </li>
               
              <li>
                <a class="dropdown-item d-flex align-items-center"
                   href="servletlogin?action=logout">
                  <i class="bi bi-box-arrow-right"></i><span>Déconnexion</span></a>
              </li>
            </ul>
            <!-- End Profile Dropdown Items -->
          </li><!-- End Profile Nav -->
        </ul>
      </nav>
      <!-- End Icons Navigation -->
    </header><!-- End Header -->
     
    <aside id="sidebar" class="sidebar">
      <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item">
          <a class="nav-link collapsed" href="PageAccueil.jsp">
            <i class="bi bi-grid"></i><span>Dashboard</span></a>
        </li><!-- End Accueil Nav -->
         
        <%
                 if (p.indexOf(p1.getIdprivilege())!=-1||p.indexOf(p2.getIdprivilege())!=-1){
                 %>
         
        <li class="nav-item">
          <a class="nav-link collapsed" data-bs-target="#components-nav"
             data-bs-toggle="collapse" href="#">
            <i class="bi bi-menu-button-wide"></i><span>Administration</span><i class="bi bi-chevron-down ms-auto"></i></a>
          <ul id="components-nav" class="nav-content collapse "
              data-bs-parent="#sidebar-nav">
            <%if (p.indexOf(p1.getIdprivilege())!=-1){%>
             
            <li>
              <a href="PageUsers.jsp">
                <i class="bi bi-circle"></i><span>Utilisateurs</span></a>
            </li>
             
            <%}%>
             
            <%if (p.indexOf(p2.getIdprivilege())!=-1){%>
             
            <li>
              <a href="PageRoles.jsp">
                <i class="bi bi-circle"></i><span>R&ocirc;les</span></a>
            </li>
             
            <%}%>
             
          </ul>
        </li><!-- End Administration Nav -->
         
        <%}%>
         
        <%
                 if (p.indexOf(p3.getIdprivilege())!=-1||p.indexOf(p10.getIdprivilege())!=-1||p.indexOf(p4.getIdprivilege())!=-1||p.indexOf(p9.getIdprivilege())!=-1||p.indexOf(p5.getIdprivilege())!=-1||p.indexOf(p12.getIdprivilege())!=-1||p.indexOf(p6.getIdprivilege())!=-1||p.indexOf(p11.getIdprivilege())!=-1){
                 %>
         
        <li class="nav-item">
          <a class="nav-link collapsed" data-bs-target="#forms-nav"
             data-bs-toggle="collapse" href="#">
            <i class="bi bi-journal-text"></i><span>R&eacute;f&eacute;rentiels</span><i class="bi bi-chevron-down ms-auto"></i></a>
          <ul id="forms-nav" class="nav-content collapse "
              data-bs-parent="#sidebar-nav">
            <%if (p.indexOf(p4.getIdprivilege())!=-1||p.indexOf(p9.getIdprivilege())!=-1){%>
             
            <li>
              <a href="PageRefAnnexes.jsp">
                <i class="bi bi-circle"></i><span>Composantes des Annexes</span></a>
            </li>
             
            <%}%>
             
            <%if (p.indexOf(p3.getIdprivilege())!=-1||p.indexOf(p10.getIdprivilege())!=-1){%>
             
            <li>
              <a href="PageRefBanque.jsp">
                <i class="bi bi-circle"></i><span>Composantes des Banques</span></a>
            </li>
             
            <%}%>
             
            <%if (p.indexOf(p6.getIdprivilege())!=-1||p.indexOf(p11.getIdprivilege())!=-1){%>
             
            <li>
              <a href="PageRefRubriques.jsp">
                <i class="bi bi-circle"></i><span>Composantes des Rubriques</span></a>
            </li>
             
            <%}%>
             
            <%if (p.indexOf(p5.getIdprivilege())!=-1||p.indexOf(p12.getIdprivilege())!=-1){%>
             
            <li>
              <a href="PageRefColonnes.jsp">
                <i class="bi bi-circle"></i><span>Composantes des Colonnes</span></a>
            </li>
             
            <%}%>
          </ul>
        </li><!-- End Référentiels Nav -->
         
        <%}%>
         
        <%if (p.indexOf(p7.getIdprivilege())!=-1||p.indexOf(p8.getIdprivilege())!=-1){%>
         
        <li class="nav-item">
          <a class="nav-link collapsed" data-bs-target="#charts-nav"
             data-bs-toggle="collapse" href="#">
            <i class="bi bi-layout-text-window-reverse"></i><span>Tableau de bord</span><i class="bi bi-chevron-down ms-auto"></i></a>
          <%if (p.indexOf(p7.getIdprivilege())!=-1){%>
          <ul id="charts-nav" class="nav-content collapse "
              data-bs-parent="#sidebar-nav">
            <li>
              <a href="PageConsulterDeclaration.jsp">
                <i class="bi bi-circle"></i><span>Consultation
                                                  D&eacute;clarations</span></a>
            </li>
             
            <li>
              <a href="PageConsultSerieBanque.jsp">
                <i class="bi bi-circle"></i><span>Consultation S&eacute;rie
                                                  Banque</span></a>
            </li>
             
            <li>
              <a href="PageConsultSerieChrono.jsp">
                <i class="bi bi-circle"></i><span>Consultation S&eacute;rie
                                                  Chronologique</span></a>
            </li>
             
            <%}%>
             
            <%if (p.indexOf(p8.getIdprivilege())!=-1){%>
             
            <li>
              <a href="PageVariations.jsp">
                <i class="bi bi-circle"></i><span>Consultation Variations</span></a>
            </li>
             
            <%}%>
          </ul>
        </li><!-- End DashBoard Nav -->
         
        <%}%>
         
        <li class="nav-heading">&agrave;-propos</li>
         
        <li class="nav-item">
          <a class="nav-link collapsed" href="users-profile.jsp">
            <i class="bi bi-person"></i><span>Profile</span></a>
        </li><!-- End Profile Page Nav -->
      </ul>
    </aside>
     
    <a href="#"
       class="back-to-top d-flex align-items-center justify-content-center"> 
      <i class="bi bi-arrow-up-short"></i></a>
     
    <!-- JS Files -->
    <script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="assets/vendor/chart.js/chart.min.js"></script>
    <script src="assets/vendor/quill/quill.min.js"></script>
    <!-- datatables -->
    <script src="assets/vendor/simple-datatables/datatables.min.js"></script>
    <script src="assets/vendor/simple-datatables/DataTables-1.11.5/js/dataTables.bootstrap5.js"></script>
    <script src="assets/vendor/simple-datatables/DataTables-1.11.5/js/dataTables.dataTables.min.js"></script>
    <script src="assets/vendor/simple-datatables/Buttons-2.2.2/js/buttons.bootstrap5.js"></script>
    <script src="assets/vendor/php-email-form/validate.js"></script>
     <script src="assets/vendor/echarts/echarts.min.js"></script>
    <script src="assets/vendor/tinymce/tinymce.min.js"></script>
    <!-- Template Main JS File -->
    <script src="assets/js/main.js"></script>
  </body>
</html>