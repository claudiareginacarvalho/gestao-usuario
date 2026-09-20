package gestaousuario;

public class AtualizarUsuarioDTO {
    private String nome;
    private String email;
    private String senha;
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

