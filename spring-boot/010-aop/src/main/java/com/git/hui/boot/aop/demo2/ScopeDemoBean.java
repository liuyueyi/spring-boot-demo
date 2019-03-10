package com.git.hui.boot.aop.demo2;

import com.git.hui.boot.aop.annotation.AnoDot;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by @author yihui in 18:43 19/3/2.
 */
@Component
public class ScopeDemoBean {

    @AnoDot
    String defaultRandUUID(long time) {
        try {
            System.out.println(" in ScopeDemoBean defaultRandUUID before!");
            return UUID.randomUUID() + " | default | " + time;
        } finally {
            System.out.println(" in ScopeDemoBean defaultRandUUID finally!");
        }
    }

    @AnoDot
    protected String protectedRandUUID(long time) {
        try {
            System.out.println(" in ScopeDemoBean protectedRandUUID before!");
            return UUID.randomUUID() + " | protected | " + time;
        } finally {
            System.out.println(" in ScopeDemoBean protectedRandUUID finally!");
        }
    }

    @AnoDot
    private String privateRandUUID(long time) {
        try {
            System.out.println(" in ScopeDemoBean privateRandUUID before!");
            return UUID.randomUUID() + " | private | " + time;
        } finally {
            System.out.println(" in ScopeDemoBean privateRandUUID finally!");
        }
    }

}
