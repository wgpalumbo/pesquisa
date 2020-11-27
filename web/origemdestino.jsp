<!DOCTYPE HTML> 
<head>
    <title>Monitor</title>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" /> 
<style>
#modo, #motivo, #ano {  color: #000000; background-color: #FDFEE0; border: 1px #FF9900 double; text-align:left; font-weight: bold;}
</style>
<script src="inc2/jquery-3.1.0.min.js"></script>
<script language="JavaScript">
function EnviaExibir(){
	document.frmcad.action = "origemdestino.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}
$(document).ready(function(){
	//$('[id=motivo]').attr("disabled", "disabled");
});
</script>
</head>
<% 
	String ano = request.getParameter("ano");
	if (ano == null) {
		ano = "";
	} 
	String modo = request.getParameter("modo");
	if (modo == null) {
		modo = "";
	} 
	String motivo = request.getParameter("motivo");
	if (motivo == null) {
		motivo = "01";
	} 
	//-----
	ArrayList<String> colunasXLS =  new ArrayList<>();
	String planilhaExcel = "";
	ArrayList<String> planilhaXLS =  new ArrayList<>();
	//-----
	Relatorios relato = new Relatorios(pageContext);
	HashMap<String, Integer> mapOrigemDestino = relato.MontaOrigemDestino(ano, motivo, modo); 
	ArrayList<String> lstZonas = relato.lstZonas;
	ArrayList<String> lstModos = relato.Modos();
	ArrayList<String> lstMotivos = relato.Motivos();
	if(!lstZonas.isEmpty()){
		java.util.Collections.sort(lstZonas);
	}
	relato=null;

%>
<body bgcolor="#FFFFFF">
<form name="frmcad" id="frmcad" METHOD="post"><input name="fazeroq" id="fazeroq" type="hidden"  value READONLY>
<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr align="center" bgcolor="#819FC9">
    <td colspan="7" nowrap><span class="style5"><font size="1" face="Verdana">Filtro</font></span></td>
  </tr>
  <tr bgcolor="#FFFFFF">
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">ANO:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="ano" id="ano" >
        <option value = "">Sem Fator</option>
        <option value = "2019" <% if(ano.equals("2019"))out.println("selected"); %>>2019</option>
        <option value = "2036" <% if(ano.equals("2036"))out.println("selected"); %>>2036</option>
        </select>
    </font></td>
    <td align="left" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">MOTIVO:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font color="#000000" size="2" face="Tahoma"><%= Relatorios.montaMotivos(lstMotivos,motivo) %></font></td>
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">MODO:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
    <%= Relatorios.montaModos(lstModos,modo) %></font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <input type="button" name="btdone" id="btdone" value="Exibir" onClick="javascript:EnviaExibir();">
    </font></td>
  </tr>
</table>
</form></body>

<% 
	//----------
	//----------
	/*
	if(!mapOrigemDestino.isEmpty()){
		Iterator iterator = new TreeSet(mapOrigemDestino.keySet()).iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			int conta = mapOrigemDestino.get(key);
			out.println(key+" - "+String.valueOf(conta)+"<hr>");
		}
	}
	*/
	//----------
	int totalzaolinha=0;
	int[] totalcoluna = new int[lstZonas.size()];
	if(!mapOrigemDestino.isEmpty()&&!lstZonas.isEmpty()){
		//------		
		String fonteTTF = "Tahoma";
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Origem x Destino</font></td></tr><tr><td>");
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Zonas</font></td>");
		colunasXLS.add("Zonas");
		for(int i=0;i<lstZonas.size();i++){
			String iModo = lstZonas.get(i);
			out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>"+iModo+"</font></td>");
			colunasXLS.add(iModo);
		}
		colunasXLS.add("Total");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Total</font></td>");
		out.println("</tr>");
		//-------------
		planilhaExcel = "";
		for(int i=0;i<lstZonas.size();i++){ 
			String xZona = lstZonas.get(i);
			out.println("<tr align='left' bgcolor='#6699CC'>");
			out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>"+xZona+"</font></td>");
			planilhaExcel+=xZona+";";
			int totallinha=0;
			for(int j=0;j<lstZonas.size();j++){
				String yZona = lstZonas.get(j);
				int conta = 0;
				//-----------
				try {
                    int x1 = Integer.parseInt(xZona);
                    int y1 = Integer.parseInt(yZona);
					String ch=String.valueOf(x1)+"-"+String.valueOf(y1);
					if(mapOrigemDestino.containsKey(ch)){
						conta = mapOrigemDestino.get(ch);
					}
                } catch (Exception e) {
                    conta = 0;
                }
				//-----------
				totallinha+=conta;
				totalcoluna[j]+=conta;
				out.println("<td nowrap align='center' bgcolor='white'><font face="+fonteTTF+" size='2' color='black'><b>"+(conta>0?String.valueOf(conta):" ")+"</b></font></td>");
				planilhaExcel+=(conta>0?String.valueOf(conta):" ")+";";
			}		
			out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totallinha>0?String.valueOf(totallinha):" ")+"</b></font></td>");
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
		for(int j=0;j<lstZonas.size();j++){
			out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totalcoluna[j]>0?String.valueOf(totalcoluna[j]):" ")+"</b></font></td>");
			planilhaExcel += (totalcoluna[j]>0?String.valueOf(totalcoluna[j]):" ") +";";
		}
		out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totalzaolinha>0?String.valueOf(totalzaolinha):" ")+"</b></font></td>");
		planilhaExcel += (totalzaolinha>0?String.valueOf(totalzaolinha):" ") +";";
		out.println("</tr>");
		out.println("</table>");
		planilhaXLS.add("Total;"+planilhaExcel);
		planilhaExcel="";
	}
%>
<%
	//------------------
	String nomeArquivo="OrigemDestino_"+ano;
	if(modo.length()>0){
		nomeArquivo+="_Modo_"+modo;
	}
	if(motivo.length()>0){
		nomeArquivo+="_Motivo_"+motivo;
	}
	GerarExcel gerarExcel = new GerarExcel(nomeArquivo);
	//List<String> colunas = Arrays.asList( new String[]{"Zona","ID","Endereco","X-UTM","Y-UTM"} );
	gerarExcel.GerarColunas(colunasXLS);
	gerarExcel.GerarPlanilha(planilhaXLS);
	gerarExcel.Close();
	gerarExcel=null;
	//------------------
%>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*,Jau.Util.GerarExcel" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
