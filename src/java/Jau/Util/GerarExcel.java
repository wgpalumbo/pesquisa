/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jau.Util;

import Jau.Infra.Propriedades;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Walter
 */
public class GerarExcel {

    private Workbook wb = null;
    private String pastaArq = "";
    private OutputStream fileOut;
    private Sheet sheet;
    private int contaColuna, contaRow;

    public GerarExcel(String arq) {
        String arqLocal = arq + ".xls";
        Propriedades prop = new Propriedades();
        pastaArq = prop.getExcelDir() + "/" + arqLocal;
        contaColuna = 0;
        contaRow = 0;
        try {
            fileOut = new FileOutputStream(pastaArq);
            wb = new HSSFWorkbook();
            sheet = wb.createSheet("Planilha");

        } catch (Exception e) {
            System.out.println("GerarExcel:" + e.getMessage());
        }
    }

    public void Close() throws IOException {
        try {
            wb.write(fileOut);
            wb.close();
            fileOut.close();
        } catch (Exception e) {
            System.out.println("Close:" + e.getMessage());
        }
    }

    public void GerarColunas(List<String> colunas) throws FileNotFoundException, IOException {
        ExecutorService executor2 = Executors.newFixedThreadPool(5);
        executor2.execute(() -> {
            try {
                if (!colunas.isEmpty()) {

                    Row row = sheet.createRow(0);
                    contaRow++;
                    contaColuna = 0;
                    colunas.forEach((String s) -> {
                        System.out.println(s);
                        Cell cell = row.createCell(contaColuna);
                        cell.setCellValue(s);

                        Font font = wb.createFont();
                        font.setColor(IndexedColors.BLUE.getIndex());
                        font.setBold(true);
                        font.setFontHeightInPoints((short) 10);

                        CellStyle cellStyle = wb.createCellStyle();
                        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                        cellStyle.setFont(font);

                        cell.setCellStyle(cellStyle);

                        //sheet[contaColuna] = wb.createSheet(s);
                        contaColuna++;
                    });
                }
            } catch (Exception e) {
                System.out.println("GerarExcel-GerarColunas:" + e.getMessage());
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }

    }

    public void GerarPlanilha(List<String> conteudocolunas) {
        ExecutorService executor2 = Executors.newFixedThreadPool(5);
        executor2.execute(() -> {
            try {
                if (!conteudocolunas.isEmpty()) {
                    conteudocolunas.forEach((String s) -> {
                        String cc[] = s.split(";");
                        Row row = sheet.createRow(contaRow);
                        contaRow++;
                        //-----
                        for (int i = 0; i < cc.length; i++) {
                            Cell cell = row.createCell(i);
                            cell.setCellValue(cc[i]);
                            

                            Font font = wb.createFont();
                            font.setColor(IndexedColors.BLACK.getIndex());
                            font.setBold(false);
                            font.setFontHeightInPoints((short) 9);

                            CellStyle cellStyle = wb.createCellStyle();
                            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                            cellStyle.setFont(font);

                            cell.setCellStyle(cellStyle);

                        }
                    });
                }
            } catch (Exception e) {
                System.out.println("GerarExcel-GerarColunas:" + e.getMessage());
            }
        });
        executor2.shutdown();
        while (!executor2.isTerminated()) {
        }
    }

}
