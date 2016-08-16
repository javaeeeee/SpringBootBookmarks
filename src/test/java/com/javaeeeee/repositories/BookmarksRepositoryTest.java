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
package com.javaeeeee.repositories;

import com.javaeeeee.entities.Bookmark;
import com.javaeeeee.entities.User;
import java.util.Optional;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test class to test BookmarksRepository interface.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookmarksRepositoryTest {

    /**
     * Test user name.
     */
    private static final String USER_NAME = "Phil";

    /**
     * Some user for testing purposes.
     */
    private User user;
    /**
     * The URL of the test bookmark.
     */
    private static final String BM_URL = "http://url.com";
    /**
     * The description of the test bookmark.
     */
    private static final String BM_DESCRIPTION = "description";
    /**
     * A bookmark for testing purposes.
     */
    private Bookmark bookmark;
    /**
     * An entity manager provided by spring for testing.
     */
    @Autowired
    private TestEntityManager entityManager;

    /**
     * System under test.
     */
    @Autowired
    private BookmarksRepository bookmarksRepository;

    /**
     * A method to perform before class initialization.
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * A method used for before method initialization.
     */
    @Before
    public void setUp() {
        user = new User(USER_NAME, "1");
        bookmark = new Bookmark(BM_URL, BM_DESCRIPTION);
        user.addBookmark(bookmark);
        entityManager
                .persist(user);
    }

    /**
     * Do cleanup after each method.
     */
    @After
    public void tearDown() {
        entityManager.remove(user);
    }

    /**
     * Test of findByUserUsername method, of class BookmarksRepository.
     */
    @Test
    public void testFindByUserUsername() {
        Set<Bookmark> bookmarks
                = bookmarksRepository.findByUserUsername(USER_NAME);

        Assert.assertNotNull(bookmarks);
        Assert.assertFalse(bookmarks.isEmpty());
        Assert.assertEquals(1, bookmarks.size());
        Assert.assertEquals(USER_NAME,
                bookmarks.iterator().next().getUser().getUsername());

    }

    /**
     * Test of findByIdAndUserUsername method, of class BookmarksRepository.
     */
    @Test
    public void testFindByIdAndUserUsername() {
        Optional<Bookmark> optional
                = bookmarksRepository
                .findByIdAndUserUsername(bookmark.getId(), USER_NAME);
        Assert.assertNotNull(optional);
        Assert.assertTrue(optional.isPresent());
        Bookmark bm = optional.get();
        Assert.assertEquals(BM_URL, bm.getUrl());
    }

}
