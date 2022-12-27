package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.luck.picture.lib.PictureSelectionModel
import com.example.myapplication.luck.picture.lib.PictureSelector
import com.example.myapplication.luck.picture.lib.config.PictureConfig
import com.example.myapplication.luck.picture.lib.config.PictureMimeType
import com.example.myapplication.luck.picture.lib.entity.LocalMedia
import com.example.myapplication.luck.picture.lib.listener.OnResultCallbackListener
import com.luck.picture.lib.R
import java.util.*

class MainActivity : AppCompatActivity() {
//    var btnOnclick:TextView = findViewById(R.id.btnClick)
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val model: PictureSelectionModel = PictureSelector.create(this)
        .openGallery(PictureMimeType.ofAll())
    model.filterMaxFileSize(6000000)
    Utils.setLanguage(model, "vi")
    setPhotoSelectOpt(model, 10, 100.0)
    model.isGif(false)
    model.videoMaxSecond(120)
    resolveMedias(model)
//        startActivity(Intent(this,PictureSelectorActivity::class.java))

    }

    fun setPhotoSelectOpt(
        model: PictureSelectionModel,
        count: Int,
        quality: Double
    ): PictureSelectionModel? {
        model
            .imageEngine(GlideEngine.createGlideEngine())
            .maxSelectNum(count)
            .minSelectNum(1)
            .maxVideoSelectNum(count)
            .minVideoSelectNum(1)
            .selectionMode(if (count > 1) PictureConfig.MULTIPLE else PictureConfig.SINGLE)
            .isSingleDirectReturn(false)
            .isWeChatStyle(true)
            .isCamera(false)
            .isZoomAnim(true)
            .isGif(true)
            .isEnableCrop(false)
            .isCompress(false)
            .minimumCompressSize(100)
            .isReturnEmpty(false)
            .isAndroidQTransform(true)
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .isOriginalImageControl(false)
            .isMaxSelectEnabledMask(true)
            .setCameraImageFormat(PictureMimeType.JPEG)
            .setCameraVideoFormat(PictureMimeType.MP4)
            .renameCompressFile("image_picker_compress_" + UUID.randomUUID().toString() + ".jpg")
            .renameCropFileName(
                "image_picker_crop_" + UUID.randomUUID().toString() + ".jpg"
            ) //                .cameraFileName("image_picker_camera_"+UUID.randomUUID().toString()+".jpg")
        if (quality > 0) {
            model.isCompress(true).compressQuality((quality * 100).toInt())
        }
        return model
    }
    private fun resolveMedias(model: PictureSelectionModel) {
        model.forResult(object : OnResultCallbackListener<LocalMedia?> {
            override fun onResult(medias: List<LocalMedia?>) {
                // 结果回调
                object : Thread() {
                    override fun run() {
                        Log.e("TAG", "run: SIZE:"+ medias.size.toString())
                      /*  val resArr: MutableList<Any> = ArrayList()
                        for (media in medias) {
                            val map = HashMap<String, Any>()
                            var path: String = media.getPath()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                path = media.getAndroidQToPath()
                            }

//              if (media.isCut()) path = media.getCutPath();
//              if (media.isCompressed()) path = media.getCompressPath();
                            if (media.getMimeType().contains("image")) {
                                if (media.isCut()) path = media.getCutPath()
                                if (media.isCompressed()) path = media.getCompressPath()
                            }
                            //              path = copyToTmp(path);
                            map["path"] = path
                            var thumbPath: String
                            if (media.getMimeType().contains("image")) {
                                thumbPath = path
                            } else {
                                thumbPath = createVideoThumb(path)
                            }
                            map["thumbPath"] = thumbPath
                            val size: Int = getFileSize(path)
                            map["size"] = size
                            Log.i("pick test", map.toString())
                            resArr.add(map)
                        }

//            PictureFileUtils.deleteCacheDirFile(context, type);
//            PictureFileUtils.deleteAllCacheDirFile(context);
                        Handler(context.getMainLooper()).post(Runnable { _result.success(resArr) })*/
                    }
                }.start()
            }

        override    fun onCancel() {
                // 取消
//                _result.success(null)
            }
        })
    }

}