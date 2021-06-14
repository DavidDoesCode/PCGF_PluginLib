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

import at.pcgamingfreaks.TestClasses.TestBukkitServer;
import at.pcgamingfreaks.TestClasses.TestObjects;
import org.bukkit.Bukkit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class LanguageTest
{
	@BeforeClass
	public static void prepareTestData() throws NoSuchFieldException, IllegalAccessException
	{
		TestBukkitServer server = new TestBukkitServer();
		server.serverVersion = "TestServer-1_7";
		if (Bukkit.getServer() == null) {
			Bukkit.setServer(new TestBukkitServer());
		}
		//TestObjects.initNMSReflection();
	}

	@Before
	public void prepareTestObjects() throws Exception
	{
		//TestObjects.initMockedJavaPlugin();
		//whenNew(at.pcgamingfreaks.Language.class).withAnyArguments().thenAnswer(invocationOnMock -> null);
		//suppress(at.pcgamingfreaks.Language.class.getDeclaredMethods());
	}

	@Ignore
	@Test
	public void testLanguage()
	{
		assertNotNull("The language object should not be null", new Language(TestObjects.getJavaPlugin(), 1));
		assertNotNull("The language object should not be null", new Language(TestObjects.getJavaPlugin(), 1, "bukkit", "bukkit"));
	}

	@Ignore
	@Test
	public void testGetMessage() throws Exception
	{
		TestObjects.setBukkitVersion("1_7_R1");
		Language mockedLanguage = spy(new Language(TestObjects.getJavaPlugin(), 1));
		doReturn("TestText").when(mockedLanguage).get("test");
		assertEquals("The language text should match", "TestText", mockedLanguage.getMessage("test").getClassicMessage());
		assertEquals("The language text should match", "TestText", mockedLanguage.getMessage("test", false).getClassicMessage());
		TestObjects.setBukkitVersion("1_1_R1");
		assertEquals("The language text should match", "TestText", mockedLanguage.getMessage("test").getClassicMessage());
		TestObjects.setBukkitVersion("1_8_R1");
		assertEquals("The language text should match", "TestText", mockedLanguage.getMessage("test").getClassicMessage());
	}
}