import org.junit.Test;

import java.time.LocalDate;

/**
 * @Auther: mao
 * @Date: 18-12-7 13:17
 */

public class TimeTest {
    @Test
    public void time() {
        LocalDate localDate = LocalDate.now();

        System.out.println(localDate.plusDays(1).toString());
    }
}
