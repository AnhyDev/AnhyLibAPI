package ink.anh.api.lingo.item;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;

import ink.anh.api.LibraryManager;
import ink.anh.api.nbt.NBTExplorer;

/**
 * Handles the translation of ItemStacks based on NBT data and language settings.
 */
public class TranslateItemStack {

    private String lang_NBT;
    private String key_NBT;
    
    /**
     * Constructs a TranslateItemStack instance with default NBT keys.
     */
    public TranslateItemStack() {
        this.lang_NBT = "Lingo";
        this.key_NBT = "ItemLingo";
    }

    /**
     * Modifies an ItemStack to apply language-specific translations based on the provided languages.
     *
     * @param langs An array of language codes to consider for translation.
     * @param item The ItemStack to be modified.
     * @param libraryManager The LibraryManager instance to access language data.
     */
    public void modifyItem(String[] langs, ItemStack item, LibraryManager libraryManager) {
        if (langs == null) {
            return;
        }
        
        // Retrieve the NBT compound of the item
        NbtCompound compound = NbtFactory.asCompound(NbtFactory.fromItemTag(item));
        
        // Process the item if it contains the key_NBT
        if (compound.containsKey(key_NBT)) {
            String customID = String.valueOf(compound.getValue(key_NBT).getValue());

            // Modify the item's name and lore if customID exists in the language item stack
            if (libraryManager.getLanguageItemStack().dataContainsKey(customID, langs)) {
                ItemLang itemLang = null;
                boolean processed = false;

                // Check for the lang_NBT tag and its match with the provided languages
                if (compound.containsKey(lang_NBT)) {
                    String langID = String.valueOf(compound.getValue(lang_NBT).getValue());
                    
                    for (String currentLang : langs) {
                        itemLang = libraryManager.getLanguageItemStack().getTranslate(customID, currentLang);

                        if (langID.equals(currentLang)) {
                            processed = true;
                            return;
                        } else if (itemLang != null) {
                            translateItemStack(item, itemLang);
                            processed = true;
                        }
                    }
                }
                if (!processed) {
                    itemLang = libraryManager.getLanguageItemStack().getData(customID, langs);
                    translateItemStack(item, itemLang);
                }
            }
        }
    }
    
    /**
     * Translates the ItemStack's display name and lore based on the provided ItemLang.
     *
     * @param item The ItemStack to be translated.
     * @param itemLang The ItemLang containing the translated name and lore.
     */
    private void translateItemStack(ItemStack item, ItemLang itemLang) {
        // Set the NBT value
        NBTExplorer.setNBTValueFromString(item, lang_NBT, "string:" + itemLang.getLang());
        
        ItemMeta meta = item.getItemMeta();
        
        String displayName = itemLang.getName();
        if (displayName != null) {
            meta.setDisplayName(displayName);
        }
        
        List<String> lore = itemLang.getLore() != null ? Arrays.asList(itemLang.getLore()) : null;
        if (lore != null) {
            meta.setLore(lore);
        }
        
        item.setItemMeta(meta);
    }
	
}
