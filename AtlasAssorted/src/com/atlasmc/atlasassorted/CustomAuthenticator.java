package com.atlasmc.atlasassorted;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class CustomAuthenticator extends Authenticator {
    
    // Called when password authorization is needed
    protected PasswordAuthentication getPasswordAuthentication() {

        String username = "AtlasServerAustin";
        String password = "S3a1l4t1a5";

        // Return the information (a data holder that is used by Authenticator)
        return new PasswordAuthentication(username, password.toCharArray());
         
    }
}
