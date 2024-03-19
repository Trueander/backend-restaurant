package com.abs.restaurant.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Table( name = "categories" )
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
