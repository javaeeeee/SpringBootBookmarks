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

import com.javaeeeee.entities.Bookmark;
import com.javaeeeee.entities.User;
import com.javaeeeee.repositories.BookmarksRepository;
import com.javaeeeee.repositories.UsersRepository;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * A class to test bookmarks controller.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@RunWith(SpringRunner.class)
@WebMvcTest(BookmarksController.class)
public class BookmarksControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookmarksRepository bookmarksRepository;

    @MockBean
    private UsersRepository usersRepository;

    /**
     * Test of getAllBookmarks method, of class BookmarksController.
     */
    @Test
    public void testGetAllBookmarks() throws Exception {
    }

    /**
     * Test of getBookmark method, of class BookmarksController.
     */
    @Test
    public void testGetBookmark() throws Exception {
        final String username = "Phil";
        final String url = "http://economist.com";
        final int bookmarkId = 1;
        final User user = new User(username, "1");
        final Bookmark bookmark = new Bookmark(url, "Cool reading.");
        bookmark.setId(bookmarkId);
        bookmark.setUser(user);
        BDDMockito
                .given(usersRepository.findByUsername(username))
                .willReturn(Optional.of(user));
        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(1, username))
                .willReturn(Optional.of(bookmark));

        mvc.perform(MockMvcRequestBuilders
                .get("/" + username + "/bookmarks/" + bookmarkId)
        //.accept(MediaType.TEXT_PLAIN)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(bookmarkId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url",
                        Matchers.is(url)));

    }

    /**
     * Test of addBookmark method, of class BookmarksController.
     */
    @Test
    public void testAddBookmark() throws Exception {
    }

}
