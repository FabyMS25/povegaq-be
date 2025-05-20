package com.gamq.ambiente.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class FechaUtil {
    public static Date calcularFechaVencimiento(Date fechaEntrega) {
        // Convertir Date a LocalDate
        LocalDate localDate = fechaEntrega.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        int nuevoAnio = localDate.getYear() + 2;
        int mes = localDate.getMonthValue();
        int dia = localDate.getDayOfMonth();

        // Ajustar si era 29 de febrero y el nuevo año no es bisiesto
        if (mes == 2 && dia == 29 && !Year.isLeap(nuevoAnio)) {
            dia = 28;
        }

        LocalDate fechaVencimientoLD = LocalDate.of(nuevoAnio, mes, dia);

        // Convertir de vuelta a Date
        return Date.from(fechaVencimientoLD.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date sumarUnAnio(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.YEAR, 1);  // Suma 1 año
        return calendar.getTime();
    }

    public static Date sumarDias(Date fecha, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DAY_OF_YEAR, dias); // suma X días
        return cal.getTime();
    }
}
