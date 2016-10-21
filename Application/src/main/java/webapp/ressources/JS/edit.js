/**
 * Created by thibom on 20/10/16.
 */

/* Faut true si le panel est visible à l'écran */
var deroulerDroite = false;
var deroulerGauche = false;

var editor = ace.edit("editor");
editor.setTheme("ace/theme/tomorrow_night_blue");
editor.session.setMode("ace/mode/java");
editor.setValue("int a = 2; \nint b = 8;\nif(a < b){\n  System.out.println(\"yes\");\n }\n //On a du code random, mais on peut pas check à chaque fois ce qui est écrit");

$("#ancrePanelDroite").click(deroulerPanelDroite);
$("#ancrePanelGauche").click(deroulerPanelGauche);
$("#style").change(changeTheme);

function deroulerPanelDroite(){

    if(!deroulerDroite){
        $("#panelDroite").animate({
            right: "0px",
        },1000);
        $("#ancrePanelDroite").animate({
            right: "300px",
        },1000);
        deroulerDroite = true;
        $("#chevronAncreDroite").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }
    else{
        $("#panelDroite").animate({
            right: "-300px",
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
            left: "300px",
        },1000);
        deroulerGauche = true;
        $("#chevronAncreGauche").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }
    else{
        $("#panelGauche").animate({
            left: "-300px",
        },1000);
        $("#ancrePanelGauche").animate({
            left: "0px",
        },1000);
        deroulerGauche = false;
        $("#chevronAncreGauche").toggleClass('glyphicon-chevron-left glyphicon-chevron-right');
    }

}

function changeTheme() {
    var e = document.getElementById("style");
    var theme = e.options[e.selectedIndex].text;
    ace.edit("editor").setTheme("ace/theme/" + theme);
}


