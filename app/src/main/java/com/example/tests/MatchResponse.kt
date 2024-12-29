data class MatchResponse(
    val response: Response
)

data class Response(
    val venues: List<Venue>
)

data class Venue(
    val id: String,
    val name: String,
    val location: Location
)

data class Location(
    val address: String,
    val lat: Double,
    val lng: Double
)
