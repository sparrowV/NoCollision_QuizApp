package database.bean;


public class Announcement {
	private int announcementId;
	private String announcement;
	private int adminId;

	public Announcement(int adminId, String announcement) {
		this.adminId = adminId;
		this.announcement = announcement;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public int getAdminId() {
		return adminId;
	}

	public int getAnnouncementId() { return announcementId; };

	public String toString() {
		return "AuthorId: " + adminId + " -- " +
				"AnnouncementServlet: " + announcement;
	}
}
