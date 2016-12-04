/**
 * Created by thibom on 22/11/16.
 */

/* Vaut true si le panel est visible à l'écran */
var deroulerGauche = false;
var inputChangerTheme = document.getElementById("changerTheme");

$("#ancrePanelGauche").click(deroulerPanelGauche);
$("#changerTheme").change(changerTheme);

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
    indentUnit: 3,
    readOnly: "nocursor",
});

var mac = CodeMirror.keyMap.default === CodeMirror.keyMap.macDefault;
CodeMirror.keyMap.default[(mac ? "Cmd" : "Ctrl") + "-Space"] = "autocomplete";

function changerTheme(){
    editeur.setOption("theme", inputChangerTheme.options[inputChangerTheme.selectedIndex].value);
}