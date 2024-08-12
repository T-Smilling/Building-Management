package com.javaweb.model.request;

import com.javaweb.model.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest extends BaseDTO {
    private String fullName;
    private String phone;
    private String email;
    private String demand;
    private String status;
}
