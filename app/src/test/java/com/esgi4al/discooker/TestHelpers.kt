package com.esgi4al.discooker

import com.esgi4al.discooker.models.Category
import com.esgi4al.discooker.models.Comment
import com.esgi4al.discooker.models.Ingredient
import com.esgi4al.discooker.models.Instruction
import com.esgi4al.discooker.models.Recipe
import com.esgi4al.discooker.models.Region
import com.esgi4al.discooker.models.User

class TestHelpers {

    companion object {
        fun getMockRecipe(): Recipe {
            val mockUser = User(_id = "1", username = "John Doe", email = "john@example.com")
            val mockCategory = Category(name = "Dessert", imgUrl = "category.png")
            val mockRegion = Region(name = "France", imgUrl = "region.png")
            val mockIngredients = listOf(Ingredient(name = "Sugar", quantity = "100g", _id = "1"))
            val mockInstructions = listOf(Instruction(step = 1, instruction = "Mix everything", _id = "1"))
            val mockComments = listOf(Comment(user = mockUser, content = "Great recipe!"))

            val mockRecipe = Recipe(
                _id = "1",
                user = mockUser,
                title = "Chocolate Cake",
                description = "A delicious chocolate cake",
                thumbnail = "cake.png",
                ingredients = mockIngredients,
                instructions = mockInstructions,
                category = mockCategory,
                region = mockRegion,
                likes = listOf("2", "3"),
                comments = mockComments,
                createdAt = "2024-03-01",
                updatedAt = "2024-03-02"
            )

            return mockRecipe
        }
    }
}