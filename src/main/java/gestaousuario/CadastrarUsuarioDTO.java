package gestaousuario;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class CadastrarUsuarioDTO {

    private String nome;
    private String email;
    private String senha;
    private Perfil perfil;

    //construtor vazio
    public CadastrarUsuarioDTO() {
    }
    //construtor
    public CadastrarUsuarioDTO(String nome, String email, String senha, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }
    //get
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public String getSenha() {
        return senha;
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
