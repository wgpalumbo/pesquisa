//********************************************************************************
// JavaScript Document

//********************************************************************************
function TestaData(cData){
	var data = cData;
	if(data.indexOf(":")!= -1)while(data.indexOf(":")!= -1)data = data.substr(0,data.indexOf(":"))+data.substr(data.indexOf(":")+1,data.length);
	if(data.indexOf("/")!= -1)while(data.indexOf("/")!= -1)data = data.substr(0,data.indexOf("/"))+data.substr(data.indexOf("/")+1,data.length);
	if(data.indexOf("-")!= -1)while(data.indexOf("-")!= -1)data = data.substr(0,data.indexOf("-"))+data.substr(data.indexOf("-")+1,data.length);
	if(data.indexOf(" ")!= -1)while(data.indexOf(" ")!= -1)data = data.substr(0,data.indexOf(" "))+data.substr(data.indexOf(" ")+1,data.length);
	var tam = data.length;
	if	(tam != 8 && tam != 6)	return(false);
	var ano = "0";
	if	(tam == 8 )	ano = data.substr(4,4);
	if	(tam == 6 )	ano = "20" + data.substr(4,2);
	//if  (parseInt(ano) < 2000) return(false);
	var dia = data.substr(0,2);
	var mes = data.substr(2,2);
	if  (mes < 1 || mes > 12) return(false);
	switch (mes){
		case '01':
			if  (dia > 31)return(false);
			break;
		case '02':
			if  (dia > 29)return(false);
			break;
		case '03':
			if  (dia > 31)return(false);
			break;
		case '04':
			if  (dia > 30)return(false);
			break;
		case '05':
			if  (dia > 31)return(false);
			break;
		case '06':
			if  (dia > 30)return(false);
			break;
		case '07':
			if  (dia > 31)return(false);
			break;
		case '08':
			if  (dia > 31)return(false);
			break;
		case '09':
			if  (dia > 30)return(false);
			break;
		case '10':
			if  (dia > 31)return(false);
			break;
		case '11':
			if  (dia > 30)return(false);
			break;
		case '12':
			if  (dia > 31)return(false);
			break;
		}	

	return(true); 
}
//********************************************************************************
function TestaHora(hora){
	if(hora.indexOf(":")!= -1)while(hora.indexOf(":")!= -1)hora = hora.substr(0,hora.indexOf(":"))+hora.substr(hora.indexOf(":")+1,hora.length);
	var tam = hora.length;
	if	(tam < 3){
		alert('A hora esta incorreta');
		return(false);
	}
	var hr = hora.substr(0,1);
	if(tam==4)hr = hora.substr(0,2);
	var min = hora.substr (2,2);
	if ((hr > 23) || (min > 59)){
		return(false);
	}
	if(tam==6)se = hora.substr(4,2);
	if ((se > 59)){
		return(false);
	}
	return(true);
}

//********************************************************************************
function validarCPF(cpf)
{
	var vr = cpf;
	if(vr.indexOf(",")!= -1)while(vr.indexOf(",")!= -1)vr = vr.substr(0,vr.indexOf(","))+vr.substr(vr.indexOf(",")+1,vr.length);
	if(vr.indexOf(".")!= -1)while(vr.indexOf(".")!= -1)vr = vr.substr(0,vr.indexOf("."))+vr.substr(vr.indexOf(".")+1,vr.length);
	if(vr.indexOf("/")!= -1)while(vr.indexOf("/")!= -1)vr = vr.substr(0,vr.indexOf("/"))+vr.substr(vr.indexOf("/")+1,vr.length);
	if(vr.indexOf("-")!= -1)while(vr.indexOf("-")!= -1)vr = vr.substr(0,vr.indexOf("-"))+vr.substr(vr.indexOf("-")+1,vr.length);
	if(vr.indexOf(" ")!= -1)while(vr.indexOf(" ")!= -1)vr = vr.substr(0,vr.indexOf(" "))+vr.substr(vr.indexOf(" ")+1,vr.length);
	var CPFaux = vr;
	if (CPFaux.length != 11)
	return false;

	var count = 0;
	for (count++; count < CPFaux.length; count++)
	{
		if (CPFaux.charAt(count) != CPFaux.charAt(count-1))
		break;
	}
	if (count == CPFaux.length)
		return false;

	var NR_CPF = CPFaux.substr(0,9);
	var NR_DV = CPFaux.substr(9,2);
	d1 = 0;
	for (i=0;i<9;i++)
		d1 += NR_CPF.charAt(i)*(10-i);
	d1 = 11 - (d1 % 11);
	if (d1>9) d1 = 0;
	if (NR_DV.charAt(0) != d1)
		return false;
	d1 *= 2;
	for (i=0;i<9;i++)
		d1 += NR_CPF.charAt(i)*(11-i);
	d1 = 11 - (d1 % 11);
	if (d1>9) d1 = 0;

	if (NR_DV.charAt(1) != d1)
		return false;
	return true;
}

//********************************************************************************
function isValidEmailAddress(emailAddress) {
	var pattern = new RegExp(/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i);
	// alert( pattern.test(emailAddress) );
	return pattern.test(emailAddress);
};
//********************************************************************************