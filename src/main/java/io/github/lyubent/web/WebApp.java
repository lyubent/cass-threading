package io.github.lyubent.web;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.util.Map;

public class WebApp extends NanoHTTPD {

    // todo - webport should be configurable.
    private static final int WEB_PORT = 8080;

    public WebApp() {
        super(WEB_PORT);
        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException iox) {
            System.out.println("Failed to launch Web-app:\n" + iox);
        }
        System.out.println("\nWeb-app Running! Point your browsers to http://localhost:8080/ \n");
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
}
