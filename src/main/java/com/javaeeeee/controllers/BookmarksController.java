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
import com.javaeeeee.exception.BookmarkNotFoundException;
import com.javaeeeee.exception.UserNotFoundException;
import com.javaeeeee.repositories.BookmarksRepository;
import com.javaeeeee.repositories.UsersRepository;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller that exposes resource methods to work with bookmarks for a
 * particular user.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@RequestMapping("/{username}/bookmarks")
@RestController
public class BookmarksController {

    /**
     * The repository to work with bookmarks.
     */
    private final BookmarksRepository bookmarksRepository;
    /**
     * The repository to work with users.
     */
    private final UsersRepository usersRepository;

    /**
     * The constructor which allows to inject repositories.
     *
     * @param bookmarksRepository The repository to work with bookmarks.
     * @param usersRepository The repository to work with users.
     */
    @Autowired
    public BookmarksController(BookmarksRepository bookmarksRepository,
            UsersRepository usersRepository) {
        this.bookmarksRepository = bookmarksRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * A method to return bookmarks for a particular user.
     *
     * @param username the name of a user whose bookmarks are listed.
     * @return list of user's bookmarks.
     * @throws java.lang.Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public Set<Bookmark> getAllBookmarks(@PathVariable String username) throws Exception {
        validateUser(username);
        return bookmarksRepository.findByUserUsername(username);
    }

    /**
     * A method to find a bookmark by id.
     */
    @RequestMapping(value = "/{bookmarkId}", method = RequestMethod.GET)
    public Bookmark getBookmark(@PathVariable String username,
            @PathVariable Integer bookmarkId) throws UserNotFoundException, BookmarkNotFoundException {
        validateUser(username);
        Optional<Bookmark> optional
                = bookmarksRepository
                .findByIdAndUserUsername(bookmarkId, username);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new BookmarkNotFoundException(bookmarkId.toString());
        }
    }

    /**
     * A method to add a bookmark.
     */
    @RequestMapping(method = RequestMethod.POST)
    Bookmark addBookmark(@PathVariable String username,
            @RequestBody Bookmark bookmark) throws UserNotFoundException {
        Optional<User> optional = usersRepository.findByUsername(username);
        if (optional.isPresent()) {
            User user = optional.get();
            user.addBookmark(bookmark);
            bookmark.setUser(user);
            bookmarksRepository.save(bookmark);
            return bookmark;
        } else {
            throw new UserNotFoundException(username);
        }
    }

    /**
     * A method to edit a bookmark.
     */
    /**
     * A method to check if a user exists.
     *
     * @param username username.
     * @throws UserNotFoundException thrown if user doesn't exist.
     */
    private void validateUser(String username) throws UserNotFoundException {
        if (!usersRepository.findByUsername(username).isPresent()) {
            throw new UserNotFoundException(username);
        }
    }
}
