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


    ////////// PROJET /////////////

    // Créer un nouveau projet
    $('#btnProjet').on("click", function (e) {
        e.preventDefault();
        $("#creerProjet").modal('toggle');
        var url = "/api/project?"+ $("#formProjet").serialize() +"&idUser="+ Cookies.get('idUser');
        ApiRequest('POST',url,"",addProject);
    });

    // Si on click sur un projet, on récupère ses informations
    $("#listeProjets").on("click", ".userProject-list", function (e) {
        e.preventDefault();

        var idProject = $(this).attr("project");
        var idCreator = $(this).attr("creator");
        infoProject(idProject);
        // On indique le projet des collaborateurs si ajouts
        $("#select-collaborateur").attr("project",idProject);

        listBranch(idProject,idCreator,Cookies.get('idUser'));
        listCommit(idProject,idCreator,Cookies.get('idUser'),"master");
        listDeveloppers(idProject);
    });

    // Si on supprime un projet
    $(".userProject-list-delete").on("click", function(e){
        e.preventDefault();
        alert("delete projet TODO");
        //TODO
    });

    //SI on ouvre un projet
    $(".userProject-list-open").on("click", function(e){
        e.preventDefault();
        var idProject = $(this).attr("project");
        var idCreator = $(this).attr("creator");
        openProject(idProject, idCreator);
    });

    // SI on ajoute un collaborateur au projet
    $("#btnAjoutCollaborateur").on("click", function(e){
        var idProject = $("#select-collaborateur").attr("project");
        var idUser = $("#select-collaborateur option:selected").attr("idUser");
        addCollaborateur(idProject,idUser);
    });


    ////////// Collaboration /////////////

    //Si on click sur une collaboration
    $(".userCollaboration-list").on("click", function (e) {
        e.preventDefault();
        alert("collaboration TODO");
        // TODO
    });

    // Si on supprime une collaboration
    $('.userCollaboration-list-delete').on('click', function(e){
        e.preventDefault();
        alert("delete collabab TODO");
        // TODO
    });




    ////////// Information projet /////////////

    //Si on change de branch
    $('#selectBranch').on("change", function (e) {
        e.preventDefault();
        //console.log($('#listBranch option:selected').val() + " " + $('#listBranch option:selected').text());
        var idProject = $('#selectBranch option:selected').attr("project");
        var idCreator = $('#selectBranch option:selected').attr("creator");
        var idUser = Cookies.get('idUser');
        var branch = $('#selectBranch option:selected').text();
        listCommit(idProject,idCreator, idUser,branch);
    });

    // SI on click sur un commit
    /*$("#divAfficherCommit").on("click",function(e){
        e.preventDefault();
        var idProject = $(this).attr("project");
        var branch = $(this).attr("branch");
        var revision = $(this).attr("revision");
        var idCreator = $(this).attr("creator");
        openCommit(idProject, idCreator, branch, revision);
    });*/

    // SI on click le bouton diff d'un commit
    $("#divAfficherCommit").on("click", "#diffButton", function (e) {
        e.preventDefault();
        var idProject = $(this).attr("project");
        var idUser = Cookies.get('idUser');
        var revision = $(this).attr("revision");
        var idCreator = $(this).attr("creator");
        diffCommit(idProject,idCreator,idUser,revision);
    });

    // Si on veut récupérer l'archive
    $("#getArchive").on("click", function (e) {
        e.preventDefault();
        var idProject = $('#selectBranch option:selected').attr("project");
        var idCreator = $('#selectBranch option:selected').attr("creator");
        var idUser = Cookies.get('idUser');
        var branch = $('#selectBranch option:selected').text();
        getArchive(idProject,idCreator,idUser,branch);
    });

    // Si on clone un projet distant
    $("#btnCloneProjet").on("click", function (e) {
        e.preventDefault();
        // TODO
    })

});

/* Actualise la page */
function refreshPage(){
    listProject();
    listCollaborations();
    listUser();
}

