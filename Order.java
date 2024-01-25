import java.util.*;

public class Order {

    public static class byArtist implements Comparator<Track> {

        @Override
        public int compare(Track track1, Track track2)
            {return track1.artist().compareTo(track2.artist());
        }
    }

    public static class byTitle implements Comparator<Track> {

        @Override
        public int compare(Track track1, Track track2) {
            return track1.title().compareTo(track2.title());
        }
    }

    // TODO: Create one for danceability called byDanceability
    // Use the song title as the tie-breaker
    public static class byDanceability implements Comparator<Track> {

        @Override
        public int compare(Track track1, Track track2) {
            if(track1.danceability().compareTo(track2.danceability()) == 0){
                return track1.title().compareTo(track2.title());
            }
            else{
                return track1.danceability().compareTo(track2.danceability());
            }
        }
    }
}