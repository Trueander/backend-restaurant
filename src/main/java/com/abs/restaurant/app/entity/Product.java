package com.abs.restaurant.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table( name = "PRODUCT_TBL" )
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "name" )
    private String name;

    @Column( name = "description" )
    private String description;

    @Column( name = "price" )
    private BigDecimal price;

    @Column( name = "stock" )
    private Integer stock;

    @Column( name = "image_url")
    private String imageUrl;

    @Column( name = "is_active" )
    private Boolean isActive;

    @Column( name = "discount" )
    private Double discount;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "category_id" )
    private Category category;


}
