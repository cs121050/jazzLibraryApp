package com.example.jazzLibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class JazzLibraryDAO {


    public static Connection getConnection() {

        Connection conn = null;


        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.2.59:1433/jazzLibraryDB", "sa", "admin");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("DB Connectrion exception " + ex);

        }

        return conn;
    }


    public static String[] retriveArtistNames() {

        List<String> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT artist_name,artist_surname FROM artist \n");
            ResultSet rs = ps.executeQuery();

            while (rs.next())
                list.add(rs.getString(1) + " " + rs.getString(2));

            con.close();
        } catch (SQLException ex) {

        }

        String[] videoNames = list.toArray(new String[0]);
        return videoNames;
    }


    public static List<ArtistAndVideoCount> retriveArtistAndVideoCount_By(int instrument_id) {                 //!!!!!!!!!!!!!!!!

        List<ArtistAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT artist.artist_id , artist.artist_name , artist.artist_surname ,count(*) as 'videoCount'\n" +
                            "FROM artist\n" +
                            "inner join videoContainsArtist ON artist.artist_id=videoContainsArtist.artist_id\n" +
                            "where instrument_id=?\n    " +
                            "group by  artist.artist_id , artist.artist_name , artist.artist_surname \n" +
                            ";\n"
            );


            ps.setInt(1, instrument_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArtistAndVideoCount artistAndVideoCount = new ArtistAndVideoCount();
                artistAndVideoCount.setArtist_id(rs.getInt(1));
                artistAndVideoCount.setArtist_name(rs.getString(2));
                artistAndVideoCount.setArtist_surname(rs.getString(3));
                artistAndVideoCount.setArtist_video_count(rs.getInt(4));
                list.add(artistAndVideoCount);
            }
            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<ArtistAndVideoCount> retriveArtistAndVideoCount_By(int instrument_id, int type_id) {

        List<ArtistAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT artist.artist_id , artist.artist_name , artist.artist_surname   ,count(*) as 'videoCount'\n" +
                            "FROM artist\n" +
                            "inner join videoContainsArtist ON artist.artist_id=videoContainsArtist.artist_id\n" +
                            "inner join Video ON Video.video_id=videoContainsArtist.video_id\n" +
                            "where instrument_id=? and type_id=?\n" +
                            "group by  artist.artist_id , artist.artist_name , artist.artist_surname;\n"
            );
            ps.setInt(1, instrument_id);
            ps.setInt(2, type_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArtistAndVideoCount artistAndVideoCount = new ArtistAndVideoCount();
                artistAndVideoCount.setArtist_id(rs.getInt(1));
                artistAndVideoCount.setArtist_name(rs.getString(2));
                artistAndVideoCount.setArtist_surname(rs.getString(3));
                artistAndVideoCount.setArtist_video_count(rs.getInt(4));
                list.add(artistAndVideoCount);
            }
            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<ArtistAndVideoCount> retriveArtistAndVideoCount_By(int instrument_id, int type_id, int duration_id) {

        List<ArtistAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT artist.artist_id , artist.artist_name , artist.artist_surname   ,count(*) as 'videoCount'\n" +
                            "FROM artist\n" +
                            "inner join videoContainsArtist ON artist.artist_id=videoContainsArtist.artist_id\n" +
                            "inner join Video ON Video.video_id=videoContainsArtist.video_id\n" +
                            "where instrument_id=? and type_id=? and duration_id=?\n" +
                            "group by  artist.artist_id , artist.artist_name , artist.artist_surname;\n"
            );
            ps.setInt(1, instrument_id);
            ps.setInt(2, type_id);
            ps.setInt(3, duration_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArtistAndVideoCount artistAndVideoCount = new ArtistAndVideoCount();
                artistAndVideoCount.setArtist_id(rs.getInt(1));
                artistAndVideoCount.setArtist_name(rs.getString(2));
                artistAndVideoCount.setArtist_surname(rs.getString(3));
                artistAndVideoCount.setArtist_video_count(rs.getInt(4));
                list.add(artistAndVideoCount);
            }
            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<TypeAndVideoCount> retriveTypeAndVideoCount_By() {

        List<TypeAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  type.type_id ,type.type_name, count(*) as 'videoCount'\n" +
                            "FROM type\n" +
                            "inner join video ON video.type_id=type.type_id\n" +
                            "group by type.type_id ,type.type_name;\n");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeAndVideoCount typeAndVideoCount = new TypeAndVideoCount();
                typeAndVideoCount.setType_id(rs.getInt(1));
                typeAndVideoCount.setType_name(rs.getString(2));
                typeAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(typeAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<TypeAndVideoCount> retriveTypeAndVideoCount_By(int instrument_id) {

        List<TypeAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  type.type_id, type.type_name, count(*) as 'videoCount'\n" +
                            "FROM type\n" +
                            "inner join video ON video.type_id=type.type_id\n" +
                            "inner join videoContainsArtist ON video.video_id=videoContainsArtist.video_id\n" +
                            "inner join Artist ON artist.artist_id=videoContainsArtist.artist_id\n" +
                            "where instrument_id=?\n" +
                            "group by type.type_id, type.type_name;\n");
            ps.setInt(1, instrument_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeAndVideoCount typeAndVideoCount = new TypeAndVideoCount();
                typeAndVideoCount.setType_id(rs.getInt(1));
                typeAndVideoCount.setType_name(rs.getString(2));
                typeAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(typeAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }

    public static List<TypeAndVideoCount> retriveTypeAndVideoCount_By(int artist_id, int instrument_id) {

        List<TypeAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  Type.type_id ,type.type_name, count(*) as 'videoCount'\n" +
                            "FROM type\n" +
                            "inner join Video ON Video.type_id=type.type_id\n" +
                            "inner join videoContainsArtist ON videoContainsArtist.video_id=Video.video_id\n" +
                            "where artist_id=?\n" +
                            "group by Type.type_id ,type.type_name\n" +
                            ";");
            ps.setInt(1, artist_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeAndVideoCount typeAndVideoCount = new TypeAndVideoCount();
                typeAndVideoCount.setType_id(rs.getInt(1));
                typeAndVideoCount.setType_name(rs.getString(2));
                typeAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(typeAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<TypeAndVideoCount> retriveTypeAndVideoCount_By(int instrument_id, int artist_id, int duration_id) {

        List<TypeAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  type.type_id ,type.type_name, count(*) as 'videoCount'\n" +
                            "FROM type\n" +
                            "inner join video ON video.type_id=type.type_id\n" +
                            "inner join VideoContainsArtist ON VideoContainsArtist.video_id=video.video_id\n" +
                            "where artist_id=1 and duration_id=1\n" +
                            "group by type.type_id ,type.type_name;\n");
            ps.setInt(1, artist_id);
            ps.setInt(2, duration_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeAndVideoCount typeAndVideoCount = new TypeAndVideoCount();
                typeAndVideoCount.setType_id(rs.getInt(1));
                typeAndVideoCount.setType_name(rs.getString(2));
                typeAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(typeAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }

    public static List<TypeAndVideoCount> retriveTypeAndVideoCount_By(int instrument_id, int artist_id, int type_id, int duration_id) {

        List<TypeAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  type.type_id ,type.type_name, count(*) as 'videoCount'\n" +
                            "FROM type\n" +
                            "inner join video ON video.type_id=type.type_id\n" +
                            "where Type.type_id=? and duration_id=?\n" +
                            "group by type.type_id ,type.type_name;"
            );
            ps.setInt(1, type_id);
            ps.setInt(2, duration_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeAndVideoCount typeAndVideoCount = new TypeAndVideoCount();
                typeAndVideoCount.setType_id(rs.getInt(1));
                typeAndVideoCount.setType_name(rs.getString(2));
                typeAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(typeAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<TypeAndVideoCount> retriveTypeAndVideoCount_By(int instrument_id, int artist_id, int type_id, int duration_id, int nothing) {

        List<TypeAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  type.type_id ,type.type_name, count(*) as 'videoCount'\n" +
                            "FROM type\n" +
                            "inner join video ON video.type_id=type.type_id\n" +
                            "inner join videoContainsArtist ON videoContainsArtist.video_id=video.video_id\n" +
                            "inner join Artist ON  Artist.artist_id=videoContainsArtist.artist_id\n" +
                            "where instrument_id=? and duration_id=?\n" +
                            "group by type.type_id ,type.type_name;\n");
            ps.setInt(1, instrument_id);
            ps.setInt(2, duration_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TypeAndVideoCount typeAndVideoCount = new TypeAndVideoCount();
                typeAndVideoCount.setType_id(rs.getInt(1));
                typeAndVideoCount.setType_name(rs.getString(2));
                typeAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(typeAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<DurationAndVideoCount> retriveDurationAndVideoCount_By(int type_id) {

        List<DurationAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT duration.duration_id ,duration.duration_name,  count(*) as 'videoCount'\n" +
                            "FROM duration\n" +
                            "inner join video ON Video.duration_id=duration.duration_id\n" +
                            "WHERE type_id=?\n" +
                            "group by duration.duration_id ,duration.duration_name;"
            );
            ps.setInt(1, type_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DurationAndVideoCount durationAndVideoCount = new DurationAndVideoCount();
                durationAndVideoCount.setDuration_id(rs.getInt(1));
                durationAndVideoCount.setDuration_name(rs.getString(2));
                durationAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(durationAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<DurationAndVideoCount> retriveDurationAndVideoCount_By(int type_id, int instrument_id) {

        List<DurationAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  duration.duration_id ,duration.duration_name, count(*) as 'videoCount'\n" +
                            "FROM duration\n" +
                            "inner join video ON video.duration_id=duration.duration_id\n" +
                            "inner join VideoContainsArtist ON VideoContainsArtist.video_id=video.video_id\n" +
                            "inner join Artist ON Artist.artist_id=VideoContainsArtist.artist_id\n" +
                            "where type_id=? and instrument_id=?\n" +
                            "group by duration.duration_id ,duration.duration_name;\n"
            );
            ps.setInt(1, type_id);
            ps.setInt(2, instrument_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DurationAndVideoCount durationAndVideoCount = new DurationAndVideoCount();
                durationAndVideoCount.setDuration_id(rs.getInt(1));
                durationAndVideoCount.setDuration_name(rs.getString(2));
                durationAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(durationAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<DurationAndVideoCount> retriveDurationAndVideoCount_By(int type_id, int instrument_id, int artist_id) {

        List<DurationAndVideoCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  duration.duration_id ,duration.duration_name, count(*) as 'videoCount'\n" +
                            "FROM duration\n" +
                            "inner join video ON video.duration_id=duration.duration_id\n" +
                            "inner join VideoContainsArtist ON videoContainsArtist.video_id=video.video_id\n" +
                            "where type_id=? AND artist_id=?\n" +
                            "group by duration.duration_id ,duration.duration_name\n" +
                            ";");
            ps.setInt(1, type_id);
            ps.setInt(2, artist_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DurationAndVideoCount durationAndVideoCount = new DurationAndVideoCount();
                durationAndVideoCount.setDuration_id(rs.getInt(1));
                durationAndVideoCount.setDuration_name(rs.getString(2));
                durationAndVideoCount.setVideo_count(rs.getInt(3));
                list.add(durationAndVideoCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<InstrumentAndArtistCount> retriveInstrumentAndArtistCount_By() {                 //!!!!!!!!!!!!!!!!

        List<InstrumentAndArtistCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT instrument.instrument_id , instrument.instrument_name ,count(Artist_id ) as 'artistCount'\n" +
                            "FROM instrument\n" +
                            "inner join Artist ON instrument.instrument_id=Artist.instrument_id\n" +
                            "group by  instrument.instrument_id , instrument.instrument_name\n" +
                            "order by instrument_id" +
                            ";\n");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InstrumentAndArtistCount instrumentAndArtistCount = new InstrumentAndArtistCount();
                instrumentAndArtistCount.setInstrument_id(rs.getInt(1));
                instrumentAndArtistCount.setInstrument_name(rs.getString(2));
                instrumentAndArtistCount.setArtist_count(rs.getInt(3));
                list.add(instrumentAndArtistCount);
            }


            con.close();
        } catch (SQLException ex) {

        }
        return list;            //... tin opoia tha e3ageis sto telos gia ton     Servlet to use
    }


    public static List<InstrumentAndArtistCount> retriveInstrumentAndArtistCount_By(int type_id) {

        List<InstrumentAndArtistCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  instrument.instrument_id, instrument.instrument_name, count(*) as 'artistCount'\n" +
                            "FROM instrument\n" +
                            "inner join Artist ON  Artist.instrument_id  =  instrument.instrument_id\n" +
                            "inner join videoContainsArtist ON Artist.artist_id=videoContainsArtist.artist_id\n" +
                            "inner join Video ON Video.video_id=videoContainsArtist.video_id\n" +
                            "where type_id=?\n" +
                            "group by instrument.instrument_id, instrument.instrument_name;\n"
            );
            ps.setInt(1, type_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InstrumentAndArtistCount instrumentAndArtistCount = new InstrumentAndArtistCount();
                instrumentAndArtistCount.setInstrument_id(rs.getInt(1));
                instrumentAndArtistCount.setInstrument_name(rs.getString(2));
                instrumentAndArtistCount.setArtist_count(rs.getInt(3));
                list.add(instrumentAndArtistCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<InstrumentAndArtistCount> retriveInstrumentAndArtistCount_By(int type_id, int duration_id) {

        List<InstrumentAndArtistCount> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT  instrument.instrument_id, instrument.instrument_name, count(*) as 'artistCount'\n" +
                            "FROM instrument\n" +
                            "inner join Artist ON  Artist.instrument_id  =  instrument.instrument_id\n" +
                            "inner join videoContainsArtist ON Artist.artist_id=videoContainsArtist.artist_id\n" +
                            "inner join Video ON Video.video_id=videoContainsArtist.video_id\n" +
                            "where type_id=? and duration_id=?\n" +
                            "group by instrument.instrument_id, instrument.instrument_name\n" +
                            ";");
            ps.setInt(1, type_id);
            ps.setInt(2, duration_id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InstrumentAndArtistCount instrumentAndArtistCount = new InstrumentAndArtistCount();
                instrumentAndArtistCount.setInstrument_id(rs.getInt(1));
                instrumentAndArtistCount.setInstrument_name(rs.getString(2));
                instrumentAndArtistCount.setArtist_count(rs.getInt(3));
                list.add(instrumentAndArtistCount);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static String[] retriveVideoNames() {

        List<String> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT video_name FROM video \n");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString(1));
            }

            con.close();
        } catch (SQLException ex) {

        }
        String[] videoNames = list.toArray(new String[0]);
        return videoNames;
    }

    public static List<Video> retriveVideo_By() {

        List<Video> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT video.video_id , video.video_name , video.video_duration , video.duration_id , video.video_path\n" +
                            "FROM video \n");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video video = new Video();
                video.setVideo_id(rs.getInt(1));
                video.setVideo_name(rs.getString(2));
                video.setVideo_duration(rs.getString(3));
                video.setDuration_id(rs.getInt(4));
                video.setVideo_path(rs.getString(5));
                list.add(video);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<Video> retriveVideo_By(int artist_id) {

        List<Video> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT video.video_id , video.video_name , video.video_duration , video.duration_id , video.video_path\n" +
                            "FROM video \n" +
                            "inner join VideoContainsArtist on VideoContainsArtist.video_id=video.video_id\n" +
                            "WHERE artist_id=?");
            ps.setInt(1, artist_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video video = new Video();
                video.setVideo_id(rs.getInt(1));
                video.setVideo_name(rs.getString(2));
                video.setVideo_duration(rs.getString(3));
                video.setDuration_id(rs.getInt(4));
                video.setVideo_path(rs.getString(5));
                list.add(video);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<Video> retriveVideo_By(int artist_id, int type_id) {

        List<Video> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT video.video_id , video.video_name , video.video_duration , video.duration_id , video.video_path\n" +
                            "FROM video\n" +
                            "inner join VideoContainsArtist on VideoContainsArtist.video_id=video.video_id\n" +
                            "WHERE artist_id=? and type_id=?\n");
            ps.setInt(1, artist_id);
            ps.setInt(2, type_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video video = new Video();
                video.setVideo_id(rs.getInt(1));
                video.setVideo_name(rs.getString(2));
                video.setVideo_duration(rs.getString(3));
                video.setDuration_id(rs.getInt(4));
                video.setVideo_path(rs.getString(5));
                list.add(video);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<Video> retriveVideo_By(int artist_id, int type_id, int duration_id) {

        List<Video> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT video.video_id , video.video_name , video.video_duration , video.duration_id , video.video_path\n" +
                            "FROM video\n" +
                            "inner join VideoContainsArtist on VideoContainsArtist.video_id=video.video_id\n" +
                            "WHERE artist_id=? and type_id=? and duration_id=?\n");
            ps.setInt(1, artist_id);
            ps.setInt(2, type_id);
            ps.setInt(3, duration_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video video = new Video();
                video.setVideo_id(rs.getInt(1));
                video.setVideo_name(rs.getString(2));
                video.setVideo_duration(rs.getString(3));
                video.setDuration_id(rs.getInt(4));
                video.setVideo_path(rs.getString(5));
                list.add(video);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<Video> retriveVideo_By(String artist_name, String artist_surname) {

        List<Video> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT video.video_id , video.video_name , video.video_duration , video.duration_id , video.video_path\n" +
                            "FROM video\n" +
                            "inner join VideoContainsArtist on VideoContainsArtist.video_id=video.video_id\n" +
                            "inner join Artist on Artist.artist_id=VideoContainsArtist.artist_id\n" +
                            "WHERE artist_name=? and artist_surname=?");
            ps.setString(1, artist_name);
            ps.setString(2, artist_surname);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video video = new Video();
                video.setVideo_id(rs.getInt(1));
                video.setVideo_name(rs.getString(2));
                video.setVideo_duration(rs.getString(3));
                video.setDuration_id(rs.getInt(4));
                video.setVideo_path(rs.getString(5));
                list.add(video);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<Video> retriveVideo_By(String video_name) {

        List<Video> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT video_id , video_name , video_duration , duration_id , video_path\n" +
                            "FROM video\n" +
                            "WHERE video_name LIKE ?");
            ps.setString(1, video_name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video video = new Video();
                video.setVideo_id(rs.getInt(1));
                video.setVideo_name(rs.getString(2));
                video.setVideo_duration(rs.getString(3));
                video.setDuration_id(rs.getInt(4));
                video.setVideo_path(rs.getString(5));
                list.add(video);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<Video> retriveVideo_By(int nothing1, int nothing2, String ContainsInVideo) {

        List<Video> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT video.video_id , video.video_name , video.video_duration , video.duration_id , video.video_path\n" +
                            "FROM video\n" +
                            "inner join VideoContainsArtist on VideoContainsArtist.video_id=video.video_id\n" +
                            "inner join Artist on Artist.artist_id=VideoContainsArtist.artist_id\n" +
                            "WHERE artist_name=? and type_surname=?");
            ps.setString(1, ContainsInVideo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Video video = new Video();
                video.setVideo_id(rs.getInt(1));
                video.setVideo_name(rs.getString(2));
                video.setVideo_duration(rs.getString(3));
                video.setDuration_id(rs.getInt(4));
                video.setVideo_path(rs.getString(5));
                list.add(video);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }


    public static List<SplashScreenQuotes> retriveSplashScreenQuotes() {

        List<SplashScreenQuotes> list = new ArrayList<>();
        Connection con = JazzLibraryDAO.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM splashscreenQuotes\n");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SplashScreenQuotes splashScreenQuotes = new SplashScreenQuotes();
                splashScreenQuotes.setQuote_id(rs.getInt(1));
                splashScreenQuotes.setQuote_txt(rs.getString(2));
                list.add(splashScreenQuotes);
            }

            con.close();
        } catch (SQLException ex) {

        }
        return list;
    }





}