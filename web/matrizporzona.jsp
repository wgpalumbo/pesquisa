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
	//-----
	double salariominimo=1045.00;
	Relatorios relato = new Relatorios(pageContext); 
	HashMap<String, Integer> mapPop = relato.Populacao();	
	HashMap<String, Integer> mapSinteseDom = relato.MontaSintesePorZonaDomicilio();	
	HashMap<String, Integer> mapSinteseRes = relato.MontaSintesePorZonaResidentes();	
	HashMap<String, Integer> mapMenores = relato.MontaMenores10Anos();	
	//-----
	HashMap<String, Integer> mapSM_SemRend = relato.MontaSalarioMinimo(0,0);
	HashMap<String, Integer> mapSM_0 = relato.MontaSalarioMinimo(0,0.5*salariominimo);
	HashMap<String, Integer> mapSM_1 = relato.MontaSalarioMinimo(0.5*salariominimo,salariominimo);
	HashMap<String, Integer> mapSM_1_2 = relato.MontaSalarioMinimo(salariominimo,2*salariominimo);
	HashMap<String, Integer> mapSM_2_3 = relato.MontaSalarioMinimo(2*salariominimo,3*salariominimo);
	HashMap<String, Integer> mapSM_3_5 = relato.MontaSalarioMinimo(3*salariominimo,5*salariominimo);
	HashMap<String, Integer> mapSM_5_10 = relato.MontaSalarioMinimo(5*salariominimo,10*salariominimo);
	HashMap<String, Integer> mapSM_10_15 = relato.MontaSalarioMinimo(10*salariominimo,15*salariominimo);
	HashMap<String, Integer> mapSM_15_20 = relato.MontaSalarioMinimo(15*salariominimo,20*salariominimo);
	HashMap<String, Integer> mapSM_20_99 = relato.MontaSalarioMinimo(20*salariominimo,1000*salariominimo);
	//-----
	ArrayList<String> lstZonas = relato.lstZonas;
	if(!lstZonas.isEmpty()){
		java.util.Collections.sort(lstZonas);
	}
	//-----
%>
<body bgcolor="#FFFFFF">
<table border="1" cellspacing="1" cellpadding="4" align="center" bordercolor="#000000" width="80%">
  <tr bgcolor="#6CCECE">
    <td align="center" noWrap bgcolor="#BBE3FF" rowspan="2"><font face="verdana" size="1" color="Black">Zona</font></td>
    <td colspan="4" align="center" noWrap bgcolor="#6CCECE"><font face="verdana" size="1" color="Black">Pesquisa</font></td>
    <td colspan="12" align="center" noWrap bgcolor="#6CCECE"><font face="verdana" size="1" color="Black">Sal.Min.R$:&nbsp;<%= nrodecimal2.format(salariominimo) %></font></td>
  </tr>
  <tr>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Popula&ccedil;&atilde;o<br>
    IBGE</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Domic&iacute;lios</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Popula&ccedil;&atilde;o<br>
    Residentes</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Habitantes<br>
Domicilio</font></td>
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
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Com ou Sem<br>
    Rendimento</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Menores<br>10 anos</font></td>
  </tr>
  <%	int contador=0,contador1=0,contador2=0,populacao=0;
		int contasal_0=0,contasal_1=0,contasal_1_2=0,contasal_2_3=0,contasal_3_5=0,contasal_5_10=0;
		int contasal_10_15=0,contasal_15_20=0,contasal_20_99=0,contasal_SemRend=0,soma_tot_linha=0;
		int tot_0=0,tot_1=0,tot_1_2=0,tot_2_3=0,tot_3_5=0,tot_5_10=0,tot_10_15=0,tot_15_20=0,tot_20_99=0,tot_SemRend=0;
		//-----
 		for(int i=0;i<lstZonas.size();i++){ 
			String izona = lstZonas.get(i);
			int conta=0,conta1=0,conta2=0;
			if(mapMenores.containsKey(izona)){
				conta = mapMenores.get(izona);
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
			double popxcasa = 0;
			if(conta2!=0){
				popxcasa = (double)conta1/conta2;
			}
			//-----
			int sal_0=0,sal_1=0,sal_1_2=0,sal_2_3=0,sal_3_5=0,sal_5_10=0,sal_10_15=0,sal_15_20=0,sal_20_99=0,sal_SemRend=0;
			//-----
			if(mapSM_0.containsKey(izona)){
				sal_0 = mapSM_0.get(izona);
				tot_0+=sal_0;
			}
			if(mapSM_1.containsKey(izona)){
				sal_1 = mapSM_1.get(izona);
				tot_1+=sal_1;
			}
			if(mapSM_1_2.containsKey(izona)){
				sal_1_2 = mapSM_1_2.get(izona);
				tot_1_2+=sal_1_2;
			}
			if(mapSM_2_3.containsKey(izona)){
				sal_2_3 = mapSM_2_3.get(izona);
				tot_2_3+=sal_2_3;
			}
			if(mapSM_3_5.containsKey(izona)){
				sal_3_5 = mapSM_3_5.get(izona);
				tot_3_5+=sal_3_5;
			}
			if(mapSM_5_10.containsKey(izona)){
				sal_5_10 = mapSM_5_10.get(izona);
				tot_5_10+=sal_5_10;
			}
			if(mapSM_10_15.containsKey(izona)){
				sal_10_15 = mapSM_10_15.get(izona);
				tot_10_15+=sal_10_15;
			}
			if(mapSM_15_20.containsKey(izona)){
				sal_15_20 = mapSM_15_20.get(izona);
				tot_15_20+=sal_15_20;
			}
			if(mapSM_20_99.containsKey(izona)){
				sal_20_99 = mapSM_20_99.get(izona);
				tot_20_99+=sal_20_99;
			}
			if(mapSM_SemRend.containsKey(izona)){
				sal_SemRend = mapSM_SemRend.get(izona);
				tot_SemRend+=sal_SemRend;
			}
			//-----
			int tot_linha =sal_0;
			tot_linha+=sal_1;
			tot_linha+=sal_1_2;
			tot_linha+=sal_2_3;
			tot_linha+=sal_3_5;
			tot_linha+=sal_5_10;
			tot_linha+=sal_10_15;
			tot_linha+=sal_15_20;
			tot_linha+=sal_20_99;
			tot_linha+=sal_SemRend;
			soma_tot_linha+=tot_linha;
			//-----
	%>
  <tr>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><%= izona %></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= populacao %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= conta2 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= conta1 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal2.format(popxcasa) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_0 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_1 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_1_2 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_2_3 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_3_5 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_5_10 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_10_15 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_15_20 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_20_99 %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= sal_SemRend %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_linha %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= conta %></b></font></td>
  </tr>
  <%
		}
	if(contador1>0){ %>
  <tr>
    <td align="right" colspan="1" bgcolor="#BBE3FF" nowrap><font face="verdana" size="1" color="Black">Total:</font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap>&nbsp;</td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= contador2 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= contador1 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= (contador2!=0?nrodecimal2.format((double)contador1/contador2):0) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_0 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_1 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_1_2 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_2_3 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_3_5 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_5_10 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_10_15 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_15_20 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" noWrap><font face="verdana" size="2" color="Black"><b><%= tot_20_99 %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= tot_SemRend %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= soma_tot_linha %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= contador %></b></font></td>
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
