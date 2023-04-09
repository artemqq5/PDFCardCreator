package com.crecardbissnes.ebissnescard.domain

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.crecardbissnes.ebissnescard.R
import com.crecardbissnes.ebissnescard.domain.models.BusinessCardModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PDFManager(private val context: Context) {

    suspend fun createPDF(name: String, modelCard: BusinessCardModel, end: (String) -> Unit) {
        val doc = PdfDocument()

        val pageConsist = PdfDocument.PageInfo.Builder(200, 100, 1).create()

        val path =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
        val dir = File(path)
        val file = File(dir, name)


        val title = Paint()

        val myPage = doc.startPage(pageConsist)

        val canvas: Canvas = myPage.canvas

        title.typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        title.textSize = 14f
        title.color = ContextCompat.getColor(context, R.color.black)
        canvas.drawText("${modelCard.name}, ${modelCard.lastName}", 20f, 30f, title)
        title.textSize = 9f
        canvas.drawText(modelCard.number, 20f, 45f, title)
        title.textSize = 10f
        canvas.drawText(modelCard.workPosition, 20f, 75f, title)

        doc.finishPage(myPage)

        try {

            withContext(Dispatchers.IO) {
                doc.writeTo(FileOutputStream(file))
            }

        } catch (e: IOException) {
            Log.i("myLog create", e.toString())
        } finally {
            doc.close()
            end(name)
        }

    }

    fun openPDF(name: String) {
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            name
        )
        val excelPath: Uri =
            FileProvider.getUriForFile(
                context,
                "com.crecardbissnes.ebissnescard.provider",
                file
            )

        val pdfIntent = Intent(Intent.ACTION_VIEW)
        pdfIntent.setDataAndType(excelPath, "application/pdf")

        pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        pdfIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(pdfIntent)
        } catch (e: ActivityNotFoundException) {
            Log.i("myLog result", e.toString())
        }
    }


}
