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
    refreshPage();

    /////////////// TEST ////////////////
    // Test création d'un projet

    //var url = "/api/project/add?name=test&version=1&root=/&type=JAVA&user="+ Cookies.get('id');
    //ApiRequest('GET',url,"",addProject);

    /*var url = "/api/project/add?name=salut&version=1&root=/&type=JAVA&user="+ Cookies.get('id');
    ApiRequest('GET',url,"",addProject);*/

    // Liste des projets de l'utilisateur
    //url = "/api/project/getall?idUser=" +  Cookies.get('id');
    //ApiRequest('GET',url,"",listProject);

    //Liste
    $("#deconnexion").on("click", function (e) {
        e.preventDefault();
        deconnexion();
    });

    // Créer un nouveau projet
    $('#modalCreateProjectSubmit').on("click", function (e) {
        e.preventDefault();
        var url = "/api/project/add?"+ $("#createProjectForm").serialize() +"&user="+ Cookies.get('idUser');
        console.log(url);
        ApiRequest('GET',url,"",addProject);
        refreshPage();
    });

    // Si on click sur un projet, on récupère son arborescence
    $(".userProject-list").on("click", function (e) {
        e.preventDefault();
        alert("get arborescence");
        var url = "/git/"+ Cookies.get('idUser') + "/" + $(this).attr("value") + "/branches";
        ApiRequest('GET',url,"",arborescenceAffichage);
    })



});

/* Actualise la page */
function refreshPage(){
    listProject();
}

/* Liste les projets d'un utilisateur */
function listProject(){
    url = "/api/project/getall?idUser=" +  Cookies.get('idUser');
    ApiRequest('GET',url,"",listProjectAffichage);
}

/* Affiche la liste des projets d'un utilisateur*/
function listProjectAffichage(json){
    console.log("Liste projets: " + JSON.stringify(json));
    $("#listeProjets").empty();

    $.each(json, function(index, element) {

        $('#listeProjets').append('<a href="#" value="'+ element.idProject +'"class="list-group-item userProject-list">' + element.name +'</a>');
    });
}
function addProject(json){
    console.log(json);
    //console.log(json["id"]);
    $("#listeProjets").append(JSON.stringify(json));
}

function arborescenceAffichage(json){
    console.log("arbo: " + JSON.stringify(json));
}




