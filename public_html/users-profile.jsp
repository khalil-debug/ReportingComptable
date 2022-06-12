<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="modele.*"%>
<%@ page import="Entities.Privilege"%>
<%@ page import="java.util.ArrayList"%>
<% //on récupère notre utilisateur si le login est effectué
   String nom= (String) session.getAttribute("nom");
   String prenom= (String) session.getAttribute("prenom");
   String role = (String) session.getAttribute("role");
   String dep = (String) session.getAttribute("departement");
   String matricule = (String) session.getAttribute("matricule");
   String email = (String) session.getAttribute("email");
   String iduser = (String) session.getAttribute("iduser");
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
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Profile</title>
  </head>
  <body>
    <jsp:include page="/PageHeader.jsp"/>
    <main id="main" class="main">
    <section class="section profile">
      <div class="row">
        <div class="col-xl-4">

          <div class="card">
            <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">

              <img src="assets/img/profile-img.jpg" alt="Profile" class="rounded-circle">
              <h2><%=prenom.charAt(0)%><%=prenom.substring(1).toLowerCase()%> <%=nom%></h2>
              <h3><%=dep%></h3>
            </div>
          </div>

        </div>

        <div class="col-xl-8">

          <div class="card">
            <div class="card-body pt-3">
              <!-- Bordered Tabs -->
              <ul class="nav nav-tabs nav-tabs-bordered">

                <li class="nav-item">
                  <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#profile-overview">Overview</button>
                </li>

                <li class="nav-item">
                  <button class="nav-link" data-bs-toggle="tab" data-bs-target="#profile-edit">Modifier Profile</button>
                </li>

              </ul>
              <div class="tab-content pt-2">

                <div class="tab-pane fade show active profile-overview" id="profile-overview">
                
                  <h5 class="card-title">Details du Profile</h5>
                    
                  <div class="row">
                    <div class="col-lg-3 col-md-4 label ">Full Name</div>
                    <div class="col-lg-9 col-md-8"><%=prenom.charAt(0)%><%=prenom.substring(1).toLowerCase()%> <%=nom%></div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Département</div>
                    <div class="col-lg-9 col-md-8"><%=dep%></div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Matricule</div>
                    <div class="col-lg-9 col-md-8"><%=matricule%></div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Rôle</div>
                    <div class="col-lg-9 col-md-8"><%=role%></div>
                  </div>

                  <div class="row">
                    <div class="col-lg-3 col-md-4 label">Addresse Email</div>
                    <div class="col-lg-9 col-md-8"><%=email%></div>
                  </div>

                </div>

                <div class="tab-pane fade profile-edit pt-3" id="profile-edit">

                  <!-- Profile Edit Form -->
                  <form method="POST" action="servletlogin?action=enregistrer">
                  <input type="hidden" id="idusr" value="${iduser}">
                    <div class="row mb-3">
                      <label for="name" class="col-md-4 col-lg-3 col-form-label">Nom</label>
                      <div class="col-md-8 col-lg-9">
                        <input name="name" type="text" class="form-control" id="fullName" value="<%=nom%>">
                      </div>
                    </div>
                    <div class="row mb-3">
                      <label for="surname" class="col-md-4 col-lg-3 col-form-label">Prénom</label>
                      <div class="col-md-8 col-lg-9">
                        <input name="surname" type="text" class="form-control" id="fullName" value="<%=prenom.charAt(0)%><%=prenom.substring(1).toLowerCase()%>" >
                      </div>
                    </div>

                    <div class="row mb-3">
                      <label for="dept" class="col-md-4 col-lg-3 col-form-label">Département</label>
                      <div class="col-md-8 col-lg-9">
                        <input type="text" class="form-control" id="dept" value="<%=dep%>" readonly="readonly">
                      </div>
                    </div>

                    <div class="row mb-3">
                      <label for="Job" class="col-md-4 col-lg-3 col-form-label">Matricule</label>
                      <div class="col-md-8 col-lg-9">
                        <input name="matricule" type="text" class="form-control" value="<%=matricule%>" readonly="readonly">
                      </div>
                    </div>

                    <div class="row mb-3">
                      <label for="role" class="col-md-4 col-lg-3 col-form-label">Rôle</label>
                      <div class="col-md-8 col-lg-9">
                        <input type="text" class="form-control" id="role" value="<%=role%>" readonly="readonly">
                      </div>
                    </div>

                    <div class="row mb-3">
                      <label for="email" class="col-md-4 col-lg-3 col-form-label">Addresse Email</label>
                      <div class="col-md-8 col-lg-9">
                        <input name="email" type="email" class="form-control" id="email" value="<%=email%>" >
                      </div>
                    </div>

                    <div class="text-center">
                      <button type="submit" class="btn btn-primary">Enregistrer</button>
                    </div>
                  </form><!-- End Profile Edit Form -->

                </div>

              </div><!-- End Bordered Tabs -->

            </div>
          </div>

        </div>
      </div>
    </section>

  </main><!-- End #main -->
  </body>
</html>