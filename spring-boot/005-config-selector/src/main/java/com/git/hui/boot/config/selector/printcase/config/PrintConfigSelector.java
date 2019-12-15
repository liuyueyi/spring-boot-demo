package com.git.hui.boot.config.selector.printcase.config;

import com.git.hui.boot.config.selector.printcase.PrintSelector;
import com.git.hui.boot.config.selector.printcase.print.ConsolePrint;
import com.git.hui.boot.config.selector.printcase.print.DbPrint;
import com.git.hui.boot.config.selector.printcase.print.FilePrint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by @author yihui in 18:55 19/12/13.
 */
public class PrintConfigSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        AnnotationAttributes attributes =
                AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(PrintSelector.class.getName()));

        Class config = attributes.getClass("value");
        return new String[]{config.getName()};
    }

    public static class ConsoleConfiguration {
        @Bean
        public ConsolePrint consolePrint() {
            return new ConsolePrint();
        }
    }

    public static class FileConfiguration {
        @Bean
        public FilePrint filePrint() {
            return new FilePrint();
        }
    }

    public static class DbConfiguration {
        @Bean
        public DbPrint dbPrint() {
            return new DbPrint();
        }
    }
}
