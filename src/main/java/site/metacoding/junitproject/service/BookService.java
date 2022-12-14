package site.metacoding.junitproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;

@RequiredArgsConstructor // final 있을때 무조건 주입 해줌
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 책등록 파일널 있을시 객체 생성시 반드시 값 존재해야함
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveReqDto dto) {
        Book bookPS = bookRepository.save(dto.toEntity());

        if (bookPS != null) {
            if (!mailSender.send()) {
                // throw new RuntimeErrorException(null, "메일이 전송되지 않았습니다.");
                // throw new Rui
            }
        }

        return bookPS.toDto();
        // statcic
        // return BookRespDto().toDto(bookPS);
    }

    // 책목록
    public List<BookRespDto> 책목록보기() {
        // List<BookRespDto> dtos = bookRepository.findAll().stream()
        // .map(new BookRespDto()::toDto)
        // .collect(Collectors.toList());
        List<BookRespDto> dtos = bookRepository.findAll().stream()
                // .map((bookPS) -> new BookRespDto().toDto(bookPS))
                .map(Book::toDto)
                .collect(Collectors.toList());

        dtos.stream().forEach(dto -> {
            System.out.println("x=============");
            System.out.println(dto.getTitle());
            System.out.println(dto.getAuthor());

        });

        return dtos;
    }

    // // 2. 책목록보기
    // public BookListRespDto 책목록보기() {

    // List<BookRespDto> dtos = bookRepository.findAll().stream()
    // // .map((bookPS) -> bookPS.toDto())
    // .map(Book::toDto)
    // .collect(Collectors.toList());

    // dtos.stream().forEach((b) -> {
    // System.out.println(b.getId());
    // System.out.println(b.getTitle());
    // System.out.println("============ 서비스 레이어");
    // });

    // BookListRespDto bookListRespDto =
    // BookListRespDto.builder().bookList(dtos).build();
    // return bookListRespDto;
    // }

    // List<BookRespDto> dtos = bookRepository.findAll().stream()
    // // .map((bookPS) -> bookPS.toDto())
    // .map(Book::toDto)
    // .collect(Collectors.toList());

    // 책한권보기
    public BookRespDto 책한권보기(long id) {
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()) { // 찾았다면
            Book bookPs = bookOP.get();
            // return new BookRespDto().toDto(bookOP.get());
            return bookPs.toDto();
        } else {
            throw new RuntimeException("Can`t find ID");
        }
    }

    // 책삭제

    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제(long id) {
        bookRepository.deleteById(id);
    }

    // 책수정
    public BookRespDto 책수정(long id, BookSaveReqDto dto) {
        Optional<Book> bookOP = bookRepository.findById(id);

        if (bookOP.isPresent()) {
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return bookPS.toDto();
        } else {
            throw new RuntimeException("Can`t find ID");
        }

    }// 더티체킹이 되서 업데이트 됨

}
