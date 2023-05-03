package com.example.messageme.Activities.DataClass

import android.net.Uri
import de.hdodenhof.circleimageview.CircleImageView

data class UserDetails(
    var userId: String? = null,
    var fullName: String? = null,
    var email: String? = null,
    var pass: String? = null,
) {

}