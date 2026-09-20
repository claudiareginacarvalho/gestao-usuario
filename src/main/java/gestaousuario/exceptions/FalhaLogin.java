package gestaousuario.exceptions;

public class FalhaLogin extends RuntimeException {
  public FalhaLogin(String message) {
    super(message);
  }
}
