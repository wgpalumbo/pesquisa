<%  
	String nomearq=request.getParameter("catid");if(nomearq==null)nomearq="";
	String path = new Propriedades().getExcelDir()+"/";	
	response.setContentType("application/octet-stream");
	response.setHeader("Content-Disposition","attachment;filename=\""+nomearq+"\"");
	FileInputStream reader = null;
	ServletOutputStream writer = response.getOutputStream();
	try {
			File f = new File(path+nomearq);
			reader = new FileInputStream(f);
			byte[] bytes = new byte[8197];
			int length = 8196;
			int bread = 0;
			while((bread = reader.read(bytes, 0, length)) != -1) {
				writer.write(bytes, 0, bread);
			}
			writer.flush();
			writer.close();
			reader.close();
	}
	finally {	if (reader != null) {	reader.close();	} }
	
%> 
<%@ page import="java.io.*,java.util.*,javax.servlet.*,Jau.Infra.Propriedades"  %>