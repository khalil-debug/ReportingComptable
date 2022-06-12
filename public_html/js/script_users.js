//script Utilisateurs
$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });
    
    
    $('#data').dataTable( {
        "scrollY" : 500, "scrollX" : true, "pageLength" : 50, dom : 'Bfrtip', buttons : [
        {
            extend : 'excelHtml5', title : 'Users', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5, 6, 7]
            }
        },
        {
            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID' , title : 'Users', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5, 6, 7]
            }
        },
        {
            extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5, 6, 7]
            }

        },
        {
            extend : 'csvHtml5', title : 'Users', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5, 6, 7]
            }
        }
]
    });

    $("#btnEnregistrer").unbind("click").click(function (event) {
        var id = "";
        var matricule = $("#Matricule").val().trim().toUpperCase();
        var nom = $("#Nom").val().trim();
        var prenom = $("#Prenom").val().trim();
        var email = $("#email").val().trim();
        var mdp = $("#MDP").val().trim();
        var dept = $("#Dept").val().trim();
        var role = $("#role").val().trim();
        
        var paswd=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{10,18}$/;
        var formatemail = /^([a-z\d\.-]+)@(bct)\.(gov)\.(tn)$/;
        var formatmatricule = /^[0-9]{4}[A-Z]$/;
        var formatnomprenom = /([a-z]|[A-Z]|\s)$/;

        if (matricule == "") {
            Swal.fire('Matricule de l\'utilisateur?', 'Veuillez saisir la matricule de l\'utilisateur.', 'warning')
            return false;
        }else if (!matricule.match(formatmatricule)){
            Swal.fire('Caractéristiques de la matricule', 'La matricule doit être sous le format de 4 chiffres puis une lettre en majuscule, e.g. <code>0000A<\/code>', 'question');
            return false;
        }

        if (nom == "") {
            Swal.fire('Nom de l\'utilisateur?', 'Veuillez saisir le nom de l\'utilisateur.', 'warning');
            return false;
        }

        if (prenom == "") {
            Swal.fire('Prénom de l\'utilisateur?', 'Veuillez saisir le prénom de l\'utilisateur.', 'warning');
            return false;
        }
        
        if (!nom.match(formatnomprenom)||!prenom.match(formatnomprenom)){
            Swal.fire('Vérifiez le nom et le prénom', 'le nom et le prénom ne doivent pas comporter des chiffres ou des caractères spéciaux.', 'question');
            return false;
        }
        
        if (email == "") {
            Swal.fire('Email de l\'utilisateur?', 'Veuillez saisir l\'email de l\'utilisateur.', 'warning')
            return false;
        }else if (!email.match(formatemail)){
            Swal.fire('Caractéristiques de l\'email', 'Veuillez saisir un email sous format de <code>example@bct.gov.tn<\/code>.', 'question');
            return false;
        }

        var mdpvalide=true;
        if (mdp == "") {
            Swal.fire('Mot d\'accés de l\'utilisateur?', 'Veuillez saisir le mot d\'accés de l\'utilisateur.', 'warning');
            mdpvalide=false;
            return false;
        }else if (mdp.match(paswd)){
            Swal.fire('Caractéristiques du mot de passe', 'Veuillez saisir un mot de passe qui contient au moin: <ul>'+
                                                            '<li><code>1 minuscule<\/code>. ('+mdp.replace(/[^a-z]/g, '').length+')<\/li>'+
                                                            '<li><code>1 Majuscule<\/code>. ('+mdp.replace(/[^A-Z]/g, '').length+')<\/li>'+
                                                            '<li><code>1 chiffre<\/code>. ('+mdp.replace(/[^0-9]/g, '').length+')<\/li>'+
                                                            '<li><code>1 caractère spécial<\/code>. ('+mdp.replace(/((?![^A-Za-z0-9]).)/g, '').length+')<\/li>'+
                                                            '<li><code>longueur entre 10 et 18<\/code>. ('+mdp.length+')<\/li>'+
                                                            '<\/ul>', 'question');
            mdpvalide=false;
            return false;
            
        }
        
        if (dept == "") {
            Swal.fire('Département de l\'utilisateur?', 'Veuillez saisir le département de l\'utilisateur.', 'warning')
            return false;
        }

        if (role == "") {
            Swal.fire('Rôle de l\'utilisateur?', 'Veuillez ajouter le rôle de l\'utilisateur.', 'warning');
            return false;
        }

        $.ajax( {
            type : 'POST', url : 'servletutilisateur', dataType : 'JSON', data :  {
                id : id, matricule : matricule, nom : nom, prenom : prenom, email : email, mdp : mdp, dept : dept, role : role, mdpvalide : mdpvalide
            },
            success : function (responseJson) {
                if (responseJson[0] == "matriculexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'La Matricule doit être Unique!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'L\'utilisateur avec la matricule ' + matricule + ' a été créé avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }
            }
        });

    });

    //Remove a previously-attached event handler from the elements.
    $('#btnEnregistrer2').unbind("click").click(function (event) {

        var id = $("#txtid").val().trim();
        var matricule = $("#Matricule2").val().trim().toUpperCase();
        var nom = $("#Nom2").val().trim();
        var prenom = $("#Prenom2").val().trim();
        var email = $("#email2").val().trim();
        var mdp = $("#MDP2").val().trim();
        var dept = $("#Dept2").val().trim();
        var role = $("#role2").val().trim();
        var matriculeUsr = $("#matriculeUsr").val().trim();
        /*regex*/
        var paswd=  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\s).{10,18}$/;
        var formatemail = /^([a-z\d\.-]+)@(bct)\.(gov)\.(tn)$/;
        var formatmatricule = /\d{4}[A-Z]$/;
        var formatnomprenom = /([a-z]|[A-Z]|\s)$/;

        if (matricule == "") {
            Swal.fire('Matricule de l\'utilisateur?', 'Veuillez saisir la matricule de l\'utilisateur.', 'warning')
            return false;
        }else if (!matricule.match(formatmatricule)){
            Swal.fire('Caractéristiques de la matricule', 'La matricule doit être sous le format de 4 chiffres puis une lettre en majuscule, e.g. <code>0000A<\/code>', 'question');
            return false;
        }

        if (nom == "") {
            Swal.fire('Nom de l\'utilisateur?', 'Veuillez saisir le nom de l\'utilisateur.', 'warning');
            return false;
        }

        if (prenom == "") {
            Swal.fire('Prénom de l\'utilisateur?', 'Veuillez saisir le prénom de l\'utilisateur.', 'warning');
            return false;
        }
        
        if (!nom.match(formatnomprenom)||!prenom.match(formatnomprenom)){
            Swal.fire('Vérifiez le nom et le prénom', 'le nom et le prénom ne doivent pas comporter des chiffres ou des caractères spéciaux.', 'question');
            return false;
        }
        
        if (email == "") {
            Swal.fire('Email de l\'utilisateur?', 'Veuillez saisir l\'email de l\'utilisateur.', 'warning')
            return false;
        }else if (!email.match(formatemail)){
            Swal.fire('Caractéristiques de l\'email', 'Veuillez saisir un email sous format de <code>example@bct.gov.tn<\/code>.', 'question');
            return false;
        }
        
        
        var mdpvalide=true;
        if (mdp == "") {
            Swal.fire('Mot d\'accés de l\'utilisateur?', 'Veuillez saisir le mot d\'accés de l\'utilisateur.', 'warning');
            mdpvalide=false;
            return false;
        }else if (!mdp.match(paswd)){
            Swal.fire('Caractéristiques du mot de passe', 'Veuillez saisir un mot de passe qui contient au moin: <ul>'+
                                                            '<li><code>1 minuscule<\/code>. ('+mdp.replace(/[^a-z]/g, '').length+')<\/li>'+
                                                            '<li><code>1 Majuscule<\/code>. ('+mdp.replace(/[^A-Z]/g, '').length+')<\/li>'+
                                                            '<li><code>1 chiffre<\/code>. ('+mdp.replace(/[^0-9]/g, '').length+')<\/li>'+
                                                            '<li><code>1 caractère spécial<\/code>. ('+mdp.replace(/((?![^A-Za-z0-9]).)/g, '').length+')<\/li>'+
                                                            '<li><code>longueur entre 10 et 18<\/code>. ('+mdp.length+')<\/li>'+
                                                            '<\/ul>', 'question');
            mdpvalide=false;
            return false;
            
        }
        
        if (dept == "") {
            Swal.fire('Département de l\'utilisateur?', 'Veuillez saisir le département de l\'utilisateur.', 'warning')
            return false;
        }

        if (role == "") {
            Swal.fire('Rôle de l\'utilisateur?', 'Veuillez ajouter le rôle de l\'utilisateur.', 'warning');
            return false;
        }

        $.ajax( {
            type : 'POST', url : 'servletutilisateur', dataType : 'JSON', data :  {
                id : id, matricule : matricule, nom : nom, prenom : prenom, email : email, mdp : mdp, dept : dept, role : role, matriculeUsr : matriculeUsr, mdpvalide : mdpvalide
            },
            success : function (responseJson) {
                if (responseJson[0] == "matriculexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'La Matricule doit être Unique!'
                    });
                }else if (responseJson[0] == "loginRequired"){
                    Swal.fire('Enregistrement effectué!', 'L\'utilisateur avec la matricule ' + matricule + ' a été modifié avec succés!, mais vous devrez vous reconnecter pour des mesures de sécurité!', 'success').then(function () {
                        window.location.href='servletlogin?action=logout' ;
                    });
                }
                else {
                    Swal.fire('Modification(s) effectué!', 'L\'utilisateur avec la matricule ' + matricule + ' a été modifié avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }
            }
        });
    });

});

