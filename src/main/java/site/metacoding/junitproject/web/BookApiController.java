package site.metacoding.junitproject.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;
import site.metacoding.junitproject.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 1책등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
        // System.out.println(bindingResult.hasErrors());
        System.out.println("===========================");
        System.out.println(bindingResult.hasErrors());
        System.out.println("===========================");
        BookRespDto bookRespDto = bookService.책등록하기(bookSaveReqDto);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            System.out.println(errorMap.toString());
            // return new
            // ResponseEntity<>(CMRespDto.builder().code(1).msg(errorMap.toString()).body(bookRespDto).build(),
            // HttpStatus.BAD_REQUEST);

            throw new RuntimeException(errorMap.toString());
        }

        // CMRespDto<?> cmRespDto =
        // CMRespDto.builder().code(1).msg("글저장성공").body(bookRespDto).build();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글저장성공").body(bookRespDto).build(),
                HttpStatus.CREATED);

    }

    // 2책목록
    public ResponseEntity<?> getBookList() {
        return null;
    }

    // 3책한건
    public ResponseEntity<?> gettBookOne() {
        return null;
    }

    // 4책수정
    public ResponseEntity<?> deleteBook() {
        return null;
    }

    // 5책삭제
    public ResponseEntity<?> updateBook() {
        return null;
    }

}
