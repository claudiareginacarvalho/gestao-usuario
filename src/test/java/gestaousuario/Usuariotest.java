package gestaousuarios;

import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    void test(){

        Usuario user1 = new Usuario(10L, "Usuario1@gmail.com", "User", "!@#$", Perfil.ADMINISTRADOR);

        System.out.println(user1.toString());

    }

}