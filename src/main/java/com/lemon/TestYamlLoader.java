package com.lemon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestYamlLoader {

    @Autowired
    private ymlProperties ymlProperties;

    @Test
    public void test() {
        System.out.println(ymlProperties.toString());
    }
}