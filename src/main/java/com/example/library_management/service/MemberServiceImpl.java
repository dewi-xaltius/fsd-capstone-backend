// src/main/java/com/example/library_management/service/MemberServiceImpl.java
package com.example.library_management.service; // Corrected package based on your feedback

import com.example.library_management.entity.Member;
import com.example.library_management.entity.User; // Import the User entity
import com.example.library_management.repository.MemberRepository;
import com.example.library_management.repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; // Keep Optional import for getMemberById

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository; // Declare UserRepository

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository; // Initialize UserRepository
    }

    // --- Your existing methods ---

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long id, Member memberDetails) {
        Member existingMember = memberRepository.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Member not found with id " + id));
        existingMember.setName(memberDetails.getName());
        existingMember.setAddress(memberDetails.getAddress());
        existingMember.setContactInfo(memberDetails.getContactInfo());
        existingMember.setRegistrationDate(memberDetails.getRegistrationDate());
        existingMember.setMembershipExpiryDate(memberDetails.getMembershipExpiryDate());
        return memberRepository.save(existingMember);
    }

    @Override
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    @Override
    public List<Member> searchMembersByName(String name) {
        // This method now works because you added findByNameContainingIgnoreCase to MemberRepository
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    // --- Implementation for the new getMemberByUsername method ---
    @Override
    public Member getMemberByUsername(String username) {
        // Your UserRepository.java returns User directly, not Optional<User>
        // So, no need for Optional here.
        User user = userRepository.findByUsername(username);

        if (user != null) {
            // User.java has getMember() method, so this is correct.
            return user.getMember();
        }
        return null; // Return null if user (or associated member) is not found
    }
}