$(document).ready(function () {
    $("#spinner").bind("ajaxSend", function () {
        $this.show();
    }).bind("ajaxStop", function () {
        $(this).hide();
    }).bind("ajaxError", function () {
        $(this).hide();
    });

    $("#login").unbind("click").click(function (event) {
        var pseudo = $("#pseudo").val().trim();
        var mdp = $("#MDP").val().trim();

        if (pseudo == "") {
            Swal.fire('Votre Pseudo?', 'Veuillez saisir votre pseudo.', 'warning');
            return false;
        }

        if (mdp == "") {
            Swal.fire('Votre Mot De Passe?', 'Veuillez saisir votre mot de passe.', 'warning');
            return false;
        }
        $.ajax( {
            type : 'POST', url : 'servletlogin', dataType : 'JSON', data :  {
                mdp : mdp, pseudo : pseudo
            },
            success : function (responseJson) {

                if (responseJson[0] == "rien") {
                    toastMixin.fire( {
                        animation : true, title : 'Verifiez vos coordonnées.', icon : 'error'
                    });
                }
                else {
                    toastMixin.fire( {
                        animation : true, title : 'Bienvenu'
                    });
                }

            }
        });

    })

})