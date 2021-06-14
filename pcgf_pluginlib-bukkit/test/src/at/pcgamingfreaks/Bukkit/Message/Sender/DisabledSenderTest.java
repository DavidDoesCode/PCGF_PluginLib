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

package at.pcgamingfreaks.Bukkit.Message.Sender;

import at.pcgamingfreaks.Bukkit.Util.Utils;
import at.pcgamingfreaks.TestClasses.TestBukkitPlayer;
import at.pcgamingfreaks.TestClasses.TestBukkitServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

public class DisabledSenderTest
{
	@BeforeClass
	public static void prepareTestData() throws Exception
	{
		if (Bukkit.getServer() == null) {
			Bukkit.setServer(new TestBukkitServer());
		}
		//TestObjects.initNMSReflection();
	}

	@Before
	public void prepareTestObjects() throws Exception
	{
		/*TestObjects.initBukkitOnlinePlayers();
		mockStatic(Utils.class);
		doNothing().when(Utils.class, "sendPacket", any(Player.class), any());*/
	}

	@Ignore
	@Test
	public void testSend()
	{
		int sendPacketCalls = 0;
		TestBukkitPlayer player = new TestBukkitPlayer();
		List<Player> players = new ArrayList<>();
		players.add(player);
		players.add(player);
		DisabledSender disabledSender = new DisabledSender();
		disabledSender.send(player, "");
		verifyStatic(Utils.class, times(sendPacketCalls));
		Utils.sendPacket(any(Player.class), any());
		disabledSender.send(player, "", null);
		verifyStatic(Utils.class, times(sendPacketCalls));
		Utils.sendPacket(any(Player.class), any());
		disabledSender.send(players, "");
		verifyStatic(Utils.class, times(sendPacketCalls));
		Utils.sendPacket(any(Player.class), any());
		disabledSender.send(players, "", null);
		verifyStatic(Utils.class, times(sendPacketCalls));
		Utils.sendPacket(any(Player.class), any());
	}

	@Ignore
	@Test
	public void testBroadcast()
	{
		int sendPacketCalls = 0;
		DisabledSender disabledSender = new DisabledSender();
		disabledSender.broadcast("");
		verifyStatic(Utils.class, times(sendPacketCalls));
		Utils.sendPacket(any(Player.class), any());
		disabledSender.broadcast("", null);
		verifyStatic(Utils.class, times(sendPacketCalls));
		Utils.sendPacket(any(Player.class), any());
	}
}