package ink.anh.api.messages;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * The MessageComponents class is utilized for creating and managing complex text components 
 * in the Minecraft environment using the Kyori Adventure library. This class serves as a wrapper 
 * for {@link net.kyori.adventure.text.Component}, providing a more convenient and flexible way 
 * to construct interactive and richly-formatted text messages.
 *
 * <p>MessageComponents includes an internal class, MessageBuilder, which allows users to 
 * sequentially configure properties of a text component such as content, color, decorations, 
 * and interactive actions (e.g., clickable events, hover events). This ensures cleaner code 
 * and ease in setting up complex components.</p>
 *
 * <p>Key features of the class:</p>
 * <ul>
 * <li><b>Encapsulation of Component:</b> The class wraps around Component, allowing the encapsulation 
 * of the complexity involved in creating text components.</li>
 * <li><b>Chainable Methods:</b> Provides a fluent API for sequential setting of text properties.</li>
 * <li><b>Flexible Formatting:</b> Easy setting of colors, decorations, and interactive actions.</li>
 * <li><b>Separation of Creation Logic from Usage:</b> Create complex components in one place, 
 * use them elsewhere in the program.</li>
 * </ul>
 *
 * <p>This class is useful for creating text messages that are not only visually appealing but also interactive.</p>
 *
 * <p>Example of usage:</p>
 * <pre>{@code
 * MessageComponents messageComponents = MessageComponents.builder()
 *     .content("Welcome to the game!")
 *     .color("GREEN")
 *     .decoration("BOLD", true)
 *     .build();
 * Component component = messageComponents.getComponent();
 * }</pre>
 *
 * @see net.kyori.adventure.text.Component
 */
public class MessageComponents {
    private Component component;

    /**
     * Private constructor used to create an instance of MessageComponents.
     * This constructor is called internally by the MessageBuilder.
     *
     * @param component The Kyori Adventure Component that represents the built message.
     */
    private MessageComponents(Component component) {
        this.component = component;
    }

    /**
     * Retrieves the built Kyori Adventure Component.
     * This method can be used to obtain the final text component for use in Minecraft.
     *
     * @return The built Component.
     */
    public Component getComponent() {
        return this.component;
    }

    /**
     * Builder class for creating {@link MessageComponents}.
     * Provides methods for setting text content, color, decoration, and interactive actions.
     */
    public static class MessageBuilder {
        private final List<Component> components = new ArrayList<>();
        private TextComponent.Builder currentComponentBuilder = Component.text();
        
        private String hoverMessage;
        private Component hoverComponent;
        private HoverEvent<?> showItemEvent;
        private HoverEvent<?> showEntityEvent;
        private String clickAction;
        private String clickValue;

        /**
         * Sets the content of the current text component.
         *
         * @param content The text content to set.
         * @return The builder instance for chaining.
         */
        public MessageBuilder content(String content) {
            applyCurrentComponent();
            currentComponentBuilder = Component.text().content(content);
            return this;
        }

        /**
         * Sets a hover message for the current text component.
         *
         * @param hoverMessage The hover message text.
         * @return The builder instance for chaining.
         */
        public MessageBuilder hoverMessage(String hoverMessage) {
            this.hoverMessage = hoverMessage;
            return this;
        }

        /**
         * Adds a popup message in the form of a ready-made MessageComponents object.
         *
         * @param hoverMessageComponents MessageComponents object to display on hover.
         * @return The builder instance for chaining.
         */
        public MessageBuilder hoverComponent(MessageComponents hoverMessageComponents) {
            if (hoverMessageComponents != null) {
                this.hoverComponent = hoverMessageComponents.getComponent();
            }
            return this;
        }

        public MessageBuilder showItem(HoverEvent.ShowItem showItemData) {
            this.showItemEvent = HoverEvent.showItem(showItemData);
            return this;
        }

        public MessageBuilder showEntity(HoverEvent.ShowEntity showEntityData) {
            this.showEntityEvent = HoverEvent.showEntity(showEntityData);
            return this;
        }

        public MessageBuilder insertTextChat(String text) {
            this.clickAction = "insertionText";
            this.clickValue = text;
            return this;
        }

