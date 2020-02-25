package com.unisys.news.news.repo.dto

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.unisys.news.news.repo.local.DBConstant

/**
 * Created by Patricio Iván Garcia <patriciogarcia1988@gmail.com> on 02/24/2020.
 */
@Entity(tableName = DBConstant.NEWS_TABLE_NAME)
class Article() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DBConstant.NEWS_ID)
    var id: Int = 0

    @SerializedName("author")
    @ColumnInfo(name = DBConstant.NEWS_AUTHOR)
    var author: String? = ""

    @SerializedName("title")
    @ColumnInfo(name = DBConstant.NEWS_TITLE)
    var title: String? = ""

    @SerializedName("urlToImage")
    @ColumnInfo(name = DBConstant.NEWS_URL_IMAGE)
    var urlToImage: String? = ""

    @SerializedName("description")
    @ColumnInfo(name = DBConstant.NEWS_DESCRIPTION)
    var description: String? = ""

    @SerializedName("content")
    @ColumnInfo(name = DBConstant.NEWS_CONTENT)
    var content: String? = ""

    @SerializedName("publishedAt")
    @ColumnInfo(name = DBConstant.NEWS_PUBLISHED_AT)
    var publishedAt: String? = ""

    @SerializedName("url")
    @ColumnInfo(name = DBConstant.NEWS_URL)
    var url: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        author = parcel.readString()
        title = parcel.readString()
        urlToImage = parcel.readString()
        description = parcel.readString()
        content = parcel.readString()
        publishedAt = parcel.readString()
        url = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(urlToImage)
        parcel.writeString(description)
        parcel.writeString(content)
        parcel.writeString(publishedAt)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }

}
