package gestaousuario.controller;
import gestaousuario.dto.AtualizarUsuarioDTO;
import gestaousuario.dto.CadastrarUsuarioDTO;
import gestaousuario.dto.UsuarioDTO;
import gestaousuario.entity.Perfil;
import gestaousuario.exceptions.EmailJaCadastrado;
import gestaousuario.exceptions.NaoEncontrado;
import gestaousuario.services.*;
import gestaousuario.utils.DadosToken;
import gestaousuario.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private CadastrarUsuarioService cadastrarUsuarioService;
    private ConsultarUsuarioService consultarUsuarioService;
    private ListarUsuarioService listarUsuarioService;
    private DeletarUsuarioService deletarUsuarioService;
    private AtualizarUsuarioService atualizarUsuarioService;
    private JwtUtil jwtUtil;

    //construtor
    public UsuarioController(CadastrarUsuarioService cadastrarUsuarioService,
                             ConsultarUsuarioService consultarUsuarioService,
                             ListarUsuarioService listarUsuarioService,
                             DeletarUsuarioService deletarUsuarioService,
                             AtualizarUsuarioService atualizarUsuarioService,
                             JwtUtil jwtUtil) {
        this.cadastrarUsuarioService = cadastrarUsuarioService;
        this.consultarUsuarioService = consultarUsuarioService;
        this.listarUsuarioService = listarUsuarioService;
        this.deletarUsuarioService = deletarUsuarioService;
        this.atualizarUsuarioService = atualizarUsuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid CadastrarUsuarioDTO cadastrarUsuarioDTO,
                                       @RequestHeader(value = "Authorization", required = false) String authorization) {
        DadosToken usuarioAutenticado = autenticar(authorization);
        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        if (usuarioAutenticado.getPerfil() != Perfil.ADMINISTRADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autorizado");
        }

        try {
            cadastrarUsuarioService.cadastrar(cadastrarUsuarioDTO);
            return ResponseEntity.status(201).build();
        }
        catch (EmailJaCadastrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> consultarPorId(@PathVariable("id") Long id,
                                            @RequestHeader(value = "Authorization", required = false) String authorization) {
        DadosToken usuarioAutenticado = autenticar(authorization);
        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        if (!podeConsultar(usuarioAutenticado, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autorizado");
        }

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
    public ResponseEntity<?> listarUsuario(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        DadosToken usuarioAutenticado = autenticar(authorization);
        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        if (!podeListar(usuarioAutenticado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autorizado");
        }

        return ResponseEntity.ok(listarUsuarioService.listarTodos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable("id") Long id,
                                            @RequestHeader(value = "Authorization", required = false) String authorization) {
        DadosToken usuarioAutenticado = autenticar(authorization);
        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        if (usuarioAutenticado.getPerfil() != Perfil.ADMINISTRADOR) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autorizado");
        }

        try {
            deletarUsuarioService.deletar(id);
            return ResponseEntity.status(204).build();

        } catch (NaoEncontrado e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable("id") Long id,
                                               @Valid @RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO,
                                               @RequestHeader(value = "Authorization", required = false) String authorization) {
        DadosToken usuarioAutenticado = autenticar(authorization);
        if (usuarioAutenticado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
        if (!podeAtualizar(usuarioAutenticado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário não autorizado");
        }

        try {
            atualizarUsuarioService.atualizarUsuario(id, atualizarUsuarioDTO);
            return ResponseEntity.status(200).build();
        }catch (NaoEncontrado e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (EmailJaCadastrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
    private DadosToken autenticar(String authorization) {
        try {
            return jwtUtil.validarToken(authorization);
        } catch (JwtException | IllegalArgumentException exception) {
            return null;
        }
    }

    private boolean podeConsultar(DadosToken usuario, Long id) {
        return usuario.getPerfil() == Perfil.ADMINISTRADOR
                || usuario.getPerfil() == Perfil.OPERADOR
                || usuario.getId().equals(id);
    }

    private boolean podeListar(DadosToken usuario) {
        return usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.OPERADOR;
    }

    private boolean podeAtualizar(DadosToken usuario) {
        return usuario.getPerfil() == Perfil.ADMINISTRADOR || usuario.getPerfil() == Perfil.OPERADOR;
    }
}
