package com.example.demo.application;

import com.example.demo.domain.Member;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    private String name;
    private Integer age;

    @QueryProjection
    public MemberDto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public MemberDto(Member member) {
        this.name = member.getName();
        this.age = member.getAge();
    }
}
