plugins {
	id("java")
	id("fabric-loom") version("1.4-SNAPSHOT")
	kotlin("jvm") version ("1.8.20")
}

group = property("maven_group")!!
version = property("mod_version")!!


repositories {
	mavenCentral()
	maven("https://maven.impactdev.net/repository/development/")
	maven ("https://oss.sonatype.org/content/repositories/snapshots")
	maven("https://maven.nucleoid.xyz")
}

dependencies {
	minecraft("com.mojang:minecraft:${property("minecraft_version")}")
	mappings("net.fabricmc:yarn:${property("yarn_mappings")}")
	modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
	
	// Cobblemon
	modImplementation("com.cobblemon:fabric:${property("cobblemon_version")}")
}


tasks {
	processResources {
		inputs.property("version", project.version)

		filesMatching("fabric.mod.json") {
			expand(mutableMapOf("version" to project.version))
		}
	}

	jar {
		from("LICENSE")
	}

	compileKotlin {
		kotlinOptions.jvmTarget = "17"
	}
}