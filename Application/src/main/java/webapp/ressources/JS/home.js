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

    /*var url = "/api/project/add?name=salut&version=1&root=/&type=JAVA&user="+ Cookies.get('id');
    ApiRequest('GET',url,"",addProject);*/

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
    $("#listeProjets").empty();

    $.each(json, function(index, element) {
        console.log(index + " " +element);
        $('#listeProjets').append( '<a value=="'+ element.id +'" href="#" class="list-group-item">'+ element.name + '</a>');
    });
}

function addProject(json){
    console.log(json);
    console.log(json["id"]);
    $("#listeProjets").append(JSON.stringify(json));
    Cookies.set('project', json["id"]);
}




