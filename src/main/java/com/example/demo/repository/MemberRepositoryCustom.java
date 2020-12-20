package com.example.demo.repository;

import com.example.demo.Member;
import com.example.demo.MemberCondition;
import com.example.demo.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface MemberRepositoryCustom {
    Page<MemberDto> someComplexQueryWithPagination(Pageable pageable);

    Stream<MemberDto> someComplexQueryWithStream();

    Optional<Integer> findMaxAge();

    List<Member> someConditionalQuery(MemberCondition memberCondition);
}
