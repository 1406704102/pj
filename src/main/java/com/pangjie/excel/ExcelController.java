package com.pangjie.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelController {
    public ResponseEntity<Object> importExcel(MultipartFile file) throws IOException, InvalidFormatException {
        List<List<Object>> lists = new ArrayList<>();
        WorkbookFactory.create(file.getInputStream()).getSheetAt(0).forEach(cells -> {
            //...
        });
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    public static void main(String[] args) {
        File file = new File("D:\\OtherTool\\WeChat\\WeChat Files\\pangjie123456789\\FileStorage\\File\\2023-05\\pppp.xlsx");
        try {
            FileInputStream fis = new FileInputStream(file);
            WorkbookFactory.create(fis).getSheetAt(0).forEach(cells -> {
                //...
            });
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}


