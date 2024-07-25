package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildingDTO extends BaseDTO {
    private Long id;
    private String name;
    private String address;
    private Long floorArea;
    private String district;
    private String ward;
    private String street;
    private Long numberOfBasement;
    private String direction;
    private Long level;
    private String rentArea;
    private Long rentPrice;
    private String managerName;
    private String managerPhone;
    private List<String> typeCode;
    private String structure;
    private String rentPriceDescription;
    private String serviceFee;
    private String carFee;
    private String motorbikeFee;
    private String extraFee;
    private String electricFee;
    private String deposit;
    private String payment;
    private String rentTime;
    private String decorationTime;
    private String brokerageFee;
    private String note;
    private String image;
    private String imageBase64;
    private String imageName;
}