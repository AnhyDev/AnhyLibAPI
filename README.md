# AnhyLibAPI: For Minecraft Plugins

**AnhyLibAPI** is a library designed for integration into Minecraft plugins, developed to enhance their capabilities on servers running on Spigot, Paper, Purpur, and other Spigot forks

The latest version of the `<span style="box-sizing: border-box; font-weight: bolder;">ProtocolLib</span>` plugin is required for operation.


### Key Features:

*   <span style="box-sizing: border-box; font-weight: bolder;">Multilingual Support</span>: Easy integration of language packs for plugins.
*   <span style="box-sizing: border-box; font-weight: bolder;">NBT Tags Handling</span>: Advanced management of NBT tags for flexible data interaction.
*   <span style="box-sizing: border-box; font-weight: bolder;">Player Persistent Data</span>: Efficient use of persistent data for players.
*   <span style="box-sizing: border-box; font-weight: bolder;">Customizing Messages</span>: Individual customization of message delivery to players.
*   <span style="box-sizing: border-box; font-weight: bolder;">Logging</span>: Unique methods for event logging.


### Documentation:

JavaDoc for AnhyLibAPI are available at the following link: [AnhyLibAPI Documentation](https://dev.anh.ink/anhylibapi/javadoc/).


### Integration with Gradle and Maven:

<span style="box-sizing: border-box; font-weight: bolder;">Gradle:</span>

<div class="code-block" style="box-sizing: border-box; margin-top: 10px; text-wrap: nowrap;">

<pre>dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
</pre>

</div>

<div class="code-block" style="box-sizing: border-box; margin-top: 10px; text-wrap: nowrap;">

<pre style="box-sizing: border-box; font-family: SFMono-Regular, Menlo, Monaco, Consolas, 'Liberation Mono', 'Courier New', monospace; font-size: 14px; margin-top: 0px; margin-bottom: 1rem; overflow: auto; color: #057c68; overflow-wrap: break-word; background-color: #050505; border: 1px solid #4e4141; border-radius: 4px; width: 1134.39px; padding: 10px 10px 10px 50px; margin-left: 50px;">dependencies {
    implementation 'com.github.AnhyDev:AnhyLibAPI:v1.3.3'
}
</pre>

</div>

<span style="box-sizing: border-box; font-weight: bolder;">Maven:</span>

<div class="code-block" style="box-sizing: border-box; margin-top: 10px; text-wrap: nowrap;">

<pre><repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
</pre>


<pre><dependency>
    <groupId>com.github.AnhyDev</groupId>
    <artifactId>AnhyLibAPI</artifactId>
    <version>v1.3.3</version>
</dependency>
</pre>

### Plugin Writing Examples:

For examples of writing plugins using AnhyLibAPI, visit the following link: [AnhyLibAPI Plugin Examples](https://github.com/AnhyDev/ResourcesHub/tree/main/AnhyLibAPI/examples/ExampleLangPlugin).

<pre>package ink.anh.example;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.Translator;
import ink.anh.api.lingo.lang.LanguageManager;
import ink.anh.api.messages.Logger;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.md_5.bungee.api.ChatColor;

public class GlobalManager extends LibraryManager {

    private static GlobalManager instance;
	private ExampleLangPlugin plugin;

	private LanguageManager langManager;
    private String pluginName;
    private String defaultLang;
    private static BukkitAudiences bukkitAudiences;
    private boolean debug;

	private GlobalManager(ExampleLangPlugin plugin) {
		super(plugin);
		this.plugin = plugin;
		this.saveDefaultConfig();
		this.loadFields(plugin);
	}

    public static synchronized GlobalManager getManager(ExampleLangPlugin plugin) {
        if (instance == null) {
            instance = new GlobalManager(plugin);
        }
        return instance;
    }

	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public String getPluginName() {
		return pluginName;
	}

	@Override
	public BukkitAudiences getBukkitAudiences() {
		return bukkitAudiences;
	}

	@Override
	public LanguageManager getLanguageManager() {
		return this.langManager;
	}

	@Override
	public String getDefaultLang() {
		return defaultLang;
	}

	@Override
	public boolean isDebug() {
		return debug;
	}

    private void loadFields(ExampleLangPlugin plugin) {
        bukkitAudiences = BukkitAudiences.create(plugin);
        defaultLang = plugin.getConfig().getString("language", "en");
        pluginName = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("plugin_name", "ExampleLangPlugin"));
        debug = plugin.getConfig().getBoolean("debug", false);
        setLanguageManager();
    }

    private void saveDefaultConfig() {
    	File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            YamlConfiguration defaultConfig = new YamlConfiguration();
            defaultConfig.set("plugin_name", "ExampleLangPlugin");
            defaultConfig.set("language", "en");
            defaultConfig.set("debug", false);
            try {
                defaultConfig.save(configFile);
                Logger.warn(plugin, "Default configuration created. ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setLanguageManager() {
        if (this.langManager == null) {
            this.langManager = LangMessage.getInstance(this);;
        } else {
        	this.langManager.reloadLanguages();
        }
    }

	public boolean reload() {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
	        try {
	        	saveDefaultConfig();
	            plugin.reloadConfig();
	            loadFields(plugin);
	            Logger.info(plugin, Translator.translateKyeWorld(instance, "configuration_reloaded" , new String[] {defaultLang}));
	        } catch (Exception e) {
	            e.printStackTrace();
	            Logger.error(plugin, Translator.translateKyeWorld(instance, "err_reloading_configuration ", new String[] {defaultLang}));
	        }
		});
        return true;
    }
}

</pre>


<pre>package ink.anh.example;

import ink.anh.api.lingo.lang.LanguageManager;

public class LangMessage extends LanguageManager {

    private static LangMessage instance = null;
    private static final Object LOCK = new Object();

    private LangMessage(GlobalManager manager) {
        super(manager, "lang");
    }

    public static LangMessage getInstance(GlobalManager manager) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new LangMessage(manager);
                }
            }
        }
        return instance;
    }
}

</pre>

