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
        var idbanque = $("#ddlBanque").val().trim();
        var idannexe = $("#ddlAnnexe").val().trim();
        var date = $("#dateDeclaration").val().trim();

        console.log(idbanque + " and " + idannexe);

        if (idbanque == "") {
            Swal.fire('Banque à consulter?', 'Veuillez choisir la banque à consulter.', 'warning')
            return false;
        }
        if (idannexe == "") {
            Swal.fire('Annexe à consulter?', 'Veuillez choisir l\'annexe à consulter.', 'warning')
            return false;
        }
        if (date == "" || date.length != 7) {
            Swal.fire('Date à consulter?', 'Veuillez entrer une date valide à consulter.', 'warning')
            return false;
        }
        else {
            date = "01/" + date;
        }

        //methode de récupération des colonnes
        $.ajax( {
            type : 'POST', url : 'servletmouvements', dataType : 'JSON', data :  {
                idbanque : idbanque, idannexe : idannexe, date : date, action : "displayColDecl"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'On n\'a trouvé aucune colonne à afficher encore!'
                    });
                }
                else {
                    $('#tableadeclaration').html("");
                    var html = "";
                    html = "<thead>" + "<tr>" + "<th scope=\"col\">CODE<\/th>" + "<th scope=\"col\">RUBRIQUE<\/th>";
                    for (var i = 0;i < responseJson.length;i++) {
                        html += "<th scope=\"col\">" + responseJson[i].libcolonne + "<\/th>";
                        console.log(responseJson[i].libcolonne);
                    }
                    html += "<\/tr>" + "<\/thead>" + "<tbody id=\"lignes\">" + "<\/tbody>";
                    $('#tableadeclaration').append(html);
                }
            }
        })
        //la methode de recupération des montants
        $.ajax( {
            type : 'POST', url : 'servletmouvements', dataType : 'JSON', data :  {
                idbanque : idbanque, idannexe : idannexe, date : date, action : "displayLignesDecl"
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
                            extend : 'excelHtml5', title : 'Declaration' + ' ' + date + 'Banque' + idbanque
                        },
                        {
                            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Declaration' + ' ' + date + 'Banque' + idbanque
                        },
                        {
                            extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer'

                        },
                        {
                            extend : 'csvHtml5', title : 'Declaration' + ' ' + date + 'Banque' + idbanque
                        }
]
                    });
                }
            }

        })
    })
})