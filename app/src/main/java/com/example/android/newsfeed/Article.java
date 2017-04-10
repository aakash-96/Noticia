package com.example.android.newsfeed;

/**
 * Created by HP PC on 12-02-2017.
 */

public class Article {
    public boolean is_Image_Downloaded = false;
    private long id;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String date;

    public Article(String Author,String Title,String Description,String Url,String UrlToImage,String Date)
    {
        this.author=Author;
        this.title=Title;
        this.description=Description;
        this.url=Url;
        this.urlToImage=UrlToImage;
        this.date=Date;
    }

    public void set_Image_Downloaded(boolean is_Image_Downloaded) { this.is_Image_Downloaded = is_Image_Downloaded; }
    public void setId(long id) { this.id = id; }

    public long getId() { return this.id; }
    public String getAuthor() { return this.author; }
    public String getTitle() { return this.title; }
    public String getDescription() { return this.description; }
    public String getUrl() { return this.url; }
    public String getUrlToImage() { return this.urlToImage; }
    public String getDate() { return this.date; }
}
