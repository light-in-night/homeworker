package org.freeuni.homeworker.server.model.managers.categories;

import org.freeuni.homeworker.server.model.objects.category.Category;

/**
 * Handles category insert/update/delete into the
 * database.
 *
 * Author : Tornike Onoprishvili
 */
public interface CategoryManager {
    /**
     * Creates a new category
     *
     * @param category new category object to add
     */
    void add(Category category);


    /**
     * removes the category by id
     * @param id category id
     */
    void removeById(long id);

    /**
     * returns category by it's id
     * or null if one was not found in database.
     *
     * @param id category id
     * @return category object
     */
    Category getById(long id);
}
