package com.knowledgeview.tablet.arabnews.models.local;

import com.knowledgeview.tablet.arabnews.models.data.Node;
import com.knowledgeview.tablet.arabnews.models.data.ParentSection;
import com.knowledgeview.tablet.arabnews.models.data.ReadingList;
import com.knowledgeview.tablet.arabnews.models.data.SectionListing;
import com.knowledgeview.tablet.arabnews.models.data.Video;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by Miriana on 1/4/2018.
 */

@Dao
public interface DaoAccess {

    @Query("SELECt * from ParentSection")
    LiveData<List<ParentSection>> getAllSections();

    @Query("Select * from ParentSection")
    List<ParentSection> getAllTabs();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTabs(List<ParentSection> tabs);

    @Query("DELETE FROM ParentSection")
    void deleteTabs();

    /*sections*/
    @Query("SELECt * from SectionListing where categoryID=:categoryID")
    List<SectionListing> getAllListing(int categoryID);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNews(List<SectionListing> news);

    @Query("DELETE FROM SectionListing where categoryID= :id")
    void deleteNews(int id);

    @Query("Select * from SectionListing where entityID=:entityID")
    SectionListing getSection(String entityID);


    /*Reading List*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReadingList(ReadingList news);


    @Query("SELECt * from ReadingList ")
    LiveData<List<ReadingList>> getAllReadingList();


    @Query("Delete from ReadingList where entityID=:id")
    void removeReadingList(String id);


    /*Videos*/
    @Query("Delete from video")
    void deleteVideos();

    @Insert
    void insertVideos(List<Video> videos);

    @Query("Select * from video")
    List<Video> getAllVideos();

    /*Node*/
    @Query("select * from Node where entityID=:entityID")
    LiveData<List<Node>> getNodes(String entityID);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNodes(List<Node> nodes);

}

