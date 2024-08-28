package org.ecom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name should not exceed 100 characters")
    private String productName;

    @NotBlank(message = "Product description is required")
    @Size(max = 1000, message = "Product description should not exceed 1000 characters")
    private String productDescription;

    @NotNull(message = "Product rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be greater than zero")
    private Double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Available in stock is required")
    @Min(value = 0, message = "Available in stock must be 0 or more")
    private Integer availableInStock;

    @JsonIgnore
    private LocalDateTime deleted_at;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime created_at;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updated_at;
    

}
