/**
 * Created by thibom on 19/10/16.
 */

/* Faut true si le panel est visible à l'écran */
var derouler = false;

$("#ancrePanel").click(deroulerPanel);

function deroulerPanel(){
    if(!derouler){
        $("#panelDeroulant").animate({
            right: "0px",
        },1000);
        $("#ancrePanel").animate({
            right: "20%",
        },1000);
        derouler = true;
        $("#chevronAncre").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }
    else{
        $("#panelDeroulant").animate({
            right: "-20%",
        },1000);
        $("#ancrePanel").animate({
            right: "0px",
        },1000);
        derouler = false;
        $("#chevronAncre").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }

}


$(document).ready(function() {

    /////////////// TEST ////////////////
    // Test création d'un projet

    var url = "/api/project/add?name=test&version=1&root=/&type=JAVA&user="+ Cookies.get('id');
    ApiRequest('GET',url,"",addProject);


    // Liste des projets de l'utilisateur
    var url = "/api/project/getall?idUser=" +  Cookies.get('id');
    ApiRequest('GET',url,"",listProject);

    //Liste


    $("#deconnexion").on("click", function (e) {
        e.preventDefault();
        deconnexion();
    })





});

function listProject(json){
    console.log(json);
    $.each(json, function(index, element) {
        $('#listeProjets').append(element);
    });
}
function addProject(json){
    console.log(json);
    console.log(json["id"]);
}




