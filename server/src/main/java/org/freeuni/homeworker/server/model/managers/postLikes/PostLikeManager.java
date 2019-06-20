package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.objects.postLike.PostLike;

/**
 * Manages the postLike entries in the database
 *
 * Author : Tornike kechakhmadze
 */
public interface PostLikeManager {

    /**
     * Adds a new entry in postLike table. Adds Like Information In DataBase.
     *
     * note: postLike connects user to post. many to many relationship.
     * @param postLikeObject new entry to postLike object
     * @return true on successful addition false otherwise
     */
    boolean like(PostLike postLikeObject); // User Like Method

    /**
     * Removes Post Like Information From DataBase
     * note: postLike connects user to post. many to many relationship.
     *
     * @param postLikeObject new entry to postLike object
     * @return true on successful addition false otherwise
     */
    boolean unLike(PostLike postLikeObject); // User Unlike Method

}