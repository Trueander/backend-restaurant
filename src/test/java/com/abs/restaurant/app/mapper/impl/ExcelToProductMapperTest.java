package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Product;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
public class ExcelToProductMapperTest {

    private ExcelToProductMapper excelToProductMapper;

    @BeforeEach
    public void init() {
        excelToProductMapper = new ExcelToProductMapper();
    }

    @Test
    public void mapFromWorkBookToProductFullTest() throws IOException {
        InputStream inputStream = new ClassPathResource("products.xlsx").getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        Product product = excelToProductMapper.mapFromWorkBookToProduct(row);
        assertNotNull(product);
        assertNotNull(product.getName());
        assertNotNull(product.getDescription());
        assertNotNull(product.getStock());
        assertNotNull(product.getPrice());
        assertNotNull(product.getImageUrl());
        assertNotNull(product.getCategory());
        assertNotNull(product.getCategory().getId());
    }

    @Test
    public void mapFromWorkBookToProductEmptyRowTest() throws IOException {
        InputStream inputStream = new ClassPathResource("products.xlsx").getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        row.getCell(0).setCellValue("");
        row.getCell(1).setCellValue("");
        row.getCell(2).setCellValue("");
        row.getCell(3).setCellValue("");
        row.getCell(4).setCellValue("");
        row.getCell(5).setCellValue("");

        Product product = excelToProductMapper.mapFromWorkBookToProduct(row);

        assertNotNull(product);
        assertNull(product.getName());
        assertNull(product.getDescription());
        assertNull(product.getStock());
        assertNull(product.getPrice());
        assertNull(product.getImageUrl());
        assertNotNull(product.getCategory());
        assertNull(product.getCategory().getId());
    }

    @Test
    public void mapFromWorkBookToProductNumberFormatExceptionTest() throws IOException {
        InputStream inputStream = new ClassPathResource("products.xlsx").getInputStream();
        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1);
        row.getCell(3).setCellValue("d");
        row.getCell(4).setCellValue("d");
        row.getCell(5).setCellValue("d");

        Product product = excelToProductMapper.mapFromWorkBookToProduct(row);

        assertNotNull(product);
        assertNull(product.getStock());
        assertNull(product.getPrice());
        assertNotNull(product.getCategory());
        assertNull(product.getCategory().getId());
    }

}
