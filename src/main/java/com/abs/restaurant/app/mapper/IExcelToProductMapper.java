package com.abs.restaurant.app.mapper;

import com.abs.restaurant.app.entity.Product;
import org.apache.poi.ss.usermodel.Row;

public interface IExcelToProductMapper {

    Product mapFromWorkBookToProduct(Row row);
}
