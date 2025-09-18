package com.solvians.showcase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class CertificateUpdate {

    private long timestamp;
    private String isin;
    private double bidPrice;
    private int bidSize;
    private double askPrice;
    private int askSize;

}
