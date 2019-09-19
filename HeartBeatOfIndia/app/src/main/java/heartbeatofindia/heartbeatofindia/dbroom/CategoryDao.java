package heartbeatofindia.heartbeatofindia.dbroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Query("SELECT * FROM Category WHERE parent_id IN (:cid)")
    List<Category> loadAllByIds(int[] cid);

    @Query("SELECT * FROM Category WHERE parent_id =:parentID")
    List<Category> getAllCateByParentID(int parentID);

    @Query("SELECT * FROM Category WHERE cid =:cid LIMIT 1")
    Category findById(int cid);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM Category")
    void deleteAll();
}
