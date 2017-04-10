package com.example.android.newsfeed.Data;

import android.provider.BaseColumns;

/**
 * Created by HP PC on 17-02-2017.
 */

public class ArticleContract {
    private ArticleContract(){}    //We dont need any instance of contract class

    //Entertainment
    public static final class EntertainmentEntry implements BaseColumns{

        public static final String TABLE_NAME = "ENTERTAINMENT";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }

    public static final class topEntertainmentEntry implements BaseColumns{

        public static final String TABLE_NAME = "TOPENTERTAINMENT";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }

    //Technology
    public static final class TechnologyEntry implements BaseColumns{

        public static final String TABLE_NAME = "TECHNOLOGY";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }
    public static final class topTechnologyEntry implements BaseColumns{

        public static final String TABLE_NAME = "TOPTECHNOLOGY";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }

    //Sports
    public static final class SportsEntry implements BaseColumns{

        public static final String TABLE_NAME = "SPORTS";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }
    public static final class topSportsEntry implements BaseColumns{

        public static final String TABLE_NAME = "TOPSPORTS";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }

    //Business
    public static final class BusinessEntry implements BaseColumns{

        public static final String TABLE_NAME = "BUSINESS";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }
    public static final class topBusinessEntry implements BaseColumns{

        public static final String TABLE_NAME = "TOPBUSINESS";

        public static final String COLUMN_ISIMAGEDOWNLOADED = "isimagedownloaded";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_URLTOIMAGE = "urltoimage";
        public static final String COLUMN_DATE = "date";
    }
}
