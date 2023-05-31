package com.project.Booktion.repository;

import com.project.Booktion.model.Book;
import com.project.Booktion.model.Review;
import com.project.Booktion.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository<Review, Long> {
    List<Review> findByBook(Book book);

    Review findByReviewIdAndClientId(Long reviewId, Long clientId);
    Review save(Review review);

    void deleteByUserIdAndReviewId(String userId, Long reviewId);

    long count();

    List<com.project.Booktion.model.Review> findByUser(User currentUser);

    void deleteById(long reviewId);

    Optional<Object> finById(long reviewId);
}
