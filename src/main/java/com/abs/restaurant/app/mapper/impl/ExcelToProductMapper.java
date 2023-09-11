package com.abs.restaurant.app.mapper.impl;

import com.abs.restaurant.app.entity.Category;
import com.abs.restaurant.app.entity.Product;
import com.abs.restaurant.app.mapper.IExcelToProductMapper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExcelToProductMapper implements IExcelToProductMapper {

    @Override
    public Product mapFromWorkBookToProduct(Row row) {
        DataFormatter dataFormatter = new DataFormatter();
        Product product = new Product();
        product.setName(parseFromCellToString(dataFormatter.formatCellValue(row.getCell(0))));
        product.setDescription(parseFromCellToString(dataFormatter.formatCellValue(row.getCell(1))));
        product.setImageUrl(parseFromCellToString(dataFormatter.formatCellValue(row.getCell(2))));
        product.setPrice(parseFromCellToBigDecimal(dataFormatter.formatCellValue(row.getCell(3))));
        product.setStock(parseFromCellToInteger(dataFormatter.formatCellValue(row.getCell(4))));
        Category category = new Category();
        category.setId(parseFromCellToLong(dataFormatter.formatCellValue(row.getCell(5))));
        product.setCategory(category);
        return product;
    }

    private String parseFromCellToString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value;
    }

    private BigDecimal parseFromCellToBigDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        BigDecimal result;
        try {
            result = new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }

        return result;
    }

    private Integer parseFromCellToInteger(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Long parseFromCellToLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
