package gestaousuario.utils;

import gestaousuario.entity.Perfil;

public class DadosToken {

    private Long id;
    private String email;
    private Perfil perfil;

    public DadosToken(Long id, String email, Perfil perfil) {
        this.id = id;
        this.email = email;
        this.perfil = perfil;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Perfil getPerfil() {
        return perfil;
    }
}
