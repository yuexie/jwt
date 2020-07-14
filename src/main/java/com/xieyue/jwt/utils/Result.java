package com.xieyue.jwt.utils;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @param <T>
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4223093271408401375L;

    /**
     * 成功
     */
    public static final String OK = "ok";
    /**
     * 内部服务错误
     */
    public static final String INTERNAL_SERVER_ERROR = "internal-server-error";

    /**
     *
     */
    public static final String INTERNAL_SERVER_ERROR_MSG = "内部服务错误";

    /**
     * 错误的请求
     */
    public static final String BAD_REQUEST = "bad-request";
    /**
     * 未授权
     */
    public static final String UNAUTHORIZED = "unauthorized";
    /**
     * 未找到
     */
    public static final String NOT_FOUND = "not-found";
    /**
     * 请求超时
     */
    public static final String REQUEST_TIMEOUT = "request-timeout";
    /**
     * 冲突
     */
    public static final String CONFLICT = "conflict";
    /**
     * 未实现
     */
    public static final String NOT_IMPLEMENTED = "not-implemented";
    /**
     * 服务不可用
     */
    public static final String SERVICE_UNAVAILABLE = "service-unavailable";
    /**
     * 错误的影响行数
     */
    public static final String ERROR_ROWS_AFFECTED = "error-rows-affected";
    /**
     * 错误的状态
     */
    public static final String ERROR_STATUS = "error-status";
    /**
     * 重复消费
     */
    public static final String REPETITION_CONSUME = "repetition-consume";

    /**
     *
     */
    private String code = OK;
    /**
     *
     */
    private String msg;
    /**
     *
     */
    private T result;

    /**
     *
     */
    public Result() {

    }

    /**
     *
     *
     * @param code
     */
    public Result(String code) {
        this.code = code;
    }

    /**
     *
     *
     * @param code
     * @param msg
     */
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     *
     *
     * @return 返回true说明ok，否则返回false.
     */
    public boolean wasOk() {
        return OK.equals(code);
    }

    /**
     *
     * @return
     */
    public boolean wasNotFound() {
        return Objects.equals(NOT_FOUND, code);
    }

    /**
     *
     * @return
     */
    public boolean wasBadRequest() {
        return Objects.equals(BAD_REQUEST, code);
    }

    public <TT> Result<TT> transfer() {
        Result<TT> target = new Result<>();
        target.setCode(this.code);
        target.setMsg(this.msg);
        return target;
    }

    public <TT> Result<TT> transfer(Class<TT> clazz) {
        Result<TT> target = transfer();
        T result0;
        if(clazz != null && (result0 = this.result) != null && clazz.isInstance(result0)) {
            target.setResult((TT)result0);
        }
        return target;
    }

    public <TT> Result<TT> transfer(Function<T, TT> delegate) {
        Result<TT> target = transfer();
        T result0;
        if(delegate != null && (result0 = this.result) != null) {
            target.setResult(delegate.apply(result0));
        }
        return target;
    }

    /**
     *
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> error() {
        return new Result<T>(INTERNAL_SERVER_ERROR);
    }

    /**
     *
     *
     * @param <T>
     * @param code
     * @return
     */
    public static <T> Result<T> error(String code) {
        return new Result<T>(code);
    }

    /**
     *
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> errorWithMsg() {
        return new Result<T>(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }

    /**
     *
     *
     * @param <T>
     * @param code
     * @param msg
     * @return
     */
    public static <T> Result<T> errorWithMsg(String code, String msg) {
        return new Result<T>(code, msg);
    }

    /**
     *
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> result() {
        return new Result<T>();
    }

    /**
     *
     *
     * @param <T>
     * @param result
     * @return
     */
    public static <T> Result<T> result(T result) {
        Result<T> response = new Result<T>();
        response.setResult(result);
        return response;
    }

    public static <R> Result<R> ofBadRequest() {
        return errorWithMsg(BAD_REQUEST, "bad request");
    }

    public static <R> Result<R> ofBadRequest(String message) {
        return errorWithMsg(BAD_REQUEST, message);
    }

    public static <R> Result<R> ofNotFound() {
        return errorWithMsg(NOT_FOUND, "not found");
    }

    public static <R> Result<R> ofNotFound(Object object) {
        return errorWithMsg(NOT_FOUND, "The object by " + object + " is not found.");
    }

    /**
     *
     *
     * @param source
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> copy(Result<?> source) {
        if(source == null) {
            throw new IllegalArgumentException();
        }
        Result<T> target = new Result<T>();
        target.setCode(source.getCode());
        target.setMsg(source.getMsg());
        Object result = source.getResult();
        if(result != null) {
            try {
                target.setResult((T)result);
            } catch(ClassCastException e) {
                //do nothing.
            }
        }
        return target;
    }

    @Override
    public String toString() {
        return "{code: "
                + code
                + ",\nmsg: "
                + msg
                + ",\nresult: "
                + result
                + "}";
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
}
