package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Requisito;
import com.gamq.ambiente.model.TipoParametro;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.RequisitoRepository;
import com.gamq.ambiente.repository.TipoParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntidadHelperServiceImpl {
    @Autowired
    private InspeccionRepository inspeccionRepository;
    @Autowired
    private RequisitoRepository requisitoRepository;
    @Autowired
    private TipoParametroRepository tipoParametroRepository;

    public Inspeccion obtenerInspeccion(String inspeccionUuid) {
        Optional<Inspeccion> optionalInspeccion = inspeccionRepository.findByUuid(inspeccionUuid);
        if(optionalInspeccion.isEmpty()){
            throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "el uuid de la inspeccion no existe");
        }
        return optionalInspeccion.get();
    }

    public Requisito obtenerRequisito(String requisitoUuid){
        Optional<Requisito> optionalRequisito = requisitoRepository.findByUuid(requisitoUuid);
        if (optionalRequisito.isEmpty()){
            throw  new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "el uuid del requisito no existe");
        }
        return optionalRequisito.get();
    }

    public TipoParametro obtenerTipoParametro(String tipoParametroUuid) {
        Optional<TipoParametro> optionalTipoParametro = tipoParametroRepository.findByUuid(tipoParametroUuid);
        if(optionalTipoParametro.isEmpty()){
            throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "el uuid del tipo de parametro para detalle inspeccion no existe");
        }
        return optionalTipoParametro.get();
    }
}
