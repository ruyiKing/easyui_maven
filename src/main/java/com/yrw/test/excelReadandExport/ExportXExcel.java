package com.yrw.test.excelReadandExport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportXExcel  {  
      
	//excle文件名  
	private String fileName;  
	//显示的导出表的标题  
    private String title;  
    //导出表的列名  
    private String[] rowName ;  
    //数据list  
    private List<Object[]>  dataList = new ArrayList<Object[]>();  
      
    //HttpServletResponse  response;  
      
    //构造方法，传入要导出的数据  
    public ExportXExcel(String fileName, String title,String[] rowName,List<Object[]>  dataList){  
        this.dataList = dataList;  
        this.rowName = rowName;  
        this.title = title;  
        this.fileName = fileName;
    }  
              
     
	/**
	 * 导出文件方法   export()
	 * @throws Exception
	 */
	public void export() throws Exception{  
        try{  
            XSSFWorkbook workbook = new XSSFWorkbook();  // 创建工作簿对象  
            XSSFSheet sheet = workbook.createSheet(title); // 创建工作表  
              
            // 产生表格标题行  
            XSSFRow rowm = sheet.createRow(0);  
            XSSFCell cellTiltle = rowm.createCell(0);  
            XSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook); //获取列头样式对象  
            XSSFCellStyle style = this.getStyle(workbook); //单元格样式对象  
            //sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));  //合并单元格 
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));  //合并单元格 
            cellTiltle.setCellStyle(columnTopStyle);  
            cellTiltle.setCellValue(title);  
              
              
            // 设置表格列名（第三行）  
            int columnNum = rowName.length;  // 定义所需列数
            XSSFRow rowRowName = sheet.createRow(2);  // 在索引2的位置创建行(最顶端的行开始的第三行)  
            for(int n=0;n<columnNum;n++){  
                XSSFCell  cellRowName = rowRowName.createCell(n);  //创建列头对应个数的单元格  
                cellRowName.setCellType(XSSFCell.CELL_TYPE_STRING);  //设置列头单元格的数据类型  
                XSSFRichTextString text = new XSSFRichTextString(rowName[n]); //获取列名
                cellRowName.setCellValue(text);  //设置列头单元格的值  
                cellRowName.setCellStyle(columnTopStyle);  //设置列头单元格样式  
            }  
              
            //将查询出的数据设置到sheet对应的单元格中  
            for(int i=0;i<dataList.size();i++){  
                Object[] obj = dataList.get(i);//遍历每个对象  
                XSSFRow row = sheet.createRow(i+3);//创建所需的行数  
                for(int j=0; j<obj.length; j++){  
                    XSSFCell  cell = null;   //设置单元格的数据类型  
                    cell = row.createCell(j,XSSFCell.CELL_TYPE_STRING);  
                    if(!"".equals(obj[j]) && obj[j] != null){  
                        cell.setCellValue(obj[j].toString());                       //设置单元格的值  
                    }  
                    cell.setCellStyle(style);                                   //设置单元格样式  
                }  
            }  
            
            //让列宽随着导出的列长自动适应  
            for (int colNum = 0; colNum < columnNum; colNum++) {  
                int columnWidth = sheet.getColumnWidth(colNum) / 256;  
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {  
                    XSSFRow currentRow;  
                    //当前行未被使用过  
                    if (sheet.getRow(rowNum) == null) {  
                        currentRow = sheet.createRow(rowNum);  
                    } else {  
                        currentRow = sheet.getRow(rowNum);  
                    }  
                    if (currentRow.getCell(colNum) != null) {  
                        XSSFCell currentCell = currentRow.getCell(colNum);  
                        if (currentCell.getCellType() == XSSFCell.CELL_TYPE_STRING) {  
                            int length = currentCell.getStringCellValue().getBytes().length;//获取单元格内容长度  
                            if (columnWidth < length) {//初始长度<单元格内容长度时，列宽调为内容的长度  
                                columnWidth = length+2;  
                            }  
                        }  
                    }  
                }  
                if(colNum == 0){  
                    sheet.setColumnWidth(colNum, (columnWidth-2) * 256);  
                }else{  
                    sheet.setColumnWidth(colNum, (columnWidth+4) * 256);  
                }  
            }  
              
            //文件输出
            if(workbook !=null){  
                try  
                {  	
                	String filepash = "E:/" + fileName;
                    FileOutputStream out = new FileOutputStream(filepash);  
                    workbook.write(out);  
                    System.out.println("Excel文件导出成功：" + filepash);
                }  
                catch (IOException e)  
                {  
                    e.printStackTrace();  
                }  
            }  
  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
          
    }  
      
    /*  
     * 列头单元格样式 
     */      
    public XSSFCellStyle getColumnTopStyle(XSSFWorkbook workbook) {  
          
          // 设置字体  
          XSSFFont font = workbook.createFont();  
          //设置字体大小  
          font.setFontHeightInPoints((short)11);  
          //字体加粗  
          font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);  
          //设置字体名字   
          font.setFontName("Courier New");  
          //设置样式;   
          XSSFCellStyle style = workbook.createCellStyle();  
          //设置底边框;   
          style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
          //设置底边框颜色;    
          style.setBottomBorderColor(HSSFColor.BLACK.index);  
          //设置左边框;     
          style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
          //设置左边框颜色;   
          style.setLeftBorderColor(HSSFColor.BLACK.index);  
          //设置右边框;   
          style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
          //设置右边框颜色;   
          style.setRightBorderColor(HSSFColor.BLACK.index);  
          //设置顶边框;   
          style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
          //设置顶边框颜色;    
          style.setTopBorderColor(HSSFColor.BLACK.index);  
          //在样式用应用设置的字体;    
          style.setFont(font);  
          //设置自动换行;   
          style.setWrapText(false);  
          //设置水平对齐的样式为居中对齐;    
          style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
          //设置垂直对齐的样式为居中对齐;   
          style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
            
          return style;  
            
    }  
      
    /*   
     * 列数据信息单元格样式 
     */    
    public XSSFCellStyle getStyle(XSSFWorkbook workbook) {  
          // 设置字体  
          XSSFFont font = workbook.createFont();  
          //设置字体大小  
          //font.setFontHeightInPoints((short)10);  
          //字体加粗  
          //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
          //设置字体名字   
          font.setFontName("Courier New");  
          //设置样式;   
          XSSFCellStyle style = workbook.createCellStyle();  
          //设置底边框;   
          style.setBorderBottom(XSSFCellStyle.BORDER_THIN);  
          //设置底边框颜色;    
          style.setBottomBorderColor(HSSFColor.BLACK.index);  
          //设置左边框;     
          style.setBorderLeft(XSSFCellStyle.BORDER_THIN);  
          //设置左边框颜色;   
          style.setLeftBorderColor(HSSFColor.BLACK.index);  
          //设置右边框;   
          style.setBorderRight(XSSFCellStyle.BORDER_THIN);  
          //设置右边框颜色;   
          style.setRightBorderColor(HSSFColor.BLACK.index);  
          //设置顶边框;   
          style.setBorderTop(XSSFCellStyle.BORDER_THIN);  
          //设置顶边框颜色;    
          style.setTopBorderColor(HSSFColor.BLACK.index);  
          //在样式用应用设置的字体;    
          style.setFont(font);  
          //设置自动换行;   
          style.setWrapText(false);  
          //设置水平对齐的样式为居中对齐;    
          style.setAlignment(XSSFCellStyle.ALIGN_CENTER);  
          //设置垂直对齐的样式为居中对齐;   
          style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);  
           
          return style;  
      
    }  
} 