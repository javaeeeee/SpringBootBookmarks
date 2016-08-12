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

import com.javaeeeee.entities.User;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Users repository.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
public interface UsersRepository
        extends PagingAndSortingRepository<User, Integer> {

    /**
     * The method looks for a user by username.
     *
     * @param username the name of the user to find.
     * @return Optional of a user, empty if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * The method looks for a user by username and password for authentication
     * purposes.
     *
     * @param username
     * @param password
     * @return Optional of user if found and empty Optional otherwise.
     */
    Optional<User> findByUsernameAndPassword(String username, String password);
}
