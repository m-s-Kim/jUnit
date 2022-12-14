package site.metacoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMRespDto<T> {
    private Integer code; // 1성공 -1 실패
    private String msg;
    private T body;

    @Builder
    public CMRespDto(Integer code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;

    }
}
