package io.arrogantprogrammer.pints;

import java.util.Objects;

public class Beer {

    String name;

    String tagline;

    double abv;

    int ibu;

    public Beer() {
    }

    public Beer(String name, String tagline, double abv, int ibu) {
        this.name = name;
        this.tagline = tagline;
        this.abv = abv;
        this.ibu = ibu;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "name='" + name + '\'' +
                ", tagline='" + tagline + '\'' +
                ", abv=" + abv +
                ", ibu=" + ibu +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Double.compare(beer.abv, abv) == 0 && ibu == beer.ibu && Objects.equals(name, beer.name) && Objects.equals(tagline, beer.tagline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tagline, abv, ibu);
    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public double getAbv() {
        return abv;
    }

    public int getIbu() {
        return ibu;
    }
}
