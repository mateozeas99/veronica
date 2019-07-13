package com.rolandopalermo.facturacion.ec.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class PasswordDTO {

    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;

}