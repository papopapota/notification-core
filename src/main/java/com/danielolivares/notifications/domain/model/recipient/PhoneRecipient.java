package com.danielolivares.notifications.domain.model.recipient;

import java.util.regex.Pattern;

public final class PhoneRecipient extends BaseRecipient {

    // Formato E.164 opcional con '+' y entre 8 y 15 dígitos numéricos
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            /*
            * ^\\+? puede estar +
            * [1-9] inicia con un numero del 1 al 9
            * \\d cualquier numero
            * {7,14} 7 a 14 digitos
            * $ fin de la cadena
            * */
            "^\\+?[1-9]\\d{7,14}$"
    );

    public PhoneRecipient(String value) {
        super(value);
        if (!PHONE_PATTERN.matcher(this.value()).matches()) {
            throw new IllegalArgumentException("Invalid phone number format: " + this.value());
        }
    }

    public static PhoneRecipient of(String value) {
        return new PhoneRecipient(value);
    }
}