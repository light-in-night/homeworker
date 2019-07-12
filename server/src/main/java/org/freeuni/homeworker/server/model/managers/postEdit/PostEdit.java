package org.freeuni.homeworker.server.model.managers.postEdit;

import org.freeuni.homeworker.server.model.objects.postEdit.PostEditObject;

import java.sql.SQLException;

/**
 * Handles the re-edit of the post
 * Author : Givi Khartashvili
 *
 * Deprecated : Instead use PostManager.updatePostContents
 */
@Deprecated
public interface PostEdit {
    /**
     * Replaces the existing object in database with the given new object
     * @param obj new object to replace the old object in db
     */
    @Deprecated
    void editPost(PostEditObject obj) throws SQLException, InterruptedException;
}
