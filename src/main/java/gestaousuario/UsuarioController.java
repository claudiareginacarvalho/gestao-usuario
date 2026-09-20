package gestaousuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private CadastrarUsuarioService cadastrarUsuarioService;
    private ConsultarUsuarioService consultarUsuarioService;
    private ListarUsuarioService listarUsuarioService;

    //construtor
    public UsuarioController(CadastrarUsuarioService cadastrarUsuarioService, ConsultarUsuarioService consultarUsuarioService, ListarUsuarioService listarUsuarioService) {
        this.cadastrarUsuarioService = cadastrarUsuarioService;
        this.consultarUsuarioService = consultarUsuarioService;
        this.listarUsuarioService = listarUsuarioService;
    }

    @PostMapping
    public void cadastrar (@RequestBody CadastrarUsuarioDTO cadastrarUsuarioDTO){
        cadastrarUsuarioService.cadastrar(cadastrarUsuarioDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarPorId (@PathVariable("id") Long id){
        try {
            UsuarioDTO usuarioDTO = consultarUsuarioService.consultarPorId(id);
            return ResponseEntity
                    .ok(usuarioDTO);
        } catch (NaoEncontrado e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    @GetMapping()
    public List <UsuarioDTO> listarUsuario (){
        return listarUsuarioService.listarTodos();
    }
}
