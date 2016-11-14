/**
 * Created by thibom on 20/10/16.
 */

/* Vaut true si le panel est visible à l'écran */
var deroulerDroite = false;
var deroulerGauche = false;
var input = document.getElementById("changerTheme");

$("#ancrePanelDroite").click(deroulerPanelDroite);
$("#ancrePanelGauche").click(deroulerPanelGauche);
$("#changerTheme").change(changerTheme);

function deroulerPanelDroite(){
    if(!deroulerDroite){
        $("#panelDroite").animate({
            right: "0px",
        },1000);
        $("#ancrePanelDroite").animate({
            right: "20%",
        },1000);
        deroulerDroite = true;
        $("#chevronAncreDroite").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }
    else{
        $("#panelDroite").animate({
            right: "-20%",
        },1000);
        $("#ancrePanelDroite").animate({
            right: "0px",
        },1000);
        deroulerDroite = false;
        $("#chevronAncreDroite").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }

}

function deroulerPanelGauche(){

    if(!deroulerGauche){
        $("#panelGauche").animate({
            left: "0px",
        },1000);
        $("#ancrePanelGauche").animate({
            left: "20%",
        },1000);
        deroulerGauche = true;
        $("#chevronAncreGauche").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }
    else{
        $("#panelGauche").animate({
            left: "-20%",
        },1000);
        $("#ancrePanelGauche").animate({
            left: "0px",
        },1000);
        deroulerGauche = false;
        $("#chevronAncreGauche").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }

}

var txt =  document.getElementById("java-code");
var editeur = CodeMirror.fromTextArea(txt, {
    lineNumbers: true,
    matchBrackets: true,
    mode: "text/x-java",
    theme: input.options[input.selectedIndex].value,
    viewportMargin: Infinity,

});

var mac = CodeMirror.keyMap.default === CodeMirror.keyMap.macDefault;
CodeMirror.keyMap.default[(mac ? "Cmd" : "Ctrl") + "-Space"] = "autocomplete";

function changerTheme(){
    editeur.setOption("theme", input.options[input.selectedIndex].value);
}

/* Enregistrement automatique de l'éditeur toutes les 30 secondes */
window.setInterval(function(){
    //TODO
}, 30000);

editeur.getDoc().getValue(); //Permet d'avoir le contenu de l'éditeur


