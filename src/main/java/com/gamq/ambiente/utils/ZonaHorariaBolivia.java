package com.gamq.ambiente.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ZonaHorariaBolivia {
    //public static final ZoneId BOLIVIA = ZoneId.of("America/La_Paz");

    private final ZoneId zonaId;

    public ZonaHorariaBolivia(@Value("${spring.jackson.time-zone}") String zona) {
        this.zonaId = ZoneId.of(zona);
    }

    public ZoneId getZonaId() {
        return zonaId;
    }
}
