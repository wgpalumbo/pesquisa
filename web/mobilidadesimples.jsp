<!DOCTYPE HTML> 
<head>
    <title>Monitor</title>
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Pragma" content="no-cache" /> 
<script>
function EnviaExibir(){
	document.frmcad.action = "mobilidadesimples.jsp";
	document.frmcad.target = "_self"; 
	document.frmcad.submit();
}

</script>
</head>
<% 
	NumberFormat nrodecimal2 = NumberFormat.getNumberInstance(Locale.GERMANY);
	nrodecimal2.setMaximumFractionDigits(2);
	nrodecimal2.setMinimumFractionDigits(2);	
	NumberFormat nrodecimal1 = NumberFormat.getNumberInstance(Locale.GERMANY);
	nrodecimal1.setMaximumFractionDigits(0);
	nrodecimal1.setMinimumFractionDigits(0);	
	//-----
	String modo=request.getParameter("modo");if(modo==null)modo=""; 
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
	//-----
	HashMap<Integer, String> mapFaixa = new HashMap<>();
	mapFaixa.put(1000,"0 a 1000");
	mapFaixa.put(2000,"1001 a 2000");
	mapFaixa.put(3000,"2001 a 3000");
	mapFaixa.put(4000,"3001 a 4000");
	mapFaixa.put(5000,"4001 a 5000");
	mapFaixa.put(7000,"5001 a 7000");
	mapFaixa.put(10000,"7001 a 10000");
	mapFaixa.put(1000000,"Mais de 10001");
	//-----
	HashMap<Integer, int[]> mapViagem = relato.ObterMobilidadeViagem(modo);
	HashMap<Integer, int[]> mapPessoa = relato.ObterMobilidadePessoas();
	//-----
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
    <td align="right" nowrap bgcolor="#CCCCCC"><font color="#000000" size="1" face="Verdana">MODO:</font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <select name="modo" id="modo" >
        <option value = "">Todas</option>
        <option value = "C" <% if(modo.equals("C"))out.println("selected"); %>>CARRO</option>
        <option value = "O" <% if(modo.equals("O"))out.println("selected"); %>>ONIBUS</option>
        </select>
    </font></td>
    <td align="left" nowrap bgcolor="#FFFFFF"><font size=-1>
      <input type="button" name="btdone" id="btdone" value="Exibir" onClick="javascript:EnviaExibir();">
    </font></td>
  </tr>
</table>
</form>

