/*
 *   Copyright (C) 2014-2015 GeorgH93
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

package at.pcgamingfreaks.Bukkit.Effects;

import at.pcgamingfreaks.Bukkit.Reflection;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Effect_1_7 extends EffectBase
{
	private final static Class<?> EntityPlayer = Reflection.getNMSClass("EntityPlayer");
	private final static Method sendPacket = Reflection.getMethod(Reflection.getNMSClass("PlayerConnection"), "sendPacket");
	private final static Field playerConnection = Reflection.getField(EntityPlayer, "playerConnection");
	private final Constructor packetConstructor;

	public Effect_1_7() throws NoSuchMethodException, NullPointerException
	{
		//noinspection ConstantConditions
		packetConstructor = Reflection.getNMSClass("PacketPlayOutWorldParticles").getConstructor(String.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
	}

	public void spawnParticle(Location location, Effects type, double visibleRange, int count, float offsetX, float offsetY, float offsetZ, float speed)
	{
		try
		{
			Object packet = packetConstructor.newInstance(type.getName(), (float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, count), handle;
			for(Entity entity : location.getWorld().getEntities())
			{
				if(entity instanceof Player && entity.getLocation().getWorld().equals(location.getWorld()) && entity.getLocation().distance(location) < visibleRange)
				{
					handle = Reflection.getHandle(entity);
					if(handle != null && handle.getClass() == EntityPlayer)
					{
						sendPacket.invoke(playerConnection.get(handle), packet);
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Unable to spawn particle " + type.getName() + ". (Version 1.7)");
			e.printStackTrace();
		}
	}
}