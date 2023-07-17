package backend.refree.infra.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeneralResponse<T> extends BasicResponse {

    private int code; // 응답 코드
    private String message; // 응답 메시지
    private int count; // 데이터의 개수
    private T data;

    public GeneralResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.code = 200;
        if (data instanceof List) {
            this.count = ((List<?>) data).size();
        } else {
            this.count = 1;
        }
    }
}
