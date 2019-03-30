package com.knowledgeview.tablet.arabnews.models.local;

import com.knowledgeview.tablet.arabnews.models.data.HomeData;
import com.knowledgeview.tablet.arabnews.models.data.Node;
import com.knowledgeview.tablet.arabnews.models.data.ParentSection;
import com.knowledgeview.tablet.arabnews.models.data.ReadingList;
import com.knowledgeview.tablet.arabnews.models.data.ResultData;
import com.knowledgeview.tablet.arabnews.models.data.SectionListing;
import com.knowledgeview.tablet.arabnews.models.data.Video;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


/**
 * Created by Miriana on 1/4/2018.
 */
@Database(entities = {ParentSection.class, SectionListing.class, ReadingList.class
, Video.class, ResultData.class, Node.class}, version = 3, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ArabNewsDatabase extends RoomDatabase {
    public abstract DaoAccess daoAccess();

}
