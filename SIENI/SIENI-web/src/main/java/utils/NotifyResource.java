package utils;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

@PushEndpoint("/notifyNotice")
public class NotifyResource {

    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(String count) {
        return count;
    }
}
