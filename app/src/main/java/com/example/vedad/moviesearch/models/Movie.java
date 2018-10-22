package com.example.vedad.moviesearch.models;

import java.util.List;

/**
 * Created by Vedad on 19.10.2018..
 */

public class Movie {
        public int vote_count;
        public int id;
        public Boolean video;
        public double vote_average;
        public String title;
        public double popularity;
        public String poster_path;
        public String original_language;
        public String original_title;
        public List<Integer> genre_ids;
        public String backdrop_path;
        public Boolean adult;
        public String overview;
        public String release_date;
}
