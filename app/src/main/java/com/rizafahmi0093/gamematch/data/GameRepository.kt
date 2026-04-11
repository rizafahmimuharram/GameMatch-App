package com.rizafahmi0093.gamematch.data

import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.model.Game

object GameRepository {

    private val gameList = listOf(

        Game(
            1,
            "Apex Legends",
            "Action",
            "Competitive",
            listOf("PC", "Console"),
            4.5,
            listOf("Multiplayer"),
            "Battle royale FPS",
            R.drawable.apex
        ),
        Game(
            2,
            "Dead by Daylight",
            "Horror",
            "Tegang",
            listOf("PC", "Console"),
            4.3,
            listOf("Multiplayer"),
            "Asymmetrical horror multiplayer",
            R.drawable.dbd
        ),
        Game(
            3,
            "Efootball",
            "Sport",
            "Casual",
            listOf("Mobile", "PC", "Console"),
            4.0,
            listOf("Multiplayer"),
            "Football simulation game",
            R.drawable.efootball
        ),
        Game(
            4,
            "Elden Ring",
            "RPG",
            "Serius",
            listOf("PC", "Console"),
            4.8,
            listOf("Single-player"),
            "Open-world dark fantasy RPG",
            R.drawable.elden
        ),
        Game(
            5,
            "Five Nights at Freddy's",
            "Horror",
            "Tegang",
            listOf("PC", "Console", "Mobile"),
            4.2,
            listOf("Single-player"),
            "Survival horror game",
            R.drawable.fnaff
        ),
        Game(
            6,
            "Fortnite",
            "Action",
            "Competitive",
            listOf("All Platforms"),
            4.4,
            listOf("Multiplayer"),
            "Battle royale shooter",
            R.drawable.fortnite
        ),
        Game(
            7,
            "Hello Neighbor",
            "Horror",
            "Tegang",
            listOf("PC", "Console", "Mobile"),
            4.1,
            listOf("Single-player"),
            "Stealth horror puzzle",
            R.drawable.hello
        ),
        Game(
            8,
            "Into The Pit",
            "Horror",
            "Serius",
            listOf("PC", "Console"),
            4.0,
            listOf("Single-player"),
            "Dark horror exploration",
            R.drawable.intothepit
        ),
        Game(
            9,
            "Little Nightmares",
            "Horror",
            "Serius",
            listOf("PC", "Console"),
            4.5,
            listOf("Single-player"),
            "Puzzle horror adventure",
            R.drawable.little
        ),
        Game(
            10,
            "Minecraft",
            "Simulation",
            "Santai",
            listOf("All Platforms"),
            4.9,
            listOf("Multiplayer"),
            "Sandbox creative game",
            R.drawable.minecraft
        ),
        Game(
            11,
            "PUBG",
            "Action",
            "Competitive",
            listOf("PC", "Console", "Mobile"),
            4.3,
            listOf("Multiplayer"),
            "Realistic battle royale",
            R.drawable.pubg
        ),
        Game(
            12,
            "Stardew Valley",
            "Simulation",
            "Santai",
            listOf("PC", "Console", "Mobile"),
            4.8,
            listOf("Single-player"),
            "Farming relaxing game",
            R.drawable.stardey
        ),
        Game(
            13,
            "Valorant",
            "Action",
            "Competitive",
            listOf("PC"),
            4.6,
            listOf("Multiplayer"),
            "Tactical FPS shooter",
            R.drawable.valorant
        ),
        Game(
            14,
            "The Witcher 3",
            "RPG",
            "Serius",
            listOf("PC", "Console"),
            4.9,
            listOf("Single-player"),
            "Story-driven RPG",
            R.drawable.witcher
        ),
        Game(
            15,
            "Black Myth Wukong",
            "RPG",
            "Serius",
            listOf("PC", "PS5"),
            4.7,
            listOf("Single-player"),
            "Action RPG mythological",
            R.drawable.wukong
        ),
        Game(
            16,
            "Ghost of Yotei",
            "RPG",
            "Serius",
            listOf("PS5"),
            4.4,
            listOf("Single-player"),
            "Samurai adventure RPG",
            R.drawable.yotei
        ),
        Game(
            17,
            "It Takes Two",
            "RPG",
            "Casual",
            listOf("PC", "Console"),
            4.5,
            listOf("Multiplayer (Co-op)"),
            "Coop adventure game",
            R.drawable.takestwo
        ),
        Game(
            18,
            "God of War",
            "Action",
            "Serius",
            listOf("PC", "PlayStation"),
            4.8,
            listOf("Single-player"),
            "Story action game",
            R.drawable.godofwar
        ),
        Game(
            19,
            "Spider-Man",
            "Action",
            "Casual",
            listOf("PC", "PlayStation"),
            4.7,
            listOf("Single-player"),
            "Open world superhero",
            R.drawable.spiderman
        ),
        Game(
            20,
            "Resident Evil",
            "Horror",
            "Tegang",
            listOf("PC", "Console"),
            4.6,
            listOf("Single-player"),
            "Survival horror",
            R.drawable.resident
        ),
        Game(
            21,
            "FIFA 24",
            "Sports",
            "Casual",
            listOf("PC", "Console"),
            4.3,
            listOf("Multiplayer"),
            "Football game",
            R.drawable.fifa
        ),
        Game(
            22,
            "Overcooked",
            "Simulation",
            "Casual",
            listOf("PC", "Console"),
            4.4,
            listOf("Multiplayer (Co-op)"),
            "Chaos cooking coop",
            R.drawable.overcooked
        ),
        Game(
            23,
            "Phasmophobia",
            "Horror",
            "Tegang",
            listOf("PC"),
            4.5,
            listOf("Multiplayer"),
            "Ghost hunting coop",
            R.drawable.phasmophobia
        ),
        Game(
            24,
            "Cyberpunk 2077",
            "RPG",
            "Serius",
            listOf("PC", "Console"),
            4.2,
            listOf("Single-player"),
            "Futuristic RPG",
            R.drawable.cyberpunk
        ),
        Game(
            25,
            "Detroit Become Human",
            "RPG",
            "Serius",
            listOf("PC", "PlayStation"),
            4.7,
            listOf("Single-player"),
            "Story driven game",
            R.drawable.detroit
        ),
        Game(
            26,
            "Hades",
            "RPG",
            "Competitive",
            listOf("PC", "Console"),
            4.6,
            listOf("Single-player"),
            "Roguelike action",
            R.drawable.hades
        )
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


        val normalizedMode = when (mode) {
            "Coop" -> listOf("Multiplayer (Co-op)")
            "Multi" -> listOf("Multiplayer", "Multiplayer (Co-op)")
            "Single" -> listOf("Single-player")
            else -> listOf(mode)
        }


        val filtered = gameList.filter { game ->

            val genreMatch = game.genre.equals(genre, true)
            val moodMatch = game.mood.equals(mood, true)
            val ratingMatch = game.rating >= minRating

            val platformMatch =
                "All Platforms" in game.platforms ||
                        game.platforms.any { it.equals(platform, true) }

            val modeMatch =
                game.modes.any { it in normalizedMode }

            genreMatch && moodMatch && platformMatch && modeMatch && ratingMatch
        }

        if (filtered.isNotEmpty()) {
            return filtered.sortedByDescending { it.rating }
        }


        val fallback1 = gameList.filter { game ->
            game.genre.equals(genre, true) &&
                    game.mood.equals(mood, true) &&
                    ("All Platforms" in game.platforms || platform in game.platforms) &&
                    game.rating >= minRating
        }

        if (fallback1.isNotEmpty()) {
            return fallback1.sortedByDescending { it.rating }
        }


        val fallback2 = gameList.filter { game ->
            game.genre.equals(genre, true) &&
                    game.rating >= minRating
        }

        if (fallback2.isNotEmpty()) {
            return fallback2.sortedByDescending { it.rating }
        }


        return gameList
            .sortedByDescending { it.rating }
            .take(5)
    }
}