package com.example.demo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCondition {
    private Long id;
    private String name;
    private Integer age;
    private String updatedAtGte;
    private String updatedAtLt;

    public static MemberCondition name(String name) {
        return new MemberCondition(null, name, null, null, null);
    }

    public static MemberCondition age(Integer age) {
        return new MemberCondition(null, null, age, null, null);
    }

    public static MemberCondition updatedAtBetween(String updatedAtGte, String updatedAtLt) {
        return new MemberCondition(null, null, null, updatedAtGte, updatedAtLt);
    }

    public LocalDateTime getUpdatedAtGte() {
        return this.updatedAtGte != null
                ? this.toLocalDateTime(this.updatedAtGte)
                : null;
    }

    public LocalDateTime getUpdatedAtLt() {
        return this.updatedAtLt != null
                ? this.toLocalDateTime(this.updatedAtLt)
                : null;
    }

    private LocalDateTime toLocalDateTime(String time) {
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(Long.parseLong(time)),
                TimeZone.getDefault().toZoneId());
    }
}
