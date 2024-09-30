package rpc;

import java.io.Serializable;

public interface RpcInterface extends Serializable {
    String getTime();  // Method to return the current time

    String capitalize(String input);  // Method to capitalize a string
}
