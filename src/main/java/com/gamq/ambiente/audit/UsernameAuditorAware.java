package com.gamq.ambiente.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class UsernameAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String usuarioActual = "";
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (attr != null) {
            if (attr.getRequest() != null) {
                if (attr.getRequest().getSession() != null) {
                    usuarioActual = attr.getRequest().getSession().getAttribute("usuario").toString();
                }
            }
        }
        return Optional.of(usuarioActual);
    }
}