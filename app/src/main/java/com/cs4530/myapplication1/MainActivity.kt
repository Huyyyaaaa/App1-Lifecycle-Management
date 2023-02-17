package com.cs4530.myapplication1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), RegisterFragment.DataPassingInterface {

    private var stringFName: String? = null
    private var stringMName: String? = null
    private var stringLName: String? = null
    private var stringImageFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fTrans = supportFragmentManager.beginTransaction()
            fTrans.replace(R.id.reg_frag, RegisterFragment(), "frag_reg")
            fTrans.commit()
        }
    }

    override fun passData(data: Map<String, String?>?) {
        stringFName = data!!["fName"]
        stringMName = data!!["mName"]
        stringLName = data!!["lName"]
        stringImageFilePath = data!!["imageFilePath"]

        val detailsFragment = DetailsFragment()
        detailsFragment.arguments = bundleUserData()

        val fTrans = supportFragmentManager.beginTransaction()
        fTrans.replace(R.id.reg_frag, detailsFragment, "frag_details")
        fTrans.commit()

    }

    private fun bundleUserData(): Bundle {
        val userData = Bundle()
        userData.putString("fNAME_DATA", stringFName)
        userData.putString("mNAME_DATA", stringMName)
        userData.putString("lNAME_DATA", stringLName)
        userData.putString("IMAGE_FILEPATH", stringImageFilePath)
        return userData
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("fNAME_DATA", stringFName)
        outState.putString("mNAME_DATA", stringMName)
        outState.putString("lNAME_DATA", stringLName)
        outState.putString("IMAGE_FILEPATH", stringImageFilePath)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        stringFName = savedInstanceState.getString("fNAME_DATA")
        stringMName = savedInstanceState.getString("mNAME_DATA")
        stringLName = savedInstanceState.getString("lNAME_DATA")
        stringImageFilePath = savedInstanceState.getString("IMAGE_FILEPATH")
    }
}