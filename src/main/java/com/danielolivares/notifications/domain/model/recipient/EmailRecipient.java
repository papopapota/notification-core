package com.danielolivares.notifications.domain.model.recipient;

import java.util.regex.Pattern;

public final class EmailRecipient extends BaseRecipient {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            /*
             * ^[A-Za-z0-9+_.-]+ : Usuario (1 o más letras, dígitos, +, _, . o -)
             * @                 : Arroba obligatoria
             * [A-Za-z0-9.-]+    : Dominio / subdominios (1 o más letras, dígitos, . o -)
             * \\.               : Punto literal previo a la extensión
             * [A-Za-z]{2,}$     : Extensión / TLD (mínimo 2 letras) y fin de cadena
             */
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public EmailRecipient(String value) {
        super(value);
        if (!EMAIL_PATTERN.matcher(this.value()).matches()) {
            throw new IllegalArgumentException("Invalid email format: " + this.value());
        }
    }

    public static EmailRecipient of(String value) {
        return new EmailRecipient(value);
    }
}
