import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Auther: mao
 * @Date: 18-10-24 22:09
 */
public class RedisTest {
    private Jedis jedis;

    @Before
    public void setJedis() {
        //连接redis服务器(在这里是连接本地的)
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("9069309848"); //权限认证
        System.out.println("连接服务成功");
    }

    @Test
    public void getTest() {
        System.out.println(jedis.lrange("alertlist", 0, -1));
        System.out.println(jedis.type("timer"));
        System.out.println(jedis.exists("sss"));
        //key#10
        //list listname->value
        //string stringname -> name
        //set setname -> name
        //hash hashkey -> name
        //zset setname -> name
    }
}
