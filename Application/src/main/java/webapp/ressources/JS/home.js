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
    // On actualise les champs de la page
    refreshPage();

    // Créer un nouveau projet
    $('#modalCreateProjectSubmit').on("click", function (e) {
        e.preventDefault();
        var url = "/api/project/?"+ $("#createProjectForm").serialize() +"&idUser="+ Cookies.get('idUser');
        ApiRequest('POST',url,"",addProject);
    });

    // Si on click sur un projet, on récupère ses informations
    $(".userProject-list").on("click", function (e) {
        e.preventDefault();
        var idProject = $(this).attr("value");
        listBranch(idProject);
        listCommit(idProject,"master");
        listDeveloppers(idProject);
    });

    //Si on change de branch
    $('#listBranch').on("change", function (e) {
        e.preventDefault();
        //console.log($('#listBranch option:selected').val() + " " + $('#listBranch option:selected').text());
        listCommit($('#listBranch option:selected').val(), $('#listBranch option:selected').text());
    });

    //Si on change de commit
    /*$('#listCommit').on("change",function(e){
        e.preventDefault();
        //console.log($('#listCommit option:selected').val() + " " + $('#listCommit option:selected').text());
        getArborescence($('#listCommit option:selected').val(),$('#listCommit option:selected').text());
    });*/
});

/* Actualise la page */
function refreshPage(){
    listProject();
    listCollarborations();
}

/* Liste les projets d'un utilisateur */
function listProject(){
    url = "/api/permissions/projects/users/" +  Cookies.get('idUser') + "/admin";
    ApiRequest('GET',url,"",function (json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Projets',
                message: 'Impossible de récupérer la liste des projets',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Liste projets: " + JSON.stringify(json));
            $("#listeProjets").empty();

            $.each(json, function(index, element) {
                $('#listeProjets').append('<a href="#" value="'+ element.idProject +'"class="list-group-item userProject-list">' + element.name +'</a>');
            });
        }
    });
}

/* Liste les collaborations d'un utilisateur */
function listCollarborations(){
    url = "/api/permissions/projects/users/" +  Cookies.get('idUser') + "/developers";
    ApiRequest('GET',url,"",function (json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Collaboration',
                message: 'Impossible de récupérer la liste de collaborations',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Liste collaboration: " + JSON.stringify(json));
            $("#listeCollaborations").empty();

            $.each(json, function(index, element) {
                $('#listeCollaborations').append('<a href="#" value="'+ element.idProject +'"class="list-group-item userProject-list">' + element.name +'</a>');
            });
        }
    });
}

/* Créer un nouveau projet */
function addProject(json){
    BootstrapDialog.show({
        title: 'Projets',
        message: 'Projet crée.'
    });
    console.log("Nouveau projet:" + JSON.stringify(json));
    refreshPage();
}


/* Liste les devéloppeurs d'un projet */
function listDeveloppers(idProject){
    var url = "/api/permissions/projects/"+ idProject +"/developers";
    ApiRequest('GET',url,"",function(json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Développeurs',
                message: 'Impossible de récupérer la liste des développeurs du projet',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else {
            console.log("List des devs du projets " + idProject + ": " + JSON.stringify(json));
        }
    });
}





