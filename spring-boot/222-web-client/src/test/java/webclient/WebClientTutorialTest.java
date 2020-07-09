package webclient;

import com.git.hui.boo.webclient.rest.WebClientTutorial;
import org.junit.Test;

/**
 * Created by @author yihui in 09:59 20/7/8.
 */
public class WebClientTutorialTest {

    @Test
    public void get() {
        WebClientTutorial web = new WebClientTutorial();
        web.get();
        hook(4000);
    }


    @Test
    public void post() {
        WebClientTutorial web = new WebClientTutorial();
        web.post();
        hook(4000);
    }

    private void hook(long time) {
        // 避免线程直接退出，导致没有输出
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
