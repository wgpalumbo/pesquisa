<!DOCTYPE HTML> 
<head>
    <title>Monitor</title>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" /> 
    <script>
function EnviaExibir(){
	document.frmcad.action = "escolaridademobilidadepr.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}

</script>
</head>
<%
	String ano=request.getParameter("ano");if(ano==null)ano=""; 
	//-----
	ArrayList<String> colunasXLS =  new ArrayList<>();
	String planilhaExcel = "";
	ArrayList<String> planilhaXLS =  new ArrayList<>();
	//-----
%>
<body bgcolor="#FFFFFF">
<form name="frmcad" id="frmcad" METHOD="post"><input name="fazeroq" id="fazeroq" type="hidden"  value READONLY>
<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr align="center" bgcolor="#819FC9">
    <td colspan="3" nowrap><span class="style5"><font size="1" face="Verdana">Filtro</font></span></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">ANO:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="ano" id="ano" >
        <option value = "">SemFator</option>
        <option value = "2019" <% if(ano.equals("2019"))out.println("selected"); %>>2019</option>
        <option value = "2036" <% if(ano.equals("2036"))out.println("selected"); %>>2036</option>
        </select>
    </font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <input type="button" name="btdone" id="btdone" value="Exibir" onClick="javascript:EnviaExibir();">
    </font></td>
  </tr>
</table>
</form>

<% 
	//-----
	NumberFormat nrodecimal = NumberFormat.getNumberInstance(Locale.GERMANY);
	nrodecimal.setMaximumFractionDigits(4);
	nrodecimal.setMinimumFractionDigits(4);
	//-----
	Relatorios relato = new Relatorios(pageContext); 
	ArrayList<String> lstMotivos = relato.Escolaridade();
	ArrayList<String> lstModos = relato.ModosPR();
	HashMap<String, Integer> mapMotModo = relato.MontaEscolaridadeModosPR(ano);	
	HashMap<String, Integer> mapTotFaixa = relato.MontaTotalEscolaridade(ano);	
	relato=null;
	int totalfaixa=0;
	int totalzaolinha=0;
	double mobilzao=0.0;
	int[] totalcoluna = new int[lstModos.size()];
	if(!mapMotModo.isEmpty()&&!lstMotivos.isEmpty()){
		//------		
		String fonteTTF = "Tahoma";
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Mobilidade x Escolaridade</font></td></tr><tr><td>");
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Escolaridade</font></td>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Soma/Faixa</font></td>");
		colunasXLS.add("Escolaridade");
		colunasXLS.add("Soma/Faixa");
		for(int i=0;i<lstModos.size();i++){
			String iModo = lstModos.get(i);
			out.println("<td nowrap colspan=2><font face="+fonteTTF+" size='2' color='white'>"+iModo+"</font></td>");
			colunasXLS.add(iModo);
		}
		colunasXLS.add("Total");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Total</font></td>");
		out.println("</tr>");
		//-------------
		int totalsomafaixa=0;
		for(int i=0;i<lstMotivos.size();i++){ 
			String iMotivo = lstMotivos.get(i);
			String ii = iMotivo.split("-")[0];
			if (mapTotFaixa.containsKey(ii)) {
				totalsomafaixa+=mapTotFaixa.get(ii);
			}
		}
		/*
		for (Map.Entry<String, Integer> set : mapTotFaixa.entrySet()) {
		    totalsomafaixa += set.getValue();
			out.println(set.getKey());
			out.println(set.getValue());
			out.println("<br>");
		}
		out.println(totalsomafaixa);
		*/
		//-------------
		planilhaExcel = "";
		for(int i=0;i<lstMotivos.size();i++){ 
			String iMotivo = lstMotivos.get(i);
			String ii = iMotivo.split("-")[0];
			out.println("<tr align='left' bgcolor='#6699CC'>");
			out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>"+iMotivo+"</font></td>");
			planilhaExcel+=iMotivo+";";
			//----
			int soma=0;
			if (mapTotFaixa.containsKey(ii)) {
				soma = mapTotFaixa.get(ii);
			}
			totalfaixa+=soma;
			out.println("<td nowrap align='center' bgcolor='white'><font face="+fonteTTF+" size='2' color='black'><b>"+String.valueOf(soma)+"</b></font></td>");
			planilhaExcel+=String.valueOf(soma)+";";
			//----
			int totallinha=0;
			double mobilinha=0;
			for(int j=0;j<lstModos.size();j++){
				String iModo = lstModos.get(j);
				String jj = iModo.split("-")[0];
				int conta = 0;
				String ch=ii+"-"+jj;
				if(mapMotModo.containsKey(ch)){
					conta = mapMotModo.get(ch);
				}
				totallinha+=conta;
				totalcoluna[j]+=conta;
				//----
				double mobil = 0;
				try{
					if(soma>0)mobil = (double)conta/totalsomafaixa;
				} catch(Exception e){ }
				mobilinha+=mobil;
				//----
				out.println("<td nowrap align='center' bgcolor='white'><font face="+fonteTTF+" size='2' color='black'><b>"+(conta>0?String.valueOf(conta):" ")+"</b></font></td>");
				out.println("<td nowrap align='center' bgcolor='white'><font face="+fonteTTF+" size='2' color='red'>"+(mobil>0?nrodecimal.format(mobil):" ")+"</font></td>");
				planilhaExcel+=(conta>0?String.valueOf(conta):" ")+";";
			}		
			try{
				mobilinha = (double)totallinha/totalsomafaixa;
			} catch(Exception e){ }
			out.println("<td nowrap align='right' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totallinha>0?String.valueOf(totallinha):" ")+"</b> / <font color='yellow'>"+(mobilinha>0?nrodecimal.format(mobilinha):" ")+"</font></font></td>");
			totalzaolinha+=totallinha;
			mobilzao+=mobilinha;
			out.println("</tr>");
			planilhaExcel+=(totallinha>0?String.valueOf(totallinha):" ")+";";
			planilhaXLS.add(planilhaExcel);
			planilhaExcel = "";
		}
		//Total
		out.println("<tr align='left' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Total</font></td>");
		out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totalfaixa>0?String.valueOf(totalfaixa):" ")+"</b></font></td>");
		planilhaExcel = (totalfaixa>0?String.valueOf(totalfaixa):" ") +";";
		for(int j=0;j<lstModos.size();j++){
			double mobilinha=0.00;
			try{
				mobilinha = (double)totalcoluna[j]/totalfaixa;
			} catch(Exception e){ }
			out.println("<td nowrap colspan=2  align='right' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totalcoluna[j]>0?String.valueOf(totalcoluna[j]):" ")+"</b> / <font color='yellow'>"+(mobilinha>0?nrodecimal.format(mobilinha):" ")+"</font></font></td>");
			planilhaExcel += (totalcoluna[j]>0?String.valueOf(totalcoluna[j]):" ") +";";
		}
		try{
			mobilzao = (double)totalzaolinha/totalfaixa;
		} catch(Exception e){ }
		out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totalzaolinha>0?String.valueOf(totalzaolinha):" ")+"</b> / <font color='yellow'>"+(mobilzao>0?nrodecimal.format(mobilzao):" ")+"</font></font></td>");
		out.println("</tr>");
		out.println("</table>");
		//------		
		out.println("</td></tr></table>");
		planilhaExcel += (totalzaolinha>0?String.valueOf(totalzaolinha):" ") +";";
		planilhaXLS.add("Total;"+planilhaExcel);
		planilhaExcel="";
	}
%>
</body>
<%
	//------------------
	String nomeArquivo="ModoPrincipalxEscolaridade";
	if(ano.length()>0){
		nomeArquivo+="_ano_"+ano;
	}
	GerarExcel gerarExcel = new GerarExcel(nomeArquivo);
	//List<String> colunas = Arrays.asList( new String[]{"Faixas Distancias","ID","SEM REND.","0 - ½","½ - 1","1 - 2","2 - 3","3 - 5","5 - 10","10 - 15","15 - 20","20 - 99","Menores de 10","Total"} );
	gerarExcel.GerarColunas(colunasXLS);
	gerarExcel.GerarPlanilha(planilhaXLS);
	gerarExcel.Close();
	gerarExcel=null;
	//------------------
%>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*,java.text.NumberFormat,Jau.Util.GerarExcel" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
