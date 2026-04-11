package com.rizafahmi0093.gamematch.data

import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.model.Game

object GameRepository {

    private val gameList = listOf(

        Game(1, "Apex Legends", "Action", "Competitive", 4.5, "Battle royale FPS", R.drawable.apex),
        Game(
            2,
            "Dead by Daylight",
            "Horror",
            "Tegang",
            4.3,
            "Asymmetrical horror multiplayer",
            R.drawable.dbd
        ),
        Game(
            3,
            "Efootball",
            "Sport",
            "Casual",
            4.0,
            "Football simulation game",
            R.drawable.efootball
        ),
        Game(
            4,
            "Elden Ring",
            "RPG",
            "Serius",
            4.8,
            "Open-world dark fantasy RPG",
            R.drawable.elden
        ),
        Game(
            5,
            "Five Nights at Freddy's",
            "Horror",
            "Tegang",
            4.2,
            "Survival horror game",
            R.drawable.fnaff
        ),
        Game(
            6,
            "Fortnite",
            "Action",
            "Competitive",
            4.4,
            "Battle royale shooter",
            R.drawable.fortnite
        ),
        Game(
            7,
            "Hello Neighbor",
            "Horror",
            "Tegang",
            4.1,
            "Stealth horror puzzle",
            R.drawable.hello
        ),
        Game(
            8,
            "Into The Pit",
            "Horror",
            "Serius",
            4.0,
            "Dark horror exploration",
            R.drawable.intothepit
        ),
        Game(
            9,
            "Little Nightmares",
            "Horror",
            "Serius",
            4.5,
            "Puzzle horror adventure",
            R.drawable.little
        ),
        Game(
            10,
            "Minecraft",
            "Simulation",
            "Santai",
            4.9,
            "Sandbox creative game",
            R.drawable.minecraft
        ),
        Game(11, "PUBG", "Action", "Competitive", 4.3, "Realistic battle royale", R.drawable.pubg),
        Game(
            12,
            "Stardew Valley",
            "Simulation",
            "Santai",
            4.8,
            "Farming relaxing game",
            R.drawable.stardey
        ),
        Game(
            13,
            "Valorant",
            "Action",
            "Competitive",
            4.6,
            "Tactical FPS shooter",
            R.drawable.valorant
        ),
        Game(14, "The Witcher 3", "RPG", "Serius", 4.9, "Story-driven RPG", R.drawable.witcher),
        Game(
            15,
            "Black Myth Wukong",
            "RPG",
            "Serius",
            4.7,
            "Action RPG mythological",
            R.drawable.wukong
        ),
        Game(16, "Ghost of Yotei", "RPG", "Serius", 4.4, "Samurai adventure RPG", R.drawable.yotei),
        Game(17, "It Takes Two", "RPG", "Casual", 4.5, "Coop adventure game", R.drawable.takestwo),
        Game(18, "God of War", "Action", "Serius", 4.8, "Story action game", R.drawable.godofwar),
        Game(
            19,
            "Spider-Man",
            "Action",
            "Casual",
            4.7,
            "Open world superhero",
            R.drawable.spiderman
        ),
        Game(20, "Resident Evil", "Horror", "Tegang", 4.6, "Survival horror", R.drawable.resident),
        Game(21, "FIFA 24", "Sports", "Casual", 4.3, "Football game", R.drawable.fifa),
        Game(
            22,
            "Overcooked",
            "Simulation",
            "Casual",
            4.4,
            "Chaos cooking coop",
            R.drawable.overcooked
        ),
        Game(
            23,
            "Phasmophobia",
            "Horror",
            "Tegang",
            4.5,
            "Ghost hunting coop",
            R.drawable.phasmophobia
        ),
        Game(24, "Cyberpunk 2077", "RPG", "Serius", 4.2, "Futuristic RPG", R.drawable.cyberpunk),
        Game(
            25,
            "Detroit Become Human",
            "RPG",
            "Serius",
            4.7,
            "Story driven game",
            R.drawable.detroit
        ),
        Game(26, "Hades", "RPG", "Competitive", 4.6, "Roguelike action", R.drawable.hades)
    )

    fun getRecommendedGames(
        genre: String,
        mood: String,
        platform: String,
        rating: String,
        mode: String
    ): List<Game> {

        val minRating = when (rating) {
            "4.5" -> 4.5
            "4.0" -> 4.0
            else -> 0.0
        }

        val filtered = gameList.filter { game ->

            val genreMatch = game.genre.equals(genre, true)
            val moodMatch = game.mood.equals(mood, true)
            val ratingMatch = game.rating >= minRating

            val platformMatch = when (platform) {
                "PC" -> true
                "Mobile" -> game.name in listOf("PUBG", "Minecraft", "Stardew Valley")
                "Console" -> true
                else -> true
            }

            val modeMatch = when (mode) {
                "Single" -> game.genre != "Action"
                "Multi" -> game.genre == "Action"
                "Coop" -> game.name in listOf("It Takes Two", "Overcooked", "Phasmophobia")
                else -> true
            }

            genreMatch && moodMatch && ratingMatch && platformMatch && modeMatch
        }

        // 🔥 fallback kalau kosong
        return if (filtered.isEmpty()) {
            gameList.filter { it.genre.equals(genre, true) }.take(5)
        } else {
            filtered
        }
    }
}
