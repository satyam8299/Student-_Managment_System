package com.cs.sms.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExp {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExp.class);

    // ✅ Handle CSRF Exception (Session Expired Case)
    @ExceptionHandler(CsrfException.class)
    public String handleCsrfException(CsrfException ex,
                                      RedirectAttributes redirectAttributes) {

        log.warn("CSRF validation failed: {}", ex.getMessage());

        redirectAttributes.addFlashAttribute(
                "errorMessage",
                "Session expired. Please login again."
        );

        return "redirect:/login";
    }

    // ✅ Handle All Other Exceptions (Generic)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex) {

        log.error("Unexpected error occurred", ex);

        return "500";   // ye 500.html template render karega
    }
}
