package ink.anh.api;

import org.bukkit.plugin.Plugin;

import ink.anh.api.lingo.item.ItemLang;
import ink.anh.api.lingo.item.LanguageItemStack;
import ink.anh.api.lingo.lang.LanguageManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;

public interface LibraryManager {

    public abstract Plugin getPlugin();

    // Отримання назви плагіну
    public abstract String getPluginName();

    // Управління інтерфейсом користувача та повідомленнями
    public abstract BukkitAudiences getBukkitAudiences();

    // Робота з локалізацією та мовами
    public abstract LanguageManager getLanguageManager();

    // Отримання мови за замовчуванням
    public abstract String getDefaultLang();
    
    public abstract LanguageItemStack getLanguageItemStack();

    // Переклад предметів у інвентарі
    public abstract ItemLang getTranslateItemStack();

    // Управління налаштуваннями та конфігурацією
    public abstract boolean isDebug();
}
