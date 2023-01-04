package com.example.moviereview.repository;

import com.example.moviereview.entity.Member;
import com.example.moviereview.entity.Movie;
import com.example.moviereview.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);


    void deleteByMember(Member member);

}
