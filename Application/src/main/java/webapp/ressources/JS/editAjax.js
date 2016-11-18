$(document).ready(function() {

    $("#deconnexion").on("click", function (e) {
        e.preventDefault();
        deconnexion();
    });

    // On charge l'arborescence
    var url = "/api/";
    ApiRequest('GET',url,"",arborescence);

});


function arborescence(json){
    $("#arborescence").append(JSON.stringify(json));
}