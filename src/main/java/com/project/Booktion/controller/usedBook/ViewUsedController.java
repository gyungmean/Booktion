package com.project.Booktion.controller.usedBook;

import com.project.Booktion.model.Book;
import com.project.Booktion.model.UsedBook;
import com.project.Booktion.repository.UsedBookRepository;
import com.project.Booktion.repository.UserRepository;
import com.project.Booktion.service.UsedBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j // 로그 찍는 기능
@Controller
@RequestMapping("/used")
@RequiredArgsConstructor
public class ViewUsedController {
    private final UsedBookService usedBookService;
    private final UsedBookRepository userRepository;

    @RequestMapping("/books/{bookId}")
    public String showBookInfo(@PathVariable long bookId, Model model){
        //책 상세페이지로 이동
        UsedBook usedBook = userRepository.findById(bookId).orElse(null);
        if(usedBook == null){
            return "noBook"; //상품이 없다는 페이지로 이동
        }
        model.addAttribute("book", usedBook.getBook());
        return "used/bookInfo";
    }

    @RequestMapping("/main")
    public String showUsedMain(Model model){
        //중고책 메인화면으로 이동
        List<Book> bookList = usedBookService.getAllUsedBookList(2);
        model.addAttribute(bookList);

        //새로 등록된 중고책 list
        List<Book> newBookList = usedBookService.getNewBookList(2);
        //특가 기회 list
        List<Book> saleBookList = usedBookService.getSaleBookList(2);
        return "used/usedMain";
    }
}
