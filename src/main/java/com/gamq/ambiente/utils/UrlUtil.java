package com.gamq.ambiente.utils;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component()
public class UrlUtil {
    public static String construirBaseUrl(HttpServletRequest request) {
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isEmpty()) {
            scheme = request.getScheme(); //por ejemplo recupera esquema: http / https
        }

        String host = request.getHeader("X-Forwarded-Host");
        if (host == null || host.isEmpty()) {
            host = request.getServerName(); //por ejemplo recupera el host: localhost
        }

        String port = request.getHeader("X-Forwarded-Port");
        if (port == null || port.isEmpty()) {
            port = String.valueOf(request.getServerPort()); //por ejemplo recupera port: 8080
        }

        // Evita mostrar el puerto si es el estándar
        boolean esPuertoDefault = (scheme.equals("http") && port.equals("80")) || (scheme.equals("https") && port.equals("443"));
        String portPart = esPuertoDefault ? "" : ":" + port;

        return scheme + "://" + host + portPart;
    }
}
