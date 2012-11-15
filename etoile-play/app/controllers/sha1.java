package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class sha1  {

    public static String parseSHA1Password(String password)  {
        MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA1");
		
        md.update(password.getBytes());
        byte[] bytes = md.digest();

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     return null;  
    }
}