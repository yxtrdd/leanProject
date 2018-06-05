package cn.easyproject.easyee.sm.base.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;

import jxl.Cell;
import jxl.CellType;
import jxl.FormulaCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * @ClassName: ExcelUtil
 * @Description: jxl操作excel的工具类.
 * @date: 2017年9月20日 上午9:39:05 <br/>
 * @author: gaojx <br/>
 * @copyright: 北京志诚泰和信息技术有限公司
 */

public class ExcelUtil {
    /**
     * Excel 2003版本，一个工作表最多可有65536行，256列
     * Excel 2007及以后版本，一个工作表最多可有1048576行，16384列
     */
    public static int EXPORT_NUMBER_PER = 5000; // 单次导出最大数
    private static DecimalFormat decimalFormat = new DecimalFormat("###################.###########");

    /**
     * @Title: 创建工作表表头
     * @param ws 工作表
     * @param Format 单元格样式
     * @param headers 表头数组
     * @return
     */
    public static boolean createSheetHeader(WritableSheet ws, WritableCellFormat Format, String[] headers) {
        boolean bool = true;
        try {
            SheetSettings ss = ws.getSettings();
            ss.setVerticalFreeze(1);// 冻结表头

            // 循环写入表头
            for (int i = 0; i < headers.length; i++) {
                /*
                 * 添加单元格(Cell)内容addCell() 添加Label对象Label()
                 * 数据的类型有很多种、在这里你需要什么类型就导入什么类型
                 * 如：jxl.write.Label[DateTime|Number](i, 0, columns[i], Format)
                 * 其中i为列、0为行、columns[i]为数据、Format为样式
                 * 合起来就是说将columns[i]添加到第一行(行、列下标都是从0开始)第i列、样式内容居中
                 */
                ws.addCell(new Label(i, 0, headers[i], Format));
            }
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    /**
     * @Title: 将数据写入单元格
     * @param ws 工作表
     * @param Format 单元格样式
     * @param data 导出内容数组
     * @param columns 列对应的数据在Map中的key
     * @return
     */
    public static <T> boolean writeDataToSheet(WritableSheet ws, WritableCellFormat Format, List<T> data, String[] columns) {
        boolean bool = true;
        try {
            // 循环写入表中数据
            for (int i = 0; i < data.size(); i++) {
                Map<?, ?> map = null;

                T datum = data.get(i);
                // 转换成map集合{activyName:测试功能,count:2}
                if (datum instanceof Map) {
                    map = (Map<?, ?>) datum;
                } else if (datum instanceof Object) {
                    map = BeanUtils.describe(datum);
                }

                // 循环输出map中的子集：既列值
                for (int j = 0; j < columns.length; j++) {
                    Object value = map.get(columns[j]);
                    if (null == value) {
                        value = "";
                    }
                    WritableCell cell = null;
                    if (value instanceof Date) {
                        cell = new jxl.write.DateTime(j, i + 1, (Date) value, Format);
                    } else if (value instanceof Integer || value instanceof Long || value instanceof Float || value instanceof Double) {
                        cell = new jxl.write.Number(j, i + 1, (Double) value, Format);
                    } else {
                        cell = new jxl.write.Label(j, i + 1, (String) value, Format);
                    }
                    ws.addCell(cell);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        }
        return bool;
    }

    /**
     * @Title: exportToExcel
     * @Description: 导出excel
     * @param response
     * @param sheetName 导出工作表的名称.
     * @param data 导出内容数组.
     * @param headers 导出Excel的表头数组.
     * @param columns 列对应的数据在Map中的key.
     * @return
     */
    public static <T> int exportToExcel(HttpServletResponse response, List<T> data, String[] headers, String[] columns) {
        int flag = 0;

        // 声明工作簿jxl.write.WritableWorkbook
        WritableWorkbook wwb = null;
        OutputStream os = null;

        try {
            // 根据传进来的file对象创建可写入的Excel工作薄
            os = response.getOutputStream();

            wwb = Workbook.createWorkbook(os);

            WritableFont font = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
            WritableCellFormat Format = new WritableCellFormat(font);

            // 背景颜色
            // Format.setBackground(jxl.format.Colour.YELLOW);
            Format.setAlignment(Alignment.CENTRE); // 平行居中
            Format.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
            Format.setWrap(true);// 是否自动换行

            /*
             * 这个是单元格内容居中显示 还有很多很多样式
             */
            Format.setAlignment(Alignment.CENTRE);

            /*
             * 创建一个工作表、sheetName为工作表的名称、"0"为第一个工作表
             * 打开Excel的时候会看到左下角默认有3个sheet、"sheet1、sheet2、sheet3"
             * 这样代码中的"0"就是sheet1、其它的一一对应 createSheet(sheetName, 0)
             * 一个是工作表的名称，另一个是工作表在工作薄中的位置
             */
            WritableSheet ws = wwb.createSheet("Sheet1", 0);

            // 判断一下表头数组是否有数据
            if (headers != null && headers.length > 0) {
                boolean b1 = createSheetHeader(ws, Format, headers);
                // 判断表中是否有数据
                if (b1 && data != null && data.size() > 0) {
                    writeDataToSheet(ws, Format, data, columns);
                    // 写入Exel工作表
                    wwb.write();
                    // 关闭Excel工作薄对象
                    wwb.close();
                    // 清空缓存
                    os.flush();
                } else {
                    flag = -1;
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            flag = 0;
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                if (null != os) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * @Title: exportExcel
     * @Description: 下载excel
     * @param response
     * @param fileName 文件名 ,如:20110808.xls
     * @param data 数据源
     * @param headers 列名称集合,如：{物品名称，数量，单价}
     * @param columns 列对应的数据在Map中的key
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, List<T> data, String[] headers, String[] columns) {
        // 调用上面的方法、生成Excel文件
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + new Date().getTime() + ".xls");

            exportToExcel(response, data, headers, columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  设置单元格样式
     */
    public static WritableCellFormat getCellStyle(){
        WritableCellFormat Format = null;
        try {
            WritableFont font = new WritableFont(WritableFont.createFont("微软雅黑"), 10, WritableFont.BOLD);
            Format = new WritableCellFormat(font);
            
            // Format.setBackground(jxl.format.Colour.YELLOW);// 背景颜色
            Format.setAlignment(Alignment.CENTRE); // 平行居中
            Format.setVerticalAlignment(VerticalAlignment.CENTRE); // 垂直居中
            Format.setWrap(true);// 是否自动换行
            Format.setAlignment(Alignment.CENTRE);// 这个是单元格内容居中显示 还有很多很多样式
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Format;
    }
    
    /**
     * @Title: exportExcleByTemplate
     * @Description: 根据模板导出Excel文件
     * @param response
     * @param templateFilePath
     *            模板文件路径包括模板名称和扩展名
     * @param beanParams
     *            导出数据
     * @param resultFileName
     *            导出文件名称和扩展名
     * @throws UnsupportedEncodingException
     */
    public static void exportExcleByTemplate(HttpServletResponse response, String templateFilePath, Map<String, Object> beanParams,
            String resultFileName) throws UnsupportedEncodingException {
        // 设置响应
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(resultFileName.getBytes("UTF-8"), "ISO8859-1"));
        response.setContentType("application/vnd.ms-excel");

        // 创建XLSTransformer对象
        XLSTransformer transformer = new XLSTransformer();

        InputStream in = null;
        OutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(templateFilePath));
            org.apache.poi.ss.usermodel.Workbook workbook = transformer.transformXLS(in, beanParams);
            out = response.getOutputStream();
            // 将内容写入输出流并把缓存的内容全部发出去
            workbook.write(out);
            out.flush();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

    }

    /**
     * @Title: createExcel
     * @Description: 根据模板生成Excel文件
     * @param srcFilePath
     *            模板文件路径
     * @param beanParams
     *            模板中存放的数据
     * @param destFilePath
     *            生成的文件路径
     */
    public static void createExcel(String srcFilePath, Map<String, Object> beanParams, String destFilePath) {
        // 创建XLSTransformer对象
        XLSTransformer transformer = new XLSTransformer();
        try {
            // 生成Excel文件
            transformer.transformXLS(srcFilePath, beanParams, destFilePath);
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: readExcel
     * @Description: 读取Excel文件的内容
     * @param file
     *            待读取的文件
     * @return
     */
    public static List<List<List<String>>> readExcel(File file) throws BiffException, IOException {
        Workbook workBook = Workbook.getWorkbook(file);

        if (workBook == null)
            return null;

        return getDataInWorkbook(workBook);
    }

    private static List<List<List<String>>> getDataInWorkbook(Workbook workBook) {
        // 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
        Sheet[] sheet = workBook.getSheets();

        List<List<List<String>>> dataList = new ArrayList<List<List<String>>>();

        if (sheet != null && sheet.length > 0) {
            // 对每个工作表进行循环
            for (int i = 0; i < sheet.length; i++) {
                List<List<String>> rowList = new ArrayList<List<String>>();
                // 得到当前工作表的行数
                int rowNum = sheet[i].getRows();
                int colNum = sheet[i].getColumns();
                for (int j = 0; j < rowNum; j++) {
                    // 得到当前行的所有单元格
                    Cell[] cells = sheet[i].getRow(j);
                    if (cells != null && cells.length > 0) {
                        List<String> cellList = new ArrayList<String>();
                        // 对每个单元格进行循环
                        for (int k = 0; k < colNum; k++) {
                            Cell cell = sheet[i].getCell(k, j);
                            String cellValue = "";
                            // 判断单元格的值是否是数字
                            if (cell.getType() == CellType.NUMBER) {
                                NumberCell numberCell = (NumberCell) cell;
                                double value = numberCell.getValue();
                                cellValue = decimalFormat.format(value);
                            } else if (cell.getType() == CellType.NUMBER_FORMULA || cell.getType() == CellType.STRING_FORMULA
                                    || cell.getType() == CellType.BOOLEAN_FORMULA || cell.getType() == CellType.DATE_FORMULA
                                    || cell.getType() == CellType.FORMULA_ERROR) {
                                FormulaCell nfc = (FormulaCell) cell;
                                cellValue = nfc.getContents();
                            } else {
                                // 读取当前单元格的值
                                cellValue = cell.getContents();
                                // 特殊字符处理
                                cellValue = excelCharaterDeal(cellValue);
                            }
                            // 去掉空格
                            cellList.add(cellValue.trim());
                        }
                        rowList.add(cellList);
                    }
                }
                dataList.add(rowList);
            }
        }
        // 最后关闭资源，释放内存
        workBook.close();

        return dataList;
    }

    /**
     * @Title: toToken
     * @Description: 除去字符串中指定的分隔符
     * @param s
     *            字符串
     * @param val
     *            指定的分隔符
     * @return
     */
    private static String toToken(String s, String val) {
        if (s == null || s.trim().equals("")) {
            return s;
        }
        if (val == null || val.equals("")) {
            return s;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String[] result = s.split(val);
        for (int x = 0; x < result.length; x++) {
            stringBuffer.append(" ").append(result[x]);
        }
        return stringBuffer.toString();

    }

    /**
     * @Title: excelCharaterDeal
     * @Description: Excel特殊字符处理
     * @param str
     *            字符串
     * @return
     */
    private static String excelCharaterDeal(String str) {
        String[] val = {"-", "_", "/"};// 定义特殊字符
        for (String i : val) {
            str = toToken(str, i);
        }
        return str;
    }

    /**
     * @Title: readExcelTitle
     * @Description: 读取Excel表格表头的内容
     * @param file
     *            待读取的文件
     * @return 表头内容的数组
     * @throws Exception
     */
    public String[] readExcelTitle(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(row.getCell(i));
        }
        return title;
    }

    /**
     * @Title: readExcelByPoi
     * @Description: 通过POI读取Excel文件的内容
     * @param file
     *            待读取的文件
     * @return
     * @throws Exception
     */
    public static List<List<List<String>>> readExcelByPoi(File file) throws Exception {
        List<List<List<String>>> dataList = new ArrayList<List<List<String>>>();

        InputStream is = new FileInputStream(file);
        HSSFWorkbook wb = new HSSFWorkbook(is);

        int sheetNum = wb.getNumberOfSheets();

        for (int i = 0; i < sheetNum; i++) {
            HSSFSheet sheet = wb.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 得到总行数
            int rowNum = sheet.getLastRowNum();

            HSSFRow row = sheet.getRow(0);
            if (row == null) {
                continue;
            }
            // 得到总列数
            int colNum = row.getPhysicalNumberOfCells();

            List<List<String>> rowList = new ArrayList<List<String>>();
            // 循环行Row
            for (int j = 0; j <= rowNum; j++) {
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                List<String> cellList = new ArrayList<String>();
                int k = 0;
                while (k < colNum) {
                    String cellValue = getCellFormatValue(row.getCell(k)).trim();
                    cellList.add(cellValue);
                    k++;
                }
                rowList.add(cellList);
            }
            dataList.add(rowList);
        }

        return dataList;
    }

    /**
     * @Title: getCellFormatValue
     * @Description: 获取单元格数据内容为字符串类型的数据
     * @param cell
     *            Excel单元格
     * @return 单元格数据内容
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case HSSFCell.CELL_TYPE_NUMERIC :
                    // 取得当前Cell的数值
                    cellvalue = decimalFormat.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_FORMULA : {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        // 如果是Date类型则，转化为Data格式

                        // 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();

                        // 方法2：这样子的data格式是不带带时分秒的：2011-10-12
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    } else {
                        FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
                        CellValue cellValue = evaluator.evaluate(cell);
                        switch (cellValue.getCellType()) {
                            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN :
                                cellvalue = String.valueOf(cellValue.getBooleanValue());
                                break;
                            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC :
                                cellvalue = decimalFormat.format(cellValue.getNumberValue());
                                break;
                            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING :
                                cellvalue = String.valueOf(cellValue.getStringValue());
                                break;
                            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK :
                                break;
                            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR :
                                break;
                        }
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case HSSFCell.CELL_TYPE_STRING :
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default :
                    cellvalue = "";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;
    }
    
    /**
     * @Title: DB2Excel
     * @Description: 通过结果集写入EXCEL
     * @param rs 结果集
     * @param filePath 路径
     * @param fileName 文件名
     * @param map 其他参数
     * @return boolean
     */
    
    public static byte[] DB2Excel(ResultSet rs,String filePath,String fileName,Map<String,String> map,InputStream is) { 
        WritableWorkbook workBook = null; 
        Workbook wk=null;
        WritableSheet sheet = null; 
        WritableCell cell = null; 
        ByteArrayOutputStream bos=null;
        try { 
            // 创建Excel表 
            bos = new ByteArrayOutputStream();
            /*wk=Workbook.getWorkbook(is);
            workBook = Workbook.createWorkbook(bos);
            Sheet st=wk.getSheet(0);
            workBook.createSheet(st.getName(), 0);
            workBook.importSheet(st.getName(), 0, st);*/
            wk = Workbook.getWorkbook(new File(filePath+"\\"+fileName)); // 获得原始文档  
            workBook = Workbook.createWorkbook(new File(filePath+"\\"+fileName),wk); //通过原始文档获得备份文档
            //workBook = Workbook.createWorkbook(FileUtil.createFile(filePath+"\\copy.xls", true),wk); //通过原始文档获得备份文档
            // 创建Excel表中的sheet 
            sheet=workBook.getSheet(0);
            // 向Excel中添加数据 
            ResultSetMetaData rsmd = rs.getMetaData(); 
            int columnCount = rsmd.getColumnCount(); 
            
            //获取内容为XH的单元格坐标
            int x=0;
            int y=0;
            loop:for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    String name= sheet.getCell(i, j).getContents();
                    if(name.equals("XH")){
                        x=i;
                        y=j;
                        System.out.println("x="+x+"   y="+y);
                        break loop;
                    }
                }
            }
            int row=y+1;
            int xh=1;
            //设置单元格样式
            WritableCellFormat format=new WritableCellFormat(NumberFormats.TEXT);
            format.setBorder(Border.ALL, BorderLineStyle.THIN);  //边框
            format.setAlignment(Alignment.CENTRE);   //居中
            
            NumberFormat doublenf=new NumberFormat("0.00");
            WritableCellFormat doubleFormat=new WritableCellFormat(doublenf);
            doubleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            doubleFormat.setAlignment(Alignment.CENTRE);
            
            DateFormat df = new DateFormat("yyyy-MM-dd hh:mm");
            WritableCellFormat dataFormat = new WritableCellFormat(df);
            dataFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            dataFormat.setAlignment(Alignment.CENTRE);
            while (rs.next()) { 
                //根据表头写入数据
                for (int i = 0; i < columnCount; i++) { 
                    //获取表头字段
                    String name= sheet.getCell(x+1+i, y).getContents();
                    if("".equals(name)){
                        break;
                    }
                    //获取字段类型
                    String dataType=map.get(name);
                    //设置单元格数字格式
                    if("CHAR".equals(dataType)||"VARCHAR2".equals(dataType)){
                        cell = new jxl.write.Label(x+1+i, row, rs.getString(name),format); 
                    }else if("NUMBER".equals(dataType)){
                        cell = new jxl.write.Number(x+1+i, row, rs.getDouble(name),doubleFormat);
                    }else if("DATE".equals(dataType)){
                        if(rs.getDate(name)==null){
                            cell = new jxl.write.Label(x+1+i, row, "",format);
                        }else{
                            cell = new jxl.write.DateTime(x+1+i, row, rs.getDate(name),dataFormat);
                        }
                    }else{
                        cell = new jxl.write.Label(x+1+i, row, rs.getString(name),format); 
                    }
                  //  label = new Label(x+1+i, row, rs.getString(name),format); 
                    sheet.addCell(cell); 
                }
                //写入型号（行数）
                cell = new Label(x, row, xh+"",format); 
                sheet.addCell(cell); 
                row++; 
                xh++;
            } 
            //删除临时表头字段
            sheet.removeRow(y);
            workBook.write(); 
            System.out.println("数据成功写入Excel"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } finally { 
            try { 
                if(workBook!=null){
                    workBook.close(); 
                }
                if(wk!=null){
                    wk.close();
                }
                if(rs!=null){
                    rs.close();
                }
                if(is!=null){
                    is.close();
                }
                if(bos!=null){
                    bos.close();
                }
            } catch (Exception e2) { 
                e2.printStackTrace(); 
            } 
        } 
        return bos.toByteArray(); 
    }     

}