        /**
         * Sets a click action to copy text to clipboard.
         *
         * @param copyToClipboard The text to copy to clipboard when clicked.
         * @return The builder instance for chaining.
         */
        public MessageBuilder clickActionCopy(String copyToClipboard) {
            this.clickAction = "copyToClipboard";
            this.clickValue = copyToClipboard;
            return this;
        }

        /**
         * Sets a click action to run a command.
         *
         * @param runCommand The command to run when clicked.
         * @return The builder instance for chaining.
         */
        public MessageBuilder clickActionRunCommand(String runCommand) {
            this.clickAction = "runCommand";
            this.clickValue = runCommand;
            return this;
        }

        /**
         * Sets a click action to open a URL.
         *
         * @param openUrl The URL to open when clicked.
         * @return The builder instance for chaining.
         */
        public MessageBuilder clickActionOpenUrl(String openUrl) {
            this.clickAction = "openUrl";
            this.clickValue = openUrl;
            return this;
        }
        
        /**
         * Sets the color of the current text component using a color name.
         * Named colors are predefined and include:
         * BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE,
         * GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE,
         * YELLOW, WHITE.
         *
         * @param colorName The name of the color. Must match one of the predefined named colors.
         *                  The color name is case-insensitive.
         * @return The builder instance for chaining.
         */
        public MessageBuilder color(String colorName) {
            if (colorName != null) {
                NamedTextColor color = getColorFromName(colorName);
                if (color != null) {
                    currentComponentBuilder.color(color);
                }
            }
            return this;
        }

        /**
         * Sets the color of the current text component using a hex color value.
         *
         * @param hexColor The hex color value.
         * @return The builder instance for chaining.
         */
        public MessageBuilder hexColor(String hexColor) {
            if (hexColor != null) {
                try {
                    TextColor color = TextColor.fromHexString(hexColor);
                    currentComponentBuilder.color(color);
                } catch (IllegalArgumentException e) {
                    // Недійсне або нульове значення hexColor
                }
            }
            return this;
        }
        
        /**
         * Sets a text decoration for the current component.
         * Available decorations include:
         * BOLD - Makes the text bold.
         * ITALIC - Makes the text italic.
         * UNDERLINED - Adds an underline to the text.
         * STRIKETHROUGH - Strikes through the text.
         * OBFUSCATED - Makes the text obfuscated (random characters).
         *
         * @param decorationName The name of the decoration. Must match one of the predefined decorations.
         *                       The decoration name is case-insensitive.
         * @param flag           True to apply the decoration, false to remove it.
         * @return The builder instance for chaining.
         */
        public MessageBuilder decoration(String decorationName, boolean flag) {
            if (decorationName != null) {
                TextDecoration decoration = getDecorationFromName(decorationName);
                if (decoration != null) {
                    currentComponentBuilder.decoration(decoration, flag);
                }
            }
            return this;
        }

        /**
         * Appends a new line to the text components.
         *
         * @return The builder instance for chaining.
         */
        public MessageBuilder appendNewLine() {
            applyCurrentComponent();
            components.add(Component.newline());
            return this;
        }

        /**
         * Converts a string representation of a color name to a {@link NamedTextColor} instance.
         * Accepts predefined color names and returns the corresponding NamedTextColor.
         *
         * @param colorName The name of the color in string format. 
         *                  Accepted values include standard Minecraft color names, e.g., "RED", "BLUE".
         *                  The name is case-insensitive.
         * @return The corresponding {@link NamedTextColor} instance, or {@code null} if no match is found.
         */
        private NamedTextColor getColorFromName(String colorName) {
            if (colorName == null) {
                return null;
            }
            switch (colorName.toUpperCase()) {
                case "BLACK": return NamedTextColor.BLACK;
                case "DARK_BLUE": return NamedTextColor.DARK_BLUE;
                case "DARK_GREEN": return NamedTextColor.DARK_GREEN;
                case "DARK_AQUA": return NamedTextColor.DARK_AQUA;
                case "DARK_RED": return NamedTextColor.DARK_RED;
                case "DARK_PURPLE": return NamedTextColor.DARK_PURPLE;
                case "GOLD": return NamedTextColor.GOLD;
                case "GRAY": return NamedTextColor.GRAY;
                case "DARK_GRAY": return NamedTextColor.DARK_GRAY;
                case "BLUE": return NamedTextColor.BLUE;
                case "GREEN": return NamedTextColor.GREEN;
                case "AQUA": return NamedTextColor.AQUA;
                case "RED": return NamedTextColor.RED;
                case "LIGHT_PURPLE": return NamedTextColor.LIGHT_PURPLE;
                case "YELLOW": return NamedTextColor.YELLOW;
                case "WHITE": return NamedTextColor.WHITE;
                default: return null;
            }
        }
        
