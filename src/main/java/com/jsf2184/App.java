package com.jsf2184;

import com.jsf2184.utility.ResourceUtility;
import org.apache.log4j.Logger;
import org.apache.spark.sql.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.apache.spark.sql.functions.desc;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger _log = Logger.getLogger(App.class);
    private final Map<Integer, String> _nameMap;
    private String _reviewsPath;
    SparkSession _sparkSession;

    public App(Map<Integer, String> nameMap, String reviewsPath, SparkSession sparkSession) {
        _nameMap = nameMap;
        _reviewsPath = reviewsPath;
        _sparkSession = sparkSession;
    }

    public static Map<Integer, String> loadMovieNames() throws Exception {
        File resourceFile = ResourceUtility.getResourceFile("config/MovieNames.data");
        FileReader fileReader = new FileReader(resourceFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        Map<Integer, String> res = new HashMap<>();
        while ((line = bufferedReader.readLine()) != null) {
            //noinspection RegExpEmptyAlternationBranch
            String[] parts = line.split("|");
            int id = Integer.parseInt(parts[0]);
            res.put(id, parts[1]);
        }
        return res;
    }

    public static void main( String[] args )
    {
        _log.info("Hello World!");
//        String resourcePath = ResourceUtility.getResourcePath("config/log4j.info.properties");

        App app;
        try {
            app = bootstrap();
        } catch (Exception e) {
            _log.error("App.main(): caught bootstrap exception", e);
            return;
        }
        app.run();
    }

    public static App bootstrap() throws Exception {
        SparkSession sparkSession = SparkSession.builder().appName("Simple Application").config("spark.master", "local").getOrCreate();
        if (sparkSession == null) {
            throw new Exception("Unable to create SparkSession");
        }
        Map<Integer, String> nameMap = loadMovieNames();
        String reviewsPath = ResourceUtility.getResourcePath("config/MovieReviews.data");
        App app = new App(nameMap, reviewsPath, sparkSession);
        return app;
    }

    //    userID:int, movieID:int, rating:int, ratingTime:int
    public static class Rating implements Serializable {
        int userId;
        int movieId;
        int numStars;
        int ratingTime;

        public Rating() {
        }

        public static Rating parse(String s) {
            String[] parts = s.split("\t");
            int userId = Integer.parseInt(parts[0]);
            int movieId = Integer.parseInt(parts[1]);
            int numStars = Integer.parseInt(parts[2]);
            int ratingTime = Integer.parseInt(parts[3]);
            Rating res = new Rating();
            res.setUserId(userId);
            res.setMovieId(movieId);
            res.setNumStars(numStars);
            res.setRatingTime(ratingTime);
            return res;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public int getNumStars() {
            return numStars;
        }

        public void setNumStars(int numStars) {
            this.numStars = numStars;
        }

        public int getRatingTime() {
            return ratingTime;
        }

        public void setRatingTime(int ratingTime) {
            this.ratingTime = ratingTime;
        }

        @Override
        public String toString() {
            return "Rating{" +
                    "userId=" + userId +
                    ", movieId=" + movieId +
                    ", numStars=" + numStars +
                    ", ratingTime=" + ratingTime +
                    '}';
        }
    }
    public void run()  {
        Dataset<String> dataset = _sparkSession.read().textFile(_reviewsPath);
        Encoder<Rating> bean = Encoders.bean(Rating.class);
        Dataset<Rating> ratings = dataset.map(Rating::parse, bean);

        ratings.show(10);
//        ratings.foreach(r -> _log.info(r.toString()));
        Dataset<Row> movieAverages = ratings.groupBy("movieId").avg("numStars");
        movieAverages.show(10);

        Dataset<Row> ratingCounts = ratings.groupBy("movieId").count();
        _log.info(String.format("ratingCountSchema: %s", ratingCounts.schema().simpleString()));
        ratingCounts.show(10);

        _log.info(String.format("ratings size = %d", ratings.count()));
        Dataset<Row> movieRatingSummary = ratingCounts.join(movieAverages, "movieId").orderBy(desc("avg(numStars)"), desc("count"));
        movieRatingSummary.show(300);

    }
}
