package org.freeuni.homeworker.server.model.managers.categories;

import org.freeuni.homeworker.server.model.objects.category.Category;

import java.util.List;

public interface CategoryManager {
    void add(Category category);

    void removeById(long id);

    Category getById(long id);
}
