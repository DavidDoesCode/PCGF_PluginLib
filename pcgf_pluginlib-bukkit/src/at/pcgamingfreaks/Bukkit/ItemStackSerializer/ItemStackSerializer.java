/*
 *   Copyright (C) 2020 GeorgH93
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
 *   along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package at.pcgamingfreaks.Bukkit.ItemStackSerializer;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public interface ItemStackSerializer
{
	/**
	 * Deserialize a serialized byte array to an ItemStack array.
	 *
	 * @param data The data that should get deserialized.
	 * @return The deserialized ItemStack array.
	 */
	ItemStack[] deserialize(byte[] data);

	/**
	 * Serializes a ItemStack array to a byte array.
	 *
	 * @param itemStacks The ItemStacks that should be serialized.
	 * @return Serialized ItemsStacks as byte array. Null if serialization failed.
	 */
	byte[] serialize(ItemStack[] itemStacks);

	/**
	 * Checks if the minecraft version of the server is compatible with the logger!
	 *
	 * @return True if the servers minecraft version is compatible with the logger.
	 */
	boolean checkIsMCVersionCompatible();

	/**
	 * Sets the logger that should be used by the serializer.
	 *
	 * @param logger The logger that should be used. null for no logger.
	 */
	default void setLogger(final @Nullable Logger logger) {}

	/**
	 * @deprecated Unreliable on many MC versions when being used with items that have NBT-tags. Use {@link ItemStackSerializer#makeNBTItemStackSerializer()} instead.
	 */
	@Deprecated
	static ItemStackSerializer makeBukkitItemStackSerializer()
	{
		return new BukkitItemStackSerializer();
	}

	static ItemStackSerializer makeNBTItemStackSerializer()
	{
		return new NBTItemStackSerializerGen2();
	}

	static ItemStackSerializer makeNBTItemStackSerializer(final @Nullable Logger logger)
	{
		return new NBTItemStackSerializerGen2(logger);
	}

	static boolean isNBTItemStackSerializerAvailable()
	{
		return NBTItemStackSerializerGen2.isMCVersionCompatible();
	}
}