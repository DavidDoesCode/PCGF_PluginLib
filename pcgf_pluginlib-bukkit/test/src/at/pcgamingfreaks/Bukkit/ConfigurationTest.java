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

import at.pcgamingfreaks.TestClasses.TestBukkitConfiguration;
import at.pcgamingfreaks.TestClasses.TestObjects;
import at.pcgamingfreaks.TestClasses.TestUtils;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class ConfigurationTest
{
	@Ignore
	@Test
	public void testConfiguration()
	{
		assertNotNull("The configuration object should not be null", new Configuration(TestObjects.getJavaPlugin(), 1));
		assertNotNull("The configuration object should not be null", new Configuration(TestObjects.getJavaPlugin(), 1, "config.yml"));
		assertNotNull("The configuration object should not be null", new Configuration(TestObjects.getJavaPlugin(), 1, 2));
	}

	@Ignore
	@Test
	public void testDeprecatedMethods() throws NoSuchFieldException
	{
		TestUtils.initReflection();
		TestBukkitConfiguration configuration = new TestBukkitConfiguration(TestObjects.getJavaPlugin(), 1);
		assertTrue("The version UUID compatibility should match", configuration.getIsBukkitVersionUUIDCompatible());
	}
}