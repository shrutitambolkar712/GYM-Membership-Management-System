package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;

@Controller
public class EmailController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MemberRepository repo;

    @GetMapping("/sendReceipt")
    public String sendReceipt(@RequestParam Long id) {

        Member member = repo.findById(id).orElse(null);

        if(member != null){

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(member.getEmail());
            message.setSubject("Gym Membership Receipt");

            message.setText(
                    "Hello " + member.getFullName() +
                    "\n\nThank you for joining our Gym 💪" +
                    "\nMembership Type: " + member.getMembershipType() +
                    "\nAmount Paid: ₹" + member.getAmount() +
                    "\nJoin Date: " + member.getJoinDate() +
                    "\n\nStay Fit!"
            );

            mailSender.send(message);
        }

        return "redirect:/receipt?id=" + id;
    }
}
