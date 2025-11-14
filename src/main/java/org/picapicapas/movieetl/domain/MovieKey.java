package org.picapicapas.movieetl.domain;

import java.util.Objects;

public class MovieKey {
    private final String normalizedTitle;
    private final Integer releaseYear;

    public MovieKey(String title, Integer releaseYear) {
        this.normalizedTitle = normalizeTitle(title);
        this.releaseYear = releaseYear;
    }

    private static String normalizeTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Movie title cannot be null");
        }
        return title.trim().toLowerCase();
    }

    public String getNormalizedTitle() {
        return normalizedTitle;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieKey movieKey = (MovieKey) o;
        return Objects.equals(normalizedTitle, movieKey.normalizedTitle) &&
               Objects.equals(releaseYear, movieKey.releaseYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalizedTitle, releaseYear);
    }

    @Override
    public String toString() {
        if (releaseYear != null) {
            return normalizedTitle + " (" + releaseYear + ")";
        }
        return normalizedTitle;
    }
}
