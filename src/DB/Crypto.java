package DB;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Crypto {
    public Crypto() {
        super();
    }
    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = "1234567891234567".getBytes();

    protected static Key generateKey() throws Exception {
        Key cle = new SecretKeySpec(keyValue, ALGORITHM);
        return cle;
    }

    public static String encrypt(String motAcces, Key cle) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, cle);

        byte[] encValue = cipher.doFinal(motAcces.getBytes());
        byte[] encByteValue = new Base64().encode(encValue);

        return new String(encByteValue);
    }

    public static String decrypt(String valeurEncrypte,
                                 Key cle) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, cle);

        byte[] byteDecode = new Base64().decode(valeurEncrypte.getBytes());

        byte[] valeur = cipher.doFinal(byteDecode);

        return new String(valeur);
    }

    public String encryptWord(String word) {
        String encryptedWord=null;
        try {
            Key cle = generateKey();
            encryptedWord = encrypt(word, cle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedWord;
    }
    
    public String decryptWord(String word) {
        String decryptedWord=null;
        try {
            Key cle = generateKey();
            decryptedWord = decrypt(word, cle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedWord;
    }
    
}
