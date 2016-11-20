
/** Crée une session avec un cookie qui contient le hashkey */
function connexion(json){
    Cookies.set('id', json["idUser"]);
    Cookies.set('hashkey', json["hashkey"]);
    Cookies.set('mail', json["mail"]);
    Cookies.set('username', json["username"]);

    //console.log(json);
    window.location.href = "/JSP/home.jsp";
}

function deconnexion(){
    Cookies.remove('id');
    Cookies.remove('hashkey');
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
                //alert('request successful');
                callback(data);
            },
            error: function(xhr, textStatus, errorThrown){
                //alert('request failed');
                return null;
            }
        });
    }else if(method === "POST"){
        //alert("post");
   // }else{
        //alert("Method Ajax inconnue !");
    }


}

