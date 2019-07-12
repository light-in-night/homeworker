package org.freeuni.homeworker.server.model.managers.categories;

import org.freeuni.homeworker.server.model.objects.category.Category;

import java.sql.SQLException;
import java.util.List;

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
     * @return
     */
    long add(Category category) throws InterruptedException, SQLException;


    /**
     * removes the category by id
     * @param id category id
     */
    void removeById(long id) throws SQLException, InterruptedException;

    /**
     * returns category by it's id
     * or null if one was not found in database.
     *
     * @param id category id
     * @return category object
     */
    Category getById(long id) throws SQLException, InterruptedException;

    /**
     * Returns every category in the database
     * @return list of categories
     */
    List<Category> getAllCategories() throws InterruptedException, SQLException;

    void modifyCategory(Category category) throws InterruptedException, SQLException;
}
