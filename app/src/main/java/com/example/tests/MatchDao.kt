import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tests.SavedMatch
@Dao
interface MatchDao {

    @Insert
    fun insert(match: SavedMatch)

    @Delete
    fun delete(match: SavedMatch)

    @Query("SELECT * FROM saved_matches WHERE id = :matchId")
    fun getMatchById(matchId: String): SavedMatch?

    @Query("SELECT * FROM saved_matches")
    fun getAllMatches(): List<SavedMatch>  // Retrieve all saved matches
}
