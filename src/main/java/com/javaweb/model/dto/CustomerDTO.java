package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends BaseDTO
{
    private Long id;
    private String fullName;
    private String managementStaff;
    private String customerPhone;
    private String email;
    private String note;
    private String status;
    private String companyName;
}
