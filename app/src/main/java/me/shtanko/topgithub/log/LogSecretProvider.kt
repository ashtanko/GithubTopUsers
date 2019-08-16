package me.shtanko.topgithub.log

import android.content.Context
import android.os.Build
import androidx.annotation.NonNull
import java.security.SecureRandom


object LogSecretProvider {

//    fun getOrCreateAttachmentSecret(@NonNull context: Context): ByteArray {
//
//    }

    private fun createAndStoreSecret(@NonNull context: Context): ByteArray {
        val random = SecureRandom()
        val secret = ByteArray(32)
        random.nextBytes(secret)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //val encryptedSecret = KeyStoreHelper.seal(secret)
        } else {

        }


        return secret
    }


}