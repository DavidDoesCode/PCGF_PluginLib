/*
 *   Copyright (C) 2022 GeorgH93
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package at.pcgamingfreaks.Bukkit.Message;

import at.pcgamingfreaks.Message.MessageColor;
import at.pcgamingfreaks.Message.MessageComponent;
import at.pcgamingfreaks.Message.MessageFormat;
import at.pcgamingfreaks.Reflection;

import org.bukkit.Achievement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MessageBuilder extends at.pcgamingfreaks.Message.MessageBuilder<MessageBuilder, Message>
{
	//region Constructors
	/**
	 * Creates a new MessageBuilder with an empty {@link MessageComponent}.
	 */
	public MessageBuilder()
	{
		super(new MessageComponent());
	}

	/**
	 * Creates a new MessageBuilder with a given {@link MessageComponent}.
	 *
	 * @param initComponent The {@link MessageComponent} that should be on the first position of the message.
	 */
	public MessageBuilder(MessageComponent initComponent)
	{
		super(initComponent);
	}

	/**
	 * Creates a new MessageBuilder with a given {@link Collection} of {@link MessageComponent}'s.
	 *
	 * @param initCollection The {@link Collection} of {@link MessageComponent} that should be used to initiate the MessageBuilder.
	 */
	public MessageBuilder(Collection<? extends MessageComponent> initCollection)
	{
		super(initCollection);
	}

	/**
	 * Creates a new MessageBuilder from a given text and format information.
	 *
	 * @param text    The text that should be used to initialize the first {@link MessageComponent} of the message.
	 */
	public MessageBuilder(String text)
	{
		this(new MessageComponent(text));
	}

	/**
	 * Creates a new MessageBuilder from a given text and format information.
	 *
	 * @param text    The text that should be used to initialize the first {@link MessageComponent} of the message.
	 * @param color   The color that should be used to initialize the first {@link MessageComponent} of the message.
	 * @param formats The style that should be used to initialize the first {@link MessageComponent} of the message.
	 */
	public MessageBuilder(String text, MessageColor color, MessageFormat... formats)
	{
		this(new MessageComponent(text, color, formats));
	}

	/**
	 * Creates a new MessageBuilder from a given text and format information.
	 *
	 * @param text   The text that should be used to initialize the first {@link MessageComponent} of the message.
	 * @param styles The style that should be used to initialize the first {@link MessageComponent} of the message.
	 * @deprecated   Use {@link MessageBuilder#MessageBuilder(String, MessageColor, MessageFormat...)} instead.
	 */
	@Deprecated
	@ApiStatus.ScheduledForRemoval(inVersion = "1.0.40")
	public MessageBuilder(String text, ChatColor[] styles)
	{
		this(new MessageComponent(text));
		style(styles);
	}
	//endregion

	/**
	 * Creates a MessageBuilder from a JSON string.
	 *
	 * @param json The JSON that should be used to create the MessageBuilder object.
	 * @return The MessageBuilder object containing the {@link MessageComponent}'s from the given JSON.
	 */
	public static MessageBuilder fromJson(String json)
	{
		MessageBuilder builder = new MessageBuilder((MessageComponent) null);
		builder.appendJson(json);
		return builder;
	}

	//region Append functions
	/**
	 * Adds a new {@link MessageComponent} to the builder, generated from a text and optional style data.
	 *
	 * @param text   The text that should be used to generate the new {@link MessageComponent} that will be added to the builder.
	 * @param styles The style information for the new {@link MessageComponent} that will be added to the builder.
	 * @return The message builder instance (for chaining).
	 * @deprecated Use {@link at.pcgamingfreaks.Message.MessageBuilder#append(String, MessageColor, MessageFormat...)} instead.
	 */
	@Deprecated
	@ApiStatus.ScheduledForRemoval(inVersion = "1.0.40")
	public MessageBuilder append(String text, ChatColor[] styles)
	{

		if(styles == null || styles.length == 0) return this;
		append(text);
		return style(styles);
	}
	//endregion

	//region Modifier for the current component
	/**
	 * Set the behavior of the current component to display information about an achievement when the client hovers over the text.
	 *
	 * @param achievement The achievement to display.
	 * @return The message builder instance (for chaining).
	 */
	public MessageBuilder achievementTooltip(Achievement achievement)
	{
		MessageTooltipFactory.achievementTooltip(getCurrentComponent(), achievement);
		return this;
	}

	/**
	 * Set the behavior of the current component to display information about a parameterless statistic when the client hovers over the text.
	 *
	 * @param statistic The statistic to display.
	 * @return The message builder instance (for chaining).
	 * @exception IllegalArgumentException If the statistic requires a parameter which was not supplied.
	 */
	public MessageBuilder statisticTooltip(Statistic statistic) throws IllegalArgumentException
	{
		MessageTooltipFactory.statisticTooltip(getCurrentComponent(), statistic);
		return this;
	}

	/**
	 * Set the behavior of the current component to display information about a parameterless statistic when the client hovers over the text.
	 *
	 * @param statistic The statistic to display.
	 * @param material The material parameter to the statistic.
	 * @return The message builder instance (for chaining).
	 * @exception IllegalArgumentException If the statistic requires a parameter which was not supplied, or was supplied a parameter that was not required.
	 */
	public MessageBuilder statisticTooltip(Statistic statistic, Material material) throws IllegalArgumentException
	{
		MessageTooltipFactory.statisticTooltip(getCurrentComponent(), statistic, material);
		return this;
	}

	/**
	 * Set the behavior of the current component to display information about a parameterless statistic when the client hovers over the text.
	 *
	 * @param statistic The statistic to display.
	 * @param entity The entity type parameter to the statistic.
	 * @return The message builder instance (for chaining).
	 * @exception IllegalArgumentException If the statistic requires a parameter which was not supplied, or was supplied a parameter that was not required.
	 */
	public MessageBuilder statisticTooltip(Statistic statistic, EntityType entity) throws IllegalArgumentException
	{
		MessageTooltipFactory.statisticTooltip(getCurrentComponent(), statistic, entity);
		return this;
	}

	/**
	 * Set the behavior of the current component to display information about an item when the client hovers over the text.
	 *
	 * @param itemStack The stack for which to display information.
	 * @return The message builder instance (for chaining).
	 */
	public MessageBuilder itemTooltip(ItemStack itemStack)
	{
		MessageTooltipFactory.itemTooltip(getCurrentComponent(), itemStack);
		return this;
	}


	/**
	 * Sets the color of the current component.
	 *
	 * @param color The new color of the current component.
	 * @return The message builder instance (for chaining).
	 * @deprecated Use {@link at.pcgamingfreaks.Message.MessageBuilder#color(MessageColor)} instead!
	 */
	@Deprecated
	@ApiStatus.ScheduledForRemoval(inVersion = "1.0.40")
	public MessageBuilder color(ChatColor color)
	{
		if(!color.isColor()) throw new IllegalArgumentException(color.name() + " is not a color!");
		getCurrentComponent().setColor(MessageColor.valueOf(color.name()));
		return this;
	}

	/**
	 * Sets the format of the current component
	 *
	 * @param formats The array of formats to apply to the current component.
	 * @return The message builder instance (for chaining).
	 * @exception IllegalArgumentException If any of the enumeration values in the array do not represent formatters.
	 * @deprecated Use {@link at.pcgamingfreaks.Message.MessageBuilder#format(MessageFormat...)} instead!
	 */
	@Deprecated
	@ApiStatus.ScheduledForRemoval(inVersion = "1.0.40")
	public MessageBuilder format(ChatColor... formats) throws IllegalArgumentException
	{
		List<MessageFormat> formatsList = new ArrayList<>(formats.length);
		for(ChatColor format : formats)
		{
			if(!format.isFormat()) throw new IllegalArgumentException(format.name() + " is not a format!");
			formatsList.add(MessageFormat.valueOf(format.name()));
		}
		getCurrentComponent().setFormats(formatsList);
		return this;
	}

	/**
	 * Sets the style of the current component.
	 *
	 * @param styles The array of styles to apply to the current component.
	 * @return The message builder instance (for chaining).
	 * @deprecated Use {@link at.pcgamingfreaks.Message.MessageBuilder#format(MessageFormat...)} or {@link at.pcgamingfreaks.Message.MessageBuilder#color(MessageColor)} instead!
	 */
	@Deprecated
	@ApiStatus.ScheduledForRemoval(inVersion = "1.0.40")
	public MessageBuilder style(ChatColor... styles)
	{
		for(ChatColor style : styles)
		{
			if(style == ChatColor.RESET) { color(MessageColor.RESET); format(MessageFormat.RESET); }
			else if(style.isColor()) color(style);
			else format(style);
		}
		return this;
	}
	//endregion
}