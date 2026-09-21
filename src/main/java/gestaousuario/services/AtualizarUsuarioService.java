package gestaousuario.services;

import gestaousuario.dto.AtualizarUsuarioDTO;
import gestaousuario.exceptions.EmailJaCadastrado;
import gestaousuario.exceptions.NaoEncontrado;
import gestaousuario.entity.Usuario;
import gestaousuario.repository.UsuarioRepository;
import gestaousuario.utils.HashUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AtualizarUsuarioService {

    public UsuarioRepository repository;
    public HashUtil hashUtil;

    //construtor
    public AtualizarUsuarioService(UsuarioRepository repository, HashUtil hashUtil) {
        this.repository = repository;
        this.hashUtil = hashUtil;
    }

    public void atualizarUsuario (Long id, AtualizarUsuarioDTO atualizarUsuarioDTO){
        Optional<Usuario> usuarioOptonal = repository.findById(id);
        if (usuarioOptonal.isEmpty()){
            throw new NaoEncontrado("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptonal.get();

        boolean verificador = repository.existsByEmail(atualizarUsuarioDTO.getEmail());

        if (verificador == true && atualizarUsuarioDTO.getEmail().equals(usuario.getEmail()) == false){
            throw new EmailJaCadastrado("Já existe um usuario com o email cadastrado.");
        }

        String hashDaSenha = hashUtil.gerarHash(atualizarUsuarioDTO.getSenha());

        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setSenha(hashDaSenha);
        usuario.setEmail(atualizarUsuarioDTO.getEmail());
        usuario.setPerfil(atualizarUsuarioDTO.getPerfil());;
        repository.save(usuario);
    }


}
