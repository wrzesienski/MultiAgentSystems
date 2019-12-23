package AgentLauncher;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс для импорта данных из xlsx-файлов
 */
class ImportFromExcel {


    /**
     * Метод, импортирующий график нагрузки потребителя из xlsx-файла в xml
     * @param args
     */
    static void importLoad(String args) {

        File myFile = new File("C://Users//Alexander//IdeaProjects//Lab4//NagrGraph.xlsx");
        FileInputStream fis;
        XSSFWorkbook myWorkBook = null;
        try {
            fis = new FileInputStream(myFile);
            // Finds the workbook instance for XLSX file
            myWorkBook = new XSSFWorkbook(fis);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(2);

        // Get iterator to all the rows in current sheet

        // Traversing over each row of XLSX file

        AgentConfig object;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(AgentConfig.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object obj = jaxbUnmarshaller.unmarshal(new File(args));
            object = (AgentConfig) obj;

            for (AgentDefinition obb : object.getAgentDefinitions()) {
                ArrayList<Double> al = new ArrayList<>();
                Iterator<Row> rowIterator = mySheet.iterator();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    System.out.println(cell.getStringCellValue());


                    if (cell.getStringCellValue().equals(obb.getAgentName())) {
                        int colin = cell.getColumnIndex();

                        // Traversing over each row of XLSX file
                        while (rowIterator.hasNext()) {
                            Row row1 = rowIterator.next();
                            Cell cell1 = row1.getCell(colin);


                            if (cell1.getCellType() == CellType.NUMERIC) {
                                al.add(cell1.getNumericCellValue());
                            }
                        }
                    }
                }

                obb.setFilepath(al);
            }

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, new File(args));


        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, импортирующий график объема производства электроэнергии ЭС
     * из xlsx-файла в xml
     * @param args
     */
    static void importPower(String args) {

        File myFile = new File("C://Users//Alexander//IdeaProjects//Lab4//Lab4Power.xlsx");
        FileInputStream fis;
        XSSFWorkbook myWorkBook = null;
        try {
            fis = new FileInputStream(myFile);
            myWorkBook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        XSSFSheet mySheet = myWorkBook.getSheetAt(2);


        AgentConfig object;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(AgentConfig.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Object obj = jaxbUnmarshaller.unmarshal(new File(args));
            object = (AgentConfig) obj;

            for (AgentDefinition obb : object.getAgentDefinitions()) {

                Iterator<Row> rowIterator = mySheet.iterator();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getStringCellValue().equals(obb.getAgentName())) {
                        int colin = cell.getColumnIndex();

                        // Traversing over each row of XLSX file
                        while (rowIterator.hasNext()) {
                            Row row1 = rowIterator.next();
                            Cell cell1 = row1.getCell(colin);

                            if (cell1.getCellType() == CellType.NUMERIC) {
                                obb.getFilepath().add(cell1.getNumericCellValue());
                            }
                        }
                    }
                }
            }

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(object, new File(args));

        } catch (JAXBException | ClassCastException e) {
            e.printStackTrace();
        }
    }

}
