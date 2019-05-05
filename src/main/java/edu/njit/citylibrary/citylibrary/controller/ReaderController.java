package edu.njit.citylibrary.citylibrary.controller;

import edu.njit.citylibrary.citylibrary.domain.Document;
import edu.njit.citylibrary.citylibrary.repository.DocumentRepo;
import edu.njit.citylibrary.citylibrary.repository.ReaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReaderController {

    @Autowired
    ReaderRepo readerRepo;

    @Autowired
    DocumentRepo documentRepo;


    @GetMapping(value = "/reader")
    public String reader(){
        return "reader";
    }
    @GetMapping(value = "/readerlogin")
    public String loginReader(@RequestParam(defaultValue = "-1") int cardnumber, Model model){
        Boolean result = readerRepo.enterCardNo(cardnumber);
        if (result){
            return "readermenu";
        } else return "reader";
    }

    @GetMapping(value = "/search")
    public String searchDocument(@RequestParam String searchquery, Model model){
        List<Document> documentList = new ArrayList<>();
        try {
            List<Document> searchDocumentById = documentRepo.searchDocumentById(Integer.valueOf(searchquery));
            List<Document> searchDocumentByPublisherId = documentRepo.searchDocumentByPublisherId(Integer.valueOf(searchquery));
            documentList.addAll(searchDocumentById);
            documentList.addAll(searchDocumentByPublisherId);
        }catch (NumberFormatException e){
            //do nothing
        }
        List<Document> searchDocumentByTitle = documentRepo.searchDocumentByTitle(searchquery);
        documentList.addAll(searchDocumentByTitle);
        documentList = documentList.stream().distinct().collect(Collectors.toList());
        model.addAttribute("books", documentList);
        return "books";
    }

    /*@GetMapping(value = "/checkout")
    public String checkoutDocument(){

    }

    @GetMapping(value = "/return")
    public String returnDocument(){

    }

    @GetMapping(value = "/reserve")
    public String reserveDocument(){

    }

    @GetMapping(value = "/fine")
    public String totalFine(){

    }

    @GetMapping(value = "/print")
    public String printDocument(){

    }
*/
}
