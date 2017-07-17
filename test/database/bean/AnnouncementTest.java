package database.bean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AnnouncementTest {
	Announcement announcement;

	@Before
	public void setUp() throws Exception {
		announcement = new Announcement();
	}

	@Test
	public void getAnnouncement() throws Exception {
		String text = "test";
		announcement.setAnnouncement(text);
		assertEquals(text, announcement.getAnnouncement());
	}

	@Test
	public void getAdminId() throws Exception {
		int id = 10;
		announcement.setAdminId(id);
		assertEquals(id, announcement.getAdminId());
	}

	@Test
	public void getAnnouncementId() throws Exception {
		int id = 3;
		announcement.setAnnouncementId(id);
		assertEquals(id, announcement.getAnnouncementId());
	}

}