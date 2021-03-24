package com.totorys.toy.demo.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Order(2)
@Component
public class DemoCommandLIneRunner implements CommandLineRunner {

    @Value(value = "${process.code:@null}")
    private String processCode;

    @Override
    public void run(String... args) throws Exception{
        System.out.println("========================== DemoCommandLIneRunner");
        System.out.println("::::: VM options");
        System.out.println("# processCode = " + processCode);
        System.out.println("::::: Program arguments");
        if (args.length> 0) Arrays.stream(args).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("--------------------------------------------------------------");
    }

}
