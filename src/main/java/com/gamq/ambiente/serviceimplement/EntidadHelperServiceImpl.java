package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Requisito;
import com.gamq.ambiente.model.TipoCombustible;
import com.gamq.ambiente.model.TipoParametro;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.RequisitoRepository;
import com.gamq.ambiente.repository.TipoCombustibleRepository;
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
    @Autowired
    private TipoCombustibleRepository tipoCombustibleRepository;

    public Inspeccion obtenerInspeccion(String inspeccionUuid) {
        return inspeccionRepository.findByUuid(inspeccionUuid)
                .orElseThrow(()-> new ResourceNotFoundException("Inspeccion","uuid", inspeccionUuid));
        //Optional<Inspeccion> optionalInspeccion = inspeccionRepository.findByUuid(inspeccionUuid);
        //if(optionalInspeccion.isEmpty()){
        //    throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "el uuid de la inspeccion no existe");
        //}
        //return optionalInspeccion.get();
    }

    public Requisito obtenerRequisito(String requisitoUuid){
        return requisitoRepository.findByUuid(requisitoUuid)
                .orElseThrow(()-> new ResourceNotFoundException("Requisito", "uuid", requisitoUuid));
       // Optional<Requisito> optionalRequisito = requisitoRepository.findByUuid(requisitoUuid);
        //if (optionalRequisito.isEmpty()){
        //    throw  new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "el uuid del requisito no existe");
        //}
        //return optionalRequisito.get();
    }

    public TipoParametro obtenerTipoParametro(String tipoParametroUuid) {
        return tipoParametroRepository.findByUuid(tipoParametroUuid)
                .orElseThrow(()-> new ResourceNotFoundException("Tipo Parametro", "uuid", tipoParametroUuid));
    }
    public TipoCombustible obtenerTipoCombustible(String tipoCombustibleUuid){
        return tipoCombustibleRepository.findByUuid(tipoCombustibleUuid)
                .orElseThrow(()-> new ResourceNotFoundException("Tipo Combustible","uuid", tipoCombustibleUuid));
    }
}
