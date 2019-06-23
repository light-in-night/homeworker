package org.freeuni.homeworker.server.model.managers.postCategory;

import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;

import java.sql.SQLException;
import java.util.List;

/**
 * Handles postCategory insert/update/delete into the
 * database.
 *
 * Author : Tornike Onoprishvili
 */
public interface PostCategoryManager {
    /**
     * Creates a new category
     *
     * @param postCategory new postCategory object to add
     */
    void add(PostCategory postCategory) throws InterruptedException, SQLException;

    /**
     * removes the category by id
     * @param id category id
     */
    void removeById(long id) throws InterruptedException, SQLException;

    /**
     * gets all postCategory-entries that reference the given postId.
     *
     * @param postId the postId that we are looking for
     * @return list of postsCategories that reference the given postId
     */
    List<PostCategory> getByPostId(long postId) throws InterruptedException, SQLException;


    /**
     * gets all postCategory-entries that reference the given categoryId.
     *
     * @param categoryId the categoryId that we are looking for
     * @return list of postsCategories that reference the given categoryId
     */
    List<PostCategory> getByCategoryId(long categoryId) throws InterruptedException, SQLException;

    /**
     * gets all posts that have the given category.
     * note: posts and categories have many-to-many relationship
     *
     * @param categoryId the category we are looking for
     * @return all posts with given category
     */
    List<Post> getPostsInCategory(long categoryId) throws InterruptedException, SQLException;

    /**
     * gets all categories of given post.
     *
     * note: posts and categories have many-to-many relationship
     *
     * @param postId the post we are looking for
     * @return list of all categories of given post
     */
    List<Category> getCategoriesOfPost(long postId) throws InterruptedException, SQLException;
}
