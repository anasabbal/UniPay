package com.unipay.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * A helper class to manage messages from the `MessageSource`. It is used for
 * internationalization (i18n), retrieving localized messages based on the current locale.
 */
@Component
@AllArgsConstructor
public class MessageSourceHandler {

    private MessageSource messageSource;

    /**
     * Retrieves a localized message from the message source.
     * @param code The message code (usually a key in the properties files).
     * @param args Arguments to format the message.
     * @return The localized message.
     */
    public String getMessage(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }
}
