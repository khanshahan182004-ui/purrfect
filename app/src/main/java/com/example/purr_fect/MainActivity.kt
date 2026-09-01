package com.example.purr_fect
import android.content.Intent
import android.content.Context
import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.roundToInt
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.UUID
/* =========================================================
BACKEND CONNECTION
For Android Studio emulator use 10.0.2.2 to reach the PC
where the Node.js backend is running on port 5000.
========================================================= */
private const val PURR_FECT_API_BASE_URL = "http://127.0.0.1:5000"
data class BackendUser(
    val id: Int,
    val name: String,
    val email: String
)

/* =========================================================
LOGIN SESSION STORAGE
Keeps the local login session when the app is closed/reopened.
Android normally clears app data after an uninstall.
========================================================= */
private const val PURR_FECT_SESSION_PREFS = "purrfect_session"
private const val SESSION_LOGGED_IN = "logged_in"
private const val SESSION_USER_ID = "user_id"
private const val SESSION_USER_NAME = "user_name"
private const val SESSION_USER_EMAIL = "user_email"

private fun loadSavedUser(context: android.content.Context): BackendUser? {
    val prefs = context.getSharedPreferences(
        PURR_FECT_SESSION_PREFS,
        android.content.Context.MODE_PRIVATE
    )
    if (!prefs.getBoolean(SESSION_LOGGED_IN, false)) return null

    val userId = prefs.getInt(SESSION_USER_ID, 0)
    val name = prefs.getString(SESSION_USER_NAME, null) ?: return null
    val email = prefs.getString(SESSION_USER_EMAIL, null) ?: return null

    if (userId <= 0) return null

    return BackendUser(
        id = userId,
        name = name,
        email = email
    )
}

private fun saveUserSession(context: android.content.Context, user: BackendUser) {
    context.getSharedPreferences(
        PURR_FECT_SESSION_PREFS,
        android.content.Context.MODE_PRIVATE
    )
        .edit()
        .putBoolean(SESSION_LOGGED_IN, true)
        .putInt(SESSION_USER_ID, user.id)
        .putString(SESSION_USER_NAME, user.name)
        .putString(SESSION_USER_EMAIL, user.email)
        .apply()
}

private fun clearUserSession(context: android.content.Context) {
    context.getSharedPreferences(
        PURR_FECT_SESSION_PREFS,
        android.content.Context.MODE_PRIVATE
    )
        .edit()
        .clear()
        .apply()
}
private object PurrFectApi {
    private suspend fun postJson(
        path: String,
        body: JSONObject
    ): JSONObject = withContext(Dispatchers.IO) {
        val connection =
            (URL(PURR_FECT_API_BASE_URL + path).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                connectTimeout = 8000
                readTimeout = 8000
                doInput = true
                doOutput = true
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
            }
        try {
            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(body.toString())
                writer.flush()
            }
            val stream =
                if (connection.responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
            val responseText =
                BufferedReader(InputStreamReader(stream)).use { reader ->
                    reader.readText()
                }
            val responseJson =
                if (responseText.isBlank()) {
                    JSONObject()
                } else {
                    JSONObject(responseText)
                }
            if (connection.responseCode !in 200..299) {
                throw IllegalStateException(
                    responseJson.optString(
                        "message",
                        "Backend request failed (${connection.responseCode})"
                    )
                )
            }
            responseJson
        } finally {
            connection.disconnect()
        }
    }
    suspend fun register(
        name: String,
        email: String,
        password: String
    ): BackendUser {
        val response = postJson(
            "/api/users",
            JSONObject().apply {
                put("name", name)
                put("email", email)
                put("password", password)
            }
        )
        val user = response.getJSONObject("user")
        return BackendUser(
            id = user.getInt("id"),
            name = user.getString("name"),
            email = user.getString("email")
        )
    }
    suspend fun login(
        email: String,
        password: String
    ): BackendUser {
        val response = postJson(
            "/api/login",
            JSONObject().apply {
                put("email", email)
                put("password", password)
            }
        )
        val user = response.getJSONObject("user")
        return BackendUser(
            id = user.getInt("id"),
            name = user.getString("name"),
            email = user.getString("email")
        )
    }
    suspend fun googleLogin(context: Context): BackendUser {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(
                "1090132929074-d6udtdgg7l0a00344vdjgunn72l7bu0l.apps.googleusercontent.com"
            )
            .setFilterByAuthorizedAccounts(false)
            .setAutoSelectEnabled(false)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val result = credentialManager.getCredential(
            context = context,
            request = request
        )

        val credential = result.credential

        if (
            credential !is CustomCredential ||
            credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            throw IllegalStateException("Google credential was not returned")
        }

        val googleCredential =
            GoogleIdTokenCredential.createFrom(credential.data)

        val idToken = googleCredential.idToken

        if (idToken.isBlank()) {
            throw IllegalStateException("Google ID token is empty")
        }

        val response = postJson(
            "/api/auth/google",
            JSONObject().apply {
                put("idToken", idToken)
            }
        )

        val user = response.getJSONObject("user")

        return BackendUser(
            id = user.getInt("id"),
            name = user.getString("name"),
            email = user.getString("email")
        )
    }
    suspend fun likeCat(
        likerCatId: Int,
        likedCatId: Int
    ): JSONObject {
        if (likerCatId <= 0 || likedCatId <= 0) {
            throw IllegalStateException("Invalid cat ID")
        }
        if (likerCatId == likedCatId) {
            throw IllegalStateException("You cannot like your own cat")
        }
        return postJson(
            "/api/cats/$likerCatId/like",
            JSONObject().apply {
                put("liked_cat_id", likedCatId)
            }
        )
    }

