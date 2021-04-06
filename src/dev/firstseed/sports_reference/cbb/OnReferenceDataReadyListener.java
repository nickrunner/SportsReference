package dev.firstseed.sports_reference.cbb;

import dev.firstseed.sports_reference.AbstractSeason;

public interface OnReferenceDataReadyListener
{
    void onSeasonReady(int year, Season teams);
    void statusUpdate(int total, int remaining);
}
