<!DOCTYPE HTML> 
<head>
    <title>Monitor</title>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" /> 
</head>
<body bgcolor="#FFFFFF">

<% 
	//-----
	Relatorios relato = new Relatorios(pageContext); 
	ArrayList<String> lstMotivos = relato.Escolaridade();
	ArrayList<String> lstModos = relato.ModosPR();
	HashMap<String, Integer> mapMotModo = relato.MontaEscolaridadeModosPR();	
	relato=null;
	int totalzaolinha=0;
	int[] totalcoluna = new int[lstModos.size()];
	if(!mapMotModo.isEmpty()&&!lstMotivos.isEmpty()){
		//------		
		String fonteTTF = "Tahoma";
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Escolaridade x Modos</font></td></tr><tr><td>");
		//------		
		out.println("<table align='center' border='1' cellpadding='2' cellspacing='0' bordercolor='#DDDDDD' bgcolor='#FFFFFF'>");
		out.println("<tr align='center' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Escolaridade</font></td>");
		for(int i=0;i<lstModos.size();i++){
			String iModo = lstModos.get(i);
			out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>"+iModo+"</font></td>");
		}
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Total</font></td>");
		out.println("</tr>");
		//-------------
		for(int i=0;i<lstMotivos.size();i++){ 
			String iMotivo = lstMotivos.get(i);
			String ii = iMotivo.split("-")[0];
			out.println("<tr align='left' bgcolor='#6699CC'>");
			out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>"+iMotivo+"</font></td>");
			int totallinha=0;
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
				out.println("<td nowrap align='center' bgcolor='white'><font face="+fonteTTF+" size='2' color='black'><b>"+(conta>0?String.valueOf(conta):" ")+"</b></font></td>");
			}		
			out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totallinha>0?String.valueOf(totallinha):" ")+"</b></font></td>");
			totalzaolinha+=totallinha;
			out.println("</tr>");
		}
		//Total
		out.println("<tr align='left' bgcolor='#6699CC'>");
		out.println("<td nowrap><font face="+fonteTTF+" size='2' color='white'>Total</font></td>");
		for(int j=0;j<lstModos.size();j++){
			out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totalcoluna[j]>0?String.valueOf(totalcoluna[j]):" ")+"</b></font></td>");
		}
		out.println("<td nowrap align='center' bgcolor='#6699CC'><font face="+fonteTTF+" size='2' color='white'><b>"+(totalzaolinha>0?String.valueOf(totalzaolinha):" ")+"</b></font></td>");
		out.println("</tr>");
		out.println("</table>");
		//------		
		out.println("</td></tr></table>");
	}
%>
</body>
</HTML>
<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
