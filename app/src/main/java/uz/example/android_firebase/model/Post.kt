package uz.example.android_firebase.model

class Post {
    var id:String? = null
    var title:String? = null
    var body:String? = null
    var image:String? = null

    constructor()
    constructor(title: String?, body: String?) {
        this.title = title
        this.body = body
    }

    constructor(id: String, title: String?, body: String?, image: String?=null) {
        this.id = id
        this.title = title
        this.body = body
    }

}
