package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//AppConfig
//애플리케이션의 전체 동작 방식을 구성(config)하기 위해,
//"구현 객체를 생성, 연결"하는 책임을 가지는 별도의 설정 클래스

//동작순
//1. appConfig는 MemoryMemberRepository 객체를 생성하고,
//2. 그 참조값을 MemberServiceImpl 생성하면서 생성자로 전달한다.

//AppConfig 는 Client 코드(OrderServiceImpl)를 변경하지 않고, 기능 확장 가능.
//변경은 Application을 구성하는 역할을 하는 AppConfig만 해주면 된다.
//즉, [사용 영역]의 개발코드는 수정 없이, [구성 영역]만 변경해주면 된다.
//AppConfig를 통해 OCP, DIP 만족하는 코드가 된다.

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService(){
        //Singleton test : 1번 호출
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //Singleton test : 3번 호출? -> 그런데 출력 결과는 모두 1번만 호출된다.
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        //Singleton test : 1번 호출
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy(); //정액할인
        return new RateDiscountPolicy(); //정률할인
    }
}
