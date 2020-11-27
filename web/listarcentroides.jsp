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
<body bgcolor="#FFFFFF">
<% 
	Relatorios relato = new Relatorios(pageContext); 
	ArrayList<String> mapMotModo = relato.ListaCentroides();	
	//------------------
	GerarExcel gerarExcel = new GerarExcel("ListagemCentroides");
	List<String> colunas = Arrays.asList( new String[]{"Zona","ID","Endereco","X-UTM","Y-UTM"} );
	gerarExcel.GerarColunas(colunas);
	gerarExcel.GerarPlanilha(mapMotModo);
	gerarExcel.Close();
	gerarExcel=null;
	//------------------
%>

<table border="1" align="center" cellpadding="3" cellspacing="0">
  <tr>
    <Th colspan="12" bgcolor="#002C73"><font size="2" face="Tahoma" color="#FFFFFF">(Pesquisa Centroides)</font></Th>
  
  <TR>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Zona</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">ID </font></TD>
    <TD align=left ><font face="Tahoma" size="2" color="#000080">Endere&ccedil;o</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Xutm</font></TD>
    <TD align=center ><font face="Tahoma" size="2" color="#000080">Yutm</font></TD>
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
    <TD align=center nowrap bgcolor="#EEEEEE" ><font face="Tahoma" size="2" color="black"><%=  cc[1] %></font></TD>
    <TD align=center bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%= cc[0] %></font></TD>
    <TD align=left bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[2] %></font></TD>
    <TD align=left bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[3] %></font></TD>
    <TD align=left bgcolor="#E1E1E1" ><font face="Tahoma" size="2" color="black"><%=  cc[4] %></font></TD>
  </TR>
  <% } } } catch (Exception e) {  out.println(e.getMessage()); }  %>
</TABLE>
</body>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,Jau.Util.GerarExcel,java.util.*" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
