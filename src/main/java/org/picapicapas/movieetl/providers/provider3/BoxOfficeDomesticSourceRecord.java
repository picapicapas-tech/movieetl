package org.picapicapas.movieetl.providers.provider3;

public class BoxOfficeDomesticSourceRecord {
    private final String filmName;
    private final String yearOfRelease;
    private final String boxOfficeGrossUsd;

    public BoxOfficeDomesticSourceRecord(String filmName, String yearOfRelease, String boxOfficeGrossUsd) {
        this.filmName = filmName;
        this.yearOfRelease = yearOfRelease;
        this.boxOfficeGrossUsd = boxOfficeGrossUsd;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public String getBoxOfficeGrossUsd() {
        return boxOfficeGrossUsd;
    }
}
