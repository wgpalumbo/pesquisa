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

        <script type="text/javascript">
            $(document).ready(function () {

                $('#nav li').hover(
                        function () {
                            //show its submenu
                            $('ul', this).stop(true, true).slideDown(100);

                        },
                        function () {
                            //hide its submenu
                            $('ul', this).stop(true, true).slideUp(100);
                        }
                );
                $('.myIframe').css('height', $(window).height() + 'px');
                $("#loader").css("display", "none");


            });
        //----------
            function menuM(x) {
                if (x === 0) {
                    $('#meio').attr("src", "meio.html");
                }
                if (x === 1) {
                    $('#meio').attr("src", "motivosmodos.jsp?ismaior=0");
                }
                if (x === 2) {
                    $('#meio').attr("src", "motivosmodos.jsp?ismaior=1");
                }
                if (x === 3) {
                    $('#meio').attr("src", "fxrendamotivos.jsp");
                }
                if (x === 4) {
                    $('#meio').attr("src", "fxrendamodos.jsp");
                }
                if (x === 5) {
                    $('#meio').attr("src", "escolaridademodos.jsp");
                }
                if (x === 6) {
                    $('#meio').attr("src", "idademodos.jsp");
                }
                if (x === 7) {
                    $('#meio').attr("src", "origemdestino.jsp");
                }
                if (x === 8) {
                    $('#meio').attr("src", "motivosmodospr.jsp?ismaior=0");
                }
                if (x === 9) {
                    $('#meio').attr("src", "motivosmodospr.jsp?ismaior=1");
                }
                if (x === 11) {
                    $('#meio').attr("src", "fxrendamodospr.jsp");
                }
                if (x === 12) {
                    $('#meio').attr("src", "escolaridademodospr.jsp");
                }
                if (x === 13) {
                    $('#meio').attr("src", "idademodospr.jsp");
                }
                if (x === 14) {
                    $('#meio').attr("src", "sinteseporzona.jsp");
                }
                if (x === 15) {
                    $('#meio').attr("src", "idademobilidadepr.jsp");
                }
                if (x === 16) {
                    $('#meio').attr("src", "escolaridademobilidadepr.jsp");
                }
                if (x === 17) {
                    $('#meio').attr("src", "modosfxrendpr.jsp");
                }
                if (x === 18) {
                    $('#meio').attr("src", "modosfxrendmobilidadepr.jsp");
                }
                if (x === 20) {
                    $('#meio').attr("src", "matrizporzona.jsp");
                }
                if (x === 21) {
                    $('#meio').attr("src", "matrizpico2010.jsp");
                }
                if (x === 22) {
                    $('#meio').attr("src", "matrizpico2010.jsp?indice=1.1397");
                }
                if (x === 23) {
                    $('#meio').attr("src", "matrizpico2010.jsp?indice=1.2845");
                }
                if (x === 24) {
                    $('#meio').attr("src", "matrizporzonacoef.jsp?indice=1.1397&ano=2019");
                }
                if (x === 25) {
                    $('#meio').attr("src", "motivosmodosporzona.jsp");
                }
                if (x === 26) {
                    $('#meio').attr("src", "matrizporzonacoef.jsp?indice=1.2845&ano=2036");
                }
                if (x === 27) {
                    $('#meio').attr("src", "listamotivomodos.jsp");
                }
                if (x === 28) {
                    $('#meio').attr("src", "listarcentroides.jsp");
                }
                if (x === 29) {
                    $('#meio').attr("src", "cruzardomiccentro.jsp");
                }
                if (x === 30) {
                    $('#meio').attr("src", "motivosmodosprzona.jsp");
                }
                if (x === 31) {
                    $('#meio').attr("src", "ajustamenu.jsp");
                }
                if (x === 32) {
                    $('#meio').attr("src", "ajustamorador.jsp");
                }
                if (x === 33) {
                    $('#meio').attr("src", "ajustadomicilio.jsp");
                }
                if (x === 34) {
                    $('#meio').attr("src", "ajustacentroides.jsp");
                }
                if (x === 35) {
                    $('#meio').attr("src", "listadomicilios.jsp");
                }
                if (x === 36) {
                    $('#meio').attr("src", "mobilidadesimples.jsp");
                }
                if (x === 37) {
                    $('#meio').attr("src", "atribuifatorviagens.jsp");
                }
                if (x === 38) {
                    $('#meio').attr("src", "centroidesviagens.jsp");
                }
                if (x === 39) {
                    $('#meio').attr("src", "relarquivosxls.jsp");
                }
                if (x === 40) {
                    $('#meio').attr("src", "sexomobilidadepr.jsp");
                }
                if (x === 41) {
                    $('#meio').attr("src", "faixahorariamodopr.jsp");
                }
                if (x === 42) {
                    $('#meio').attr("src", "rendaindividualmodopr.jsp");
                }
               return;
            }
        //----------
        </script>
        <style>

            body {
                background-color: #EBE8E4;
                color: #222;
                font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
                font-weight: 300;
                font-size: 15px;
            }
            .submenu {
                text-decoration: blink; 
                display: block;
                background-color: #EBE8E4;
                color: #222;
                font-family: Arial;
                font-weight: 300;
                font-size: 13px;
                padding:8px 5px 0 5px; 
            }

            /* remove the list style */
            #nav {
                margin:0; 
                padding:0; 
                list-style:none;
                background-color: #fff;
                border: 1px solid #dedede;
                border-radius: 4px;
                box-shadow: 0 2px 2px -1px rgba(0, 0, 0, 0.055);
                color: #888;
                display: block;
                margin: 8px 22px 8px 22px;
            }	

            /* make the LI display inline */
            /* it's position relative so that position absolute */
            /* can be used in submenu */
            #nav li {
                float:left; 
                display:block; 
                width:200px; 
                background:  #fff; 
                position:relative;
                z-index:500; 
                margin:0 1px;
                text-align:center; 
            }

            /* this is the parent menu */
            #nav li a {
                display:block; 
                padding:8px 5px 0 5px; 
                font-weight:700;  
                height:23px; 
                text-decoration:none; 
                color:#fff; 
                text-align:center; 
                color:#333;
            }

            #nav li a:hover {
                color: #06F;
                text-decoration:none; 
                background: #CCC; 
            }

            /* you can make a different style for default selected value */
            #nav a.selected {
                color:#f00;
            }

            /* submenu, it's hidden by default */
            #nav ul {
                position:absolute; 
                left:0; 
                display:none; 
                margin:0 0 0 -1px; 
                padding:0; 
                list-style:none;
            }

            #nav ul li {
                width:200px; 
                float:left; 
                border-top:1px solid #CCC;
                transition: all 0.2s; 
            }

            /* display block will make the link fill the whole area of LI */
            #nav ul a {
                display:block;  
                height:15px;
                padding: 8px 5px; 
                color:#666;
            }

            #nav ul a:hover {
                text-decoration: none;	 
                background: #CCC; 
            }

            /* fix ie6 small issue */
            /* we should always avoid using hack like this */
            /* should put it into separate file : ) */
            #html #nav ul {
                margin:0 0 0 -2px;
            }

            .who2{
                font-size:12px;
                /*width: 270px;*/
                float: left;
                margin-left: 9px;
                display: inline;
                background: #CCC;
                padding: 2px;
                text-align: right;
                text-decoration : none;
                border-radius: 2px;
                color: black;
            }
            .who{
                font-size:10px;
                /*width: 270px;*/
                float: left;
                margin-left: 9px;
                display: inline;
                background: #CCC;
                padding: 2px;
                text-align: right;
                text-decoration : none;
                border-radius: 2px;
                color: blue;
            }

        </style> 
    </head>
    <body>
        <input name="usuario" id="usuario" type="hidden" value="<%= usuario%>" READONLY>
        <input name="tipouser" id="tipouser" type="hidden" value="<%= tipouser%>" READONLY>
        <ul id="nav">
            <li><a href="javascript:menuM(0);" onclick="menuM(0);"  target="meio">Relat&oacute;rios</a>
                <ul>
                    <li><font class="submenu"> MODOS INDIVIDUAIS </font></li>
                    <li><a href="javascript:;" onclick="menuM(25);" target="meio">Motivos x Modos</a></li>
                    <li><font class="submenu"> MODOS PRINCIPAIS </font></li>
                    <li><a href="javascript:;" onclick="menuM(30);" target="meio">Motivos x Modos-PR</a></li>
                    <li><a href="javascript:;" onclick="menuM(41);" target="meio">Fx.Hora x Modos-PR</a></li>
                    <li><a href="javascript:;" onclick="menuM(15);" target="meio">Idade x Modos-PR</a></li>
                    <li><a href="javascript:;" onclick="menuM(16);" target="meio">Escolaridade-Modos-PR</a></li>
                    <li><a href="javascript:;" onclick="menuM(18);" target="meio">Renda x Modos-PR</a></li>
                    <li><a href="javascript:;" onclick="menuM(42);" target="meio">Renda Indiv. x Modos-PR</a></li>
                    <li><a href="javascript:;" onclick="menuM(40);" target="meio">Sexo x Modos-PR</a></li>
                   <li><font class="submenu"> MOBILIDADE </font></li>
                    <li><a href="javascript:;" onclick="menuM(36);" target="meio">Mobilidade-Simples</a></li>
                    <li><font class="submenu"> MATRIZ ORIGEM DESTINO </font></li>
                    <li><a href="javascript:;" onclick="menuM(7);" target="meio">Origem x Destino</a></li>
                    <li><font class="submenu"> ARQUIVOS EXCEL</font></li>
                   	<li><a href="javascript:;" onclick="menuM(39);" target="meio">Listar Arquivos</a></li>
                </ul>
            </li>
          <li><a href="javascript:;" onclick="menuM(0);"  target="meio">Listagens</a>
                <ul>
                    <li><a href="javascript:;" onclick="menuM(20);" target="meio">Matriz Zona</a></li>
                    <li><a href="javascript:;" onclick="menuM(21);" target="meio">Matriz Pico 2010</a></li>
                    <li><a href="javascript:;" onclick="menuM(22);" target="meio">Matriz Pico 2019</a></li>
                    <li><a href="javascript:;" onclick="menuM(23);" target="meio">Matriz Pico 2036</a></li>
                    <li>&nbsp;</li>
                    <li><a href="javascript:;" onclick="menuM(27);" target="meio">Listar Mot.x Modos</a></li>
                    <li>&nbsp;</li>
                    <li><a href="javascript:;" onclick="menuM(28);" target="meio">Listar Centroides</a></li>
                    <li><a href="javascript:;" onclick="menuM(29);" target="meio">Cruzar Domic. x Centr.</a></li>
                    <li><a href="javascript:;" onclick="menuM(35);" target="meio">Listar Domicilios</a></li>
                    <li><a href="javascript:;" onclick="menuM(38);" target="meio">Centroides x Viagens</a></li>
                     <li>&nbsp;</li>
                   	<li><a href="javascript:;" onclick="menuM(24);" target="meio">Fator M.Zona x 2019</a></li>
                    <li><a href="javascript:;" onclick="menuM(26);" target="meio">Fator M.Zona x 2036</a></li>
                </ul>
            </li>
            <li class="who2">Pesquisa<span class="who"><%= usuario%></span></li>
        </ul>
        <iframe src="meio.html" id="meio" name="meio" width="100%" class="myIframe" frameborder="10" align="middle"></iframe>
    </body>
</html>