/* Liste les projets d'un utilisateur */
function listProject(){
    url = "/api/permissions/projects/users/" +  Cookies.get('idUser') + "/admin";
    ApiRequest('GET',url,"",function (json){
        console.log("Liste projets: " + JSON.stringify(json));
        $("#listeProjets").empty();

        $.each(json, function(index, element) {
            $('#listeProjets').append(
                '<div class="btn-group col-lg-12 ligneListeProjet"> \
                    <button type="button" class="btn btn-default nomListeProjets userProject-list"\
                        project="' + element.idProject +'" creator="'+ element.idCreator + '">' + element.name +'</button> \
                    <button type="button" class="btn btn-default userProject-list-open" project="' + element.idProject +'" creator="'+ element.idCreator + '">\
                        <span class="glyphicon glyphicon-pencil"></span>\
                    </button> \
                    <button type="button" class="btn btn-default userProject-list-delete" project="' + element.idProject +'" creator="'+ element.idCreator + '">\
                        <span class="glyphicon glyphicon-remove spanSupprimerProjet"></span>\
                    </button> \
                </div>'
            );
        });
    });
}

/* Liste les collaborations d'un utilisateur */
function listCollaborations(){
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
                $('#listeCollaborations').append(
                '<div class="btn-group col-lg-12 ligneListeCollaborations"> \
                    <button type="button" class="btn btn-default nomListeCollaborations userCollaboration-list" project="'+ element.idProject +'" creator="'+ element.idCreator + '">' + element.name +'</button> \
                    <button type="button" class="btn btn-default userCollaboration-list-delete" project="'+ element.idProject +'" creator="'+ element.idCreator + '">\
                        <span class="glyphicon glyphicon-remove spanSupprimerProjet"></span>\
                    </button> \
                </div>'
                );
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
        console.log("List des devs du projets " + idProject + ": " + JSON.stringify(json));

        $('#listDev').empty();
        $.each(json, function(index, element) {
            $('#listDev').append(
                '<li class="list-group-item">' +element.username + '</li>'
            );
        });
    });
}

/* Liste les développeur de l'application */
function listUser() {
    var url = "/api/user/";
    ApiRequest('GET',url,"",function(json){
        console.log("Liste des users: " + JSON.stringify(json));
        $("#select-collaborateur").empty();
        $.each(json, function(index, element) {
            $('#select-collaborateur').append(
                '<option idUser="' + element.idUser +'">'+ element.username + '</option>'
            );
        });
    });
}

/* Ouvre un projet */
function openProject(idProject, idCreator){

    // On récupère le dernier commit de la branche master
    var url = "/api/git/" + Cookies.get('idUser') + "/" + idCreator + "/" + idProject + "/listCommit/master";
    ApiRequest('GET',url,"",function(json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Commits',
                message: 'Impossible de récupérer le dernier commit de master',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Dernier commit de master: " + json["commits"][0].id);
            Cookies.set('project', idProject);
            Cookies.set('creator', idCreator);
            Cookies.set('branch', "master");
            Cookies.set('revision', json["commits"][0].id);
            Cookies.set('path', '');
            window.location.href = "/JSP/edit.jsp";
        }
    });
}

/* Ouvre un commit afin de l'observer */
function openCommit(idProject, idCreator, branch, revision) {
    Cookies.set('project', idProject);
    Cookies.set('creator', idCreator);
    Cookies.set('branch', branch);
    Cookies.set('revision', revision);
    window.location.href = "/JSP/viewer.jsp";
}

/* Affiche les informations d'un projets */
function infoProject(idProject){
    var url = "/api/project/" + idProject;
    ApiRequest('GET',url,"",function(json){
            console.log("Info projet: " + JSON.stringify(json));
    });
}

/* Ajoute un collaborateur au projet */
function addCollaborateur(idProject,idUser){
    var url = "/api/permissions?idProject=" + idProject + "&idUser=" + idUser;
    ApiRequest('POST',url,"",function(json){
        console.log("Ajout du collaborateur au projet: " + idProject + " idUser:  " + idUser + " : " + JSON.stringify(json));
        listDeveloppers(idProject);
    });
}

function removeCollaborateur(idProject, idUser){
    var url = "/api/projects/"+ idProject + "/users/" + idUser;
    ApiRequest('DELETE',url,"",function(json){
        console.log("Remove collaborateur au projet: " + idProject + " idUser:  " + idUser + " : " + JSON.stringify(json));
        listDeveloppers(idProject);
    });
}

function deleteProject(idProject,idUser){
    var url = "/api/project/" + idProject + "/" + idUser;
    ApiRequest('DELETE',url,"",function(json){
        console.log("Ajout du collaborateur au projet: " + idProject + " idUser:  " + idUser + " : " + JSON.stringify(json));
        listDeveloppers(idProject);
    });
}
