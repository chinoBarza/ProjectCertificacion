package com.example.chinobarza.facebooklogin.utils;

public class QuerysUtils {

    public static String insertarUsuario(String usuario, String pass) {
        return " INSERT INTO USUARIO VALUES ( NULL , '" + usuario + "','" + pass + "')";
    }

}
