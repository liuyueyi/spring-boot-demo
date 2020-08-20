package webclient;

import com.git.hui.boo.webclient.rest.WebClientTutorial;
import org.junit.Test;

/**
 * Created by @author yihui in 09:59 20/7/8.
 */
public class WebClientTutorialTest {

    public void get() {
        try {
            WebClientTutorial web = new WebClientTutorial();
            web.get();
            hook(4000);
        } catch (Exception e) {
        }
    }


    public void post() {
        try {
            WebClientTutorial web = new WebClientTutorial();
            web.post();
            hook(4000);
        } catch (Exception e) {
        }
    }

    public void upload() {
        try {
            WebClientTutorial web = new WebClientTutorial();
            web.postFile();
            hook(3000);
        } catch (Exception e) {
        }
    }

    public void sync() {
        try {
            WebClientTutorial web = new WebClientTutorial();
            web.sync();
            hook(3000);
        } catch (Exception e) {
        }
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
