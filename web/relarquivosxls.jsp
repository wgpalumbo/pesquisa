<html>
<head>
<title>Index - GPS</title>
<meta http-equiv="Content-Language" content="pt-br">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

<link rel="stylesheet" href="inc/estilogin.css" type="text/css" media="all" />

<script src="inc2/jquery-3.1.0.min.js"></script>

<style>
body {
  font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
  font-weight: 300;
  font-size: 12px;
  background-color: #E0E0E0;
}
.button2 {
	display: block;
	width: 240px;
	height: 16px;
	background: #FFCA00;
	padding: 5px;
	text-align: center;
	text-decoration : none;
	border-radius: 2px;
	color: black;
	border-style: solid;
	border-width : 1px 1px 1px 1px;
	text-decoration : none;
	border-color : #000000;
}


</style>

</head>
<% 
	String mesref=request.getParameter("mesref");if(mesref==null)mesref="";
	String anoref=request.getParameter("anoref");if(anoref==null)anoref="";
	Calendar today = Calendar.getInstance();
	if(mesref.length()==0){
		mesref = String.valueOf(today.getTime().getMonth()+1);	
		if(mesref.length()<2)mesref="0"+mesref;
	}
	if(anoref.length()==0)anoref = String.valueOf(today.getTime().getYear() + 1900);

%>
<body>
<form METHOD="post" name="frmcad">
  <font face="arial" size="3"><img src="icons/folder.open.gif" border=0>&nbsp;<b>Arquivos Excel
  <input id="codigozip"name="codigozip"  type="hidden" value READONLY>
  </b></font><br>
  <table border=0 cellpadding=1 cellspacing=0 width="100%">
    <tr> 
      <td nowrap><font size="1" face="arial"><b>Nome</b></font></td>
      <td nowrap><font size="1" face="arial"><b>Tamanho</b></font></td>
      <td nowrap><font size="1" face="arial"><b>Tipo</b></font></td>
      <td nowrap><font size="1" face="arial"><b>Data</b></font></td>
    </tr>
  <tr> 
      <td colspan="4" nowrap>
        <img src="imagens/linha_amarela.gif" width="100%" height="2"> </td>
  </tr>
<% 
 	//-------------------------------------
		String[] colors = { "white" , "#EBEBEB" };
		String pastaraiz = new Propriedades().getExcelDir() + File.separator;
	
		//*******************************************************************************

		java.io.File dir = new java.io.File(pastaraiz);
		String mesref1=mesref;
		if(mesref.equals("00"))mesref1="";
		//out.println(mesref1+anoref);
		File[] files = dir.listFiles(new MyFileNameFilter(mesref1+anoref));
		try{
			if(files.length>0){
				Arrays.sort(files);
				for (int i=0; i<files.length; i++) {
					String nomeFile = files[i].getName();
					String iconFile = getIcon(files[i]);
					//--------------------------------
%>				
		
  <tr bgcolor="<%= colors[i % 2] %>"> 
      <td nowrap> <a href='enviafile.jsp?catid=<%= nomeFile %>'> <img src="<%= iconFile %>" border=0> 
        <font size="2" face="arial"><b><%= nomeFile %></b></font></a></td>
      <td nowrap><font size="2" face="arial"><%= getSize(files[i]) %></font></td>
      <td nowrap><font size="1" face="arial"><%= getType(files[i]) %>&nbsp;<% if(nomeFile.toLowerCase().endsWith(".gz"))out.println("(ZIP)"); %></font></td>
      <td nowrap><font size="1" face="arial"><i><%= getDate(files[i]) %></i></font></td>
    </tr>
        		
<%		
	//--------------------------------			
			}
		}
	} catch(Exception  e) { out.println(e.getMessage()); } 
 	//-------------------------------------
%>
</table>
</form>
</body>
</html>
<%! 
//=====================
class MyFileNameFilter implements FilenameFilter{
	
	private String ext;
         
	public MyFileNameFilter(String ext){
		this.ext = ext;
	}
	public boolean accept(File dir, String name) {
		return (name.toLowerCase().endsWith(".xls")); 		
	}

}
//=====================
	
private String getIcon(File file) {
	if(file.isDirectory()) return "icons/folder.gif";
	if(file.toString().endsWith(".jsp")) return "icons/html.gif";
	if(file.toString().toLowerCase().endsWith(".txt"))  return "icons/text.gif";
	if(file.toString().toLowerCase().endsWith(".gz"))  return "icons/zip.gif";
	if(file.toString().toLowerCase().endsWith(".xls"))  return "icons/excel.gif";
	String type = getServletContext().getMimeType ( file.toString ());
	if(type==null) return "icons/unknown.gif";
	if(type.equals("text/html")) return "icons/html.gif";
	if(type.startsWith("text/")) return "icons/text.gif";
	if(type.startsWith("image/")) return "icons/image2.gif";
	return "icons/generic.gif";
}

private String getType(File file) {
	if(file.isDirectory()) return "Diretorio";
	if(file.toString().toLowerCase().endsWith(".txt")) return "TXT";
	if(file.toString().toLowerCase().endsWith(".xls")) return "XLS";
	String type = getServletContext().getMimeType ( file.toString ());
	if(type==null) return "Indefinido";
	if(type.equals("text/html")) return "HTML";
	if(type.startsWith("text/")) return "Texto";
	if(type.startsWith("image/")) return "Figura";
	return type;
}
		
private String getSize(File file) {
	if(file.isDirectory()) return (" - ");
	long size = file.length();
	if (size > 1000) return ((size/1000) + " Kb" );
	return size + " bytes";
}
	
private String getDate(File file) {
	String pattern = " ";
	Calendar now = Calendar.getInstance();
	now.roll(Calendar.DATE,true);
	now.add(Calendar.DATE, - 7 );
	java.util.Date fileDate = new java.util.Date(file.lastModified());
	pattern = "dd/MM/yyyy - hh:mm a / EEEE";
	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	return formatter.format(fileDate);
}
%>
<%@ page contentType="text/html; charset=utf-8" import="Jau.Infra.Propriedades,java.util.*,java.io.*,java.text.*" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 

