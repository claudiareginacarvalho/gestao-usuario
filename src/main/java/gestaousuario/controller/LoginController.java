package gestaousuario.controller;

import gestaousuario.dto.CredenciaisDTO;
import gestaousuario.dto.TokenDTO;
import gestaousuario.exceptions.FalhaLogin;
import gestaousuario.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody CredenciaisDTO credenciaisDTO) {
        try {
            TokenDTO tokenDTO = loginService.login(credenciaisDTO);
            return ResponseEntity.ok(tokenDTO);
        } catch (FalhaLogin e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
