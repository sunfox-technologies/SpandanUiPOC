package com.example.spandanuipoc

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import `in`.sunfox.healthcare.commons.android.spandan_sdk.SpandanSDK
import `in`.sunfox.healthcare.commons.android.spandan_sdk.enums.EcgTestType
import `in`.sunfox.healthcare.commons.android.spandan_sdk.listener.PDFReportGenerationCallback
import `in`.sunfox.healthcare.commons.android.spandan_sdk.retrofit_helper.PatientData
import `in`.sunfox.healthcare.commons.android.spandan_sdk.retrofit_helper.ReportGenerationResult
import `in`.sunfox.healthcare.commons.android.spandan_sdk_ui.helper.SpandanSDKUI
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.lead_II_test).setOnClickListener {
            findViewById<TextView>(R.id.error_msg).text = ""
            try {
                SpandanSDKUI.createTestAndStartWithUI(
                    activity = application,
                    spandanSDK = SpandanSDK.getInstance(),
                    ecgTestType = EcgTestType.LEAD_TWO,
                    patientData = PatientData(
                        age = "24",
                        firstName = "first",
                        lastName = "name",
                        height = "234",
                        gender = "male",
                        weight = "34"
                    ),
                    pdfReportGenerationCallback = object : PDFReportGenerationCallback{
                        override fun onReportGenerationFailed(errorMsg: String) {
                            findViewById<TextView>(R.id.error_msg).text = errorMsg
                        }

                        override fun onReportGenerationSuccess(reportGenerationResult: ReportGenerationResult) {

                            runOnUiThread{
                                findViewById<TextView>(R.id.result).text = reportGenerationResult.pdfReportUrl
                            }

                        }

                    }
                )    
            }catch (e:Exception){
                Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.twelve_lead).setOnClickListener {
            findViewById<TextView>(R.id.error_msg).text = ""
            SpandanSDKUI.createTestAndStartWithUI(
                activity = application,
                spandanSDK = SpandanSDK.getInstance(),
                ecgTestType = EcgTestType.TWELVE_LEAD,
                patientData = PatientData(
                    age = "24",
                    firstName = "first",
                    lastName = "name",
                    height = "234",
                    gender = "male",
                    weight = "34"
                ),
                pdfReportGenerationCallback = object : PDFReportGenerationCallback{
                    override fun onReportGenerationFailed(errorMsg: String) {
                        runOnUiThread {
                            findViewById<TextView>(R.id.error_msg).text = errorMsg
                        }
                    }

                    override fun onReportGenerationSuccess(reportGenerationResult: ReportGenerationResult) {

                        runOnUiThread{
                            findViewById<TextView>(R.id.result).text = reportGenerationResult.pdfReportUrl
                        }

                    }

                }
            )
        }
    }
}