    private suspend fun uploadMultipart(
        path: String,
        fieldName: String,
        fileName: String,
        mimeType: String,
        bytes: ByteArray
    ): JSONObject = withContext(Dispatchers.IO) {
        val boundary = "----PurrFectBoundary${UUID.randomUUID()}"
        val connection =
            (URL(PURR_FECT_API_BASE_URL + path).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                connectTimeout = 15000
                readTimeout = 15000
                doInput = true
                doOutput = true
                setRequestProperty(
                    "Content-Type",
                    "multipart/form-data; boundary=$boundary"
                )
                setRequestProperty("Accept", "application/json")
            }
        try {
            connection.outputStream.use { output ->
                val writer = output.writer(Charsets.UTF_8)
                writer.write("--$boundary\r\n")
                writer.write(
                    "Content-Disposition: form-data; name=\"$fieldName\"; filename=\"$fileName\"\r\n"
                )
                writer.write("Content-Type: $mimeType\r\n\r\n")
                writer.flush()
                output.write(bytes)
                output.flush()
                writer.write("\r\n--$boundary--\r\n")
                writer.flush()
            }
            val responseCode = connection.responseCode
            val stream =
                if (responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
            val responseText =
                BufferedReader(InputStreamReader(stream)).use { reader ->
                    reader.readText()
                }
            val responseJson =
                if (responseText.isBlank()) {
                    JSONObject()
                } else {
                    JSONObject(responseText)
                }
            if (responseCode !in 200..299) {
                throw IllegalStateException(
                    responseJson.optString(
                        "message",
                        "Photo upload failed ($responseCode)"
                    )
                )
            }
            responseJson
        } finally {
            connection.disconnect()
        }
    }
    private fun absolutePhotoUrl(photoUrl: String): String {
        return if (photoUrl.startsWith("http://") || photoUrl.startsWith("https://")) {
            photoUrl
        } else {
            PURR_FECT_API_BASE_URL +
                    if (photoUrl.startsWith("/")) photoUrl else "/$photoUrl"
        }
    }
    private suspend fun downloadBitmap(photoUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val connection =
                (URL(absolutePhotoUrl(photoUrl)).openConnection() as HttpURLConnection).apply {
                    requestMethod = "GET"
                    connectTimeout = 10000
                    readTimeout = 10000
                    doInput = true
                }
            try {
                if (connection.responseCode !in 200..299) {
                    return@withContext null
                }
                connection.inputStream.use { input ->
                    BitmapFactory.decodeStream(input)
                }
            } finally {
                connection.disconnect()
            }
        } catch (_: Exception) {
            null
        }
    }
    suspend fun uploadCatPhoto(
        catId: Int,
        bitmap: Bitmap
    ): String? {
        if (catId <= 0) {
            throw IllegalStateException("Cat ID is missing")
        }
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
        val response = uploadMultipart(
            path = "/api/cats/$catId/photos",
            fieldName = "photo",
            fileName = "cat_${catId}_${System.currentTimeMillis()}.jpg",
            mimeType = "image/jpeg",
            bytes = stream.toByteArray()
        )
        return response
            .optJSONObject("photo")
            ?.optString("photo_url", null)
    }
    private suspend fun deleteJson(path: String): JSONObject = withContext(Dispatchers.IO) {
        val connection =
            (URL(PURR_FECT_API_BASE_URL + path).openConnection() as HttpURLConnection).apply {
                requestMethod = "DELETE"
                connectTimeout = 8000
                readTimeout = 8000
                doInput = true
                setRequestProperty("Accept", "application/json")
            }
        try {
            val stream = if (connection.responseCode in 200..299) connection.inputStream else connection.errorStream
            val responseText = BufferedReader(InputStreamReader(stream)).use { it.readText() }
            val responseJson = if (responseText.isBlank()) JSONObject() else JSONObject(responseText)
            if (connection.responseCode !in 200..299) {
                throw IllegalStateException(responseJson.optString("message", "Backend request failed (${connection.responseCode})"))
            }
            responseJson
        } finally {
            connection.disconnect()
        }
    }
    private suspend fun putJson(
        path: String,
        body: JSONObject
    ): JSONObject = withContext(Dispatchers.IO) {
        val connection =
            (URL(PURR_FECT_API_BASE_URL + path).openConnection() as HttpURLConnection).apply {
                requestMethod = "PUT"
                connectTimeout = 8000
                readTimeout = 8000
                doInput = true
                doOutput = true
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
            }
        try {
            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(body.toString())
                writer.flush()
            }
            val stream =
                if (connection.responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
            val responseText =
                BufferedReader(InputStreamReader(stream)).use { reader ->
                    reader.readText()
                }
            val responseJson =
                if (responseText.isBlank()) {
                    JSONObject()
                } else {
                    JSONObject(responseText)
                }
            if (connection.responseCode !in 200..299) {
                throw IllegalStateException(
                    responseJson.optString(
                        "message",
                        "Backend request failed (${connection.responseCode})"
                    )
                )
            }
            responseJson
        } finally {
            connection.disconnect()
        }
    }
    private suspend fun getJson(
        path: String
    ): JSONObject = withContext(Dispatchers.IO) {
        val connection =
            (URL(PURR_FECT_API_BASE_URL + path).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 8000
                readTimeout = 8000
                doInput = true
                setRequestProperty("Accept", "application/json")
            }
        try {
            val stream =
                if (connection.responseCode in 200..299) {
                    connection.inputStream
                } else {
                    connection.errorStream
                }
            val responseText =
                BufferedReader(InputStreamReader(stream)).use { reader ->
                    reader.readText()
                }
            val responseJson =
                if (responseText.isBlank()) {
                    JSONObject()
                } else {
                    JSONObject(responseText)
                }
            if (connection.responseCode !in 200..299) {
                throw IllegalStateException(
                    responseJson.optString(
                        "message",
                        "Backend request failed (${connection.responseCode})"
                    )
                )
            }
            responseJson
        } finally {
            connection.disconnect()
        }
    }
    private fun cleanCatText(
        value: String?,
        fallback: String
    ): String {
        return if (value.isNullOrBlank() || value.equals("null", ignoreCase = true)) {
            fallback
        } else {
            value
        }
    }
    private fun findPrimaryPhotoUrl(photos: org.json.JSONArray?): String? {
        if (photos == null || photos.length() == 0) return null
        for (index in 0 until photos.length()) {
            val photo = photos.optJSONObject(index) ?: continue
            if (photo.optBoolean("is_primary", false)) {
                return photo.optString("photo_url", null)
            }
        }
        return photos.optJSONObject(0)?.optString("photo_url", null)
    }
    suspend fun getCatByUserId(userId: Int): CatProfile? {
        return try {
            val response = getJson("/api/users/$userId/cat")
            val cat = response.optJSONObject("cat") ?: return null
            val photos = cat.optJSONArray("photos")
            val photoUrl = findPrimaryPhotoUrl(photos)
            val photoBitmap = if (!photoUrl.isNullOrBlank()) {
                downloadBitmap(photoUrl)
            } else {
                null
            }
            CatProfile(
                id = cat.optInt("id", 0),
                name = cleanCatText(cat.optString("name", null), "My Cat"),
                gender = cleanCatText(cat.optString("gender", null), "Male"),
                breed = cleanCatText(cat.optString("breed", null), "Breed not added"),
                age = cleanCatText(cat.optString("age", null), "0"),
                about = cleanCatText(
                    cat.optString("bio", null),
                    "No information added yet."
                ),
                personality = cleanCatText(
                    cat.optString("personality", null),
                    "Not added"
                ),
                activities = cleanCatText(
                    cat.optString("activities", null),
                    "Not added"
                ),
                health = cleanCatText(
                    cat.optString("health", null),
                    "Not added"
                ),
                lookingFor = cleanCatText(
                    cat.optString("looking_for", null),
                    "Not added"
                ),
                photoUrl = photoUrl,
                photoBitmap = photoBitmap
            )
        } catch (error: Exception) {
            null
        }
    }
    suspend fun getDiscoverCats(referenceCatId: Int): List<CatProfile> {
        if (referenceCatId <= 0) return emptyList()
        return try {
            val response = getJson("/api/cats?cat_id=$referenceCatId")
            val cats = response.optJSONArray("cats") ?: return emptyList()
            buildList {
                for (index in 0 until cats.length()) {
                    val cat = cats.optJSONObject(index) ?: continue
                    val photos = cat.optJSONArray("photos")
                    val photoUrl = findPrimaryPhotoUrl(photos)
                    val photoBitmap = if (!photoUrl.isNullOrBlank()) {
                        downloadBitmap(photoUrl)
                    } else {
                        null
                    }
                    add(
                        CatProfile(
                            id = cat.optInt("id", 0),
                            name = cleanCatText(cat.optString("name", null), "Cat"),
                            gender = cleanCatText(cat.optString("gender", null), "Not added"),
                            breed = cleanCatText(cat.optString("breed", null), "Breed not added"),
                            age = cleanCatText(cat.optString("age", null), "0"),
                            about = cleanCatText(
                                cat.optString("bio", null),
                                "No information added yet."
                            ),
                            personality = cleanCatText(
                                cat.optString("personality", null),
                                "Not added"
                            ),
                            activities = cleanCatText(
                                cat.optString("activities", null),
                                "Not added"
                            ),
                            health = cleanCatText(
                                cat.optString("health", null),
                                "Not added"
                            ),
                            lookingFor = cleanCatText(
                                cat.optString("looking_for", null),
                                "Not added"
                            ),
                            photoUrl = photoUrl,
                            photoBitmap = photoBitmap
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
    suspend fun getCatById(catId: Int): CatProfile? {
        if (catId <= 0) return null
        return try {
            val response = getJson("/api/cats/$catId")
            val cat = response.optJSONObject("cat") ?: return null
            val photos = cat.optJSONArray("photos")
            val photoUrl = findPrimaryPhotoUrl(photos)
            val photoBitmap = if (!photoUrl.isNullOrBlank()) {
                downloadBitmap(photoUrl)
            } else {
                null
            }
            CatProfile(
                id = cat.optInt("id", catId),
                name = cleanCatText(cat.optString("name", null), "Cat"),
                gender = cleanCatText(cat.optString("gender", null), "Not added"),
                breed = cleanCatText(cat.optString("breed", null), "Breed not added"),
                age = cleanCatText(cat.optString("age", null), "0"),
                about = cleanCatText(cat.optString("bio", null), "No information added yet."),
                personality = cleanCatText(cat.optString("personality", null), "Not added"),
                activities = cleanCatText(cat.optString("activities", null), "Not added"),
                health = cleanCatText(cat.optString("health", null), "Not added"),
                lookingFor = cleanCatText(cat.optString("looking_for", null), "Not added"),
                photoUrl = photoUrl,
                photoBitmap = photoBitmap
            )
        } catch (_: Exception) {
            null
        }
    }
    suspend fun getMatches(catId: Int): List<MatchItem> {
        if (catId <= 0) return emptyList()
        return try {
            val response = getJson("/api/cats/$catId/matches")
            val matches = response.optJSONArray("matches") ?: return emptyList()
            buildList {
                for (index in 0 until matches.length()) {
                    val match = matches.optJSONObject(index) ?: continue
                    val matchedCatId = match.optInt("matched_cat_id", 0)
                    if (matchedCatId <= 0) continue
                    val matchedCat = getCatById(matchedCatId)
                    add(
                        MatchItem(
                            name = cleanCatText(
                                match.optString("name", null),
                                matchedCat?.name ?: "Cat"
                            ),
                            gender = cleanCatText(
                                match.optString("gender", null),
                                matchedCat?.gender ?: "Not added"
                            ),
                            age = cleanCatText(
                                match.optString("age", null),
                                matchedCat?.age ?: "0"
                            ),
                            breed = cleanCatText(
                                match.optString("breed", null),
                                matchedCat?.breed ?: "Breed not added"
                            ),
                            distance = "Nearby",
                            about = cleanCatText(
                                match.optString("bio", null),
                                matchedCat?.about ?: "No information added yet."
                            ),
                            imageRes = R.drawable.signcat,
                            online = false,
                            isNewMatch = true,
                            likedYou = true,
                            photoBitmap = matchedCat?.photoBitmap
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
    suspend fun starCat(catId: Int, starredCatId: Int): JSONObject {
        return postJson(
            "/api/cats/$catId/star",
            JSONObject().apply { put("starred_cat_id", starredCatId) }
        )
    }

    suspend fun unstarCat(catId: Int, starredCatId: Int): JSONObject {
        return deleteJson("/api/cats/$catId/star/$starredCatId")
    }

    suspend fun getStarredCats(catId: Int): List<CatProfile> {
        if (catId <= 0) return emptyList()
        val response = getJson("/api/cats/$catId/starred")
        val array = response.optJSONArray("starred_cats") ?: return emptyList()
        return buildList {
            for (i in 0 until array.length()) {
                val item = array.optJSONObject(i) ?: continue
                val photos = item.optJSONArray("photos")
                var photoUrl: String? = null
                if (photos != null && photos.length() > 0) {
                    for (j in 0 until photos.length()) {
                        val photo = photos.optJSONObject(j) ?: continue
                        val candidate = photo.optString("photo_url", "")
                        if (candidate.isNotBlank()) {
                            photoUrl = candidate
                            if (photo.optBoolean("is_primary", false)) break
                        }
                    }
                }
                val bitmap = photoUrl?.let { downloadBitmap(it) }
                add(
                    CatProfile(
                        id = item.optInt("id", 0),
                        name = item.optString("name", "Cat"),
                        gender = item.optString("gender", "Not added"),
                        breed = item.optString("breed", "Breed not added"),
                        age = item.optString("age", "0"),
                        about = item.optString("bio", "No information added yet."),
                        personality = item.optString("personality", "Not added"),
                        activities = item.optString("activities", "Not added"),
                        health = item.optString("health", "Not added"),
                        lookingFor = item.optString("looking_for", "Not added"),
                        photoUrl = photoUrl,
                        photoBitmap = bitmap
                    )
                )
            }
        }
    }

    suspend fun createCat(
        userId: Int,
        profile: CatProfile
    ): CatProfile? {
        if (userId <= 0) {
            throw IllegalStateException("User ID is missing")
        }

        val response = postJson(
            "/api/cats",
            JSONObject().apply {
                put("user_id", userId)
                put("name", profile.name)
                put("age", profile.age)
                put("breed", profile.breed)
                put("gender", profile.gender)
                put("bio", profile.about)
                put("personality", profile.personality)
                put("activities", profile.activities)
                put("health", profile.health)
                put("looking_for", profile.lookingFor)
            }
        )

        val cat = response.optJSONObject("cat") ?: return null

        return CatProfile(
            id = cat.optInt("id", 0),
            name = cleanCatText(cat.optString("name", null), profile.name),
            gender = cleanCatText(cat.optString("gender", null), profile.gender),
            breed = cleanCatText(cat.optString("breed", null), profile.breed),
            age = cleanCatText(cat.optString("age", null), profile.age),
            about = cleanCatText(cat.optString("bio", null), profile.about),
            personality = cleanCatText(cat.optString("personality", null), profile.personality),
            activities = cleanCatText(cat.optString("activities", null), profile.activities),
            health = cleanCatText(cat.optString("health", null), profile.health),
            lookingFor = cleanCatText(cat.optString("looking_for", null), profile.lookingFor),
            photoUrl = profile.photoUrl,
            photoBitmap = profile.photoBitmap
        )
    }

    suspend fun updateCat(profile: CatProfile): CatProfile? {
        if (profile.id <= 0) {
            throw IllegalStateException("Cat ID is missing")
        }
        val response = putJson(
            "/api/cats/${profile.id}",
            JSONObject().apply {
                put("name", profile.name)
                put("age", profile.age)
                put("breed", profile.breed)
                put("gender", profile.gender)
                put("bio", profile.about)
                put("personality", profile.personality)
                put("activities", profile.activities)
                put("health", profile.health)
                put("looking_for", profile.lookingFor)
            }
        )
        val cat = response.optJSONObject("cat") ?: return null
        return CatProfile(
            id = cat.optInt("id", profile.id),
            name = cleanCatText(cat.optString("name", null), profile.name),
            gender = cleanCatText(cat.optString("gender", null), profile.gender),
            breed = cleanCatText(cat.optString("breed", null), profile.breed),
            age = cleanCatText(cat.optString("age", null), profile.age),
            about = cleanCatText(cat.optString("bio", null), profile.about),
            personality = cleanCatText(cat.optString("personality", null), profile.personality),
            activities = cleanCatText(cat.optString("activities", null), profile.activities),
            health = cleanCatText(cat.optString("health", null), profile.health),
            lookingFor = cleanCatText(cat.optString("looking_for", null), profile.lookingFor)
        )
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PurrFectApp()
        }
    }
}
/* =========================================================
COLORS
========================================================= */
private val BackgroundColor = Color(0xFFFFF8F4)
private val CardColor = Color(0xFFFFFCFA)
private val Pink = Color(0xFFE95D78)
private val Purple = Color(0xFF8057C7)
private val TextDark = Color(0xFF292329)
private val TextGrey = Color(0xFF756D70)
private val LightPink = Color(0xFFFFE9EC)
private val BorderColor = Color(0xFFEDE4E0)
private val StarYellow = Color(0xFFFFC107)
/* =========================================================
CAT PROFILE DATA
========================================================= */
data class CatProfile(
    val id: Int = 0,
    val name: String = "Milo",
    val gender: String = "Male",
    val breed: String = "Maine Coon",
    val age: String = "3",
    val about: String =
        "Milo is a gentle giant who loves naps,\ntreats and head scratches.",
    val personality: String =
        "Calm, Friendly, Independent",
    val activities: String =
        "Napping, Playing, Eating",
    val health: String =
        "Vaccinated • Healthy",
    val lookingFor: String =
        "Playmate",
    val photoUrl: String? = null,
    val photoBitmap: Bitmap? = null
)
/* =========================================================
CHAT DATA
========================================================= */
data class ChatItem(
    val name: String,
    val message: String,
    val time: String,
    val unread: Int = 0,
    val online: Boolean = false
)
/* =========================================================
MATCH DATA
========================================================= */
data class MatchItem(
    val id: Int = 0,
    val name: String,
    val gender: String,
    val age: String,
    val breed: String,
    val distance: String,
    val about: String,
    val imageRes: Int,
    val online: Boolean = false,
    val isNewMatch: Boolean = false,
    val likedYou: Boolean = false,
    val photoBitmap: Bitmap? = null
)
/* =========================================================
MESSAGE DATA
========================================================= */
data class ChatMessage(
    val text: String,
    val isMine: Boolean,
    val time: String
)
/* =========================================================
MAIN APP
========================================================= */
@Composable
fun PurrFectApp() {
    val context = LocalContext.current
    val savedUser = remember { loadSavedUser(context) }

    var selectedTab by remember {
        mutableIntStateOf(0)
    }
    var currentPage by remember {
        mutableStateOf(if (savedUser != null) "main" else "welcome")
    }
    var isSideMenuOpen by remember {
        mutableStateOf(false)
    }
    var starredCats by remember {
        mutableStateOf<List<CatProfile>>(emptyList())
    }
    var selectedChat by remember {
        mutableStateOf<ChatItem?>(null)
    }
    var selectedMatch by remember {
        mutableStateOf<MatchItem?>(null)
    }
    var catProfile by remember {
        mutableStateOf(CatProfile())
    }
    var loggedInUser by remember {
        mutableStateOf<BackendUser?>(savedUser)
    }
    val scope = rememberCoroutineScope()
// Load the logged-in user's real cat profile from the backend.
// The existing UI stays the same; only the data source becomes real.
    LaunchedEffect(loggedInUser?.id) {
        val userId = loggedInUser?.id ?: return@LaunchedEffect
        val backendCat = PurrFectApi.getCatByUserId(userId)
        if (backendCat != null) {
            catProfile = backendCat
        } else {
            catProfile = CatProfile(
                id = 0,
                name = "",
                gender = "",
                breed = "",
                age = "",
                about = "",
                personality = "",
                activities = "",
                health = "",
                lookingFor = ""
            )
            currentPage = "edit"
            selectedTab = 3
        }
    }
    var matchItems by remember {
        mutableStateOf<List<MatchItem>>(emptyList())
    }
// Load real matches for the logged-in/current cat from PostgreSQL.
    LaunchedEffect(catProfile.id) {
        if (catProfile.id > 0) {
            matchItems = PurrFectApi.getMatches(catProfile.id)
            starredCats = PurrFectApi.getStarredCats(catProfile.id)
        } else {
            starredCats = emptyList()
        }
    }
    when (currentPage) {
        "welcome" -> {
            WelcomeScreen(
                onGetStarted = {
                    currentPage = "main"
                    selectedTab = 0
                },
                onLogin = {
                    currentPage = "login"
                },
                onSignUp = {
                    currentPage = "signup"
                }
            )
        }
        "signup" -> {
            SignupScreen(
                onBack = {
                    currentPage = "welcome"
                },
                onLogin = {
                    currentPage = "login"
                },
                onSignupSuccess = { user ->
                    saveUserSession(context, user)
                    loggedInUser = user
                    catProfile = CatProfile(
                        id = 0,
                        name = "",
                        gender = "",
                        breed = "",
                        age = "",
                        about = "",
                        personality = "",
                        activities = "",
                        health = "",
                        lookingFor = ""
                    )
                    currentPage = "edit"
                    selectedTab = 3
                },
                onGoogleSuccess = { user ->
                    saveUserSession(context, user)
                    loggedInUser = user
                    catProfile = CatProfile()
                    currentPage = "edit"
                    selectedTab = 3
                }
            )
        }
        "login" -> {
            LoginScreen(
                onBack = {
                    currentPage = "welcome"
                },
                onSignUp = {
                    currentPage = "signup"
                },
                onLoginSuccess = { user ->
                    saveUserSession(context, user)
                    loggedInUser = user
                    currentPage = "main"
                    selectedTab = 0
                },
                onGoogleSuccess = { user ->
                    saveUserSession(context, user)
                    loggedInUser = user
                    currentPage = "main"
                    selectedTab = 0
                }
            )
        }
        "main" -> {
            when (selectedTab) {
                0 -> {
                    DiscoverScreen(
                        currentCatId = catProfile.id,
                        onTabSelected = {
                            selectedTab = it
                        },
                        onCatClick = { cat ->
                            selectedMatch = MatchItem(
                                id = cat.id,
                                name = cat.name,
                                gender = cat.gender,
                                age = cat.age,
                                breed = cat.breed,
                                distance = "Nearby",
                                about = cat.about,
                                imageRes = matchItems.firstOrNull()?.imageRes ?: 0,
                                photoBitmap = cat.photoBitmap
                            )
                            currentPage = "catProfile"
                        },
                        onLikeConfirmed = { likedCatId ->
                            val response = PurrFectApi.likeCat(
                                likerCatId = catProfile.id,
                                likedCatId = likedCatId
                            )
                            val message = response.optString("message")
                            Toast.makeText(
                                context,
                                if (message.isNotBlank()) message else "Cat liked successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            if (catProfile.id > 0) {
                                matchItems = PurrFectApi.getMatches(catProfile.id)
                            }
                        },
                        onStarCat = { starredCatId ->
                            val response = PurrFectApi.starCat(
                                catId = catProfile.id,
                                starredCatId = starredCatId
                            )
                            val message = response.optString("message")
                            Toast.makeText(
                                context,
                                if (message.isNotBlank()) message else "Cat starred successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            starredCats = PurrFectApi.getStarredCats(catProfile.id)
                        },
                        onMenuClick = {
                            isSideMenuOpen = true
                        }
                    )
                }
                1 -> {
                    MatchesScreen(
                        matches = matchItems,
                        onTabSelected = {
                            selectedTab = it
                        },
                        onChatClick = { match ->
                            selectedChat = ChatItem(
                                name = match.name,
                                message = "You matched with ${match.name}! 🐱❤️🐱",
                                time = "Now",
                                unread = 0,
                                online = match.online
                            )
                            currentPage = "chat"
                        },
                        onMatchClick = { match ->
                            selectedMatch = match
                            currentPage = "compatibility"
                        }
                    )
                }
                2 -> {
                    ChatsScreen(
                        onTabSelected = {
                            selectedTab = it
                        },
                        onChatClick = { chat ->
                            selectedChat = chat
                            currentPage = "chat"
                        }
                    )
                }
                3 -> {
                    MyCatScreen(
                        profile = catProfile,
                        onEditProfile = {
                            currentPage = "edit"
                        },
                        onTabSelected = {
                            selectedTab = it
                        }
                    )
                }
                else -> {
                    DiscoverScreen(
                        currentCatId = catProfile.id,
                        onTabSelected = {
                            selectedTab = it
                        },
                        onCatClick = { cat ->
                            selectedMatch = MatchItem(
                                id = cat.id,
                                name = cat.name,
                                gender = cat.gender,
                                age = cat.age,
                                breed = cat.breed,
                                distance = "Nearby",
                                about = cat.about,
                                imageRes = matchItems.firstOrNull()?.imageRes ?: 0,
                                photoBitmap = cat.photoBitmap
                            )
                            currentPage = "catProfile"
                        },
                        onLikeConfirmed = { likedCatId ->
                            val response = PurrFectApi.likeCat(
                                likerCatId = catProfile.id,
                                likedCatId = likedCatId
                            )
                            val message = response.optString("message")
                            Toast.makeText(
                                context,
                                if (message.isNotBlank()) message else "Cat liked successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            if (catProfile.id > 0) {
                                matchItems = PurrFectApi.getMatches(catProfile.id)
                            }
                        },
                        onStarCat = { starredCatId ->
                            val response = PurrFectApi.starCat(
                                catId = catProfile.id,
                                starredCatId = starredCatId
                            )
                            val message = response.optString("message")
                            Toast.makeText(
                                context,
                                if (message.isNotBlank()) message else "Cat starred successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            starredCats = PurrFectApi.getStarredCats(catProfile.id)
                        },
                        onMenuClick = {
                            isSideMenuOpen = true
                        }
                    )
                }
            }
        }
        "starred" -> {
            StarredCatsScreen(
                starredCats = starredCats,
                onBack = {
                    currentPage = "main"
                    selectedTab = 0
                },
                onCatClick = { cat ->
                    selectedMatch = MatchItem(
                        id = cat.id,
                        name = cat.name,
                        gender = cat.gender,
                        age = cat.age,
                        breed = cat.breed,
                        distance = "Nearby",
                        about = cat.about,
                        imageRes = 0,
                        photoBitmap = cat.photoBitmap
                    )
                    currentPage = "catProfile"
                },
                onRemoveStar = { starredCatId ->
                    scope.launch {
                        try {
                            PurrFectApi.unstarCat(catProfile.id, starredCatId)
                            starredCats = PurrFectApi.getStarredCats(catProfile.id)
                        } catch (error: Exception) {
                            Toast.makeText(
                                context,
                                "Unstar failed: ${error.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            )
        }
        "catProfile" -> {
            selectedMatch?.let { match ->
                CatProfileScreen(
                    match = match,
                    onBack = {
                        currentPage = "main"
                        selectedTab = 0
                    },
                    onLikeSent = {
// The like animation is handled inside the profile screen.
                    },
                    onLikeConfirmed = { likedCatId ->
                        val response = PurrFectApi.likeCat(
                            likerCatId = catProfile.id,
                            likedCatId = likedCatId
                        )
                        val message = response.optString("message")
                        Toast.makeText(
                            context,
                            if (message.isNotBlank()) message else "Cat liked successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        if (catProfile.id > 0) {
                            matchItems = PurrFectApi.getMatches(catProfile.id)
                        }
                    }
                )
            }
        }
        "compatibility" -> {
            selectedMatch?.let { match ->
                CompatibilityScreen(
                    match = match,
                    onBack = {
                        currentPage = "main"
                        selectedTab = 1
                    }
                )
            }
        }
        "edit" -> {
            EditCatProfileScreen(
                profile = catProfile,
                onBack = {
                    currentPage = "main"
                },
                onSave = { updatedProfile ->
                    scope.launch {
                        try {
                            val wasCreating = updatedProfile.id <= 0
                            val savedProfile =
                                if (wasCreating) {
                                    PurrFectApi.createCat(
                                        userId = loggedInUser?.id ?: 0,
                                        profile = updatedProfile
                                    )
                                } else {
                                    PurrFectApi.updateCat(updatedProfile)
                                }

                            if (savedProfile == null) {
                                throw IllegalStateException(
                                    "Backend did not return the cat profile"
                                )
                            }

                            if (updatedProfile.photoBitmap != null &&
                                updatedProfile.photoBitmap !== catProfile.photoBitmap
                            ) {
                                PurrFectApi.uploadCatPhoto(
                                    catId = savedProfile.id,
                                    bitmap = updatedProfile.photoBitmap
                                )
                            }

// Re-fetch the complete profile so the uploaded image
// and all backend values are immediately reflected.
                            catProfile =
                                PurrFectApi.getCatByUserId(
                                    loggedInUser?.id ?: 0
                                ) ?: savedProfile
                            currentPage = "main"
                            selectedTab = 3
                            Toast.makeText(
                                context,
                                if (wasCreating) {
                                    "Cat profile created successfully"
                                } else {
                                    "Cat profile updated successfully"
                                },
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (error: Exception) {
                            Toast.makeText(
                                context,
                                "Update failed: ${error.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            )
        }
        "chat" -> {
            selectedChat?.let { chat ->
                IndividualChatScreen(
                    chat = chat,
                    onBack = {
                        currentPage = "main"
                        selectedTab = 2
                    }
                )
            }
        }
    }

    if (isSideMenuOpen) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.25f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(285.dp)
                    .background(BackgroundColor)
                    .padding(top = 38.dp, start = 20.dp, end = 18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PurrFect",
                        color = TextDark,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { isSideMenuOpen = false }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Close menu",
                            tint = TextDark
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(17.dp))
                        .background(Color.White)
                        .border(1.dp, BorderColor, RoundedCornerShape(17.dp))
                        .clickable {
                            isSideMenuOpen = false
                            currentPage = "starred"
                        }
                        .padding(horizontal = 15.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = Purple,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "Starred Cats",
                        color = TextDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    if (starredCats.isNotEmpty()) {
                        Text(
                            text = starredCats.size.toString(),
                            color = Pink,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                if (loggedInUser != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(17.dp))
                            .background(Color.White)
                            .border(1.dp, BorderColor, RoundedCornerShape(17.dp))
                            .clickable {
                                clearUserSession(context)
                                loggedInUser = null
                                catProfile = CatProfile(
                                    id = 0,
                                    name = "",
                                    gender = "",
                                    breed = "",
                                    age = "",
                                    about = "",
                                    personality = "",
                                    activities = "",
                                    health = "",
                                    lookingFor = ""
                                )
                                matchItems = emptyList()
                                starredCats = emptyList()
                                selectedMatch = null
                                selectedChat = null
                                isSideMenuOpen = false
                                currentPage = "welcome"
                                selectedTab = 0
                            }
                            .padding(horizontal = 15.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = null,
                            tint = Pink,
                            modifier = Modifier.size(26.dp)
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = "Sign Out",
                            color = TextDark,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                isSideMenuOpen = false
                                currentPage = "signup"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Pink
                            ),
                            shape = RoundedCornerShape(17.dp)
                        ) {
                            Text(
                                text = "Sign Up",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                isSideMenuOpen = false
                                currentPage = "login"
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            ),
                            shape = RoundedCornerShape(17.dp)
                        ) {
                            Text(
                                text = "Sign In",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { isSideMenuOpen = false }
            )
        }
    }
}
/* =========================================================
STARRED CATS SCREEN
========================================================= */
@Composable
fun StarredCatsScreen(
    starredCats: List<CatProfile>,
    onBack: () -> Unit,
    onCatClick: (CatProfile) -> Unit,
    onRemoveStar: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Starred Cats",
                    color = TextDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(48.dp))
        }

        if (starredCats.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = Purple,
                        modifier = Modifier.size(58.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "No starred cats yet",
                        color = TextDark,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Star a cat from Discover and it will appear here.",
                        color = TextGrey,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(17.dp),
                verticalArrangement = Arrangement.spacedBy(13.dp)
            ) {
                items(starredCats, key = { it.id }) { cat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(104.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
                            .clickable { onCatClick(cat) }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .clip(RoundedCornerShape(16.dp))
                        ) {
                            if (cat.photoBitmap != null) {
                                Image(
                                    bitmap = cat.photoBitmap.asImageBitmap(),
                                    contentDescription = "${cat.name} photo",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFFFFF1E7)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Pets,
                                        contentDescription = null,
                                        tint = Pink,
                                        modifier = Modifier.size(34.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(13.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = cat.name,
                                color = TextDark,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${cat.breed} • ${cat.age}",
                                color = TextGrey,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(7.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = StarYellow,
                                    modifier = Modifier.size(17.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Starred",
                                    color = Purple,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        IconButton(onClick = { onRemoveStar(cat.id) }) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Remove star",
                                tint = StarYellow,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
WELCOME SCREEN
========================================================= */
@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onLogin: () -> Unit,
    onSignUp: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
/* =================================================
DECORATIVE CIRCLES
================================================= */
        WelcomeCircle(
            x = screenWidth * 0.29f,
            y = screenHeight * 0.035f
        )
        WelcomeCircle(
            x = screenWidth * 0.77f,
            y = screenHeight * 0.19f
        )
        WelcomeCircle(
            x = screenWidth * 0.28f,
            y = screenHeight * 0.255f
        )
        WelcomeCircle(
            x = screenWidth * 0.79f,
            y = screenHeight * 0.64f
        )
        WelcomeCircle(
            x = screenWidth * 0.37f,
            y = screenHeight * 0.81f
        )
        /* =================================================
YELLOW STARS
================================================= */
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.47f,
            y = screenHeight * 0.085f,
            size = 28
        )
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.62f,
            y = screenHeight * 0.145f,
            size = 26
        )
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.285f,
            y = screenHeight * 0.47f,
            size = 28
        )
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.73f,
            y = screenHeight * 0.79f,
            size = 26
        )
        /* =================================================
OUTLINE STARS
================================================= */
        WelcomeStar(
            text = "🐾",
            x = screenWidth * 0.43f,
            y = screenHeight * 0.018f,
            size = 31,
            color = Color(0xFFFFA9C4)
        )
        WelcomeStar(
            text = "🐾",
            x = screenWidth * 0.69f,
            y = screenHeight * 0.035f,
            size = 31,
            color = Color(0xFFFFA9C4)
        )
        WelcomeStar(
            text = "🐾",
            x = screenWidth * 0.30f,
            y = screenHeight * 0.365f,
            size = 29,
            color = Color(0xFFFFA9C4)
        )
        WelcomeStar(
            text = "🐾",
            x = screenWidth * 0.76f,
            y = screenHeight * 0.39f,
            size = 29,
            color = Color(0xFFFFA9C4)
        )
        WelcomeStar(
            text = "🐾",
            x = screenWidth * 0.255f,
            y = screenHeight * 0.74f,
            size = 29,
            color = Color(0xFFFFA9C4)
        )
        /* =================================================
PAW LOGO
================================================= */
        Icon(
            imageVector = Icons.Outlined.Pets,
            contentDescription = "Purr Match",
            tint = Pink,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.TopCenter)
                .offset(
                    y = screenHeight * 0.165f
                )
        )
        /* =================================================
APP NAME
================================================= */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .offset(
                    y = screenHeight * 0.245f
                ),
            horizontalArrangement =
                Arrangement.Center,
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            Text(
                text = "Purr",
                color = Color(0xFF18142E),
                fontSize = 32.sp,
                fontWeight =
                    FontWeight.ExtraBold
            )
            Text(
                text = " Match",
                color = Pink,
                fontSize = 32.sp,
                fontWeight =
                    FontWeight.ExtraBold
            )
        }
        /* =================================================
TAGLINE
================================================= */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .offset(
                    y = screenHeight * 0.315f
                ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            Text(
                text = "Where cats find",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight =
                    FontWeight.Bold,
                fontStyle =
                    FontStyle.Italic,
                fontFamily =
                    FontFamily.Serif
            )
            Text(
                text = "Their purrfect match",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight =
                    FontWeight.Bold,
                fontStyle =
                    FontStyle.Italic,
                fontFamily =
                    FontFamily.Serif
            )
        }
        /* =================================================
TWO CATS
IMPORTANT:
EACH CAT HAS EXACTLY TWO EARS.
================================================= */
        WelcomeCats(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    screenHeight * 0.28f
                )
                .align(Alignment.TopCenter)
                .offset(
                    y = screenHeight * 0.405f
                )
        )
        /* =================================================
HEART
================================================= */
        Text(
            text = "♥",
            color =
                Color(0xFFFF3F72),
            fontSize =
                34.sp,
            modifier =
                Modifier
                    .align(
                        Alignment.TopCenter
                    )
                    .offset(
                        y = screenHeight * 0.545f
                    )
        )
        /* =================================================
GET STARTED
================================================= */
        Button(
            onClick =
                onGetStarted,
            modifier =
                Modifier
                    .width(
                        screenWidth * 0.50f
                    )
                    .height(52.dp)
                    .align(
                        Alignment.TopCenter
                    )
                    .offset(
                        y = screenHeight * 0.675f
                    ),
            shape =
                RoundedCornerShape(26.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFFFF3F72)
                )
        ) {
            Icon(
                imageVector =
                    Icons.Outlined.Pets,
                contentDescription =
                    null,
                tint =
                    Color.White,
                modifier =
                    Modifier.size(21.dp)
            )
            Spacer(
                modifier =
                    Modifier.width(6.dp)
            )
            Text(
                text = "Get Started",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }
        /* =================================================
SIGN UP
================================================= */
        Row(
            modifier =
                Modifier
                    .align(
                        Alignment.TopCenter
                    )
                    .offset(
                        y = screenHeight * 0.750f
                    )
                    .clickable {
                        onSignUp()
                    },
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            Text(
                text =
                    "New user? ",
                color =
                    Color(0xFF777777),
                fontSize =
                    10.sp
            )
            Text(
                text =
                    "Sign Up",
                color =
                    Pink,
                fontSize =
                    10.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }
        /* =================================================
LOGIN
================================================= */
        Row(
            modifier =
                Modifier
                    .align(
                        Alignment.TopCenter
                    )
                    .offset(
                        y = screenHeight * 0.785f
                    )
                    .clickable {
                        onLogin()
                    },
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            Text(
                text =
                    "Already have an account? ",
                color =
                    Color(0xFF777777),
                fontSize =
                    10.sp
            )
            Text(
                text =
                    "Login",
                color =
                    Pink,
                fontSize =
                    10.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}
/* =========================================================
SIGN UP SCREEN
========================================================= */
@Composable
fun SignupScreen(
    onBack: () -> Unit,
    onLogin: () -> Unit,
    onSignupSuccess: (BackendUser) -> Unit,
    onGoogleSuccess: (BackendUser) -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var confirmPasswordVisible by remember {
        mutableStateOf(false)
    }
    var signupLoading by remember {
        mutableStateOf(false)
    }
    var googleLoading by remember {
        mutableStateOf(false)
    }
    val signupScope = rememberCoroutineScope()
    val signupContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4EFF8))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
                .clip(
                    RoundedCornerShape(38.dp)
                )
                .background(
                    Color(0xFFFFF9F4)
                )
        ) {
            Box(
                modifier = Modifier
                    .size(270.dp)
                    .align(Alignment.TopEnd)
                    .offset(
                        x = 55.dp,
                        y = 125.dp
                    )
                    .clip(
                        RoundedCornerShape(140.dp)
                    )
                    .background(
                        Color(0xFFD9C9F4)
                    )
                    .graphicsLayer {
                        rotationZ = -24f
                    }
            )
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.ArrowBack,
                    contentDescription =
                        "Back",
                    tint = TextDark,
                    modifier =
                        Modifier.size(29.dp)
                )
            }
            Icon(
                imageVector =
                    Icons.Outlined.Pets,
                contentDescription =
                    "PurrFect",
                tint =
                    Color(0xFFB9A3E8),
                modifier = Modifier
                    .size(58.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 28.dp,
                        end = 28.dp,
                        top = 96.dp,
                        bottom = 28.dp
                    )
            ) {
                Text(
                    text =
                        "Join the\nPurr-fect community!",
                    color =
                        Color(0xFF202332),
                    fontSize = 30.sp,
                    lineHeight = 37.sp,
                    fontWeight =
                        FontWeight.Bold
                )
                Spacer(
                    modifier =
                        Modifier.height(25.dp)
                )
                Text(
                    text =
                        "Create your account to find your\npurr-fect match.",
                    color =
                        Color(0xFF4D4C53),
                    fontSize = 16.sp,
                    lineHeight = 25.sp
                )
                Spacer(
                    modifier =
                        Modifier.height(24.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(205.dp)
                ) {
                    Image(
                        painter =
                            painterResource(
                                id =
                                    R.drawable.signcat
                            ),
                        contentDescription =
                            "Cat",
                        modifier = Modifier
                            .size(190.dp)
                            .align(Alignment.TopEnd)
                            .padding(end = 0.dp),
                        contentScale =
                            ContentScale.Fit
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )
                SignupField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    placeholder =
                        "Your Name",
                    icon = {
                        Icon(
                            imageVector =
                                Icons.Outlined.PersonOutline,
                            contentDescription = null,
                            tint = TextDark
                        )
                    }
                )
                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
                SignupField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    placeholder =
                        "Email Address",
                    keyboardType =
                        KeyboardType.Email,
                    icon = {
                        Icon(
                            imageVector =
                                Icons.Outlined.Email,
                            contentDescription = null,
                            tint = TextDark
                        )
                    }
                )
                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
                SignupField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    placeholder =
                        "Password",
                    keyboardType =
                        KeyboardType.Password,
                    visualTransformation =
                        if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    icon = {
                        Icon(
                            imageVector =
                                Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = TextDark
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible =
                                    !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible) {
                                        Icons.Outlined.VisibilityOff
                                    } else {
                                        Icons.Outlined.Visibility
                                    },
                                contentDescription =
                                    "Toggle password",
                                tint = TextDark
                            )
                        }
                    }
                )
                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
                SignupField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                    },
                    placeholder =
                        "Confirm Password",
                    keyboardType =
                        KeyboardType.Password,
                    visualTransformation =
                        if (confirmPasswordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    icon = {
                        Icon(
                            imageVector =
                                Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = TextDark
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                confirmPasswordVisible =
                                    !confirmPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (confirmPasswordVisible) {
                                        Icons.Outlined.VisibilityOff
                                    } else {
                                        Icons.Outlined.Visibility
                                    },
                                contentDescription =
                                    "Toggle confirm password",
                                tint = TextDark
                            )
                        }
                    }
                )
                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )
                Button(
                    onClick = {
                        if (name.isBlank() || email.isBlank() || password.isBlank()) {
                            Toast.makeText(
                                signupContext,
                                "Please fill all required fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (password != confirmPassword) {
                            Toast.makeText(
                                signupContext,
                                "Passwords do not match",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!signupLoading) {
                            signupLoading = true
                            signupScope.launch {
                                try {
                                    val user = PurrFectApi.register(
                                        name = name.trim(),
                                        email = email.trim(),
                                        password = password
                                    )
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            signupContext,
                                            "Account created successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        signupLoading = false
                                        onSignupSuccess(user)
                                    }
                                } catch (error: Exception) {
                                    withContext(Dispatchers.Main) {
                                        signupLoading = false
                                        Toast.makeText(
                                            signupContext,
                                            error.message ?: "Registration failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    shape =
                        RoundedCornerShape(18.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFFE85E78)
                        )
                ) {
                    Text(
                        text =
                            if (signupLoading) "Creating..." else "Sign Up",
                        color =
                            Color.White,
                        fontSize =
                            17.sp,
                        fontWeight =
                            FontWeight.Medium
                    )
                    Spacer(
                        modifier =
                            Modifier.width(10.dp)
                    )
                    Icon(
                        imageVector =
                            Icons.Outlined.Pets,
                        contentDescription =
                            null,
                        tint =
                            Color.White,
                        modifier =
                            Modifier.size(25.dp)
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                Color(0xFFE4DCD8)
                            )
                    )
                    Text(
                        text =
                            " or continue with ",
                        color =
                            Color(0xFF5C5960),
                        fontSize =
                            14.sp
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                Color(0xFFE4DCD8)
                            )
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    SocialButton(
                        label = if (googleLoading) "Signing in..." else "Google",
                        symbol = "G",
                        modifier =
                            Modifier.weight(1f),
                        symbolColor =
                            Color(0xFF4285F4),
                        onClick = {
                            if (!googleLoading) {
                                googleLoading = true
                                signupScope.launch {
                                    try {
                                        val user =
                                            PurrFectApi.googleLogin(signupContext)

                                        withContext(Dispatchers.Main) {
                                            googleLoading = false
                                            Toast.makeText(
                                                signupContext,
                                                "Google account ready",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            onGoogleSuccess(user)
                                        }
                                    } catch (error: Exception) {
                                        withContext(Dispatchers.Main) {
                                            googleLoading = false
                                            Toast.makeText(
                                                signupContext,
                                                error.message ?: "Google sign up failed",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    )
                    SocialButton(
                        label = "Facebook",
                        symbol = "f",
                        modifier =
                            Modifier.weight(1f),
                        symbolColor =
                            Color(0xFF1877F2)
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLogin()
                            },
                    horizontalArrangement =
                        Arrangement.Center,
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Text(
                        text =
                            "Already have an account? ",
                        color =
                            Color(0xFF4D4C53),
                        fontSize =
                            15.sp
                    )
                    Text(
                        text =
                            "Log In",
                        color =
                            Color(0xFFE85E78),
                        fontSize =
                            15.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
/* =========================================================
LOGIN SCREEN
========================================================= */
@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onSignUp: () -> Unit,
    onLoginSuccess: (BackendUser) -> Unit,
    onGoogleSuccess: (BackendUser) -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(false)
    }
    var rememberMe by remember {
        mutableStateOf(true)
    }
    var loginLoading by remember {
        mutableStateOf(false)
    }
    var googleLoading by remember {
        mutableStateOf(false)
    }
    val loginScope = rememberCoroutineScope()
    val loginContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1ECF8))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 14.dp
                )
                .clip(
                    RoundedCornerShape(40.dp)
                )
                .background(
                    Color(0xFFFFFAF6)
                )
        ) {
/* =================================================
BACK BUTTON
================================================= */
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(12.dp)
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.ArrowBack,
                    contentDescription =
                        "Back",
                    tint =
                        TextDark,
                    modifier =
                        Modifier.size(30.dp)
                )
            }
            /* =================================================
TOP PAW
================================================= */
            Icon(
                imageVector =
                    Icons.Outlined.Pets,
                contentDescription =
                    "PurrFect",
                tint =
                    Color(0xFFB9A3E8),
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.TopEnd)
                    .padding(9.dp)
            )
            /* =================================================
LAVENDER CAT BACKGROUND
================================================= */
            Box(
                modifier = Modifier
                    .size(390.dp)
                    .align(Alignment.TopEnd)
                    .offset(
                        x = 145.dp,
                        y = 185.dp
                    )
                    .clip(
                        RoundedCornerShape(210.dp)
                    )
                    .background(
                        Color(0xFFD8C9F4)
                    )
                    .graphicsLayer {
                        rotationZ = -17f
                    }
            )
            /* =================================================
DECORATIVE PAWS / HEARTS
================================================= */
            Text(
                text = "♥",
                color = Color(0xFFF48BA0),
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(
                        x = (-58).dp,
                        y = 103.dp
                    )
            )
            Text(
                text = "♥",
                color = Color(0xFFF8A7B5),
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(
                        x = 110.dp,
                        y = 202.dp
                    )
            )
            Icon(
                imageVector =
                    Icons.Outlined.Pets,
                contentDescription = null,
                tint =
                    Color(0xFFD8C8F4),
                modifier = Modifier
                    .size(58.dp)
                    .align(Alignment.TopStart)
                    .offset(
                        x = 20.dp,
                        y = 62.dp
                    )
            )
            Icon(
                imageVector =
                    Icons.Outlined.Pets,
                contentDescription = null,
                tint =
                    Color(0xFFD5C5F1),
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.BottomEnd)
                    .offset(
                        x = (-18).dp,
                        y = (-12).dp
                    )
            )
            /* =================================================
MAIN CONTENT
================================================= */
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 28.dp,
                        end = 28.dp,
                        top = 150.dp,
                        bottom = 30.dp
                    )
            ) {
                Text(
                    text =
                        "Welcome Back!",
                    color =
                        Color(0xFF202332),
                    fontSize =
                        31.sp,
                    lineHeight =
                        38.sp,
                    fontWeight =
                        FontWeight.Bold
                )
                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )
                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Text(
                        text =
                            "Login to continue your\npurr-fect journey ",
                        color =
                            Color(0xFF56545B),
                        fontSize =
                            17.sp,
                        lineHeight =
                            27.sp
                    )
                    Icon(
                        imageVector =
                            Icons.Outlined.Pets,
                        contentDescription = null,
                        tint =
                            Color(0xFFB7A1E4),
                        modifier =
                            Modifier
                                .size(25.dp)
                                .offset(y = 9.dp)
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(23.dp)
                )
                /* =================================================
CAT IMAGE
================================================= */
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(235.dp)
                ) {
                    Image(
                        painter =
                            painterResource(
                                id = R.drawable.signcat
                            ),
                        contentDescription =
                            "Cat",
                        modifier = Modifier
                            .size(285.dp)
                            .align(Alignment.TopEnd)
                            .offset(
                                x = 25.dp,
                                y = (-22).dp
                            ),
                        contentScale =
                            ContentScale.Fit
                    )
                }
                /* =================================================
EMAIL
================================================= */
                SignupField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    placeholder =
                        "Email Address",
                    keyboardType =
                        KeyboardType.Email,
                    icon = {
                        Icon(
                            imageVector =
                                Icons.Outlined.Email,
                            contentDescription = null,
                            tint =
                                Color(0xFF7560B8)
                        )
                    }
                )
                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
                /* =================================================
PASSWORD
================================================= */
                SignupField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    placeholder =
                        "Password",
                    keyboardType =
                        KeyboardType.Password,
                    visualTransformation =
                        if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    icon = {
                        Icon(
                            imageVector =
                                Icons.Outlined.Lock,
                            contentDescription = null,
                            tint =
                                Color(0xFF7560B8)
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible =
                                    !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector =
                                    if (passwordVisible) {
                                        Icons.Outlined.VisibilityOff
                                    } else {
                                        Icons.Outlined.Visibility
                                    },
                                contentDescription =
                                    "Toggle password",
                                tint =
                                    TextDark
                            )
                        }
                    }
                )
                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )
                /* =================================================
REMEMBER ME / FORGOT PASSWORD
================================================= */
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                rememberMe = !rememberMe
                            },
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(
                                    RoundedCornerShape(5.dp)
                                )
                                .background(
                                    if (rememberMe) {
                                        Color(0xFF9A80D0)
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF9A80D0),
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            contentAlignment =
                                Alignment.Center
                        ) {
                            if (rememberMe) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Remember Me selected",
                                    tint = Color.White,
                                    modifier = Modifier.size(19.dp)
                                )
                            }
                        }
                        Spacer(
                            modifier =
                                Modifier.width(10.dp)
                        )
                        Text(
                            text =
                                "Remember Me",
                            color =
                                Color(0xFF4E4C53),
                            fontSize =
                                16.sp
                        )
                    }
                    Text(
                        text =
                            "Forgot Password?",
                        color =
                            Color(0xFF7359B5),
                        fontSize =
                            15.sp,
                        modifier =
                            Modifier.clickable { }
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(20.dp)
                )
                /* =================================================
LOGIN BUTTON
================================================= */
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(
                                loginContext,
                                "Enter email and password",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!loginLoading) {
                            loginLoading = true
                            loginScope.launch {
                                try {
                                    val user = PurrFectApi.login(
                                        email = email.trim(),
                                        password = password
                                    )
                                    withContext(Dispatchers.Main) {
                                        loginLoading = false
                                        Toast.makeText(
                                            loginContext,
                                            "Login successful",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        onLoginSuccess(user)
                                    }
                                } catch (error: Exception) {
                                    withContext(Dispatchers.Main) {
                                        loginLoading = false
                                        Toast.makeText(
                                            loginContext,
                                            error.message ?: "Login failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp),
                    shape =
                        RoundedCornerShape(20.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Color(0xFFF06B84)
                        )
                ) {
                    Text(
                        text =
                            if (loginLoading) "Logging in..." else "Log In",
                        color =
                            Color.White,
                        fontSize =
                            18.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                    Spacer(
                        modifier =
                            Modifier.width(14.dp)
                    )
                    Icon(
                        imageVector =
                            Icons.Outlined.Pets,
                        contentDescription = null,
                        tint =
                            Color.White,
                        modifier =
                            Modifier.size(28.dp)
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )
                /* =================================================
CONTINUE WITH
================================================= */
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                Color(0xFFE4DCD8)
                            )
                    )
                    Text(
                        text =
                            " or continue with ",
                        color =
                            Color(0xFF5C5960),
                        fontSize =
                            14.sp
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                Color(0xFFE4DCD8)
                            )
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {
                    SocialButton(
                        label = if (googleLoading) "Signing in..." else "Google",
                        symbol = "G",
                        modifier = Modifier.weight(1f),
                        symbolColor = Color(0xFF4285F4),
                        onClick = {
                            if (!googleLoading) {
                                googleLoading = true
                                loginScope.launch {
                                    try {
                                        val user =
                                            PurrFectApi.googleLogin(loginContext)

                                        withContext(Dispatchers.Main) {
                                            googleLoading = false
                                            Toast.makeText(
                                                loginContext,
                                                "Google login successful",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            onGoogleSuccess(user)
                                        }
                                    } catch (error: Exception) {
                                        withContext(Dispatchers.Main) {
                                            googleLoading = false
                                            Toast.makeText(
                                                loginContext,
                                                error.message ?: "Google login failed",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    )
                    SocialButton(
                        label = "Facebook",
                        symbol = "f",
                        modifier = Modifier.weight(1f),
                        symbolColor = Color(0xFF1877F2)
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(22.dp)
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSignUp()
                            },
                    horizontalArrangement =
                        Arrangement.Center,
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Text(
                        text =
                            "Don’t have an account? ",
                        color =
                            Color(0xFF4D4C53),
                        fontSize =
                            15.sp
                    )
                    Text(
                        text =
                            "Sign Up →",
                        color =
                            Color(0xFFE85E78),
                        fontSize =
                            15.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
/* =========================================================
SIGN UP FIELD
========================================================= */
@Composable
fun SignupField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: @Composable () -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType =
        KeyboardType.Text,
    visualTransformation: VisualTransformation =
        VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier =
            Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFF858189),
                fontSize = 15.sp
            )
        },
        leadingIcon = icon,
        trailingIcon = trailingIcon,
        singleLine = true,
        keyboardOptions =
            KeyboardOptions(
                keyboardType = keyboardType
            ),
        visualTransformation =
            visualTransformation,
        shape =
            RoundedCornerShape(17.dp),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor =
                    Color(0xFFE2D9D5),
                unfocusedBorderColor =
                    Color(0xFFE2D9D5),
                focusedContainerColor =
                    Color(0xFFFFFCFA),
                unfocusedContainerColor =
                    Color(0xFFFFFCFA),
                focusedTextColor =
                    TextDark,
                unfocusedTextColor =
                    TextDark,
                cursorColor =
                    Pink
            )
    )
}
/* =========================================================
SOCIAL BUTTON
========================================================= */
@Composable
fun SocialButton(
    label: String,
    symbol: String,
    modifier: Modifier = Modifier,
    symbolColor: Color,
    onClick: () -> Unit = {}
) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        modifier = modifier
            .height(58.dp)
            .clickable(onClick = onClick),
        readOnly = true,
        leadingIcon = {
            Text(
                text = symbol,
                color = symbolColor,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        },
        placeholder = {
            Text(
                text = label,
                color = TextDark,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        },
        singleLine = true,
        shape =
            RoundedCornerShape(17.dp),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor =
                    Color(0xFFE2D9D5),
                unfocusedBorderColor =
                    Color(0xFFE2D9D5),
                focusedContainerColor =
                    Color(0xFFFFFCFA),
                unfocusedContainerColor =
                    Color(0xFFFFFCFA)
            )
    )
}
/* =========================================================
WELCOME CIRCLE
========================================================= */
@Composable
fun WelcomeCircle(
    x: Dp,
    y: Dp
) {
    Box(
        modifier = Modifier
            .size(31.dp)
            .offset(
                x = x,
                y = y
            )
            .clip(CircleShape)
            .background(
                Color(0xFFFFDEC8)
            )
    )
}
/* =========================================================
WELCOME STAR
========================================================= */
@Composable
fun WelcomeStar(
    text: String,
    x: Dp,
    y: Dp,
    size: Int,
    color: Color = Color(0xFFFFEFA8)
) {
    Icon(
        imageVector =
            if (text == "H") {
                Icons.Filled.Star
            } else {
                Icons.Outlined.StarBorder
            },
        contentDescription = "Decorative star",
        tint = color,
        modifier = Modifier
            .offset(x = x, y = y)
            .size(size.dp)
    )
}
/* =========================================================
WELCOME CATS
========================================================= */
@Composable
fun WelcomeCats(
    modifier: Modifier = Modifier
) {
    Image(
        painter =
            painterResource(
                id =
                    R.drawable.catpair
            ),
        contentDescription =
            "Two cats",
        modifier =
            modifier,
        contentScale =
            ContentScale.Fit
    )
}
/* =========================================================
DISCOVER SCREEN
========================================================= */
@Composable
fun DiscoverScreen(
    currentCatId: Int,
    onTabSelected: (Int) -> Unit,
    onCatClick: (CatProfile) -> Unit,
    onLikeConfirmed: suspend (Int) -> Unit,
    onStarCat: suspend (Int) -> Unit,
    onMenuClick: () -> Unit
) {
    var currentCat by remember {
        mutableIntStateOf(0)
    }
    var discoverCats by remember {
        mutableStateOf<List<CatProfile>>(emptyList())
    }
    var isLoading by remember {
        mutableStateOf(true)
    }
    var buttonAction by remember {
        mutableIntStateOf(0)
    }
    var likeAnimationTrigger by remember {
        mutableIntStateOf(0)
    }
    var likeSending by remember {
        mutableStateOf(false)
    }
    var starSending by remember {
        mutableStateOf(false)
    }
    val discoverLikeScope = rememberCoroutineScope()
    val discoverContext = LocalContext.current
    var starSelected by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(currentCatId) {
        currentCat = 0
        buttonAction = 0
        starSelected = false
        isLoading = true
        discoverCats = PurrFectApi.getDiscoverCats(currentCatId)
        isLoading = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    tint = TextDark
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Discover",
                    color = TextDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filters",
                    tint = TextDark
                )
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Finding cats near you...",
                    color = TextGrey,
                    fontSize = 14.sp
                )
            }
        } else if (discoverCats.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🐱", fontSize = 64.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No other cats found",
                        color = TextDark,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "New cats will appear here when they join PurrFect.",
                        color = TextGrey,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            val safeIndex = currentCat.coerceIn(0, discoverCats.lastIndex)
            SwipeCard(
                cat = discoverCats[safeIndex],
                catNumber = safeIndex,
                buttonAction = buttonAction,
                onCatClick = {
                    onCatClick(discoverCats[safeIndex])
                },
                onSwipeComplete = {
                    currentCat++
                    buttonAction = 0
                    starSelected = false
                },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 13.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionButton(
                icon = Icons.Outlined.Close,
                iconColor = Pink,
                size = 53.dp,
                onClick = { buttonAction = 1 }
            )
            StarActionButton(
                selected = starSelected,
                onClick = {
                    val starredCatId = discoverCats.getOrNull(
                        currentCat.coerceIn(0, discoverCats.lastIndex)
                    )?.id ?: 0
                    if (!starSending && starredCatId > 0) {
                        starSending = true
                        discoverLikeScope.launch {
                            try {
                                onStarCat(starredCatId)
                                starSelected = true
                                buttonAction = 3
                            } catch (error: Exception) {
                                Toast.makeText(
                                    discoverContext,
                                    "Star failed: ${error.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } finally {
                                starSending = false
                            }
                        }
                    }
                }
            )
            HeartBurstButton(
                trigger = likeAnimationTrigger,
                onClick = {
                    val likedCatId =
                        discoverCats.getOrNull(
                            currentCat.coerceIn(0, discoverCats.lastIndex)
                        )?.id ?: 0

                    if (!likeSending && likedCatId > 0) {
                        likeSending = true
                        discoverLikeScope.launch {
                            try {
                                onLikeConfirmed(likedCatId)
                                buttonAction = 2
                                likeAnimationTrigger++
                            } catch (error: Exception) {
                                Toast.makeText(
                                    discoverContext,
                                    "Like failed: ${error.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            } finally {
                                likeSending = false
                            }
                        }
                    }
                }
            )
            ShareButton()
        }
        BottomNavigation(
            selectedTab = 0,
            onTabSelected = { onTabSelected(it) }
        )
    }
}
/* =========================================================
STAR BUTTON
========================================================= */
@Composable
fun StarActionButton(
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(53.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(
                1.dp,
                BorderColor,
                CircleShape
            ),
        contentAlignment =
            Alignment.Center
    ) {
        IconButton(
            onClick =
                onClick
        ) {
            Icon(
                imageVector =
                    if (selected) {
                        Icons.Filled.Star
                    } else {
                        Icons.Outlined.StarBorder
                    },
                contentDescription =
                    "Star",
                tint =
                    if (selected) {
                        StarYellow
                    } else {
                        Purple
                    },
                modifier =
                    Modifier.size(27.dp)
            )
        }
    }
}
/* =========================================================
HEART BURST
========================================================= */
@Composable
fun HeartBurstButton(
    trigger: Int,
    onClick: () -> Unit
) {
    Box(
        modifier =
            Modifier.size(100.dp),
        contentAlignment =
            Alignment.Center
    ) {
        ActionButton(
            icon =
                Icons.Outlined.Favorite,
            iconColor =
                Pink,
            size =
                53.dp,
            onClick =
                onClick
        )
        if (trigger > 0) {
            HeartParticle(
                trigger = trigger,
                x = -25f,
                y = -5f,
                delay = 0
            )
            HeartParticle(
                trigger = trigger,
                x = -12f,
                y = 0f,
                delay = 70
            )
            HeartParticle(
                trigger = trigger,
                x = 3f,
                y = -3f,
                delay = 120
            )
            HeartParticle(
                trigger = trigger,
                x = 17f,
                y = 0f,
                delay = 50
            )
            HeartParticle(
                trigger = trigger,
                x = 28f,
                y = -4f,
                delay = 100
            )
            HeartParticle(
                trigger = trigger,
                x = -18f,
                y = 3f,
                delay = 150
            )
        }
    }
}
/* =========================================================
HEART PARTICLE
========================================================= */
@Composable
fun HeartParticle(
    trigger: Int,
    x: Float,
    y: Float,
    delay: Int
) {
    val particleY =
        remember {
            Animatable(0f)
        }
    val particleX =
        remember {
            Animatable(0f)
        }
    val alpha =
        remember {
            Animatable(1f)
        }
    val scale =
        remember {
            Animatable(0.5f)
        }
    LaunchedEffect(trigger) {
        particleY.snapTo(0f)
        particleX.snapTo(0f)
        alpha.snapTo(1f)
        scale.snapTo(0.5f)
        kotlinx.coroutines.delay(
            delay.toLong()
        )
        launch {
            particleY.animateTo(
                -95f,
                tween(850)
            )
        }
        launch {
            particleX.animateTo(
                x,
                tween(850)
            )
        }
        launch {
            scale.animateTo(
                1f,
                tween(350)
            )
        }
        launch {
            kotlinx.coroutines.delay(350)
            alpha.animateTo(
                0f,
                tween(500)
            )
        }
    }
    Text(
        text =
            "♥",
        color =
            Pink,
        fontSize =
            13.sp,
        modifier =
            Modifier
                .offset {
                    IntOffset(
                        particleX.value.roundToInt(),
                        particleY.value.roundToInt()
                    )
                }
                .graphicsLayer {
                    this.alpha =
                        alpha.value
                    scaleX =
                        scale.value
                    scaleY =
                        scale.value
                }
    )
}
/* =========================================================
SWIPE CARD
========================================================= */
@Composable
fun SwipeCard(
    cat: CatProfile,
    catNumber: Int,
    buttonAction: Int,
    onCatClick: () -> Unit,
    onSwipeComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope =
        rememberCoroutineScope()
    val offsetX =
        remember {
            Animatable(0f)
        }
    val offsetY =
        remember {
            Animatable(0f)
        }
    var lastHandledAction by remember {
        mutableIntStateOf(0)
    }
    var superLike by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(buttonAction) {
        if (
            buttonAction != 0 &&
            buttonAction != lastHandledAction
        ) {
            lastHandledAction =
                buttonAction
            when (buttonAction) {
                1 -> {
                    offsetX.animateTo(
                        -1200f,
                        tween(350)
                    )
                    onSwipeComplete()
                }
                2 -> {
                    offsetX.animateTo(
                        1200f,
                        tween(350)
                    )
                    onSwipeComplete()
                }
                3 -> {
                    superLike = true
                    offsetY.animateTo(
                        -1000f,
                        tween(400)
                    )
                    onSwipeComplete()
                    superLike = false
                }
            }
        }
    }
    LaunchedEffect(catNumber) {
        offsetX.snapTo(0f)
        offsetY.snapTo(0f)
        superLike = false
        lastHandledAction = 0
    }
    Box(
        modifier =
            modifier,
        contentAlignment =
            Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        offsetX.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .graphicsLayer {
                    rotationZ =
                        (
                                offsetX.value / 25f
                                ).coerceIn(
                                -12f,
                                12f
                            )
                    alpha =
                        1f -
                                (
                                        abs(
                                            offsetX.value
                                        ) / 900f
                                        ).coerceIn(
                                        0f,
                                        0.35f
                                    )
                }
                .clickable {
                    onCatClick()
                }
                .pointerInput(catNumber) {
                    detectDragGestures(
                        onDrag = {
                                change,
                                dragAmount ->
                            change.consume()
                            scope.launch {
                                offsetX.snapTo(
                                    offsetX.value +
                                            dragAmount.x
                                )
                                offsetY.snapTo(
                                    offsetY.value +
                                            dragAmount.y * 0.25f
                                )
                            }
                        },
                        onDragEnd = {
                            val threshold =
                                220f
                            when {
                                offsetX.value >
                                        threshold -> {
                                    scope.launch {
                                        offsetX.animateTo(
                                            1200f,
                                            tween(350)
                                        )
                                        onSwipeComplete()
                                    }
                                }
                                offsetX.value <
                                        -threshold -> {
                                    scope.launch {
                                        offsetX.animateTo(
                                            -1200f,
                                            tween(350)
                                        )
                                        onSwipeComplete()
                                    }
                                }
                                else -> {
                                    scope.launch {
                                        offsetX.animateTo(
                                            0f,
                                            tween(250)
                                        )
                                        offsetY.animateTo(
                                            0f,
                                            tween(250)
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFE4D4C8)),
                contentAlignment = Alignment.Center
            ) {
                if (cat.photoBitmap != null) {
                    Image(
                        bitmap = cat.photoBitmap.asImageBitmap(),
                        contentDescription = "${cat.name} cat photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "🐱", fontSize = 86.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = cat.name,
                            color = TextGrey,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            if (superLike) {
                Box(
                    modifier = Modifier
                        .align(
                            Alignment.TopCenter
                        )
                        .padding(
                            top = 25.dp
                        )
                        .clip(CircleShape)
                        .background(
                            Purple
                        )
                        .padding(
                            horizontal = 18.dp,
                            vertical = 8.dp
                        )
                ) {
                    Text(
                        text =
                            "SUPER LIKE n",
                        color =
                            Color.White,
                        fontSize =
                            13.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(
                        Alignment.BottomCenter
                    )
                    .clip(
                        RoundedCornerShape(18.dp)
                    )
                    .background(
                        CardColor
                    )
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 13.dp,
                        bottom = 14.dp
                    )
            ) {
                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Text(
                        text = "${cat.name}, ${cat.age}",
                        color = TextDark,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier =
                            Modifier.width(5.dp)
                    )
                    Text(
                        text = if (cat.gender.equals("Male", ignoreCase = true)) "\u2642" else "\u2640",
                        color = if (cat.gender.equals("Male", ignoreCase = true)) Purple else Pink,
                        fontSize = 20.sp
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )
                Text(
                    text = "${cat.breed} • Nearby",
                    color = TextDark,
                    fontSize = 13.sp
                )
                Spacer(
                    modifier =
                        Modifier.height(9.dp)
                )
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(7.dp)
                ) {
                    ProfileTag(
                        "Playful"
                    )
                    ProfileTag(
                        "Friendly"
                    )
                    ProfileTag(
                        "Indoor"
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(9.dp)
                )
                Text(
                    text =
                        "Loves chasing toys and\n" +
                                "sleeping in sunny windows.",
                    color =
                        TextDark,
                    fontSize =
                        14.sp,
                    lineHeight =
                        19.sp
                )
            }
        }
    }
}
/* =========================================================
MATCHES SCREEN
========================================================= */
@Composable
fun MatchesScreen(
    matches: List<MatchItem>,
    onTabSelected: (Int) -> Unit,
    onChatClick: (MatchItem) -> Unit,
    onMatchClick: (MatchItem) -> Unit
) {
    var selectedFilter by remember {
        mutableIntStateOf(0)
    }
    val visibleMatches =
        if (selectedFilter == 0) {
            matches
        } else {
            matches.filter {
                it.likedYou
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
/* =================================================
HEADER
================================================= */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.Menu,
                    contentDescription =
                        "Menu",
                    tint = TextDark,
                    modifier =
                        Modifier.size(27.dp)
                )
            }
            Box(
                modifier =
                    Modifier.weight(1f),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text =
                        "Matches",
                    color = TextDark,
                    fontSize = 20.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.Tune,
                    contentDescription =
                        "Filters",
                    tint = TextDark,
                    modifier =
                        Modifier.size(27.dp)
                )
            }
        }
        /* =================================================
MATCH FILTER
================================================= */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 17.dp,
                    vertical = 6.dp
                )
                .clip(
                    RoundedCornerShape(22.dp)
                )
                .background(CardColor)
                .border(
                    width = 1.dp,
                    color = BorderColor,
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(2.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            MatchFilterButton(
                text = "All Matches",
                icon = Icons.Outlined.Pets,
                selected = selectedFilter == 0,
                modifier = Modifier.weight(1f),
                onClick = {
                    selectedFilter = 0
                }
            )
            MatchFilterButton(
                text = "Liked You",
                icon = Icons.Outlined.Favorite,
                selected = selectedFilter == 1,
                modifier = Modifier.weight(1f),
                onClick = {
                    selectedFilter = 1
                }
            )
        }
        Spacer(
            modifier =
                Modifier.height(8.dp)
        )
        /* =================================================
MATCH / LIKES CONTENT
================================================= */
        if (selectedFilter == 1) {
            LikedYouContent(
                likedUsers = visibleMatches,
                modifier = Modifier.weight(1f)
            )
        } else if (visibleMatches.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment =
                    Alignment.Center
            ) {
                Column(
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector =
                            Icons.Outlined.FavoriteBorder,
                        contentDescription =
                            null,
                        tint = Pink,
                        modifier =
                            Modifier.size(48.dp)
                    )
                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )
                    Text(
                        text =
                            "No matches yet",
                        color = TextDark,
                        fontSize = 15.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )
                    Text(
                        text =
                            "Your matches will appear here.",
                        color = TextGrey,
                        fontSize = 11.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding =
                    PaddingValues(
                        start = 17.dp,
                        end = 17.dp,
                        top = 7.dp,
                        bottom = 10.dp
                    ),
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {
                items(
                    visibleMatches
                ) { match ->
                    MatchCard(
                        match = match,
                        onChatClick = {
                            onChatClick(match)
                        },
                        onMatchClick = {
                            onMatchClick(match)
                        }
                    )
                }
            }
        }
        BottomNavigation(
            selectedTab = 1,
            onTabSelected = {
                onTabSelected(it)
            }
        )
    }
}
/* =========================================================
MATCH FILTER BUTTON
========================================================= */
@Composable
fun MatchFilterButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .height(46.dp)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(
                if (selected) {
                    Pink
                } else {
                    Color.Transparent
                }
            )
            .clickable {
                onClick()
            },
        horizontalArrangement =
            Arrangement.Center,
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint =
                if (selected) {
                    Color.White
                } else {
                    TextGrey
                },
            modifier =
                Modifier.size(21.dp)
        )
        Spacer(
            modifier =
                Modifier.width(7.dp)
        )
        Text(
            text = text,
            color =
                if (selected) {
                    Color.White
                } else {
                    TextGrey
                },
            fontSize = 13.sp,
            fontWeight =
                if (selected) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                }
        )
    }
}
/* =========================================================
LIKED YOU CONTENT
========================================================= */
@Composable
fun LikedYouContent(
    likedUsers: List<MatchItem>,
    modifier: Modifier = Modifier
) {
    if (likedUsers.isEmpty()) {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = Pink,
                    modifier =
                        Modifier.size(52.dp)
                )
                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )
                Text(
                    text =
                        "No likes yet",
                    color = TextDark,
                    fontSize = 16.sp,
                    fontWeight =
                        FontWeight.SemiBold
                )
                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )
                Text(
                    text =
                        "When someone likes your cat,\nthey will appear here.",
                    color = TextGrey,
                    fontSize = 11.sp,
                    lineHeight = 17.sp
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding =
                PaddingValues(
                    start = 17.dp,
                    end = 17.dp,
                    top = 7.dp,
                    bottom = 10.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {
            items(
                likedUsers
            ) { user ->
                LikedYouCard(
                    user = user
                )
            }
        }
    }
}
/* =========================================================
LIKED YOU CARD
========================================================= */
@Composable
fun LikedYouCard(
    user: MatchItem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(18.dp)
            )
            .background(CardColor)
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(10.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(105.dp)
                .clip(
                    RoundedCornerShape(15.dp)
                )
                .background(
                    Color(0xFFEDE4E0)
                )
        ) {
            Image(
                painter =
                    painterResource(
                        id = user.imageRes
                    ),
                contentDescription =
                    "${user.name} cat photo",
                modifier =
                    Modifier.fillMaxSize(),
                contentScale =
                    ContentScale.Crop
            )
            if (user.online) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .align(
                            Alignment.TopEnd
                        )
                        .clip(CircleShape)
                        .background(
                            Color(0xFF63B87A)
                        )
                        .border(
                            2.dp,
                            CardColor,
                            CircleShape
                        )
                ) { }
            }
        }
        Spacer(
            modifier =
                Modifier.width(12.dp)
        )
        Column(
            modifier =
                Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                Text(
                    text = user.name,
                    color = TextDark,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier =
                        Modifier.width(5.dp)
                )
                Text(
                    text =
                        if (user.gender == "Female") {
                            "🐾"
                        } else {
                            "🐾"
                        },
                    color =
                        if (user.gender == "Female") {
                            Pink
                        } else {
                            Color(0xFF638BC7)
                        },
                    fontSize = 18.sp
                )
            }
            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )
            Text(
                text =
                    "${user.breed} • ${user.distance}",
                color = TextDark,
                fontSize = 11.sp
            )
            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )
            Text(
                text = user.about,
                color = TextGrey,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                maxLines = 2
            )
            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(14.dp)
                    )
                    .background(
                        LightPink
                    )
                    .padding(
                        horizontal = 9.dp,
                        vertical = 4.dp
                    )
            ) {
                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector =
                            Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint = Pink,
                        modifier =
                            Modifier.size(13.dp)
                    )
                    Spacer(
                        modifier =
                            Modifier.width(4.dp)
                    )
                    Text(
                        text =
                            "Liked You",
                        color = Pink,
                        fontSize = 10.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(
            modifier =
                Modifier.width(6.dp)
        )
        Box(
            modifier = Modifier
                .size(49.dp)
                .clip(CircleShape)
                .background(Pink),
            contentAlignment =
                Alignment.Center
        ) {
            Icon(
                imageVector =
                    Icons.Outlined.Favorite,
                contentDescription =
                    "Like back ${user.name}",
                tint = Color.White,
                modifier =
                    Modifier.size(24.dp)
            )
        }
    }
}
/* =========================================================
MATCH CARD
========================================================= */
@Composable
fun MatchCard(
    match: MatchItem,
    onChatClick: () -> Unit,
    onMatchClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onMatchClick()
            }
            .clip(
                RoundedCornerShape(18.dp)
            )
            .background(CardColor)
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(10.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
/* =================================================
CAT PHOTO
================================================= */
        Box(
            modifier = Modifier
                .size(105.dp)
                .clip(
                    RoundedCornerShape(15.dp)
                )
                .background(
                    Color(0xFFEDE4E0)
                )
        ) {
            Image(
                painter =
                    painterResource(
                        id = match.imageRes
                    ),
                contentDescription =
                    "${match.name} cat photo",
                modifier =
                    Modifier.fillMaxSize(),
                contentScale =
                    ContentScale.Crop
            )
            if (match.online) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .align(
                            Alignment.TopEnd
                        )
                        .clip(CircleShape)
                        .background(
                            Color(0xFF63B87A)
                        )
                        .border(
                            2.dp,
                            CardColor,
                            CircleShape
                        )
                ) { }
            }
        }
        Spacer(
            modifier =
                Modifier.width(12.dp)
        )
        /* =================================================
INFORMATION
================================================= */
        Column(
            modifier =
                Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                Text(
                    text = match.name,
                    color = TextDark,
                    fontSize = 17.sp,
                    fontWeight =
                        FontWeight.Bold
                )
                Spacer(
                    modifier =
                        Modifier.width(5.dp)
                )
                Text(
                    text =
                        if (match.gender == "Female") {
                            "\u2640"
                        } else {
                            "\u2642"
                        },
                    color =
                        if (match.gender == "Female") {
                            Pink
                        } else {
                            Color(0xFF638BC7)
                        },
                    fontSize = 18.sp
                )
            }
            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )
            Text(
                text =
                    "${match.breed} • ${match.distance}",
                color = TextDark,
                fontSize = 11.sp
            )
            Spacer(
                modifier =
                    Modifier.height(7.dp)
            )
            Text(
                text = match.about,
                color = TextGrey,
                fontSize = 11.sp,
                lineHeight = 16.sp,
                maxLines = 2
            )
            if (match.isNewMatch) {
                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(14.dp)
                        )
                        .background(
                            LightPink
                        )
                        .padding(
                            horizontal = 9.dp,
                            vertical = 4.dp
                        )
                ) {
                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector =
                                Icons.Outlined.Pets,
                            contentDescription =
                                null,
                            tint = Pink,
                            modifier =
                                Modifier.size(13.dp)
                        )
                        Spacer(
                            modifier =
                                Modifier.width(4.dp)
                        )
                        Text(
                            text =
                                "New Match",
                            color = Pink,
                            fontSize = 10.sp,
                            fontWeight =
                                FontWeight.SemiBold
                        )
                    }
                }
            }
        }
        Spacer(
            modifier =
                Modifier.width(6.dp)
        )
        /* =================================================
CHAT BUTTON
================================================= */
        Box(
            modifier = Modifier
                .size(49.dp)
                .clip(CircleShape)
                .background(Pink)
                .clickable {
                    onChatClick()
                },
            contentAlignment =
                Alignment.Center
        ) {
            Icon(
                imageVector =
                    Icons.Outlined.ChatBubbleOutline,
                contentDescription =
                    "Chat with ${match.name}",
                tint = Color.White,
                modifier =
                    Modifier.size(24.dp)
            )
        }
    }
}
/* =========================================================
CAT PROFILE SCREEN
========================================================= */
@Composable
fun CatProfileScreen(
    match: MatchItem,
    onBack: () -> Unit,
    onLikeSent: () -> Unit,
    onLikeConfirmed: suspend (Int) -> Unit
) {
    var likeTrigger by remember {
        mutableIntStateOf(0)
    }
    var likeSent by remember {
        mutableStateOf(false)
    }
    var likeSending by remember {
        mutableStateOf(false)
    }
    var likeError by remember {
        mutableStateOf<String?>(null)
    }
    val likeAlpha = remember {
        Animatable(1f)
    }
    val likeScope = rememberCoroutineScope()
    LaunchedEffect(likeSent) {
        if (likeSent) {
            likeAlpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(350)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.ArrowBack,
                    contentDescription =
                        "Back",
                    tint =
                        TextDark,
                    modifier =
                        Modifier.size(28.dp)
                )
            }
            Box(
                modifier =
                    Modifier.weight(1f),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text =
                        "Cat Profile",
                    color =
                        TextDark,
                    fontSize =
                        20.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
            Box(
                modifier =
                    Modifier.width(48.dp),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text =
                        "•••",
                    color =
                        TextDark,
                    fontSize =
                        22.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding =
                PaddingValues(
                    start = 18.dp,
                    end = 18.dp,
                    top = 4.dp,
                    bottom = 20.dp
                )
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(270.dp)
                        .clip(
                            RoundedCornerShape(22.dp)
                        )
                ) {
                    if (match.photoBitmap != null) {
                        Image(
                            bitmap = match.photoBitmap.asImageBitmap(),
                            contentDescription = "${match.name} cat photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = match.imageRes),
                            contentDescription = "${match.name} cat photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .clip(CircleShape)
                            .background(
                                Color(0x99000000)
                            )
                            .padding(
                                horizontal = 13.dp,
                                vertical = 7.dp
                            )
                    ) {
                        Text(
                            text =
                                "1/5",
                            color =
                                Color.White,
                            fontSize =
                                12.sp,
                            fontWeight =
                                FontWeight.Bold
                        )
                    }
                }
            }
            item {
                Spacer(
                    modifier =
                        Modifier.height(13.dp)
                )
                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Text(
                        text =
                            "${match.name}, ${match.age}",
                        color =
                            TextDark,
                        fontSize =
                            27.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )
                    Text(
                        text =
                            if (match.gender == "Male") "\u2642" else "\u2640",
                        color =
                            if (match.gender == "Male") {
                                Color(0xFF7189D9)
                            } else {
                                Pink
                            },
                        fontSize =
                            27.sp
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(3.dp)
                )
                Text(
                    text =
                        "${match.breed} • ${match.distance}",
                    color =
                        TextDark,
                    fontSize =
                        14.sp
                )
                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
            }
            item {
                ProfileDivider()
                Text(
                    text =
                        "About ${match.name}",
                    color =
                        TextDark,
                    fontSize =
                        16.sp,
                    fontWeight =
                        FontWeight.Bold,
                    modifier =
                        Modifier.padding(top = 10.dp)
                )
                Spacer(
                    modifier =
                        Modifier.height(9.dp)
                )
                Text(
                    text =
                        match.about,
                    color =
                        TextDark,
                    fontSize =
                        14.sp,
                    lineHeight =
                        21.sp
                )
                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )
            }
            item {
                ProfileDivider()
                Text(
                    text =
                        "Personality",
                    color =
                        TextDark,
                    fontSize =
                        16.sp,
                    fontWeight =
                        FontWeight.Bold,
                    modifier =
                        Modifier.padding(top = 10.dp)
                )
                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(7.dp)
                ) {
                    ProfileTag("Calm")
                    ProfileTag("Affectionate")
                    ProfileTag("Friendly")
                }
                Spacer(
                    modifier =
                        Modifier.height(7.dp)
                )
                ProfileTag("Independent")
                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )
            }
            item {
                ProfileDivider()
                Text(
                    text =
                        "Favorite Activities",
                    color =
                        TextDark,
                    fontSize =
                        16.sp,
                    fontWeight =
                        FontWeight.Bold,
                    modifier =
                        Modifier.padding(top = 10.dp)
                )
                Spacer(
                    modifier =
                        Modifier.height(13.dp)
                )
                Row(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        Arrangement.SpaceEvenly
                ) {
                    FavoriteActivity(
                        icon = "💤",
                        label = "Napping"
                    )
                    FavoriteActivity(
                        icon = "🎾",
                        label = "Playing"
                    )
                    FavoriteActivity(
                        icon = "🍽",
                        label = "Eating"
                    )
                    FavoriteActivity(
                        icon = "🧭",
                        label = "Exploring"
                    )
                }
                Spacer(
                    modifier =
                        Modifier.height(15.dp)
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),
                    contentAlignment =
                        Alignment.Center
                ) {
                    if (!likeSent) {
                        Button(
                            onClick = {
                                if (!likeSending && !likeSent) {
                                    likeSending = true
                                    likeError = null
                                    likeScope.launch {
                                        try {
                                            onLikeConfirmed(match.id)
                                            likeSent = true
                                            likeTrigger++
                                            onLikeSent()
                                        } catch (error: Exception) {
                                            likeError = error.message ?: "Like failed"
                                        } finally {
                                            likeSending = false
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .graphicsLayer {
                                    alpha =
                                        likeAlpha.value
                                },
                            shape =
                                RoundedCornerShape(30.dp),
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        Pink
                                )
                        ) {
                            Icon(
                                imageVector =
                                    Icons.Filled.Favorite,
                                contentDescription =
                                    null,
                                tint =
                                    Color.White,
                                modifier =
                                    Modifier.size(23.dp)
                            )
                            Spacer(
                                modifier =
                                    Modifier.width(8.dp)
                            )
                            Text(
                                text =
                                    "Like",
                                color =
                                    Color.White,
                                fontSize =
                                    17.sp,
                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }
                    }
                    likeError?.let { errorMessage ->
                        Text(
                            text = errorMessage,
                            color = Color(0xFFD32F2F),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 6.dp)
                        )
                    }
                    if (likeTrigger > 0) {
                        HeartParticle(
                            trigger = likeTrigger,
                            x = -55f,
                            y = 0f,
                            delay = 0
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = -35f,
                            y = 5f,
                            delay = 60
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = -15f,
                            y = -4f,
                            delay = 110
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = 10f,
                            y = 2f,
                            delay = 40
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = 30f,
                            y = -2f,
                            delay = 90
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = 50f,
                            y = 4f,
                            delay = 140
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun ProfileDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(BorderColor)
    )
}
@Composable
fun FavoriteActivity(
    icon: String,
    label: String
) {
    Column(
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFFFFF1E7)
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text = icon,
                color = Purple,
                fontSize = 22.sp
            )
        }
        Spacer(
            modifier =
                Modifier.height(5.dp)
        )
        Text(
            text = label,
            color = TextDark,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
/* =========================================================
COMPATIBILITY SCREEN
========================================================= */
private fun CompatibilityHeartShape() = GenericShape { size, _ ->
    val w = size.width
    val h = size.height
    moveTo(
        w / 2f,
        h * 0.92f
    )
    cubicTo(
        w * 0.38f,
        h * 0.82f,
        w * 0.08f,
        h * 0.62f,
        w * 0.08f,
        h * 0.34f
    )
    cubicTo(
        w * 0.08f,
        h * 0.08f,
        w * 0.38f,
        h * 0.03f,
        w / 2f,
        h * 0.23f
    )
    cubicTo(
        w * 0.62f,
        h * 0.03f,
        w * 0.92f,
        h * 0.08f,
        w * 0.92f,
        h * 0.34f
    )
    cubicTo(
        w * 0.92f,
        h * 0.62f,
        w * 0.62f,
        h * 0.82f,
        w / 2f,
        h * 0.92f
    )
    close()
}
@Composable
fun CompatibilityScreen(
    match: MatchItem,
    onBack: () -> Unit
) {
    var likeTrigger by remember {
        mutableIntStateOf(0)
    }
    var likeSent by remember {
        mutableStateOf(false)
    }
    val likeAlpha = remember {
        Animatable(1f)
    }
    LaunchedEffect(likeSent) {
        if (likeSent) {
            likeAlpha.animateTo(
                targetValue = 0f,
                animationSpec = tween(350)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.ArrowBack,
                    contentDescription =
                        "Back",
                    tint = TextDark,
                    modifier =
                        Modifier.size(28.dp)
                )
            }
            Box(
                modifier =
                    Modifier.weight(1f),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text =
                        "Compatibility",
                    color = TextDark,
                    fontSize = 20.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
            Spacer(
                modifier =
                    Modifier.size(48.dp)
            )
        }
        androidx.compose.foundation.lazy.LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding =
                PaddingValues(
                    start = 18.dp,
                    end = 18.dp,
                    top = 4.dp,
                    bottom = 24.dp
                ),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp),
                    contentAlignment =
                        Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.86f)
                            .height(260.dp)
                            .clip(
                                CompatibilityHeartShape()
                            )
                            .background(
                                Color(0xFFFFE8ED)
                            )
                            .border(
                                width = 9.dp,
                                color = Pink,
                                shape =
                                    CompatibilityHeartShape()
                            )
                    ) {
                        Image(
                            painter =
                                painterResource(
                                    id = R.drawable.signcat
                                ),
                            contentDescription =
                                "My cat",
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            contentScale =
                                ContentScale.Crop
                        )
                        Image(
                            painter =
                                painterResource(
                                    id = match.imageRes
                                ),
                            contentDescription =
                                "${match.name} cat",
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxSize(),
                            contentScale =
                                ContentScale.Crop
                        )
                    }
                }
            }
            item {
                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Text(
                        text =
                            "My Cat",
                        color = TextDark,
                        fontSize = 24.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                    Spacer(
                        modifier =
                            Modifier.width(12.dp)
                    )
                    Icon(
                        imageVector =
                            Icons.Filled.Favorite,
                        contentDescription =
                            "Love",
                        tint = Pink,
                        modifier =
                            Modifier.size(30.dp)
                    )
                    Spacer(
                        modifier =
                            Modifier.width(12.dp)
                    )
                    Text(
                        text =
                            match.name,
                        color = TextDark,
                        fontSize = 24.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
            item {
                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )
                Text(
                    text =
                        "87% Compatible",
                    color = Pink,
                    fontSize = 24.sp,
                    fontWeight =
                        FontWeight.Bold
                )
                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )
                Text(
                    text =
                        "Great match! They both enjoy\ncalm environments and love to play.",
                    color = TextDark,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    modifier =
                        Modifier.padding(horizontal = 10.dp)
                )
            }
            item {
                Spacer(
                    modifier =
                        Modifier.height(18.dp)
                )
                CompatibilityMetric(
                    icon = "🐾",
                    title = "Age",
                    value = 90
                )
                CompatibilityMetric(
                    icon = "😊",
                    title = "Personality",
                    value = 85
                )
                CompatibilityMetric(
                    icon = "⚡",
                    title = "Energy Level",
                    value = 80
                )
                CompatibilityMetric(
                    icon = "🎾",
                    title = "Play Style",
                    value = 90
                )
                CompatibilityMetric(
                    icon = "📍",
                    title = "Location",
                    value = 80
                )
            }
            item {
                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    contentAlignment =
                        Alignment.Center
                ) {
                    if (!likeSent) {
                        Button(
                            onClick = {
                                likeSent = true
                                likeTrigger++
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .graphicsLayer {
                                    alpha = likeAlpha.value
                                },
                            shape =
                                RoundedCornerShape(28.dp),
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        Purple
                                )
                        ) {
                            Text(
                                text =
                                    "Send Like",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }
                    }
                    if (likeTrigger > 0) {
                        HeartParticle(
                            trigger = likeTrigger,
                            x = -55f,
                            y = 0f,
                            delay = 0
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = -35f,
                            y = 5f,
                            delay = 60
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = -15f,
                            y = -4f,
                            delay = 110
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = 10f,
                            y = 2f,
                            delay = 40
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = 30f,
                            y = -2f,
                            delay = 90
                        )
                        HeartParticle(
                            trigger = likeTrigger,
                            x = 50f,
                            y = 4f,
                            delay = 140
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun CompatibilityMetric(
    icon: String,
    title: String,
    value: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            color = Purple,
            fontSize = 23.sp,
            modifier =
                Modifier.width(42.dp)
        )
        Text(
            text = title,
            color = TextDark,
            fontSize = 13.sp,
            fontWeight =
                FontWeight.SemiBold,
            modifier =
                Modifier.width(118.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFFF2E8EA)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(value / 100f)
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Pink)
            ) { }
        }
        Spacer(
            modifier =
                Modifier.width(12.dp)
        )
        Text(
            text =
                "$value%",
            color = TextDark,
            fontSize = 12.sp,
            fontWeight =
                FontWeight.SemiBold,
            modifier =
                Modifier.width(35.dp)
        )
    }
}
/* =========================================================
CHATS SCREEN
========================================================= */
@Composable
fun ChatsScreen(
    onTabSelected: (Int) -> Unit,
    onChatClick: (ChatItem) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }
    val chats =
        remember {
            listOf(
                ChatItem(
                    name = "Bella",
                    message =
                        "Milo looks so cute! n",
                    time = "8:42 PM",
                    unread = 2,
                    online = true
                ),
                ChatItem(
                    name = "Simba",
                    message =
                        "Are you free for a playdate?",
                    time = "7:18 PM",
                    unread = 1,
                    online = true
                ),
                ChatItem(
                    name = "Luna",
                    message =
                        "Aww, they would get along!",
                    time = "Yesterday"
                ),
                ChatItem(
                    name = "Coco",
                    message =
                        "Thanks for the match ¤n",
                    time = "Yesterday"
                ),
                ChatItem(
                    name = "Oliver",
                    message =
                        "That sounds great!",
                    time = "Monday"
                )
            )
        }
    val filteredChats =
        chats.filter {
            it.name.contains(
                searchText,
                ignoreCase = true
            ) ||
                    it.message.contains(
                        searchText,
                        ignoreCase = true
                    )
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundColor
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            Box(
                modifier =
                    Modifier.fillMaxWidth(),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text =
                        "Chats",
                    color =
                        TextDark,
                    fontSize =
                        16.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
        OutlinedTextField(
            value =
                searchText,
            onValueChange = {
                searchText = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp
                ),
            placeholder = {
                Text(
                    text =
                        "Search chats...",
                    color =
                        TextGrey,
                    fontSize =
                        12.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector =
                        Icons.Outlined.Search,
                    contentDescription =
                        "Search",
                    tint =
                        TextGrey
                )
            },
            singleLine =
                true,
            shape =
                RoundedCornerShape(14.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =
                        Pink,
                    unfocusedBorderColor =
                        BorderColor,
                    focusedContainerColor =
                        CardColor,
                    unfocusedContainerColor =
                        CardColor,
                    cursorColor =
                        Pink
                )
        )
        Spacer(
            modifier =
                Modifier.height(12.dp)
        )
        if (filteredChats.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment =
                    Alignment.Center
            ) {
                Column(
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector =
                            Icons.Outlined.ChatBubbleOutline,
                        contentDescription =
                            null,
                        tint =
                            TextGrey,
                        modifier =
                            Modifier.size(45.dp)
                    )
                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )
                    Text(
                        text =
                            "No chats found",
                        color =
                            TextDark,
                        fontSize =
                            14.sp,
                        fontWeight =
                            FontWeight.SemiBold
                    )
                    Spacer(
                        modifier =
                            Modifier.height(4.dp)
                    )
                    Text(
                        text =
                            "Your conversations will appear here.",
                        color =
                            TextGrey,
                        fontSize =
                            11.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding =
                    PaddingValues(
                        top = 3.dp,
                        bottom = 12.dp
                    )
            ) {
                items(
                    filteredChats
                ) { chat ->
                    ChatRow(
                        chat = chat,
                        onClick = {
                            onChatClick(chat)
                        }
                    )
                }
            }
        }
        BottomNavigation(
            selectedTab =
                2,
            onTabSelected = {
                onTabSelected(it)
            }
        )
    }
}
/* =========================================================
CHAT ROW
========================================================= */
@Composable
fun ChatRow(
    chat: ChatItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            ),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(58.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFFEDE4E0)
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Icon(
                imageVector =
                    Icons.Outlined.Pets,
                contentDescription =
                    "Cat photo",
                tint =
                    TextGrey,
                modifier =
                    Modifier.size(25.dp)
            )
            if (chat.online) {
                Box(
                    modifier = Modifier
                        .size(13.dp)
                        .align(
                            Alignment.BottomEnd
                        )
                        .clip(CircleShape)
                        .background(
                            Color(0xFF63B87A)
                        )
                        .border(
                            2.dp,
                            CardColor,
                            CircleShape
                        )
                ) { }
            }
        }
        Spacer(
            modifier =
                Modifier.width(13.dp)
        )
        Column(
            modifier =
                Modifier.weight(1f)
        ) {
            Text(
                text =
                    chat.name,
                color =
                    TextDark,
                fontSize =
                    14.sp,
                fontWeight =
                    FontWeight.SemiBold
            )
            Spacer(
                modifier =
                    Modifier.height(4.dp)
            )
            Text(
                text =
                    chat.message,
                color =
                    if (
                        chat.unread > 0
                    ) {
                        TextDark
                    } else {
                        TextGrey
                    },
                fontSize =
                    11.sp,
                fontWeight =
                    if (
                        chat.unread > 0
                    ) {
                        FontWeight.SemiBold
                    } else {
                        FontWeight.Normal
                    },
                maxLines =
                    1
            )
        }
        Column(
            horizontalAlignment =
                Alignment.End
        ) {
            Text(
                text =
                    chat.time,
                color =
                    if (
                        chat.unread > 0
                    ) {
                        Pink
                    } else {
                        TextGrey
                    },
                fontSize =
                    9.sp
            )
            if (chat.unread > 0) {
                Spacer(
                    modifier =
                        Modifier.height(6.dp)
                )
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(
                            Pink
                        ),
                    contentAlignment =
                        Alignment.Center
                ) {
                    Text(
                        text =
                            chat.unread.toString(),
                        color =
                            Color.White,
                        fontSize =
                            9.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}
/* =========================================================
INDIVIDUAL CHAT
========================================================= */
@Composable
fun IndividualChatScreen(
    chat: ChatItem,
    onBack: () -> Unit
) {
    var messageText by remember {
        mutableStateOf("")
    }
    val messages =
        remember {
            mutableStateListOf(
                ChatMessage(
                    text =
                        "Hey! n",
                    isMine =
                        false,
                    time =
                        "8:35 PM"
                ),
                ChatMessage(
                    text =
                        "Hey! How are you?",
                    isMine =
                        true,
                    time =
                        "8:36 PM"
                ),
                ChatMessage(
                    text =
                        "I'm good! Milo looks so cute! n",
                    isMine =
                        false,
                    time =
                        "8:37 PM"
                ),
                ChatMessage(
                    text =
                        "Thank you! He's very playful.",
                    isMine =
                        true,
                    time =
                        "8:39 PM"
                ),
                ChatMessage(
                    text =
                        "Milo and Bella should definitely meet sometime!",
                    isMine =
                        false,
                    time =
                        "8:42 PM"
                )
            )
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundColor
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(
                    CardColor
                )
                .border(
                    width = 1.dp,
                    color = BorderColor
                )
                .padding(
                    horizontal = 8.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            IconButton(
                onClick =
                    onBack
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.ArrowBack,
                    contentDescription =
                        "Back",
                    tint =
                        TextDark
                )
            }
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        Color(0xFFEDE4E0)
                    ),
                contentAlignment =
                    Alignment.Center
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.Pets,
                    contentDescription =
                        "Cat",
                    tint =
                        TextGrey,
                    modifier =
                        Modifier.size(21.dp)
                )
            }
            Spacer(
                modifier =
                    Modifier.width(11.dp)
            )
            Column(
                modifier =
                    Modifier.weight(1f)
            ) {
                Text(
                    text =
                        chat.name,
                    color =
                        TextDark,
                    fontSize =
                        14.sp,
                    fontWeight =
                        FontWeight.Bold
                )
                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )
                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(
                                if (
                                    chat.online
                                ) {
                                    Color(0xFF63B87A)
                                } else {
                                    TextGrey
                                }
                            )
                    )
                    Spacer(
                        modifier =
                            Modifier.width(5.dp)
                    )
                    Text(
                        text =
                            if (
                                chat.online
                            ) {
                                "Online"
                            } else {
                                "Offline"
                            },
                        color =
                            TextGrey,
                        fontSize =
                            10.sp
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding =
                PaddingValues(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {
            items(
                messages
            ) { message ->
                MessageBubble(
                    message =
                        message
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    CardColor
                )
                .border(
                    width = 1.dp,
                    color = BorderColor
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 9.dp
                ),
            verticalAlignment =
                Alignment.Bottom
        ) {
            OutlinedTextField(
                value =
                    messageText,
                onValueChange = {
                    messageText = it
                },
                modifier =
                    Modifier.weight(1f),
                placeholder = {
                    Text(
                        text =
                            "Type a message...",
                        color =
                            TextGrey,
                        fontSize =
                            12.sp
                    )
                },
                singleLine =
                    false,
                maxLines =
                    4,
                shape =
                    RoundedCornerShape(22.dp),
                colors =
                    OutlinedTextFieldDefaults.colors(
                        focusedBorderColor =
                            Pink,
                        unfocusedBorderColor =
                            BorderColor,
                        focusedContainerColor =
                            BackgroundColor,
                        unfocusedContainerColor =
                            BackgroundColor,
                        focusedTextColor =
                            TextDark,
                        unfocusedTextColor =
                            TextDark,
                        cursorColor =
                            Pink
                    )
            )
            Spacer(
                modifier =
                    Modifier.width(8.dp)
            )
            Box(
                modifier = Modifier
                    .size(47.dp)
                    .clip(CircleShape)
                    .background(
                        if (
                            messageText.isNotBlank()
                        ) {
                            Pink
                        } else {
                            BorderColor
                        }
                    ),
                contentAlignment =
                    Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        if (
                            messageText.isNotBlank()
                        ) {
                            messages.add(
                                ChatMessage(
                                    text =
                                        messageText.trim(),
                                    isMine =
                                        true,
                                    time =
                                        "Now"
                                )
                            )
                            messageText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector =
                            Icons.Outlined.Send,
                        contentDescription =
                            "Send",
                        tint =
                            if (
                                messageText.isNotBlank()
                            ) {
                                Color.White
                            } else {
                                TextGrey
                            },
                        modifier =
                            Modifier.size(21.dp)
                    )
                }
            }
        }
    }
}
/* =========================================================
MESSAGE BUBBLE
========================================================= */
@Composable
fun MessageBubble(
    message: ChatMessage
) {
    Row(
        modifier =
            Modifier.fillMaxWidth(),
        horizontalArrangement =
            if (
                message.isMine
            ) {
                Arrangement.End
            } else {
                Arrangement.Start
            }
    ) {
        Column(
            horizontalAlignment =
                if (
                    message.isMine
                ) {
                    Alignment.End
                } else {
                    Alignment.Start
                }
        ) {
            Box(
                modifier = Modifier
                    .width(
                        if (
                            message.text.length > 35
                        ) {
                            270.dp
                        } else {
                            220.dp
                        }
                    )
                    .clip(
                        RoundedCornerShape(
                            topStart = 17.dp,
                            topEnd = 17.dp,
                            bottomStart =
                                if (
                                    message.isMine
                                ) {
                                    17.dp
                                } else {
                                    4.dp
                                },
                            bottomEnd =
                                if (
                                    message.isMine
                                ) {
                                    4.dp
                                } else {
                                    17.dp
                                }
                        )
                    )
                    .background(
                        if (
                            message.isMine
                        ) {
                            Pink
                        } else {
                            CardColor
                        }
                    )
                    .border(
                        width =
                            if (
                                message.isMine
                            ) {
                                0.dp
                            } else {
                                1.dp
                            },
                        color =
                            if (
                                message.isMine
                            ) {
                                Color.Transparent
                            } else {
                                BorderColor
                            },
                        shape =
                            RoundedCornerShape(
                                topStart = 17.dp,
                                topEnd = 17.dp,
                                bottomStart =
                                    if (
                                        message.isMine
                                    ) {
                                        17.dp
                                    } else {
                                        4.dp
                                    },
                                bottomEnd =
                                    if (
                                        message.isMine
                                    ) {
                                        4.dp
                                    } else {
                                        17.dp
                                    }
                            )
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 10.dp
                    )
            ) {
                Text(
                    text =
                        message.text,
                    color =
                        if (
                            message.isMine
                        ) {
                            Color.White
                        } else {
                            TextDark
                        },
                    fontSize =
                        12.sp,
                    lineHeight =
                        18.sp
                )
            }
            Spacer(
                modifier =
                    Modifier.height(3.dp)
            )
            Text(
                text =
                    message.time,
                color =
                    TextGrey,
                fontSize =
                    8.sp,
                modifier =
                    Modifier.padding(
                        horizontal = 4.dp
                    )
            )
        }
    }
}
/* =========================================================
MY CAT SCREEN
========================================================= */
@Composable
fun MyCatScreen(
    profile: CatProfile,
    onEditProfile: () -> Unit,
    onTabSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundColor
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            Box(
                modifier =
                    Modifier.fillMaxWidth(),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text =
                        "My Cat",
                    color =
                        TextDark,
                    fontSize =
                        16.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 18.dp
                    ),
                verticalAlignment =
                    Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(92.dp)
                        .clip(CircleShape)
                ) {
                    if (profile.photoBitmap != null) {
                        Image(
                            bitmap = profile.photoBitmap.asImageBitmap(),
                            contentDescription = "Cat photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(
                                id = R.drawable.simba
                            ),
                            contentDescription = "Cat photo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Spacer(
                    modifier =
                        Modifier.width(18.dp)
                )
                Column {
                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {
                        Text(
                            text =
                                profile.name,
                            color =
                                TextDark,
                            fontSize =
                                20.sp,
                            fontWeight =
                                FontWeight.Bold
                        )
                        Spacer(
                            modifier =
                                Modifier.width(5.dp)
                        )
                        Text(
                            text =
                                if (
                                    profile.gender ==
                                    "Female"
                                ) {
                                    "🐾"
                                } else {
                                    "🐾"
                                },
                            color =
                                if (
                                    profile.gender ==
                                    "Female"
                                ) {
                                    Pink
                                } else {
                                    Color(0xFF638BC7)
                                },
                            fontSize =
                                18.sp
                        )
                    }
                    Spacer(
                        modifier =
                            Modifier.height(3.dp)
                    )
                    Text(
                        text =
                            profile.breed,
                        color =
                            TextDark,
                        fontSize =
                            13.sp
                    )
                    Text(
                        text =
                            "${profile.age} years old",
                        color =
                            TextDark,
                        fontSize =
                            13.sp
                    )
                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(
                                    18.dp
                                )
                            )
                            .border(
                                1.dp,
                                BorderColor,
                                RoundedCornerShape(
                                    18.dp
                                )
                            )
                            .clickable {
                                onEditProfile()
                            }
                            .padding(
                                horizontal = 13.dp,
                                vertical = 6.dp
                            )
                    ) {
                        Text(
                            text =
                                "Edit Profile",
                            color =
                                TextDark,
                            fontSize =
                                11.sp
                        )
                    }
                }
            }
            ProfileSection(
                title =
                    "About ${profile.name}",
                content =
                    profile.about
            )
            InfoRow(
                title =
                    "Personality",
                value =
                    profile.personality,
                icon =
                    "_"
            )
            InfoRow(
                title =
                    "Favorite Activities",
                value =
                    profile.activities,
                icon =
                    "😊"
            )
            InfoRow(
                title =
                    "Health Info",
                value =
                    profile.health,
                icon =
                    "🐾"
            )
            InfoRow(
                title =
                    "Looking For",
                value =
                    profile.lookingFor,
                icon =
                    "❤"
            )
        }
        BottomNavigation(
            selectedTab =
                3,
            onTabSelected = {
                onTabSelected(it)
            }
        )
    }
}
/* =========================================================
EDIT CAT PROFILE
========================================================= */
@Composable
fun EditCatProfileScreen(
    profile: CatProfile,
    onBack: () -> Unit,
    onSave: (CatProfile) -> Unit
) {
    val context = LocalContext.current
    val photoScope = rememberCoroutineScope()
    var showPhotoOptions by remember {
        mutableStateOf(false)
    }
    var selectedPhotoBitmap by remember {
        mutableStateOf<Bitmap?>(profile.photoBitmap)
    }
    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                photoScope.launch(Dispatchers.IO) {
                    val bitmap = try {
                        context.contentResolver
                            .openInputStream(uri)
                            ?.use { input ->
                                BitmapFactory.decodeStream(input)
                            }
                    } catch (error: Exception) {
                        null
                    }
                    withContext(Dispatchers.Main) {
                        if (bitmap != null) {
                            selectedPhotoBitmap = bitmap
                        } else {
                            Toast.makeText(
                                context,
                                "Could not load selected photo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            if (bitmap != null) {
                selectedPhotoBitmap = bitmap
            }
        }
    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                cameraLauncher.launch(null)
            } else {
                Toast.makeText(
                    context,
                    "Camera permission is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    var name by remember {
        mutableStateOf(profile.name)
    }
    var gender by remember {
        mutableStateOf(profile.gender)
    }
    var breed by remember {
        mutableStateOf(profile.breed)
    }
    var age by remember {
        mutableStateOf(profile.age)
    }
    var about by remember {
        mutableStateOf(
            profile.about.replace(
                "\n",
                " "
            )
        )
    }
    var personality by remember {
        mutableStateOf(profile.personality)
    }
    var activities by remember {
        mutableStateOf(
            if (profile.activities.equals("null", ignoreCase = true) || profile.activities.isBlank()) {
                "Not added"
            } else {
                profile.activities
            }
        )
    }
    var health by remember {
        mutableStateOf(
            if (profile.health.equals("null", ignoreCase = true) || profile.health.isBlank()) {
                "Not added"
            } else {
                profile.health
            }
        )
    }
    var lookingFor by remember {
        mutableStateOf(
            if (profile.lookingFor.equals("null", ignoreCase = true) || profile.lookingFor.isBlank()) {
                "Not added"
            } else {
                profile.lookingFor
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                BackgroundColor
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .padding(
                    horizontal = 10.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {
            IconButton(
                onClick =
                    onBack
            ) {
                Icon(
                    imageVector =
                        Icons.Outlined.ArrowBack,
                    contentDescription =
                        "Back",
                    tint =
                        TextDark
                )
            }
            Box(
                modifier =
                    Modifier.weight(1f),
                contentAlignment =
                    Alignment.Center
            ) {
                Text(
                    text =
                        "Edit Cat Profile",
                    color =
                        TextDark,
                    fontSize =
                        16.sp,
                    fontWeight =
                        FontWeight.Bold
                )
            }
            Spacer(
                modifier =
                    Modifier.width(48.dp)
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding =
                PaddingValues(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 25.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(
                    modifier =
                        Modifier.fillMaxWidth(),
                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(105.dp)
                            .clip(CircleShape)
                            .clickable {
                                showPhotoOptions = true
                            }
                    ) {
                        if (selectedPhotoBitmap != null) {
                            Image(
                                bitmap = selectedPhotoBitmap!!.asImageBitmap(),
                                contentDescription = "Cat photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.simba
                                ),
                                contentDescription = "Cat photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )
                    Text(
                        text = "Change Cat Photo",
                        color = Pink,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable {
                            showPhotoOptions = true
                        }
                    )
                    if (showPhotoOptions) {
                        AlertDialog(
                            onDismissRequest = {
                                showPhotoOptions = false
                            },
                            title = { Text("Change Cat Photo") },
                            text = { Text("Choose Gallery or Camera to set your cat's DP.") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showPhotoOptions = false
                                        galleryLauncher.launch("image/*")
                                    }
                                ) {
                                    Text("Gallery")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = {
                                        showPhotoOptions = false
                                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                    }
                                ) {
                                    Text("Camera")
                                }
                            }
                        )
                    }
                }
            }
            item {
                EditField(
                    label =
                        "Cat Name",
                    value =
                        name,
                    onValueChange = {
                        name = it
                    }
                )
            }
            item {
                Text(
                    text =
                        "Gender",
                    color =
                        TextDark,
                    fontSize =
                        12.sp,
                    fontWeight =
                        FontWeight.SemiBold
                )
                Spacer(
                    modifier =
                        Modifier.height(7.dp)
                )
                Row(
                    horizontalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {
                    SelectionChip(
                        text =
                            "Male",
                        selected =
                            gender == "Male",
                        onClick = {
                            gender = "Male"
                        }
                    )
                    SelectionChip(
                        text =
                            "Female",
                        selected =
                            gender == "Female",
                        onClick = {
                            gender = "Female"
                        }
                    )
                }
            }
            item {
                EditField(
                    label =
                        "Breed",
                    value =
                        breed,
                    onValueChange = {
                        breed = it
                    }
                )
            }
            item {
                EditField(
                    label =
                        "Age",
                    value =
                        age,
                    onValueChange = {
                        age = it
                    },
                    keyboardType =
                        KeyboardType.Number
                )
            }
            item {
                EditField(
                    label =
                        "About",
                    value =
                        about,
                    onValueChange = {
                        about = it
                    },
                    singleLine =
                        false,
                    minLines =
                        4
                )
            }
            item {
                EditField(
                    label =
                        "Personality",
                    value =
                        personality,
                    onValueChange = {
                        personality = it
                    }
                )
            }
            item {
                EditField(
                    label =
                        "Favorite Activities",
                    value =
                        activities,
                    onValueChange = {
                        activities = it
                    }
                )
            }
            item {
                EditField(
                    label =
                        "Health Info",
                    value =
                        health,
                    onValueChange = {
                        health = it
                    }
                )
            }
            item {
                EditField(
                    label =
                        "Looking For",
                    value =
                        lookingFor,
                    onValueChange = {
                        lookingFor = it
                    }
                )
            }
            item {
                Button(
                    onClick = {
                        onSave(
                            CatProfile(
                                id = profile.id,
                                name =
                                    name.ifBlank {
                                        "My Cat"
                                    },
                                gender =
                                    gender,
                                breed =
                                    breed.ifBlank {
                                        "Breed not added"
                                    },
                                age =
                                    age.ifBlank {
                                        "0"
                                    },
                                about =
                                    about.ifBlank {
                                        "No information added yet."
                                    },
                                personality =
                                    personality.ifBlank {
                                        "Not added"
                                    },
                                activities =
                                    activities.ifBlank {
                                        "Not added"
                                    },
                                health =
                                    health.ifBlank {
                                        "Not added"
                                    },
                                lookingFor =
                                    lookingFor.ifBlank {
                                        "Not added"
                                    },
                                photoBitmap =
                                    selectedPhotoBitmap
                            )
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                    shape =
                        RoundedCornerShape(14.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                Pink
                        )
                ) {
                    Text(
                        text =
                            "Save Changes",
                        color =
                            Color.White,
                        fontSize =
                            14.sp,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }
    }
}
/* =========================================================
EDIT FIELD
========================================================= */
@Composable
fun EditField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType =
        KeyboardType.Text,
    singleLine: Boolean =
        true,
    minLines: Int =
        1
) {
    Column(
        modifier =
            Modifier.fillMaxWidth()
    ) {
        Text(
            text =
                label,
            color =
                TextDark,
            fontSize =
                12.sp,
            fontWeight =
                FontWeight.SemiBold
        )
        Spacer(
            modifier =
                Modifier.height(7.dp)
        )
        OutlinedTextField(
            value =
                value,
            onValueChange =
                onValueChange,
            modifier =
                Modifier.fillMaxWidth(),
            singleLine =
                singleLine,
            minLines =
                minLines,
            keyboardOptions =
                KeyboardOptions(
                    keyboardType =
                        keyboardType
                ),
            shape =
                RoundedCornerShape(12.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor =
                        Pink,
                    unfocusedBorderColor =
                        BorderColor,
                    focusedContainerColor =
                        CardColor,
                    unfocusedContainerColor =
                        CardColor,
                    focusedTextColor =
                        TextDark,
                    unfocusedTextColor =
                        TextDark,
                    cursorColor =
                        Pink
                )
        )
    }
}
/* =========================================================
SELECTION CHIP
========================================================= */
@Composable
fun SelectionChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(
                if (selected) {
                    LightPink
                } else {
                    CardColor
                }
            )
            .border(
                1.dp,
                if (selected) {
                    Pink
                } else {
                    BorderColor
                },
                RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
            }
            .padding(
                horizontal = 18.dp,
                vertical = 9.dp
            )
    ) {
        Text(
            text =
                text,
            color =
                if (selected) {
                    Pink
                } else {
                    TextDark
                },
            fontSize =
                12.sp,
            fontWeight =
                if (selected) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                }
        )
    }
}
/* =========================================================
PROFILE SECTION
========================================================= */
@Composable
fun ProfileSection(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 13.dp
            )
    ) {
        Text(
            text =
                title,
            color =
                TextDark,
            fontSize =
                13.sp,
            fontWeight =
                FontWeight.Bold
        )
        Spacer(
            modifier =
                Modifier.height(6.dp)
        )
        Text(
            text =
                content,
            color =
                TextGrey,
            fontSize =
                12.sp,
            lineHeight =
                18.sp
        )
    }
}
/* =========================================================
INFO ROW
========================================================= */
@Composable
fun InfoRow(
    title: String,
    value: String,
    icon: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 13.dp
            ),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Text(
            text =
                icon,
            color =
                Pink,
            fontSize =
                18.sp,
            modifier =
                Modifier.width(35.dp)
        )
        Text(
            text =
                title,
            color =
                TextDark,
            fontSize =
                11.sp,
            fontWeight =
                FontWeight.SemiBold,
            modifier =
                Modifier.width(125.dp)
        )
        Text(
            text =
                value,
            color =
                TextGrey,
            fontSize =
                10.sp
        )
    }
}
/* =========================================================
PROFILE TAG
========================================================= */
@Composable
fun ProfileTag(
    text: String
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                LightPink
            )
            .padding(
                horizontal = 11.dp,
                vertical = 5.dp
            )
    ) {
        Text(
            text =
                text,
            color =
                TextDark,
            fontSize =
                11.sp
        )
    }
}
/* =========================================================
ACTION BUTTON
========================================================= */
@Composable
fun ActionButton(
    icon:
    androidx.compose.ui.graphics.vector.ImageVector,
    iconColor:
    Color,
    size:
    Dp,
    onClick:
        () -> Unit
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Color.White
            )
            .border(
                1.dp,
                BorderColor,
                CircleShape
            ),
        contentAlignment =
            Alignment.Center
    ) {
        IconButton(
            onClick =
                onClick
        ) {
            Icon(
                imageVector =
                    icon,
                contentDescription =
                    null,
                tint =
                    iconColor,
                modifier =
                    Modifier.size(25.dp)
            )
        }
    }
}
/* =========================================================
SHARE BUTTON
========================================================= */
@Composable
fun ShareButton() {
    val context =
        LocalContext.current
    Box(
        modifier = Modifier
            .size(53.dp)
            .clip(CircleShape)
            .background(
                Color.White
            )
            .border(
                1.dp,
                BorderColor,
                CircleShape
            ),
        contentAlignment =
            Alignment.Center
    ) {
        IconButton(
            onClick = {
                val shareIntent =
                    Intent(
                        Intent.ACTION_SEND
                    ).apply {
                        type =
                            "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Check out Luna on Purr_Fect! 🐱❤️🐱"
                        )
                    }
                context.startActivity(
                    Intent.createChooser(
                        shareIntent,
                        "Share Luna"
                    )
                )
            }
        ) {
            Icon(
                imageVector =
                    Icons.Outlined.Share,
                contentDescription =
                    "Share",
                tint =
                    Purple,
                modifier =
                    Modifier.size(25.dp)
            )
        }
    }
}
/* =========================================================
BOTTOM NAVIGATION
========================================================= */
@Composable
fun BottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(
                CardColor
            )
            .padding(
                horizontal = 4.dp
            ),
        horizontalArrangement =
            Arrangement.SpaceEvenly,
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        NavigationItem(
            icon =
                Icons.Outlined.Pets,
            label =
                "Discover",
            selected =
                selectedTab == 0,
            onClick = {
                onTabSelected(0)
            }
        )
        NavigationItem(
            icon =
                Icons.Outlined.FavoriteBorder,
            label =
                "Matches",
            selected =
                selectedTab == 1,
            onClick = {
                onTabSelected(1)
            }
        )
        NavigationItem(
            icon =
                Icons.Outlined.ChatBubbleOutline,
            label =
                "Chats",
            selected =
                selectedTab == 2,
            onClick = {
                onTabSelected(2)
            }
        )
        NavigationItem(
            icon =
                Icons.Outlined.PersonOutline,
            label =
                "Profile",
            selected =
                selectedTab == 3,
            onClick = {
                onTabSelected(3)
            }
        )
    }
}
/* =========================================================
NAVIGATION ITEM
========================================================= */
@Composable
fun NavigationItem(
    icon:
    androidx.compose.ui.graphics.vector.ImageVector,
    label:
    String,
    selected:
    Boolean,
    onClick:
        () -> Unit
) {
    Column(
        modifier = Modifier
            .width(65.dp)
            .clickable(
                onClick =
                    onClick
            ),
        horizontalAlignment =
            Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.Center
    ) {
        Icon(
            imageVector =
                icon,
            contentDescription =
                label,
            tint =
                if (selected) {
                    Pink
                } else {
                    Color(0xFF81787B)
                },
            modifier =
                Modifier.size(21.dp)
        )
        Spacer(
            modifier =
                Modifier.height(2.dp)
        )
        Text(
            text =
                label,
            color =
                if (selected) {
                    Pink
                } else {
                    Color(0xFF81787B)
                },
            fontSize =
                9.sp,
            fontWeight =
                if (selected) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                }
        )
    }
}