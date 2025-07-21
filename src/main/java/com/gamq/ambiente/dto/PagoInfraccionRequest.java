package com.gamq.ambiente.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PagoInfraccionRequest {
    private String uuidInfraccion;
    private String numeroTasa;
    private Date fechaPago;
}
