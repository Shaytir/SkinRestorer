package skinsrestorer.shared.update;

import skinsrestorer.shared.utils.*;
import skinsrestorer.shared.utils.updater.update.spiget.comparator.*;
import java.net.*;
import java.io.*;
import skinsrestorer.shared.utils.updater.update.spiget.*;
import java.util.logging.*;
import com.google.gson.*;
import java.util.*;

public class UpdateChecker
{
    public static final String RESOURCE_INFO = "http://api.spiget.org/v2/resources/%s?ut=%s";
    public static final String RESOURCE_VERSION = "http://api.spiget.org/v2/resources/%s/versions/latest?ut=%s";
    private final int resourceId;
    public String currentVersion;
    private SRLogger log;
    private String userAgent;
    private VersionComparator versionComparator;
    private ResourceInfo latestResourceInfo;
    
    public UpdateChecker(final int resourceId, final String currentVersion, final SRLogger log, final String userAgent) {
        this.versionComparator = VersionComparator.SEM_VER_SNAPSHOT;
        this.resourceId = resourceId;
        this.currentVersion = currentVersion;
        this.log = log;
        this.userAgent = userAgent;
    }
    
    public void checkForUpdate(final UpdateCallback callback) {
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(String.format("http://api.spiget.org/v2/resources/%s?ut=%s", this.resourceId, System.currentTimeMillis())).openConnection();
            connection.setRequestProperty("User-Agent", this.getUserAgent());
            JsonObject jsonObject = new JsonParser().parse((Reader)new InputStreamReader(connection.getInputStream())).getAsJsonObject();
            this.latestResourceInfo = (ResourceInfo)new Gson().fromJson((JsonElement)jsonObject, (Class)ResourceInfo.class);
            connection = (HttpURLConnection)new URL(String.format("http://api.spiget.org/v2/resources/%s/versions/latest?ut=%s", this.resourceId, System.currentTimeMillis())).openConnection();
            connection.setRequestProperty("User-Agent", this.getUserAgent());
            jsonObject = new JsonParser().parse((Reader)new InputStreamReader(connection.getInputStream())).getAsJsonObject();
            this.latestResourceInfo.latestVersion = (ResourceVersion)new Gson().fromJson((JsonElement)jsonObject, (Class)ResourceVersion.class);
            if (this.isVersionNewer(this.currentVersion, this.latestResourceInfo.latestVersion.name)) {
                callback.updateAvailable(this.latestResourceInfo.latestVersion.name, "https://spigotmc.org/" + this.latestResourceInfo.file.url, !this.latestResourceInfo.external);
            }
            else {
                callback.upToDate();
            }
        }
        catch (Exception e) {
            this.log.log(Level.WARNING, "Failed to get resource info from spiget.org", (Throwable)e);
        }
    }
    
    public List<String> getUpToDateMessages(final String currentVersion, final boolean bungeeMode) {
        final List<String> upToDateMessages = new LinkedList<String>();
        if (bungeeMode) {
        }
        return upToDateMessages;
    }
    
    public List<String> getUpdateAvailableMessages(final String newVersion, final String downloadUrl, final boolean hasDirectDownload, final String currentVersion, final boolean bungeeMode) {
        return this.getUpdateAvailableMessages(newVersion, downloadUrl, hasDirectDownload, currentVersion, bungeeMode, false, null);
    }
    
    public List<String> getUpdateAvailableMessages(final String newVersion, final String downloadUrl, final boolean hasDirectDownload, final String currentVersion, final boolean bungeeMode, final boolean updateDownloader, final String failReason) {
        final List<String> updateAvailableMessages = new LinkedList<String>();
        if (bungeeMode) {
        }
        if (updateDownloader && hasDirectDownload) {
            if (failReason == null) {
            }
            else {
            }
        }
        else {
        }
        return updateAvailableMessages;
    }
    
    public boolean isVersionNewer(final String oldVersion, final String newVersion) {
        return this.versionComparator.isNewer(oldVersion, newVersion);
    }
    
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }
    
    public ResourceInfo getLatestResourceInfo() {
        return this.latestResourceInfo;
    }
}
