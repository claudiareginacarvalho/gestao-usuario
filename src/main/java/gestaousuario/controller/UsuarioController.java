package gestaousuario.controller;
import gestaousuario.dto.AtualizarUsuarioDTO;
import gestaousuario.dto.CadastrarUsuarioDTO;
import gestaousuario.dto.UsuarioDTO;
import gestaousuario.exceptions.EmailJaCadastrado;
import gestaousuario.exceptions.NaoEncontrado;
import gestaousuario.services.*;
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
    private DeletarUsuarioService deletarUsuarioService;
    private AtualizarUsuarioService atualizarUsuarioService;

    //construtor
    public UsuarioController(CadastrarUsuarioService cadastrarUsuarioService,
                             ConsultarUsuarioService consultarUsuarioService,
                             ListarUsuarioService listarUsuarioService,
                             DeletarUsuarioService deletarUsuarioService,
                             AtualizarUsuarioService atualizarUsuarioService) {
        this.cadastrarUsuarioService = cadastrarUsuarioService;
        this.consultarUsuarioService = consultarUsuarioService;
        this.listarUsuarioService = listarUsuarioService;
        this.deletarUsuarioService = deletarUsuarioService;
        this.atualizarUsuarioService = atualizarUsuarioService;
    }

    @PostMapping
    public ResponseEntity<?>  cadastrar (@RequestBody CadastrarUsuarioDTO cadastrarUsuarioDTO){
        try {
            cadastrarUsuarioService.cadastrar(cadastrarUsuarioDTO);
            return ResponseEntity.status(201).build();
        }
        catch (EmailJaCadastrado e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable("id") Long id){
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
    public ResponseEntity<?> atualizarUsuario (@PathVariable("id") Long id, @RequestBody AtualizarUsuarioDTO atualizarUsuarioDTO){
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
}
