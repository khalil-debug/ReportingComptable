$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });

    $("#btnConsulter").unbind("click").click(function (event) {

        if ($.fn.DataTable.isDataTable('#tableauVariation')) {
            $('#tableauVariation').DataTable().destroy();
        }
        var idbanque = $("#ddlBanque").val().trim();
        var idannexe = $("#ddlAnnexe").val().trim();
        var dateAnc = $("#dateAncien").val().trim();
        var datePres = $("#datePresent").val().trim();
        const types = document.querySelector('input[name="typeDisplay"]:checked').value;

        console.log(idbanque + " and " + idannexe + " and " + datePres);

        if (idbanque == "") {
            Swal.fire('Banque � consulter?', 'Veuillez choisir la banque � consulter.', 'warning')
            return false;
        }
        if (idannexe == "") {
            Swal.fire('Annexe � consulter?', 'Veuillez choisir l\'annexe � consulter.', 'warning')
            return false;
        }
        if (dateAnc == "" || datePres == "") {
            Swal.fire('Date � consulter?', 'Veuillez entrer la date � consulter.', 'warning')
            return false;
        }
        else {
            dateAnc = "01/" + dateAnc;
            datePres = "01/" + datePres;
        }
        
        var dateD = new Date(dateAnc);
        var dateF = new Date(datePres);
        if (+dateD > +dateF){
            Swal.fire('Date � consulter?', 'la date de d�but doit �tre strictement inf�rieur � celle de la date de fin.', 'question');
            return false;
        }

        //methode de r�cup�ration des colonnes
        $.ajax( {
            type : 'GET', url : 'servletvariations', dataType : 'JSON', data :  {
                idbanque : idbanque, idannexe : idannexe, datePres : datePres, action : "displayColVaria"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'On n\'a trouv� aucune Colonne ayant ces caract�ristiques � afficher encore!'
                    });
                }
                else {
                    $('#tableauVariation').html("");
                    var html = "";
                    html = "<thead>" + "<tr>" + "<th scope=\"col\">CODE<\/th>" + "<th scope=\"col\">RUBRIQUE<\/th>";
                    for (var i = 0;i < responseJson.length;i++) {
                        html += "<th scope=\"col\ style\"text-align: center;\">" + responseJson[i].libcolonne + "<\/th>";
                    }
                    html += "<\/tr>" + "<\/thead>" + "<tbody id=\"lignes\">" + "<\/tbody>";
                    $('#tableauVariation').append(html);
                }
            }
        });

        //la methode de recup�ration des montants en %
        if (types == 'normal') {
            $.ajax( {
                type : 'GET', url : 'servletvariations', dataType : 'JSON', data :  {
                    idbanque : idbanque, idannexe : idannexe, dateAnc : dateAnc, datePres : datePres, action : "normalEnMD"
                },
                success : function (responseJson) {
                    if (responseJson[0] == "rien") {
                        Swal.fire('Aucun Montant trouv�', 'Veuillez verifier vos donn�es entr�es ou communiquer un administrateur pour ajouter des montants.', 'warning').then(function () {
                            window.location.reload();
                        });
                    }
                    else {
                        $('#lignes').html("");
                        var html = "";
                        for (var i = 0;i < responseJson.length;i++) {
                            html += "<tr style=\" page-break-inside:avoid; page-break-after:auto\">" + "<td style=\"font: 10px verdana;font-weight: bold;width: 10%;text-align: center;\">" + responseJson[i].codeRub + "<\/td>" + "<td style=\"font: 10px verdana;font-weight: bold;width: 30%;text-align: left;\">" + responseJson[i].libRub + "<\/td>";
                            for (var j in responseJson[i].montants) {
                                html += "<td style=\"font: 10px verdana;font-weight: bold;text-align: left;text-align: center;color:black;\">" + parseFloat(responseJson[i].montants[j]) + "<\/td>";
                            }
                            html += "<\/tr>";
                        }
                        Swal.fire('On a r�cup�r� pour vous ' + responseJson.length + ' rubriques.');
                        $('#lignes').append(html);
                        $('#tableauVariation').dataTable( {
                            "scrollY" : 600, "scrollX" : true, "pageLength" : 75, dom : 'Bfrtip', buttons : [
                            {
                                extend : 'excelHtml5', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                            },
                            {
                                extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                            },
                            {
                                extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer'

                            },
                            {
                                extend : 'csvHtml5', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                            }
]
                        });
                    }
                }

            })
        }
        else {
            $.ajax( {
                type : 'GET', url : 'servletvariations', dataType : 'JSON', data :  {
                    idbanque : idbanque, idannexe : idannexe, dateAnc : dateAnc, datePres : datePres, action : "pourcentage"
                },
                success : function (responseJson) {
                    if (responseJson[0] == "rien") {
                        Swal.fire('Aucun Montant trouv�', 'Veuillez verifier vos donn�es entr�es ou communiquer un administrateur pour ajouter des montants.', 'warning').then(function () {
                            window.location.reload();
                        });
                    }
                    else {
                        $('#lignes').html("");
                        var html = "";
                        for (var i = 0;i < responseJson.length;i++) {
                            html += "<tr style=\" page-break-inside:avoid; page-break-after:auto\">" + "<td style=\"font: 10px verdana;font-weight: bold;width: 10%;text-align: center;\">" + responseJson[i].codeRub + "<\/td>" + "<td style=\"font: 10px verdana;font-weight: bold;width: 30%;text-align: left;\">" + responseJson[i].libRub + "<\/td>";
                            for (var j in responseJson[i].montants) {
                                if (parseFloat(responseJson[i].montants[j]) >= 10 || parseFloat(responseJson[i].montants[j]) <=  - 10) {
                                    html += "<td style=\"font: 10px verdana;font-weight: bold;text-align: left;text-align: center;color:black;background-color:#f8d7da; \">" + parseFloat(responseJson[i].montants[j]) + "%<\/td>";
                                }
                                else if (parseFloat(responseJson[i].montants[j]) == 0) {
                                    html += "<td style=\"font: 10px verdana;font-weight: bold;text-align: left;text-align: center;color:black;\">" + parseFloat(responseJson[i].montants[j]) + "%<\/td>";
                                }
                                else {
                                    html += "<td style=\"font: 10px verdana;font-weight: bold;text-align: left;text-align: center;color:black;background-color:#d1e7dd ;\">" + parseFloat(responseJson[i].montants[j]) + "%<\/td>";
                                }

                            }
                            html += "<\/tr>";
                        }
                        Swal.fire('On a r�cup�r� pour vous ' + responseJson.length + ' rubriques.');
                        $('#lignes').append(html);
                        $('#tableauVariation').dataTable( {
                            "scrollY" : 600, "scrollX" : true, "pageLength" : 75, dom : 'Bfrtip', buttons : [
                            {
                                extend : 'excelHtml5', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                            },
                            {
                                extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                            },
                            {
                                extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer'

                            },
                            {
                                extend : 'csvHtml5', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                            }
]
                        });
                    }
                }

            })

        }
    })
    $("#btnConsulterVariation").unbind("click").click(function (event) {

        if ($.fn.DataTable.isDataTable('#tableauVariation')) {
            $('#tableauVariation').DataTable().destroy();
        }
        var idbanque = $("#ddlBanque").val().trim();
        var idannexe = $("#ddlAnnexe").val().trim();
        var dateAnc = $("#dateAncien").val().trim();
        var datePres = $("#datePresent").val().trim();

        console.log(idbanque + " and " + idannexe + " and " + datePres);

        if (idbanque == "") {
            Swal.fire('Banque � consulter?', 'Veuillez choisir la banque � consulter.', 'warning')
            return false;
        }
        if (idannexe == "") {
            Swal.fire('Annexe � consulter?', 'Veuillez choisir l\'annexe � consulter.', 'warning')
            return false;
        }
        if (dateAnc == "" || datePres == "") {
            Swal.fire('Date � consulter?', 'Veuillez entrer la date � consulter.', 'warning')
            return false;
        }
        else {
            dateAnc = "01/" + dateAnc;
            datePres = "01/" + datePres;
        }
        
        var dateD = new Date(dateAnc);
        var dateF = new Date(datePres);
        if (+dateD >= +dateF){
            Swal.fire('Date � consulter?', 'la date de d�but doit �tre inf�rieur � celle de la date de fin.', 'question');
            return false;
        }

        //methode de r�cup�ration des colonnes
        $.ajax( {
            type : 'GET', url : 'servletvariations', dataType : 'JSON', data :  {
                idbanque : idbanque, idannexe : idannexe, datePres : datePres, action : "displayColVaria"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'On n\'a trouv� aucune Colonne ayant ces caract�ristiques � afficher encore!'
                    });
                }
                else {
                    $('#tableauVariation').html("");
                    var html = "";
                    html = "<thead>" + "<tr>" + "<th scope=\"col\">CODE<\/th>" + "<th scope=\"col\">RUBRIQUE<\/th>";
                    for (var i = 0;i < responseJson.length;i++) {
                        html += "<th scope=\"col\ style\"text-align: center;\">" + responseJson[i].libcolonne + "<\/th>";
                    }
                    html += "<\/tr>" + "<\/thead>" + "<tbody id=\"lignes\">" + "<\/tbody>";
                    $('#tableauVariation').append(html);
                }
            }
        });

        //la methode de recup�ration des montants en %
        $.ajax( {
            type : 'GET', url : 'servletvariations', dataType : 'JSON', data :  {
                idbanque : idbanque, idannexe : idannexe, dateAnc : dateAnc, datePres : datePres, action : "pourcentage"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                    Swal.fire('Aucun Montant trouv�', 'Veuillez verifier vos donn�es entr�es ou communiquer un administrateur pour ajouter des montants.', 'warning').then(function () {
                        window.location.reload();
                    });
                }
                else {
                    $('#lignes').html("");
                    var html = "";
                    for (var i = 0;i < responseJson.length;i++) {
                        html += "<tr style=\" page-break-inside:avoid; page-break-after:auto\">" + "<td style=\"font: 10px verdana;font-weight: bold;width: 10%;text-align: center;\">" + responseJson[i].codeRub + "<\/td>" + "<td style=\"font: 10px verdana;font-weight: bold;width: 30%;text-align: left;\">" + responseJson[i].libRub + "<\/td>";
                        for (var j in responseJson[i].montants) {
                            if (parseFloat(responseJson[i].montants[j]) >= 10 || parseFloat(responseJson[i].montants[j]) <=  - 10) {
                                html += "<td class='table-active' style=\"text-align: center;\"> <\/td>";
                            }
                            else {
                                html += "<td style=\"font: 10px verdana;font-weight: bold;text-align: left;text-align: center;color:black;\">" + parseFloat(responseJson[i].montants[j]) + "%<\/td>";
                            }
                        }
                        html += "<\/tr>";
                    }
                    Swal.fire('On a r�cup�r� pour vous ' + responseJson.length + ' rubriques.');
                    $('#lignes').append(html);
                    $('#tableauVariation').dataTable( {
                        "scrollY" : 600, "scrollX" : true, "pageLength" : 75, dom : 'Bfrtip', buttons : [
                        {
                            extend : 'excelHtml5', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                        },
                        {
                            extend : 'pdfHtml5',orientaion: 'landscape', pageSize: 'TABLOID', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                        },
                        {
                            extend : 'print',orientaion: 'landscape', pageSize: 'TABLOID', text : 'Imprimer'

                        },
                        {
                            extend : 'csvHtml5', title : 'Variation' + ' ' + dateAnc + ' � ' + datePres + ' Banque '+idbanque+' Annexe '+idannexe
                        }
]
                    });
                }
            }

        })
    })

})