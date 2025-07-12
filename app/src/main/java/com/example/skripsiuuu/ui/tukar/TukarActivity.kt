package com.example.skripsiuuu.ui.tukar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.skripsiuuu.databinding.ActivityTukarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class TukarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTukarBinding
    private val botToken = "8077638548:AAEk0Uo5yA3Tly_gGOQbxHjNGQ7ymsDce4c"
    private val chatId = "1335332571"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTukarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pulsaSepuluh.setOnClickListener(){
            val amount = "10000"
            showAlertDialogForPulsa(amount)
        }
        binding.pulsaDuaLima.setOnClickListener(){
            val amount = "25000"
            showAlertDialogForPulsa(amount)
        }
        binding.pulsaLimaPuluh.setOnClickListener(){
            val amount = "50000"
            showAlertDialogForPulsa(amount)
        }
        binding.pulsaSeratus.setOnClickListener(){
            val amount = "100000"
            showAlertDialogForPulsa(amount)
        }

        binding.gopayLimaPuluh.setOnClickListener(){
            val amount = "50000"
            showAlertDialogForGopay(amount)
        }
        binding.gopaySeratus.setOnClickListener(){
            val amount = "100000"
            showAlertDialogForGopay(amount)
        }

        binding.steamEmpatLima.setOnClickListener(){
            val amount = "45000"
            showAlertDialogForSteam(amount)
        }
        binding.steamSeratusDuaPuluh.setOnClickListener(){
            val amount = "120000"
            showAlertDialogForSteam(amount)
        }

        binding.berasCV.setOnClickListener(){
            showAlertDialog()
        }
        binding.minyakCV.setOnClickListener(){
            showAlertDialog()
        }
        binding.gulaCV.setOnClickListener(){
            showAlertDialog()
        }
        binding.telurCV.setOnClickListener(){
            showAlertDialog()
        }

        binding.uanglimapuluh.setOnClickListener(){
            showAlertDialog()
        }
        binding.uangseratus.setOnClickListener(){
            showAlertDialog()
        }
        binding.uangDuaRatusLimaPuluh.setOnClickListener(){
            showAlertDialog()
        }
        binding.uangLimaRatus.setOnClickListener(){
            showAlertDialog()
        }

    }

    private fun showAlertDialog() {
        val dialog = AlertDialog.Builder(this)
            .setMessage("Silahkan datang ke lokasi Bank Sampah!!!")
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun showAlertDialogForPulsa(amount:String){
        val tipe = "pulsa"
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.hint = "Masukkan disini!!!"

        val dialog = AlertDialog.Builder(this)
            .setMessage("Masukkan nomor yang akan diisi pulsa!!! ")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val phoneNumber = input.text.toString()
                if (phoneNumber.isNotEmpty()) {
                    sendToTele(amount, tipe, phoneNumber)
//                    Toast.makeText(this, "Nomor telepon: $phoneNumber", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Jangan kosong kek hatimu!!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Kembali") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun showAlertDialogForGopay(amount: String){
        val tipe = "gopay"
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.hint = "Masukkan disini!!!"

        val dialog = AlertDialog.Builder(this)
            .setMessage("Masukkan nomor yang akan diisi Gopay!!! ")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val phoneNumber = input.text.toString()
                if (phoneNumber.isNotEmpty()) {
                    sendToTele(amount, tipe, phoneNumber)
//                    Toast.makeText(this, "Nomor telepon: $phoneNumber", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Jangan kosong kek hatimu!!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Kembali") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun showAlertDialogForSteam(amount: String){
        val tipe = "steam"
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_PHONE
        input.hint = "Masukkan disini!!!"

        val dialog = AlertDialog.Builder(this)
            .setMessage("Masukkan nomor WhatsApp untuk kirim reedem code!!! ")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val phoneNumber = input.text.toString()
                if (phoneNumber.isNotEmpty()) {
                    sendToTele(amount, tipe, phoneNumber)
//                    Toast.makeText(this, "Nomor telepon: $phoneNumber", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Jangan kosong kek hatimu!!!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .setNegativeButton("Kembali") { dialog, _ ->
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun sendToTele(amount: String, tipe: String, phoneNumber: String){
        CoroutineScope(Dispatchers.IO).launch {
            val message = "Tipe = $tipe\nJumlah = $amount\nNomor = $phoneNumber"
            try {
                val urlString = "https://api.telegram.org/bot$botToken/sendMessage"
                val url = URL(urlString)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                conn.doOutput = true

                val jsonPayload = """
                    {
                        "chat_id": "$chatId",
                        "text": "$message"
                    }
                """.trimIndent()

                val outputStream = OutputStreamWriter(conn.outputStream)
                outputStream.write(jsonPayload)
                outputStream.flush()
                outputStream.close()

                val responseCode = conn.responseCode
                withContext(Dispatchers.Main) {
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(this@TukarActivity, "Tunggu yaa, nanti juga masuk!!!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@TukarActivity, "Sorry ulangi lagi yaa!! Tenang aja poinnya ga berkurang kok", Toast.LENGTH_SHORT).show()
                    }
                }
                conn.disconnect()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@TukarActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}