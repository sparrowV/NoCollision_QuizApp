package database.bean;

public class Badge {
	private int badgeId;
	private String badgeName;
	private String description;
	private int quizzesNeeded;
	private double xpNeeded;
	private int categoryId;

	public Badge(int badgeId, String badgeName, String description, int quizzesNeeded, double xpNeeded, int categoryId) {
		this.badgeId = badgeId;
		this.badgeName = badgeName;
		this.description = description;
		this.quizzesNeeded = quizzesNeeded;
		this.xpNeeded = xpNeeded;
		this.categoryId = categoryId;
	}

	public int getBadgeId() {
		return badgeId;
	}

	public String getBadgeName() {
		return badgeName;
	}

	public String getDescription() {
		return description;
	}

	public int getQuizzesNeeded() {
		return quizzesNeeded;
	}

	public double getXpNeeded() {
		return xpNeeded;
	}

	public int getCategoryId() {
		return categoryId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Badge badge = (Badge) o;

		if (badgeId != badge.badgeId) return false;
		if (quizzesNeeded != badge.quizzesNeeded) return false;
		if (xpNeeded != badge.xpNeeded) return false;
		if (categoryId != badge.categoryId) return false;
		if (!badgeName.equals(badge.badgeName)) return false;
		return description.equals(badge.description);
	}

	@Override
	public int hashCode() {
		int result = badgeId;
		result = 31 * result + badgeName.hashCode();
		result = 31 * result + description.hashCode();
		result = 31 * result + quizzesNeeded;
		result = 31 * result + (int) xpNeeded;
		result = 31 * result + categoryId;
		return result;
	}

	@Override
	public String toString() {
		return badgeName;
		/*return "Badge{" +
				"badgeId=" + badgeId +
				", badgeName='" + badgeName + '\'' +
				", description='" + description + '\'' +
				", quizzesNeeded=" + quizzesNeeded +
				", xpNeeded=" + xpNeeded +
				", categoryId=" + categoryId +
				'}';*/
	}
}
