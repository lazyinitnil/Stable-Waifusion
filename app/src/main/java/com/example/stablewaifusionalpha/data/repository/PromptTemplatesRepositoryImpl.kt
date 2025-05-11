package com.example.stablewaifusionalpha.data.repository

import android.content.Context
import com.example.stablewaifusionalpha.domain.model.remote.PromptTemplate
import com.example.stablewaifusionalpha.domain.repository.PromptTemplatesRepository
import javax.inject.Inject

class PromptTemplatesRepositoryImpl @Inject constructor(
    private val context: Context
): PromptTemplatesRepository {

    private val qualityPrompts = listOf(
        "masterpiece", "best quality", "high resolution",
        "ultra detailed", "4k", "8k"
    )

    private val stylePrompts = listOf(
        "minimalism", "photorealism", "hyperrealism",
        "psychedelic", "surrealism", "landscape", "portraits",
        "manga", "anime", "graphic novell", "comic book",
        "mosaic", "isometric", "digital painting",
        "character design", "cityscape",
    )

    private val subjectPrompts = listOf(
        ""
    )

    private val colorPrompts = listOf(
        "flat color", "gradient", "vivid", "olive", "colorful",
        "glitter", "sepia colors", "deep colors", "rich colors"
    )

    private val artistPrompts = listOf(
        "Picasso", "Gustav Klimt", "Rembrandt", "Claude Monet",
        "Van Gogh", "Da Vinci", "Edvard Munch", "Hokusai",
        "Francisco Goya", "Michelangelo",
    )

    private val lightningPrompts = listOf(
        "cinematic lightning", "dynamic lightning", "dramatic lightning",
        "sunlight", "natural lightning", "volumetric light", "studio light"
    )

    private val moodPrompts = listOf(
        "mysterious", "emotional", "dreamy", "peaceful", "playful",
        "enigmatic", "joyful", "dramatic", "nostalgic", "lyrical"
    )

    private val promptTemplates = listOf(
        PromptTemplate("Quality", qualityPrompts),
        PromptTemplate("Style", stylePrompts),
        PromptTemplate("Subject", subjectPrompts),
        PromptTemplate("Color", colorPrompts),
        PromptTemplate("Artist", artistPrompts),
        PromptTemplate("Composition", emptyList()),
        PromptTemplate("Lightning", lightningPrompts),
        PromptTemplate("Mood", moodPrompts),
    )

    override fun getPromptTemplates(): List<PromptTemplate> {
        return promptTemplates
    }

}