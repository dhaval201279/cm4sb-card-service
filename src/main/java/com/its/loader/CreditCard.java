package com.its.loader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard implements Serializable {
    private String issuingNetwork;
    private String cardNumber;
    private String name;
    private String address;
    private String country;
    private String cvv;
    private String expiryDate;
}
