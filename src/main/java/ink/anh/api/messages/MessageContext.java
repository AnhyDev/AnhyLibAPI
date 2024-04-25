package ink.anh.api.messages;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.Translator;
import ink.anh.api.messages.MessageComponents.MessageBuilder;
import ink.anh.api.utils.LangUtils;
import ink.anh.api.utils.StringUtils;

public class MessageContext {
    private String[] langs;
    private String translateText;
    private MessageBuilder mBuilder;

    /**
     * Constructs a new MessageContext for a specific sender and message formatting information.
     * It automatically resolves the language preferences of the sender, translates and formats the message,
     * and prepares it for sending.
     *
     * @param libraryManager The LibraryManager instance, providing access to plugin-specific settings and utilities.
     * @param sender The CommandSender to whom the message will be sent.
     * @param textForFormatting The structured message data containing template and replacements.
     * @param addPluginName Indicates whether to prefix the message with the plugin's name.
     */
    public MessageContext(LibraryManager libraryManager, CommandSender sender, MessageForFormatting textForFormatting, boolean addPluginName) {
        // Determine language settings for the sender
        this.langs = sender instanceof Player ? LangUtils.getPlayerLanguage((Player) sender) : new String[]{libraryManager.getDefaultLang()};
        
        // Translate and format the message according to the specified language and formatting rules
        this.translateText = Translator.translateKyeWorld(libraryManager, textForFormatting.getTemplate(), langs);
        this.translateText = StringUtils.formatString(translateText, textForFormatting.getReplacements());
        
        // Initialize the MessageBuilder for constructing the final message
        this.mBuilder = MessageComponents.builder();
        
        // Optionally add the plugin's name to the message
        if (addPluginName) {
            mBuilder.append(MessageComponents.builder()
                .content("[" + libraryManager.getPluginName() + "] ")
                .hexColor("#1D87E4")
                .decoration("BOLD", true)
                .build());
        }
    }

	public String[] getLangs() {
		return langs;
	}

	public String getTranslateText() {
		return translateText;
	}

	public MessageBuilder getmBuilder() {
		return mBuilder;
	}
    
}