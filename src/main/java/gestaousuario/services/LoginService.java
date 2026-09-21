package gestaousuario.services;

import gestaousuario.dto.CredenciaisDTO;
import gestaousuario.dto.TokenDTO;
import gestaousuario.entity.Usuario;
import gestaousuario.exceptions.FalhaLogin;
import gestaousuario.repository.UsuarioRepository;
import gestaousuario.utils.HashUtil;
import gestaousuario.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    //atributo repositorio
    public UsuarioRepository usuarioRepository;

    //atributo jwtutil
    public JwtUtil jwtUtil;

    //atributo hashutil
    public HashUtil hashUtil;

    //construtor
    public LoginService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, HashUtil hashUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.hashUtil = hashUtil;
    }

    public TokenDTO login ( CredenciaisDTO credenciaisDTO){

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(credenciaisDTO.getEmail());

        if (usuarioOptional.isEmpty()) {
            throw new FalhaLogin("Email ou senha inválido");
        }

        Usuario usuario = usuarioOptional.get();

        if (hashUtil.senhaValida(credenciaisDTO.getSenha(), usuario.getSenha()) == false) {
            throw new FalhaLogin("Email ou senha inválido");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail());
        return new TokenDTO(token);
    }

}
