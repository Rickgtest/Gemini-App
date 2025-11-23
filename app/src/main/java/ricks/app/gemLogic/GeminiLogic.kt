package ricks.app.gemLogic


import com.google.genai.Client
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GenerateContentResponse
import com.google.genai.types.GoogleSearch
import com.google.genai.types.Tool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


var api = "Paste key here"
var modelText = "gemini-2.5-flash"
object GeminiLogic {

    private val geminiClient: Client
        get() = Client.builder().apiKey(api).build()

    private val googleSearchTool: Tool = Tool.builder()
        .googleSearch(GoogleSearch.builder().build())
        .build()
    private val config: GenerateContentConfig = GenerateContentConfig.builder()
        .tools(listOf(googleSearchTool))
        .build()
    fun updateSettings(newApiKey: String, newModelName: String) {
        api = newApiKey
        modelText = newModelName
    }

    suspend fun generateWithGoogleSearch(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {

                    val response: GenerateContentResponse = geminiClient.models.generateContent(
                        modelText,           // Positional: model (String)
                        prompt,     // Positional: contents (String)
                        config               // Positional: config (GenerateContentConfig)
                    )
                    response.text() ?: "No response generated."
                } catch (e: Exception) {
                    "Error: ${e.message}"
                }
            }
        }
    }

