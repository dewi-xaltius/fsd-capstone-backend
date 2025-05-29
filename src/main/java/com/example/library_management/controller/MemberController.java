// src/main/java/com/example/library_management/controller/MemberController.java
package com.example.library_management.controller;

import com.example.library_management.entity.Member;
import com.example.library_management.service.MemberService; // Assuming you have a MemberService
import com.example.library_management.dto.MemberDTO; // <-- ADD THIS IMPORT

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService; // Ensure this is correctly injected

    @GetMapping("/byUsername/{username}")
    // Keep your @PreAuthorize annotation as security is working now
    @PreAuthorize("hasRole('MEMBER') and #username == authentication.principal.username")
    // Change return type to MemberDTO
    public ResponseEntity<MemberDTO> getMemberByUsername(@PathVariable String username) {
        // Assume memberService.getMemberByUsername returns a Member entity
        Member member = memberService.getMemberByUsername(username);

        if (member == null) {
            return ResponseEntity.notFound().build();
        }

        // Manually map the Member entity to the MemberDTO
        MemberDTO memberDTO = new MemberDTO(
            member.getId(),
            member.getName(),
            member.getAddress(),
            member.getContactInfo(),
            member.getRegistrationDate(),
            member.getMembershipExpiryDate()
        );

        return ResponseEntity.ok(memberDTO);
    }
}