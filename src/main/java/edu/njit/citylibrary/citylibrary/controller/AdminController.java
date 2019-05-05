package edu.njit.citylibrary.citylibrary.controller;

import edu.njit.citylibrary.citylibrary.domain.Admin;
import edu.njit.citylibrary.citylibrary.domain.Branch;
import edu.njit.citylibrary.citylibrary.domain.Copy;
import edu.njit.citylibrary.citylibrary.domain.Document;
import edu.njit.citylibrary.citylibrary.domain.Reader;
import edu.njit.citylibrary.citylibrary.repository.AdminRepo;
import edu.njit.citylibrary.citylibrary.repository.DocumentRepo;
import edu.njit.citylibrary.citylibrary.repository.ReaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
public class AdminController {

    private static final String ADMIN_USERNAME = "adminUsername";
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    ReaderRepo readerRepo;
    @Autowired
    DocumentRepo documentRepo;

    @GetMapping(value = "/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping(value = "/adminlogin")
    public String loginAdmin(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        Admin admin = adminRepo.adminLogin(username, password);
        if (Objects.nonNull(admin)) {
            HttpSession session = request.getSession(false);
            if (!Objects.isNull(session)) {
                session.invalidate();
            }
            session = request.getSession(true);
            session.setAttribute(ADMIN_USERNAME, admin.getUsername());
            return "adminmenu";
        } else {
            return "admin";
        }
    }

    @GetMapping(value = "/adddocument")
    public String adminAdddocument(Model model) {
        List<Branch> branches = adminRepo.getBranches();
        model.addAttribute("branches", branches);
        return "adddocument";
    }

    @PostMapping(value = "/addcopy")
    public String adminAddCopy(Model model, @ModelAttribute Copy copy) {
        List<Branch> branches = adminRepo.getBranches();
        model.addAttribute("branches", branches);
        int i = adminRepo.addCopy(copy);
        if (i == 1) {
            return "adminmenu";
        } else {
            return "adddocument";
        }
    }

    @GetMapping(value = "/branches")
    public String adminGetBranches(Model model) {
        List<Branch> branches = adminRepo.getBranches();
        model.addAttribute("branches", branches);
        return "branchinfo";
    }

    @GetMapping(value = "/addreaderform")
    public String adminAddReaderForm(Model model) {
        return "addreaderform";
    }

    @GetMapping(value = "/adminmenu")
    public String adminMenu(Model model) {
        return "adminmenu";
    }

    @GetMapping(value = "/borrowerlist")
    public String adminGetTopTenBorrowers(Model model) {
        List<Reader> topTenBorrowers = readerRepo.getTopTenBorrowers();
        model.addAttribute("readers", topTenBorrowers);
        return "readerinfo";
    }

    @PostMapping(value = "/addreader")
    public String adminAddReader(Model model, @ModelAttribute Reader reader) {
        int i = adminRepo.addReader(reader);
        if (i == 1) {
            return "adminmenu";
        } else {
            return "addreaderform";
        }
    }

    @GetMapping(value = "/toptenborrowedbooklist")
    public String topTenBorrowedBookList(Model model) {
        List<Document> topTenBorrowers = documentRepo.getTopTenBorrowedBooks();
        model.addAttribute("books", topTenBorrowers);
        return "books";
    }

    @GetMapping(value = "/toptenpopularbooklist")
    public String topTenPopularBookList(Model model) {
        List<Document> topTenBorrowers = documentRepo.getTopTenBorrowedBooks();
        model.addAttribute("books", topTenBorrowers);
        return "books";
    }
}
