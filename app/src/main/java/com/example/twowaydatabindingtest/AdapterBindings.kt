package com.example.twowaydatabindingtest

import android.util.Log
import android.widget.CompoundButton
import android.widget.ToggleButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener


object AdapterBindings {

    @BindingAdapter("bind:switchOnCheckedChanged")
    @JvmStatic
    fun setChecked(switch: SwitchCompat, checked: Boolean){
        switch?.let {
            Log.d("test@123", "BindingAdapter cheched : ${checked}")
        }
    }

    @BindingAdapter("onCheckedChangedAttrChanged")
    @JvmStatic
    fun setChecked(switch: SwitchCompat, listener: InverseBindingListener) {
        switch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                listener.onChange()
            }
        })
    }

    @InverseBindingAdapter(attribute = "bind:switchOnCheckedChanged", event = "onCheckedChangedAttrChanged")
    @JvmStatic
    fun getChecked(switch: SwitchCompat) : Boolean{
        Log.d("test@123", "InverseBindingAdapter cheched : ${switch.isChecked}")
        return switch.isChecked
    }
}