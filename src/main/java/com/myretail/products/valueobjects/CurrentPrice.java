package com.myretail.products.valueobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter @ToString @NoArgsConstructor
public class CurrentPrice {

    public BigDecimal value;
    public String currency_code;
}
