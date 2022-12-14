package site.metacoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.domain.Book;

@NoArgsConstructor
@Getter
public class BookRespDto {
    private Long id;
    private String title;
    private String author;

    @Builder
    public BookRespDto(long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // public BookRespDto toDto(Book bookPS) {
    // this.id = bookPS.getId();
    // this.title = bookPS.getTitle();
    // this.author = bookPS.getAuthor();
    // return this;
    // }

    // public static BookRespDto toDto(Book bookPS) {
    // BookRespDto dto = new BookRespDto();
    // dto.id = bookPS.getId();
    // dto.title = bookPS.getTitle();
    // dto.author = bookPS.getAuthor();
    // return dto;
    // }
}
