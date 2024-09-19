package com.sandclan.common.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIText {

    data class RemoteString(val message: String) : UIText()

    class LocalString(@StringRes val res: Int, vararg val args: Any) : UIText()

    data object Idle : UIText()

    fun getString(context: Context): String {
        return when (this) {
            is RemoteString -> {
                message
            }

            is LocalString -> {
                context.getString(res, *args)
            }

            Idle -> ""
        }
    }


    @Composable
    fun getString(): String {
        return when (this) {
            is RemoteString -> {
                message
            }

            is LocalString -> {
                stringResource(res, *args)
            }

            Idle -> ""
        }
    }


}