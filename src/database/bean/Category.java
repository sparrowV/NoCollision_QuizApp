package database.bean;


public class Category {
	private int categoryId;
	private String categoryName;

	public Category(int categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Category category = (Category) o;

		if (categoryId != category.categoryId) return false;
		return categoryName.equals(category.categoryName);
	}

	@Override
	public int hashCode() {
		int result = categoryId;
		result = 31 * result + categoryName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "categoryId=" + categoryId +
				", categoryName=" + categoryName + ';';
	}
}
