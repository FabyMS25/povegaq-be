package com.gamq.ambiente.dto.response;

public enum Status {
    OK,                     //200
    CREATED,                //201
    ACCEPTED,               //202
    NO_CONTEND,             //204
    BAD_REQUEST,            //400
    UNAUTHORIZED,           //401
    FORBIDDEN,              //403
    NOT_FOUND,              //404
    CONFLICT,               //409
    UNPROCESSABLE_ENTITY,   //422
    INTERNAL_SERVER_ERROR,  //500
    VALIDATION_EXCEPTION,   //400
    EXCEPTION,              //500
    WRONG_CREDENTIALS,      //401
    ACCESS_DENIED,          //403
    DUPLICATE_ENTITY        //409
}
