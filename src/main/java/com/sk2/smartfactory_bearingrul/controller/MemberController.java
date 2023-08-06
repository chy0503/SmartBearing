package com.sk2.smartfactory_bearingrul.controller;

import com.sk2.smartfactory_bearingrul.dto.RequestLoginMemberDto;
import com.sk2.smartfactory_bearingrul.dto.RequestSignupMemberDto;
import com.sk2.smartfactory_bearingrul.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "로그인", notes = "로그인 정보 인증 과정을 거친 후 JWT 토큰을 발급 및 저장합니다.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody RequestLoginMemberDto requestLogin) {
        return ResponseEntity.ok(memberService.login(requestLogin));
    }

    @ApiOperation(value = "로그아웃", notes = "발급 받은 JWT 토큰을 삭제합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {
        memberService.logout();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "회원 가입 - 사원 확인", notes = "회원 가입하려는 사원의 정보가 존재하는지 확인합니다.")
    @PostMapping("/signup/check-employee")
    public ResponseEntity<Boolean> checkRegistration(@RequestBody RequestSignupMemberDto requestSignup) {
        String employeeId = requestSignup.getEmployeeId();
        String email = requestSignup.getEmail();
        Boolean checkRegistration = memberService.checkRegistration(employeeId, email);
        if (checkRegistration) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @ApiOperation(value = "회원 가입", notes = "회원 가입 인증 과정을 거친 후 입력 받은 정보를 바탕으로 회원을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity registerMember(@RequestBody RequestSignupMemberDto requestSignup) {
        memberService.registerMember(requestSignup);
        return new ResponseEntity<>(requestSignup, HttpStatus.CREATED);
    }

    @ApiOperation(value = "회원 가입 - 회원 아이디 중복 확인", notes = "이미 사용되고 있는 회원 아이디인지 확인합니다.")
    @PostMapping("/signup/is-duplicated")
    public ResponseEntity<Boolean> isDuplicated(@RequestBody RequestSignupMemberDto requestSignup) {
        String memberId = requestSignup.getMemberId();
        boolean isDuplicated =  memberService.isDuplicated(memberId);
        if (isDuplicated) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @ApiOperation(value = "회원 가입 - 회원 중복 가입 확인", notes = "해당 사원이 이미 회원 가입을 했을 경우를 확인합니다.")
    @PostMapping("/signup/is-existed")
    public ResponseEntity<Boolean> isExisted(@RequestBody RequestSignupMemberDto requestSignup) {
        String employeeId = requestSignup.getEmployeeId();
        boolean isExisted = memberService.isExisted(employeeId);
        if (isExisted) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }
}
