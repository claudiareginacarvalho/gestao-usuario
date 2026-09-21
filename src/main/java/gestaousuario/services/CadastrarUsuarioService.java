package gestaousuario.services;
import gestaousuario.dto.CadastrarUsuarioDTO;
import gestaousuario.entity.Usuario;
import gestaousuario.exceptions.EmailJaCadastrado;
import gestaousuario.repository.UsuarioRepository;
import gestaousuario.utils.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class CadastrarUsuarioService {

    public UsuarioRepository repository;
    private HashUtil hashUtil;

    //construtor
    public CadastrarUsuarioService(UsuarioRepository repository, HashUtil hashUtil) {
        this.repository = repository;
        this.hashUtil = hashUtil;
    }

    public void cadastrar (CadastrarUsuarioDTO cadastrarUsuarioDTO){

        boolean verificador = repository.existsByEmail(cadastrarUsuarioDTO.getEmail());

        if (verificador == true){
            throw new EmailJaCadastrado("Já existe um usuario com o email cadastrado.");
        }

        String hashDaSenha = hashUtil.gerarHash(cadastrarUsuarioDTO.getSenha());

        Usuario u = new Usuario(
                null,
                cadastrarUsuarioDTO.getEmail(),
                cadastrarUsuarioDTO.getNome(),
                hashDaSenha,
                cadastrarUsuarioDTO.getPerfil());

        repository.save(u);
    }

}
