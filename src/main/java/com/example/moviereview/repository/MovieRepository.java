package com.example.moviereview.repository;

import com.example.moviereview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m, min(mi.inum), min(mi.imgName), avg(coalesce(r.grade,0)), count(distinct r.reviewnum), m.regDate, mi.path, min(mi.uuid) " +
            "from Movie  m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "group by m.mno, m.title, m.regDate, m.modDate, mi.movie, r.movie")
    Page<Object[]> getListPage(Pageable pageable);


    // 특정 영화의 모든 이미지와 리뷰를 가져오는 쿼리
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) " +
            "from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "where m.mno =:mno " +
            "group by mi")
    List<Object[]> getMovieWithAll(Long mno);



}
