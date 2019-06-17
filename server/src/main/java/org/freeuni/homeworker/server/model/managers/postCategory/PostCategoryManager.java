package org.freeuni.homeworker.server.model.managers.postCategory;

import org.freeuni.homeworker.server.model.objects.category.Category;
import org.freeuni.homeworker.server.model.objects.post.Post;
import org.freeuni.homeworker.server.model.objects.postCategory.PostCategory;

import java.util.List;

public interface PostCategoryManager {
    void add(PostCategory postCategory);

    void removeById(long id);

    List<PostCategory> getByPostId(long postId);

    List<PostCategory> getByCategoryId(long categoryId);

    List<Post> getPostsInCategory(long categoryId);

    List<Category> getCategoriesOfPost(long postId);
}
