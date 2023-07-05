import com.example.coins.network.model.CoinNet
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsApiService {
    @GET("coins/markets")
    suspend fun getCoinList(
        @Query("vs_currency") currency: String = "usd",
    ): List<CoinNet>
}