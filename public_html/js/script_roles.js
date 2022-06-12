//script Roles
$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });

    $('#table').dataTable( {
        "scrollY" : 400, "scrollX" : true, "pageLength" : 50, dom : 'Bfrtip', buttons : [
        {
            extend : 'excelHtml5', title : 'Roles', exportOptions: {
                    columns: [ 0, 1 ]
                }
        },
        {
            extend : 'pdfHtml5', title : 'Roles', exportOptions: {
                    columns: [ 0, 1 ]
                }
        },{
            extend: 'print',
            text : 'Imprimer',exportOptions: {
                    columns: [ 0, 1 ]
                }
            
        }, 
        {
            extend : 'csvHtml5', title : 'Roles', exportOptions: {
                    columns: [ 0, 1 ]
                }
        }
]
    });

    $("#btnEnregistrer").unbind("click").click(function (event) {
        var id = "";
        var role = $("#role").val().trim();
        var formatrole = /([a-z]|[A-Z]|\s)$/;

        if (role == "") {
            Swal.fire('Nouveau Role?', 'Veuillez saisir le nom du rôle.', 'warning')
            return false;
        }
        if (!role.match(formatrole)){
            Swal.fire('Vérifiez le rôle entré!', 'le Rôle ne doit pas comporter des chiffres ou des caractères spéciaux.', 'question');
            return false;
        }
        else {
            passPrivilegesAjout(id);
        }
        
        role = role.toLowerCase();
        role = role.charAt(0).toUpperCase()+ role.slice(1);

        $.ajax( {
            type : 'POST', url : 'servletrole', dataType : 'JSON', data :  {
                id : id, role : role, action : 'ajoutRole'
            },
            success : function (responseJson) {
                if (responseJson[0] == "roleesxist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Ce Rôle existe Déjà!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'Le role a été enregistré avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }
            }
        });

    });

    //Remove a previously-attached event handler from the elements.
    $('#btnEnregistrer2').unbind("click").click(function (event) {

        var id = $('#txtid').val();
        var role = $('#role2').val().trim();
        var roleUtil = $('#idroleUtil').val();
        var formatrole = /([a-z]|[A-Z]|\s)$/;
        if (role == "") {
            Swal.fire('Nouveau Role?', 'Veuillez saisir le nom du rôle.', 'warning')
            return false;
        }
        if (!role.match(formatrole)){
            Swal.fire('Vérifiez le rôle entré!', 'le Rôle ne doit pas comporter des chiffres ou des caractères spéciaux.', 'question');
            return false;
        }
        else {
            passPrivilegesModif(id);
        }
        
        role = role.toLowerCase();
        role = role.charAt(0).toUpperCase()+ role.slice(1);
        
        $.ajax( {
            type : 'POST', url : 'servletrole', dataType : 'JSON', data :  {
                id : id, role : role, action : 'modifRole', roleUtil : roleUtil
            },
            success : function (responseJson) {

                if (responseJson[0] == "roleexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Ce Rôle existe Déjà!'
                    });
                }else if (responseJson[0] == "a_se_re_authentifier") {
                    Swal.fire('Enregistrement effectué!', 'Le role ' + role + ' a été enregistré avec succés, mais vous devrez vous reconnecter pour des mesures de sécurité!', 'success').then(function () {
                        window.location.href='servletlogin?action=logout' ;
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'Le role ' + role + ' a été enregistré avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }

            }
        });
    });

});

function modifier(id) {
    initializeCheckboxes();
    $.ajax( {
        type : 'GET', url : 'servletrole', dataType : 'JSON', data :  {
            id : id, action : 'getrlbyid'
        },
        success : function (responseJson) {
            $('#txtid').val(responseJson.idrole);
            $('#role2').val(responseJson.librole);
            for (i in responseJson.sections) {
                $('#' + responseJson.sections[i].idprivilege + 'Modif').attr('checked', true);
            }
        }
    });
}

function initializeCheckboxes() {
    $('input[name="privileges2"]').each(function () {
        $(this).removeAttr('checked');
    })
}

function supprimer(id) {

    const swalWithBootstrapButtons = Swal.mixin( {
        customClass :  {
            confirmButton : 'btn btn-success', cancelButton : 'btn btn-danger'
        },
        buttonsStyling : true
    })

    swalWithBootstrapButtons.fire( {
        title : 'Etes vous sûre?', text : "Veuillez d'abord supprimer les utilisateurs ayant ce rôle puis les ré-ajouter plus tard, ou leur affecter un autre role avant d'affecter la suppression, s'il vous plaît!", icon : 'warning', showCancelButton : true, confirmButtonText : 'Oui, supprimez!', cancelButtonText : 'Non, annuler!', reverseButtons : false
    }).then((result) => 
    {
        if (result.isConfirmed) {
            $.ajax( {
                type : 'GET', url : 'servletrole', dataType : 'JSON', data :  {
                    id : id, action : 'supprimerRl'
                },
                success : function (responseJson) {
                    if (responseJson[0] == "usrexist") {
                        Swal.fire( {
                            icon : 'error', title : 'Oops...', text : 'Ce Rôle est déja affecté à un utilisateur!'
                        });
                    }
                    else if (responseJson[0] == "rolesup") {
                        swalWithBootstrapButtons.fire('Supprimé!', 'Le rôle a été supprimé avec succès.', 'success').then(function () {
                            window.location.reload();
                        });
                    }
                }
            });

        }
        else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire('Opération annulée', 'Ce role est en sécurité :)', 'error');
        }
    });

}

function passPrivilegesAjout(role) {
    $('input[name="privileges"]:checked').each(function () {
        var jsonData = new Object();
        jsonData.idprivilege = this.value;
        $.ajax( {
            url : 'servletrole', type : 'POST', data :  {
                jsonPostRequest : JSON.stringify(jsonData), role : role, actionAjout : "setprivilegesAjout", action : "", actionModif : ""
            },
            success : function (response) {
            },
            error : function (response) {
                alert("Error: Timeout please try again later");
            },
        });
    });
}

function passPrivilegesModif(role) {
    $('input[name="privileges2"]:checked').each(function () {
        var jsonData = new Object();
        jsonData.idprivilege = this.value;
        $.ajax( {
            url : 'servletrole', type : 'POST', data :  {
                jsonPostRequest : JSON.stringify(jsonData), role : role, actionModif : "setprivilegesModif", action : "", actionAjout : ""
            },
            success : function (response) {
            },
            error : function (response) {
                alert("Error: Timeout please try again later");
            },
        });
    });

}