package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //문제점 : MemberServiceImpl는 MemberRepository, MemoryMemberRepository 둘 다 의존
    //이는 DIP 위반
    //참고로, DIP : 추상화에 의존해야지, 구체화에 의존하면 안된다. (구현 클래스에 의존하지 말고, 인터페이스에 의존하라는 뜻)
    /*
    이전 실습에서
    - MemberService 클라이언트가 구현 클래스를 직접 선택
    - "MemberRepository m = new MemoryMemberRepository();" -> DIP 위반
    - 정리하면, 다형성 만으로는 OCP, DIP를 지킬 수 없다. 뭔가 더 필요하다.
    - 해결법 : 스프링은 [DI 컨테이너] 제공하여 DI기술로 '다형성 + OCP, DIP'를 가능하게 지원
     */
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    //Sigleton 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    //MemberServiceImpl 입잡에서 보면 의존관계를 외부에서 주입하므로 의존관계주입(DI)이라 한다
    @Autowired //의존관계를 자동으로 주입
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        //다형성에 의해, MemberRepository 인터페이스가 아닌, MemoryMemberRepository 에 있는 save 호출
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
