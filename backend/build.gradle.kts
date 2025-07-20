plugins {
	kotlin("jvm") version "1.9.25" apply false
}

group = "com.ferick"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}

subprojects {
	repositories {
		mavenCentral()
	}
	group = rootProject.group
	version = rootProject.version
}
