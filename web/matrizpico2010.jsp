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
	nrodecimal2.setMinimumFractionDigits(2);	
	NumberFormat nrodecimal1 = NumberFormat.getNumberInstance(Locale.GERMANY);
	nrodecimal1.setMaximumFractionDigits(0);
	nrodecimal1.setMinimumFractionDigits(0);	
	//-----
	String indireq = request.getParameter("indice");
	if (indireq == null) {
		indireq = "1";
	}	
	double indice = 1;
	try{
		indice = Double.parseDouble(indireq);
	}catch(Exception e){ indice=1; }
	//-----
	Relatorios relato = new Relatorios(pageContext); 
	HashMap<String, Double[]> mapPico = relato.MatrizPico2010(indice);	
	List<String> lstZonas = new ArrayList<>(mapPico.keySet());
	Collections.sort(lstZonas);

	//-----
%>
<body bgcolor="#FFFFFF">
<table border="1" cellspacing="1" cellpadding="4" align="center" bordercolor="#000000" width="80%">
  <tr bgcolor="#6CCECE">
    <td align="center" noWrap bgcolor="#BBE3FF" rowspan="2"><font face="verdana" size="1" color="Black">Zona</font></td>
    <td colspan="16" align="center" noWrap bgcolor="#6CCECE"><font face="verdana" size="1" color="Black">PICO 2010</font></td>
  </tr>
  <tr>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Popula&ccedil;&atilde;o<br>
    IBGE</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">N&deg;<br>Domic&iacute;lios</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Habitantes<br>
Domicilio</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Popula&ccedil;&atilde;o<br>
    Total</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">0 - &frac12;<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">&frac12; - 1<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">1 - 2<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">2 - 3<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">3 - 5<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">5 - 10<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">10 - 15<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">15 - 20<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">20 - 99<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Sem Rend.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Com ou Sem<br>Rend.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Menores<br>
    10 anos</font></td>
  </tr>
  <%	
		//-----
		double[] total = new double[20];
 		for(int i=0;i<lstZonas.size();i++){ 
			String izona = lstZonas.get(i);
		    Double[] celula = mapPico.get(izona);
			for(int j=0;j<celula.length;j++)
				total[j]+=celula[j];
		
	%>
  <tr>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><%= izona %></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[0]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[1]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal2.format(celula[2]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[3]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[4]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[5]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[6]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[7]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[8]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[9]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[10]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[11]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[12]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[13]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[14]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[15]) %></b></font></td>
  </tr>
  <% } %>
  <tr>
    <td align="right" colspan="1" bgcolor="#BBE3FF" nowrap><font face="verdana" size="1" color="Black">Total:</font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[0]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[1]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal2.format((total[1]!=0?total[0]/total[1]:0)) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[3]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[4]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[5]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[6]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[7]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[8]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[9]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[10]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[11]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[12]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[13]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[14]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(total[15]) %></b></font></td>
  </tr>
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
