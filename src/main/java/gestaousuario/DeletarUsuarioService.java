package gestaousuario;

import org.springframework.stereotype.Service;

@Service
public class DeletarUsuarioService {
    public UsuarioRepository usuarioRepository;

    public DeletarUsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    public void deletar (Long id){
       boolean verificador = usuarioRepository.existsById(id);
       if (verificador == false){
           throw new NaoEncontrado("Usuario não existe");
       }
        usuarioRepository.deleteById(id);

    }
}
