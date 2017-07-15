package model;

import database.bean.Badge;
import database.dao.BadgeDAO;

import java.util.List;

public class BadgeManager {
	private BadgeDAO dao;

	public BadgeManager(BadgeDAO dao) {
		this.dao = dao;
	}

	public void addBadge(Badge badge) {
		dao.addBadge(badge);
	}

	public void updateBadgesByUserId(int userId) {
		dao.updateBadgesByUserId(userId);
	}

	public List<Badge> getBadgesByUserId(int userId) {
		return dao.getBadgesByUserId(userId);
	}

}
