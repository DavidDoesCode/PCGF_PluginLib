/*
 * Copyright (C) 2016, 2018 MarkusWME
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

package at.pcgamingfreaks;

import at.pcgamingfreaks.TestClasses.TestMessage;
import at.pcgamingfreaks.TestClasses.TestSendMethod;
import at.pcgamingfreaks.TestClasses.TestUtils;
import at.pcgamingfreaks.yaml.YAML;
import com.google.common.io.Files;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@PrepareForTest({ Utils.class })
public class LanguageTest
{
	private static File tmpDir;
	private static Logger mockedLogger;
	public static int loggedInfoCount;

	@BeforeClass
	public static void prepareTestData() throws NoSuchFieldException
	{
		tmpDir = Files.createTempDir();
		tmpDir.deleteOnExit();
		loggedInfoCount = 0;
		mockedLogger = mock(Logger.class);
		doAnswer(invocationOnMock -> {
			loggedInfoCount++;
			return null;
		}).when(mockedLogger).info(anyString());
		TestUtils.initReflection();
	}

	@Test
	public void testLanguage()
	{
		Language language = new Language(mockedLogger, tmpDir, 1);
		assertNotNull("The loaded language file should not be null", language);
		language = new Language(mockedLogger, tmpDir, 4, 3);
		assertNotNull("The loaded language file should not be null", language);
		assertFalse("Language data should not be loaded", language.isLoaded());
	}

	@Test(expected = NullPointerException.class)
	public void testLoad() throws Exception
	{
		Language language = new Language(mockedLogger, tmpDir, 1);
		language.load("de", "overwrite");
		assertFalse("Language data should not be loaded", language.isLoaded());
		language.load("en", "update");
        assertFalse("Language data should not be loaded", language.isLoaded());
		language.load("en", "update");
        assertFalse("Language data should not be loaded", language.isLoaded());
		assertEquals("The version of the language file should match", 1, language.getVersion());
		assertEquals("The language string should match", "text", language.get("Lang1"));
		assertEquals("The language string should match", "t", language.getTranslated("Lang4"));
		assertEquals("The language string should match", "§cMessage not found!", language.get("Lang1000"));
		language.set("Language.Lang1000", "New language value");
		assertEquals("The language string should match", "New language value", language.get("Lang1000"));
		assertNotNull("Language data should not be null", language.getLang());
		whenNew(YAML.class).withArguments(anyString()).thenThrow(new IOException());
		assertTrue("Language data should be loaded", language.load("en", "overwrite"));
	}

	@Test
	public void testReload()
	{
		Language language = new Language(mockedLogger, tmpDir, 1);
		language.reload();
		assertNotNull("The language object should not be null", language);
	}

	@Test
	public void testLoadWithConfig()
	{

		Language language = spy(new Language(mockedLogger, tmpDir, 1));
		doReturn(false).when(language).load(anyString(), any(YamlFileUpdateMethod.class));
		Configuration mockedConfig = mock(Configuration.class);
		doReturn("").when(mockedConfig).getLanguage();
		language.load(mockedConfig);
		assertNotNull("The language object should not be null", language);
	}

	@Test
	public void testExtract() throws IllegalAccessException
	{
		Language language = new Language(mockedLogger, tmpDir, 1);
		Field languageField = TestUtils.setAccessible(Language.class, language, "language", "de");
		language.extractFile();
		TestUtils.set(languageField, language, "en");
		language.extractFile();
		TestUtils.setUnaccessible(languageField, language, false);
		assertNotNull("The language object should not be null", language);
	}

	@Test(expected = NullPointerException.class)
	public void testPropertyGetters()
	{
		Language language = new Language(mockedLogger, tmpDir, 1);
		language.load("de", "overwrite");
		assertEquals("Unknown", language.getAuthor());
		assertEquals("de", language.getLanguage());
	}

	@Test(expected = NullPointerException.class)
	public void testGetMessage() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		at.pcgamingfreaks.Language language = new at.pcgamingfreaks.Language(mockedLogger, tmpDir, 1);
		language.load("en", YamlFileUpdateMethod.UPDATE);
		Method getMessage = at.pcgamingfreaks.Language.class.getDeclaredMethod("getMessage", boolean.class, String.class);
		getMessage.setAccessible(true);
		Field messageClasses = TestUtils.setAccessible(at.pcgamingfreaks.Language.class, language, "messageClasses", null);
		assertNull("The returned value should be null when the message classes are not initialized", getMessage.invoke(language, true, "Lang1"));
		TestUtils.set(messageClasses, language, new at.pcgamingfreaks.Language.MessageClassesReflectionDataHolder(Reflection.getConstructor(TestMessage.class, String.class), Reflection.getMethod(TestMessage.class, "setSendMethod", TestSendMethod.class), TestSendMethod.class));
		assertNull("The returned value should be null", getMessage.invoke(language, true, "Lang1"));
		language.set("Language.Lang1_SendMethod", "CHAT");
		assertNotNull("The returned value should not be null", getMessage.invoke(language, false, "Lang1"));
		language.set("Language.Lang1_SendMethod", "TITLE");
		language.set("Language.Lang1_Parameters", "{\"isSubtitle\": false}");
		assertNotNull("The returned value should not be null", getMessage.invoke(language, false, "Lang1"));
		language.set("Language.Lang1_SendMethod", "CHAT");
		assertNotNull("The returned value should not be null", getMessage.invoke(language, false, "Lang1"));
		TestUtils.setUnaccessible(messageClasses, language, false);
		getMessage.setAccessible(false);
	}
}