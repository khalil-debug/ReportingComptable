$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });

    $("#btnConsulter").unbind("click").click(function (event) {
        if ($.fn.DataTable.isDataTable('#tableadeclaration')) {
            $('#tableadeclaration').DataTable().destroy();
        }
        
        var idcolonne = $("#ddlColonne").val().trim();
        var idannexe = $("#ddlAnnexe").val().trim();
        var idbanque = $("#ddlBanque").val().trim();
        var dateDebut = $("#dateD").val().trim();
        var dateFin = $("#dateF").val().trim();

        console.log(idcolonne + " and " + idannexe);

        if (idcolonne == "") {
            Swal.fire('Colonne à consulter?', 'Veuillez choisir la colonne à consulter.', 'warning')
            return false;
        }
        if (idannexe == "") {
            Swal.fire('Annexe à consulter?', 'Veuillez choisir l\'annexe à consulter.', 'warning')
            return false;
        }
        if (idbanque == "") {
            Swal.fire('Banque à consulter?', 'Veuillez choisir la banque à consulter.', 'warning')
            return false;
        }
        if (dateDebut == "" || dateFin == "" || dateDebut.length != 7 || dateFin.length != 7) {
            Swal.fire('Date à consulter?', 'Veuillez entrer des dates valides à consulter.', 'warning')
            return false;
        }
        else {
            dateDebut = "01/" + dateDebut;
            dateFin = "01/" + dateFin;
        }
        var dateD = new Date(dateDebut);
        var dateF = new Date(dateFin);
        if (+dateD >= +dateF){
            Swal.fire('Date à consulter?', 'la date de début doit être inférieur à celle de la date de fin.', 'question');
            return false;
        }

        //methode de récupération des colonnes
        $.ajax( {
            type : 'POST', url : 'servletseriechrono', dataType : 'JSON', data :  {
                idcolonne : idcolonne, idannexe : idannexe, idbanque : idbanque, dateDebut : dateDebut, dateFin : dateFin, action : "displayDate"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'On n\'a trouvé aucune Date ayant ces caractéristiques à afficher encore!'
                    });
                }
                else {
                    $('#tableadeclaration').html("");
                    var html = "";
                    html = "<thead>" + "<tr>" + "<th scope=\"col\">CODE<\/th>" + "<th scope=\"col\">RUBRIQUE<\/th>";
                    for (var i = 0;i < responseJson.length;i++) {
                        var date = new Date(responseJson[i]);
                        var yyyy = date.getFullYear();
                        var mm = date.getMonth() + 1;
                        if (mm < 10)
                            mm = '0' + mm;
                        html += "<th scope=\"col\" style=\"text-align: center;\">" + mm + "/" + yyyy + "<\/th>";
                    }
                    html += "<\/tr>" + "<\/thead>" + "<tbody id=\"lignes\">" + "<\/tbody>";
                    $('#tableadeclaration').append(html);
                }
            }
        })
        //l'affichage des montants
        $.ajax( {
            type : 'POST', url : 'servletseriechrono', dataType : 'JSON', data :  {
                idcolonne : idcolonne, idannexe : idannexe, idbanque : idbanque, dateDebut : dateDebut, dateFin : dateFin, action : "displayLigneChrono"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                    Swal.fire('Aucun Montant trouvé', 'Veuillez verifier vos données entrées ou communiquer un administrateur pour ajouter des montants.', 'warning').then(function () {
                        window.location.reload();
                    });
                }
                else {
                    $('#lignes').html("");
                    var html = "";
                    for (var i = 0;i < responseJson.length;i++) {
                        html += "<tr style=\" page-break-inside:avoid; page-break-after:auto\">" + "<td style=\"font: 10px verdana;font-weight: bold;width: 10%;text-align: center;\">" + responseJson[i].codeRub + "<\/td>" + "<td style=\"font: 10px verdana;font-weight: bold;width: 30%;text-align: left;\">" + responseJson[i].libRub + "<\/td>";
                        for (var j in responseJson[i].montants) {
                            html += "<td style=\"font: 10px verdana;font-weight: bold;text-align: left;text-align: center;color:black;\">" + responseJson[i].montants[j] + "<\/td>";
                        }
                        html += "<\/tr>";
                    }
                    Swal.fire('On a récupéré pour vous ' + responseJson.length + ' rubriques.');
                    $('#lignes').append(html);
                    $('#tableadeclaration').dataTable( {
                        "scrollY" : 600, "scrollX" : true, "pageLength" : 75, dom : 'Bfrtip', buttons : [
                        {
                            extend : 'excelHtml5', title : 'SerieChrono' + ' ' + dateDebut + ' à ' + dateFin + ' Annexe '+ idannexe + ' Banque '+ idbanque
                        },
                        {
                            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'SerieChrono' + ' ' + dateDebut + ' à ' + dateFin+ ' Annexe '+ idannexe + ' Banque '+ idbanque
                        },
                        {
                            extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer'

                        },
                        {
                            extend : 'csvHtml5', title : 'SerieChrono' + ' ' + dateDebut + ' à ' + dateFin+ ' Annexe '+ idannexe + ' Banque '+ idbanque
                        }
]
                    });
                }
            }
        })

    })
})

function recupererColonnes() {
    var idannexe = $("#ddlAnnexe :selected").val().trim();
    if (idannexe == "noth") {
        Swal.fire('Annexe à consulter?', 'Veuillez choisir une annexe valide.', 'warning');
        return false;
    }
    $.ajax( {
        type : 'POST', url : 'servletcolonnes', dataType : 'JSON', data :  {
            idannexe : idannexe, action : "getcolbyax"
        },
        success : function (responseJson) {
            if (responseJson[0] == "rien") {
                Swal.fire( {
                    icon : 'error', title : 'Oops...', text : 'On n\'a trouvé aucune colonne à afficher encore!'
                });
            }
            else {
                $('#ddlColonne').html('');
                $('#ddlColonne').append("<option value=\"noth\">S&eacute;lectionner<\/option>");
                for (var i in responseJson) {
                    $('#ddlColonne').append("<option value=" + responseJson[i].idcolonne + ">" + responseJson[i].libcolonne + "<\/option>");
                }
            }
        }
    })

}