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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A class to test UsersRepository.
 * https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4#testing-the-jpa-slice
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UsersRepositoryTest {

    /**
     * Test entity manager provided by Spring.
     */
    @Autowired
    private TestEntityManager entityManager;
    /**
     * System under test.
     */
    @Autowired
    private UsersRepository usersRepository;

    @Before
    public void setUpMethod() {
        entityManager.getEntityManager().createQuery("delete from User u").executeUpdate();
    }

    /**
     * A method to test that findByUsername returns a user.
     */
    @Test
    public void findByUsernameShouldReturnAUser() {
        final String name = "Phil";
        entityManager.persist(new User(name, "1"));
        Optional<User> optional = usersRepository.findByUsername(name);

        Assert.assertTrue(optional.isPresent());
        User user = optional.get();

        Assert.assertEquals(name, user.getUsername());
    }

    /**
     * A methods tests that empty optional is returned is user is nonexistent.
     */
    @Test
    public void findByUsernameShouldReturnEmpty() {
        final String name = "Phil";
        entityManager.persist(new User(name, "1"));
        Optional<User> optional = usersRepository.findByUsername(name + "mmm");

        Assert.assertFalse(optional.isPresent());
    }
}
