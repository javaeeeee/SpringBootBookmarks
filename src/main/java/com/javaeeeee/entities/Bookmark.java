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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * An entity to store bookmarks.
 *
 * @author Dmitry Noranovich <javaeeeee at gmail dot com>
 */
@Entity
@Table(name = "bookmarks", catalog = "bookmarks", schema = "")
@NamedQueries({
    @NamedQuery(name = "Bookmark.findAll", query = "SELECT b FROM Bookmark b"),
    @NamedQuery(name = "Bookmark.findById", query = "SELECT b FROM Bookmark b "
            + "WHERE b.id = :id"),
    @NamedQuery(name = "Bookmark.findByUrl", query = "SELECT b FROM Bookmark b "
            + "WHERE b.url = :url"),
    @NamedQuery(name = "Bookmark.findByDescription", query = "SELECT b FROM "
            + "Bookmark b WHERE b.description = :description"),
    @NamedQuery(name = "Bookmark.findByUserId", query = "SELECT b FROM "
            + "Bookmark b WHERE b.user.id = :userId")})
public class Bookmark implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * The auto-generated id of a bookmark.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    /**
     * The URL of a bookmark.
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String url;
    /**
     * The description of a bookmark.
     */
    @Size(max = 2048)
    @Column(length = 2048)
    private String description;
    /**
     * The owner of a bookmark.
     */
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id", nullable = false)
    @ManyToOne
    private User user;

    /**
     * The no-argument constructor.
     */
    public Bookmark() {
    }

    /**
     * A constructor to create a bookmark.
     *
     * @param id
     * @param url
     * @param user
     */
    public Bookmark(String url, User user) {
        this.url = url;
        this.user = user;
    }

    /**
     * ID getter.
     *
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * ID setter
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bookmark)) {
            return false;
        }
        Bookmark other = (Bookmark) object;
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bookmark{" + "id=" + id + ", url=" + url
                + ", description=" + description + ", user=" + user + '}';
    }

}
