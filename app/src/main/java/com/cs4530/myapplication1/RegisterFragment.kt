package com.cs4530.myapplication1

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.lang.ClassCastException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var fName: EditText? = null
    private var mName: EditText? = null
    private var lName: EditText? = null

    private var buttonSubmit: Button? = null
    private var buttonCamera: Button? = null

    private var ivThumbnail: ImageView? = null
    private var thumbnailImage: Bitmap? = null
    private var imageFilePath: String? = null

    var dataPasser: DataPassingInterface? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    interface DataPassingInterface {
        fun passData(data: Map<String, String?>?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = try {
            context as DataPassingInterface
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SubmitFragment.DataPassingInterface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        this.fName = view.findViewById(R.id.editFName)
        this.mName = view.findViewById(R.id.editMName)
        this.lName = view.findViewById(R.id.editLName)

        this.buttonSubmit = view.findViewById(R.id.button_submit) as Button
        this.buttonSubmit!!.setOnClickListener(this)

        this.buttonCamera = view.findViewById(R.id.button_pic) as Button
        this.buttonCamera!!.setOnClickListener(this)

        this.ivThumbnail = view.findViewById(R.id.preview_pic) as ImageView

        val incomingBundle = arguments
        if(incomingBundle != null) {
            this.fName!!.setText(incomingBundle.getString("fNAME_DATA"))
            this.mName!!.setText(incomingBundle.getString("mNAME_DATA"))
            this.lName!!.setText(incomingBundle.getString("lNAME_DATA"))
            this.imageFilePath = incomingBundle.getString("IMAGE_FILEPATH")
            if(imageFilePath != null) {
                thumbnailImage = BitmapFactory.decodeFile(imageFilePath)
                if(thumbnailImage != null) {
                    ivThumbnail!!.setImageBitmap(thumbnailImage)
                }
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.button_submit -> {
                val fName = fName!!.text.toString()
                val mName = mName!!.text.toString()
                val lName = lName!!.text.toString()

                if(fName.isNullOrBlank())
                    Toast.makeText(activity, "Please enter your first name.", Toast.LENGTH_SHORT).show()
                else if (lName.isNullOrBlank())
                    Toast.makeText(activity, "Please enter your last name.", Toast.LENGTH_SHORT).show()
                else if (imageFilePath.isNullOrBlank())
                    Toast.makeText(activity, "Please take a picture before submitting!", Toast.LENGTH_SHORT).show()
                else {
                    val data: Map<String, String?> = mapOf(
                        "fName" to fName,
                        "mName" to mName,
                        "lName" to lName,
                        "imageFilePath" to imageFilePath
                    )
                    Toast.makeText(activity, "Thank you for filling out your information!", Toast.LENGTH_SHORT).show()
                    // pass data
                    dataPasser!!.passData(data)
                }
            }
            R.id.button_pic -> {
                val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraLauncher.launch(camIntent)
                }
                catch (ex: ActivityNotFoundException) {
                    Toast.makeText(activity, "There was a problem launching the camera. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val extras = result.data!!.extras
            thumbnailImage = extras!!["data"] as Bitmap?

            // open a file and write to it
            if (isExternalStorageWritable) {
                imageFilePath = saveImage(thumbnailImage)
                if (thumbnailImage != null) {
                    ivThumbnail!!.setImageBitmap(thumbnailImage)
                }
            } else {
                Toast.makeText(activity, "External storage not writable", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImage(finalBitmap: Bitmap?): String {
        val root = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val filename = "Thumbnail_$timeStamp.jpg"
        val file = File(myDir, filename)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            Toast.makeText(activity, "File saved!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

    private val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save path to photo
        outState.putString("IMAGE_FILEPATH", imageFilePath)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        // restore photo if one was taken
        if (savedInstanceState != null) {
            imageFilePath = savedInstanceState!!.getString("IMAGE_FILEPATH")
            if (imageFilePath != null) {
                thumbnailImage = BitmapFactory.decodeFile(imageFilePath)
                if (thumbnailImage != null) {
                    ivThumbnail!!.setImageBitmap(thumbnailImage)
                }
            }
        }
    }
}