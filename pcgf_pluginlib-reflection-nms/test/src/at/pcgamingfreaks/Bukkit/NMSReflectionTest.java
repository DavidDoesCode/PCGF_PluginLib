/*
 * Copyright (C) 2016 MarkusWME
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package at.pcgamingfreaks.Bukkit;

import at.pcgamingfreaks.TestClasses.FakeEntityPlayer;
import at.pcgamingfreaks.TestClasses.FakePlayer;
import at.pcgamingfreaks.TestClasses.TestBukkitServer;
import net.minecraft.server.TestBukkitServer.TestEnum;
import net.minecraft.server.TestBukkitServer.FakeTestNMSServer;
import org.bukkit.Bukkit;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NMSReflectionTest
{
	@BeforeClass
	public static void prepareTestData() {
		Bukkit.setServer(new TestBukkitServer());
	}

	@Test
	public void testGetVersion()
	{
		assertEquals("The version should match", "TestBukkitServer", NMSReflection.getVersion());
	}

	@Test
	public void testGetClass()
	{
		assertEquals("The NMS class should be correct", FakeTestNMSServer.class, NMSReflection.getNMSClass("FakeTestNMSServer"));
		assertNull("The NMS class should not be found", NMSReflection.getNMSClass(""));
	}

	@Test
	public void testGetMethod() throws NoSuchMethodException
	{
		assertEquals("The version method of the server should be found", FakeTestNMSServer.class.getDeclaredMethod("getVersion"), NMSReflection.getNMSMethod("FakeTestNMSServer", "getVersion"));
		assertNull("The version method of the server should not be found in an invalid class", NMSReflection.getNMSMethod("", "getVersion"));
	}

	@Test
	public void testGetField() throws NoSuchFieldException
	{
		assertEquals("The server field should be found", FakeTestNMSServer.class.getDeclaredField("serverField"), NMSReflection.getNMSField("FakeTestNMSServer", "serverField"));
		assertNull("The server field should not be found in an invalid class", NMSReflection.getNMSField("", "serverField"));
	}

	@Test
	public void testGetEnum()
	{
		assertEquals("The enum should be found", TestEnum.Value1, NMSReflection.getNMSEnum("TestEnum.Value1"));
		assertEquals("The enum should be found", TestEnum.Value2, NMSReflection.getNMSEnum("TestEnum", "Value2"));
	}

	@Test
	public void testGetHandle()
	{
		assertEquals("The handle should be get correctly", FakeEntityPlayer.class, Objects.requireNonNull(NMSReflection.getHandle(new FakePlayer())).getClass());
		assertNull("The handle should not be found", NMSReflection.getHandle(this));
	}
}