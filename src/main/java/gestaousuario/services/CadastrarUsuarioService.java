package gestaousuario.services;
import gestaousuario.dto.CadastrarUsuarioDTO;
import gestaousuario.entity.Usuario;
import gestaousuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioService {
    public UsuarioRepository repository;

    //construtor
    public CadastrarUsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void cadastrar (CadastrarUsuarioDTO cadastrarUsuarioDTO){

        Usuario u = new Usuario(
                null,
                cadastrarUsuarioDTO.getEmail(),
                cadastrarUsuarioDTO.getNome(),
                cadastrarUsuarioDTO.getSenha(),
                cadastrarUsuarioDTO.getPerfil());

        repository.save(u);
    }

}
