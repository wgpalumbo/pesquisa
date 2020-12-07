<!DOCTYPE HTML> 
<head>
    <title>Monitor</title>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" /> 
</head>
<body bgcolor="#FFFFFF">
<script>
function EnviaExibir(){
	document.frmcad.action = "rendaindividualmodopr.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}

</script>
</head>
<%
	String zona=request.getParameter("zona");if(zona==null)zona=""; 
	String ano=request.getParameter("ano");if(ano==null)ano="2019"; 
	String semfator=request.getParameter("semfator");if(semfator==null)semfator="N"; 
%>
<form name="frmcad" id="frmcad" METHOD="post"><input name="fazeroq" id="fazeroq" type="hidden"  value READONLY>
<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr align="center" bgcolor="#819FC9">
    <td colspan="7" nowrap><span class="style5"><font size="1" face="Verdana">Filtro</font></span></td>
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
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">ANO:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="ano" id="ano" >
        <option value = "2019" <% if(ano.equals("2019"))out.println("selected"); %>>2019</option>
        <option value = "2036" <% if(ano.equals("2036"))out.println("selected"); %>>2036</option>
        </select>
    </font></td>
    <td align="left" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">TIRAR FATOR:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="semfator" id="semfator" >
        <option value = "N" <% if(semfator.equals("N"))out.println("selected"); %>>NAO</option>
        <option value = "S" <% if(semfator.equals("S"))out.println("selected"); %>>SIM</option>
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
	ArrayList<String> colunasXLS =  new ArrayList<>();
	String planilhaExcel = "";
	ArrayList<String> planilhaXLS =  new ArrayList<>();
	//-----
	Relatorios relato = new Relatorios(pageContext); 
	ArrayList<String> lstMotivos = relato.RendaIndividual();
	ArrayList<String> lstModos = relato.ModosPR();
	HashMap<String, Integer> mapMotModo = relato.MontaRendaIndividualModosPRPorZonas(zona, ano, semfator);	
	relato=null;
	int totalzaolinha=0;
	int[] totalcoluna = new int[lstModos.size()];
	if(!mapMotModo.isEmpty()&&!lstMotivos.isEmpty()){
		String fonteTTF = "Tahoma";
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Renda Individual x Modos</font></td>");
		out.println("</tr><tr><td>");
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Sal&aacute;rio Min</font></td>");
		colunasXLS.add("Motivos");
		for(int i=0;i<lstModos.size();i++){
			String iModo = lstModos.get(i);
			out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>"+iModo+"</font></td>");
			colunasXLS.add(iModo);
		}
		colunasXLS.add("Total");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Total</font></td>");
		out.println("</tr>");
		//-------------
		planilhaExcel = "";
		for(int i=0;i<lstMotivos.size();i++){ 
			String iMotivo = lstMotivos.get(i);
			String ii = iMotivo;
			out.println("<tr align='left' bgcolor='#6699CC'>");
			out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>"+iMotivo+"</font></td>");
			planilhaExcel+=iMotivo+";";
			int totallinha=0;
			for(int j=0;j<lstModos.size();j++){
				String iModo = lstModos.get(j);
				String jj = iModo.split("-")[0];
				int conta = 0;
				String ch=ii+"-"+jj;
				//out.println(ch+"<br>");
				if(mapMotModo.containsKey(ch)){
					conta = mapMotModo.get(ch);
				}
				totallinha+=conta;
				totalcoluna[j]+=conta;
				out.println("<td nowrap align='center' bgcolor='white'><font face="+fonteTTF+" size='2' color='black'><b>"+(conta>0?String.valueOf(conta):" ")+"</b></font></td>");
				planilhaExcel+=(conta>0?String.valueOf(conta):" ")+";";
			}		
			out.println("<td nowrap align='center' bgcolor='#C8D6E8'><font face="+fonteTTF+" size='2' color='blue'><b>"+(totallinha>0?String.valueOf(totallinha):" ")+"</b></font></td>");
			planilhaExcel+=(totallinha>0?String.valueOf(totallinha):" ")+";";
			totalzaolinha+=totallinha;
			out.println("</tr>");
			planilhaXLS.add(planilhaExcel);
			planilhaExcel = "";
		}
		//Total
		out.println("<tr align='left' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Total</font></td>");
		planilhaExcel = "";
		for(int j=0;j<lstModos.size();j++){
			out.println("<td nowrap align='center' bgcolor='#C8D6E8'><font face="+fonteTTF+" size='2' color='blue'><b>"+(totalcoluna[j]>0?String.valueOf(totalcoluna[j]):" ")+"</b></font></td>");
			planilhaExcel += (totalcoluna[j]>0?String.valueOf(totalcoluna[j]):" ") +";";
		}
		out.println("<td nowrap align='center' bgcolor='#C8D6E8'><font face="+fonteTTF+" size='2' color='blue'><b>"+(totalzaolinha>0?String.valueOf(totalzaolinha):" ")+"</b></font></td>");
		planilhaExcel += (totalzaolinha>0?String.valueOf(totalzaolinha):" ") +";";
		out.println("</tr>");
		out.println("</table>");
		//------		
		out.println("</td></tr></table>");
		planilhaXLS.add("Total;"+planilhaExcel);
		planilhaExcel="";
	}
%>
<%
	//------------------
	String nomeArquivo="ModoPrincipalxRendaIndividual"+ano;
	if(zona.length()>0){
		nomeArquivo+="_Zona_"+zona;
	}
	GerarExcel gerarExcel = new GerarExcel(nomeArquivo);
	//List<String> colunas = Arrays.asList( new String[]{"Zona","ID","Endereco","X-UTM","Y-UTM"} );
	gerarExcel.GerarColunas(colunasXLS);
	gerarExcel.GerarPlanilha(planilhaXLS);
	gerarExcel.Close();
	gerarExcel=null;
	//------------------
%>
</body>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*,Jau.Util.GerarExcel" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
