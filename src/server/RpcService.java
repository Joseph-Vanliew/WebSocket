package server;

import rpc.RpcInterface;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RpcService implements RpcInterface {

    @Override
    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());  // Return the current time
    }

    @Override
    public String capitalize(String input) {
        return input.toUpperCase();  // Capitalize the input string
    }
}
