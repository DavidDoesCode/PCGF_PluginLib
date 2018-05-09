/*
 *   Copyright (C) 2018 GeorgH93
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

package at.pcgamingfreaks.Updater.UpdateProviders;

import at.pcgamingfreaks.Version;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.logging.Logger;

@SuppressWarnings("RedundantThrows")
public abstract class BaseOnlineProviderWithDownload extends BaseOnlineProvider
{
	protected UpdateFile lastResult = null;

	//region constructors
	protected BaseOnlineProviderWithDownload(@NotNull Logger logger)
	{
		super(logger);
	}

	protected BaseOnlineProviderWithDownload(@NotNull String userAgent, @NotNull Logger logger)
	{
		super(userAgent, logger);
	}
	//endregion

	@Override
	public final boolean provideDownloadURL()
	{
		return true;
	}

	//region getter for the latest version
	@Override
	public @NotNull String getLatestFileName() throws NotSuccessfullyQueriedException
	{
		if(lastResult == null) { throw new NotSuccessfullyQueriedException(); }
		return lastResult.getFileName();
	}

	@Override
	public @NotNull String getLatestName() throws RequestTypeNotAvailableException, NotSuccessfullyQueriedException
	{
		if(lastResult == null) { throw new NotSuccessfullyQueriedException(); }
		return lastResult.getName();
	}

	@Override
	public @NotNull String getLatestVersionAsString() throws NotSuccessfullyQueriedException
	{
		return getLatestVersion().toString();
	}

	@Override
	public @NotNull Version getLatestVersion() throws NotSuccessfullyQueriedException
	{
		if(lastResult == null) throw new NotSuccessfullyQueriedException();
		return lastResult.getVersion();
	}

	@Override
	public @NotNull URL getLatestFileURL() throws RequestTypeNotAvailableException, NotSuccessfullyQueriedException
	{
		if(lastResult == null) throw new NotSuccessfullyQueriedException();
		return lastResult.getDownloadURL();
	}
	//endregion
}