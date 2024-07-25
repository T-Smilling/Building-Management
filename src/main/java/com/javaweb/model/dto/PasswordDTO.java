package com.javaweb.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDTO extends BaseDTO<PasswordDTO> {

    private static final long serialVersionUID = 8835146939192307340L;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
