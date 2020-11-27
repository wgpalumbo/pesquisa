<!DOCTYPE HTML> 
<head>
    <title>Monitor</title>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" /> 
</head>
<% 
	NumberFormat nrodecimal2 = NumberFormat.getNumberInstance(Locale.GERMANY);
	nrodecimal2.setMaximumFractionDigits(2);
	nrodecimal2.setMinimumFractionDigits(2);	//-----
	Relatorios relato = new Relatorios(pageContext); 
	ArrayList<String> lstModos = relato.ModosPR();
	HashMap<String, Integer> mapPop = relato.Populacao();	
	HashMap<String, Integer> mapSintese = relato.MontaSintesePorZona();	
	HashMap<String, Integer> mapSinteseDom = relato.MontaSintesePorZonaDomicilio();	
	HashMap<String, Integer> mapSinteseRes = relato.MontaSintesePorZonaResidentes();	
	HashMap<String, Integer> mapSinteseModos = relato.MontaSintesePorZonaModosPR();	
	ArrayList<String> lstZonas = relato.lstZonas;
	if(!lstZonas.isEmpty()){
		java.util.Collections.sort(lstZonas);
	}
	int totmodo[] = new int[lstModos.size()];
%>
<body bgcolor="#FFFFFF">
<table border="1" cellspacing="1" cellpadding="4" align="center" bordercolor="#000000" width="80%">
  <tr bgcolor="#FFE79B">
    <td align="center" noWrap bgcolor="#FFFFCC" rowspan="2"><font face="verdana" size="1" color="Black">Zona</font></td>
    <td align="center" noWrap colspan="5"><font face="verdana" size="1" color="Black">Entrevistados</font></td>
    <td colspan="<%=lstModos.size()%>" align="center" noWrap><font face="verdana" size="1" color="Black">Apurados</font></td>
  </tr>
  <tr>
    <td align="center" noWrap bgcolor="#FFFFCC"><font face="verdana" size="1" color="Black">Popula&ccedil;&atilde;o</font></td>
    <td align="center" noWrap bgcolor="#FFFFCC"><font face="verdana" size="1" color="Black">Fator</font></td>
    <td align="center" noWrap bgcolor="#FFFFCC"><font face="verdana" size="1" color="Black">Domic&iacute;lios</font></td>
    <td align="center" bgcolor="#FFFFCC" noWrap><font face="verdana" size="1" color="Black">Residentes</font></td>
    <td align="center" bgcolor="#FFFFCC" noWrap><font face="verdana" size="1" color="Black">Viajantes</font></td>
    <% for(int j=0;j<lstModos.size();j++){ String iModo = lstModos.get(j); %>
    <td align="center" bgcolor="#FFFFCC" noWrap><font face="verdana" size="1" color="Black"><%= iModo %></font></td>
	<% } %>
  </tr>
  <%	int contador=0,contador1=0,contador2=0,populacao=0;
  		for(int i=0;i<lstZonas.size();i++){ 
			String izona = lstZonas.get(i);
			int conta=0,conta1=0,conta2=0;
			if(mapSintese.containsKey(izona)){
				conta = mapSintese.get(izona);
			}
			if(mapSinteseRes.containsKey(izona)){
				conta1 = mapSinteseRes.get(izona);
			}
			if(mapSinteseDom.containsKey(izona)){
				conta2 = mapSinteseDom.get(izona);
			}
			if(mapPop.containsKey(izona)){
				populacao = mapPop.get(izona);
			} else {
				populacao=0;
			}
			contador+=conta;
			contador1+=conta1;
			contador2+=conta2;
			//-----
			double fator = 0;
			if(conta1>0){
				fator = populacao/conta1;
			}
			//-----

	%>
  <tr>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><%= izona %></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= populacao %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal2.format(fator) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= conta2 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= conta1 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= conta %></b></font></td>
	<% for(int j=0;j<lstModos.size();j++){ 
		String iModo = lstModos.get(j); 
		String ch = izona+"-"+iModo.split("-")[0];
		int conta4=0;
		if(mapSinteseModos.containsKey(ch)){
			conta4 = mapSinteseModos.get(ch);
		}
		totmodo[j]+=conta4;
	%>
    <td align="center" bgcolor="#FFFFFF" noWrap><font face="verdana" size="2" color="Black"><b><%= conta4 %></b></font></td>
	<% } %>
  </tr>
  <%
		}
	if(contador>0){ %>
  <tr>
    <td align="right" colspan="1" bgcolor="#FFFFCC" nowrap><font face="verdana" size="1" color="Black">Total:</font></td>
    <td align="center" bgcolor="#ffffff" nowrap>&nbsp;</td>
    <td align="center" bgcolor="#ffffff" nowrap>&nbsp;</td>
    <td align="center" bgcolor="#ffffff" nowrap><font face="verdana" size="2" color="Black"><b><%= contador2 %></b></font></td>
    <td align="center" bgcolor="#ffffff" nowrap><font face="verdana" size="2" color="Black"><b><%= contador1 %></b></font></td>
    <td align="center" bgcolor="#ffffff" nowrap><font face="verdana" size="2" color="Black"><b><%= contador %></b></font></td>
	<% for(int j=0;j<lstModos.size();j++){ 	%>
    <td align="center" bgcolor="#FFFFFF" noWrap><font face="verdana" size="2" color="Black"><b><%= totmodo[j] %></b></font></td>
	<% } %>
  </tr>
  <% } %>
</table>
</body>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*,java.text.NumberFormat" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
