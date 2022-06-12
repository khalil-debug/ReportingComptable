//script annexe
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
            extend : 'excelHtml5', title : 'Annexes', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }
        },
        {
            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Annexes', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }
        },
        {
            extend : 'print', text : 'Imprimer', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }

        },
        {
            extend : 'csvHtml5', title : 'Annexes', exportOptions :  {
                columns : [0, 1, 2, 3, 4]
            }
        }
]
    });

    $("#btnEnregistrer").unbind("click").click(function (event) {
        var id = "";
        var code = $("#code_ax").val().trim();
        var abrvannexe = $("#abrev_annexe").val().trim();
        var libannexe = $("#lib_ax").val().trim();
        var periodicite = $("#periodicite").val().trim();
        var formatabrv = /([A-Z]|[a-z]|\d)$/;
        var formatcode = /(\d){2}$/;

        if (code == "") {
            Swal.fire('Code de l\'annexe?', 'Veuillez saisir le code de l\'annexe.', 'warning');
            return false;
        }
        
        if (!code.match(formatcode)){
            Swal.fire('V�rifiez votre code entr�!', 'le code ne doit comporter que <code>2<\/code> chiffres.', 'question');
            return false;
        }

        if (abrvannexe == "") {
            Swal.fire('Abr�viation de l\'annexe?', 'Veuillez saisir l\'abr�viation de l\'annexe.', 'warning');
            return false;
        }
        
        if (!abrvannexe.match(formatabrv)){
            Swal.fire('V�rifiez votre abr�viation entr�e!', 'l\'abr�viation ne doit comporter que des chiffres et des lettres.', 'question');
            return false;
        }

        if (libannexe == "") {
            Swal.fire('Libell� de l\'annexe?', 'Veuillez saisir le libell� de l\'annexe.', 'warning');
            return false;
        }

        if (periodicite == "") {
            Swal.fire('P�riodicit� de l\'annexe?', 'Veuillez choisir la p�riodicit� de l\'annexe.', 'warning');
            return false;
        }
        
        abrvannexe=abrvannexe.toUpperCase();

        $.ajax( {
            type : 'POST', url : 'servletannexe', dataType : 'JSON', data :  {
                id : id, code : code, abrvannexe : abrvannexe, libannexe : libannexe, periodicite : periodicite
            },
            success : function (responseJson) {
                if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de l\'annexe existe D�j�!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectu�!', 'L\'annexe avec le code ' + code + ' a �t� enregistr� avec succ�s!', 'success').then(function () {
                        window.location.reload();
                    });

                }
            }
        });

    });

    //Remove a previously-attached event handler from the elements.
    $('#btnEnregistrer2').unbind("click").click(function (event) {

        var id = $('#txtid').val();
        var code = $("#code_ax2").val().trim();
        var abrvannexe = $("#abrev_annexe2").val().trim();
        var libannexe = $("#lib_ax2").val();
        var periodicite = $("#periodicite2").val().trim();
        var formatabrv = /([A-Z]|[a-z]|\d)$/;
        var formatcode = /(\d){2}$/;

        if (code == "") {
            Swal.fire('Code de l\'annexe?', 'Veuillez saisir le code de l\'annexe.', 'warning');
            return false;
        }
        
        if (!code.match(formatcode)){
            Swal.fire('V�rifiez votre code entr�!', 'le code ne doit comporter que <code>2<\/code> chiffres.', 'question');
            return false;
        }

        if (abrvannexe == "") {
            Swal.fire('Abr�viation de l\'annexe?', 'Veuillez saisir l\'abr�viation de l\'annexe.', 'warning');
            return false;
        }
        
        if (!abrvannexe.match(formatabrv)){
            Swal.fire('V�rifiez votre abr�viation entr�e!', 'l\'abr�viation ne doit comporter que des chiffres et des lettres.', 'question');
            return false;
        }

        if (libannexe == "") {
            Swal.fire('Libell� de l\'annexe?', 'Veuillez saisir le libell� de l\'annexe.', 'question');
            return false;
        }

        if (periodicite == "") {
            Swal.fire('P�riodicit� de l\'annexe?', 'Veuillez choisir la p�riodicit� de l\'annexe.', 'question');
            return false;
        }
        
        abrvannexe=abrvannexe.toUpperCase();

        $.ajax( {
            type : 'POST', url : 'servletannexe', dataType : 'JSON', data :  {
                id : id, code : code, abrvannexe : abrvannexe, libannexe : libannexe, periodicite : periodicite
            },
            success : function (responseJson) {

                if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de l\'annexe existe D�j�!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectu�!', 'L\'annexe ' + code + ': ' + '\'' + abrvannexe + '.\'' + ' a �t� enregistr� avec succ�s!', 'success').then(function () {
                        window.location.reload();
                    });

                }

            }
        });
    });

});

function modifier(id) {
    $.ajax( {
        type : 'GET', url : 'servletannexe', dataType : 'JSON', data :  {
            id : id, action : 'getaxbyid'
        },
        success : function (responseJson) {
            $('#txtid').val(responseJson.idannexe);
            $('#code_ax2').val(responseJson.codeannexe);
            $('#abrev_annexe2').val(responseJson.abrevAnnexe);
            $('#lib_ax2').val(responseJson.libelle);
            $('#periodicite2').val(responseJson.periodicite);
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
        title : 'Etes vous s�re?', text : "Tous les montants assign�es � cette annexe seront perdus!", icon : 'warning', showCancelButton : true, confirmButtonText : 'Oui, supprimez!', cancelButtonText : 'Non, annuler!', reverseButtons : false
    }).then((result) => 
    {
        if (result.isConfirmed) {
            $.ajax( {
                type : 'GET', url : 'servletannexe', dataType : 'JSON', data :  {
                    id : id, action : 'supprimerax'
                },
                success : function (responseJson) {
                    if (responseJson[0] == "axdeleted") {
                        swalWithBootstrapButtons.fire('Supprim�!', 'L\'annexe avec ses montants ont �t� supprim�s avec succ�s.', 'success').then(function () {
                            window.location.reload();
                        });
                    }
                }
            });

        }
        else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire('Op�ration annul�e', 'Cette annexe est en s�curit� :)', 'error');
        }
    });

}