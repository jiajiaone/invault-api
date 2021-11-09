package encry.entity;

import java.io.Serializable;

/**
 * @Classname OpenResult
 * @Date 2021/9/3 16:41
 * @Created by chenrujia
 * @Description oepnapi返回参数
 */
public class OpenResult<T> implements Serializable {
    private String jsonrpc;
    private String method;
    private T result;
    private Integer id;
    private OpenResultError error;

    private static String rpc = "2.0";

    public OpenResult() {

    }

    public OpenResult(T result) {
        this.result = result;
    }

    public OpenResult(OpenResultError error) {
        this.error = error;
    }

    public OpenResult(String jsonrpc, String method, T result, Integer id) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.result = result;
        this.id = id;
    }

    public OpenResult(String jsonrpc, String method, OpenResultError error, Integer id) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.error = error;
        this.id = id;
    }

    public OpenResult(String jsonrpc, OpenResultError error, Integer id) {
        this.jsonrpc = jsonrpc;
        this.error = error;
        this.id = id;
    }

    public static <T> OpenResult<T> newSuccess(T result) {
        return new OpenResult<>(result);
    }

    public static <T> OpenResult<T> newError(OpenResultError error) {
        return new OpenResult<>(error);
    }

    public static <T> OpenResult<T> newOpenError(OpenResultError error) {
        return new OpenResult<>(rpc,error,null);
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OpenResultError getError() {
        return error;
    }

    public void setError(OpenResultError error) {
        this.error = error;
    }
}
