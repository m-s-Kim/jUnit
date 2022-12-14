package site.metacoding.junitproject.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest // DB와관련된컴포넌트만 메모리 등록
public class BookRepositoryTest {

    // 테스트메서드 하나후실행 종료되면 데이터 초기화 @Transactional 어노테이션
    // 1건 , 2건 트랜잭션 종류 후 데이터 초기화 기본키 auto_increment 초기화 안됌

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach // 테스트시작전 한번 beforeeach 은 테스트 건당
    public void dataSetReady() {

        String title = "junit";
        String author = "getInThere";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);

    }

    @Test
    // @Order(1) 순서등록
    public void 책등록_test() {
        // given (데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)

        assertEquals(title, bookPS.getTitle());

        assertEquals(author, bookPS.getAuthor());

        System.out.println("======title" + bookPS.getTitle());

    } // 트랜잭션 종료 (저장된 데이터를 초기화함)

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책목록보기_test() {

        String title = "junit";
        String author = "getInThere";

        // when (테스트 실행)

        List<Book> bookPS = bookRepository.findAll();

        System.out.println("===============사이즈 :" + bookPS.size());

        System.out.println(bookPS.size());

        assertEquals(author, bookPS.get(0).getAuthor());
        assertEquals(title, bookPS.get(0).getTitle());
    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책삭제_test() {
        Long id = 1L;

        bookRepository.deleteById(id);

        assertFalse(bookRepository.findById(id).isPresent());

    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책수정_test() {
        Long id = 1L;
        String title = "TEST";
        String author = "나다";

        Book book = new Book(id, title, author);

        bookRepository.findAll().stream()
                .forEach((b) -> {
                    System.out.println(b.getId());
                    System.out.println(b.getAuthor());
                    System.out.println(b.getTitle());
                    System.out.println("================");

                });

        bookRepository.save(book);
        System.out.println("dfdsafdsfadsf");
        bookRepository.findAll().stream()
                .forEach((b) -> {
                    System.out.println(b.getId());
                    System.out.println(b.getAuthor());
                    System.out.println(b.getTitle());
                    System.out.println("================");

                });

    }

}
