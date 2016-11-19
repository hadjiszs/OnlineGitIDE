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
        <title>Projet MULTIMIF - Edition</title>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/2.1.3/js.cookie.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="../ressources/CSS/edit.css">
        <link href="../ressources/img/favicon.ico" rel="icon" type="image/x-icon" />
        <!--[if lt IE 9]>
        <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <script src="../codeMirror/lib/codemirror.js"></script>
        <link rel="stylesheet" href="../codeMirror/lib/codemirror.css">
        <link rel="stylesheet" href="../../codeMirror/theme/night.css">
        <script src="../codeMirror/mode/clike/clike.js"></script>
        <link rel="stylesheet" href="../../codeMirror/theme/rubyblue.css">
        <script src="../../codeMirror/mode/clike/clike.js"></script>

        <!-- Test -->
        <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jstree/3.3.3/themes/default/style.min.css" />
        <script src="//cdnjs.cloudflare.com/ajax/libs/jstree/3.3.3/jstree.min.js"></script>

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
                            <li><a href="#">Projet</a></li>
                            <li class="active"><a href="edit.jsp">Edition</a></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#">Deconnexion</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <main class="container-fluid">
            <aside class="row">
                <div class="col-lg-8 col-lg-offset-2" id="premiereBarre">
                    <div class="btn-group">
                        <select class="form-control" id="changerTheme">
                            <option value="dracula">Dracula</option>
                            <option value="night">Night</option>
                            <option value="rubyblue">RubyBlue</option>
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
                        <button type="button" class="btn btn-default" id="autoIndent">Tout Indenter</button>
                        <button type="button" class="btn btn-default" id="TODO">TODO</button>
                        <button type="button" class="btn btn-default" id="TODO">TODO</button>
                        <button type="button" class="btn btn-default" id="TODO">TODO</button>
                        <button type="button" class="btn btn-default" id="TODO">TODO</button>
                        <button type="button" class="btn btn-default" id="TODO">TODO</button>
                        <button type="button" class="btn btn-default" id="TODO">TODO</button>
                        <input  type="button" class="btn btn-default" id="commit"  value="Commit" data-toggle="modal" data-target="#fenetreCommit"/>
                    </div>
                </div>
            </aside>

            <section class="row">
                    <div class="col-lg-8 col-lg-offset-2" id="editeurJava">
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
                <div class="col-lg-8 col-lg-offset-2">
                        <p> Rien pour le moment</p>
                </div>
            </section>

            <a id="ancrePanelDroite" aria-label="Panel deroulant">
                <span class="glyphicon glyphicon-chevron-left" id="chevronAncreDroite" aria-hidden="true"></span>
            </a>

            <section id="panelDroite">
                <p> BLABLABLA </p>
                <p> BLABLABLA </p>
                <p> BLABLABLA </p>
            </section>

            <a id="ancrePanelGauche" aria-label="Panel deroulant">
                <span class="glyphicon glyphicon-chevron-right" id="chevronAncreGauche" aria-hidden="true"></span>
            </a>

            <section id="panelGauche">
                    <h2> Arborescence </h2>
                    <div id="arborescenceFichier">
                        <ul>
                            <li>Root node
                                <ul>
                                    <li>Child node 1</li>
                                    <li>Child node 2</li>
                                </ul>
                            </li>
                        </ul>
                    </div>
            </section>
        </main>

        <!-- Modal -->
        <div id="fenetreCommit" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Ajouter un message pour votre Commit</h4>
                    </div>
                    <div class="modal-body">
                        <form action="fenetreCommit.jsp" method="post">
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
        <!-- Fin Modal -->
        <script src="../JS/edit.js"></script>
    </body>
</html>






