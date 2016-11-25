<%--
  Created by IntelliJ IDEA.
  User: thibom
  Date: 17/10/16
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Projet MULTIMIF - Home</title>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/2.1.3/js.cookie.js"></script>
        <script src="https://mbraak.github.io/jqTree/tree.jquery.js"></script>
        <link rel="stylesheet" href="https://mbraak.github.io/jqTree/jqtree.css" type="text/css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/css/bootstrap-dialog.min.css" type="text/css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/js/bootstrap-dialog.min.js"></script>

        <!-- Script perso -->
        <script src="../ressources/JS/api.js"></script>
        <script src="../ressources/JS/git.js"></script>


        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="../ressources/CSS/home.css">
        <!--[if lt IE 9]>
        <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <link href="../ressources/img/favicon.png" rel="icon" type="image/x-icon" />
    </head>
    <body>
        <header>
            <nav class="navbar navbar-default navbar-fixed-top navbar-inverse">
                <div class="container-fluid">
                    <!-- Pour navigation mobile -->
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>

                    <!-- Pour navigation ordinateur -->
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="home.jsp">Home</a></li>
                            <li><a href="edit.jsp">Edition</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a id="deconnexion" href="#">Deconnexion</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>

        <main class="container">
            <div class="col-lg-5 col-md-12" id="homeGauche">
                <section class="col-lg-12" id="divProjets">
                    <header class="titreSection">
                        <h2 class="h2DivProjet"> Mes Projets </h2>
                        <button data-toggle="modal" data-target="#creerProjet" class="btn btn-success" id="ajouterProjet"> Créer un Projet</button>
                    </header>
                    <div class="col-lg-12" id="listeProjets">
                        <div class="btn-group col-lg-12 ligneListeProjet">
                            <button type="button" class="btn btn-default nomListeProjets">Projet 1</button>
                            <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-remove spanSupprimerProjet"></span></button>
                        </div>
                        <div class="btn-group col-lg-12 ligneListeProjet">
                            <button type="button" class="btn btn-default nomListeProjets">Projet 2</button>
                            <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-remove spanSupprimerProjet"></span></button>
                        </div>
                        <div class="btn-group col-lg-12 ligneListeProjet">
                            <button type="button" class="btn btn-default nomListeProjets">Projet 3</button>
                            <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-remove spanSupprimerProjet"></span></button>
                        </div>
                    </div>
                </section>
                <section class="col-lg-12" id="divCollaboration">
                    <header class="titreSection">
                        <h2 id="h2DivCollaboration"> Mes Collaborations </h2>
                    </header>
                    <div class="btn-group col-lg-12" id="listeCollaborations">
                        <div class="btn-group col-lg-12 ligneListeCollaborations">
                            <button type="button" class="btn btn-default nomListeCollaborations">Collaboration 1</button>
                            <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-remove spanSupprimerProjet"></span></button>
                        </div>
                        <div class="btn-group col-lg-12 ligneListeCollaborations">
                            <button type="button" class="btn btn-default nomListeCollaborations">Collaboration 2</button>
                            <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-remove spanSupprimerProjet"></span></button>
                        </div>
                        <div class="btn-group col-lg-12 ligneListeCollaborations">
                            <button type="button" class="btn btn-default nomListeCollaborations">Collaboration 3</button>
                            <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-remove spanSupprimerProjet"></span></button>
                        </div>
                    </div>
                </section>
            </div>
            <section class="col-lg-7 col-md-12" id="homeDroite">
                <header class="col-lg-12 titreSection">
                    <h2 id="h2DivCommit"> Nom du Projet </h2>
                    <button data-toggle="modal" data-target="#ajoutCollaborateur" class="btn btn-success" id="btnAjouterCollaborateur"> Ajouter un Collaborateur</button>
                </header>
                <div class="col-lg-8" id="divCommit">
                    <div class="col-lg-12" id="divSelectCommit">
                        <select class="form-group form-control" id="selectBranch">
                            <option></option>
                        </select>
                    </div>
                    <div class="col-lg-12" id="divAfficherCommit">
                        <ul class="list-group" id="listCommit">
                            <li class="list-group-item ligneCommit">Commit 1</li>
                            <li class="list-group-item ligneCommit">Commit 2</li>
                            <li class="list-group-item ligneCommit">Commit 3</li>
                            <li class="list-group-item ligneCommit">Commit 4</li>
                            <li class="list-group-item ligneCommit">Commit 5</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-4" id="divListeDeveloppeur">
                    <header>
                        <h4> Ils sont sur le Projet</h4>
                    </header>
                    <div>
                        <ul class="list-group" id="listDev">
                            <li class="list-group-item">Nom 1</li>
                            <li class="list-group-item">Nom 2</li>
                            <li class="list-group-item">Nom 3</li>
                            <li class="list-group-item">Nom 4</li>
                            <li class="list-group-item">Nom 5</li>
                        </ul>
                    </div>
                </div>
            </section>
            <a id="ancrePanel" aria-label="Panel deroulant"><span class="glyphicon glyphicon-chevron-left" id="chevronAncre" aria-hidden="true"></span></a>
            <section id="panelDeroulant">
                <p> BLABLABLA </p>
                <p> BLABLABLA </p>
            </section>
        </main>

        <!-- Modal Creation de projet -->
        <div id="creerProjet" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Parametre du projet </h4>
                    </div>
                    <div class="modal-body">
                        <form id="formProjet">
                            <label class="labelProjet"> Nom du projet </label>
                            <input type="text" placeholder="Nom du projet" name="name" id="nomProjet" required />
                            <label class="labelProjet"> Langage </label>
                            <select name="type" class="form-group form-control" id="selectProjet">
                                <option value="JAVA"> JAVA </option>
                                <option value="C++"> C++ </option>
                                <option value="python"> Python </option>
                            </select>
                            <input  class="btn btn-success" id="btnProjet" type="submit" value="Envoyer" />
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </div>
                </div>
                <!-- Fin Modal Content -->

            </div>
        </div>
        <!-- Fin Modal Creation de Projet -->

        <!-- Modal Ajout de Collaborateur -->
        <div id="ajoutCollaborateur" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Ajouter un Collaborateur </h4>
                    </div>
                    <div class="modal-body">
                        <formid="formAjoutCollaborateur">
                            <select id="select-collaborateur"></select>
                            <input  class="btn btn-success" id="btnAjoutCollaborateur" type="submit" value="Ajouter" />
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </div>
                </div>
                <!-- Fin Modal Content -->

            </div>
        </div>
        <!-- Fin Modal Ajout de Collaborateur -->

        <script src="../ressources/JS/home.js"></script>
    </body>
</html>
