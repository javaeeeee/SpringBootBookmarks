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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaeeeee.entities.Bookmark;
import com.javaeeeee.entities.User;
import com.javaeeeee.repositories.BookmarksRepository;
import com.javaeeeee.repositories.UsersRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
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

    /**
     * The Id of a test BOOKMARK.
     */
    private static final int BOOKMARK_ID = 1;
    /**
     * The URL of a test BOOKMARK.
     */
    private static final String URL = "http://economist.com";
    /**
     * Bookmark description.
     */
    private static final String BM_DESCRIPTION = "Cool reading.";
    /**
     * A test BOOKMARK.
     */
    private static final Bookmark BOOKMARK = new Bookmark(URL, BM_DESCRIPTION);
    /**
     * The name of a test USER.
     */
    private static final String USERNAME = "Phil";
    /**
     * An id of a nonexistent bookmark.
     */
    private static final int NONEXISTENT_BOOKMARK_ID = 10967876;
    /**
     * A username of a nonexistent user.
     */
    private static final String NONEXISTENT_USERNAME = USERNAME + 10967876;
    /**
     * A test USER.
     */
    private static final User USER = new User(USERNAME, "1");
    /**
     * A URL to change in PUT method.
     */
    private static final String NEW_URL = "http://time.com";
    /**
     * A JSON string for PUT method test.
     */
    private static final String JSON_DATA = String.format("{\"url\":\"%s\"}", NEW_URL);

    /**
     * Mock MVC.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Mock bookmarks repository.
     */
    @MockBean
    private BookmarksRepository bookmarksRepository;

    /**
     * Mock USER repository.
     */
    @MockBean
    private UsersRepository usersRepository;

    /**
     * Initialization method.
     */
    @BeforeClass
    public static void setUpClass() {
        BOOKMARK.setId(BOOKMARK_ID);
        BOOKMARK.setUser(USER);
    }

    /**
     * Method carries out initialization before each method.
     */
    @Before
    public void setUp() {
        BDDMockito
                .given(usersRepository.findByUsername(USERNAME))
                .willReturn(Optional.of(USER));
        BDDMockito
                .given(usersRepository.findByUsername(NONEXISTENT_USERNAME))
                .willReturn(Optional.empty());
    }

    /**
     * Test of getAllBookmarks method, of class BookmarksController for an
     * existent user.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllBookmarksShouldOk() throws Exception {
        BDDMockito.given(bookmarksRepository.findByUserUsername(USERNAME))
                .willReturn(new HashSet<>(Arrays.asList(BOOKMARK)));

        mvc.perform(MockMvcRequestBuilders.get("/" + USERNAME + "/bookmarks/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].url",
                        Matchers.is(URL)));
    }

    /**
     * Test of getAllBookmarks() method for a nonexistent user.
     *
     * @throws java.lang.Exception
     */
    public void testGetAllBookmarksNoSuchUser() throws Exception {
        BDDMockito.given(bookmarksRepository.findByUserUsername(NONEXISTENT_USERNAME))
                .willReturn(new HashSet<>());

        mvc.perform(MockMvcRequestBuilders.get("/" + NONEXISTENT_USERNAME + "/bookmarks/"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test of getBookmark method, of class BookmarksController.
     */
    @Test
    public void testGetBookmarkFound() throws Exception {
        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(BOOKMARK_ID, USERNAME))
                .willReturn(Optional.of(BOOKMARK));

        mvc.perform(MockMvcRequestBuilders
                .get("/" + USERNAME + "/bookmarks/" + BOOKMARK_ID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(BOOKMARK_ID)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url",
                        Matchers.is(URL)));

    }

    /**
     * Method tests the situation when we loo for a non-existent bookmark.
     *
     * @throws Exception
     */
    @Test
    public void testGetBookmarkNotFound() throws Exception {
        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(NONEXISTENT_BOOKMARK_ID, USERNAME))
                .willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders
                .get("/" + USERNAME + "/bookmarks/" + NONEXISTENT_BOOKMARK_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetBookmarkNoSuchUser() throws Exception {
        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(BOOKMARK_ID, NONEXISTENT_USERNAME))
                .willReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders
                .get("/" + NONEXISTENT_USERNAME + "/bookmarks/" + BOOKMARK_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test of addBookmark method, of class BookmarksController.
     */
    @Test
    public void testAddBookmark() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonData = mapper.writeValueAsString(BOOKMARK);
        mvc.perform(
                MockMvcRequestBuilders.post("/" + USERNAME + "/bookmarks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(BOOKMARK_ID)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url",
                        Matchers.is(URL)));
    }

    /**
     * Method testing editing a bookmark when a bookmark is not found.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testEditBookmarkNotFound() throws Exception {
        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(NONEXISTENT_BOOKMARK_ID, USERNAME))
                .willReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders
                .put("/" + USERNAME + "/bookmarks/" + NONEXISTENT_BOOKMARK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_DATA)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method to test editing a bookmark, happy path.
     */
    @Test
    public void testEditBookmarkShouldOk() throws Exception {

        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(BOOKMARK_ID, USERNAME))
                .willReturn(Optional.of(BOOKMARK));

        mvc.perform(MockMvcRequestBuilders
                .put("/" + USERNAME + "/bookmarks/" + BOOKMARK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_DATA))
                .andExpect(
                        MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers
                        .jsonPath("$.id", Matchers.is(BOOKMARK_ID)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.url", Matchers.is(NEW_URL)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.description",
                                Matchers.is(BM_DESCRIPTION)));

        BOOKMARK.setUrl(URL);
    }

    /**
     * Method tests Delete for a nonexistent bookmark.
     */
    @Test
    public void testDeleteBookmarkNotFound() throws Exception {
        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(NONEXISTENT_BOOKMARK_ID, USERNAME))
                .willReturn(Optional.empty());
        BDDMockito.doNothing().when(bookmarksRepository).delete(BOOKMARK);

        mvc.perform(MockMvcRequestBuilders
                .delete("/" + USERNAME + "/bookmarks/" + NONEXISTENT_BOOKMARK_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        BDDMockito
                .verify(bookmarksRepository)
                .findByIdAndUserUsername(NONEXISTENT_BOOKMARK_ID, USERNAME);
        BDDMockito.verifyNoMoreInteractions(bookmarksRepository);
    }

    /**
     * Method tests successful delete.
     */
    @Test
    public void testDeleteBookmarkHappyPath() throws Exception {
        BDDMockito
                .given(bookmarksRepository
                        .findByIdAndUserUsername(BOOKMARK_ID, USERNAME))
                .willReturn(Optional.of(BOOKMARK));
        BDDMockito.doNothing().when(bookmarksRepository).delete(BOOKMARK);

        mvc.perform(MockMvcRequestBuilders
                .delete("/" + USERNAME + "/bookmarks/" + BOOKMARK_ID))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito
                .verify(bookmarksRepository)
                .findByIdAndUserUsername(BOOKMARK_ID, USERNAME);
        BDDMockito
                .verify(bookmarksRepository).delete(BOOKMARK);
        BDDMockito.verifyNoMoreInteractions(bookmarksRepository);
    }
}
