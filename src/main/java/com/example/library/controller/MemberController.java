package com.example.library.controller;

import com.example.library.model.Member;
import com.example.library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {

    private final LibraryService libraryService;

    public MemberController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/members")
    public String listMembers(Model model) {
        model.addAttribute("members", libraryService.getAllMembers());
        return "members";
    }

    @GetMapping("/members/new")
    public String showAddMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "add-member";
    }

    @PostMapping("/members")
    public String addMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes) {
        try {
            libraryService.addMember(member);
            redirectAttributes.addFlashAttribute("success", "Member added successfully.");
            return "redirect:/members";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/members/new";
        }
    }
}
