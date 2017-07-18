package model;

import database.dao.CategoryDAO;
import database.dao.FriendshipDAO;

/**
 * Created by m1sho on 18.07.2017.
 */
public class CategoryManager {
	private CategoryDAO dao;

	public CategoryManager(CategoryDAO dao) {
		this.dao = dao;
	}

	public void addCategory(String newCategory) {
		this.dao.addCategory(newCategory);
	}
}
