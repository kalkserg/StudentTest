package com.example.localization;

import com.example.ExercisesApplication;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class LocalizedResource {

    public static String getLocalizedResource(Locale locale, String baseName, String suffix) {
        ResourceBundle.Control control = ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);
        List<Locale> candidateLocales = control.getCandidateLocales(baseName, locale);
        for (Locale specificLocale : candidateLocales) {
            String bundleName = control.toBundleName(baseName, specificLocale);
            String resourceName = control.toResourceName(bundleName, suffix);
            URL url = ExercisesApplication.class.getClassLoader().getResource(resourceName);
            if (url != null) {
                File file = new File(url.getFile());
                return file.getName();
            }
        }
        return null;
    }
}
