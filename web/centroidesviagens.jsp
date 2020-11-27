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
	document.frmcad.action = "centroidesviagens.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}

</script>
</head>
<%
	String zona=request.getParameter("zona");if(zona==null)zona=""; 
	String ano=request.getParameter("ano");if(ano==null)ano=""; 
	//------------------
	ArrayList<String> planilhaXLS =  new ArrayList<>();
%>
<body bgcolor="#FFFFFF">
<form name="frmcad" id="frmcad" METHOD="post"><input name="fazeroq" id="fazeroq" type="hidden"  value READONLY>
<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr align="center" bgcolor="#819FC9">
    <td colspan="5" nowrap><span class="style5"><font size="1" face="Verdana">Filtro</font></span></td>
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
	Relatorios relato = new Relatorios(pageContext); 
	HashMap<String, Integer> mapMotModo = relato.ObterViagemCentroide(zona, ano);
%>

<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr>
    <Th colspan="9" bgcolor="#002C73"><font size="2" face="Tahoma" color="#FFFFFF">(Pesquisa JAU)</font></Th>
  
  <TR>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Centroide</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Viagens</font></TD>
  </TR>
  <%
		int soma = 0;
		//-------------------------------------------------
		try {
			if(!mapMotModo.isEmpty()){
				for (Map.Entry<String, Integer> set : mapMotModo.entrySet()) {
					int utm = (int)set.getValue();
					soma+=utm;
					//-----------------
					planilhaXLS.add(set.getKey()+";"+String.valueOf(set.getValue()));
            
	%>
  <TR>
    <TD align=center nowrap bgcolor="#EEEEEE" ><font face="Tahoma" size="2" color="black"><%=set.getKey() %></font></TD>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= set.getValue() %></font></TD>
  </TR>
  <% } } } catch (Exception e) {  out.println(e.getMessage()); }  %>
  <TR>
    <TD align=center nowrap bgcolor="#EEEEEE" ><font face="Tahoma" size="2" color="#000080">Total</font></TD>
    <TD align=center bgcolor="#E1E1E1" ><%= soma %></TD>
  </TR>
</TABLE>
</body>
</HTML>
<%
	//------------------
	String nomeArquivo="ViagensPorCentroides";
	if(zona.length()>0){
		nomeArquivo+="_zona_"+zona;
	}
	if(ano.length()>0){
		nomeArquivo+="_ano_"+ano;
	}
	GerarExcel gerarExcel = new GerarExcel(nomeArquivo);
	List<String> colunas = Arrays.asList( new String[]{"Centroide","Viagens"} );
	gerarExcel.GerarColunas(colunas);
	gerarExcel.GerarPlanilha(planilhaXLS);
	gerarExcel.Close();
	gerarExcel=null;
	//------------------
%>

<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*,Jau.Util.GerarExcel" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
