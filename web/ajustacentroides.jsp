<%
    String usuario = (String) session.getAttribute("usuario");
    if (usuario == null) {
        usuario = "";
    }
    String tipouser = (String) session.getAttribute("tipouser");
    if (tipouser == null) {
        tipouser = "";
    }
%>
<!DOCTYPE HTML>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <title>Menu</title>
        <script src="inc2/jquery-3.1.0.min.js"></script>

        <style>

            body {
                background-color: #9CAAB6;
                color: #222;
                font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
                font-weight: 300;
                font-size: 15px;
            }

        </style> 
    </head>
    <body>
<table border="0" align="center" cellpadding="2" cellspacing="3" class="pagina" id="tablita" bgcolor="#CCCCCC">
  <tr> 
    <td align="center" nowrap><font face="Verdana, Arial, Helvetica, sans-serif" size="+1">Aguarde Finalizar</font>
    </td>
  </tr>
  <tr> 
    <td align="center" nowrap><img src="images/processando.gif" align="middle" border="0"></td>
  </tr>
</table> 
    <iframe src="ajustacentroidesorigem.jsp" id="meio" name="meio" width="100%" class="myIframe" frameborder="10" align="middle"></iframe>
    </body>
</html>
