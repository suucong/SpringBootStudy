package spring.practice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import spring.practice.domain.Member;
import spring.practice.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@Transactional  // 테스트 후 데이터 모두 제거하기 위해 필요
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;

    @Test
//    @Commit을 통해 클래스 자체에서 Transactional이 선언되어있어도 db에 반영 가능
    @DisplayName("회원가입")
    public void 회원가입() throws Exception {
        //Given
        Member member = new Member();
        member.setName("hello");

        //When
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberService.findOne(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    @DisplayName("중복_회원_예외")
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
    }

}