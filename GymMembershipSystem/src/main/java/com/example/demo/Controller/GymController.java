package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Member;
import com.example.demo.entity.Trainer;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.TrainerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class GymController {

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private TrainerRepository trainerRepo;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    // MEMBER LOGIN PAGE
    @GetMapping("/member-login")
    public String memberLoginPage() {
        return "member-login";
    }

    // TRAINER LOGIN PAGE
    @GetMapping("/trainer-login")
    public String trainerLoginPage() {
        return "trainer-login";
    }

    // MEMBER LOGIN PROCESS
    @PostMapping("/member-login")
    public String memberLogin(@RequestParam String email,
                               @RequestParam String password,
                               Model model,HttpSession session) {
        Member member = memberRepo.findByEmailAndPassword(email, password);
        if (member != null) {
        	  session.setAttribute("loggedInMember", member);
            model.addAttribute("name", member.getFullName());
            model.addAttribute("email", member.getEmail());
            model.addAttribute("membershipType", member.getMembershipType());
            model.addAttribute("joinDate", member.getJoinDate());
            return "redirect:/member-dashboard"; // ✅ MUST
        }
        return "redirect:/member-login?error=true";
    }
    
    
    @GetMapping("/member-dashboard")
    public String dashboard(HttpSession session, Model model) {

        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            return "redirect:/member-login";
        }

        model.addAttribute("name", member.getFullName());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("membershipType", member.getMembershipType());
        model.addAttribute("joinDate", member.getJoinDate());

        return "member-dashboard";
    }
    
    
    // TRAINER LOGIN PROCESS
    @PostMapping("/trainerLogin")
    public String trainerLogin(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session) {

        Trainer trainer = trainerRepo.findByEmailAndPassword(email, password);

        if (trainer != null) {
        	 session.setAttribute("trainerName", trainer.getName()); // ✅ save
            return "redirect:/trainer-dashboard";
        }

        return "redirect:/trainer-login?error";
    }
    
    @GetMapping("/trainer-dashboard")
    public String trainerDashboard(HttpSession session, Model model) {

        String name = (String) session.getAttribute("trainerName");
        model.addAttribute("name", name);

        return "trainer-dashboard";
    }

}