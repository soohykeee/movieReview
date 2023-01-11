package com.example.moviereview.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//DTO와 Entity 타입을 의미한다. 제네릭 타입으로 설정하여 다양한 곳에 이용할 수 있도록 한다.
@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;      //dto리스트

    private int totalPages;         //총 페이지 번호

    private int page, size;         //현재 페이지 번호, 목록 사이즈

    private int start, end;         //시작 페이지 번호, 끝 페이지 번호

    private boolean prev, next;     //이전, 다음

    private List<Integer> pageList; //페이지 번호 목록록

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPages = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPages > tempEnd ? tempEnd : totalPages;
        next = totalPages > tempEnd;
        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());

    }

}
