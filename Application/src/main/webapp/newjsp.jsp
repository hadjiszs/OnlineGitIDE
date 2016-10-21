<%-- 
    Document   : newjsp
    Created on : 19 oct. 2016, 10:25:35
    Author     : Anthony
--%>
        
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>Editor</title>
  <style type="text/css" media="screen">
    body {
        overflow: hidden;
    }
  </style>
</head>
<body>

<div style="margin-left : 20%;margin-right : 20%;margin-top: 50px;">
    <select id="style" onchange="changeTheme()" class="selectpicker" data-style="btn-primary">
      <option value="1">tomorrow_night_bright</option>
      <option value="2" selected="selected">tomorrow_night_blue</option>
      <option value="3">solarized_light</option>
    </select>
    <pre id="editor" style="  resize: both;overflow: auto;  height: 900px;width:100%;">
        int a = 2;
        int b = 8;
        if(a < b){
            System.out.println("yes");
        }   
    //On a du code random, mais on peut pas check � chaque fois ce qui est �crit    
    </pre>
</div>
<script src="ace-builds-master/src-min-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
<script>
    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/tomorrow_night_blue");
    editor.session.setMode("ace/mode/java");
</script>

<script>
function changeTheme() { 
   var e = document.getElementById("style");
   var theme = e.options[e.selectedIndex].text;
   ace.edit("editor").setTheme("ace/theme/" + theme);
}
</script>

</body>
</html>
        
        
        
        