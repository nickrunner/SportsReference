package dev.firstseed.sports_reference.cbb;

import dev.firstseed.sports_reference.AbstractSeason;
import dev.firstseed.sports_reference.AbstractTeam;
import dev.firstseed.sports_reference.StatModel;

import java.util.ArrayList;

public class Season extends AbstractSeason
{
    public Season(int year, ArrayList<Team> teams)
    {
        super(year, teams);
    }
}
