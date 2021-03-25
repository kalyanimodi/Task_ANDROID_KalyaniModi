package com.wmt.kalyani.model

class RegisterResModel {
    var meta: MetaModel? = null
    var data: DataModel? = null

    inner class MetaModel {
        var status: String? = null
        var message: String? = null
    }

    inner class DataModel {
        var user: UserModel? = null
        var token: TokenModel? = null
    }

    inner class TokenModel {
        var type: String? = null
        var token: String? = null
        var refreshtoken: String? = null
    }

    inner class UserModel {
        var id: String? = null
        var profile_pic: String? = null
        var username: String? = null
        var email: String? = null
        var created_at: String? = null
        var updated_at: String? = null
    }

}