<table border="1" cellspacing="1" cellpadding="4" align="center" bordercolor="#000000" width="80%">
  <tr bgcolor="#6CCECE">
    <td colspan="2" rowspan="2" align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">FAIXAS<br>DISTANCIAS</font></td>
    <td colspan="12" align="center" noWrap bgcolor="#6CCECE"><font face="verdana" size="1" color="Black">FAIXAS DE RENDA</font></td>
  </tr>
  <tr>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Sem Rend.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">0 - &frac12;<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">&frac12; - 1<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">1 - 2<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">2 - 3<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">3 - 5<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">5 - 10<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">10 - 15<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">15 - 20<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">20 - 99<br>sal.min.</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Menores<br>
    10 anos</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Total</font></td>
  </tr>
  <%	
		//-----
		double[] total = new double[20];
		int totalcelula=0,totalcelula2=0;
		int[] acumcelula = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};	
		int[] acumcelula2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};	
		Iterator iterator = new TreeSet(mapFaixa.keySet()).iterator();
		while (iterator.hasNext()) {
			int chave = (Integer)iterator.next();
			String valor = (String)mapFaixa.get(chave);
			//------------------------
			int[] celula = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};	
			int[] celula2 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};	
			if(mapViagem.containsKey(chave)){
				celula = mapViagem.get(chave);
			}
			if(mapPessoa.containsKey(chave)){
				celula2 = mapPessoa.get(chave);
			}
			//---------
			int somacelula=0,somacelula2=0;
			for(int j=0;j<celula.length;j++){
				somacelula+=celula[j];
				acumcelula[j]+=celula[j];
			}
			totalcelula+=somacelula;
			for(int j=0;j<celula2.length;j++){
				somacelula2+=celula2[j];
				acumcelula2[j]+=celula2[j];
			}
			totalcelula2+=somacelula2;
			//---------
			for(int j=0;j<celula.length;j++)
			//-------------------------
			planilhaExcel=valor+";Nro.Viagens;"+nrodecimal1.format(celula[0])+";";
			planilhaExcel+=nrodecimal1.format(celula[1])+";";
			planilhaExcel+=nrodecimal1.format(celula[2])+";";
			planilhaExcel+=nrodecimal1.format(celula[3])+";";
			planilhaExcel+=nrodecimal1.format(celula[4])+";";
			planilhaExcel+=nrodecimal1.format(celula[5])+";";
			planilhaExcel+=nrodecimal1.format(celula[6])+";";
			planilhaExcel+=nrodecimal1.format(celula[7])+";";
			planilhaExcel+=nrodecimal1.format(celula[8])+";";
			planilhaExcel+=nrodecimal1.format(celula[9])+";";
			planilhaExcel+=nrodecimal1.format(celula[10])+";";
			planilhaExcel+=nrodecimal1.format(somacelula);
			planilhaXLS.add(planilhaExcel);
			planilhaExcel=valor+";Nro.Moradores;"+nrodecimal1.format(celula2[0])+";";
			planilhaExcel+=nrodecimal1.format(celula2[1])+";";
			planilhaExcel+=nrodecimal1.format(celula2[2])+";";
			planilhaExcel+=nrodecimal1.format(celula2[3])+";";
			planilhaExcel+=nrodecimal1.format(celula2[4])+";";
			planilhaExcel+=nrodecimal1.format(celula2[5])+";";
			planilhaExcel+=nrodecimal1.format(celula2[6])+";";
			planilhaExcel+=nrodecimal1.format(celula2[7])+";";
			planilhaExcel+=nrodecimal1.format(celula2[8])+";";
			planilhaExcel+=nrodecimal1.format(celula2[9])+";";
			planilhaExcel+=nrodecimal1.format(celula2[10])+";";
			planilhaExcel+=nrodecimal1.format(somacelula2);
			planilhaXLS.add(planilhaExcel);
			planilhaExcel=valor+";Mobilidade;"+(celula2[0]!=0?nrodecimal2.format((double)celula[0]/celula2[0]):"0")+";";
			planilhaExcel+=(celula2[1]!=0?nrodecimal2.format((double)celula[1]/celula2[1]):"0")+";";
			planilhaExcel+=(celula2[2]!=0?nrodecimal2.format((double)celula[2]/celula2[2]):"0")+";";
			planilhaExcel+=(celula2[3]!=0?nrodecimal2.format((double)celula[3]/celula2[3]):"0")+";";
			planilhaExcel+=(celula2[4]!=0?nrodecimal2.format((double)celula[4]/celula2[4]):"0")+";";
			planilhaExcel+=(celula2[5]!=0?nrodecimal2.format((double)celula[5]/celula2[5]):"0")+";";
			planilhaExcel+=(celula2[6]!=0?nrodecimal2.format((double)celula[6]/celula2[6]):"0")+";";
			planilhaExcel+=(celula2[7]!=0?nrodecimal2.format((double)celula[7]/celula2[7]):"0")+";";
			planilhaExcel+=(celula2[8]!=0?nrodecimal2.format((double)celula[8]/celula2[8]):"0")+";";
			planilhaExcel+=(celula2[9]!=0?nrodecimal2.format((double)celula[9]/celula2[9]):"0")+";";
			planilhaExcel+=(celula2[10]!=0?nrodecimal2.format((double)celula[10]/celula2[10]):"0")+";";
			planilhaExcel+=(somacelula2!=0?nrodecimal2.format((double)somacelula/somacelula2):"0");
			planilhaXLS.add(planilhaExcel);
	%>
  <tr>
    <td rowspan="3" align="center" noWrap><font face="verdana" size="2" color="Black"><%= valor %></font></td>
    <td align="center" noWrap><font face="verdana" size="1" color="Black">N&deg; Viagens</font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[0]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[1]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[2]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[3]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[4]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[5]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[6]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[7]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[8]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[9]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula[10]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(somacelula) %></b></font></td>
  </tr>
  <tr>
    <td align="center" noWrap><font face="verdana" size="1" color="Black">N&deg; Moradores</font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[0]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[1]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[2]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[3]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[4]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[5]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[6]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[7]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[8]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[9]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(celula2[10]) %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(somacelula2) %></b></font></td>
  </tr>
  <tr>
    <td align="center" noWrap><font face="verdana" size="1" color="Black">Mobilidade</font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[0]!=0?nrodecimal2.format((double)celula[0]/celula2[0]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[1]!=0?nrodecimal2.format((double)celula[1]/celula2[1]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[2]!=0?nrodecimal2.format((double)celula[2]/celula2[2]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[3]!=0?nrodecimal2.format((double)celula[3]/celula2[3]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[4]!=0?nrodecimal2.format((double)celula[4]/celula2[4]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[5]!=0?nrodecimal2.format((double)celula[5]/celula2[5]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[6]!=0?nrodecimal2.format((double)celula[6]/celula2[6]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[7]!=0?nrodecimal2.format((double)celula[7]/celula2[7]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[8]!=0?nrodecimal2.format((double)celula[8]/celula2[8]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[9]!=0?nrodecimal2.format((double)celula[9]/celula2[9]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (celula2[10]!=0?nrodecimal2.format((double)celula[10]/celula2[10]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (somacelula2!=0?nrodecimal2.format((double)somacelula/somacelula2):"0") %></b></font></td>
  </tr>
  <tr>
    <td colspan="14" align="center" noWrap bgcolor="#CCCCCC">&nbsp;</td>
  </tr>
  <% } %>
  <tr>
    <td colspan="1" rowspan="3" align="right" nowrap bgcolor="#BBE3FF"><font face="verdana" size="1" color="Black">Total:</font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="1" color="Black">N&deg; Viagens</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[0]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[1]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[2]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[3]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[4]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[5]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[6]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[7]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[8]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[9]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula[10]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(totalcelula) %></b></font></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="1" color="Black">N&deg; Moradores</font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[0]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[1]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[2]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[3]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[4]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[5]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[6]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[7]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[8]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[9]) %></b></font></td>
    <td align="center" noWrap bgcolor="#BBE3FF"><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(acumcelula2[10]) %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= nrodecimal1.format(totalcelula2) %></b></font></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="1" color="Black">Mobilidade</font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[0]!=0?nrodecimal2.format((double)acumcelula[0]/acumcelula2[0]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[1]!=0?nrodecimal2.format((double)acumcelula[1]/acumcelula2[1]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[2]!=0?nrodecimal2.format((double)acumcelula[2]/acumcelula2[2]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[3]!=0?nrodecimal2.format((double)acumcelula[3]/acumcelula2[3]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[4]!=0?nrodecimal2.format((double)acumcelula[4]/acumcelula2[4]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[5]!=0?nrodecimal2.format((double)acumcelula[5]/acumcelula2[5]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[6]!=0?nrodecimal2.format((double)acumcelula[6]/acumcelula2[6]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[7]!=0?nrodecimal2.format((double)acumcelula[7]/acumcelula2[7]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[8]!=0?nrodecimal2.format((double)acumcelula[8]/acumcelula2[8]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[9]!=0?nrodecimal2.format((double)acumcelula[9]/acumcelula2[9]):"0") %></b></font></td>
    <td align="center" noWrap><font face="verdana" size="2" color="Black"><b><%= (acumcelula2[10]!=0?nrodecimal2.format((double)acumcelula[10]/acumcelula2[10]):"0") %></b></font></td>
    <td align="center" bgcolor="#BBE3FF" nowrap><font face="verdana" size="2" color="Black"><b><%= (totalcelula2!=0?nrodecimal2.format((double)totalcelula/totalcelula2):"0") %></b></font></td>
  </tr>
</table>
</body>
</HTML>
<%
	//------------------
	String nomeArquivo="MobilidadeSimples";
	if(modo.length()>0){
		nomeArquivo+="_modo_"+modo;
	}
	GerarExcel gerarExcel = new GerarExcel(nomeArquivo);
	List<String> colunas = Arrays.asList( new String[]{"Faixas Distancias","ID","SEM REND.","0 - ½","½ - 1","1 - 2","2 - 3","3 - 5","5 - 10","10 - 15","15 - 20","20 - 99","Menores de 10","Total"} );
	gerarExcel.GerarColunas(colunas);
	gerarExcel.GerarPlanilha(planilhaXLS);
	gerarExcel.Close();
	gerarExcel=null;
	//------------------
%>

<%@ page contentType="text/html; charset=UTF-8" import="Jau.Sistema.Relatorios,Jau.Util.GravarLog,java.util.*,java.text.NumberFormat,Jau.Util.GerarExcel" %>
<%
//******Coletor de Lixo
//chamndo o coletor
Runtime rt = Runtime.getRuntime(); 
rt.gc();	rt=null;
//******************************	
%> 
