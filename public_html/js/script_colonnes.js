//script Colonnes
$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });

    $('#table').dataTable( {
        "scrollY" : 300, "scrollX" : true, "pageLength" : 50, dom : 'Bfrtip', buttons : [
        {
            extend : 'excelHtml5', title : 'Colonnes', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }
        },
        {
            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Colonnes', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }
        },
        {
            extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }

        },
        {
            extend : 'csvHtml5', title : 'Colonnes', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }
        }
]
    });

    $("#btnEnregistrer").unbind("click").click(function (event) {
        var id = "";
        var code = $("#code_colonne").val().trim();
        var libcolonne = $("#lib_colonne").val().trim();
        var idannexe = $("#ddlAnnexe :selected").val().trim();

        if (code == "") {
            Swal.fire('Code de la colonne?', 'Veuillez saisir le code de la colonne.', 'warning')
            return false;
        }

        if (libcolonne == "") {
            Swal.fire('Libellé de la colonne?', 'Veuillez saisir le libellé de la colonne.', 'warning');
            return false;
        }

        if (idannexe == "") {
            Swal.fire('Annexe choisi?', 'Veuillez choisir une annexe pour la colonne.', 'warning');
            return false;
        }

        $.ajax( {
            type : 'POST', url : 'servletcolonnes', dataType : 'JSON', data :  {
                id : id, code : code, libcolonne : libcolonne, idannexe : idannexe, action : 'amCol'
            },
            success : function (responseJson) {
                if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de la colonne existe Déjà!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'La colonne avec le code ' + code + ' a été enregistrée avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }
            }
        });

    });

    //Remove a previously-attached event handler from the elements.
    $('#btnEnregistrer2').unbind("click").click(function (event) {

        var id = $('#txtid').val();
        var code = $("#code_colonne2").val().trim();
        var idannexe = $("#ddlAnnexe2 :selected").val().trim();
        var libcolonne = $("#lib_colonne2").val().trim();
        
        if (code == "") {
            Swal.fire('Code de la colonne?', 'Veuillez saisir le code de la colonne.', 'warning')
            return false;
        }

        if (libcolonne == "") {
            Swal.fire('Libellé de la colonne?', 'Veuillez saisir le libellé de la colonne.', 'warning');
            return false;
        }

        if (idannexe == "") {
            Swal.fire('Annexe choisi?', 'Veuillez choisir une annexe pour la colonne.', 'warning');
            return false;
        }

        $.ajax( {
            type : 'POST', url : 'servletcolonnes', dataType : 'JSON', data :  {
                id : id, code : code, libcolonne : libcolonne, idannexe : idannexe, action : 'amCol'
            },
            success : function (responseJson) {

                if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de la colonne existe Déjà!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'La colonne avec le code ' + code + ' a été enregistrée avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }

            }
        });
    });

});

function modifier(id) {
    $.ajax( {
        type : 'GET', url : 'servletcolonnes', dataType : 'JSON', data :  {
            id : id, action : 'getcolbyid'
        },
        success : function (responseJson) {
            $('#txtid').val(responseJson.idcolonne);
            $('#code_colonne2').val(responseJson.codecolonne);
            $('#lib_colonne2').val(responseJson.libcolonne);
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
        title : 'Etes vous sûre?', text : "Tous les montants assignées à cette colonne seront perdus!", icon : 'warning', showCancelButton : true, confirmButtonText : 'Oui, supprimez!', cancelButtonText : 'Non, annuler!', reverseButtons : false
    }).then((result) => 
    {
        if (result.isConfirmed) {
            $.ajax( {
                type : 'GET', url : 'servletcolonnes', dataType : 'JSON', data :  {
                    id : id, action : 'supprimercol'
                },
                success : function (responseJson) {
                    if (responseJson[0] == "coldeleted") {
                        swalWithBootstrapButtons.fire('Supprimé!', 'La colonne avec ses montants ont été supprimés avec succès.', 'success').then(function () {
                            window.location.reload();
                        });
                    }
                }
            });

        }
        else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire('Opération annulée', 'Cette colonne est en sécurité :)', 'error');
        }
    });

}