/**
 * Created by thibom on 20/10/16.
 */

/* Vaut true si le panel est visible à l'écran */
var deroulerDroite = false;
var deroulerGauche = false;
var inputChangerTheme = document.getElementById("changerTheme");
var inputChangerIndentation = document.getElementById("tauxIndentation");

$("#ancrePanelDroite").click(deroulerPanelDroite);
$("#ancrePanelGauche").click(deroulerPanelGauche);
$("#changerTheme").change(changerTheme);
$("#tauxIndentation").change(changerIndentation);
$("#autoIndent").click(indenterEditeur);

function deroulerPanelDroite() {
    if (!deroulerDroite) {
        $("#panelDroite").animate({
            right: "0px",
        }, 1000);
        $("#ancrePanelDroite").animate({
            right: "20%",
        }, 1000);
        deroulerDroite = true;
        $("#chevronAncreDroite").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }
    else {
        $("#panelDroite").animate({
            right: "-20%",
        }, 1000);
        $("#ancrePanelDroite").animate({
            right: "0px",
        }, 1000);
        deroulerDroite = false;
        $("#chevronAncreDroite").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }

}

function deroulerPanelGauche() {

    if (!deroulerGauche) {
        $("#panelGauche").animate({
            left: "0px",
        }, 1000);
        $("#ancrePanelGauche").animate({
            left: "20%",
        }, 1000);
        deroulerGauche = true;
        $("#chevronAncreGauche").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }
    else {
        $("#panelGauche").animate({
            left: "-20%",
        }, 1000);
        $("#ancrePanelGauche").animate({
            left: "0px",
        }, 1000);
        deroulerGauche = false;
        $("#chevronAncreGauche").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }

}

var txt =  document.getElementById("java-code");
var editeur = CodeMirror.fromTextArea(txt, {
    lineNumbers: true,
    matchBrackets: true,
    mode: "text/x-java",
    theme: inputChangerTheme.options[inputChangerTheme.selectedIndex].value,
    viewportMargin: Infinity,
    indentUnit: parseInt(inputChangerIndentation.options[inputChangerIndentation.selectedIndex].value),

});

var mac = CodeMirror.keyMap.default === CodeMirror.keyMap.macDefault;
CodeMirror.keyMap.default[(mac ? "Cmd" : "Ctrl") + "-Space"] = "autocomplete";

function changerTheme(){
    editeur.setOption("theme", inputChangerTheme.options[inputChangerTheme.selectedIndex].value);
}

/* Enregistrement automatique de l'éditeur toutes les 30 secondes */
window.setInterval(function(){
    //TODO
    editeur.getDoc().getValue();
   // console.log(editeur.getDoc().getValue());//Permet d'avoir le contenu de l'éditeur
}, 30000);



$("#TODO").click(setEditeur);

function setEditeur(){
    editeur.setValue("COUCOU");
}

function indenterEditeur(){
    var nbLigne = editeur.lineCount();
    for (i=0;i<nbLigne;i++){
        editeur.indentLine(i);
    }

}

function changerIndentation(){
    editeur.setOption("indentUnit", parseInt(inputChangerIndentation.options[inputChangerIndentation.selectedIndex].value));
    indenterEditeur();
}