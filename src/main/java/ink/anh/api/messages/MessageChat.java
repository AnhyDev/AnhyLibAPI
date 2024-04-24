package ink.anh.api.messages;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.Translator;
import ink.anh.api.messages.MessageComponents.MessageBuilder;
import ink.anh.api.utils.LangUtils;
import ink.anh.api.utils.StringUtils;

/**
 * Utility class for sending formatted and localized messages to players or command senders.
 * This class handles message translation, formatting, and sending using the Bukkit API.
 */
public class MessageChat {

    /**
     * Sends a simple translated and formatted message to a CommandSender (Player or console).
     * 
     * @param libraryManager      The LibraryManager instance for accessing various utilities.
     * @param sender             The CommandSender to whom the message will be sent.
     * @param textForFormatting  The message information including template and replacements.
     */
	public static void sendMessage(LibraryManager libraryManager, CommandSender sender, MessageForFormatting textForFormatting) {
    	MessageContext context = new MessageContext(libraryManager, sender, textForFormatting, false);
        String translateText = context.translateText;
        MessageBuilder mBuilder = context.mBuilder;

	    Messenger.sendMessage(libraryManager.getPlugin(), sender, mBuilder.build(), translateText);
	}

    /**
     * Sends a translated and formatted message with hover text to a CommandSender (Player or console).
     * Allows customization of message type and inclusion of the plugin name.
     * 
     * @param libraryManager      The LibraryManager instance.
     * @param sender             The CommandSender to whom the message will be sent.
     * @param textForFormatting  The primary message information.
     * @param hoverText          The hover text information.
     * @param type               The MessageType, defining the message color and style.
     * @param addPluginName      A boolean to decide if the plugin name should be prefixed to the message.
     */
	public static void sendMessage(LibraryManager libraryManager, CommandSender sender, MessageForFormatting textForFormatting, MessageForFormatting hoverText, MessageType type, boolean addPluginName) {
    	MessageContext context = new MessageContext(libraryManager, sender, textForFormatting, addPluginName);	
        String[] langs = context.langs;
        String translateText = context.translateText;
        MessageBuilder mBuilder = context.mBuilder;
        
        String translateHoverText = Translator.translateKyeWorld(libraryManager, hoverText.getTemplate(), langs);
        translateHoverText = StringUtils.formatString(translateHoverText, hoverText.getReplacements());
	    String consoleText = type.getColor(false) + translateText;

	    mBuilder.append(MessageComponents.builder()
	        .content(translateText)
	        .hexColor(type.getColor(true))
	        .hoverMessage(translateHoverText)
	        .build());

	    Messenger.sendMessage(libraryManager.getPlugin(), sender, mBuilder.build(), consoleText);
	}

    /**
     * Overloaded method for sending a translated and formatted message to a CommandSender (Player or console).
     * Allows customization of message type and inclusion of the plugin name.
     * This overload does not include hover text.
     * 
     * @param libraryManager      The LibraryManager instance.
     * @param sender             The CommandSender to whom the message will be sent.
     * @param textForFormatting  The message information.
     * @param type               The MessageType, defining the message color and style.
     * @param addPluginName      A boolean to decide if the plugin name should be prefixed to the message.
     */
	public static void sendMessage(LibraryManager libraryManager, CommandSender sender, MessageForFormatting textForFormatting, MessageType type, boolean addPluginName) {
    	MessageContext context = new MessageContext(libraryManager, sender, textForFormatting, addPluginName);	
        String translateText = context.translateText;
        MessageBuilder mBuilder = context.mBuilder;
        
	    String consoleText = type.getColor(false) + translateText;

	    mBuilder.append(MessageComponents.builder()
	        .content(translateText)
	        .hexColor(type.getColor(true))
	        .build());

	    Messenger.sendMessage(libraryManager.getPlugin(), sender, mBuilder.build(), consoleText);
	}

