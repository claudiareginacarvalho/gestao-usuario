package gestaousuario.utils;

import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import org.springframework.stereotype.Service;

@Service
public class HashUtil {

    public String gerarHash(String senha) {

        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        Hash hash = Password.hash(senha)
                .addPepper("shared-secret")
                .with(bcrypt);

        return hash.getResult();

    }

    public boolean senhaValida(String senha, String hash) {

        BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

        return Password.check(senha, hash)
                .addPepper("shared-secret")
                .with(bcrypt);

    }

}
