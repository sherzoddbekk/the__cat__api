package com.example.thecatapi.activity

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.thecatapi.R
import com.example.thecatapi.retrofit.RetrofitHttp
import com.example.thecatapi.utils.Logger
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class DetailsActivity : AppCompatActivity() {
    lateinit var iv_from_gallery_come_pic: ImageView
    lateinit var come_in_gallery: ImageView
    private val pickImage = 100
    private var imageUri: Uri? = null
    lateinit var back_from_details: ImageView
    lateinit var btn_upload: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initView()

    }

    private fun initView() {
        title = "KotlinApp"
        btn_upload = findViewById(R.id.send_url)
        back_from_details = findViewById(R.id.back_from_details)
        iv_from_gallery_come_pic = findViewById(R.id.iv_from_gallery_come_pic)
        come_in_gallery = findViewById(R.id.come_in_gallery)


        come_in_gallery.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        back_from_details.setOnClickListener {
            finish()
        }

        btn_upload.setOnClickListener {
            if (imageUri != null) {
                apiUploadimage(imageUri!!)
            } else {
                hideLoading(R.string.txt_uploading_fail)
                btn_upload.setTextColor(Color.RED)
                Toast.makeText(
                    this@DetailsActivity,
                    "Upload the cat Image in Cat_API which You selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    //uchqunbek


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            iv_from_gallery_come_pic.setImageURI(imageUri)
        }
    }

    private fun apiUploadimage(uri: Uri) {
        showLoading()
        val file = getFile(uri)
        val reqFile = RequestBody.create(MediaType.parse("image/jpg"), file)
        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)

        RetrofitHttp.photoService.uploadSpecificImage(body, file.name)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        hideLoading(R.string.txt_uploaded)
                        btn_upload.setTextColor(Color.GREEN)

                        Toast.makeText(
                            this@DetailsActivity,
                            "Upload the cat Image in Cat_API which You selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        hideLoading(R.string.txt_uploading_fail)
                        btn_upload.setTextColor(Color.RED)

                        Logger.e("@@@Upload", response.body().toString())
                        Toast.makeText(
                            this@DetailsActivity,
                            "Upload the cat Image in Cat_API which You selected",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Logger.d("@@@Upload", response.code().toString())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    hideLoading(R.string.txt_uploading_fail)
                    btn_upload.setTextColor(Color.RED)

                    Logger.e("@@@Upload", t.message.toString())
                    Toast.makeText(
                        this@DetailsActivity,
                        "Upload the cat Image in Cat_API which You selected",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })
    }

    private fun getFile(uri: Uri): File {
        val ins = this.contentResolver.openInputStream(uri)
        val file = File.createTempFile(
            "file",
            "jpg",
            this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val fileOutputStream = FileOutputStream(file)
        ins?.copyTo((fileOutputStream))
        ins?.close()
        fileOutputStream.close()
        return file
    }


    private fun showLoading() {
        btn_upload.text = ""

    }

    private fun hideLoading(text: Int) {
        btn_upload.setText(text)
    }

}