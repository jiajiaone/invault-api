package encry.entity;

import java.io.Serializable;
import java.util.Random;

/**
 * @Classname RPCResult
 * @Date 2021/9/3 17:15
 * @Created by chenrujia
 * @Description
 */
public class RPCResult<T> implements Serializable {
    private String jsonrpc;
    private String method;
    private T params;
    private Integer id;

    public RPCResult() {
    }

    public static <T> RPCResult<T> placeDate(String method,T params) {
        return new RPCResult<>(method, params, null);
    }

    public static <T> RPCResult<T> placeDate(String method,T params,Integer id) {
        return new RPCResult<>(method, params, id);
    }

    public RPCResult(String method, T params, Integer id) {
        this.jsonrpc = "2.0";
        this.method = method;
        this.params = params;
        if (id != null){
            this.id = id;
        }else {
            this.id = new Random().nextInt(10000);
        }
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

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
