<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!-- Page d'accueil du site internet -->
<html>
<head>
    <title> Multimif Groupe 1 - Bienvenue !</title>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="ressources/CSS/connexion.css">
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/2.1.3/js.cookie.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <script src="ressources/JS/api.js"></script>
    <script src="ressources/JS/index.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="ressources/img/favicon.ico" rel="icon" type="image/x-icon" />
</head>

<body class="bodyIndex">
    <main>
        <h1 id="titreIndex">Bienvenue sur le projet MULTIMIF - Groupe 1</h1>
        <div class="container">
            <div class="row">
                <section class="col-lg-6 col-xs-6" id="divConnexion">
                    <header>
                        <h2 class="titreForm">Vous souhaitez accéder à vos Projets ? Connectez-vous !</h2>
                    </header>
                    <form action="" name="formConnexion" class="form-horizontal formIndex" method="post">
                        <div class="form-group">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input name="username" placeholder="Idenfiant" class="form-control" type="text" id="UserUsername" required />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input name="password" placeholder="Mot de passe" class="form-control" type="password" id="UserPassword" required />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input  class="btn btn-success" id="btnConnexion" type="submit" value="Connexion" />
                            </div>
                        </div>

                    </form>

                    <!-- Lance le modal -->
                    <a data-toggle="modal" data-target="#oubliePassword" id="aOubliePassword">Mot de passe Oublié ?</a>

                    <!-- Modal -->
                    <div id="oubliePassword" class="modal fade" role="dialog">
                        <div class="modal-dialog">

                            <!-- Modal content-->
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title">Besoin d'un nouveau mot de passe ?</h4>
                                </div>
                                <div class="modal-body">
                                    <form action="oubliePassword.jsp" method="post">
                                        <input type="email" placeholder="Adresse mail" name="mailPassword" id="mailPassword" required />
                                        <input  class="btn btn-success" id="btnPassword" type="submit" value="Envoyer" />
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
                </section>

                <section class="col-lg-6 col-xs-6" id="divInscription">
                    <header>
                        <h2 class="titreForm">Vous n'avez pas de compte Multimif ? Inscrivez-vous !</h2>
                    </header>

                    <form action="" name="formInscription" class="form-horizontal formIndex" method="get">
                        <div class="form-group">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input name="username" placeholder="Nom" class="form-control" type="text" id="nomInscription"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input name="mail" placeholder="Adresse mail" class="form-control" type="email" id="emailInscription" required />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input name="password" placeholder="Mot de passe" class="form-control" type="password" id="passwordInscription" required />
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input  class="btn btn-success" id="btnInscription" type="submit" value="Inscription"/>
                            </div>
                        </div>
                    </form>
                </section>
            </div>
        </div>
        <small>
            Projet MULTIMIF - Groupe 1.
        </small>
    </main>
</body>
</html>

