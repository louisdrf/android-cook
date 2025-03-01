package com.esgi4al.discooker.createRecipePageTests

import com.esgi4al.discooker.TestHelpers
import com.esgi4al.discooker.models.*
import com.esgi4al.discooker.repositories.CreateRecipeGlobalDataRepository
import com.esgi4al.discooker.service.ApiClient
import com.esgi4al.discooker.service.GlobalDataService
import com.esgi4al.discooker.service.RecipeService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@RunWith(MockitoJUnitRunner::class)
class CreateRecipeGlobalDataRepositoryTest {

    @Mock
    lateinit var globalDataServiceMock: GlobalDataService

    @Mock
    lateinit var recipeServiceMock: RecipeService

    @InjectMocks
    lateinit var createRecipeGlobalDataRepository: CreateRecipeGlobalDataRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        createRecipeGlobalDataRepository = CreateRecipeGlobalDataRepository(globalDataServiceMock, recipeServiceMock)
    }

    @Test
    fun `postRecipe should return Recipe when successful`() = runBlocking {
        val mockRecipe = TestHelpers.getMockRecipe()
        val mockRecipeRequest = getMockRecipeRequest(mockRecipe)

        `when`(recipeServiceMock.createRecipe(mockRecipeRequest)).thenReturn(mockRecipe)
        val result = createRecipeGlobalDataRepository.postRecipe(mockRecipeRequest)
        assertEquals(mockRecipe, result)
    }

    @Test
    fun `postRecipe should return null when HttpException is thrown`() = runBlocking {
        val mockRecipe = TestHelpers.getMockRecipe()
        val mockRecipeRequest = getMockRecipeRequest(mockRecipe)

        val mockResponse = Mockito.mock(retrofit2.Response::class.java) as retrofit2.Response<*>
        val httpException = HttpException(mockResponse)
        `when`(recipeServiceMock.createRecipe(mockRecipeRequest)).thenThrow(httpException)
        val result = createRecipeGlobalDataRepository.postRecipe(mockRecipeRequest)
        assertEquals(null, result)
    }

    @Test
    fun `getIngredientsBySearch should return list of ingredients when successful`() = runBlocking {
        val mockIngredient = Ingredient(name = "Sugar", quantity = "100g", _id = "1")

        `when`(globalDataServiceMock.getIngredientsBySearch("Sugar")).thenReturn(listOf(mockIngredient))

        val result = createRecipeGlobalDataRepository.getIngredientsBySearch("Sugar")
        assertEquals(listOf(mockIngredient), result)
    }

    @Test
    fun `getIngredientsBySearch should return null when HttpException is thrown`() = runBlocking {
        val mockResponse = Mockito.mock(retrofit2.Response::class.java) as retrofit2.Response<*>
        val httpException = HttpException(mockResponse)
        `when`(globalDataServiceMock.getIngredientsBySearch("Sugar")).thenThrow(httpException)

        val result = createRecipeGlobalDataRepository.getIngredientsBySearch("Sugar")
        assertEquals(null, result)
    }

    private fun getMockRecipeRequest(mockRecipe: Recipe): ApiRequestPostRecipe {
        val mockRecipeRequest = ApiRequestPostRecipe(
            mockRecipe.title,
            mockRecipe.description,
            mockRecipe.ingredients,
            mockRecipe.instructions,
            mockRecipe.category,
            mockRecipe.region)

        return mockRecipeRequest
    }
}