//script Banques
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
            extend : 'excelHtml5', title : 'Banques', exportOptions :  {
                columns : [0, 1, 2, 3]
            }
        },
        {
            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Banques', exportOptions :  {
                columns : [0, 1, 2, 3]
            }
        },
        {
            extend : 'print', orientaion: 'landscape', pageSize: 'TABLOID',text : 'Imprimer', exportOptions :  {
                columns : [0, 1, 2, 3]
            }

        },
        {
            extend : 'csvHtml5', title : 'Banques', exportOptions :  {
                columns : [0, 1, 2, 3]
            }
        }
]
    });

    $("#btnEnregistrer").unbind("click").click(function (event) {
        var id = "";
        var code = $("#code_banque").val().trim();
        var abrvbanque = $("#abrev_banque").val().trim();
        var libbanque = $("#lib_banque").val().trim();

        if (code == "") {
            Swal.fire('Code de la banque?', 'Veuillez saisir le code de la banque.', 'warning')
            return false;
        }

        if (abrvbanque == "") {
            Swal.fire('Abréviation de la banque?', 'Veuillez saisir l\'abréviation de la banque.', 'warning');
            return false;
        }

        if (libbanque == "") {
            Swal.fire('Libellé de la banque?', 'Veuillez saisir le libellé de la banque.', 'warning');
            return false;
        }
        
        libbanque = libbanque.toUpperCase();
        code = code.toUpperCase();
        abrvbanque = abrvbanque.toUpperCase();
        
        $.ajax( {
            type : 'POST', url : 'servletbanque', dataType : 'JSON', data :  {
                id : id, code : code, abrvbanque : abrvbanque, libbanque : libbanque
            },
            success : function (responseJson) {
                if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de la banque existe Déjà!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'La banque avec le code ' + code + ' a été enregistré avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }
            }
        });

    });

    //Remove a previously-attached event handler from the elements.
    $('#btnEnregistrer2').unbind("click").click(function (event) {

        var id = $('#txtid').val();
        var code = $("#code_banque2").val().trim();
        var abrvbanque = $("#abrev_banque2").val().trim();
        var libbanque = $("#lib_banque2").val();

        if (code == "") {
            Swal.fire('Code de la banque?', 'Veuillez saisir le code de la banque.', 'warning')
            return false;
        }

        if (abrvbanque == "") {
            Swal.fire('Abréviation de la banque?', 'Veuillez saisir l\'abréviation de la banque.', 'warning');
            return false;
        }

        if (libbanque == "") {
            Swal.fire('Libellé de la banque?', 'Veuillez saisir le libellé de la banque.', 'warning');
            return false;
        }
        
        libbanque = libbanque.toUpperCase();
        code = code.toUpperCase();
        abrvbanque = abrvbanque.toUpperCase();

        $.ajax( {
            type : 'POST', url : 'servletbanque', dataType : 'JSON', data :  {
                id : id, code : code, abrvbanque : abrvbanque, libbanque : libbanque
            },
            success : function (responseJson) {

                if (responseJson[0] == "codeexist") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'Le Code de la banque existe Déjà!'
                    });
                }
                else {
                    Swal.fire('Enregistrement effectué!', 'La banque avec le code ' + code + ' a été enregistré avec succés!', 'success').then(function () {
                        window.location.reload();
                    });

                }

            }
        });
    });

});

function modifier(id) {
    $.ajax( {
        type : 'GET', url : 'servletbanque', dataType : 'JSON', data :  {
            id : id, action : 'getbqbyid'
        },
        success : function (responseJson) {
            $('#txtid').val(responseJson.idbanque);
            $('#code_banque2').val(responseJson.codebanque);
            $('#abrev_banque2').val(responseJson.abrvbanque);
            $('#lib_banque2').val(responseJson.libBanque);
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
        title : 'Etes vous sûre?', text : "Tous les montants assignées à cette banque seront perdus!", icon : 'warning', showCancelButton : true, confirmButtonText : 'Oui, supprimez!', cancelButtonText : 'Non, annuler!', reverseButtons : false
    }).then((result) => 
    {
        if (result.isConfirmed) {
            $.ajax( {
                type : 'GET', url : 'servletbanque', dataType : 'JSON', data :  {
                    id : id, action : 'supprimerbq'
                },
                success : function (responseJson) {
                    if (responseJson[0] == "bqdeleted") {
                        swalWithBootstrapButtons.fire('Supprimé!', 'La banque avec ses montants ont été supprimés avec succès.', 'success').then(function () {
                            window.location.reload();
                        });
                    }
                }
            });

        }
        else if (result.dismiss === Swal.DismissReason.cancel) {
            swalWithBootstrapButtons.fire('Opération annulée', 'Cette banque est en sécurité :)', 'error');
        }
    });

}