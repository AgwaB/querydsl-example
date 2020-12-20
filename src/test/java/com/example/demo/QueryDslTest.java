package com.example.demo;

import com.example.demo.application.MemberDto;
import com.example.demo.domain.Member;
import com.example.demo.domain.MemberCondition;
import com.example.demo.domain.Team;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class QueryDslTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    public void setup() {
        Team team1 = Team.of("team1");
        Team team2 = Team.of("team2");
        Team team3 = Team.of("team3");
        Team team4 = Team.of("team4");

        this.teamRepository.saveAll(
                Arrays.asList(team1, team2, team3, team4)
        );

        this.memberRepository.saveAll(
                Arrays.asList(
                        Member.of("member1", 20, team1), Member.of("member2", 20, team1),
                        Member.of("member3", 22, team2), Member.of("member4", 22, team2),
                        Member.of("member5", 24, team3), Member.of("member6", 24, team3),
                        Member.of("member7", 26, team4), Member.of("member8", 26, team4)
                )
        );
    }

    @Test
    void pagination() {
        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.by(
                        Sort.Order.asc("name"),
                        Sort.Order.asc("age")
                )
        );
        Page<MemberDto> page = this.memberRepository.findAllWithPaginationByComplexQuery(pageRequest);

        assertThat(page.getTotalElements()).isEqualTo(8);
    }

    @Test
    void stream() {
        try(Stream<MemberDto> stream = this.memberRepository.findWithStreamByComplexQueryWithStream()) {
            List<MemberDto> memberDtos = stream.collect(Collectors.toList());
            assertThat(memberDtos.size()).isEqualTo(8);
        }
    }

    @Test
    void findMaxAge() {
        assertThat(this.memberRepository.findMaxAge().orElse(null)).isEqualTo(26);
    }

    @Test
    void condition() {
        List<Member> age_22 = this.memberRepository.findAllByConditionalQuery(MemberCondition.age(22));
        assertThat(age_22.size()).isEqualTo(2);
        age_22.forEach(member -> assertThat(member.getAge()).isEqualTo(22));

        List<Member> name_member1 = this.memberRepository.findAllByConditionalQuery(MemberCondition.name("member1"));
        assertThat(name_member1.size()).isEqualTo(1);
        assertThat(name_member1.get(0).getName()).isEqualTo("member1");
    }
}
