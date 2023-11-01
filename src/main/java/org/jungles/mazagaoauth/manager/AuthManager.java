package org.jungles.mazagaoauth.manager;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;

public class AuthManager {

    private static AuthManager instance;
    private final HashSet<String> loggedPlayers = new HashSet<>();

    public static AuthManager getInstance(){
        if (instance == null){
            instance = new AuthManager();
        }

        return instance;
    }

    public void setAuthenticated(@NonNull String username){
        loggedPlayers.add(username.toLowerCase());
    }

    public void clear(@NonNull String username){
        loggedPlayers.remove(username.toLowerCase());
    }

    public boolean isAuthenticated(@NonNull String username){
        return loggedPlayers.contains(username.toLowerCase());
    }

}
