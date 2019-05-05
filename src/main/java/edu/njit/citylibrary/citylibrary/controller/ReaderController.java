package edu.njit.citylibrary.citylibrary.controller;

import edu.njit.citylibrary.citylibrary.domain.Copy;
import edu.njit.citylibrary.citylibrary.domain.Document;
import edu.njit.citylibrary.citylibrary.domain.Reader;
import edu.njit.citylibrary.citylibrary.repository.DocumentRepo;
import edu.njit.citylibrary.citylibrary.repository.ReaderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class ReaderController {

    private static final String READER_ID ="readerid";
    @Autowired
    ReaderRepo readerRepo;

    @Autowired
    DocumentRepo documentRepo;


    @GetMapping(value = "/reader")
    public String reader() {
        return "reader";
    }

    @GetMapping(value = "/readerlogin")
    public String loginReader(@RequestParam(defaultValue = "-1") int cardnumber, Model model, HttpServletRequest request) {
        List<Reader> readers = readerRepo.enterCardNo(cardnumber);
        if (readers.size()==1) {
            HttpSession session = request.getSession(false);
            if(!Objects.isNull(session)){
                session.invalidate();
            }
            session = request.getSession(true);
            session.setAttribute("readerid", cardnumber);
            return "readermenu";
        } else return "reader";
    }

    @GetMapping(value = "/search")
    public String searchDocument(@RequestParam String searchquery, Model model, HttpServletRequest request) {
        List<Document> documentList = new ArrayList<>();
        try {
            List<Document> searchDocumentById = documentRepo.searchDocumentById(Integer.valueOf(searchquery));
            List<Document> searchDocumentByPublisherId = documentRepo.searchDocumentByPublisherId(Integer.valueOf(searchquery));
            documentList.addAll(searchDocumentById);
            documentList.addAll(searchDocumentByPublisherId);
        } catch (NumberFormatException e) {
            //do nothing
        }
        List<Document> searchDocumentByTitle = documentRepo.searchDocumentByTitle(searchquery);
        documentList.addAll(searchDocumentByTitle);
        documentList = documentList.stream().distinct().collect(Collectors.toList());
        model.addAttribute("books", documentList);
        Object attribute = request.getSession().getAttribute(READER_ID);
        System.out.println(attribute);
        return "books";
    }

    @GetMapping(value = "/checkout")
    public String checkoutDocument(@RequestParam String checkoutquery, Model model, HttpServletRequest request){
        return getBooks(checkoutquery, model, request);
    }

    @GetMapping(value = "/return")
    public String returnDocument(@RequestParam int returndocument, Model model, HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(READER_ID);
        int i = readerRepo.returnDocument(Integer.valueOf(attribute.toString()), returndocument);
        List<Document> documentList = documentRepo.searchDocumentById(returndocument);
        model.addAttribute("books", documentList);
        return "books";
    }

    @GetMapping(value = "/reserve")
    public String reserveDocument(@RequestParam String checkoutquery, Model model, HttpServletRequest request){
        return getBooks(checkoutquery, model, request);
    }

    private String getBooks(String checkoutquery, Model model, HttpServletRequest request) {
        List<Copy> copyList = readerRepo.findCopy(checkoutquery);
        Copy copy = copyList.get(0);
        Object attribute = request.getSession().getAttribute(READER_ID);
        readerRepo.doCheckout(Integer.valueOf(attribute.toString()), copy.getDocId(), copy.getCopyNo(), copy.getLibId());
        List<Document> documentList = documentRepo.searchDocumentById(copy.getDocId());
        model.addAttribute("books", documentList.get(0));
        return "books";
    }

    @GetMapping(value = "/fine")
    public String totalFine(Model model, HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(READER_ID);
        List<Reader> readers = readerRepo.enterCardNo(Integer.valueOf(attribute.toString()));
        model.addAttribute("reader", readers);
        return "readerinfo";
    }

    @GetMapping(value = "/documentbypublisher")
    public String printDocumentFromPublisher(@RequestParam String document, Model model) {
        List<Document> documentList = new ArrayList<>();
        try {
            List<Document> documentListByDocumentId = documentRepo.searchDocumentById(Integer.valueOf(document));
            documentList.addAll(documentListByDocumentId);
        } catch (NumberFormatException e) {
            //do nothing
        }

        List<Document> documentListByDocumentPubName = documentRepo.searchDocumentByPubName(document);
        documentList.addAll(documentListByDocumentPubName);
        model.addAttribute("books", documentList);
        return "books";
    }

    @GetMapping(value = "/quit")
    public String quit(HttpServletRequest request){
        request.getSession().invalidate();
        return "reader";
    }
}
