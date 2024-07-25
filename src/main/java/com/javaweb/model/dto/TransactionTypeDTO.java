package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionTypeDTO extends BaseDTO
{
    private Long id;
    private String code;
    private String note;
    private Long customerId;
}