function modifier(id) {
    $.ajax( {
        type : 'GET', url : 'servletutilisateur', dataType : 'JSON', data :  {
            id : id, action : 'getusrbyid'
        },
        success : function (responseJson) {
            $('#txtid').val(responseJson.idutilisateur);
            $('#Matricule2').val(responseJson.matricule);
            $('#Nom2').val(responseJson.nom);
            $('#Prenom2').val(responseJson.prenom);
            $('#email2').val(responseJson.email);
            $('#MDP2').val(responseJson.motAcces);
            $('#Dept2').val(responseJson.departement);
            $('#role2').val(responseJson.idrole);
        }
    });
}

function supprimer(id) {

    const swalWithBootstrapButtons = Swal.mixin( {
        customClass :  {
            confirmButton : 'btn btn-success', cancelButton : 'btn btn-danger'
        },
        buttonsStyling : true
    })

    swalWithBootstrapButtons.fire( {
        title : 'Etes vous sûre?', text : "Veuillez verifier l'accord du superviseur avant de le supprimer, s'il vous plait!", icon : 'warning', showCancelButton : true, confirmButtonText : 'Oui, supprimez!', cancelButtonText : 'Non, annuler!', reverseButtons : false
    }).then((result) => 
    {
        if (result.isConfirmed) {
            $.ajax( {
                type : 'GET', url : 'servletutilisateur', dataType : 'JSON', data :  {
                    id : id, action : 'supprimerutil'
                },
                success : function (responseJson) {
                    if (responseJson[0] == "utilnotdeleted") {
                        Swal.fire( {
                            icon : 'error', title : 'Oops...', text : 'Désolé, on n\'a pas pu supprimer cet utilisateur!'
                        });
                    }
                    else if (responseJson[0] == "utildeleted") {
                        swalWithBootstrapButtons.fire('Supprimé!', 'L\'utilisateur a été supprimé avec succès.', 'success').then(function () {
                            window.location.reload();
                        });
                    }
                }
            });

        }
        else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire('Opération annulée', 'Cet utilisateur est en sécurité :)', 'error');
        }
    });

}

function show(variable) {
  var x = document.getElementById(variable);
  if (x.type === "password") {
    $('.bi-eye').addClass('active');
    x.type = "text";
  } else {
    $('.bi-eye').removeClass('active');
    x.type = "password";
  }
}