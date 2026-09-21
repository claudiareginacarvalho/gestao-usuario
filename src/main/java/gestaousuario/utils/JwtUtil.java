package gestaousuario.utils;

import gestaousuario.entity.Perfil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtil {

    private final long TRES_HORAS_EM_MILISEGUNDOS = 10800000L;

    @Value("${app.jwt.secret}")
    private String chaveSecreta;

    public String gerarToken(Long id, String email, Perfil perfil) {

        Date dataAtual = new Date();
        long millisegundoDaExpiracao = dataAtual.getTime() + TRES_HORAS_EM_MILISEGUNDOS;
        Date dataExpiracao = new Date(millisegundoDaExpiracao);

        String token = Jwts.builder()
                .subject(email)
            .claim("id", id)
            .claim("perfil", perfil.name())
                .expiration(dataExpiracao)
                .signWith(getSigningKey())
                .compact();

        return token;

    }

    public String extrairEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public DadosToken validarToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Token ausente ou inválido");
        }

        String token = authorizationHeader.substring(7).trim();
        if (token.isEmpty()) {
            throw new IllegalArgumentException("Token ausente ou inválido");
        }

        Claims claims = parseToken(token);
        Object idClaim = claims.get("id");
        String email = claims.getSubject();
        String perfilClaim = claims.get("perfil", String.class);

        if (!(idClaim instanceof Number) || email == null || perfilClaim == null) {
            throw new IllegalArgumentException("Token ausente ou inválido");
        }

        return new DadosToken(idClaim instanceof Long ? (Long) idClaim : ((Number) idClaim).longValue(),
            email, Perfil.valueOf(perfilClaim));
    }

    private Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.chaveSecreta.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
