package com.ia.demo.configuration;

import net.sf.clipsrules.jni.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

@Configuration
public class ClipsConfiguration {
    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public Environment clipsEnviorment() throws IOException {
        Environment clipsEnvironment = new Environment();
        File file = new ClassPathResource("lib/p.cpl").getFile();
        clipsEnvironment.loadFromResource(file.getAbsolutePath());
        clipsEnvironment.reset();
        return clipsEnvironment;
    }
}
