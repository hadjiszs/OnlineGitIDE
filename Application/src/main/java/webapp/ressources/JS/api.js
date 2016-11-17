
/** Crée une session avec un cookie qui contient le hashkey */
function connexion(json){

    Cookies.set('hashkey', json["hashkey"]);
    Cookies.set('mail', json["mail"]);
    Cookies.set('pseudo', json["pseudo"]);

    console.log(json);
    window.location.href = "/JSP/home.jsp";
}

/* Execute une requête vers l'API */
function ApiRequest(method,url,dataIn,callback) {

    if(method == "GET"){
        $.ajax({
            type: 'GET',
            dataType: 'json',
            url: url,
            timeout: 5000,
            success: function(data, textStatus ){
                alert('request successful');
                callback(data);
            },
            error: function(xhr, textStatus, errorThrown){
                alert('request failed');
                return null;
            }
        });
    }else if(method == "POST"){
        alert("post");
    }else{
        alert("Method Ajax inconnue !");
    }


}

