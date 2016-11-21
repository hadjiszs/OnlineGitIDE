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
        listBranch($(this).attr("value"));
        /*var url = "/git/"+ Cookies.get('idUser') + "/" + $(this).attr("value") + "/branches";
        ApiRequest('GET',url,"",arborescenceAffichage);*/
    })



});

/* Actualise la page */
function refreshPage(){
    listProject();
}

/* Liste les projets d'un utilisateur */
function listProject(){
    url = "/api/permission/getall?idUser=" +  Cookies.get('idUser');
    ApiRequest('GET',url,"",function (json){
                console.log("Liste projets: " + JSON.stringify(json));
                $("#listeProjets").empty();

                $.each(json, function(index, element) {

                    $('#listeProjets').append('<a href="#" value="'+ element.idProject +'"class="list-group-item userProject-list">' + element.name +'</a>');
                });
    });
}

/* Créer un nouveau projet */
function addProject(json){
    console.log("Nouveau projet:" + JSON.stringify(json));
    //console.log(json["id"]);
    $("#listeProjets").append(JSON.stringify(json));
}


/* Liste les branches d'un projet*/
function listBranch(idProject){
    var url = "/git/"+ Cookies.get('idUser') + "/" + idproject + "/branches";
    ApiRequest('GET',url,"",function(json){
                $("#listBranch").append('<select class="form-control">');
                $("#listBranch").append('<option>' +JSON.stringify(json) + '</option>');
                $("#listBranch").append('</select>');
            });
}

function listCommit(idProject,branch){
    var url = "/git/"+ Cookies.get('idUser') + "/" + idproject + "/listCommit/" + branch;
    ApiRequest('GET',url,"",function(json){
                    console.log("Liste des commits de " + branch + ": " + JSON.stringify(json));
            });
}

function getFile(idProject,revision){
    var url = "/git/"+ Cookies.get('idUser') + "/" + idproject + "/" + revision;
    ApiRequest('GET',url,"",function(json){
        console.log("Contenu du fichier " + revision + ": " + JSON.stringify(json));
    });
}

function getArborescence(idProject,revision){
    var url = "/git/"+ Cookies.get('idUser') + "/" + idproject + "//tree/" + revision;
    ApiRequest('GET',url,"",function(json){
        console.log("Arborescence de " + revision + ": " + JSON.stringify(json));
    });
}








