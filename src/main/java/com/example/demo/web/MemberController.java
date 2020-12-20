package com.example.demo.web;

import com.example.demo.application.MemberApplicationService;
import com.example.demo.application.MemberDto;
import com.example.demo.domain.MemberCondition;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("member")
public class MemberController {
    private final MemberApplicationService memberApplicationService;

    public MemberController(MemberApplicationService memberApplicationService) {
        this.memberApplicationService = memberApplicationService;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<MemberDto> getMembers(@ModelAttribute final MemberCondition condition) {
        return this.memberApplicationService.getMembers(condition);
    }
}
