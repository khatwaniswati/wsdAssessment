package com.solvians.showcase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class CertificateUpdate {

    private long timestamp;
    private String isin;
    private BigDecimal bidPrice;
    private int bidSize;
    private BigDecimal askPrice;
    private int askSize;

}
