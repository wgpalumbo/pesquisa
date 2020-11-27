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
	document.frmcad.action = "listadomicilios.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}

</script>
</head>
<%
	String zona=request.getParameter("zona");if(zona==null)zona=""; 
%>
<body bgcolor="#FFFFFF">
<form name="frmcad" id="frmcad" METHOD="post"><input name="fazeroq" id="fazeroq" type="hidden"  value READONLY>
<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr align="center" bgcolor="#819FC9">
    <td colspan="3" nowrap><span class="style5"><font size="1" face="Verdana">Filtro</font></span></td>
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
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <input type="button" name="btdone" id="btdone" value="Exibir" onClick="javascript:EnviaExibir();">
    </font></td>
  </tr>
</table>
</form>
<% 
	Relatorios relato = new Relatorios(pageContext); 
    ArrayList<String> mapMotModo = new ArrayList<>();	
	if(zona.length()>0){
		mapMotModo = relato.ListaDomicilios(zona);	
	}
%>

<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr>
    <Th colspan="12" bgcolor="#002C73"><font size="2" face="Tahoma" color="#FFFFFF">(Pesquisa JAU)</font></Th>
  
  <TR>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">ID Casa</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">x_utm</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">y_utm</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">centroide</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Dist.Matriz</font></TD>
  </TR>
  <%
		//-------------------------------------------------
		try {
			if(!mapMotModo.isEmpty()){
				for(int i=0;i<mapMotModo.size();i++){
					String corda = mapMotModo.get(i);
					String cc[] = corda.split(";");
            
	%>
  <TR>
    <TD align=center nowrap bgcolor="#EEEEEE" ><font face="Tahoma" size="2" color="black"><%= cc[0] %></font></TD>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[1] %></font></TD>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[2] %></font></TD>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[3] %></font></TD>
    <TD align=right bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[4] %></font></TD>
  </TR>
  <% } } } catch (Exception e) {  out.println(e.getMessage()); }  %>
</TABLE>
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
