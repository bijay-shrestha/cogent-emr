package com.cogent.admin.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.stream.IntStream;

import static com.cogent.admin.constants.ExcelConstants.*;

@Slf4j
public class ExcelComposer {
    private static void createHeader(Workbook workbook, Sheet sheet, int columnLength, String[] columns) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) FONT_SIZE_FOR_HEADER);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(ROW_FOR_HEADER);

        IntStream.range(0, columnLength).forEach(o -> {
            Cell cell = headerRow.createCell(o);
            cell.setCellValue(columns[o]);
            cell.setCellStyle(headerCellStyle);
        });
    }

    private static Picture addPicture(Workbook workbook, Sheet sheet, int columnLength) throws IOException {
        InputStream imageLocation = new FileInputStream(IMAGE_LOCATION);
        byte[] bytes = IOUtils.toByteArray(imageLocation);
        int picture_id = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
        imageLocation.close();
        CreationHelper helper = workbook.getCreationHelper();
        Drawing drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setDx1(SET_X_COORDINATE_IN_FIRST_CELL);
        anchor.setCol1(SET_COLUMN.apply(columnLength));
        anchor.setRow1(SET_ROW);
        return drawing.createPicture(anchor, picture_id);
    }

    private static void mapRowWithData(Sheet sheet, int columnLength, List<Object[]> list) {

        final int[] rowNum = {ROW_NUM};
        final int[] sn = {SERIAL_NUMBER};

        list.forEach(objects -> {
                    Row row = sheet.createRow(rowNum[0]++);
                    row.createCell(CELL_FOR_SN).setCellValue(sn[0]++);
                    IntStream.range(1, columnLength).forEach(o -> {
                        row.createCell(o).setCellValue(objects[o].toString());
                        sheet.autoSizeColumn(o);
                    });
                }
        );
    }

    private static Workbook createWorkbook(String[] HEADERS, String WORKBOOK_NAME, List<Object[]> rowList)
            throws IOException {

        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet(WORKBOOK_NAME);

        Picture picture = addPicture(workbook, sheet, HEADERS.length);
        picture.getPreferredSize(COLUMN_SIZE, ROW_SIZE);

        createHeader(workbook, sheet, HEADERS.length, HEADERS);
        mapRowWithData(sheet, HEADERS.length, rowList);

        /*TEST*/
//        FileOutputStream fileOut = new FileOutputStream
//                ("./admin-service/src/main/resources/admin-category.xlsx");
//        workbook.write(fileOut);
        return workbook;
    }

    public static void downloadFile(HttpServletResponse response, ByteArrayOutputStream out) {
        try {
            OutputStream outputStream = response.getOutputStream();
            out.writeTo(outputStream);
            outputStream.close();
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static ByteArrayOutputStream createExcel(String[] HEADERS,
                                                    String WORKBOOK_NAME,
                                                    List<Object[]> rowList) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        createWorkbook(HEADERS, WORKBOOK_NAME, rowList).write(out);
        return out;
    }

}
