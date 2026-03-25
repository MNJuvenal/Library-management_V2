package com.example.library.controller;

import com.example.library.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoanController {

    private final LibraryService libraryService;

    public LoanController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/loans")
    public String listLoans(Model model) {
        model.addAttribute("loans", libraryService.getAllLoans());
        model.addAttribute("books", libraryService.getAllBooks());
        model.addAttribute("members", libraryService.getAllMembers());
        return "loans";
    }

    @PostMapping("/loans/borrow")
    public String borrowBook(@RequestParam String bookId,
                             @RequestParam String memberId,
                             RedirectAttributes redirectAttributes) {
        try {
            libraryService.borrowBook(bookId, memberId);
            redirectAttributes.addFlashAttribute("success", "Book borrowed successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/loans";
    }

    @PostMapping("/loans/return")
    public String returnBook(@RequestParam String bookId,
                             @RequestParam String memberId,
                             RedirectAttributes redirectAttributes) {
        try {
            libraryService.returnBook(bookId, memberId);
            redirectAttributes.addFlashAttribute("success", "Book returned successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/loans";
    }
}
