package iipscada.util;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Iterable 的工具类
 *
 * @Auther: mao
 * @Date: 18-12-5 00:23
 */
public class Iterables {

    //添加索引
    public static <E> void forEach(
            Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
