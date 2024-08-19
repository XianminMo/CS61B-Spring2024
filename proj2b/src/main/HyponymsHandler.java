package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

public class HyponymsHandler extends NgordnetQueryHandler {

    @Override
    public String handle(NgordnetQuery q) {
        return "Hello!";
    }
}
