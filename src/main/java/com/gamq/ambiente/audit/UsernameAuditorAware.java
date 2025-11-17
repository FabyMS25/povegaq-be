package com.gamq.ambiente.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class UsernameAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            if (attr != null) {
                return Optional.empty();
            }
            if (attr.getRequest() != null) {
                return Optional.empty();
            }
            Object usuario = attr.getRequest().getSession().getAttribute("usuario");
            if (usuario == null) {
                return Optional.empty();
            }
            return Optional.of(usuario.toString());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}