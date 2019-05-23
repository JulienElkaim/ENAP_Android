package com.example.julienelkaim.testyoutube.toolbox;

public final class StringBox {

    private StringBox(){}

    public static String escapeMyUrl(String requested) {
        return requested
                .replace("%","%25")
                .replace("+","%2B")
                .replace(" ", "+")
                .replace("OR","%7C")
                .replace("'","%27")
                .replace("*","%2A")
                .replace("!","%21")
                .replace("(","%28")
                .replace(")","%29")
                .replace("\"", "%22")
                .replace("?","%3F")
                .replace("/","%2F")
                .replace(":","%3A")
                .replace("=","%3D")
                .replace("&","%26")
                .replace("$","%24")
                .replace("<","%3C")
                .replace(",","%2C")
                .replace(";","%3B")
                .replace("`","%60")
                .replace(">","%3E")
                .replace("^","%5E")
                .replace("@","%40")
                .replace("#","%23")
                ;
    }


}
