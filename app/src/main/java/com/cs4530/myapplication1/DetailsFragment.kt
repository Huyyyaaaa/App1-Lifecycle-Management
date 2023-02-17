package com.cs4530.myapplication1

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var fNameData: TextView? = null
    private var mNameData: TextView? = null
    private var lNameData: TextView? = null
    private var ivThumbnail: ImageView? = null

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        this.fNameData = view.findViewById(R.id.fNameData)
        this.mNameData = view.findViewById(R.id.mNameData)
        this.lNameData = view.findViewById(R.id.lNameData)
        this.ivThumbnail = view.findViewById(R.id.profile_pic)

        val incomingBundle = arguments
        val first = incomingBundle!!.getString("fNAME_DATA")
        val middle = incomingBundle!!.getString("mNAME_DATA")
        val last = incomingBundle!!.getString("lNAME_DATA")
        val imageFilePath = incomingBundle!!.getString("IMAGE_FILEPATH")

        if(first != null)
            this.fNameData!!.text = first
        if(middle != null)
            this.mNameData!!.text = middle
        if(last != null)
            this.lNameData!!.text = last
        if(imageFilePath != null) {
            val thumbnailImage = BitmapFactory.decodeFile(imageFilePath)
            if(thumbnailImage != null)
                ivThumbnail!!.setImageBitmap(thumbnailImage)
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
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}