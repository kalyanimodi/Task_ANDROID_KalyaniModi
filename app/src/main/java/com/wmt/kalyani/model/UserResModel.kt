package com.wmt.kalyani.model

import java.util.ArrayList

class UserResModel {
    var meta: MataModel? = null
    var data: DataModel? = null

    inner class MataModel {
        var status: String? = null
        var message: String? = null

    }

    inner class DataModel {
        var users: ArrayList<DataList>? = null

    }

    inner class DataList {
        var id: String? = null
        var profile_pic: String? = null
        var username: String? = null
        var email: String? = null
        var created_at: String? = null
        var updated_at: String? = null

    }
}