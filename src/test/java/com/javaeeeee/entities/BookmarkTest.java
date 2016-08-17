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

import static com.javaeeeee.entities.UserTest.EMPTY_ERROR_MESSAGE;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Bookmark entity test.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookmarkTest {

    /**
     * A user for testing.
     */
    private static final User USER = new User("Phil", "1");
    /**
     * A test bookmark URL.
     */
    private static final String BM_URL = "http://github.com";
    /**
     * Error message for incorrect a argument value.
     */
    public static final String EMPTY_ERROR_MESSAGE
            = "[Assertion failed] - this String argument must have length; "
            + "it must not be null or empty";
    /**
     * JUnit Rule.
     */
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Provide test implementation of EntityManager.
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * Test constructor URL is null.
     */
    @Test
    public void createWhenUrlIsNullShouldThrowException() {
        expectException();
        new Bookmark(null, "");
    }

    /**
     * Test constructor URL is empty.
     */
    @Test
    public void createWhenUrlIsEmptyShouldThrowException() {
        expectException();
        new Bookmark("", null);
    }

    /**
     * Test setter URL is null.
     */
    @Test
    public void modifyWhenUrlIsNullShouldThrowException() {
        expectException();
        Bookmark bookmark = new Bookmark(BM_URL, "");
        bookmark.setUrl(null);
    }

    /**
     * Test setter URL is empty.
     */
    @Test
    public void modifyWhenUrlIsEmptyShouldThrowException() {
        expectException();
        Bookmark bookmark = new Bookmark(BM_URL, "");
        bookmark.setUrl("");
    }

    /**
     * Test setter user is null.
     */
    @Test
    public void modifyWhenUserIsNullShouldThrowException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("[Assertion failed] - "
                + "this argument is required; it must not be null");
        Bookmark bookmark = new Bookmark(BM_URL, "");
        bookmark.setUser(null);
    }


    /**
     * Test, that entity is saved.
     */
    @Test
    public void saveShouldPersistData() {
        entityManager.persist(USER);
        Bookmark bookmark = new Bookmark(BM_URL, "");
        bookmark.setUser(USER);
        bookmark = entityManager.persistFlushFind(bookmark);
        Assert.assertEquals(BM_URL, bookmark.getUrl());
    }

    /**
     * Method that provides exception expectation settings.
     */
    private void expectException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(EMPTY_ERROR_MESSAGE);
    }
}
