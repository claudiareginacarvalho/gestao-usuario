package gestaousuario.utils;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HashUtil {

    @Value("${app.hash.pepper}")
    private String pepper;

    public String gerarHash(String senha) {

        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        Hash hash = Password.hash(senha)
            .addPepper(pepper)
                .with(bcrypt);

        return hash.getResult();

    }

    public boolean senhaValida(String senha, String hash) {

        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        return Password.check(senha, hash)
            .addPepper(pepper)
                .with(bcrypt);

    }

}
