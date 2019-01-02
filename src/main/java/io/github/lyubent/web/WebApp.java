package io.github.lyubent.web;

import fi.iki.elonen.NanoHTTPD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class WebApp extends NanoHTTPD {

    private final static Logger logger = LoggerFactory.getLogger(WebApp.class);
    // todo - webport should be configurable.
    private static final int WEB_PORT = 8080;

    public WebApp() {
        super(WEB_PORT);
        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException iox) {
            logger.error("Failed to launch Web-app:\n" + iox);
        }
        logger.info("Web-app Running! Point your browsers to http://localhost:8080/");
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
