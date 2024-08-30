package kaboo.kaboo_auth.domain;

public enum Course {
	AI("GenAI"),
	FULLSTACK("Fullstack"),
	CLOUD("Cloud");

	private final String course;

	Course(String course) {
		this.course = course;
	}
}
