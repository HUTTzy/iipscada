package iipscada.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 头皮发黑
 *
 * @author :mao
 * @version :1.0
 * @date :18-12-29
 * @description :定时任务
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Job {
    //任务名称
    String name();

    //执行时间
    String at() default "";

    //执行间隔,最小为100
    long interval() default 100;

    //执行时间
    boolean loop() default false;
}
