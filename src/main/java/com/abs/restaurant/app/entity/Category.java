package com.abs.restaurant.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@Table( name = "CATEGORY_TBL" )
@Entity
public class Category extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 3, message = "needs at least 3 characters.")
    @NotEmpty(message = "is required.")
    @Column( name = "name" )
    private String name;

    @NotEmpty(message = "is required.")
    @Column( name = "icon" )
    private String icon;

    @NotNull(message = "is required.")
    @Column( name = "is_active" )
    private Boolean isActive;

}
