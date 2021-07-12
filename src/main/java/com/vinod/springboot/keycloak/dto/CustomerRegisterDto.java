package com.vinod.springboot.keycloak.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisterDto implements Serializable {

    private String name;
    private String emailId;
    private String password;
    private String address;
}
