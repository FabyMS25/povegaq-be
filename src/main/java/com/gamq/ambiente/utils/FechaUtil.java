package com.gamq.ambiente.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class FechaUtil {

    private static final int GESTION_ACTUAL = LocalDate.now().getYear();
    public static Date calcularFechaVencimiento(Date fechaEntrega, int cantidadAnual) {
        // Convertir Date a LocalDate
        LocalDate localDate = fechaEntrega.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        //int nuevoAnio = localDate.getYear() + 2;
        int nuevoAnio = localDate.getYear() + cantidadAnual;
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
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        calendario.add(Calendar.DAY_OF_YEAR, dias); // suma X días
        return calendario.getTime();
    }

    private static final Set<LocalDate> FERIADOS = Set.of(
            LocalDate.of(GESTION_ACTUAL, 1, 1),   // Año Nuevo
            LocalDate.of(GESTION_ACTUAL, 1, 22),   // dia del estado plurinacional
            LocalDate.of(GESTION_ACTUAL, 3, 3),   // carnaval
            LocalDate.of(GESTION_ACTUAL, 3, 4),   // carnaval
            LocalDate.of(GESTION_ACTUAL, 4, 18),  // viernes santos
            LocalDate.of(GESTION_ACTUAL, 5, 1),   // Día del Trabajo
            LocalDate.of(GESTION_ACTUAL, 6, 19),  // Corpus Christi
            LocalDate.of(GESTION_ACTUAL, 6, 21),  // Año Nuevo Andino
            LocalDate.of(GESTION_ACTUAL, 9, 12),  // feriado aniversario Quillacollo
            LocalDate.of(GESTION_ACTUAL, 9, 14),  // feriado aniversario de Cochabamba
            LocalDate.of(GESTION_ACTUAL, 11, 2),  // Día de todos santos
            LocalDate.of(GESTION_ACTUAL, 12, 25)  // Navidad
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

    public static boolean esDiaHabil(LocalDate fecha) {
        DayOfWeek dia = fecha.getDayOfWeek();
        return dia != DayOfWeek.SATURDAY &&
                dia != DayOfWeek.SUNDAY &&
                !FERIADOS.contains(fecha);
    }

    //fechaInicial -> fecha notificacion o fechainfraccion
    public boolean plazoVencido(LocalDate fechaInicial, Integer plazoEnDias) {
        LocalDate hoy = LocalDate.now();
        long diasHabiles = contarDiasHabiles(fechaInicial, hoy);
        return diasHabiles > plazoEnDias;  //hardcode
    }

    // Ajusta la fecha al inicio del día (00:00:00.000)
    public static Date ajustarFechaInicioDia(Date fecha, ZoneId zona) {
        //ZoneId zona = ZoneId.of(zonaHorario);
        return Date.from(
                fecha.toInstant()
                        .atZone(zona)
                        .toLocalDate()
                        .atStartOfDay(zona)
                        .toInstant()
        );
    }

    // Ajusta la fecha al final del día (23:59:59.999)
    public static Date ajustarFechaFinDia(Date fecha, ZoneId zona) {
       // ZoneId zona = ZoneId.of(zonaHorario);
        return Date.from(
                fecha.toInstant()
                        .atZone(zona)
                        .toLocalDate()
                        .atTime(23, 59, 59, 999_000_000) // hasta el último milisegundo
                        .atZone(zona)
                        .toInstant()
        );
    }

    public static Date obtenerDiaHabilMasCercano(Date fechaVencimiento, ZoneId zona) {
        LocalDate fecha = convertirADia(fechaVencimiento, zona);

        if (esDiaHabil(fecha)) {
            return  convertirADate(fecha,zona);
        }
        LocalDate diaHabilAnterior = buscarDiaHabilAnterior(fecha);
        LocalDate diaHabilSiguiente = buscarDiaHabilSiguiente(fecha);

        LocalDate diaMasCercano = elegirDiaMasCercano(fecha, diaHabilAnterior, diaHabilSiguiente);

        return convertirADate(diaMasCercano, zona);
    }

    private static LocalDate convertirADia(Date fecha, ZoneId zona) {
        return fecha.toInstant().atZone(zona).toLocalDate();
    }

    private static Date convertirADate(LocalDate fecha, ZoneId zona) {
        return Date.from(fecha.atStartOfDay(zona).toInstant());
    }

    private static LocalDate buscarDiaHabilAnterior(LocalDate desde) {
        LocalDate actual = desde.minusDays(1);
        while (!esDiaHabil(actual)) {
            actual = actual.minusDays(1);
        }
        return actual;
    }

    private static LocalDate buscarDiaHabilSiguiente(LocalDate desde) {
        LocalDate actual = desde.plusDays(1);
        while (!esDiaHabil(actual)) {
            actual = actual.plusDays(1);
        }
        return actual;
    }

    private static LocalDate elegirDiaMasCercano(LocalDate original, LocalDate anterior, LocalDate siguiente) {
        long distanciaAnterior = Math.abs(ChronoUnit.DAYS.between(original, anterior));
        long distanciaSiguiente = Math.abs(ChronoUnit.DAYS.between(original, siguiente));
        return distanciaAnterior <= distanciaSiguiente ? anterior : siguiente;
    }
}
