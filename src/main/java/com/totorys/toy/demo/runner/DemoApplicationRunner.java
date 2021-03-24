package com.totorys.toy.demo.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(1)
@Component
public class DemoApplicationRunner implements ApplicationRunner {

    @Value(value = "${process.code:@null}")
    private String processCode;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("========================== DemoApplicationRunner");
        System.out.println("::::: VM options");
        System.out.println("# processCode = " + processCode);
        System.out.println("::::: Program arguments");
        if (!args.getOptionNames().isEmpty()) args.getOptionNames().forEach(a->System.out.println(a+"="+args.getOptionValues(a)));
        System.out.println("--------------------------------------------------------------");
    }

}
