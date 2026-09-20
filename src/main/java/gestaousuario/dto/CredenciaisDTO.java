package gestaousuario.dto;

import jakarta.validation.constraints.NotBlank;

public class CredenciaisDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    //construtor
    public CredenciaisDTO() {
    }
    public CredenciaisDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    //get
    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    //set
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
