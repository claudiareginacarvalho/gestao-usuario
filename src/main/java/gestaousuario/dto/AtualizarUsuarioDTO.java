package gestaousuario.dto;

import gestaousuario.entity.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class AtualizarUsuarioDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
    @NotNull
    private Perfil perfil;
    //construtor
    public AtualizarUsuarioDTO(String nome, String email, Perfil perfil, String senha) {
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
        this.senha = senha;
    }
    //get
    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public String getNome() {
        return nome;
    }
    //set
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}

