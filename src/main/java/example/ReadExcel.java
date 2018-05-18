package example;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @jiguiyang
 *
 * write excel and read excel
 */
public class ReadExcel {
    public static String xlsFile="C:\\Users\\j0118\\Desktop\\a.xls"; //产生的Excel文件的名称
    public static void main(String args[]) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(); //产生工作簿对象
            HSSFSheet sheet = workbook.createSheet(); //产生工作表对象
            //设置第一个工作表的名称为firstSheet
            workbook.setSheetName(0, "company");
            //产生一行
            HSSFRow row = sheet.createRow((short) 0);
            //产生第一个单元格
            HSSFCell cell = row.createCell((short) 0);
            //往第一个单元格中写入信息
            cell.setCellValue("测试成功");
            FileOutputStream fOut = new FileOutputStream(xlsFile);
            workbook.write(fOut);
            fOut.flush();
            fOut.close();
            System.out.println("文件生成...");


            //以下语句读取生成的Excel文件内容
            FileInputStream fIn = new FileInputStream(xlsFile);
            HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
            HSSFSheet readSheet = readWorkBook.getSheet("company");
            HSSFRow readRow = readSheet.getRow(0);
            HSSFCell readCell = readRow.getCell((short) 0);
            System.out.println("第一个单元是：" + readCell.getStringCellValue());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
