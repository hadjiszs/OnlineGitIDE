$(document).ready(function() {


    /* Connexion à l'application */
    $("#btnConnexion").on("click",function(e){
        e.preventDefault();
        var url = "/api/user/login?" + $(this).closest("form").serialize();
        ApiRequest('GET',url,"",connexion);
    });

    /* Inscription à l'application */
    $("#btnInscription").on("click",function(e){
        e.preventDefault();
        var url = "/api/user/?" + $(this).closest("form").serialize();
        ApiRequest('POST',url,"",connexion);
    });

});
