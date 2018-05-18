package example;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @jiguiyang
 *
 * database to excel
 */
public class DatabaseToExcel {

    public static void resultSetToExcel(ResultSet rs, String xlsName, String sheetName) throws Exception
    {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0,sheetName);
        HSSFRow row= sheet.createRow((short)0);
        HSSFCell cell;
        ResultSetMetaData md=rs.getMetaData();
        int nColumn=md.getColumnCount();
        //写入各个字段的名称
        for(int i=1;i<=nColumn;i++)
        {
            cell = row.createCell((short)(i-1));
            cell.setCellValue(md.getColumnLabel(i));
        }
        int iRow=1;
        //写入各条记录，每条记录对应Excel中的一行
        while(rs.next()) {
            row= sheet.createRow((short)iRow);;
            for(int j=1;j<=nColumn;j++)
            {
                cell = row.createCell((short)(j-1));
                cell.setCellValue(rs.getObject(j).toString());
            }
            iRow++;
        }
        FileOutputStream fOut = new FileOutputStream(xlsName);
        workbook.write(fOut);
        fOut.flush();
        fOut.close();
        JOptionPane.showMessageDialog(null,"导出数据成功！");
    }

    public static void main(String []args){
        Connection con = null;
        Properties properties = new Properties();
        try {
            properties.load(DatabaseToExcel.class.getResourceAsStream("/db.properties"));
            String driverClassName = properties.getProperty("driverClassName");
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, username, password);
            System.out.println(con);

            Statement stmt = con.createStatement();
            String sqlurl = "select * from business_information order by id desc limit 10";//1
            ResultSet rs = stmt.executeQuery(sqlurl);
            try {
                resultSetToExcel(rs,"C:\\Users\\j0118\\Desktop\\a.xls","company");
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
            rs.close();
            stmt.close();

            con.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
