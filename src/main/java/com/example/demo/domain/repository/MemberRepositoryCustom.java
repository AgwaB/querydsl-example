package com.example.demo.domain.repository;

import com.example.demo.domain.Member;
import com.example.demo.domain.MemberCondition;
import com.example.demo.application.MemberDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface MemberRepositoryCustom {
    Page<MemberDto> findAllWithPaginationByComplexQuery(Pageable pageable);

    Stream<MemberDto> findWithStreamByComplexQueryWithStream();

    Optional<Integer> findMaxAge();

    List<Member> findAllByConditionalQuery(MemberCondition memberCondition);
}
