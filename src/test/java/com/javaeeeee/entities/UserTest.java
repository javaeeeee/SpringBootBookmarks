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
package com.javaeeeee.entities;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * User entity test.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

    /**
     * Test user name.
     */
    public static final String USERNAME = "Phil";
    /**
     * Test user password.
     */
    public static final String PASSWORD = "1";
    /**
     * JUnit Rule, no exceptions are expected.
     */
    /**
     * Error message for incorrect a argument value.
     */
    public static final String EMPTY_ERROR_MESSAGE
            = "[Assertion failed] - this String argument must have length; "
            + "it must not be null or empty";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * EntityManager provided by Spring for testing purposes.
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * Test how constructor treats null username values.
     */
    @Test
    public void createWhenUsernameIsNullShouldThrowException() {
        expectException();
        new User(null, PASSWORD);
    }

    /**
     * Test how constructor treats empty username values.
     */
    @Test
    public void createWhenUsernameIsEmptyShouldThrowException() {
        expectException();
        new User("", PASSWORD);
    }

    /**
     * Test how constructor treats null username values.
     */
    @Test
    public void createWhenPasswordIsNullShouldThrowException() {
        expectException();
        new User(USERNAME, null);
    }

    /**
     * Test how constructor treats empty password values.
     */
    @Test
    public void createWhenPasswordIsEmptyShouldThrowException() {
        expectException();
        new User(USERNAME, "");
    }

    /**
     * Test modify username.
     */
    @Test
    public void modifyWhenUsernameIsNullShouldThrowException() {
        expectException();
        User user = new User(USERNAME, PASSWORD);
        user.setUsername(null);
    }

    /**
     * Test modify password.
     */
    @Test
    public void modifyWhenUserPasswordIsEmptyShouldThrowException() {
        expectException();
        User user = new User(USERNAME, PASSWORD);
        user.setPassword("");
    }

    /**
     * Test add bookmark.
     */
    @Test
    public void modifyWhenBookmarkIsNullShouldThrowException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException
                .expectMessage("[Assertion failed] - "
                        + "this argument is required; it must not be null");
        User user = new User(USERNAME, PASSWORD);
        user.addBookmark(null);
    }

    /**
     * Test save data. In more elaborate case should test that the password is
     * encrypted.
     */
    @Test
    public void saveShouldPersist() {
        User user = entityManager
                .persistFlushFind(new User(USERNAME, PASSWORD));
        Assert.assertEquals(USERNAME, user.getUsername());
        Assert.assertEquals(PASSWORD, user.getPassword());
    }

    /**
     * Method that provides exception expectation settings.
     */
    private void expectException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(EMPTY_ERROR_MESSAGE);
    }
}
