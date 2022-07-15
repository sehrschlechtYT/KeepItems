package yt.sehrschlecht.keepitems.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 2.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigOption {
    String key();
    Class<?> type();
}
