package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCondition {
    private Long id;
    private String name;
    private Integer age;
    private LocalDateTime updatedAtGte;
    private LocalDateTime updatedAtLt;

    public MemberCondition(
            Long id,
            String name,
            Integer age,
            String updatedAtGte,
            String updatedAtLt
    ) {
        // validation
        this.id = id;
        this.name = name;
        this.age = age;
        this.updatedAtGte = updatedAtGte != null
                ? this.toLocalDateTime(updatedAtGte)
                : null;
        this.updatedAtLt = updatedAtLt != null
                ? this.toLocalDateTime(updatedAtLt)
                : null;
    }

    public static MemberCondition name(String name) {
        return new MemberCondition(null, name, null, null, null);
    }

    public static MemberCondition age(Integer age) {
        return new MemberCondition(null, null, age, null, null);
    }

    public static MemberCondition updatedAtBetween(String updatedAtGte, String updatedAtLt) {
        return new MemberCondition(null, null, null, updatedAtGte, updatedAtLt);
    }

    private LocalDateTime toLocalDateTime(String time) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(time)),
                TimeZone.getDefault().toZoneId());
    }
}
