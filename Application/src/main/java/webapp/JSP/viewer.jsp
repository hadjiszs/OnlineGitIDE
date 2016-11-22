<%--
  Created by IntelliJ IDEA.
  User: thibom
  Date: 22/11/16
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Projet MULTIMIF - Editeur</title>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/2.1.3/js.cookie.js"></script>
        <script src="https://mbraak.github.io/jqTree/tree.jquery.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/css/bootstrap-dialog.min.css" type="text/css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap3-dialog/1.34.7/js/bootstrap-dialog.min.js"></script>

        <link rel="stylesheet" href="https://mbraak.github.io/jqTree/jqtree.css" type="text/css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" type="text/css" href="../ressources/CSS/viewer.css">
        <link href="../ressources/img/favicon.png" rel="icon" type="image/x-icon" />
        <!--[if lt IE 9]>
        <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!-- Code Mirror -->
        <script src="../codeMirror/lib/codemirror.js"></script>
        <link rel="stylesheet" href="../codeMirror/lib/codemirror.css">
        <link rel="stylesheet" href="../codeMirror/theme/night.css">
        <link rel="stylesheet" href="../codeMirror/theme/dracula.css">
        <link rel="stylesheet" href="../codeMirror/theme/solarized.css">
        <link rel="stylesheet" href="../codeMirror/theme/rubyblue.css">
        <script src="../codeMirror/mode/clike/clike.js"></script>

        <!-- Script perso -->
        <script src="../ressources/JS/api.js"></script>
        <script src="../ressources/JS/git.js"></script>
        <script src="../ressources/JS/editAjax.js"></script>

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
                            <li><a href="home.jsp">Home</a></li>
                            <li><a href="edit.jsp">Edition</a></li>
                            <li class="active"><a href="viewer.jsp">Viewer</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a id="deconnexion" href="#">Deconnexion</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <main class="container-fluid">
            <aside class="row">
                <div class="col-lg-10 col-lg-offset-1" id="premiereBarre">
                    <div class="btn-group">
                        <select class="form-control" id="listBranch">
                            <option value="">Branche</option>

                        </select>
                    </div>
                    <div class="btn-group">
                        <select class="form-control" id="listCommit">
                            <option value="">Commit</option>
                        </select>
                    </div>
                    <div class="btn-group">
                        <select class="form-control" id="changerTheme">
                            <option value="dracula">Dracula</option>
                            <option value="night">Night</option>
                            <option value="rubyblue">RubyBlue</option>
                            <option value="solarized">Solarized</option>
                        </select>
                    </div>
                </div>
            </aside>

            <section class="row">
                <div class="col-lg-10 col-lg-offset-1" id="editeurJava">
                    <textarea id="java-code">
                    </textarea>
                </div>
            </section>

            <a id="ancrePanelDroite" aria-label="Panel deroulant">
                <span class="glyphicon glyphicon-chevron-left" id="chevronAncreDroite" aria-hidden="true"></span>
            </a>

            <section id="panelDroite">
                <div id="arborescence"></div>
                <p> BLABLABLA </p>
                <p> BLABLABLA </p>
            </section>

            <a id="ancrePanelGauche" aria-label="Panel deroulant">
                <span class="glyphicon glyphicon-chevron-right" id="chevronAncreGauche" aria-hidden="true"></span>
            </a>

            <section id="panelGauche">
                <h2> Arborescence </h2>
                <div id="arborescenceFichier">
                </div>
            </section>
        </main>
        <script src="../ressources/JS/viewer.js"></script>
    </body>
</html>






