package com.totorys.toy.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Enumeration;
import java.util.Properties;

/**
 * <pre>
 *
 *     [개요]
 *     스프링부트가 구동되는 시점에 특정 코드를 실행하고자 할때는 Runner 를 사용할 수 있다.
 *     Runner 는 2종류로 ApplicationRunner 와 CommandLineRunner 가 있다.
 *     2개의 Runner 간 구동 순서와 Argument 인자값을 받을 경우를 확인한다.
 *
 *     [Info]
 *     1. ApplicationRunner
 *          ApplicationRunner 인터페이스도 CommandLineRunner 인터페이스와 마찬가지로 구동 시점에 run() 메소드를 실행시키지만 다른 타입의 인자를 받습니다.
 *          단순 인자의 스트링 배열을 포함한 추상화한 ApplicationArguments 타입의 객체가 대신 run() 메소드의 인자로 넘어옵니다.
 *     2. CommandLineRunner
 *          CommandLineRunner 인터페이스는 구동 시점에 실행되는 코드가 자바 문자열 아규먼트 배열에 접근해야할 필요가 있는 경우에 사용합니다.
 *          CommandLineRunner 인터페이스를 구현한 클래스에 @Component 어노테이션을 선언해두면 컴포넌트 스캔이되고 구동 시점에 run 메소드의 코드가 실행됩니다.
 *
 *     [Prepare]
 *     1. "VM options" 설정 : -Dprocess.code=dev
 *     2. "Program arguments" 설정 : --t1=asdf --t2=qwer --t3=zxcv
 *
 *     [Test]
 *     1. CommandLineRunner 와 ApplicationRunner 를 상속받는 각 테스틀 클래스를 구성한 후 @Component 로 등록한다.
 *     2. @Order 로 Load 순서에 변화를 주어 구동 순서를 확인해본다.
 *          1) [ApplicationRunner(1) - ApplicationRunner(2)] : @Order 테스트
 *          2) [CommandLineRunner(1) - CommandLineRunner(2)] : @Order 테스트
 *          3) [ApplicationRunner(-) - CommandLineRunner(-)] : 어노테이션 없이 테스트
 *          4) [ApplicationRunner(2) - CommandLineRunner(1)] : @Order 테스트
 *     3. Runner 를 @Bean 으로 등록한다.
 *          1) @Component 와 @Bean 간 구동 순서를 확인한다.
 *
 *     4. Run Configuration 에 "VM options" 와 "Program arguments" 의 값을 설정하여 각 Runner 별로 값을 받을 수 있는지 확인한다.
 *          1) 각 Runner 에서 "VM options" 와 "Program arguments" 둘다 받을 수 있는지
 *          2) @Component 와 @Bean 으로 등록함에 따른 Argument 값 인입 여부 확인
 *
 *
 *     [Commnet]
 *     1. @Order 로 지정되는 순서와 상관없이 ApplicationRunner 는 CommandLineRunner 보다 먼저 수행된다.
 *     2. @Component 가 @Bean 보다 먼저 수행된다.
 *     3. "VM options" 환경변수는 @Value 나 System.getProperties() 를 통해 확인이 가능하다.
 *     4. "Program arguments"
 *
 *
 * </pre>
 *
 * @package : com.totorys.toy.demo
 * @name : Application.java
 * @date : 2021-03-17
 * @author : jw.yu
 * @description : SpringBootApplication
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {

            @Value(value = "${process.code:@null}")
            String processCode;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                System.out.println("========================== Application > ApplicationRunner");
                System.out.println("::::: VM options (@Value)");
                System.out.println("# process.code (True/False) = "+args.containsOption("process.code"));
                System.out.println("# processCode = "+processCode);
                System.out.println("::::: VM options (props)");
                System.out.println("# props = ");
                Properties props = System.getProperties();
                Enumeration<Object> enumerator = props.keys();
                while (enumerator.hasMoreElements()) {
                    Object ele = enumerator.nextElement();
                    String key = ele.toString();
                    System.out.println(key + ": " + System.getProperty(key));
                }
                System.out.println("--------------------------------------------------------------");
            }
        };
    }
}
