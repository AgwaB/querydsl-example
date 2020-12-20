package com.example.demo.domain.repository;

import com.example.demo.application.MemberDto;
import com.example.demo.QMemberDto;
import com.example.demo.domain.Member;
import com.example.demo.domain.MemberCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.example.demo.QMember.member;
import static com.example.demo.QTeam.team;

@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /*
    1. 'Projections.bean' with Setter
    .select(Projections.bean(MemberDto.class,
                  member.username,
                  member.age))

     2. 'Projections.fields'
     .select(Projections.fields(MemberDto.class,
                  member.username,
                  member.age))

     3. `Projections.constructor` with constructor
     .select(Projections.constructor(MemberDto.class,
                  member.username,
                  member.age))

     4. @QueryProjection with Q Entity for type checking
     .select(new QMemberDto(member.username, member.age))
     */
    @Override
    public Page<MemberDto> findAllWithPaginationByComplexQuery(Pageable pageable) {
        List<MemberDto> memberDtos = this.queryFactory
                .select(new QMemberDto(member.name, member.age))
                .from(member)
                .innerJoin(member.team, team)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<MemberDto> countQuery = this.queryFactory
                .select(new QMemberDto(member.name, member.age))
                .from(member)
                .join(member.team, team).fetchJoin();

        return PageableExecutionUtils.getPage(memberDtos, pageable, countQuery::fetchCount);
    }

    /*
    JPQL
    @Query (
            value = "SELECT new com.example.demo.application.MemberDto(m.name, m.age) " +
                    "FROM member m " +
                    "INNER JOIN team t ON (member.team.id = team.id)"
    )
     */
    @Override
    public Stream<MemberDto> findWithStreamByComplexQueryWithStream() {
        return this.queryFactory
                .select(new QMemberDto(member.name, member.age))
                .from(member)
                .innerJoin(member.team, team)
                .createQuery()
                .getResultStream();
    }

    /*
    JPQL
    @Query(
            value = "SELECT MAX(m.age) " +
                    "FROM Member m "
    )
     */
    @Override
    public Optional<Integer> findMaxAge() {
        return Optional.ofNullable(
                this.queryFactory
                        .select(member.age.max())
                        .from(member)
                        .fetchOne()
        );
    }

    @Override
    public List<Member> findAllByConditionalQuery(MemberCondition condition) {
        return this.queryFactory
                .selectFrom(member)
                .where(this.memberPredicates(condition))
                .fetch();
    }

    private BooleanExpression[] memberPredicates(MemberCondition condition) {
        return new BooleanExpression[]{
                this.idEqual(condition.getId()),
                this.nameEqual(condition.getName()),
                this.ageEqual(condition.getAge()),
                this.updateAtGte(condition.getUpdatedAtGte()),
                this.updateAtLt(condition.getUpdatedAtLt())
        };
    }

    private BooleanExpression idEqual(Long id) {
        return id != null
                ? member.id.eq(id)
                : null;
    }

    private BooleanExpression nameEqual(String name) {
        return name != null
                ? member.name.eq(name)
                : null;
    }

    private BooleanExpression ageEqual(Integer age) {
        return age != null
                ? member.age.eq(age)
                : null;
    }

    private BooleanExpression updateAtGte(LocalDateTime updatedAt) {
        return updatedAt != null
                ? member.updatedAt.goe(updatedAt)
                : null;
    }

    private BooleanExpression updateAtLt(LocalDateTime updatedAt) {
        return updatedAt != null
                ? member.updatedAt.lt(updatedAt)
                : null;
    }
}
