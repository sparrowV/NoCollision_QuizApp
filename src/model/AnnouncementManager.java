package model;

import database.bean.Announcement;
import database.dao.AnnouncementDAO;

import java.util.List;

public class AnnouncementManager {

	private AnnouncementDAO annDAO;

	public AnnouncementManager(AnnouncementDAO dao) {
		annDAO = dao;
	}

	public void addAnnouncement(Announcement announcement) {
		annDAO.addAnnouncement(announcement);
	}


	public List<Announcement> getAnnouncements(int userId) {
		return annDAO.getAnnouncements(userId);
	}

	public Announcement getLastAnnouncement() {
		return annDAO.getLastAnnouncement();
	}

}
