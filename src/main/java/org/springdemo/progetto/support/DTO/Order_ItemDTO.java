package org.springdemo.progetto.support.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springdemo.progetto.entities.Order_bucket;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Data
public class Order_ItemDTO {
    @NotNull
    private int order_item_id;
    @NotNull
    private int quantity;
    @NotNull
    private ProductDTO product;
    private Order_bucket order_bucket;
}
