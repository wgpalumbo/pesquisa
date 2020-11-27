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
	document.frmcad.action = "cruzardomiccentro.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}

</script>
</head>
<%
	NumberFormat nrodecimal2 = NumberFormat.getNumberInstance(Locale.GERMANY);
	nrodecimal2.setMaximumFractionDigits(2);
	nrodecimal2.setMinimumFractionDigits(2);	
	NumberFormat nrodecimal1 = NumberFormat.getNumberInstance(Locale.GERMANY);
	nrodecimal1.setMaximumFractionDigits(0);
	nrodecimal1.setMinimumFractionDigits(0);	
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
	ArrayList<String> mapMotModo = new ArrayList<String>();
	HashMap<String, String[]> mapDomici  = new HashMap<>();
	HashMap<String, String[]> mapCentro  = new HashMap<>();
	Relatorios relato = new Relatorios(pageContext); 
	if(zona.length()>0){
		mapDomici = relato.ObterDomiciliosCentroides(zona);
		mapCentro =  relato.ObterCentroides(zona);
		//relato.CruzarDomiciliosCentroides(zona);
	}
%>

<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr>
    <Th colspan="20" bgcolor="#002C73"><font size="2" face="Tahoma" color="#FFFFFF">(Pesquisa JAU)</font></Th>
  
  <TR>
    <TD align=center nowrap="nowrap" ><font face="Tahoma" size="2" color="#000080">ID Domiclio</font></TD>
    <TD align=center nowrap="nowrap" ><font face="Tahoma" size="2" color="#000080">UTM Domicilio</font></TD>
    <%
			if(!mapCentro.isEmpty()){
				for (Map.Entry<String, String[]> set : mapCentro.entrySet()) {
					String[] utm = set.getValue();
					out.println("<TD align=left nowrap='nowrap'><font face='Tahoma' size='2' color='#000080'>Centroide: "+set.getKey()+"<br>x:"+utm[0]+"<br>y:"+utm[1]+"<font></TD>");
				}
			}
	%>
    <TD align=center nowrap="nowrap" ><font face="Tahoma" size="2" color="#000080">Escolhido</font></TD>
    <TD align=center nowrap="nowrap" ><font face="Tahoma" size="2" color="#000080">Distancia<br>Matriz</font></TD>
  </TR>
  <%
		//-------------------------------------------------
		try {
			if(!mapDomici.isEmpty()&&!mapCentro.isEmpty()){
				
				for (Map.Entry<String, String[]> set1 : mapDomici.entrySet()) {
					String[] utm1 = set1.getValue();
            
	%>
  <TR>
    <TD align=center nowrap bgcolor="#EEEEEE" ><font face="Tahoma" size="2" color="black"><%= set1.getKey() %></font></TD>
    <TD align=left bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black">x:<%= utm1[0]+"<br>y:"+utm1[1] %></font></TD>
    <% String escolhido=""; double valorescolhido=0;
    for (Map.Entry<String, String[]> set : mapCentro.entrySet()) {
        String[] utm2 = set.getValue(); double result = relato.AcharDistancia(utm1[0],utm1[1],utm2[0],utm2[1]);
		if((valorescolhido==0)||(valorescolhido>result&&result!=-1)){ valorescolhido=result; escolhido=set.getKey(); }
        out.println("<TD align=center nowrap='nowrap' bgcolor='#E1E1E1'><font face='Tahoma' size='2' color='#000080'>"+(result==-1?result:nrodecimal2.format(result))+"<font></TD>");
    }
	%>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="red"><b><%= escolhido %></b></font></TD>
	<%
		double result2=-1;
		if(escolhido.length()>0){  result2 = relato.AcharDistanciaMatriz(escolhido); }
	%>

    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= (result2==-1?result2:nrodecimal2.format(result2)) %></font></TD>
  </TR>
  <% } } } catch (Exception e) {  out.println(e.getMessage()); }  %>
</TABLE>
</body>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*,java.text.NumberFormat" %>
<%
//******Coletor de Lixo
//chamndo o coletor
relato=null;
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
