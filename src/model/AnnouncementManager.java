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
		try {
			annDAO.addAnnouncement(announcement);
		} catch (Exception e) {
			System.out.println("Something went wrong adding announcement");
		}
	}


	public List<Announcement> getAnnouncements(int userId) {
		return annDAO.getAnnouncements(userId);
	}

}
