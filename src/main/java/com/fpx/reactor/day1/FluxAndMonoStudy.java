package com.fpx.reactor.day1;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoStudy {

    @Test
    public void testFlux(){
        Flux<String> seq1 = Flux.just("foo","bar","foobar");
        List<String> interable = Arrays.asList("foo","bar","foobar");
        Flux<String> seq2 = Flux.fromIterable(interable);
    }

    @Test
    public void testFlux1(){
        /**
         * 第一个参数是范围的开始，而第二个参数是要生成的项目数
         * 即使没有值，工厂方法也会遵循泛型类型
         */
        Flux<Integer> ints = Flux.range(1, 5);
        ints.subscribe(i -> System.out.println(i));
    }
}
