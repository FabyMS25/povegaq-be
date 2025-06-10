package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.LimiteEmisionDto;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.*;
import com.gamq.ambiente.repository.DetalleInspeccionRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.TipoParametroRepository;
import com.gamq.ambiente.service.ComparacionEmisionService;
import com.gamq.ambiente.service.InspeccionService;
import com.gamq.ambiente.service.LimiteEmisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ComparacionEmisionServiceImpl implements ComparacionEmisionService {
    @Autowired
    LimiteEmisionService limiteEmisionService;
    @Autowired
    InspeccionService inspeccionService;
    @Autowired
    InspeccionRepository inspeccionRepository;
    @Autowired
    TipoParametroRepository tipoParametroRepository;
    @Autowired
    DetalleInspeccionRepository detalleInspeccionRepository;

    @Override
    public void validarInspeccion(String inspeccionUuid) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(inspeccionUuid);
        if(inspeccionOptional.isEmpty()){
            throw new ResourceNotFoundException("Inspeccion", "uuid", inspeccionUuid);
        }
        if(inspeccionOptional.get().getDetalleInspeccionList().size() <=0){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,"No existe Datos en el Detalle inspeccion");
        }

        boolean resultadoGeneral = true;
        for (DetalleInspeccion detalle : inspeccionOptional.get().getDetalleInspeccionList()) {
            Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByUuid(detalle.getTipoParametro().getUuid());
            DatoTecnico datoTecnico = inspeccionOptional.get().getVehiculo().getDatoTecnico();

            List<LimiteEmisionDto> limites =  limiteEmisionService.buscarLimitesPorFiltro(tipoParametroOptional.get(), datoTecnico, inspeccionOptional.get().getAltitud());

            if (limites.isEmpty()) {
                throw new RuntimeException("No se encontró límite de emisión para el parámetro: " + tipoParametroOptional.get().getNombre());
            }

            if (limites.size() > 1) {
                throw new RuntimeException("Existen múltiples límites de emisión para el parámetro: " + tipoParametroOptional.get().getNombre() +
                        ". Revise los datos técnicos o la configuración de límites.");
            }

            LimiteEmisionDto limite = limites.get(0);
            BigDecimal valorMedido = detalle.getValor();
            if (valorMedido.compareTo(limite.getLimite()) > 0) {
                detalle.setResultadoParcial(false);
                detalle.setLimitePermisible(limite.getLimite());
                resultadoGeneral = false;
            } else {
                detalle.setResultadoParcial(true);
                detalle.setLimitePermisible(limite.getLimite());
            }
            detalleInspeccionRepository.save(detalle);
        }
        inspeccionRepository.save(inspeccionOptional.get().setResultado(resultadoGeneral));
    }
}
