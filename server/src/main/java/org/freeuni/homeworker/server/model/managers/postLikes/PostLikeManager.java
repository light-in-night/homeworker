package org.freeuni.homeworker.server.model.managers.postLikes;

import org.freeuni.homeworker.server.model.objects.postLike.PostLike;

public interface PostLikeManager {

    boolean like(PostLike postLikeObject); // User Like Method
    boolean unLike(PostLike postLikeObject); // User Unlike Method

}