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
        <script src="../ressources/JS/api.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="../ressources/CSS/home.css">
        <!--[if lt IE 9]>
        <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <script async src="../ressources/JS/home.js"></script>
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
                            <li><a href="#">Projet</a></li>
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
                        <button class="btn btn-success" id="ajouterProjet"> Créer un Projet</button>
                    </header>
                    <div class="list-group col-lg-8" id="listeProjets">
                        <a href="#" class="list-group-item">Projet 1</a>
                        <a href="#" class="list-group-item active">Projet 2</a>
                        <a href="#" class="list-group-item">Projet 3</a>
                        <a href="#" class="list-group-item">Projet 4</a>
                        <a href="#" class="list-group-item">Projet 5 </a>
                    </div>
                </section>
                <section class="col-lg-12" id="divCollaboration">
                    <header class="titreSection">
                        <h2> Mes Collaborations </h2>
                    </header>
                    <div class="list-group col-lg-8" id="listeCollaborations">
                        <a href="#" class="list-group-item">Collaboration 1</a>
                        <a href="#" class="list-group-item ">Collaboration 2</a>
                        <a href="#" class="list-group-item">Collaboration 3</a>
                        <a href="#" class="list-group-item">Collaboration 4</a>
                        <a href="#" class="list-group-item">Collaboration 5 </a>
                    </div>
                </section>
            </div>
            <section class="col-lg-7 col-md-12" id="homeDroite">
                <aside class="col-lg-12">
                    <form class="navbar-form navbar-left" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search ..">
                        </div>
                        <button type="submit" class="btn btn-default">Go !</button>
                    </form>
                </aside>
                <header class="col-lg-12">
                    <h2> Titre du Tableau </h2>
                </header>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Nom</th>
                            <th>Action</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>John Boo</td>
                            <td>commit</td>
                            <td>15 Sep, 8:56 AM</td>
                        </tr>
                        <tr>
                            <td>Michael Robinson</td>
                            <td>commit</td>
                            <td>15 Sep, 7:12 AM</td>
                        </tr>
                        <tr>
                            <td>Michael Robinson</td>
                            <td>new branch</td>
                            <td>15 Sep, 7:12 AM</td>
                        </tr>
                        <tr>
                            <td>John Boo</td>
                            <td>merge</td>
                            <td>15 Sep, 2:08 AM</td>
                        </tr>
                    </tbody>
                </table>
            </section>
            <a id="ancrePanel" aria-label="Panel deroulant"><span class="glyphicon glyphicon-chevron-left" id="chevronAncre" aria-hidden="true"></span></a>
            <section id="panelDeroulant">
                <p> BLABLABLA </p>
                <p> BLABLABLA </p>
            </section>
        </main>
    </body>
</html>
