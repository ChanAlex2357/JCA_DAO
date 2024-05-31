package jca.dao.helpers;

public class StringParser {
    final static public  String LOWERCASE   = "lower";
    final static public  String UPPERCASE   = "upper";
    final static public  String NORMAL      = "normal";

    static public String parse(String source , String parseOption){
        String string = source;
        if (parseOption.equals(LOWERCASE)) {
            string =  source.toLowerCase();
        }
        else if (parseOption.equals(UPPERCASE)) {
            string = source.toUpperCase();
        }
        return string;
    }
}
