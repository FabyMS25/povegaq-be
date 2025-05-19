package com.gamq.ambiente.validators;

import javax.validation.Payload;

public @interface ValidYearFabricacion {
    String message() default "El año de fabricación debe ser válido y no mayor al año actual";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
