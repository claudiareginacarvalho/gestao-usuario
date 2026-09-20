package gestaousuario;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListarUsuarioService {
    public UsuarioRepository usuarioRepository;
    //construtor
    public ListarUsuarioService(UsuarioRepository usuarioRepository) {this.usuarioRepository = usuarioRepository;}

    public List <UsuarioDTO> listarTodos (){
        List <Usuario> listUsuario = usuarioRepository.findAll();
        List <UsuarioDTO> usuarioDTOList = new ArrayList<>();

        for (Usuario usuario : listUsuario){
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getNome(), usuario.getPerfil().name());
            usuarioDTOList.add(usuarioDTO);
        }
        return usuarioDTOList;
    }

}
