package pro.tacrux.security.model;

import pro.tacrux.security.util.JsonUtil;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class R<T> {

    /**
     * 状态码
     */
    private String status;

    /**
     * 获取状态。
     *
     * @return 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 状态信息,错误描述.
     */
    private String message;

    /**
     * 获取消息内容。
     *
     * @return 消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 数据.
     */
    private T data;

    /**
     * 获取数据内容。
     *
     * @return 数据
     */
    public T getData() {
        return data;
    }

    private R(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private R(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private R(String message) {
        this.message = message;
    }

    /**
     * 创建一个带有状态、消息和数据的结果对象.
     *
     * @param status  状态
     * @param message 消息内容
     * @param data    数据
     * @return 结构数据
     */
    public static <T> R<T> var(Status status, String message, T data) {
        return new R<>(status.getCode(), message, data);
    }

    /**
     * 创建一个带有状态、消息和数据的结果对象.
     *
     * @param status  状态
     * @param message 消息内容
     * @return 结构数据
     */
    public static <T> R<T> var(Status status, String message) {
        return new R<>(status.getCode(), message);
    }

    /**
     * 创建一个带有状态和数据的结果对象.
     *
     * @param status 状态
     * @param data   数据
     * @return 结构数据
     */
    public static <T> R<T> var(Status status, T data) {
        return new R<>(status.getCode(), status.getReason(), data);
    }

    /**
     * 创建一个带有状态的结果对象.
     *
     * @param status 状态
     * @return 结构数据
     */
    public static <T> R<T> var(Status status) {
        return new R<>(status.getCode(), status.getReason());
    }

    /**
     *
     */
    public static <T> R<T> ok() {
        return R.var(Status.OK);
    }

    /**
     *
     */
    public static <T> R<T> ok(T data) {
        return R.var(Status.OK, data);
    }

    /**
     *
     */
    public static <T> R<T> fail() {
        return R.var(Status.INTERNAL_SERVER_ERROR);
    }

    /**
     *
     */
    public static <T> R<T> fail(String reason) {
        return R.var(Status.INTERNAL_SERVER_ERROR, reason);
    }

    /**
     *
     */
    public static <T> R<T> fail(Status status) {
        return R.var(status);
    }

    public void writeTo(OutputStream outputStream) {
        try {
            JsonUtil.getObjectMapper().writeValue(outputStream,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum Status {

        /**
         * 状态
         */
        OK("0", "正确"), BAD_REQUEST("1400", "错误的请求"), UNAUTHORIZED("1401", "未认证"), FORBIDDEN("1403", "已经存在"), NOT_FOUND(
            "1404", "没有可用的数据"), INTERNAL_SERVER_ERROR("1500", "服务器遇到了一个未曾预料的状况"), SERVICE_UNAVAILABLE("1503",
            "服务器当前无法处理请求"), ERROR("9999", "错误");
        /**
         * 状态码,长度固定为4位的字符串.
         */
        private final String code;

        /**
         * 错误信息.
         */
        private final String reason;

        Status(String code, String reason) {
            this.code = code;
            this.reason = reason;
        }

        public String getCode() {
            return code;
        }

        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return code + ": " + reason;
        }

    }
}
