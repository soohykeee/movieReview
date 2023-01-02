package com.example.moviereview.repository;

import com.example.moviereview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m, min(mi.inum), min(mi.imgName), avg(coalesce(r.grade,0)), count(distinct r.reviewnum) " +
            "from Movie  m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m " +
            "group by m.mno, m.title, m.regDate, m.modDate, mi.movie, r.movie")
    Page<Object[]> getListPage(Pageable pageable);

}
