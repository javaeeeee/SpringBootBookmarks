/*
 * The MIT License
 *
 * Copyright 2016 Dmitry Noranovich <javaeeeee at gmail dot com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.javaeeeee.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is a simple REST controller. It's method prints a simple "Hello
 * world" message.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@RequestMapping("/hello")
@RestController
public class HelloController {

    /**
     * A simple greeting.
     */
    public static final String GREETING = "Hello Spring Boot World";

    /**
     * The resource method returns a greeting.
     *
     * @return a greeting.
     */
    //@RequestMapping
    public String getGreeting() {
        return GREETING;
    }

    /**
     * The resource method with a path parameter to return customized greeting.
     *
     * @param name The name of a person to greet.
     * @return Customized greeting.
     */
    @RequestMapping("/{name}")
    public String getPathParamGreeting(@PathVariable String name) {
        return "Hello " + name;
    }

    /**
     * The resource method with a query parameter to return customized greeting.
     *
     * @param name The name of a person to greet.
     * @return Customized greeting.
     */
    @RequestMapping
    public String getQueryParamGreeting(@RequestParam(value = "name", required = false) String name) {
        if (name != null) {
            return "Hello " + name;
        } else {
            return GREETING;
        }
    }
}
