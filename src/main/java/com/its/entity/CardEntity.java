package com.its.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardEntity implements Serializable {
    private String issuingNetwork;
    private String cardNumber;
    private String name;
    private String address;
    private String country;
    private String cvv;
    private String expiryDate;
}
