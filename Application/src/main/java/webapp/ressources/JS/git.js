/* Liste les branches d'un projet */
function listBranch(idProject,idCreator, idUser){
    var url = "/api/git/"+  idUser + "/" + idCreator + "/" + idProject + "/branches";
    ApiRequest('GET',url,"",function(json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Branches',
                message: 'Impossible de récupérer la liste des branches du projets',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Liste des branch de " + idProject + ": " + JSON.stringify(json));
            $('#selectBranch').empty();

            $.each(json["branches"], function(index, element) {
                $('#selectBranch').append('<option project="'+ idProject+'" creator="'+ idCreator +'">' + element.name.substr(element.name.lastIndexOf('/') + 1) + '</option>');
            });
        }
    });
}

/* Liste des commits */
function listCommit(idProject,idCreator, idUser,branch){
    var url = "/api/git/" +  idUser + "/" + idCreator + "/" + idProject + "/listCommit/" + branch;
    ApiRequest('GET',url,"",function(json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Commits',
                message: 'Impossible de récupérer la liste des commits de la branche',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Liste des commits de " + branch + ": " + JSON.stringify(json));
            $('#listCommit').empty();
            $.each(json["commits"], function(index, element) {
                $('#listCommit').append(
                    '<li class="list-group-item ligneCommit" creator="'+ idCreator + '" project="'+ idProject+'" revision="' + element.id + '" branch="' + branch + '"> \
                        <span > ' + element.id + '</span> \
                        <span> ' + element.date + '</span> \
                        <span> ' + element.message + '</span> \
                        <span> ' + element.user + '</span> \
                        <span> ' + element.email + '</span> \
                      </li>');
            });
        }
    });
}

/* Récupère le contenu d'un fichier */
function getFile(idProject,idCreator, idUser,revision,path, temporary){
    if(temporary == "true"){
        Cookies.set('temporary', "true");
    }else{
        Cookies.set('temporary', "false");
    }

    var url = "/api/git/"+  idUser+ "/"+ idCreator + "/" + idProject + "/" + revision +"?path=" + path;

    ApiRequest('GET',url,"",function(json){
        console.log("Contenu du fichier " + revision + ": " + JSON.stringify(json));
        setEditeur(json["content"]);
        Cookies.set('path',path);
    });
}

/* Récupère l'arborescence du commit courant */
function getArborescence(idProject,idCreator, idUser,revision){
    var url = "/api/git/"+  idUser +"/"+ idCreator + "/" + idProject + "/tree/" + revision+"/true";
    ApiRequest('GET',url,"",function(json){
        console.log("Arborescence de " + revision + ": " + JSON.stringify(json));
        $('#arborescenceFichier').tree({
            data: json.root,
            onCreateLi: function(node, $li) {
                $li.find('.jqtree-title').attr({"path":node.path.replace("root/",""),"revision":revision, "temporary":node.temporary});
            }
        });
        // Handle a click on the edit link
        $('#arborescenceFichier').on('dblclick', function(e) {
                // Get the id from the 'node-id' data property
                alert($(e.target).attr("path"),$(e.target).attr("revision"));

                getFile(idProject,idCreator,idUser,$(e.target).attr("revision"),$(e.target).attr("path"),$(e.target).attr("temporary"))

            }
        );
    });

}

function createFile(idProject,idCreator, idUser,path,branch){
    var url = "/api/git/"+  idUser+ "/"+ idCreator + "/" + idProject + "/create/file/" + branch +"?path=" + path;
    ApiRequest('GET',url,"",function(json){
            console.log("Fichier: " + JSON.stringify(json));
    });
}

function makeCommit(idProject,idCreator, idUser,branch,message){
    var url = "/api/git/"+  idUser+ "/"+ idCreator + "/" + idProject + "/makeCommit/" + branch +"?message=" + message;
    ApiRequest('POST',url,"",function(json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Commit',
                message: 'Impossible de faire le commit',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Commit: " + JSON.stringify(json));
        }
    });

}

function createDir(){

}

function createBranch(branch, idProject, idCreator, idUser){
    var url = "/api/git/"+  idUser +"/"+ idCreator + "/" + idProject + "/create/branch/" + branch;
    ApiRequest('POST',url,"",function(json){
        if(json == null){
            BootstrapDialog.show({
                title: 'Branches',
                message: 'Impossible de créer la branche',
                type: BootstrapDialog.TYPE_DANGER,
                closable: true,
                draggable: true
            });
        }else{
            console.log("Branche crée:  " + JSON.stringify(json));
            BootstrapDialog.show({
                title: 'Branches',
                message: 'La branche ' + branch + 'a été créee.',
                type: BootstrapDialog.TYPE_SUCCESS,
                closable: true,
                draggable: true
            });
        }
    });
}
function changeBranch(idProject, idCreator, idUser, branch){
    var url = "/api/git/"+ idUser +"/" + idCreator + "/" + idProject + "/listCommit/" + branch;
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


function diffCommit(idProject,idCreator,idUser,revision){
    var url = "/api/git/"+ idUser +"/" + idCreator + "/" + idProject + "/showCommit/" + revision;
    ApiRequest('GET',url,"",function(json){
            console.log("Diff commit " + JSON.stringify(json));
    });
}

function getArchive(idProject,idCreator,idUser,branch){
    var url = "/api/git/"+ idUser +"/" + idCreator + "/" + idProject + "/archive/" + branch;
    ApiRequest('GET',url,"",function(json){
        console.log("Get archive " + JSON.stringify(json));
    });
}


