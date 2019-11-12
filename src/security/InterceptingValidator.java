package security;

import java.util.regex.Pattern;

/**
 * @program: CoffeeWeb
 * @description:
 * @author: DennyLee
 * @create: 2019-10-01 21:58
 **/
public class InterceptingValidator {

    //regular expression
    //check common sql keyword
    private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|union|and|or|delete|insert|trancate|char|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    public static boolean SQLValidator(String uri){
        String query = uri.toLowerCase();
        //detect SQL injection
        return !sqlPattern.matcher(uri).find();
    }
}
