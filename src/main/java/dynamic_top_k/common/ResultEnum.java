package dynamic_top_k.common;

public enum ResultEnum {

    SUCCESS("0000"),
    BAD_REQUEST("0001"),
    NOT_FOUND("0002");

    private final String code;

    ResultEnum(String code) {
        this.code = code;
    }

    public static String getMsg(ResultEnum code) {
        return switch (code) {
            case SUCCESS -> "OK";
            case BAD_REQUEST -> "BAD REQUEST";
            case NOT_FOUND -> "NOT FOUND";
        };
    }

    public String getCode() {
        return code;
    }
}
