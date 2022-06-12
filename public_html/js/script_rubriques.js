//script Rubriques
$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });

    $('#table').dataTable( {
        "scrollY" : 500, "scrollX" : true, "pageLength" : 50, dom : 'Bfrtip', buttons : [
        {
            extend : 'excelHtml5', title : 'Rubriques', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5]
            }
        },
        {
            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Rubriques', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5]
            }
        },, 
        {
            extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5]
            }

        },
        {
            extend : 'csvHtml5', title : 'Rubriques', exportOptions :  {
                columns : [0, 1, 2, 3, 4, 5]
            }
        }
]
    });

    $("#btnEnregistrer").unbind("click").click(function (event) {
        var id = "";
        var code = $("#code_rubrique").val().trim();
        var ordreEdition = $("#ordre_edition").val().trim();
        var librubrique = $("#lib_rubrique").val().trim();
        var idannexe = $("#ddlAnnexe :selected").val().trim();
        var formatordreedi = /(\d)$/;
        var formatcode = /([A-Z]|[a-z]|\d)$/;

        if (code == "") {
            Swal.fire('Code de la rubrique?', 'Veuillez saisir le code de la rubrique.', 'warning')
            return false;
        }
        
        if (!code.match(formatcode)){
            Swal.fire('Vérifiez votre code entré!', 'le code ne doit comporter que des chiffres et des lettres en majuscule.', 'question');
            return false;
        }
        

        if (ordreEdition == "") {
            Swal.fire('Libellé de la rubrique?', 'Veuillez saisir le libellé de la rubrique.', 'warning');
            return false;
        }
        
        if (!ordreEdition.match(formatordreedi)){
            Swal.fire('Vérifiez votre ordre d\'édition entré!', 'l\'ordre d\'édition ne doit comporter que des chiffres.', 'question');
            return false;
        }

        if (librubrique == "") {
            Swal.fire('Libellé de la rubrique?', 'Veuillez saisir le libellé de la rubrique.', 'warning');
            return false;
        }

        if (idannexe == "") {
            Swal.fire('Annexe choisi?', 'Veuillez choisir une annexe pour la rubrique.', 'warning');
            return false;
        }
        
        code = code.toUpperCase();

        $.ajax( {
            type : 'POST', url : 'servletrubrique', dataType : 'JSON', data :  {
                id : id, code : code, ordreEdition : ordreEdition, librubrique : librubrique, idannexe : idannexe
            },
            success : function (responseJson) {
                if (responseJson[0] == "annexe_ordre_exist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'L\'annexe et l\'ordre d\'édition de la rubrique existent Déjà!'
                    });
                }
                else if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de la rubrique existe Déjà!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'La rubrique avec le code ' + code + ' a été enregistrée avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }
            }
        });

    });

    //Remove a previously-attached event handler from the elements.
    $('#btnEnregistrer2').unbind("click").click(function (event) {

        var id = $('#txtid').val();
        var code = $("#code_rubrique2").val().trim();
        var ordreEdition = $("#ordre_edition2").val().trim();
        var librubrique = $("#lib_rubrique2").val().trim();
        var idannexe = $("#ddlAnnexe2 :selected").val().trim();
        var formatordreedi = /(\d)$/;
        var formatcode = /([A-Z]|[a-z]|\d)$/;

        if (code == "") {
            Swal.fire('Code de la rubrique?', 'Veuillez saisir le code de la rubrique.', 'warning')
            return false;
        }
        
        if (!code.match(formatcode)){
            Swal.fire('Vérifiez votre code entré!', 'le code ne doit comporter que des chiffres et des lettres en majuscule.', 'question');
            return false;
        }
        

        if (ordreEdition == "") {
            Swal.fire('Libellé de la rubrique?', 'Veuillez saisir le libellé de la rubrique.', 'warning');
            return false;
        }
        
        if (!ordreEdition.match(formatordreedi)){
            Swal.fire('Vérifiez votre ordre d\'édition entré!', 'l\'ordre d\'édition ne doit comporter que des chiffres.', 'question');
            return false;
        }

        if (librubrique == "") {
            Swal.fire('Libellé de la rubrique?', 'Veuillez saisir le libellé de la rubrique.', 'warning');
            return false;
        }

        if (idannexe == "") {
            Swal.fire('Annexe choisi?', 'Veuillez choisir une annexe pour la rubrique.', 'warning');
            return false;
        }
        
        code = code.toUpperCase();
        
        $.ajax( {
            type : 'POST', url : 'servletrubrique', dataType : 'JSON', data :  {
                id : id, code : code, ordreEdition : ordreEdition, librubrique : librubrique, idannexe : idannexe
            },
            success : function (responseJson) {

                if (responseJson[0] == "annexe_ordre_exist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'L\'annexe et l\'ordre d\'édition de la rubrique existent Déjà!'
                    });
                }
                else if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de la rubrique existe Déjà!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'La rubrique avec le code ' + code + ' a été enregistrée avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }

            }
        });
    });

});

function modifier(id) {
    $.ajax( {
        type : 'GET', url : 'servletrubrique', dataType : 'JSON', data :  {
            id : id, action : 'getrubbyid'
        },
        success : function (responseJson) {
            console.log(responseJson);
            $('#txtid').val(responseJson.idrubrique);
            $('#code_rubrique2').val(responseJson.codeRubrique);
            $('#ordre_edition2').val(responseJson.ordreEdition);
            $('#lib_rubrique2').val(responseJson.libelle);
            $('#ddlAnnexe2').val(responseJson.idannexe);
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
        title : 'Etes vous sûre?', text : "Tous les montants assignées à cette rubrique seront perdus!", icon : 'warning', showCancelButton : true, confirmButtonText : 'Oui, supprimez!', cancelButtonText : 'Non, annuler!', reverseButtons : false
    }).then((result) => 
    {
        if (result.isConfirmed) {
            $.ajax( {
                type : 'GET', url : 'servletrubrique', dataType : 'JSON', data :  {
                    id : id, action : 'supprimerrb'
                },
                success : function (responseJson) {
                    if (responseJson[0] == "rbdeleted") {
                        swalWithBootstrapButtons.fire('Supprimé!', 'La rubrique avec ses montants ont été supprimés avec succès.', 'success').then(function () {
                            window.location.reload();
                        });
                    }
                }
            });

        }
        else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire('Opération annulée', 'Cette rubrique est en sécurité :)', 'error');
        }
    });

}