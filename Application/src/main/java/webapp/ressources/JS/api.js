
/* Crée une session avec un cookie qui contient le hashkey */
function connexion(json){
    BootstrapDialog.show({
        title: 'Connexion',
        message: 'vous êtes maintenant connecté',
        type: BootstrapDialog.TYPE_SUCCESS,
        closable: true,
        draggable: true
    });

    Cookies.set('idUser', json["idUser"]);
    Cookies.set('password', json["password"]);
    Cookies.set('mail', json["mail"]);
    Cookies.set('username', json["username"]);
    window.location.href = "/JSP/home.jsp";
}

/* Supprime tout les cookies de la session */
function deconnexion(){
    BootstrapDialog.show({
        title: 'Déconnexion',
        message: 'vous êtes maintenant déconnecté',
        type: BootstrapDialog.TYPE_SUCCESS,
        closable: true,
        draggable: true
    });

    Cookies.remove('idUser');
    Cookies.remove('password');
    Cookies.remove('mail');
    Cookies.remove('username');
    Cookies.remove('project');
    window.location.href = "/";
}

/* Execute une requête vers l'API */
function ApiRequest(method,url,dataIn,callback) {

    if(method === "GET"){
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: url,
            async: false,
            timeout: 5000,
            success: function(data, textStatus ){
                callback(data);
            },
            error: function(xhr, textStatus, errorThrown){
                callback(null);
            }
        });
    }else if(method === "POST"){
        $.ajax({
            type: 'POST',
            dataType: 'json',
            url: url,
            async: false,
            timeout: 5000,
            success: function(data, textStatus ){
                //alert('request successful');
                callback(data);
            },
            error: function(xhr, textStatus, errorThrown){
                callback(null);
            }
        });
    }
}


$(document).ready(function() {
    $("#deconnexion").on("click", function (e) {
        e.preventDefault();
        deconnexion();
    });
});

