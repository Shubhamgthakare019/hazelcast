package com.java;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.java.model.Country;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@SpringBootApplication
public class HazelcastApplication {

    public static void main(String[] args) {
        SpringApplication.run(HazelcastApplication.class, args);
    }

    //Cache Implementation
    /*
    @Bean
    public Map<String, Country> countryMap(){
        return new HashMap<>();
    }*/

    //HazelcastImplementation

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance();
    }

    @Bean
    public Map<String, Country> countryMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("countryMap");
    }


    /*@Bean
    public Config hazelCastConfig() {
        return new Config().setManagementCenterConfig(
                new ManagementCenterConfig().setEnabled(true).setUrl("http://localhost:8080/hazelcast-mancenter"));

    }  */
}