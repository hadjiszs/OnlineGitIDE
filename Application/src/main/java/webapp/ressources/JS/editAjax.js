$(document).ready(function() {
    refreshPage();
    $("#selectBranch").on("change",function(e){
        e.preventDefault();
        var idProject = $('#selectBranch option:selected').attr("project");
        var idCreator = $('#selectBranch option:selected').attr("creator");
        var branch = $('#selectBranch option:selected').text();
        var idUser = Cookies.get('idUser');
        changeBranch(idProject, idCreator, idUser, branch);
    });

    //Créer un nouvelle branche
    $("#createBranch-button").on("click", function(e){
        e.preventDefault();
        var idCreator = Cookies.get('creator');
        var idUser = Cookies.get('idUser');
        var idProject = Cookies.get('project');
        createBranch($("#nomBranche").val(), idProject, idCreator, idUser);
    });


});

/* Actualise la page */
function refreshPage(){
    getArborescence(Cookies.get('project'),Cookies.get('revision'));
    listBranch(Cookies.get('project'));
}

