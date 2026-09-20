package gestaousuario;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public Usuario() {
    }

    //Construtor
    public Usuario(Long id, String email, String nome, String senha, Perfil perfil) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.perfil = perfil;
    }

    // get
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }
    public String getSenha() {
        return senha;
    }
    public Perfil getPerfil() {
        return perfil;
    }

    //set
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    //Provisorio
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", perfil='" + perfil.name() + '\'' +
                '}';
    }
}
