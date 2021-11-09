package encry.entity;

import java.io.Serializable;

/**
 * @Classname OpenResultError
 * @Date 2021/9/3 18:18
 * @Created by chenrujia
 * @Description
 */
public class OpenResultError<T> implements Serializable {
    private String code;
    private String message;

    public OpenResultError() {

    }

    public OpenResultError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
