package gestaousuario.services;
import gestaousuario.dto.CadastrarUsuarioDTO;
import gestaousuario.entity.Usuario;
import gestaousuario.exceptions.EmailJaCadastrado;
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

        boolean verificador = repository.existsByEmail(cadastrarUsuarioDTO.getEmail());

        if (verificador == true){
            throw new EmailJaCadastrado("Já existe um usuario com o email cadastrado.");
        }

        Usuario u = new Usuario(
                null,
                cadastrarUsuarioDTO.getEmail(),
                cadastrarUsuarioDTO.getNome(),
                cadastrarUsuarioDTO.getSenha(),
                cadastrarUsuarioDTO.getPerfil());

        repository.save(u);
    }

}
