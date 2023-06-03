package com.abs.restaurant.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @Size(min = 3, message = "")
    @NotEmpty(message = "is required.")
    @Column( name = "name" )
    private String name;

    @NotEmpty(message = "is required.")
    @Column( name = "description" )
    private String description;

    @NotNull(message = "is required.")
    @Column( name = "price" )
    private BigDecimal price;

    @NotNull(message = "is required.")
    @Column( name = "stock" )
    private Integer stock;

    @Column( name = "image_url" )
    private String imageUrl;

    @NotNull(message = "is required.")
    @Column( name = "is_active" )
    private Boolean isActive;

    @Column( name = "discount" )
    private Double discount;

    @NotNull(message = "is required.")
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn( name = "category_id" )
    private Category category;


}
