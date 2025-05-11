package com.example.stablewaifusionalpha.data.repository

import android.content.Context
import com.example.stablewaifusionalpha.R
import com.example.stablewaifusionalpha.core.constants.BASIC_REALISM_MODEL_PATH
import com.example.stablewaifusionalpha.core.constants.MEDIUM_ANIME_MODEL_PATH
import com.example.stablewaifusionalpha.core.constants.MEDIUM_REALISM_2_MODEL_PATH
import com.example.stablewaifusionalpha.core.constants.MEDIUM_REALISM_MODEL_PATH
import com.example.stablewaifusionalpha.domain.model.remote.ImageFormat
import com.example.stablewaifusionalpha.domain.model.remote.Style
import com.example.stablewaifusionalpha.domain.model.remote.Quality
import com.example.stablewaifusionalpha.domain.model.remote.ResolutionLevel
import com.example.stablewaifusionalpha.domain.model.remote.StyleParam
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

    private val standardStyles = listOf(
        Style(
            name = "Realism",
            preview = R.drawable.medium_realism,
            workflowPath = "api_test.json",
            styleParams = listOf(
                StyleParam("Model Loader", "ckpt_name", MEDIUM_REALISM_MODEL_PATH),
                StyleParam("Negative Prompt", "text", "(worst quality, low quality, illustration, 3d, 2d, painting, cartoons, sketch), open mouth"),
                StyleParam("KSampler", "sampler_name", "dpmpp_sde"),
                StyleParam("KSampler", "scheduler", "karras"),
                StyleParam("KSampler", "steps", 30),
                StyleParam("Eta", "eta", 31337),
            ),
        ),
        Style(
            name ="Realism 2",
            preview = R.drawable.onboarding_ai,
            workflowPath = "api_test.json",
            styleParams = listOf(
                StyleParam("Model Loader", "ckpt_name", MEDIUM_REALISM_2_MODEL_PATH),
                StyleParam("Negative Prompt", "text", "score_4, score_5, score_6"),
                StyleParam("KSampler", "cfg", 6.2f),
                StyleParam("KSampler", "sampler_name", "dpm_2a"),
                StyleParam("Clip Skip", "clip skip", 2),
            ),
        ),
        Style(
            name = "Anime",
            preview = R.drawable.medium_anime,
            workflowPath = "api_test.json",
            styleParams = listOf(
                StyleParam("Model Loader", "ckpt_name", MEDIUM_ANIME_MODEL_PATH),
                StyleParam("Negative Prompt", "text", "lowres,(worst quality, bad quality:1.1),bad anatomy,sketch,jpeg artifacts,signature,watermark,logo, bad hands, mutated hands, missing fingers, extra finger, bad body, messy, deformed, deformed hand"),
                StyleParam("KSampler", "cfg", 5.3f),
                StyleParam("KSampler", "sampler_name", "dpmpp_2m"),
                StyleParam("KSampler", "scheduler", "karras"),
            ),
            stylePrompt = "masterpiece, best quality, amazing quality, very aesthetic, absurdres,  newest",
        ),
    )

    private val basicStyles = listOf(
        Style(
            name ="Realism",
            preview = R.drawable.basic_realism,
            workflowPath = "api_test.json",
            styleParams = listOf(
                StyleParam("KSampler", "sampler_name", "dpmpp_sde"),
                StyleParam("KSampler", "scheduler", "karras"),
                StyleParam("KSampler", "cfg", 6.5f),
                StyleParam("Clip Skip", "clip_skip",2),
                StyleParam("Model Loader", "ckpt_name", BASIC_REALISM_MODEL_PATH),
            )
        ),
    )

    private val advancedResolutions = listOf(
        ResolutionLevel("Low",800),
        ResolutionLevel("Low",912),
        ResolutionLevel("Low",1088),
    )

    private val standardResolutions = listOf(
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
        Quality("Standart", standardStyles, standardResolutions, formats)
        ,/*Quality("Advanced", advancedStyles)*/
    )


    override fun getQualities(): List<Quality> {
        return qualities

    }

}