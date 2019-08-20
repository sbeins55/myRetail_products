package com.myretail.products.valueobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter @ToString @NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    public String Id;

    public Long redskyId;

    public String name;

    public CurrentPrice current_price;
}

