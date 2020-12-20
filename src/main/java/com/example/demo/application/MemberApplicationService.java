package com.example.demo.application;

import com.example.demo.domain.MemberCondition;
import com.example.demo.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberApplicationService {
    private final MemberRepository memberRepository;

    public MemberApplicationService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<MemberDto> getMembers(MemberCondition condition) {
        return this.memberRepository.findAllByConditionalQuery(condition).stream()
                .map(MemberDto::new)
                .collect(Collectors.toList());
    }
}
