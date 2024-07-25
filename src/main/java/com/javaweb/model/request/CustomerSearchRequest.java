package com.javaweb.model.request;

import com.javaweb.model.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerSearchRequest extends BaseDTO
{
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private Long staffId;


}
