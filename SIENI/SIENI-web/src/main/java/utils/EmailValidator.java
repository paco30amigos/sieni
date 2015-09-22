/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author francisco_medina
 */
public class EmailValidator {

    private final Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}
