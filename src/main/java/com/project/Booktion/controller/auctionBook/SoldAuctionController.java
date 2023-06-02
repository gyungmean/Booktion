package com.project.Booktion.controller.auctionBook;

import com.project.Booktion.model.AuctionBookOrder;
import com.project.Booktion.model.User;
import com.project.Booktion.service.AuctionBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
// 팔린 경매책
@Slf4j // 로그 찍는 기능
@Controller
@RequestMapping("/user/sold")
@RequiredArgsConstructor
public class SoldAuctionController {

    AuctionBookService auctionS;
    @GetMapping
    public String SellingList(HttpServletRequest request, Model model){
        Long userId =  (Long) request.getAttribute("userId");

        List<AuctionBookOrder> orderList = auctionS.findOrderBySeller(userId);
        model.addAttribute("orderList", orderList);
        return "/myPage/auction/sold";
    }
}