<!DOCTYPE HTML> 
<head>
    <title>Monitor</title>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" /> 
<style type="text/css">
.style3 {color: #FFFFFF; font-weight: bold; }
.style5 {color: #000000; font-weight: bold; }
</style>
<script>
function EnviaExibir(){
	document.frmcad.action = "listamotivomodos.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}

</script>
</head>
<%
	String zona=request.getParameter("zona");if(zona==null)zona=""; 
	String sentido=request.getParameter("sentido");if(sentido==null)sentido="O"; 
	String ano=request.getParameter("ano");if(ano==null)ano="2019"; 
	String semfator=request.getParameter("semfator");if(semfator==null)semfator="N"; 
	HashMap<String, Integer> mapSoma = new HashMap<>();
%>
<body bgcolor="#FFFFFF">
<form name="frmcad" id="frmcad" METHOD="post"><input name="fazeroq" id="fazeroq" type="hidden"  value READONLY>
<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr align="center" bgcolor="#819FC9">
    <td colspan="8" nowrap><span class="style5"><font size="1" face="Verdana">Filtro</font></span></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">ZONA:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="zona" id="zona" >
        <option value = "">Todas</option>
      <% for(int i=1;i<21;i++) { String ii = "Zona "+(i<10?"0"+String.valueOf(i):String.valueOf(i)); %>
        <option value = "<%= String.valueOf(i) %>"  <% if(zona.equals(String.valueOf(i)))out.println("selected"); %>><%= ii %></option>
      <% } %>
      </select>
    </font></td>
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">SENTIDO ZONA:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="sentido" id="sentido" >
        <option value = "O" <% if(sentido.equals("O"))out.println("selected"); %>>ORIGEM</option>
        <option value = "D" <% if(sentido.equals("D"))out.println("selected"); %>>DESTINO</option>
      </select>
    </font></td>
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">ANO:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="ano" id="ano" >
        <option value = "2019" <% if(ano.equals("2019"))out.println("selected"); %>>2019</option>
        <option value = "2036" <% if(ano.equals("2036"))out.println("selected"); %>>2036</option>
      </select>
    </font></td>
    <td align="left" nowrap bgcolor="#FFFFFF">&nbsp;</td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <input type="button" name="btdone" id="btdone" value="Exibir" onClick="javascript:EnviaExibir();">
    </font></td>
  </tr>
</table>
</form>
<% 
	Relatorios relato = new Relatorios(pageContext); 
	ArrayList<String> mapMotModo = relato.ListaMotivosModos(zona, sentido, ano);	
%>

<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr>
    <Th colspan="20" bgcolor="#002C73"><font size="2" face="Tahoma" color="#FFFFFF">(Pesquisa JAU)</font></Th>
  
  <TR>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">ID Casa</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">ID Viajante</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Motivo</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Modo 1</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Modo 2</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Modo 3</font></TD>
    <TD align=right ><font face="Tahoma" size="2" color="#000080">Z.Origem</font></TD>
    <TD align=right ><font face="Tahoma" size="2" color="#000080">Z.Destino</font></TD>
    <TD align=right ><font face="Tahoma" size="2" color="#000080">Fator</font></TD>
    <TD align=right ><font face="Tahoma" size="2" color="#000080">Renda</font></TD>
    <TD align=right ><font face="Tahoma" size="2" color="#000080">Viagem</font></TD>
    <TD align=right ><font face="Tahoma" size="2" color="#000080">Hora</font></TD>
    <TD align=left ><font face="Tahoma" size="2" color="#000080">Nome</font></TD>
  </TR>
  <%
		//-------------------------------------------------
		try {
			if(!mapMotModo.isEmpty()){
				for(int i=0;i<mapMotModo.size();i++){
					String corda = mapMotModo.get(i);
					String cc[] = corda.split(";");
					//-----------------
					String ch1 = cc[2]+"-"+cc[3];
					int soma=1;
					if(mapSoma.containsKey(ch1)){
						soma=mapSoma.get(ch1);
						soma++;
					}
					mapSoma.put(ch1,soma);
					//-----------------
					if(!cc[4].equals("00")&&false){
						String ch11 = cc[2]+"-"+cc[3];
						int soma1=1;
						if(mapSoma.containsKey(ch11)){
							soma1=mapSoma.get(ch11);
							soma1++;
						}
						mapSoma.put(ch11,soma1);
					}
					//-----------------
            
	%>
  <TR>
    <TD align=center nowrap bgcolor="#EEEEEE" ><font face="Tahoma" size="2" color="black"><%= cc[0] %></font></TD>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[1] %></font></TD>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[2] %></font></TD>
    <TD align=left bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[3] %></font></TD>
    <TD align=left bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[4] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[5] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[6] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[7] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[8] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[9] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[10] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[11] %></font></TD>
    <TD align=left bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[12] %></font></TD>
  </TR>
  <% } } } catch (Exception e) {  out.println(e.getMessage()); }  %>
</TABLE>
<%
				for (Map.Entry<String,Integer> set : mapSoma.entrySet()) {
					out.println(set.getKey()+" - "+String.valueOf(set.getValue())+"<br>");
				}

%>
</body>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
