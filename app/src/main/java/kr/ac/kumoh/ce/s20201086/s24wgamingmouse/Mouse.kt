package kr.ac.kumoh.ce.s20201086.s24wgamingmouse

// 마우스 테이블
data class Mouse(
    val id: Int,
    val title: String,
    val price: Int,
    val detail: String?,
    val image_url: String,
    val purchase_url: String
)

// 사이트 테이블
data class Site(
    val id: Int,
    val title: String,
    val site_url: String,
    val background_color: String,
    val text_color: String
)
