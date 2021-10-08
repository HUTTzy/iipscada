package iipscada.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ProviderInfo
 *
 * @Auther: mao
 * @Date: 18-10-23 23:10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProviderInfo {
    String value();

    String desc() default "";
}
