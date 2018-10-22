package com.example.vedad.moviesearch.models;

import java.util.List;

/**
 * Created by Vedad on 19.10.2018..
 */

public class ApiResult<T> {
        public int page;
        public int total_results;
        public int total_pages;
        public List<T> results;
}
