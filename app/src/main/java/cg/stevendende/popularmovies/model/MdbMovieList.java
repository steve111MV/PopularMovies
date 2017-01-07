/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies.model;

import java.util.ArrayList;

/**
 * Used as Array of Movies from themoviedb.org API
 * <p>
 * At the difference of a standard ArrayList, this ArrayList has extra vraibles needful for the APplication
 * it helps loading next pages of the popular movies request
 */
public class MdbMovieList extends ArrayList<MdbMovie> {
    private int pageNumber;
    private int totalPages;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
