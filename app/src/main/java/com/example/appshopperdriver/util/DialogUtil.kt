package com.example.appshopperdriver.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.appshopperdriver.R

object DialogUtil {

    fun showConfirmationDialog(context: Context, title: String, message: String, positiveButton: String, negativeButton: String, positiveAction: () -> Unit, negativeAction: () -> Unit) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton(positiveButton) { _, _ -> positiveAction.invoke() }
        dialog.setNegativeButton(negativeButton) { _, _ -> negativeAction.invoke() }
        dialog.show()
    }

    fun showInformationDialog(context: Context, title: String, message: String, positiveButton: String, positiveAction: () -> Unit) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setPositiveButton(positiveButton) { _, _ -> positiveAction.invoke() }
        dialog.show()
    }

    fun showErrorDialog(context: Context, message: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_error, null)
        val builder = AlertDialog.Builder(context).setView(dialogView)
        val textView = dialogView.findViewById<TextView>(R.id.text_dialog_error_message)
        textView.text = message
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }


    fun showSuccessDialog(context: Context, message: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sucess, null)
        val builder = AlertDialog.Builder(context).setView(dialogView)
        val textView = dialogView.findViewById<TextView>(R.id.text_dialog_sucess_message)
        textView.text = message
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    fun showLoadingDialog(context: Context, message: String): AlertDialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        val builder = AlertDialog.Builder(context).setView(dialogView)
        val textView = dialogView.findViewById<TextView>(R.id.text_dialog_loading)
        val imageView = dialogView.findViewById<ImageView>(R.id.image_dialog_loading)
        textView.text = message

        val animacao = AnimationUtils.loadAnimation(
            context,
            R.anim.sync_rotate
        )
        imageView.visibility = View.VISIBLE
        imageView.startAnimation(animacao)

        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)  
        dialog.setCancelable(false) 

        dialog.show()

        return dialog
    }







}