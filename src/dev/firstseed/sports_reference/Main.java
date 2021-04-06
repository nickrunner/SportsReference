package dev.firstseed.sports_reference;

import dev.firstseed.sports_reference.cbb.CBB;
import dev.firstseed.sports_reference.cbb.Season;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class Main {



    public static void main(String[] args)
    {
        SportsReference.cbb(new File("C:/FS/cache"));
        CBB cbb = SportsReference.cbb();

        ArrayList<String> statFilter = new ArrayList<>();
        statFilter.add("g");
        //statFilter.add("off_rtg_PG");
        //statFilter.add("def_rtg_PG");
        //statFilter.add("opp_off_rtg_PG");
        //statFilter.add("srs");
        statFilter.add("mp_PG");
        statFilter.add("wins_PG");
        statFilter.add("losses_PG");
        statFilter.add("opp_mp_PG");
        statFilter.add("OffComp");
        statFilter.add("DefComp");
        statFilter.add("GenComp");
        statFilter.add("CompleteComp");
        //statFilter.add("opp_pace_PG");

        Season season = cbb.getSeason(2021);

        int start = 2016;
        int end = 2019;
        double minThresh = 1640;
        double maxThresh = 1700; //1580 seems good
        double thresh = 1700;



        //StatModel model =  SportsReference.cbb().getStatModel(start, end, season.getFilteredTeamStatNames(statFilter));
        StatModel model = new StatModel(season.getFilteredTeamStatNames(statFilter));

        double gain = 0.01;
        System.out.println("Optimizing "+start+" to "+end+" for "+thresh);
        optimizeModelForScore(model, statFilter, start, end, thresh, gain);

        season.calculateComposites(model);
        season.getPredictedBracket(model).print();

    }

    public static void optimizeModel(StatModel model, ArrayList<String> statFilter, final int startYear, final int endYear, double threshold, double gain)
    {
        int c = 0;
        double accuracy = SportsReference.cbb().adjustModel(model, statFilter, gain, startYear, endYear);
        double acc = 0;
        //System.out.println("Count: "+(c++)+" thresh:"+threshold+" accuracy = "+accuracy);
        while( accuracy  < threshold)
        {
            c++;
             acc = SportsReference.cbb().adjustModel(model, statFilter, gain, startYear, endYear);
            if(acc > accuracy)
            {
                try
                {
                    model.save(new File("C:/FS/models/"+startYear+"_"+endYear+"_"+Math.round(threshold*100)+".json"));
                }
                catch (Exception e) {
                    System.out.println("Failed to save model");
                    e.printStackTrace();
                }
                /*System.out.println("\n*****");
                System.out.println("Count: "+(c)+" thresh:"+threshold+" accuracy = "+acc);
                System.out.println("*****\n");*/
                accuracy = acc;
            }
                //System.out.print("\rCount: "+(c)+" thresh:"+threshold+" accuracy = "+acc);
        }

    }

    public static void optimizeModelForScore(StatModel model, ArrayList<String> statFilter, final int startYear, final int endYear, double threshold, double gain)
    {
        int c = 0;
        double score = SportsReference.cbb().adjustModelForScore(model, statFilter, gain, startYear, endYear);
        double sc = 0;
        //System.out.println("Count: "+(c++)+" thresh:"+threshold+" accuracy = "+score);
        while( score  < threshold)
        {
            c++;
            sc = SportsReference.cbb().adjustModelForScore(model, statFilter, gain, startYear, endYear);
            if(sc > score)
            {


                score = sc;


            }
            System.out.println("... Count: "+(c)+" thresh:"+threshold+" score = "+sc);
           /* if(c > 500)
            {
                System.out.println("Failed: Count: "+(c)+" thresh:"+threshold+" score = "+sc);
                return;
            }*/

        }
        try
        {
            model.save(new File("C:/FS/models/"+startYear+"_"+endYear+"_"+Math.round(threshold)+".json"));
        }
        catch (Exception e) {
            System.out.println("Failed to save model");
            e.printStackTrace();
        }
        System.out.println("\rOptimized! Count: "+(c)+" thresh:"+threshold+" score = "+sc);
    }

}
