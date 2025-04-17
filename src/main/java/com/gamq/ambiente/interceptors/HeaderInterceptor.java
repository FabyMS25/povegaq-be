package com.gamq.ambiente.interceptors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component("headerInterceptor")
public class HeaderInterceptor implements HandlerInterceptor {
    @Value("${jwtSecret}")
    private String secretKey;
    @Value("${messageapi}")
    String messageapi;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = recuperarToken(request);

        // if (token == null || !token.startsWith("Bearer ")){
        if (token == null) {
            return true; //ojo2024
            // throw new UnAuthorizedException("401-NOAUTORIZADO",HttpStatus.UNAUTHORIZED,"exception from interceptor");
        }
        if (isValidJWT(token)) {
            // Si el JWT es válido, puedes permitir que la solicitud continue

            //Este es un mEétodo muy Util cuando el tiempo de vencimiento del token es corto.
            // response.setHeader("Authorization", token);
            // response.setHeader("Access-control-expose-headers", "Authorization");
            return true;
        } else {
            // Si el JWT no es valido, puedes enviar una respuesta de error o redireccionar a una pagina de inicio de sesion
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no válido");
            return false;
        }
    }

    private boolean isValidJWT(String token) {
        try {
            // Implementa aquí la logica para validar el JWT
            final String username = getUsernameFromToken(token);

            RestTemplate plantilla = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + (token.trim()));

            Map<String, String> datos = new HashMap<String, String>();
            datos.put("token", token);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(datos, headers);

            String url = messageapi + username;
            ResponseEntity<String> response = plantilla.exchange(url, HttpMethod.GET, request, String.class);
            HttpStatus httpStatus = response.getStatusCode();
            // System.out.println(response.getBody());

            if (httpStatus.is2xxSuccessful()) {
                boolean igualUsuario = response.getBody().contains(username);
                return (igualUsuario && !isTokenExpired(token));
            } else {
                return false;
            }
        } catch (MalformedJwtException malformedJwtException) {
            throw new MalformedJwtException("token mal formado");
        }
        catch ( ExpiredJwtException expiredJwtException ) {
            throw  new MalformedJwtException("token expirado");
        }
       /* } catch (RestClientResponseException resExc) {
            System.out.println(resExc.getRawStatusCode());
            System.out.println(resExc.getMessage());
            throw new UnAuthorizedException()
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }

    private Boolean isTokenExpired(String token) {
        return obtenerExpiracionToken(token).before(new Date());
    }

    public Date obtenerExpiracionToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            int startIndex = "Bearer ".length();
            if (authorizationHeader.length() > startIndex) {
                return authorizationHeader.substring(startIndex);
            }
        }
        return null;
    }

    private boolean verificarSession(String token) {
        boolean res = false;
   /*     try {


            RestTemplate plantilla = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYWN0dXJhZG9yY2VudHJhbHAxIiwiaWF0IjoxNjk1MjQwNzgxLCJleHAiOjE2OTU4NDU1ODF9.k_arcmAbvw70lx0You94hURoNe5VIcS1DfCxK76LqhROW-x0N8c5Vb2PN7Lda5Eeo_pGFEagJhvVxosgNJ1tng");

            Map<String, String> datos = new HashMap<String, String>();
            datos.put("token", token);
            //datos.put("password", "12345678");
            HttpEntity<Map<String, String>> request = new HttpEntity<>(datos, headers);

            //String url = "http://181.177.143.185:9090/fisqui/api/v1/entorno/estado";
            //ResponseEntity<String> response = plantilla.exchange(url, HttpMethod.POST, request, String.class);

            String url = "http://181.177.143.185:8080/api/listar/usuarios";
            ResponseEntity<String> response = plantilla.exchange(url, HttpMethod.GET, request, String.class);
            //System.out.println(response); // PRINT RESULTADO OJO
            HttpStatus httpStatus = response.getStatusCode();
            if(httpStatus.is2xxSuccessful()) {
              //  System.out.println(response.getBody());
                res = true;
            }else {
              //  System.out.println(response.getBody());
            }

        }catch (RestClientResponseException resExc) {
            System.out.println(resExc.getRawStatusCode());
            System.out.println(resExc.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } */
        return true;
        //return res;
        ///modificado por edgar
        //return true; //por el momento va pasar con true
    }
}

