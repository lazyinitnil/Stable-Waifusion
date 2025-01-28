package com.example.stablewaifusionalpha.data.repository

import android.content.Context
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.core.BASIC_REALISM_MODEL_PATH
import com.example.stablewaifusionalpha.core.MEDIUM_ANIME_MODEL_PATH
import com.example.stablewaifusionalpha.core.MEDIUM_REALISM_2_MODEL_PATH
import com.example.stablewaifusionalpha.core.MEDIUM_REALISM_MODEL_PATH
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.repository.QualitiesRepository
import javax.inject.Inject

class QualitiesRepositoryImpl @Inject constructor(
    private val context: Context
): QualitiesRepository {

 /*   private val basicUniversalSubStyles = listOf(
        SubStyle()
    )

    private val basicRealismSubStyle = listOf(
        SubStyle("","",R.drawable)
    )

    private val basicAnimeSubStyles = listOf(
        SubStyle("","",R.drawable)
    )

    private val mediumAnimeSubStyles = listOf(
        SubStyle("","", R.drawable)
    )

    private val advancedStyles = listOf(

    )*/

    private val mediumStyles = listOf(
        Style(
            name = "Realism",
            modelPath = MEDIUM_REALISM_MODEL_PATH,
            preview = R.drawable.medium_realism,
            styleNegativePrompt = "(worst quality, low quality, illustration, 3d, 2d, painting, cartoons, sketch), open mouth",
            styleSampler = "DPM++ SDE",
            styleScheduler = "Karras",
            styleSteps = 30,
            styleEta = 31337
        ),
        Style(
            name ="Realism 2",
            modelPath = MEDIUM_REALISM_2_MODEL_PATH,
            R.drawable.onboarding_ai ,
            stylePrompt = "score_9, score_8_up, score_7_up, BREAK",
            styleNegativePrompt = "score_4, score_5, score_6",
            styleSampler = "DPM2 a",
            styleCfg = 6.2f,
            styleClipSkip = 2,
        ),
        Style(
            "Anime",
            MEDIUM_ANIME_MODEL_PATH,
            R.drawable.medium_anime,
            stylePrompt = "masterpiece, best quality, amazing quality, very aesthetic, absurdres,  newest",
            styleNegativePrompt = "lowres,(worst quality, bad quality:1.1),bad anatomy,sketch,jpeg artifacts,signature,watermark,logo, bad hands, mutated hands, missing fingers, extra finger, bad body, messy, deformed, deformed hand",
            styleCfg = 5.3f,
            styleSampler = "DPM++ 2M",
            styleScheduler = "Karras",
        ),
    )

    private val basicStyles = listOf(
        Style(
            name ="Realism",
            modelPath = BASIC_REALISM_MODEL_PATH,
            preview = R.drawable.basic_realism,
            styleCfg = 6.5f,
            styleSampler = "DPM++ SDE",
            styleScheduler = "Karras",
            styleClipSkip = 2
        ),
    )

    private val advancedResolutions = listOf(
        ResolutionLevel("Low",800),
        ResolutionLevel("Low",912),
        ResolutionLevel("Low",1088),
    )

    private val mediumResolutions = listOf(
        ResolutionLevel("Low",768),
        ResolutionLevel("Medium",832),
        ResolutionLevel("High",1024)
    )

    private val basicResolutions = listOf(
        ResolutionLevel("Low",560),
        ResolutionLevel("Medium",768),
        ResolutionLevel("High",912)
    )

    private val formats = listOf(
        ImageFormat("1:1",1,1),
        ImageFormat("3:4",3,4),
        ImageFormat("4:3",4,3),
        ImageFormat("16:9",16,9),
    )

    private val qualities = listOf(
        Quality("Basic", basicStyles, basicResolutions, formats)
        ,
        Quality("Medium", mediumStyles, mediumResolutions, formats)
        ,/*Quality("Advanced", advancedStyles)*/
    )


    override fun getQualities(): List<Quality> {
        return qualities

    }

}