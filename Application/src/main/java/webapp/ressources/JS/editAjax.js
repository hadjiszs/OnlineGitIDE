$(document).ready(function() {
    refreshPage();
    $("#selectBranch").on("change",function(e){
        e.preventDefault();
        var idProject = $('#selectBranch option:selected').val();
        var branch = $('#selectBranch option:selected').text();
        changeBranch(idProject,branch);
    });

    //Créer un nouvelle branche
    $("#createBranch-button").on("click", function(e){
        e.preventDefault();
        createBranch($("#nomBranche").val());
    });


});

/* Actualise la page */
function refreshPage(){
    getArborescence(Cookies.get('project'),Cookies.get('revision'));
    listBranch(Cookies.get('project'));
}

function changeBranch(idProject,branch){
    var url = "/api/git/"+ Cookies.get('idUser') + "/" + idProject + "/listCommit/" + branch;
    ApiRequest('GET',url,"",function(json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Commits',
                message: 'Impossible de récupérer le dernier commit de ' + branch,
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Dernier commit de "+branch+ ": " + json["commits"][0].id);
            Cookies.set('project', idProject);
            Cookies.set('branch', branch);
            Cookies.set('revision', json["commits"][0].id);
            refreshPage();
        }
    });
}