    /**
     * Sends a translated and formatted message with hover text and a clickable command to a CommandSender (Player or console).
     * 
     * @param libraryManager      The LibraryManager instance.
     * @param sender             The CommandSender to whom the message will be sent.
     * @param textForFormatting  The primary message information.
     * @param hoverText          The hover text information.
     * @param command            The command that will be run when the message is clicked.
     * @param type               The MessageType, defining the message color and style.
     * @param addPluginName      A boolean to decide if the plugin name should be prefixed to the message.
     */
    public static void sendMessage(LibraryManager libraryManager, CommandSender sender, MessageForFormatting textForFormatting, MessageForFormatting hoverText, String command, MessageType type, boolean addPluginName) {
    	MessageContext context = new MessageContext(libraryManager, sender, textForFormatting, addPluginName);	
        String[] langs = context.langs;
        String translateText = context.translateText;
        MessageBuilder mBuilder = context.mBuilder;
        
        String hoverMessage = hoverText.getTemplate();
        String translateHoverText = Translator.translateKyeWorld(libraryManager, hoverMessage, langs);
        translateHoverText = StringUtils.formatString(translateHoverText, hoverText.getReplacements());
        String consoleText = type.getColor(false) + translateText;

        mBuilder.append(MessageComponents.builder()
            .content(translateText)
            .hexColor(type.getColor(true))
            .hoverMessage(translateHoverText)
            .clickActionRunCommand(command)
            .build());

        Messenger.sendMessage(libraryManager.getPlugin(), sender, mBuilder.build(), consoleText);
    }

    /**
     * Sends a translated and formatted message with hover text and a clickable URL to a CommandSender (Player or console).
     * 
     * @param libraryManager      The LibraryManager instance.
     * @param sender             The CommandSender to whom the message will be sent.
     * @param textForFormatting  The primary message information.
     * @param hoverText          The hover text information.
     * @param url                The URL that will be opened when the message is clicked.
     * @param type               The MessageType, defining the message color and style.
     * @param addPluginName      A boolean to decide if the plugin name should be prefixed to the message.
     */
    public static void sendMessageWithLink(LibraryManager libraryManager, CommandSender sender, MessageForFormatting textForFormatting, MessageForFormatting hoverText, String url, MessageType type, boolean addPluginName) {
    	MessageContext context = new MessageContext(libraryManager, sender, textForFormatting, addPluginName);	
        String[] langs = context.langs;
        String translateText = context.translateText;
        MessageBuilder mBuilder = context.mBuilder;
        
        String translateHoverText = Translator.translateKyeWorld(libraryManager, hoverText.getTemplate(), langs);
        translateHoverText = StringUtils.formatString(translateHoverText, hoverText.getReplacements());
	    String consoleText = type.getColor(false) + translateText;


        mBuilder.append(MessageComponents.builder()
            .content(translateText)
            .hexColor(type.getColor(true))
            .hoverMessage(translateHoverText)
            .clickActionOpenUrl(url)
            .build());

        Messenger.sendMessage(libraryManager.getPlugin(), sender, mBuilder.build(), consoleText);
    }
    /**
     * Sends a translated and formatted message with hover text and a clickable action to copy text to the clipboard.
     * 
     * @param libraryManager      The LibraryManager instance.
     * @param sender              The CommandSender to whom the message will be sent.
     * @param textForFormatting   The primary message information.
     * @param hoverText           The hover text information.
     * @param textToCopy          The text that will be copied to the clipboard when the message is clicked.
     * @param type                The MessageType, defining the message color and style.
     * @param addPluginName       A boolean to decide if the plugin name should be prefixed to the message.
     */
    public static void sendMessageWithCopy(LibraryManager libraryManager, CommandSender sender, MessageForFormatting textForFormatting, MessageForFormatting hoverText, String textToCopy, MessageType type, boolean addPluginName) {
    	MessageContext context = new MessageContext(libraryManager, sender, textForFormatting, addPluginName);	
        String[] langs = context.langs;
        String translateText = context.translateText;
        MessageBuilder mBuilder = context.mBuilder;
        
        String translateHoverText = Translator.translateKyeWorld(libraryManager, hoverText.getTemplate(), langs);
        translateHoverText = StringUtils.formatString(translateHoverText, hoverText.getReplacements());
        String consoleText = type.getColor(false) + translateText;


        mBuilder.append(MessageComponents.builder()
            .content(translateText)
            .hexColor(type.getColor(true))
            .hoverMessage(translateHoverText)
            .clickActionCopy(textToCopy)
            .build());

        Messenger.sendMessage(libraryManager.getPlugin(), sender, mBuilder.build(), consoleText);
    }

    /**
     * Encapsulates context information for formatting and localizing messages sent to command senders.
     * This includes determining the appropriate language settings for the sender and managing message formatting tasks.
     */
    private static class MessageContext {
        String[] langs;
        String translateText;
        MessageBuilder mBuilder;

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
    }
}
