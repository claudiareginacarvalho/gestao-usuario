package gestaousuario;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioService {
    public UsuarioRepository repository;

    //construtor
    public CadastrarUsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    void cadastrar (CadastrarUsuarioDTO cadastrarUsuarioDTO){

        Usuario u = new Usuario(
                null,
                cadastrarUsuarioDTO.getEmail(),
                cadastrarUsuarioDTO.getNome(),
                cadastrarUsuarioDTO.getSenha(),
                cadastrarUsuarioDTO.getPerfil());

        repository.save(u);
    }

}
