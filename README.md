# AnhyLibAPI
The purpose of this library is to provide a set of tools and functionalities that can be used specifically in Minecraft plugins development.

**Overview**

Developers are offered a suite of flexible and easily integrated components for language handling, player customization, message processing, and NBT data manipulation.

**Features**

*   **Multilingual Support:** Classes such as {@code LanguageManager}, {@code Translator}, and {@code LangUtils} offer capabilities for handling multiple languages, making plugins accessible to a broader audience.
*   **NBT Data Handling:** {@code NBTExplorer} and {@code NBTValueParser} allow for advanced manipulation of NBT, a key element for deeper game interaction in Minecraft.
*   **Message Management:** Modules like {@code Messenger}, {@code Logger}, and {@code ANSIColors} simplify message output and formatting, as well as logging.
*   **Component Serialization:** {@code PaperUtils} and {@code SpigotUtils} provide tools for working with text components, including their serialization and deserialization.
*   **Extensibility:** The classes are designed with the possibility of easy extension, ensuring flexibility for future plugin development.

# API

##### Use the AnhyLibAPI library in your project as a dependency. Here are the instructions for adding the plugin using Gradle and Maven.

#### Adding Using Gradle

To use AnhyLingo in your Gradle project, add the following lines to your project's `build.gradle` file:


```groovy
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

```groovy
	dependencies {
	        implementation 'com.github.AnhyDev:AnhyLibAPI:v1.0.1'
	}
```

#### Adding Using Maven

To include AnhyLingo in your Maven project, insert these lines into your `pom.xml` file:

```xml
    <repositories>
        repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```

```xml
	<dependency>
	    <groupId>com.github.AnhyDev</groupId>
	    <artifactId>AnhyLibAPI</artifactId>
	    <version>v1.0.1</version>
	</dependency>
```

