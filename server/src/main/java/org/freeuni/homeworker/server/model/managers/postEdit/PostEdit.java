package org.freeuni.homeworker.server.model.managers.postEdit;

import org.freeuni.homeworker.server.model.objects.postEdit.PostEditObject;

/**
 * Handles the re-edit of the post
 * Author : Givi Khartashvili
 */
public interface PostEdit {
    /**
     * Replaces the existing object in database with the given new object
     * @param obj new object to replace the old object in db
     */
    void editPost(PostEditObject obj);
}
