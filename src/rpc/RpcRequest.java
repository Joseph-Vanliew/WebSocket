package rpc;


import java.io.Serial;
import java.io.Serializable;

public class RpcRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String input;
    private String method;
    // The string sent by the client

    // Getters and setters
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
