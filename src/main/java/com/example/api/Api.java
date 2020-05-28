package com.example.api;

/**
 * @author sanghaklee
 */
public class Api {
    private String greetingMsg = "Hello ";
    private String name;

    public Api(String name) {
        this.name = name;
    }

    /**
     * sum
     * @param x number
     * @param y number
     * @return x+y
     */
    public int sum(int x, int y) {
        return x + y;
    }

    public String getName() {
        return this.name;
    }

    public String greeting() {
        return this.greetingMsg + this.name;
    }
}
