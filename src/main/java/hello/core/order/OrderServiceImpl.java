package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor //생성자 생성
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    //순수하게 인터페이스(MemberRepository, DiscountPolicy)에만 의존 = DIP 만족
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //Singleton 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    //Singleton 테스트 용도
    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    //생성자를 통해 DIP를 철저하게 지키고 있다.
    //OrderServiceImpl은 MemberRepository, DiscountPolicy 에 의존한다. 실제 어떤 구현 객체가 사용될지 모른다.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
