package gestaousuario.dto;

public class UsuarioDTO {
    //Aqui não utilizamos a senha para não mostrarmos no listar.
    private Long id;
    private String nome;
    private String email;
    private String perfil;

    //construtor
    public UsuarioDTO(Long id, String email, String nome, String perfil) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.perfil = perfil;
    }
    //get
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPerfil() {
        return perfil;
    }

    public Long getId() {
        return id;
    }
    //set
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
