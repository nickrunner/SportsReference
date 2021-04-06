package dev.firstseed.sports_reference;

import dev.firstseed.sports_reference.cbb.Season;

public interface DownloadListener {
    void onDownloadComplete(AbstractSeason season);
    void onDownloadStatus(int total, int remaining);
}
