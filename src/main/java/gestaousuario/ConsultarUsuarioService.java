package gestaousuario;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultarUsuarioService {
    public UsuarioRepository usuarioRepository;

    public ConsultarUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO consultarPorId (Long id){

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isEmpty()){
            throw new NaoEncontrado("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        return new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getNome(), usuario.getPerfil().name());
    }
}
