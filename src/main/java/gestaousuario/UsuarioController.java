package gestaousuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private CadastrarUsuarioService cadastrarUsuarioService;
    private ConsultarUsuarioService consultarUsuarioService;

    //construtor
    public UsuarioController(CadastrarUsuarioService cadastrarUsuarioService, ConsultarUsuarioService consultarUsuarioService) {
        this.cadastrarUsuarioService = cadastrarUsuarioService;
        this.consultarUsuarioService = consultarUsuarioService;
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
}
