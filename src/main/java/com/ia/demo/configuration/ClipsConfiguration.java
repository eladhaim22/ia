package com.ia.demo.configuration;

import net.sf.clipsrules.jni.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClipsConfiguration {

    @Bean
    public Environment clipsEnviorment(){
        Environment clipsEnvironment = new Environment();
        clipsEnvironment.loadFromResource("/lib/p.clp");
        clipsEnvironment.reset();
        return clipsEnvironment;
    }
}