        /**
         * Converts a string representation of a text decoration to a {@link TextDecoration} instance.
         * Accepts predefined decoration names and returns the corresponding TextDecoration.
         *
         * @param decorationName The name of the decoration in string format.
         *                       Accepted values include text decorations like "BOLD", "ITALIC".
         *                       The name is case-insensitive.
         * @return The corresponding {@link TextDecoration} instance, or {@code null} if no match is found.
         */
        private TextDecoration getDecorationFromName(String decorationName) {
            if (decorationName == null) {
                return null;
            }
            String upperDecorationName = decorationName.toUpperCase();
            switch (upperDecorationName) {
                case "BOLD":
                    return TextDecoration.BOLD;
                case "ITALIC":
                    return TextDecoration.ITALIC;
                case "UNDERLINED":
                    return TextDecoration.UNDERLINED;
                case "STRIKETHROUGH":
                    return TextDecoration.STRIKETHROUGH;
                case "OBFUSCATED":
                    return TextDecoration.OBFUSCATED;
                default:
                    return null;
            }
        }

        /**
         * Applies the current component builder, adding it to the components list.
         */
        private void applyCurrentComponent() {
            if (currentComponentBuilder != null) {
            	if (hoverComponent != null) {
                    currentComponentBuilder.hoverEvent(HoverEvent.showText(hoverComponent));
                    hoverComponent = null;
                } else if (showItemEvent != null) {
                    currentComponentBuilder.hoverEvent(showItemEvent);
                    showItemEvent = null;
                } else if (showEntityEvent != null) {
                    currentComponentBuilder.hoverEvent(showEntityEvent);
                    showEntityEvent = null;
                } else if (hoverMessage != null) {
                    currentComponentBuilder.hoverEvent(HoverEvent.showText(Component.text(hoverMessage)));
                    hoverMessage = null;
                }
            	
                if (clickAction != null) {
                    switch (clickAction) {
                        case "copyToClipboard":
                            currentComponentBuilder.clickEvent(ClickEvent.copyToClipboard(clickValue));
                            break;
                        case "runCommand":
                            currentComponentBuilder.clickEvent(ClickEvent.runCommand(clickValue));
                            break;
                        case "openUrl":
                            currentComponentBuilder.clickEvent(ClickEvent.openUrl(clickValue));
                            break;
                        case "insertionText":
                            currentComponentBuilder.clickEvent(ClickEvent.suggestCommand(clickValue));
                            break;
                    }
                    clickAction = null;
                    clickValue = null;
                }

                if (!currentComponentBuilder.build().content().isEmpty()) {
                    components.add(currentComponentBuilder.build());
                }

                currentComponentBuilder = Component.text();
            }
        }

        /**
         * Builds the final {@link MessageComponents} instance.
         * This method consolidates all added components into a single {@link MessageComponents} object.
         *
         * @return A new {@link MessageComponents} instance containing the built component.
         */
        public MessageComponents build() {
            applyCurrentComponent();
            TextComponent.Builder builder = Component.text();
            for (Component comp : components) {
                builder.append(comp);
            }
            // Передаємо побудований компонент в конструктор MessageComponents
            return new MessageComponents(builder.build());
        }
    }

    /**
     * Static method to create a new MessageBuilder instance.
     * This method is the starting point for building a message using the MessageComponents class.
     *
     * @return A new instance of the MessageBuilder.
     */
    public static MessageBuilder builder() {
        return new MessageBuilder();
    }
}
