package gestaousuario.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    private final long TRES_HORAS_EM_MILISEGUNDOS = 10800000L;
    private String chaveSecreta = "16c321a9-3bde-4c06-b63a-229c1b187ba5";

    public String gerarToken(String email) {

        Date dataAtual = new Date();
        long millisegundoDaExpiracao = dataAtual.getTime() + TRES_HORAS_EM_MILISEGUNDOS;
        Date dataExpiracao = new Date(millisegundoDaExpiracao);

        String token = Jwts.builder()
                .subject(email)
                .expiration(dataExpiracao)
                .signWith(getSigningKey())
                .compact();

        return token;

    }

    private Key getSigningKey() {
        byte[] keyBytes = this.chaveSecreta.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
