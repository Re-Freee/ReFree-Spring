package backend.refree.infra.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponse extends BasicResponse {

    private int code;
    private String message;

    public SingleResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
