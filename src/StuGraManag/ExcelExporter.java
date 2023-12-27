package StuGraManag;

import DataBase.Backend;
import Toaster.Toaster;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class ExcelExporter {
    private final Toaster t;
    private int classId=-1;
    private final Backend back = new Backend();
    private final JPanel panel=new JPanel();
    private String className;
    public ExcelExporter(JFrame frame) {


        t=new Toaster(this.panel);
        JLabel topLabel = new JLabel("Export the data of students in a file Excel (.xls or .xlsx):");
        JLabel branchLabel = new JLabel("Select the Class:");
        JComboBox<String> branchList;
        branchList = new JComboBox<String>();
        String[] classes=back.selectClass();
        for (String aClass : classes) {
            branchList.addItem(aClass);
        }
        JButton exportButton=new JButton("Export");
        exportButton.setBackground(Color.green);
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classId=back.searchClassId(branchList.getSelectedItem().toString());
                exporter();
            }
        });
        panel.setLayout(null);
        topLabel.setBounds(250,30,400,30);
        branchLabel.setBounds(300, 100, 200, 30);
        branchList.setBounds(300, 140, 200, 30);
        exportButton.setBounds(300, 180, 200, 30);
        panel.add(topLabel);
        panel.add(branchLabel);
        panel.add(branchList);
        panel.add(exportButton);
        frame.add(panel);
        frame.setVisible(true);
    }



    public void export(JTable table, File file) {
            try {
                TableModel m = table.getModel();
                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet("Sheet1");

                // Header
                XSSFRow rowHeader = sheet.createRow(0);
                for (int i = 0; i < m.getColumnCount(); i++) {
                    rowHeader.createCell(i).setCellValue(m.getColumnName(i));
                }

                // Data
                for (int i = 0; i < m.getRowCount(); i++) {

                    XSSFRow row = sheet.createRow(i + 1);
                    for (int j = 0; j < m.getColumnCount(); j++) {
                        row.createCell(j).setCellValue(m.getValueAt(i, j).toString());
                    }
                }

                FileOutputStream fileOut = new FileOutputStream(file);
                wb.write(fileOut);
                fileOut.close();

            } catch (IOException ignored) {

            }
    }

    public File selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select File");
        FileFilter imageFilter = new FileNameExtensionFilter("Excel file", "xls", "xlsx");
        fileChooser.setFileFilter(imageFilter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;

    }

    public void exporter(){
        try {
            String[] columns = new String[]{"Number", "Firs Name", "Last Name", "Gender", "Birthday", "Phone", "Email",
                    "Grade A","Grade B","Grade C","Grade D","Grade E","Grade F"};
            String[][] data =back.tableToExport(this.classId);
            JTable table = new JTable(data, columns);
            File file=selectFile();
            if(file!=null) {
                export(table, file);
                t.success("Data exported successfully to the file .");
            }
        }catch (Exception exception){
            t.error("Error. Please try again");
        }
    }
}
