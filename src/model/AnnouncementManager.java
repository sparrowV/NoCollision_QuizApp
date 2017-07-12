package model;

import database.bean.Announcement;
import database.dao.AnnouncementDAO;

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

}
