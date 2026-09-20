package gestaousuario.dto;

public class TokenDTO {

    private String token;
    //construtor
    public TokenDTO(String token) {
        this.token = token;
    }

    public TokenDTO() {
    }

    //get
    public String getToken() {
        return token;
    }
    //set
    public void setToken(String token) {
        this.token = token;
    }

}
