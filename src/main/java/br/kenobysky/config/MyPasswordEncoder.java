package br.kenobysky.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Shadows
 */
public class MyPasswordEncoder implements PasswordEncoder {

      public static PasswordEncoder getPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
       
        return encoder;
    }

    @Override
    public String encode(CharSequence cs) {

        return getPasswordEncoder().encode(cs);
    }

    @Override
    public boolean matches(CharSequence cs, String string) {

        return getPasswordEncoder().matches(cs, string);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return getPasswordEncoder().upgradeEncoding(encodedPassword);
    }

    public static boolean matchesStatic(CharSequence o1, String o2) {

        return getPasswordEncoder().matches(o1, o2);
    }
}
