package cat.itb.m08_uf1_p6_retrofit.models;

import com.google.gson.annotations.SerializedName;

public class Film {
    private String title;
    @SerializedName("opening_crawl")
    private String openingCrawl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOpeningCrawl() {
        return openingCrawl;
    }

    public void setOpeningCrawl(String openingCrawl) {
        this.openingCrawl = openingCrawl;
    }
}
