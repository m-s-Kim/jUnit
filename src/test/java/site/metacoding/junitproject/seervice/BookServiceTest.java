package site.metacoding.junitproject.seervice;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Arrays;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.BDDMockito.Then;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.BookRespDto;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    // @Autowired // DI
    // BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록TEST() {
        /* 문제점 서비스만 테스트하고싶은데레포지토리 레이어가 */
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("Test");
        dto.setAuthor("me");

        // stub [가설]
        // MailSenderStub mailSenderStub = new MailSenderStub();
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);
        // when
        // BookService bookService = new BookService(bookRepository, mailSenderStub);
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        // Then
        // assertEquals(dto.getTitle(), bookRespDto.getTitle());
        // assertEquals(dto.getAuthor(), bookRespDto.getAuthor());

        // 1.기대값 , 2실제값
        // assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    public void 책목록보기_테스트() {

        // given

        // stub

        // when
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit강의", "메타코딩"));
        books.add(new Book(2L, "spring강의", "메타코딩"));

        when(bookRepository.findAll()).thenReturn(books);

        List<BookRespDto> bookRespDtosList = bookService.책목록보기();

        bookRespDtosList.stream().forEach(dto -> {
            System.out.println(dto.getId());
            System.out.println(dto.getTitle());
            System.out.println(dto.getAuthor());

        });

        // then
        assertThat(bookRespDtosList.get(0).getTitle()).isEqualTo("junit강의");
    }

    // 책한건보기

    @Test
    public void 책한건보기() {
        Long id = 1L;

        Book book = new Book(1L, "Junit강의", "코딩");
        Optional<Book> bookOP = Optional.of(book);

        when(bookRepository.findById(id)).thenReturn(bookOP);

        BookRespDto bookRespDto = bookService.책한권보기(id);

        assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
    }

    // 책삭제
    @Test
    public void 책수정하기() {

        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("spring강의");
        dto.setAuthor("겟인데어");

        // stub
        Book book = new Book(1L, "junit강의", "메타코딩");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책수정(id, dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
    }

}
