import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * Created by @author yihui in 20:25 18/12/25.
 */
public class RankInitTest {

    private Random random;
    private RestTemplate restTemplate;

    @Before
    public void init() {
        random = new Random();
        restTemplate = new RestTemplate();
    }

    private int genUserId() {
        return random.nextInt(1024);
    }

    private double genScore() {
        return random.nextDouble() * 100;
    }

    @Test
    public void testInitRank() {
        try {
            for (int i = 0; i < 30; i++) {
                restTemplate.getForObject("http://localhost:8080/update?userId=" + genUserId() + "&score=" + genScore(),
                        String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
