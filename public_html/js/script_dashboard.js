$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });
    getBankChart(2);
    
    $('#btnConsulterbqtrv').unbind('click').click(function(event){
        var datemontsbq = $('#datemontsbq').val().trim();
        var datemontsbq1 = '01/'+datemontsbq;
        console.log(datemontsbq1);
        $.ajax( {
        type : 'GET', url : 'servletbanque', dataType : 'JSON', data :  {
            datemontsbq1 : datemontsbq1, action : 'getbqwithnomont'
        },
        
        success : function (responseJson) {
            var html="";
            $('#activityBq').html('');
            if (responseJson[0]=="nobq"){
                html+="<div class='activity-item d-flex'>"+
                        "<div class='activite-label'>aucune<\/div>"+
                        "<i class='bi bi-circle-fill activity-badge text-success align-self-start'><\/i>"+
                          "<div class='activity-content'>"+
                            "Toutes les banques on remis leurs montants pour la date  <a href='#' class='fw-bold text-dark'>"+datemontsbq+"<\/a>."+
                          "<\/div>"+
                        "<\/div>";
                $('#activityBq').append(html);
            }else{
            for (i in responseJson){
                console.log(responseJson[i]);
                html+="<div class='activity-item d-flex'>"+
                        "<div class='activite-label'>"+responseJson[i].abrvbanque+"<\/div>"+
                        "<i class='bi bi-circle-fill activity-badge text-danger align-self-start'><\/i>"+
                          "<div class='activity-content'>"+
                            "la banque "+responseJson[i].libBanque+" n'a pas encore de montants à la date <a href='#' class='fw-bold text-dark'>"+datemontsbq+"<\/a>."+
                          "<\/div>"+
                        "<\/div>";
            }
            $('#activityBq').append(html);
        }}
        });
    });
    
});
var chart ;
function getColumnsChart(idcol){
    $('#idcol').val(idcol);
    $('#canvas').html("");
    $('#canvas').append("<canvas id=\"barChart\" style=\"max-height: 400px;\"><\/canvas>")
}


function getBankChart(idbq){
    var idcol = $('#idcol').val();
    var annee= $('#barDate').val();
    var dates = new Array();
    $.ajax( {
            type : 'POST', url : 'servletseriechrono', dataType : 'JSON', data :  {
                idcolonne : idcol, idannexe : 1, idbanque : idbq, dateDebut : '01/01/'+annee, dateFin : '01/12/'+annee, action : "displayDate"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                    Swal.fire( {
                        icon : 'error', title : 'Oops...', text : 'On n\'a trouvé aucune Date ayant ces caractéristiques à afficher encore!'
                    });
                }
                else {
                   for (var i = 0;i < responseJson.length;i++) {
                        var datem = '';
                        var date = new Date(responseJson[i]);
                        var yyyy = date.getFullYear();
                        var mm = date.getMonth() + 1;
                        if (mm < 10)
                            mm = '0' + mm;
                        datem = mm+'/'+yyyy;
                        dates.push(datem);
                }
            }
            }
        });
        
    $.ajax( {
            type : 'POST', url : 'servletseriechrono', dataType : 'JSON', data :  {
                idcolonne : idcol, idannexe : 1, idbanque : idbq, dateDebut : '01/01/'+annee, dateFin : '01/12/'+annee, action : "displayLigneChrono"
            },
            success : function (responseJson) {
                if (responseJson[0] == "rien") {
                }
                else {
                
                var totalmontants=0;
                var montants = new Array();
                console.log(idcol);
                console.log(responseJson);
                    for (var j=0; j<dates.length;j++) {
                        totalmontants=0;
                        for (var i = 0;i < responseJson.length;i++){
                            totalmontants += responseJson[i].montants[j] ;
                        }
                        montants.push(totalmontants);
                    }
                    
                    this.idbanque = idbq;
                    this.chart = new Chart(document.querySelector('#barChart'), {
                    type: 'line',
                    data: {
                      labels: dates,
                      datasets: [{
                        label: 'Banque : ' + idbq + ', Annexe : 00 et Colonne : '+ idcol,
                        data: montants,
                        fill: false,
                        borderColor: 'rgb(75, 192, 192)',
                        borderCapStyle:'butt',
                        pointBorderWidth:1,
                        pointHoverRadius:5,
                        lineTension: 0.1,
                        tension:0.1
                      }]
                    },
                    options: {
                      scales: {
                        y: {
                          beginAtZero: true
                        }
                      }
                    }
                  });
                    
                }
            }
        });
        
}




