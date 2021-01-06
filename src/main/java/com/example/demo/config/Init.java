package com.example.demo.config;

import com.example.demo.domain.Member;
import com.example.demo.domain.Team;
import com.example.demo.domain.repository.MemberRepository;
import com.example.demo.domain.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
public class Init implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public Init(
            MemberRepository memberRepository,
            TeamRepository teamRepository
    ) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }


    @Override
    public void run(String... args) throws Exception {
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
}
