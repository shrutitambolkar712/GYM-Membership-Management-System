package com.example.demo.Controller;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository repo;

    // When clicking Take Membership
    @GetMapping("/takeMembership")
    public String takeMembership(@RequestParam String type,
                                 @RequestParam Double amount,
                                 Model model) {

        Member member = new Member();
        member.setMembershipType(type);
        member.setAmount(amount);

        model.addAttribute("member", member);
        return "add-member";
    }
    //save members
    @PostMapping("/saveMember")
    public String saveMember(@ModelAttribute Member member) {

        member.setJoinDate(LocalDate.now());
        repo.save(member);

        return "redirect:/receipt?id=" + member.getId();
    }
    
    //send receipt
    @GetMapping("/receipt")
    public String receipt(@RequestParam Long id, Model model) {

        Member member = repo.findById(id).orElse(null);
        model.addAttribute("member", member);

        return "receipt";
    }
    
    
    
    // View All Members
    @GetMapping("/members")
    public String showMembers(Model model) {
        model.addAttribute("members", repo.findAll());
        return "members";
    }

    // Delete Member
    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/members";
    }

    // Edit Member
    @GetMapping("/edit/{id}")
    public String editMember(@PathVariable Long id, Model model) {
        Member member = repo.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "add-member";
    }
    
    //member-dashboard clicks
    
    @GetMapping("/progress")
    public String progressPage() {
        return "progress";   // progress.html file name
    }
    
    @GetMapping("/workout-plan")
    public String workoutPlan() {
        return "workout-plan";
    }

    @GetMapping("/renew-membership")
    public String renewMembership() {
        return "renew-membership";
    }
    
 // UPI Payment Page
    @GetMapping("/upi-payment")
    public String upiPayment() {
        return "upi-payment";
    }
   
    //attendance-tracking 
    @GetMapping("/attendance")
    public String attendancePage(Model model) {
        model.addAttribute("members", repo.findAll());
        return "attendance";
    }
    
    //diet-plan
    @GetMapping("/diet-plan")
    public String dietPlan(HttpSession session) {

        if (session.getAttribute("loggedInMember") == null 
            && session.getAttribute("trainerName") == null) {

            return "redirect:/member-login";
        }

        return "diet-plan";
    }
}