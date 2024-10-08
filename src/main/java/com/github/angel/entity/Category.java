package com.github.angel.entity;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Category")
@Table(name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Category implements Serializable {
    @Serial
    private static final long serialVersionUID = 182715381639163161L;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="category_id")
    private Long categoryId;
    @NotBlank(message = "Category name is required")
    @Size(max = 50, message = "Category name must not exceed 50 characters")
    @Column(length = 50, nullable = false, unique = true)
    private String name;
    @Size(max = 200, message = "Description must not exceed 200 characters")
    @Column(length = 200)
    @Lob
    @JdbcTypeCode(java.sql.Types.LONGVARCHAR)
    private String description;
    @Type(value = ListArrayType.class, parameters = {
        @org.hibernate.annotations.Parameter(
                name = ListArrayType.SQL_ARRAY_TYPE,
                value = "products"
        )
    })
    @Column(name = "products", columnDefinition = "products[]")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity =  Product.class, mappedBy = "category", orphanRemoval = true)
    private List<Product> products;

    public Category() {
    }

    public Category(Long categoryId, String description, String name, List<Product> products) {
        this.categoryId = categoryId;
        this.description = description;
        this.name = name;
        this.products = products;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public @NotBlank(message = "Category name is required") @Size(max = 50, message = "Category name must not exceed 50 characters") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Category name is required") @Size(max = 50, message = "Category name must not exceed 50 characters") String name) {
        this.name = name;
    }

    public @Size(max = 150, message = "Description must not exceed 150 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 150, message = "Description must not exceed 150 characters") String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Category{");
        sb.append("categoryId=").append(categoryId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", products=").append(products);
        sb.append('}');
        return sb.toString();
    }
}
