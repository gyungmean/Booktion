package com.project.Booktion.service;

import com.project.Booktion.model.Book;

import java.util.ArrayList;
import java.util.List;

import com.project.Booktion.model.Cart;
import com.project.Booktion.model.CartItem;
import com.project.Booktion.repository.BookRepository;
import com.project.Booktion.repository.CartItemRepository;
import com.project.Booktion.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class CartService {
    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final HttpSession session;

    public List<Book> getCartItems() {
        return bookRepository.findAll();
    }

    public boolean removeCartItem(long bookId) {
        return true;
    }
    public void updateCartSession(HttpSession session) {
        session.setAttribute("cartItems", getCartItems());
    }

    public String addCartItem(long cartId, CartItem cartItem, Model model) {
        Cart cart = cartRepository.findById(cartId)
                .orElse(null);

        if (cart == null) {
            model.addAttribute("errorMessage", "카트를 찾을 수 없습니다.");
            return "cart";
        }

        Book book = bookRepository.findById(cartItem.getBook().getBookId())
                .orElse(null);

        if (book == null) {
            model.addAttribute("errorMessage", "책을 찾을 수 없습니다.");
            return "book";
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setCartId(cart.getCartId());
        newCartItem.setBook(book);
        newCartItem.setQuantity(cartItem.getQuantity());

        cartItemRepository.save(newCartItem);
        return "cart";
    }

    public String removeCart(long cartItemId) {
        cartItemRepository.deleteByCartId(cartItemId);
        return "cart";
    }

    public void updateCartItemQuantity(long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

        if(cartItem!=null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    public void removeCartItems(List<Long> cartItemIds) {
        List<Book> cartItems = getCartItems();
        List<Book> itemsToRemove = new ArrayList<>();
        for (Book cartItem : cartItems) {
            if (cartItemIds.contains(cartItem.getBookId())) {
                itemsToRemove.add(cartItem);
            }
        }
        cartItems.removeAll(itemsToRemove);
        session.setAttribute("cartItems", cartItemIds);
    }

    public List<Long> getCartItemIds() {
        List<Book> cartItems = getCartItems();
        List<Long> cartItemIds = new ArrayList<>();
        for (Book cartItem : cartItems) {
            cartItemIds.add(cartItem.getBookId());
        }
        return cartItemIds;
    }

    public Cart getCart(String clientId) {
        // 클라이언트 ID를 사용하여 해당 클라이언트의 카트를 조회하는 로직을 구현합니다.
        // 실제로는 데이터베이스에서 해당 클라이언트의 카트 정보를 조회하는 작업을 수행해야 합니다.
        // 임시로 빈 카트를 반환하도록 구현해보겠습니다.
        Cart cart = cartRepository.findByClientId(clientId);
        return cart;
    }
}
