package com.example.demo;

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
}
