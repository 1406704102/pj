package com.pangjie.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
}


