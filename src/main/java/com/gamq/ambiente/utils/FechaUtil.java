package com.gamq.ambiente.utils;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class FechaUtil {
    private static final int GESTION_ACTUAL = LocalDate.now().getYear();
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

    private static final Set<LocalDate> FERIADOS = Set.of(
            LocalDate.of(GESTION_ACTUAL, 1, 1),   // Año Nuevo
            LocalDate.of(GESTION_ACTUAL, 5, 1),   // Día del Trabajo
            LocalDate.of(GESTION_ACTUAL, 6, 21),  // Año Nuevo Andino
            LocalDate.of(GESTION_ACTUAL, 8, 6),   // Día de la Independencia
            LocalDate.of(GESTION_ACTUAL, 12, 25)  // Navidad
            // Agrega más feriados nacionales y regionales aquí
    );

    public long contarDiasHabiles(LocalDate fechaInicio, LocalDate fechaFin) {
        long diasHabiles = 0;
        LocalDate actual = fechaInicio;

        while (!actual.isAfter(fechaFin)) {
            if (esDiaHabil(actual)) {
                diasHabiles++;
            }
            actual = actual.plusDays(1);
        }

        return diasHabiles;
    }

    public boolean esDiaHabil(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return dia != DayOfWeek.SATURDAY &&
                dia != DayOfWeek.SUNDAY &&
                !FERIADOS.contains(fecha);
    }

    //fechaInicial -> fecha notificacion o fechainfraccion
    public boolean plazoVencido(LocalDate fechaInicial) {
        LocalDate hoy = LocalDate.now();
        long diasHabiles = contarDiasHabiles(fechaInicial, hoy);
        return diasHabiles > 15;
    }
}
