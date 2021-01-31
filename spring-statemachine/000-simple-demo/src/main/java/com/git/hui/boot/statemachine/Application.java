package com.git.hui.boot.statemachine;

import com.git.hui.boot.statemachine.constants.Events;
import com.git.hui.boot.statemachine.constants.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * @author yihui
 * @date 21/1/31
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("------------- start -----------------");
        stateMachine.sendEvent(Events.E1);
        stateMachine.sendEvent(Events.E2);
        System.out.println("------------- end -----------------");
    }
}
