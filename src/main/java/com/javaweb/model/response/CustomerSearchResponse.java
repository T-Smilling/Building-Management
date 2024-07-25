package com.javaweb.model.response;

import com.javaweb.model.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSearchResponse extends BaseDTO
{
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String note;
    private String status;
}
