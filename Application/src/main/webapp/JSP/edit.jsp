<%--
  Created by IntelliJ IDEA.
  User: thibom
  Date: 20/10/16
  Time: 06:15
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

        <link rel="stylesheet" type="text/css" href="../ressources/CSS/edit.css">
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
                            <li class="active"><a href="edit.jsp">Edition</a></li>
                            <li><a href="viewer.jsp">Viewer</a></li>

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
                <div class="col-lg-10 col-lg-offset-1" id="barreBtn">
                    <div class="btn-group">
                        <select class="form-control" id="changerTheme">
                            <option value="dracula">Dracula</option>
                            <option value="night">Night</option>
                            <option value="rubyblue">RubyBlue</option>
                            <option value="solarized">Solarized</option>
                        </select>
                    </div>
                    <div class="btn-group">
                        <select class="form-control" id="tauxIndentation">
                            <option value="1">Indentation 1</option>
                            <option value="2">Indentation 2</option>
                            <option value="3">Indentation 3</option>
                            <option value="4">Indentation 4</option>
                            <option value="5">Indentation 5</option>
                        </select>
                    </div>
                    <div class="btn-group">
                        <select class="form-control" id="selectBranch">
                            <option value="">Branche</option>
                      
                        </select>
                    </div>

                    <div class="btn-group">
                        <button type="button" class="btn btn-default" id="btnAutoIndent">Tout Indenter</button>
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Création
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li class="ligneBtnCreer" id="btnCreerBranche" data-toggle="modal" data-target="#fenetreBranche"><a>Créer une Branche</a></li>
                                <li class="ligneBtnCreer" id="btnCreerFichier" data-toggle="modal" data-target="#fenetreFichier"><a>Créer un Fichier</a></li>
                                <li class="ligneBtnCreer" id="btnCreerDossier" data-toggle="modal" data-target="#fenetreDossier"><a>Créer un Dossier</a></li>
                            </ul>
                        </div>
                        <input  type="button" class="btn btn-default" id="btnCommit"  value="Commit" data-toggle="modal" data-target="#fenetreCommit"/>
                        <button type="button" class="btn btn-default" id="btnSave">Sauver</button>
                        <button type="button" class="btn btn-default" id="btnCompiler">Compiler</button>
                    </div>
                </div>
            </aside>

            <section class="row">
                    <div class="col-lg-10 col-lg-offset-1" id="editeurJava">
                        <textarea id="java-code">
import com.demo.util.MyType;
import com.demo.util.MyInterface;

public enum Enum {
VAL1, VAL2, VAL3
}

public class Class<T, V> implements MyInterface {
public static final MyType<T, V> member;

private class InnerClass {
public int zero() {
return 0;
}
}

@Override
public MyType method() {
return member;
}
}
                        </textarea>
                    </div>
            </section>

            <!-- Sortie de compilation -->
            <section class="row">
                <div class="col-lg-10 col-lg-offset-1" id="divCompilation">
                    <div id="contenuCompilation">
                        <p> Sortie du compilateur</p>
                    </div>
                </div>
            </section>
            <!-- Fin compilation -->

            <a id="ancrePanelDroite" aria-label="Panel deroulant">
                <span class="glyphicon glyphicon-chevron-left" id="chevronAncreDroite" aria-hidden="true"></span>
            </a>

            <section id="panelDroite">
                <div id="arborescence"></div>
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

        <!-- Modal Commit -->
        <div id="fenetreCommit" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Ajouter un message pour votre Commit</h4>
                    </div>
                    <div class="modal-body">
                        <form>
                            <input type="text" placeholder="Votre message de commit .." name="messageCommit" id="messageCommit"/>
                            <input  class="btn btn-success" id="envoyerCommit" type="submit" value="Envoyer" />
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </div>
                </div>
                <!-- Fin Modal Content -->
            </div>
        </div>
        <!-- Modal -->
        <div id="fenetreBranche" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Créer une nouvelle branche</h4>
                    </div>
                    <div class="modal-body">
                        <form id="createBranch-modal">
                            <input type="text" placeholder="nom de la branche" name="newBranch" id="nomBranche"/>
                            <input  class="btn btn-success" id="createBranch-button" type="submit" value="Envoyer" />
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </div>
                </div>
                <!-- Fin Modal Content -->
            </div>
        </div>
        <!-- Fin Modal Commit -->
        <div id="fenetreFichier" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Créer un nouveau fichier</h4>
                    </div>
                    <div class="modal-body">
                        <form id="createFile-modal">
                            <input type="text" placeholder="nom de la branche" name="newBranch" id="nomFichier"/>
                            <input  class="btn btn-success" id="createFile-button" type="submit" value="Envoyer" />
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </div>
                </div>
                <!-- Fin Modal Content -->
            </div>
        </div>
        <div id="fenetreDossier" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Créer un nouveau dossier</h4>
                    </div>
                    <div class="modal-body">
                        <form id="createDossier-modal">
                            <input type="text" placeholder="nom de la branche" name="newBranch" id="nomDossier"/>
                            <input  class="btn btn-success" id="createDossier-button" type="submit" value="Envoyer" />
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </div>
                </div>
                <!-- Fin Modal Content -->
            </div>
        </div>
        <script src="../ressources/JS/edit.js"></script>
    </body>
</html>






