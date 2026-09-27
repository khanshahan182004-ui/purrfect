package com.example.purr_fect
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Build
import android.widget.Toast
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.setContent
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.vector.ImageVector
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
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
private const val PURR_FECT_API_BASE_URL = "http://localhost:5000"
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
private const val SESSION_CAT_ID = "cat_id"

// =========================================================
// NOTIFICATION PREFERENCES
// Stored locally so the existing backend notification system
// remains unchanged. These preferences control what the app
// surfaces as system notifications.
// =========================================================
private const val PREF_NOTIFICATIONS_MASTER = "settings_notifications_enabled"
private const val PREF_NOTIFICATIONS_MATCHES = "settings_notifications_matches"
private const val PREF_NOTIFICATIONS_MESSAGES = "settings_notifications_messages"
private const val PREF_NOTIFICATIONS_LIKES = "settings_notifications_likes"
private const val PREF_NOTIFICATIONS_ADOPTION = "settings_notifications_adoption"
private const val PREF_NOTIFICATIONS_GENERAL = "settings_notifications_general"

// =========================================================
// PRIVACY PREFERENCES
// Online-status visibility is connected to the existing presence
// heartbeat. Other privacy controls are informational until the
// backend exposes matching server-side controls.
// =========================================================
private const val PREF_PRIVACY_ONLINE_STATUS = "privacy_online_status_enabled"

private fun notificationPreferences(context: Context) =
    context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)

private fun notificationCategoryEnabled(context: Context, type: String): Boolean {
    val prefs = notificationPreferences(context)
    if (!prefs.getBoolean(PREF_NOTIFICATIONS_MASTER, true)) return false

    return when {
        type.equals("match", ignoreCase = true) ->
            prefs.getBoolean(PREF_NOTIFICATIONS_MATCHES, true)
        type.equals("message", ignoreCase = true) ->
            prefs.getBoolean(PREF_NOTIFICATIONS_MESSAGES, true)
        type.equals("like", ignoreCase = true) ||
                type.equals("star", ignoreCase = true) ->
            prefs.getBoolean(PREF_NOTIFICATIONS_LIKES, true)
        type.startsWith("adoption", ignoreCase = true) ->
            prefs.getBoolean(PREF_NOTIFICATIONS_ADOPTION, true)
        else ->
            prefs.getBoolean(PREF_NOTIFICATIONS_GENERAL, true)
    }
}

private fun saveNotificationPreferences(
    context: Context,
    master: Boolean? = null,
    matches: Boolean? = null,
    messages: Boolean? = null,
    likes: Boolean? = null,
    adoption: Boolean? = null,
    general: Boolean? = null
) {
    notificationPreferences(context).edit().apply {
        master?.let { putBoolean(PREF_NOTIFICATIONS_MASTER, it) }
        matches?.let { putBoolean(PREF_NOTIFICATIONS_MATCHES, it) }
        messages?.let { putBoolean(PREF_NOTIFICATIONS_MESSAGES, it) }
        likes?.let { putBoolean(PREF_NOTIFICATIONS_LIKES, it) }
        adoption?.let { putBoolean(PREF_NOTIFICATIONS_ADOPTION, it) }
        general?.let { putBoolean(PREF_NOTIFICATIONS_GENERAL, it) }
    }.apply()
}

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
    private fun safeParseJson(responseText: String): JSONObject {
        if (responseText.isBlank()) return JSONObject()
        return try {
            JSONObject(responseText)
        } catch (e: org.json.JSONException) {
            val preview = if (responseText.length > 100) responseText.take(100) + "..." else responseText
            throw IllegalStateException("Failed to parse backend response as JSON. Received: $preview", e)
        }
    }

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
            val responseJson = safeParseJson(responseText)
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
            val responseJson = safeParseJson(responseText)
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
            val responseJson = safeParseJson(responseText)
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
            val responseJson = safeParseJson(responseText)
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
            val responseJson = safeParseJson(responseText)
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
    suspend fun forgotPassword(email: String): JSONObject {
        return postJson(
            "/api/forgot-password",
            JSONObject().apply {
                put("email", email.trim().lowercase(Locale.US))
            }
        )
    }

    suspend fun verifyResetOtp(email: String, otp: String): JSONObject {
        return postJson(
            "/api/verify-reset-otp",
            JSONObject().apply {
                put("email", email.trim().lowercase(Locale.US))
                put("otp", otp.trim())
            }
        )
    }

    suspend fun resetPassword(
        email: String,
        resetToken: String,
        newPassword: String
    ): JSONObject {
        return postJson(
            "/api/reset-password",
            JSONObject().apply {
                put("email", email.trim().lowercase(Locale.US))
                put("reset_token", resetToken.trim())
                put("new_password", newPassword)
            }
        )
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
                adoptionIntent = cleanCatText(
                    cat.optString("adoption_intent", null),
                    "none"
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
                            photoBitmap = photoBitmap,
                            distanceKm = if (cat.has("distance_km") && !cat.isNull("distance_km")) cat.optDouble("distance_km") else null
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
    suspend fun getAdoptionCats(
        referenceCatId: Int,
        search: String = ""
    ): List<AdoptionCat> {
        return try {
            val queryParts = mutableListOf<String>()
            if (referenceCatId > 0) queryParts += "cat_id=$referenceCatId"
            if (search.isNotBlank()) queryParts += "search=${java.net.URLEncoder.encode(search.trim(), "UTF-8")}"
            val path = if (queryParts.isEmpty()) {
                "/api/adoptions"
            } else {
                "/api/adoptions?${queryParts.joinToString("&")}"
            }
            val response = getJson(path)
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
                        AdoptionCat(
                            id = cat.optInt("id", 0),
                            name = cleanCatText(cat.optString("name", null), "Cat"),
                            gender = cleanCatText(cat.optString("gender", null), "Not added"),
                            breed = cleanCatText(cat.optString("breed", null), "Breed not added"),
                            age = cleanCatText(cat.optString("age", null), "0"),
                            about = cleanCatText(cat.optString("bio", null), "No information added yet."),
                            personality = cleanCatText(cat.optString("personality", null), "Not added"),
                            activities = cleanCatText(cat.optString("activities", null), "Not added"),
                            health = cleanCatText(cat.optString("health", null), "Not added"),
                            lookingFor = cleanCatText(cat.optString("looking_for", null), "Not added"),
                            distanceKm = if (cat.has("distance_km") && !cat.isNull("distance_km")) {
                                cat.optDouble("distance_km")
                            } else {
                                null
                            },
                            createdAt = cleanCatText(cat.optString("created_at", null), ""),
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
                            id = matchedCatId,
                            matchId = match.optInt("match_id", 0),
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
                            online = match.optBoolean("online", false),
                            lastSeenLabel = match.optString("last_seen_label", "Last seen unavailable"),
                            distance = match.opt("distance_km")
                                .takeIf { it != null && it != JSONObject.NULL }
                                ?.let {
                                    val km = (it as Number).toDouble()
                                    if (km < 1.0) {
                                        String.format("%.0f m", km * 1000.0)
                                    } else {
                                        String.format("%.1f km", km)
                                    }
                                }
                                ?: "Distance unavailable",
                            about = cleanCatText(
                                match.optString("bio", null),
                                matchedCat?.about ?: "No information added yet."
                            ),
                            imageRes = R.drawable.signcat,
                            isNewMatch = match.optBoolean("is_new_match", false),
                            likedYou = match.optBoolean("liked_you", false),
                            photoBitmap = matchedCat?.photoBitmap
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun updatePresence(catId: Int, status: String): Boolean {
        if (catId <= 0) return false
        return try {
            postJson("/api/cats/$catId/presence", JSONObject().apply { put("status", status) })
            true
        } catch (_: Exception) { false }
    }

    suspend fun sendPresenceHeartbeat(catId: Int): Boolean {
        if (catId <= 0) return false
        return try {
            postJson("/api/cats/$catId/presence/heartbeat", JSONObject())
            true
        } catch (_: Exception) { false }
    }

    suspend fun getPresence(catId: Int): JSONObject? {
        if (catId <= 0) return null
        return try { getJson("/api/cats/$catId/presence").optJSONObject("presence") }
        catch (_: Exception) { null }
    }

    suspend fun getChatList(catId: Int): List<ChatItem> {
        if (catId <= 0) return emptyList()
        return try {
            val response = getJson("/api/cats/$catId/chat-list")
            val chats = response.optJSONArray("chats") ?: return emptyList()
            buildList {
                for (index in 0 until chats.length()) {
                    val chat = chats.optJSONObject(index) ?: continue
                    add(
                        ChatItem(
                            matchId = chat.optInt("match_id", 0),
                            otherCatId = chat.optInt("other_cat_id", 0),
                            name = chat.optString("other_cat_name", "Cat"),
                            message = chat.optString("last_message", "No messages yet"),
                            time = chat.optString("last_message_time", ""),
                            unread = chat.optInt("unread_count", 0),
                            online = chat.optBoolean("other_cat_online", false),
                            lastSeenLabel = chat.optString("other_cat_last_seen_label", "Last seen unavailable"),
                            photoUrl = chat.optString("other_cat_photo", null)
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun getMessages(matchId: Int, currentCatId: Int): List<ChatMessage> {
        if (matchId <= 0) return emptyList()
        return try {
            val response = getJson("/api/matches/$matchId/messages")
            val messages = response.optJSONArray("messages") ?: return emptyList()
            buildList {
                for (index in 0 until messages.length()) {
                    val msg = messages.optJSONObject(index) ?: continue
                    val senderId = msg.optInt("sender_cat_id", 0)
                    val rawMessage = msg.optString("message", "")
                    if (rawMessage.startsWith("[[FILE|") && rawMessage.endsWith("]]")) {
                        val payload = rawMessage.removePrefix("[[FILE|").removeSuffix("]]" ).split("|", limit = 4)
                        if (payload.size == 3) {
                            add(
                                ChatMessage(
                                    text = payload[0],
                                    isMine = senderId == currentCatId,
                                    time = msg.optString("created_at", ""),
                                    attachmentName = payload[0],
                                    attachmentMime = payload[1],
                                    attachmentUrl = payload[2]
                                )
                            )
                            continue
                        }
                    }
                    add(
                        ChatMessage(
                            text = rawMessage,
                            isMine = senderId == currentCatId,
                            time = msg.optString("created_at", "")
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun sendMessage(
        matchId: Int,
        senderCatId: Int,
        receiverCatId: Int,
        message: String
    ): JSONObject {
        return postJson(
            "/api/matches/$matchId/messages",
            JSONObject().apply {
                put("sender_cat_id", senderCatId)
                put("receiver_cat_id", receiverCatId)
                put("message", message)
            }
        )
    }

    suspend fun markAsRead(matchId: Int, catId: Int): JSONObject {
        return postJson(
            "/api/matches/$matchId/messages/read",
            JSONObject().apply {
                put("cat_id", catId)
            }
        )
    }

    suspend fun uploadChatFile(
        context: Context,
        matchId: Int,
        senderCatId: Int,
        uri: Uri
    ): JSONObject {
        val resolver = context.contentResolver
        val mimeType = resolver.getType(uri) ?: "application/octet-stream"
        val fileName = uri.lastPathSegment?.substringAfterLast('/')?.takeIf { it.isNotBlank() } ?: "attachment"
        val cleanFileName = fileName.replace("\"", "_")
        val bytes = resolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalStateException("Unable to read selected file")
        if (bytes.size > 5 * 1024 * 1024) {
            throw IllegalStateException("File is too large. Maximum size is 5 MB")
        }
        val boundary = "----PurrFectBoundary${UUID.randomUUID()}"
        val url = URL("$PURR_FECT_API_BASE_URL/api/matches/$matchId/files")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 30000
            readTimeout = 30000
            doInput = true
            doOutput = true
            useCaches = false
            setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
            setRequestProperty("Accept", "application/json")
        }
        try {
            connection.outputStream.use { output ->
                fun writeText(value: String) { output.write(value.toByteArray(Charsets.UTF_8)) }
                writeText("--$boundary\r\n")
                writeText("Content-Disposition: form-data; name=\"sender_cat_id\"\r\n\r\n")
                writeText(senderCatId.toString())
                writeText("\r\n--$boundary\r\n")
                writeText("Content-Disposition: form-data; name=\"file\"; filename=\"$cleanFileName\"\r\n")
                writeText("Content-Type: $mimeType\r\n\r\n")
                output.write(bytes)
                writeText("\r\n--$boundary--\r\n")
            }
            val responseCode = connection.responseCode
            val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
            val responseText = BufferedReader(InputStreamReader(stream)).use { it.readText() }
            val responseJson = JSONObject(responseText)
            if (responseCode !in 200..299) {
                throw IllegalStateException(responseJson.optString("message", "File upload failed"))
            }
            return responseJson
        } finally {
            connection.disconnect()
        }
    }

    suspend fun requestPhoneNumber(requesterCatId: Int, requestedCatId: Int): JSONObject =
        postJson("/api/cats/$requesterCatId/phone-request", JSONObject().apply {
            put("requested_cat_id", requestedCatId)
        })

    suspend fun reportUser(reporterCatId: Int, reportedCatId: Int, reason: String): JSONObject =
        postJson("/api/cats/$reporterCatId/report", JSONObject().apply {
            put("reported_cat_id", reportedCatId)
            put("reason", reason)
        })

    suspend fun blockUser(blockerCatId: Int, blockedCatId: Int): JSONObject =
        postJson("/api/cats/$blockerCatId/block", JSONObject().apply {
            put("blocked_cat_id", blockedCatId)
        })

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
        return try {
            val response = getJson("/api/cats/$catId/starred")
            val array = response.optJSONArray("starred_cats") ?: return emptyList()
            buildList {
                for (i in 0 until array.length()) {
                    val item = array.optJSONObject(i) ?: continue
                    var photoUrl: String? = item.optString("photo_url", "").takeIf { it.isNotBlank() }
                    val photos = item.optJSONArray("photos")
                    if (photoUrl == null && photos != null && photos.length() > 0) {
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
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun getNotifications(catId: Int): List<NotificationItem> {
        if (catId <= 0) return emptyList()
        return try {
            val response = getJson("/api/cats/$catId/notifications?limit=100")
            val array = response.optJSONArray("notifications") ?: return emptyList()
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    add(
                        NotificationItem(
                            id = item.optInt("id", 0),
                            type = item.optString("type", "general"),
                            title = item.optString("title", "Notification"),
                            message = item.optString("message", ""),
                            relatedCatId = if (item.has("related_cat_id") && !item.isNull("related_cat_id")) item.optInt("related_cat_id") else null,
                            relatedMatchId = if (item.has("related_match_id") && !item.isNull("related_match_id")) item.optInt("related_match_id") else null,
                            isRead = item.optBoolean("is_read", false),
                            createdAt = item.optString("created_at", "")
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    suspend fun getNotificationUnreadCount(catId: Int): Int {
        if (catId <= 0) return 0
        return try {
            getJson("/api/cats/$catId/notifications/unread-count")
                .optInt("unread_count", 0)
        } catch (_: Exception) {
            0
        }
    }

    suspend fun markNotificationRead(catId: Int, notificationId: Int): Boolean {
        if (catId <= 0 || notificationId <= 0) return false
        return try {
            postJson(
                "/api/cats/$catId/notifications/$notificationId/read",
                JSONObject()
            )
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun markAllNotificationsRead(catId: Int): Boolean {
        if (catId <= 0) return false
        return try {
            postJson(
                "/api/cats/$catId/notifications/read-all",
                JSONObject()
            )
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun deleteNotification(catId: Int, notificationId: Int): Boolean {
        if (catId <= 0 || notificationId <= 0) return false
        return try {
            deleteJson("/api/cats/$catId/notifications/$notificationId")
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun getCatProfilesByUserId(userId: Int): List<CatProfile> {
        return try {
            val response = getJson("/api/users/$userId/cats")
            val array = response.optJSONArray("cats") ?: return emptyList()
            buildList {
                for (index in 0 until array.length()) {
                    val cat = array.optJSONObject(index) ?: continue
                    val photos = cat.optJSONArray("photos")
                    val photoUrl = findPrimaryPhotoUrl(photos)
                    val photoBitmap = if (!photoUrl.isNullOrBlank()) downloadBitmap(photoUrl) else null
                    add(
                        CatProfile(
                            id = cat.optInt("id", 0),
                            name = cleanCatText(cat.optString("name", null), "My Cat"),
                            gender = cleanCatText(cat.optString("gender", null), "Male"),
                            breed = cleanCatText(cat.optString("breed", null), "Breed not added"),
                            age = cleanCatText(cat.optString("age", null), "0"),
                            about = cleanCatText(cat.optString("bio", null), "No information added yet."),
                            personality = cleanCatText(cat.optString("personality", null), "Not added"),
                            activities = cleanCatText(cat.optString("activities", null), "Not added"),
                            health = cleanCatText(cat.optString("health", null), "Not added"),
                            lookingFor = cleanCatText(cat.optString("looking_for", null), "Not added"),
                            photoUrl = photoUrl,
                            photoBitmap = photoBitmap,
                            adoptionIntent = cleanCatText(cat.optString("adoption_intent", null), "none")
                        )
                    )
                }
            }
        } catch (_: Exception) {
            emptyList()
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

    suspend fun updateAdoptionSettings(catId: Int, adoptionIntent: String): JSONObject {
        if (catId <= 0) throw IllegalStateException("Cat ID is missing")
        return putJson(
            "/api/cats/$catId/adoption-settings",
            JSONObject().apply { put("adoption_intent", adoptionIntent) }
        )
    }

    suspend fun createAdoptionRequest(
        catId: Int,
        adopterCatId: Int,
        message: String
    ): JSONObject {
        if (catId <= 0) throw IllegalStateException("Adoption cat ID is missing")
        if (adopterCatId <= 0) throw IllegalStateException("Your cat ID is missing")
        return postJson(
            "/api/adoptions/$catId/requests",
            JSONObject().apply {
                put("adopter_cat_id", adopterCatId)
                put("message", message.trim().take(1000))
            }
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
            lookingFor = cleanCatText(cat.optString("looking_for", null), profile.lookingFor),
            adoptionIntent = cleanCatText(cat.optString("adoption_intent", null), profile.adoptionIntent)
        )
    }
}
private const val PURR_FECT_NOTIFICATION_CHANNEL_ID = "purrfect_notifications"
private const val PURR_FECT_NOTIFICATION_CHANNEL_NAME = "PurrFect Notifications"

private fun createPurrFectNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channel = NotificationChannel(
        PURR_FECT_NOTIFICATION_CHANNEL_ID,
        PURR_FECT_NOTIFICATION_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply {
        description = "Likes, matches and messages from PurrFect"
    }
    manager.createNotificationChannel(channel)
}

private fun showPurrFectSystemNotification(
    context: Context,
    notification: NotificationItem
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    createPurrFectNotificationChannel(context)

    val launchIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val pendingIntent = android.app.PendingIntent.getActivity(
        context,
        notification.id,
        launchIntent,
        android.app.PendingIntent.FLAG_UPDATE_CURRENT or
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    android.app.PendingIntent.FLAG_IMMUTABLE
                } else {
                    0
                }
    )

    val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        android.app.Notification.Builder(context, PURR_FECT_NOTIFICATION_CHANNEL_ID)
    } else {
        android.app.Notification.Builder(context)
    }

    val systemNotification = builder
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(notification.title)
        .setContentText(notification.message)
        .setStyle(android.app.Notification.BigTextStyle().bigText(notification.message))
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .build()

    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.notify(notification.id, systemNotification)
}

class MainActivity : ComponentActivity() {
    private val presenceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var presenceJob: kotlinx.coroutines.Job? = null

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createPurrFectNotificationChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) !=
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        setContent { PurrFectApp() }
    }

    override fun onStart() {
        super.onStart()
        startPresenceTracking()
    }

    override fun onStop() {
        stopPresenceTracking()
        super.onStop()
    }

    override fun onDestroy() {
        presenceScope.cancel()
        super.onDestroy()
    }

    private fun currentCatId(): Int =
        getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
            .getInt(SESSION_CAT_ID, 0)

    private fun startPresenceTracking() {
        presenceJob?.cancel()
        presenceJob = presenceScope.launch {
            while (true) {
                val catId = currentCatId()
                val onlineStatusEnabled = getSharedPreferences(
                    PURR_FECT_SESSION_PREFS,
                    Context.MODE_PRIVATE
                ).getBoolean(PREF_PRIVACY_ONLINE_STATUS, true)
                if (catId > 0 && onlineStatusEnabled) {
                    PurrFectApi.updatePresence(catId, "online")
                    delay(30_000L)
                    if (getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                            .getBoolean(PREF_PRIVACY_ONLINE_STATUS, true)) {
                        PurrFectApi.sendPresenceHeartbeat(catId)
                    }
                } else if (catId > 0) {
                    PurrFectApi.updatePresence(catId, "offline")
                    delay(2_000L)
                } else {
                    delay(2_000L)
                }
            }
        }
    }

    private fun stopPresenceTracking() {
        presenceJob?.cancel()
        presenceJob = null
        // Do not write a fresh timestamp when the app stops.
        // The backend will naturally mark the user offline when the heartbeat expires.

    }
}
/* =========================================================
DARK MODE STATE / THEME HELPER
========================================================= */
private var purrFectDarkMode by mutableStateOf(false)

private fun purrFectColor(light: Color, dark: Color): Color =
    if (purrFectDarkMode) dark else light

/* =========================================================
COLORS
========================================================= */
private val BackgroundColor: Color get() = purrFectColor(Color(0xFFFFF8F4), Color(0xFF151326))
private val CardColor: Color get() = purrFectColor(Color(0xFFFFFCFA), Color(0xFF211E35))
private val Pink: Color get() = purrFectColor(Color(0xFFE95D78), Color(0xFFF45A9A))
private val Purple: Color get() = purrFectColor(Color(0xFF8057C7), Color(0xFF9A63DC))
private val TextDark: Color get() = purrFectColor(Color(0xFF292329), Color(0xFFF7F3FA))
private val TextGrey: Color get() = purrFectColor(Color(0xFF756D70), Color(0xFFA8A3B5))
private val LightPink: Color get() = purrFectColor(Color(0xFFFFE9EC), Color(0xFF211E35))
private val BorderColor: Color get() = purrFectColor(Color(0xFFEDE4E0), Color(0xFF3A344D))
private val StarYellow: Color get() = purrFectColor(Color(0xFFFFC107), Color(0xFFF4C95B))
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
    val photoBitmap: Bitmap? = null,
    val distanceKm: Double? = null,
    val adoptionIntent: String = "none"
)
/* =========================================================
ADOPTION DATA
========================================================= */
data class AdoptionCat(
    val id: Int = 0,
    val name: String = "Cat",
    val gender: String = "Not added",
    val breed: String = "Breed not added",
    val age: String = "0",
    val about: String = "No information added yet.",
    val personality: String = "Not added",
    val activities: String = "Not added",
    val health: String = "Not added",
    val lookingFor: String = "Not added",
    val distanceKm: Double? = null,
    val createdAt: String = "",
    val photoUrl: String? = null,
    val photoBitmap: Bitmap? = null
)

/* =========================================================
CHAT DATA
========================================================= */
data class ChatItem(
    val matchId: Int = 0,
    val otherCatId: Int = 0,
    val name: String,
    val message: String,
    val time: String,
    val unread: Int = 0,
    val online: Boolean = false,
    val lastSeenLabel: String = "Last seen unavailable",
    val photoUrl: String? = null
)
/* =========================================================
MATCH DATA
========================================================= */
data class MatchItem(
    val id: Int = 0,
    val matchId: Int = 0,
    val name: String,
    val gender: String,
    val age: String,
    val breed: String,
    val distance: String,
    val about: String,
    val imageRes: Int,
    val online: Boolean = false,
    val lastSeenLabel: String = "Last seen unavailable",
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
    val time: String,
    val attachmentName: String? = null,
    val attachmentUrl: String? = null,
    val attachmentMime: String? = null
)

/* =========================================================
NOTIFICATION DATA
========================================================= */
data class NotificationItem(
    val id: Int,
    val type: String,
    val title: String,
    val message: String,
    val relatedCatId: Int? = null,
    val relatedMatchId: Int? = null,
    val isRead: Boolean = false,
    val createdAt: String = ""
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
    var selectedAdoptionCat by remember {
        mutableStateOf<AdoptionCat?>(null)
    }
    var showMatchPopup by remember {
        mutableStateOf(false)
    }
    var matchedPopupCat by remember {
        mutableStateOf<CatProfile?>(null)
    }
    var matchItems by remember {
        mutableStateOf<List<MatchItem>>(emptyList())
    }

    var notificationItems by remember {
        mutableStateOf<List<NotificationItem>>(emptyList())
    }
    var notificationUnreadCount by remember {
        mutableIntStateOf(0)
    }
    var catProfile by remember {
        mutableStateOf(CatProfile())
    }
    var loggedInUser by remember {
        mutableStateOf<BackendUser?>(savedUser)
    }
    var settingsNotificationsEnabled by remember {
        mutableStateOf(
            notificationPreferences(context)
                .getBoolean(PREF_NOTIFICATIONS_MASTER, true)
        )
    }
    var notificationMatchesEnabled by remember {
        mutableStateOf(notificationPreferences(context).getBoolean(PREF_NOTIFICATIONS_MATCHES, true))
    }
    var notificationMessagesEnabled by remember {
        mutableStateOf(notificationPreferences(context).getBoolean(PREF_NOTIFICATIONS_MESSAGES, true))
    }
    var notificationLikesEnabled by remember {
        mutableStateOf(notificationPreferences(context).getBoolean(PREF_NOTIFICATIONS_LIKES, true))
    }
    var notificationAdoptionEnabled by remember {
        mutableStateOf(notificationPreferences(context).getBoolean(PREF_NOTIFICATIONS_ADOPTION, true))
    }
    var notificationGeneralEnabled by remember {
        mutableStateOf(notificationPreferences(context).getBoolean(PREF_NOTIFICATIONS_GENERAL, true))
    }
    var settingsDarkModeEnabled by remember {
        mutableStateOf(
            context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                .getBoolean("settings_dark_mode_enabled", false)
        )
    }
    var settingsDistanceUnit by remember {
        mutableStateOf(
            context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                .getString("settings_distance_unit", "Kilometres (km)")
                ?: "Kilometres (km)"
        )
    }
    var settingsLanguage by remember {
        mutableStateOf(
            context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                .getString("settings_language", "English")
                ?: "English"
        )
    }
    var privacyOnlineStatusEnabled by remember {
        mutableStateOf(
            context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                .getBoolean(PREF_PRIVACY_ONLINE_STATUS, true)
        )
    }

    LaunchedEffect(catProfile.id) {
        if (catProfile.id > 0) {
            context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                .edit().putInt(SESSION_CAT_ID, catProfile.id).apply()
        }
    }


    LaunchedEffect(settingsDarkModeEnabled) {
        purrFectDarkMode = settingsDarkModeEnabled
    }

    val scope = rememberCoroutineScope()

    suspend fun handleLikeConfirmed(likedCatId: Int) {
        val response = PurrFectApi.likeCat(
            likerCatId = catProfile.id,
            likedCatId = likedCatId
        )
        val matchObject = response.optJSONObject("match")

        if (matchObject != null) {
            matchedPopupCat = PurrFectApi.getCatById(likedCatId)
            showMatchPopup = matchedPopupCat != null
        } else {
            val message = response.optString("message")
            Toast.makeText(
                context,
                if (message.isNotBlank()) message else "Cat liked successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (catProfile.id > 0) {
            matchItems = PurrFectApi.getMatches(catProfile.id)
        }
    }

// Load the logged-in user's real cat profile from the backend.
// The existing UI stays the same; only the data source becomes real.
    LaunchedEffect(loggedInUser?.id) {
        val userId = loggedInUser?.id ?: return@LaunchedEffect
        val backendCat = PurrFectApi.getCatByUserId(userId)
        if (backendCat != null) {
            catProfile = backendCat
        }
    }
// Load real matches for the logged-in/current cat from PostgreSQL.
    LaunchedEffect(catProfile.id) {
        if (catProfile.id > 0) {
            matchItems = PurrFectApi.getMatches(catProfile.id)
            starredCats = PurrFectApi.getStarredCats(catProfile.id)
            notificationItems = PurrFectApi.getNotifications(catProfile.id)
            notificationUnreadCount = PurrFectApi.getNotificationUnreadCount(catProfile.id)
        } else {
            starredCats = emptyList()
            notificationItems = emptyList()
            notificationUnreadCount = 0
        }
    }
    LaunchedEffect(catProfile.id) {
        if (catProfile.id > 0) {
            var knownNotificationIds =
                PurrFectApi.getNotifications(catProfile.id)
                    .map { it.id }
                    .toSet()

            while (true) {
                delay(10_000L)
                val latestNotifications =
                    PurrFectApi.getNotifications(catProfile.id)

                val newNotifications = latestNotifications.filter { item ->
                    item.id > 0 &&
                            !item.isRead &&
                            item.id !in knownNotificationIds
                }

                newNotifications
                    .filter { item -> notificationCategoryEnabled(context, item.type) }
                    .take(5)
                    .forEach { item ->
                        showPurrFectSystemNotification(context, item)
                    }

                notificationItems = latestNotifications
                notificationUnreadCount =
                    PurrFectApi.getNotificationUnreadCount(catProfile.id)
                knownNotificationIds = latestNotifications.map { it.id }.toSet()
            }
        }
    }

    AnimatedContent(
        targetState = currentPage,
        transitionSpec = {
            val forwardPages = setOf("welcome", "signup", "login", "edit", "catProfile", "compatibility", "chat", "starred", "adoption", "adoptionDetails", "about", "helpSupport")
            val isForward = targetState in forwardPages && targetState != initialState

            if (isForward) {
                (slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth / 6 },
                    animationSpec = tween(280)
                ) + fadeIn(animationSpec = tween(280))) togetherWith
                        (slideOutHorizontally(
                            targetOffsetX = { fullWidth -> -fullWidth / 10 },
                            animationSpec = tween(220)
                        ) + fadeOut(animationSpec = tween(220)))
            } else {
                (slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth / 10 },
                    animationSpec = tween(280)
                ) + fadeIn(animationSpec = tween(280))) togetherWith
                        (slideOutHorizontally(
                            targetOffsetX = { fullWidth -> fullWidth / 6 },
                            animationSpec = tween(220)
                        ) + fadeOut(animationSpec = tween(220)))
            }
        },
        label = "screen_transition"
    ) { page ->
        when (page) {
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
                                handleLikeConfirmed(likedCatId)
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
                                    matchId = match.matchId,
                                    otherCatId = match.id,
                                    name = match.name,
                                    message = "You matched with ${match.name}! 🐱❤️🐱",
                                    time = "Now",
                                    unread = 0,
                                    online = match.online,
                                    lastSeenLabel = match.lastSeenLabel
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
                            currentCatId = catProfile.id,
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
                            onSettingsClick = {
                                currentPage = "settings"
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
                                handleLikeConfirmed(likedCatId)
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
            "settings" -> {
                SettingsScreen(
                    profile = catProfile,
                    user = loggedInUser,
                    onBack = {
                        currentPage = "main"
                        selectedTab = 3
                    },
                    onEditProfile = {
                        currentPage = "edit"
                    },
                    onMyCatProfiles = {
                        currentPage = "myCatProfiles"
                    },
                    onAccountSecurity = {
                        currentPage = "accountSecurity"
                    },
                    notificationsEnabled = settingsNotificationsEnabled,
                    onNotificationsEnabledChange = { enabled ->
                        settingsNotificationsEnabled = enabled
                        saveNotificationPreferences(context, master = enabled)
                        if (!enabled) {
                            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            manager.cancelAll()
                        }
                    },
                    darkModeEnabled = settingsDarkModeEnabled,
                    onDarkModeEnabledChange = { enabled ->
                        settingsDarkModeEnabled = enabled
                        context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                            .edit()
                            .putBoolean("settings_dark_mode_enabled", enabled)
                            .apply()
                    },
                    distanceUnit = settingsDistanceUnit,
                    onDistanceUnitChange = { unit ->
                        settingsDistanceUnit = unit
                        context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                            .edit()
                            .putString("settings_distance_unit", unit)
                            .apply()
                    },
                    language = settingsLanguage,
                    onLanguageChange = { language ->
                        settingsLanguage = language
                        context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                            .edit()
                            .putString("settings_language", language)
                            .apply()
                    },
                    onNotificationsPage = {
                        currentPage = "notificationSettings"
                    },
                    onPrivacyPage = {
                        currentPage = "privacy"
                    },
                    onHelpSupportPage = {
                        currentPage = "helpSupport"
                    },
                    onAboutPage = {
                        currentPage = "about"
                    },
                    onLogout = {
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
                        currentPage = "welcome"
                        selectedTab = 0
                    }
                )
            }
            "myCatProfiles" -> {
                var myCatProfiles by remember { mutableStateOf<List<CatProfile>>(emptyList()) }
                LaunchedEffect(loggedInUser?.id) {
                    val userId = loggedInUser?.id ?: 0
                    if (userId > 0) {
                        myCatProfiles = PurrFectApi.getCatProfilesByUserId(userId)
                    } else {
                        myCatProfiles = emptyList()
                    }
                }

                MyCatProfilesScreen(
                    cats = myCatProfiles,
                    activeCatId = catProfile.id,
                    onBack = { currentPage = "settings" },
                    onAddCat = {
                        catProfile = CatProfile(
                            id = 0, name = "", gender = "", breed = "", age = "",
                            about = "", personality = "", activities = "", health = "", lookingFor = ""
                        )
                        currentPage = "edit"
                    },
                    onSelectCat = { selected ->
                        catProfile = selected
                        context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                            .edit().putInt(SESSION_CAT_ID, selected.id).apply()
                        currentPage = "main"
                        selectedTab = 3
                    },
                    onEditCat = { selected ->
                        catProfile = selected
                        currentPage = "edit"
                    }
                )
            }
            "privacy" -> {
                PrivacyScreen(
                    onlineStatusEnabled = privacyOnlineStatusEnabled,
                    onOnlineStatusChange = { enabled ->
                        privacyOnlineStatusEnabled = enabled
                        context.getSharedPreferences(PURR_FECT_SESSION_PREFS, Context.MODE_PRIVATE)
                            .edit()
                            .putBoolean(PREF_PRIVACY_ONLINE_STATUS, enabled)
                            .apply()
                        if (!enabled && catProfile.id > 0) {
                            scope.launch {
                                PurrFectApi.updatePresence(catProfile.id, "offline")
                            }
                        }
                    },
                    onAccountSecurity = { currentPage = "accountSecurity" },
                    onBack = { currentPage = "settings" }
                )
            }
            "helpSupport" -> {
                HelpSupportScreen(
                    onBack = { currentPage = "settings" }
                )
            }
            "about" -> {
                AboutPurrFectScreen(
                    onBack = { currentPage = "settings" }
                )
            }
            "accountSecurity" -> {
                AccountSecurityScreen(
                    user = loggedInUser,
                    onBack = { currentPage = "settings" },
                    onLogout = {
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
                        currentPage = "welcome"
                        selectedTab = 0
                    }
                )
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
                            imageRes = R.drawable.signcat,
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
                            handleLikeConfirmed(likedCatId)
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

                                if (savedProfile.id > 0) {
                                    PurrFectApi.updateAdoptionSettings(
                                        catId = savedProfile.id,
                                        adoptionIntent = updatedProfile.adoptionIntent
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
                                catProfile = if (wasCreating) {
                                    savedProfile
                                } else {
                                    PurrFectApi.getCatByUserId(
                                        loggedInUser?.id ?: 0
                                    ) ?: savedProfile
                                }
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
                        currentCatId = catProfile.id,
                        chat = chat,
                        onBack = {
                            currentPage = "main"
                            selectedTab = 2
                        }
                    )
                }
            }
            "notificationSettings" -> {
                NotificationSettingsScreen(
                    notificationsEnabled = settingsNotificationsEnabled,
                    matchesEnabled = notificationMatchesEnabled,
                    messagesEnabled = notificationMessagesEnabled,
                    likesEnabled = notificationLikesEnabled,
                    adoptionEnabled = notificationAdoptionEnabled,
                    generalEnabled = notificationGeneralEnabled,
                    onMasterNotificationChange = { enabled ->
                        settingsNotificationsEnabled = enabled
                        saveNotificationPreferences(context, master = enabled)
                        if (!enabled) {
                            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            manager.cancelAll()
                        }
                    },
                    onMatchesChange = { enabled ->
                        notificationMatchesEnabled = enabled
                        saveNotificationPreferences(context, matches = enabled)
                    },
                    onMessagesChange = { enabled ->
                        notificationMessagesEnabled = enabled
                        saveNotificationPreferences(context, messages = enabled)
                    },
                    onLikesChange = { enabled ->
                        notificationLikesEnabled = enabled
                        saveNotificationPreferences(context, likes = enabled)
                    },
                    onAdoptionChange = { enabled ->
                        notificationAdoptionEnabled = enabled
                        saveNotificationPreferences(context, adoption = enabled)
                    },
                    onGeneralChange = { enabled ->
                        notificationGeneralEnabled = enabled
                        saveNotificationPreferences(context, general = enabled)
                    },
                    onBack = { currentPage = "settings" }
                )
            }
            "notifications" -> {
                NotificationsScreen(
                    notifications = notificationItems,
                    unreadCount = notificationUnreadCount,
                    onBack = { currentPage = "main" },
                    onMarkRead = { notificationId ->
                        scope.launch {
                            if (PurrFectApi.markNotificationRead(catProfile.id, notificationId)) {
                                notificationItems = PurrFectApi.getNotifications(catProfile.id)
                                notificationUnreadCount = PurrFectApi.getNotificationUnreadCount(catProfile.id)
                            }
                        }
                    },
                    onMarkAllRead = {
                        scope.launch {
                            if (PurrFectApi.markAllNotificationsRead(catProfile.id)) {
                                notificationItems = PurrFectApi.getNotifications(catProfile.id)
                                notificationUnreadCount = PurrFectApi.getNotificationUnreadCount(catProfile.id)
                            }
                        }
                    },
                    onDelete = { notificationId ->
                        scope.launch {
                            if (PurrFectApi.deleteNotification(catProfile.id, notificationId)) {
                                notificationItems = notificationItems.filterNot { it.id == notificationId }
                                notificationUnreadCount = PurrFectApi.getNotificationUnreadCount(catProfile.id)
                            }
                        }
                    }
                )
            }
            "adoption" -> {
                AdoptionScreen(
                    referenceCatId = catProfile.id,
                    onBack = {
                        currentPage = "main"
                    },
                    onCatClick = { cat ->
                        selectedAdoptionCat = cat
                        currentPage = "adoptionDetails"
                    }
                )
            }
            "adoptionDetails" -> {
                selectedAdoptionCat?.let { cat ->
                    AdoptionDetailsScreen(
                        cat = cat,
                        currentCatId = catProfile.id,
                        onBack = {
                            currentPage = "adoption"
                        }
                    )
                }
            }
        }

    }

    val sideMenuOffset = remember { Animatable(-1f) }
    val sideMenuAlpha = remember { Animatable(0f) }

    LaunchedEffect(isSideMenuOpen) {
        if (isSideMenuOpen) {
            launch {
                sideMenuOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(360)
                )
            }
            launch {
                sideMenuAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(280)
                )
            }
        } else {
            launch {
                sideMenuOffset.animateTo(
                    targetValue = -1f,
                    animationSpec = tween(250)
                )
            }
            launch {
                sideMenuAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(180)
                )
            }
        }
    }

    if (isSideMenuOpen) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = sideMenuAlpha.value
                }
                .background(Color.Black.copy(alpha = 0.25f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(285.dp)
                    .graphicsLayer {
                        translationX = sideMenuOffset.value * 285.dp.toPx()
                    }
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
                        .background(CardColor)
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

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(17.dp))
                        .background(CardColor)
                        .border(1.dp, BorderColor, RoundedCornerShape(17.dp))
                        .clickable {
                            isSideMenuOpen = false
                            currentPage = "notifications"
                        }
                        .padding(horizontal = 15.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = Pink,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "Notifications",
                        color = TextDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                    if (notificationUnreadCount > 0) {
                        Text(
                            text = notificationUnreadCount.toString(),
                            color = Pink,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(17.dp))
                        .background(CardColor)
                        .border(1.dp, BorderColor, RoundedCornerShape(17.dp))
                        .clickable {
                            isSideMenuOpen = false
                            currentPage = "adoption"
                        }
                        .padding(horizontal = 15.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Pets,
                        contentDescription = null,
                        tint = purrFectColor(Color(0xFF4CAF50), Color(0xFF61E067)),
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = "Cat Adoption",
                        color = TextDark,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (loggedInUser != null) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(17.dp))
                            .background(CardColor)
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
                                notificationItems = emptyList()
                                notificationUnreadCount = 0
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
                                containerColor = purrFectColor(Color(0xFF4CAF50), Color(0xFF61E067))
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

    if (showMatchPopup && matchedPopupCat != null) {
        val matchedCat = matchedPopupCat!!
        AlertDialog(
            onDismissRequest = {
                showMatchPopup = false
                matchedPopupCat = null
            },
            title = {
                Text(
                    text = "It's a Match! 🐱❤️🐱",
                    fontWeight = FontWeight.Bold,
                    color = Pink
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    ) {
                        if (matchedCat.photoBitmap != null) {
                            Image(
                                bitmap = matchedCat.photoBitmap!!.asImageBitmap(),
                                contentDescription = matchedCat.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.signcat),
                                contentDescription = matchedCat.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "You and ${matchedCat.name} liked each other!",
                        color = TextDark,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your new match is now available in Matches.",
                        color = TextGrey,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showMatchPopup = false
                        matchedPopupCat = null
                        selectedTab = 1
                        currentPage = "main"
                    }
                ) {
                    Text("View Matches", color = Pink)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showMatchPopup = false
                        matchedPopupCat = null
                    }
                ) {
                    Text("Later")
                }
            }
        )
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
                    val cardAlpha = remember(cat.id) { Animatable(0f) }
                    val cardOffset = remember(cat.id) { Animatable(18f) }

                    LaunchedEffect(cat.id) {
                        launch {
                            cardAlpha.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(360)
                            )
                        }
                        launch {
                            delay(55)
                            cardOffset.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(420)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = cardAlpha.value
                                translationY = cardOffset.value.dp.toPx()
                            }
                            .fillMaxWidth()
                            .height(104.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(CardColor)
                            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
                            .clickable { onCatClick(cat) }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(84.dp)
                                .graphicsLayer {
                                    val photoScale = 0.94f + (0.06f * cardAlpha.value)
                                    scaleX = photoScale
                                    scaleY = photoScale
                                }
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
                                        .background(purrFectColor(Color(0xFFFFF1E7), Color(0xFF211E35))),
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
    val welcomeEntrance = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        welcomeEntrance.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326)))
            .graphicsLayer {
                alpha = welcomeEntrance.value
                val scale = 0.98f + (0.02f * welcomeEntrance.value)
                scaleX = scale
                scaleY = scale
            }
    ) {
        /* =================================================
        SOFT BACKGROUND BLOBS
        ================================================= */
        Box(
            modifier = Modifier
                .size(170.dp)
                .offset(
                    x = (-65).dp,
                    y = (-25).dp
                )
                .clip(CircleShape)
                .background(purrFectColor(Color(0xFFFFE8E5), Color(0xFF211E35)))
        )

        Box(
            modifier = Modifier
                .size(170.dp)
                .offset(
                    x = screenWidth - 55.dp,
                    y = screenHeight * 0.16f
                )
                .clip(CircleShape)
                .background(purrFectColor(Color(0xFFFFE9E8), Color(0xFF211E35)))
        )

        Box(
            modifier = Modifier
                .size(145.dp)
                .offset(
                    x = (-65).dp,
                    y = screenHeight * 0.72f
                )
                .clip(CircleShape)
                .background(purrFectColor(Color(0xFFFFE9E8), Color(0xFF211E35)))
        )

        Box(
            modifier = Modifier
                .size(155.dp)
                .offset(
                    x = screenWidth - 65.dp,
                    y = screenHeight * 0.88f
                )
                .clip(CircleShape)
                .background(purrFectColor(Color(0xFFFFE8E5), Color(0xFF211E35)))
        )

        /* =================================================
        PEACH DECORATIVE CIRCLES
        ================================================= */
        WelcomeCircle(
            x = screenWidth * 0.24f,
            y = screenHeight * 0.075f
        )
        WelcomeCircle(
            x = screenWidth * 0.82f,
            y = screenHeight * 0.61f
        )
        WelcomeCircle(
            x = screenWidth * 0.34f,
            y = screenHeight * 0.90f
        )

        /* =================================================
        DECORATIVE STARS
        ================================================= */
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.10f,
            y = screenHeight * 0.36f,
            size = 27,
            color = purrFectColor(Color(0xFFFFD86A), Color(0xFFF4C95B))
        )
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.77f,
            y = screenHeight * 0.15f,
            size = 28,
            color = purrFectColor(Color(0xFFFFD86A), Color(0xFFF4C95B))
        )
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.78f,
            y = screenHeight * 0.53f,
            size = 27,
            color = purrFectColor(Color(0xFFFFD86A), Color(0xFFF4C95B))
        )
        WelcomeStar(
            text = "★",
            x = screenWidth * 0.08f,
            y = screenHeight * 0.82f,
            size = 27,
            color = purrFectColor(Color(0xFFFFD86A), Color(0xFFF4C95B))
        )

        WelcomeStar(
            text = "H",
            x = screenWidth * 0.82f,
            y = screenHeight * 0.035f,
            size = 31,
            color = purrFectColor(Color(0xFFFF9FBB), Color(0xFFFF8FC0))
        )
        WelcomeStar(
            text = "H",
            x = screenWidth * 0.38f,
            y = screenHeight * 0.025f,
            size = 31,
            color = purrFectColor(Color(0xFFFF9FBB), Color(0xFFFF8FC0))
        )
        WelcomeStar(
            text = "H",
            x = screenWidth * 0.78f,
            y = screenHeight * 0.82f,
            size = 31,
            color = purrFectColor(Color(0xFFFF9FBB), Color(0xFFFF8FC0))
        )

        /* =================================================
        SMALL PAW DECORATIONS
        ================================================= */
        Icon(
            imageVector = Icons.Outlined.Pets,
            contentDescription = null,
            tint = purrFectColor(Color(0xFFFF9FBB), Color(0xFFFF8FC0)),
            modifier = Modifier
                .size(28.dp)
                .offset(
                    x = screenWidth * 0.08f,
                    y = screenHeight * 0.19f
                )
        )

        Icon(
            imageVector = Icons.Outlined.Pets,
            contentDescription = null,
            tint = purrFectColor(Color(0xFFFF9FBB), Color(0xFFFF8FC0)),
            modifier = Modifier
                .size(25.dp)
                .offset(
                    x = screenWidth * 0.82f,
                    y = screenHeight * 0.40f
                )
        )

        Icon(
            imageVector = Icons.Outlined.Pets,
            contentDescription = null,
            tint = purrFectColor(Color(0xFFA98BEF), Color(0xFFAA8DE6)),
            modifier = Modifier
                .size(27.dp)
                .offset(
                    x = screenWidth * 0.84f,
                    y = screenHeight * 0.79f
                )
        )

        /* =================================================
        PURRFECT LOGO
        ================================================= */
        Image(
            painter = painterResource(R.drawable.purrfect_logo),
            contentDescription = "PurrFect logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .height(150.dp)
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.135f)
        )

        /* =================================================
        TAGLINE
        ================================================= */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.315f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Where cats find",
                color = purrFectColor(Color(0xFF514A4A), Color(0xFFC5C0D0)),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif
            )

            Text(
                text = "Their purrfect match",
                color = purrFectColor(Color(0xFF514A4A), Color(0xFFC5C0D0)),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Serif
            )

            Row(
                modifier = Modifier.padding(top = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(45.dp)
                        .height(2.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(purrFectColor(Color(0xFFFFA9C4), Color(0xFFFF8FC0)))
                )

                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = purrFectColor(Color(0xFFFF91AF), Color(0xFFFF8FC0)),
                    modifier = Modifier
                        .padding(horizontal = 7.dp)
                        .size(20.dp)
                )

                Box(
                    modifier = Modifier
                        .width(45.dp)
                        .height(2.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(purrFectColor(Color(0xFFFFA9C4), Color(0xFFFF8FC0)))
                )
            }
        }

        /* =================================================
        TWO CATS
        ================================================= */
        WelcomeCats(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.30f)
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.405f)
        )

        /* =================================================
        HEART ABOVE CATS
        ================================================= */
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = purrFectColor(Color(0xFFFF3F72), Color(0xFFC73159)),
            modifier = Modifier
                .size(39.dp)
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.445f)
        )

        /* =================================================
        GET STARTED
        ================================================= */
        Button(
            onClick = onGetStarted,
            modifier = Modifier
                .width(screenWidth * 0.72f)
                .height(58.dp)
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.695f),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = purrFectColor(Color(0xFFFF4F79), Color(0xFFF45A9A))
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 5.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Pets,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(27.dp)
            )

            Spacer(modifier = Modifier.width(9.dp))

            Text(
                text = "Get Started",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        /* =================================================
        SIGN UP
        ================================================= */
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.775f)
                .clickable { onSignUp() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New user? ",
                color = purrFectColor(Color(0xFF777777), Color(0xFFA8A3B5)),
                fontSize = 13.sp
            )

            Text(
                text = "Sign Up",
                color = purrFectColor(Color(0xFFFF4F79), Color(0xFFF45A9A)),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }

        /* =================================================
        LOGIN
        ================================================= */
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = screenHeight * 0.835f)
                .clickable { onLogin() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                color = purrFectColor(Color(0xFF777777), Color(0xFFA8A3B5)),
                fontSize = 13.sp
            )

            Text(
                text = "Login",
                color = purrFectColor(Color(0xFFFF4F79), Color(0xFFF45A9A)),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
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
    val signupEntrance = remember { Animatable(0f) }
    val signupFormEntrance = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        launch {
            signupEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 450)
            )
        }
        launch {
            delay(90)
            signupFormEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 480)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFF4EFF8), Color(0xFF211E35)))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = signupEntrance.value
                    val scale = 0.975f + (0.025f * signupEntrance.value)
                    scaleX = scale
                    scaleY = scale
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
                .clip(
                    RoundedCornerShape(38.dp)
                )
                .background(
                    purrFectColor(Color(0xFFFFF9F4), Color(0xFF151326))
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
                        purrFectColor(Color(0xFFD9C9F4), Color(0xFF302A46))
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
                    purrFectColor(Color(0xFFB9A3E8), Color(0xFFB18AE8)),
                modifier = Modifier
                    .size(58.dp)
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = signupFormEntrance.value
                        translationY = (1f - signupFormEntrance.value) * 22.dp.toPx()
                    }
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
                        purrFectColor(Color(0xFF202332), Color(0xFFF7F3FA)),
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
                        purrFectColor(Color(0xFF4D4C53), Color(0xFFC5C0D0)),
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
                                purrFectColor(Color(0xFFE85E78), Color(0xFFF45A9A))
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
                                purrFectColor(Color(0xFFE4DCD8), Color(0xFF302A46))
                            )
                    )
                    Text(
                        text =
                            " or continue with ",
                        color =
                            purrFectColor(Color(0xFF5C5960), Color(0xFFA8A3B5)),
                        fontSize =
                            14.sp
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                purrFectColor(Color(0xFFE4DCD8), Color(0xFF302A46))
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
                            purrFectColor(Color(0xFF4285F4), Color(0xFF3D7AE0)),
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
                            purrFectColor(Color(0xFF1877F2), Color(0xFF1462C7))
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
                            purrFectColor(Color(0xFF4D4C53), Color(0xFFC5C0D0)),
                        fontSize =
                            15.sp
                    )
                    Text(
                        text =
                            "Log In",
                        color =
                            purrFectColor(Color(0xFFE85E78), Color(0xFFF45A9A)),
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
    val loginEntrance = remember { Animatable(0f) }
    val loginFormEntrance = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        launch {
            loginEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 450)
            )
        }
        launch {
            delay(90)
            loginFormEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 480)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFF1ECF8), Color(0xFF211E35)))
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = loginEntrance.value
                    val scale = 0.975f + (0.025f * loginEntrance.value)
                    scaleX = scale
                    scaleY = scale
                }
                .padding(
                    horizontal = 14.dp,
                    vertical = 14.dp
                )
                .clip(
                    RoundedCornerShape(40.dp)
                )
                .background(
                    purrFectColor(Color(0xFFFFFAF6), Color(0xFF151326))
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
                    purrFectColor(Color(0xFFB9A3E8), Color(0xFFB18AE8)),
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
                        purrFectColor(Color(0xFFD8C9F4), Color(0xFF302A46))
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
                color = purrFectColor(Color(0xFFF48BA0), Color(0xFFE07B90)),
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
                color = purrFectColor(Color(0xFFF8A7B5), Color(0xFFE07B8D)),
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
                    purrFectColor(Color(0xFFD8C8F4), Color(0xFF302A46)),
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
                    purrFectColor(Color(0xFFD5C5F1), Color(0xFF302A46)),
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
                    .graphicsLayer {
                        alpha = loginFormEntrance.value
                        translationY = (1f - loginFormEntrance.value) * 22.dp.toPx()
                    }
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
                        purrFectColor(Color(0xFF202332), Color(0xFFF7F3FA)),
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
                            purrFectColor(Color(0xFF56545B), Color(0xFFA8A3B5)),
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
                            purrFectColor(Color(0xFFB7A1E4), Color(0xFF9D7BE0)),
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
                                purrFectColor(Color(0xFF7560B8), Color(0xFF7E68C7))
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
                                purrFectColor(Color(0xFF7560B8), Color(0xFF7E68C7))
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
                                        purrFectColor(Color(0xFF9A80D0), Color(0xFFAA8DE6))
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .border(
                                    width = 1.dp,
                                    color = purrFectColor(Color(0xFF9A80D0), Color(0xFFAA8DE6)),
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
                                purrFectColor(Color(0xFF4E4C53), Color(0xFFC5C0D0)),
                            fontSize =
                                16.sp
                        )
                    }
                    Text(
                        text =
                            "Forgot Password?",
                        color =
                            purrFectColor(Color(0xFF7359B5), Color(0xFF7E62C7)),
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
                                purrFectColor(Color(0xFFF06B84), Color(0xFFE0647B))
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
                                purrFectColor(Color(0xFFE4DCD8), Color(0xFF302A46))
                            )
                    )
                    Text(
                        text =
                            " or continue with ",
                        color =
                            purrFectColor(Color(0xFF5C5960), Color(0xFFA8A3B5)),
                        fontSize =
                            14.sp
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(
                                purrFectColor(Color(0xFFE4DCD8), Color(0xFF302A46))
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
                        symbolColor = purrFectColor(Color(0xFF4285F4), Color(0xFF3D7AE0)),
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
                        symbolColor = purrFectColor(Color(0xFF1877F2), Color(0xFF1462C7))
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
                            purrFectColor(Color(0xFF4D4C53), Color(0xFFC5C0D0)),
                        fontSize =
                            15.sp
                    )
                    Text(
                        text =
                            "Sign Up →",
                        color =
                            purrFectColor(Color(0xFFE85E78), Color(0xFFF45A9A)),
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
                color = purrFectColor(Color(0xFF858189), Color(0xFFA8A3B5)),
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
                    purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                unfocusedBorderColor =
                    purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                focusedContainerColor =
                    purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326)),
                unfocusedContainerColor =
                    purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326)),
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
                    purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                unfocusedBorderColor =
                    purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                focusedContainerColor =
                    purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326)),
                unfocusedContainerColor =
                    purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326))
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
                purrFectColor(Color(0xFFFFDEC8), Color(0xFF211E35))
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
    color: Color = purrFectColor(Color(0xFFFFEFA8), Color(0xFF211E35))
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
TRANSPARENT ANIMAL CARE LOADING ANIMATION
The supplied MP4 was converted to an animated WebP with the
white background removed. On Android 9+ AnimatedImageDrawable
plays it natively. Older Android versions show the first frame.
========================================================= */
@Composable
fun AnimalCareLoadingAnimation(
    modifier: Modifier = Modifier
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_INSIDE

                    val source =
                        android.graphics.ImageDecoder.createSource(
                            context.resources,
                            R.drawable.animal_care_loading_transparent
                        )

                    val drawable =
                        android.graphics.ImageDecoder.decodeDrawable(source)

                    setImageDrawable(drawable)

                    (drawable as? android.graphics.drawable.AnimatedImageDrawable)?.apply {
                        repeatCount = android.graphics.drawable.AnimatedImageDrawable.REPEAT_INFINITE
                        start()
                    }
                }
            },
            update = { imageView ->
                val drawable = imageView.drawable
                if (drawable is android.graphics.drawable.AnimatedImageDrawable && !drawable.isRunning) {
                    drawable.start()
                }
            }
        )
    } else {
        Image(
            painter = painterResource(R.drawable.animal_care_loading_transparent),
            contentDescription = "Loading",
            modifier = modifier,
            contentScale = ContentScale.Fit
        )
    }
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
    var loadError by remember {
        mutableStateOf<String?>(null)
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
    var searchQuery by remember {
        mutableStateOf("")
    }
    var showDiscoverFilters by remember {
        mutableStateOf(false)
    }
    val discoverLikeScope = rememberCoroutineScope()
    val discoverContext = LocalContext.current

    val discoverCardEntrance = remember {
        Animatable(0f)
    }
    val discoverActionsEntrance = remember {
        Animatable(0f)
    }

    var starSelected by remember {
        mutableStateOf(false)
    }

    // Filter states
    var selectedGender by remember { mutableStateOf<String?>(null) }
    var selectedAgeRange by remember { mutableStateOf<String?>(null) }
    var sortByNearby by remember { mutableStateOf(false) }

    val filteredCats = remember(discoverCats, searchQuery, selectedGender, selectedAgeRange, sortByNearby) {
        val query = searchQuery.trim()
        var list = if (query.isBlank()) {
            discoverCats
        } else {
            discoverCats.filter { cat ->
                cat.name.contains(query, ignoreCase = true) ||
                        cat.breed.contains(query, ignoreCase = true) ||
                        cat.gender.contains(query, ignoreCase = true) ||
                        cat.about.contains(query, ignoreCase = true)
            }
        }

        // Apply Gender Filter
        if (selectedGender != null) {
            list = list.filter { it.gender.equals(selectedGender, ignoreCase = true) }
        }

        // Apply Age Filter
        if (selectedAgeRange != null) {
            list = list.filter { cat ->
                val ageInt = cat.age.toIntOrNull() ?: 0
                when (selectedAgeRange) {
                    "Kitten" -> ageInt < 1
                    "Adult" -> ageInt in 1..7
                    "Senior" -> ageInt > 7
                    else -> true
                }
            }
        }

        // Apply Nearby Sort
        if (sortByNearby) {
            list = list.sortedBy { it.distanceKm ?: Double.MAX_VALUE }
        }

        list
    }

    LaunchedEffect(currentCatId) {
        currentCat = 0
        buttonAction = 0
        starSelected = false
        searchQuery = ""
        showDiscoverFilters = false
        selectedGender = null
        selectedAgeRange = null
        sortByNearby = false
        isLoading = true
        loadError = null
        discoverCardEntrance.snapTo(0f)
        discoverActionsEntrance.snapTo(0f)
        try {
            discoverCats = PurrFectApi.getDiscoverCats(currentCatId)
        } catch (error: Exception) {
            discoverCats = emptyList()
            loadError = error.message ?: "Unable to load cats right now."
        } finally {
            isLoading = false
        }

        if (discoverCats.isNotEmpty()) {
            launch {
                discoverCardEntrance.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 420)
                )
            }
            launch {
                kotlinx.coroutines.delay(90)
                discoverActionsEntrance.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 360)
                )
            }
        }
    }

    LaunchedEffect(searchQuery) {
        currentCat = 0
        buttonAction = 0
        starSelected = false
        if (!isLoading && loadError == null) {
            discoverCardEntrance.snapTo(0f)
            discoverActionsEntrance.snapTo(0f)
            if (filteredCats.isNotEmpty()) {
                launch {
                    discoverCardEntrance.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 300)
                    )
                }
                launch {
                    kotlinx.coroutines.delay(60)
                    discoverActionsEntrance.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = 260)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        /* =====================================================
        DISCOVER HEADER
        ===================================================== */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Menu",
                        tint = TextDark,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp, top = 1.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Discover",
                            color = TextDark,
                            fontSize = 27.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "🐾",
                            fontSize = 18.sp
                        )
                    }
                    Text(
                        text = "Find your purrrfect companion 💗",
                        color = TextGrey,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 1.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, BorderColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorites",
                        tint = Purple,
                        modifier = Modifier.size(21.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = Purple,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search cats by breed, age or location...",
                            color = TextGrey,
                            fontSize = 11.sp
                        )
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Purple,
                        unfocusedBorderColor = BorderColor,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = Purple,
                        focusedTextColor = TextDark,
                        unfocusedTextColor = TextDark
                    )
                )

                Spacer(modifier = Modifier.width(7.dp))

                IconButton(
                    onClick = { showDiscoverFilters = !showDiscoverFilters },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Purple)
                ) {
                    Icon(
                        imageVector = if (showDiscoverFilters) Icons.Outlined.Close else Icons.Outlined.Tune,
                        contentDescription = if (showDiscoverFilters) "Hide filters" else "Show filters",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = showDiscoverFilters,
                enter = fadeIn(animationSpec = tween(180)) +
                        expandVertically(animationSpec = tween(260)),
                exit = fadeOut(animationSpec = tween(120)) +
                        shrinkVertically(animationSpec = tween(220))
            ) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DiscoverFilterChip(
                            label = "Nearby",
                            icon = "⌖",
                            selected = sortByNearby,
                            onClick = { sortByNearby = !sortByNearby }
                        )
                        DiscoverFilterChip(
                            label = if (selectedAgeRange == null) "Age" else selectedAgeRange!!,
                            icon = "▣",
                            selected = selectedAgeRange != null,
                            onClick = {
                                selectedAgeRange = when (selectedAgeRange) {
                                    null -> "Kitten"
                                    "Kitten" -> "Adult"
                                    "Adult" -> "Senior"
                                    else -> null
                                }
                            }
                        )
                        DiscoverFilterChip(
                            label = "Breed",
                            icon = "🐱",
                            selected = false,
                            onClick = {
                                // For now, maybe just focus search or show a toast
                                Toast.makeText(discoverContext, "Use search to filter by breed", Toast.LENGTH_SHORT).show()
                            }
                        )
                        DiscoverFilterChip(
                            label = if (selectedGender == null) "Gender" else selectedGender!!,
                            icon = "⚥",
                            selected = selectedGender != null,
                            onClick = {
                                selectedGender = when (selectedGender) {
                                    null -> "Male"
                                    "Male" -> "Female"
                                    else -> null
                                }
                            }
                        )
                        DiscoverFilterChip(
                            label = "More",
                            icon = "•••",
                            selected = false,
                            onClick = { }
                        )
                    }
                }
            }
        }

        AnimatedContent(
            targetState = when {
                isLoading -> "loading"
                loadError != null -> "error"
                discoverCats.isNotEmpty() && filteredCats.isEmpty() -> "search_empty"
                discoverCats.isEmpty() -> "empty"
                else -> "content"
            },
            transitionSpec = {
                fadeIn(animationSpec = tween(220)) togetherWith
                        fadeOut(animationSpec = tween(160))
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            label = "discover_loading_content_transition"
        ) { state ->
            when (state) {
                "loading" -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AnimalCareLoadingAnimation(
                                modifier = Modifier.size(180.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Finding cats near you...",
                                color = TextGrey,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                "error" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "🐾", fontSize = 52.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Couldn't load cats",
                                color = TextDark,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = loadError ?: "Please try again later.",
                                color = TextGrey,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
                "search_empty" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "🔎", fontSize = 50.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "No cats found",
                                color = TextDark,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Try a different name, breed or search term.",
                                color = TextGrey,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
                "empty" -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
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
                }
                else -> {
                    val safeIndex = currentCat.coerceIn(0, filteredCats.lastIndex)
                    SwipeCard(
                        cat = filteredCats[safeIndex],
                        catNumber = safeIndex,
                        buttonAction = buttonAction,
                        onCatClick = {
                            onCatClick(filteredCats[safeIndex])
                        },
                        onSwipeComplete = {
                            currentCat++
                            buttonAction = 0
                            starSelected = false
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 17.dp, vertical = 2.dp)
                            .graphicsLayer {
                                alpha = discoverCardEntrance.value
                                val entranceScale =
                                    0.965f + (0.035f * discoverCardEntrance.value)
                                scaleX = entranceScale
                                scaleY = entranceScale
                                translationY =
                                    (1f - discoverCardEntrance.value) * 18.dp.toPx()
                            }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .graphicsLayer {
                    alpha = discoverActionsEntrance.value
                    translationY =
                        (1f - discoverActionsEntrance.value) * 10.dp.toPx()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
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
                    val starredCatId = filteredCats.getOrNull(
                        currentCat.coerceIn(0, filteredCats.lastIndex)
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
                        filteredCats.getOrNull(
                            currentCat.coerceIn(0, filteredCats.lastIndex)
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

@Composable
private fun DiscoverFilterChip(
    label: String,
    icon: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(34.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(if (selected) Pink else Color.White)
            .border(
                width = 1.dp,
                color = if (selected) Pink else BorderColor,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = icon,
            color = if (selected) Color.White else Purple,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            color = if (selected) Color.White else TextDark,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
        )
        if (!selected && label != "More") {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "⌄",
                color = TextGrey,
                fontSize = 11.sp
            )
        }
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
    val scope = rememberCoroutineScope()
    val pressScale = remember { Animatable(1f) }

    Box(
        modifier = Modifier
            .graphicsLayer {
                scaleX = pressScale.value
                scaleY = pressScale.value
            }
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
            onClick = {
                onClick()
                scope.launch {
                    pressScale.snapTo(0.88f)
                    pressScale.animateTo(1f, spring(dampingRatio = 0.55f, stiffness = 700f))
                }
            }
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
            Modifier.size(53.dp),
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
STEP 14 — GESTURE INTERACTION POLISH
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
                    val swipeProgress =
                        (abs(offsetX.value) / 900f).coerceIn(0f, 1f)
                    rotationZ =
                        (offsetX.value / 25f).coerceIn(-12f, 12f)
                    alpha =
                        1f - (swipeProgress * 0.35f)
                    // A very subtle scale change makes the card feel connected
                    // to the user's finger without affecting the swipe behavior.
                    scaleX = 1f - (swipeProgress * 0.025f)
                    scaleY = 1f - (swipeProgress * 0.025f)
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
                                        // Spring back naturally when the user
                                        // releases the card before the threshold.
                                        offsetX.animateTo(
                                            0f,
                                            spring(
                                                dampingRatio = 0.72f,
                                                stiffness = 420f
                                            )
                                        )
                                        offsetY.animateTo(
                                            0f,
                                            spring(
                                                dampingRatio = 0.72f,
                                                stiffness = 420f
                                            )
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
                    .background(purrFectColor(Color(0xFFE4D4C8), Color(0xFF302A46))),
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
                    purrFectColor(Color(0xFFEDE4E0), Color(0xFF211E35))
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
                            purrFectColor(Color(0xFF63B87A), Color(0xFF79E095))
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
                            purrFectColor(Color(0xFF638BC7), Color(0xFF709DE0))
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
                    purrFectColor(Color(0xFFEDE4E0), Color(0xFF211E35))
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
                            purrFectColor(Color(0xFF63B87A), Color(0xFF79E095))
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
                            purrFectColor(Color(0xFF638BC7), Color(0xFF709DE0))
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

    // Cat Profile entrance animation
    val profilePhotoEntrance = remember { Animatable(0f) }
    val profileDetailsEntrance = remember { Animatable(0f) }
    val profileContentEntrance = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            profilePhotoEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 450)
            )
        }
        launch {
            delay(90)
            profileDetailsEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 430)
            )
        }
        launch {
            delay(170)
            profileContentEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 450)
            )
        }
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
                        .graphicsLayer {
                            alpha = profilePhotoEntrance.value
                            val scale = 0.94f + (0.06f * profilePhotoEntrance.value)
                            scaleX = scale
                            scaleY = scale
                        }
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
                        Alignment.CenterVertically,
                    modifier = Modifier.graphicsLayer {
                        alpha = profileDetailsEntrance.value
                        translationY = (1f - profileDetailsEntrance.value) * 18.dp.toPx()
                    }
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
                                purrFectColor(Color(0xFF7189D9), Color(0xFF758EE0))
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
                        14.sp,
                    modifier = Modifier.graphicsLayer {
                        alpha = profileDetailsEntrance.value
                        translationY = (1f - profileDetailsEntrance.value) * 18.dp.toPx()
                    }
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
                        Modifier
                            .padding(top = 10.dp)
                            .graphicsLayer {
                                alpha = profileContentEntrance.value
                                translationY = (1f - profileContentEntrance.value) * 16.dp.toPx()
                            }
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
                        21.sp,
                    modifier = Modifier.graphicsLayer {
                        alpha = profileContentEntrance.value
                        translationY = (1f - profileContentEntrance.value) * 16.dp.toPx()
                    }
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
                        Modifier
                            .padding(top = 10.dp)
                            .graphicsLayer {
                                alpha = profileContentEntrance.value
                                translationY = (1f - profileContentEntrance.value) * 16.dp.toPx()
                            }
                )
                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )
                Row(
                    modifier = Modifier.graphicsLayer {
                        alpha = profileContentEntrance.value
                        translationY = (1f - profileContentEntrance.value) * 16.dp.toPx()
                    },
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
                        Modifier
                            .padding(top = 10.dp)
                            .graphicsLayer {
                                alpha = profileContentEntrance.value
                                translationY = (1f - profileContentEntrance.value) * 16.dp.toPx()
                            }
                )
                Spacer(
                    modifier =
                        Modifier.height(13.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = profileContentEntrance.value
                            translationY = (1f - profileContentEntrance.value) * 16.dp.toPx()
                        },
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
                        .height(58.dp)
                        .graphicsLayer {
                            alpha = profileContentEntrance.value
                            translationY = (1f - profileContentEntrance.value) * 20.dp.toPx()
                        },
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
                            color = purrFectColor(Color(0xFFD32F2F), Color(0xFFC72C2C)),
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
                    purrFectColor(Color(0xFFFFF1E7), Color(0xFF211E35))
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
    val matchScale = remember {
        Animatable(0.92f)
    }
    val matchAlpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(Unit) {
        launch {
            matchScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = 0.72f,
                    stiffness = 380f
                )
            )
        }
        delay(40)
        matchAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(350)
        )
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
            .graphicsLayer {
                scaleX = matchScale.value
                scaleY = matchScale.value
                alpha = matchAlpha.value
            }
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
                                purrFectColor(Color(0xFFFFE8ED), Color(0xFF211E35))
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
                    purrFectColor(Color(0xFFF2E8EA), Color(0xFF211E35))
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
    currentCatId: Int,
    onTabSelected: (Int) -> Unit,
    onChatClick: (ChatItem) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }
    var chats by remember {
        mutableStateOf<List<ChatItem>>(emptyList())
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(currentCatId) {
        if (currentCatId > 0) {
            chats = PurrFectApi.getChatList(currentCatId)
        }
        isLoading = false
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (!purrFectDarkMode) {
            Image(
                painter = painterResource(id = R.drawable.chatbg),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
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
            if (filteredChats.isEmpty() && !isLoading) {
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
            .clip(RoundedCornerShape(18.dp))
            .background(if (purrFectDarkMode) CardColor else Color.Transparent)
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
                    purrFectColor(Color(0xFFEDE4E0), Color(0xFF211E35))
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
                            purrFectColor(Color(0xFF63B87A), Color(0xFF79E095))
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
    currentCatId: Int,
    chat: ChatItem,
    onBack: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showChatOptions by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var showBlockDialog by remember { mutableStateOf(false) }
    var showPhoneDialog by remember { mutableStateOf(false) }
    var showEmojiPicker by remember { mutableStateOf(false) }
    var phoneRequestSent by remember { mutableStateOf(false) }
    var actionMessage by remember { mutableStateOf<String?>(null) }

    val filePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            scope.launch {
                try {
                    val response = PurrFectApi.uploadChatFile(
                        context = context,
                        matchId = chat.matchId,
                        senderCatId = currentCatId,
                        uri = uri
                    )
                    val file = response.optJSONObject("file")
                    val name = file?.optString("name", "Attachment") ?: "Attachment"
                    val mime = file?.optString("mime_type", "application/octet-stream") ?: "application/octet-stream"
                    val relativeUrl = file?.optString("url", "") ?: ""
                    if (relativeUrl.isBlank()) throw IllegalStateException("File URL was not returned")
                    val marker = "[[FILE|$name|$mime|$relativeUrl]]"
                    PurrFectApi.sendMessage(
                        matchId = chat.matchId,
                        senderCatId = currentCatId,
                        receiverCatId = chat.otherCatId,
                        message = marker
                    )
                    messages.add(
                        ChatMessage(
                            text = name,
                            isMine = true,
                            time = formatChatTime(System.currentTimeMillis().toString()),
                            attachmentName = name,
                            attachmentUrl = relativeUrl,
                            attachmentMime = mime
                        )
                    )
                } catch (e: Exception) {
                    actionMessage = e.message ?: "Could not send file"
                }
            }
        }
    }

    LaunchedEffect(chat.matchId) {
        if (chat.matchId > 0) {
            val history = PurrFectApi.getMessages(chat.matchId, currentCatId)
            messages.clear()
            messages.addAll(history)
            PurrFectApi.markAsRead(chat.matchId, currentCatId)
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
                .height(68.dp)
                .background(CardColor)
                .border(1.dp, BorderColor)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Outlined.ArrowBack, "Back", tint = TextDark)
            }
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(purrFectColor(Color(0xFFEDE4E0), Color(0xFF211E35))),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Pets, "Cat", tint = TextGrey, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(chat.name, color = TextDark, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier.size(7.dp).clip(CircleShape).background(
                            if (chat.online) purrFectColor(Color(0xFF36B56A), Color(0xFF43E083)) else TextGrey
                        )
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        if (chat.online) "Online" else chat.lastSeenLabel,
                        color = TextGrey,
                        fontSize = 10.sp
                    )
                }
            }
            IconButton(onClick = { showPhoneDialog = true }) {
                Text("☎", color = TextDark, fontSize = 21.sp)
            }
            IconButton(onClick = { showChatOptions = true }) {
                Text("⋮", color = TextDark, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
        }

        Box(Modifier.weight(1f).fillMaxWidth()) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(5) { index ->
                    Icon(
                        Icons.Outlined.Pets,
                        contentDescription = null,
                        tint = Pink.copy(alpha = 0.055f),
                        modifier = Modifier
                            .padding(top = (80 + index * 125).dp)
                            .size(48.dp)
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                if (messages.isEmpty()) {
                    item {
                        Column(
                            Modifier.fillMaxWidth().padding(top = 90.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                Modifier.size(62.dp).clip(CircleShape).background(Pink.copy(alpha = 0.10f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Outlined.Pets, null, tint = Pink, modifier = Modifier.size(30.dp))
                            }
                            Spacer(Modifier.height(10.dp))
                            Text("Start the conversation", color = TextDark, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text("Say hello to ${chat.name} 🐾", color = TextGrey, fontSize = 11.sp)
                        }
                    }
                }
                items(messages) { message -> MessageBubble(message) }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardColor)
                .border(1.dp, BorderColor)
                .padding(horizontal = 9.dp, vertical = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...", color = TextGrey, fontSize = 12.sp) },
                singleLine = false,
                maxLines = 4,
                shape = RoundedCornerShape(25.dp),
                leadingIcon = {
                    IconButton(onClick = { filePicker.launch("*/*") }) {
                        Text("📎", fontSize = 20.sp)
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { showEmojiPicker = true }) {
                        Text("😊", fontSize = 20.sp)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Pink,
                    unfocusedBorderColor = BorderColor,
                    focusedContainerColor = BackgroundColor,
                    unfocusedContainerColor = BackgroundColor,
                    focusedTextColor = TextDark,
                    unfocusedTextColor = TextDark,
                    cursorColor = Pink
                )
            )
            Spacer(Modifier.width(7.dp))
            Box(
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(if (messageText.isNotBlank()) Pink else BorderColor),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = {
                        val textToSend = messageText.trim()
                        if (textToSend.isNotBlank()) {
                            scope.launch {
                                try {
                                    PurrFectApi.sendMessage(chat.matchId, currentCatId, chat.otherCatId, textToSend)
                                    messages.add(ChatMessage(textToSend, true, formatChatTime(System.currentTimeMillis().toString())))
                                    messageText = ""
                                } catch (e: Exception) {
                                    actionMessage = e.message ?: "Message could not be sent"
                                }
                            }
                        }
                    }
                ) {
                    Icon(Icons.Outlined.Send, "Send", tint = if (messageText.isNotBlank()) Color.White else TextGrey, modifier = Modifier.size(21.dp))
                }
            }
        }
    }

    if (showChatOptions) {
        AlertDialog(
            onDismissRequest = { showChatOptions = false },
            title = { Text("Chat options", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    TextButton(onClick = { showChatOptions = false; showReportDialog = true }) {
                        Text("⚑", color = Pink, fontSize = 20.sp)
                        Spacer(Modifier.width(10.dp))
                        Text("Report user", color = TextDark)
                    }
                    TextButton(onClick = { showChatOptions = false; showBlockDialog = true }) {
                        Text("⊘", color = purrFectColor(Color(0xFFD94B5B), Color(0xFFC74553)), fontSize = 20.sp)
                        Spacer(Modifier.width(10.dp))
                        Text("Block user", color = TextDark)
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showChatOptions = false }) { Text("Cancel", color = Pink) } }
        )
    }

    if (showPhoneDialog) {
        AlertDialog(
            onDismissRequest = { if (!phoneRequestSent) showPhoneDialog = false },
            title = { Text(if (phoneRequestSent) "Request sent" else "Request phone number", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    if (phoneRequestSent) "Your request has been sent to ${chat.name}. If they approve it, their number can be shared with you."
                    else "Would you like to request ${chat.name}'s phone number?"
                )
            },
            confirmButton = {
                if (phoneRequestSent) {
                    TextButton(onClick = { showPhoneDialog = false; phoneRequestSent = false }) { Text("Done", color = Pink) }
                } else {
                    TextButton(onClick = {
                        scope.launch {
                            try {
                                PurrFectApi.requestPhoneNumber(currentCatId, chat.otherCatId)
                                phoneRequestSent = true
                            } catch (e: Exception) {
                                actionMessage = e.message ?: "Could not send request"
                                showPhoneDialog = false
                            }
                        }
                    }) { Text("Request", color = Pink) }
                }
            },
            dismissButton = if (!phoneRequestSent) ({ TextButton(onClick = { showPhoneDialog = false }) { Text("Cancel") } }) else null
        )
    }

    if (showReportDialog) {
        AlertDialog(
            onDismissRequest = { showReportDialog = false },
            title = { Text("Report ${chat.name}", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    listOf("Spam", "Harassment", "Inappropriate content", "Fake profile", "Other").forEach { reason ->
                        TextButton(onClick = {
                            showReportDialog = false
                            scope.launch {
                                try {
                                    PurrFectApi.reportUser(currentCatId, chat.otherCatId, reason)
                                    actionMessage = "Report submitted"
                                } catch (e: Exception) {
                                    actionMessage = e.message ?: "Could not report user"
                                }
                            }
                        }) {
                            Text("⚑", color = Pink, fontSize = 20.sp)
                            Spacer(Modifier.width(8.dp))
                            Text(reason, color = TextDark)
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showReportDialog = false }) { Text("Cancel") } }
        )
    }

    if (showBlockDialog) {
        AlertDialog(
            onDismissRequest = { showBlockDialog = false },
            title = { Text("Block ${chat.name}?", fontWeight = FontWeight.Bold) },
            text = { Text("You will no longer be able to message each other, and this chat will be removed from your chat list.") },
            confirmButton = {
                TextButton(onClick = {
                    scope.launch {
                        try {
                            PurrFectApi.blockUser(currentCatId, chat.otherCatId)
                            showBlockDialog = false
                            onBack()
                        } catch (e: Exception) {
                            actionMessage = e.message ?: "Could not block user"
                            showBlockDialog = false
                        }
                    }
                }) { Text("Block", color = purrFectColor(Color(0xFFD94B5B), Color(0xFFC74553))) }
            },
            dismissButton = { TextButton(onClick = { showBlockDialog = false }) { Text("Cancel") } }
        )
    }

    if (showEmojiPicker) {
        val emojis = listOf("😀","😂","😍","🥰","😎","😢","😡","😅","😉","😘","❤️","💕","🐱","🐾","😻","🙌","👍","👏","🔥","✨","🥹","😭","🤣","🤍","💯","🎉","😴","🤔","😇","😜","😋","🤗")
        AlertDialog(
            onDismissRequest = { showEmojiPicker = false },
            title = { Text("Choose an emoji", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    emojis.chunked(8).forEach { row ->
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            row.forEach { emoji ->
                                TextButton(onClick = { messageText += emoji; showEmojiPicker = false }) {
                                    Text(emoji, fontSize = 24.sp)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showEmojiPicker = false }) { Text("Done", color = Pink) } }
        )
    }

    actionMessage?.let { message ->
        AlertDialog(
            onDismissRequest = { actionMessage = null },
            text = { Text(message) },
            confirmButton = { TextButton(onClick = { actionMessage = null }) { Text("OK", color = Pink) } }
        )
    }
}

private fun formatChatTime(raw: String): String {
    if (raw.isBlank()) return ""
    return try {
        val millis = raw.toLongOrNull()
        val date = if (millis != null) Date(millis) else {
            val normalized = raw.replace("Z", "+0000")
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US).parse(normalized) ?: return raw
        }
        SimpleDateFormat("h:mm a", Locale.US).format(date)
    } catch (_: Exception) {
        raw
    }
}

/* =========================================================
MESSAGE BUBBLE
========================================================= */
@Composable
fun MessageBubble(
    message: ChatMessage
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (message.isMine) Alignment.End else Alignment.Start,
            modifier = Modifier.fillMaxWidth(0.86f)
        ) {
            Box(
                modifier = Modifier
                    .widthIn(max = 310.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (message.isMine) 18.dp else 5.dp,
                            bottomEnd = if (message.isMine) 5.dp else 18.dp
                        )
                    )
                    .background(if (message.isMine) Pink else CardColor)
                    .border(
                        if (message.isMine) 0.dp else 1.dp,
                        if (message.isMine) Color.Transparent else BorderColor,
                        RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (message.isMine) 18.dp else 5.dp,
                            bottomEnd = if (message.isMine) 5.dp else 18.dp
                        )
                    )
                    .padding(horizontal = 13.dp, vertical = 9.dp)
            ) {
                if (message.attachmentUrl != null) {
                    Column(Modifier.widthIn(min = 155.dp, max = 260.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                Modifier.size(38.dp).clip(RoundedCornerShape(11.dp)).background(
                                    if (message.isMine) Color.White.copy(alpha = 0.18f) else Pink.copy(alpha = 0.10f)
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.Share,
                                    null,
                                    tint = if (message.isMine) Color.White else Pink,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(Modifier.width(9.dp))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    message.attachmentName ?: "Attachment",
                                    color = if (message.isMine) Color.White else TextDark,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 2
                                )
                                Text(
                                    "Tap to open",
                                    color = if (message.isMine) Color.White.copy(alpha = 0.78f) else TextGrey,
                                    fontSize = 9.sp
                                )
                            }
                        }
                        Spacer(Modifier.height(5.dp))
                        Text(
                            message.time.let { formatChatTime(it) },
                            color = if (message.isMine) Color.White.copy(alpha = 0.78f) else TextGrey,
                            fontSize = 8.sp
                        )
                    }
                } else {
                    Column {
                        Text(
                            text = message.text,
                            color = if (message.isMine) Color.White else TextDark,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                        Spacer(Modifier.height(3.dp))
                        Text(
                            text = formatChatTime(message.time),
                            color = if (message.isMine) Color.White.copy(alpha = 0.78f) else TextGrey,
                            fontSize = 8.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
            }
            if (message.attachmentUrl != null) {
                TextButton(
                    onClick = {
                        try {
                            val url = if (message.attachmentUrl.startsWith("http")) message.attachmentUrl else "$PURR_FECT_API_BASE_URL${message.attachmentUrl}"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        } catch (_: Exception) {
                            Toast.makeText(context, "No app can open this file", Toast.LENGTH_SHORT).show()
                        }
                    },
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 0.dp)
                ) {
                    Text("Open file", color = Pink, fontSize = 9.sp)
                }
            }
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
    onSettingsClick: () -> Unit,
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
                IconButton(
                    onClick = onSettingsClick,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = TextDark,
                        modifier = Modifier.size(22.dp)
                    )
                }
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
                                    purrFectColor(Color(0xFF638BC7), Color(0xFF709DE0))
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
MY CAT PROFILES SCREEN
========================================================= */
@Composable
fun MyCatProfilesScreen(
    cats: List<CatProfile>,
    activeCatId: Int,
    onBack: () -> Unit,
    onAddCat: () -> Unit,
    onSelectCat: (CatProfile) -> Unit,
    onEditCat: (CatProfile) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Outlined.ArrowBack, "Back", tint = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)))
            }
            Text(
                "My Cat Profiles",
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    "Manage your cats",
                    color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }

            if (cats.isEmpty()) {
                item {
                    SettingsCard {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Outlined.Pets, null, tint = purrFectColor(Color(0xFF8057C7), Color(0xFF9A63DC)), modifier = Modifier.size(48.dp))
                            Spacer(Modifier.height(10.dp))
                            Text("No cat profiles yet", fontWeight = FontWeight.Bold, color = TextDark, fontSize = 18.sp)
                            Text("Add your first cat profile to get started.", color = TextGrey, fontSize = 14.sp)
                        }
                    }
                }
            } else {
                items(cats, key = { it.id }) { cat ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .background(purrFectColor(Color(0xFFFFFDFB), Color(0xFF151326)))
                            .border(1.dp, if (cat.id == activeCatId) purrFectColor(Color(0xFFFFA8B8), Color(0xFFFF8FC0)) else purrFectColor(Color(0xFFF0E5E1), Color(0xFF211E35)), RoundedCornerShape(22.dp))
                            .clickable { onSelectCat(cat) }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(Modifier.size(72.dp).clip(CircleShape).background(purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)))) {
                            if (cat.photoBitmap != null) {
                                Image(cat.photoBitmap.asImageBitmap(), cat.name, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                            } else {
                                Icon(Icons.Outlined.Pets, null, tint = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)), modifier = Modifier.align(Alignment.Center).size(34.dp))
                            }
                        }
                        Spacer(Modifier.width(14.dp))
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(cat.name.ifBlank { "Unnamed Cat" }, color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                if (cat.id == activeCatId) {
                                    Spacer(Modifier.width(8.dp))
                                    Text("ACTIVE", color = Pink, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(Modifier.height(3.dp))
                            Text("${cat.breed} • ${cat.age} years", color = TextGrey, fontSize = 13.sp)
                        }
                        IconButton(onClick = { onEditCat(cat) }) {
                            Icon(Icons.Filled.Edit, "Edit cat", tint = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)))
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .background(purrFectColor(Color(0xFFFFE7EC), Color(0xFF211E35)))
                        .clickable(onClick = onAddCat)
                        .padding(horizontal = 18.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Add, "Add cat", tint = Pink, modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Add New Cat", color = Pink, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                        Text("Create another cat profile", color = TextGrey, fontSize = 13.sp)
                    }
                    Icon(Icons.Outlined.ChevronRight, null, tint = purrFectColor(Color(0xFF73798A), Color(0xFFA8A3B5)))
                }
            }
        }
    }
}

/* =========================================================
ACCOUNT & SECURITY SCREEN
========================================================= */
@Composable
fun AccountSecurityScreen(
    user: BackendUser?,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var passwordStep by remember { mutableIntStateOf(0) }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var otp by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var resetToken by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showSecurityInfo by remember { mutableStateOf(false) }

    fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                    modifier = Modifier.size(25.dp)
                )
            }
            Text(
                text = "Account & Security",
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AccountSecurityInfoCard(
                title = "Email",
                value = user?.email?.ifBlank { "Not available" } ?: "Not available",
                icon = Icons.Outlined.Email,
                iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35))
            )

            SettingsCard {
                SettingsNavigationRow(
                    icon = Icons.Outlined.Lock,
                    iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)),
                    title = "Change Password",
                    subtitle = "Reset your password using email verification",
                    onClick = {
                        email = user?.email ?: ""
                        passwordStep = 0
                        otp = ""
                        newPassword = ""
                        confirmPassword = ""
                        resetToken = ""
                    }
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.Shield,
                    iconColor = purrFectColor(Color(0xFF7952C8), Color(0xFF8A5BD1)),
                    iconBackground = purrFectColor(Color(0xFFF0E5FF), Color(0xFF211E35)),
                    title = "Security",
                    subtitle = "Review your account security",
                    onClick = { showSecurityInfo = true }
                )
            }

            Text(
                text = "Password Reset",
                color = purrFectColor(Color(0xFF72798B), Color(0xFFA8A3B5)),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp, bottom = 0.dp)
            )

            SettingsCard {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = when (passwordStep) {
                            0 -> "Send verification code"
                            1 -> "Verify your OTP"
                            else -> "Create a new password"
                        },
                        color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = when (passwordStep) {
                            0 -> "We'll send a 6-digit code to your registered email."
                            1 -> "Enter the 6-digit code sent to $email."
                            else -> "Choose a new password with at least 8 characters."
                        },
                        color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    if (passwordStep == 0) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text("Email") },
                            leadingIcon = {
                                Icon(Icons.Outlined.Email, contentDescription = null, tint = Pink)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Pink,
                                unfocusedBorderColor = purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                                focusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326)),
                                unfocusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326))
                            ),
                            shape = RoundedCornerShape(17.dp)
                        )
                        Button(
                            onClick = {
                                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
                                    showError("Enter a valid email address")
                                    return@Button
                                }
                                loading = true
                                scope.launch {
                                    try {
                                        PurrFectApi.forgotPassword(email)
                                        passwordStep = 1
                                        Toast.makeText(context, "Verification code sent", Toast.LENGTH_SHORT).show()
                                    } catch (error: Exception) {
                                        showError(error.message ?: "Could not send verification code")
                                    } finally {
                                        loading = false
                                    }
                                }
                            },
                            enabled = !loading,
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            shape = RoundedCornerShape(17.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Pink)
                        ) {
                            Text(if (loading) "Sending..." else "Send OTP", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    } else if (passwordStep == 1) {
                        OutlinedTextField(
                            value = otp,
                            onValueChange = { value -> otp = value.filter(Char::isDigit).take(6) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text("6-digit OTP") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            leadingIcon = {
                                Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = Pink)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Pink,
                                unfocusedBorderColor = purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                                focusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326)),
                                unfocusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326))
                            ),
                            shape = RoundedCornerShape(17.dp)
                        )
                        Button(
                            onClick = {
                                if (otp.length != 6) {
                                    showError("Enter the 6-digit OTP")
                                    return@Button
                                }
                                loading = true
                                scope.launch {
                                    try {
                                        val response = PurrFectApi.verifyResetOtp(email, otp)
                                        resetToken = response.optString("reset_token")
                                        if (resetToken.isBlank()) throw IllegalStateException("Reset session could not be created")
                                        passwordStep = 2
                                        Toast.makeText(context, "OTP verified", Toast.LENGTH_SHORT).show()
                                    } catch (error: Exception) {
                                        showError(error.message ?: "Invalid or expired OTP")
                                    } finally {
                                        loading = false
                                    }
                                }
                            },
                            enabled = !loading,
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            shape = RoundedCornerShape(17.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Pink)
                        ) {
                            Text(if (loading) "Verifying..." else "Verify OTP", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        TextButton(
                            onClick = { passwordStep = 0; otp = "" },
                            enabled = !loading,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Use another email", color = Pink)
                        }
                    } else {
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text("New password") },
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = {
                                Icon(Icons.Outlined.Lock, contentDescription = null, tint = Pink)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Pink,
                                unfocusedBorderColor = purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                                focusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326)),
                                unfocusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326))
                            ),
                            shape = RoundedCornerShape(17.dp)
                        )
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text("Confirm new password") },
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIcon = {
                                Icon(Icons.Outlined.Lock, contentDescription = null, tint = Pink)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Pink,
                                unfocusedBorderColor = purrFectColor(Color(0xFFE2D9D5), Color(0xFF302A46)),
                                focusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326)),
                                unfocusedContainerColor = purrFectColor(Color(0xFFFFFCFA), Color(0xFF151326))
                            ),
                            shape = RoundedCornerShape(17.dp)
                        )
                        Button(
                            onClick = {
                                when {
                                    newPassword.length < 8 -> showError("Password must be at least 8 characters")
                                    newPassword != confirmPassword -> showError("Passwords do not match")
                                    resetToken.isBlank() -> showError("Password reset session expired")
                                    else -> {
                                        loading = true
                                        scope.launch {
                                            try {
                                                PurrFectApi.resetPassword(email, resetToken, newPassword)
                                                Toast.makeText(context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                                                passwordStep = 0
                                                otp = ""
                                                newPassword = ""
                                                confirmPassword = ""
                                                resetToken = ""
                                            } catch (error: Exception) {
                                                showError(error.message ?: "Could not change password")
                                            } finally {
                                                loading = false
                                            }
                                        }
                                    }
                                }
                            },
                            enabled = !loading,
                            modifier = Modifier.fillMaxWidth().height(54.dp),
                            shape = RoundedCornerShape(17.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Pink)
                        ) {
                            Text(if (loading) "Saving..." else "Change Password", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(purrFectColor(Color(0xFFFFF0F2), Color(0xFF211E35)))
                    .clickable { showDeleteDialog = true }
                    .padding(horizontal = 16.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete account",
                    tint = purrFectColor(Color(0xFFE24B62), Color(0xFFC74256)),
                    modifier = Modifier.size(27.dp)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Delete Account", color = purrFectColor(Color(0xFFE24B62), Color(0xFFC74256)), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Text("Permanently remove your account", color = purrFectColor(Color(0xFF8B7075), Color(0xFFA8A3B5)), fontSize = 12.sp)
                }
                Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = purrFectColor(Color(0xFF73798A), Color(0xFFA8A3B5)))
            }

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)))
            ) {
                Icon(Icons.Outlined.Logout, contentDescription = null, tint = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)))
                Spacer(modifier = Modifier.width(10.dp))
                Text("Logout", color = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
    }

    if (showSecurityInfo) {
        AlertDialog(
            onDismissRequest = { showSecurityInfo = false },
            title = { Text("Security", fontWeight = FontWeight.Bold) },
            text = {
                Text("Your PurrFect password is stored securely on the backend. Password changes require email verification and a one-time OTP.")
            },
            confirmButton = {
                TextButton(onClick = { showSecurityInfo = false }) { Text("OK", color = Pink) }
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Account?", fontWeight = FontWeight.Bold) },
            text = { Text("Account deletion is not enabled yet. Your account will not be changed.") },
            confirmButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("OK", color = Pink, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel", color = TextGrey) }
            }
        )
    }
}

@Composable
private fun AccountSecurityInfoCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBackground: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(purrFectColor(Color(0xFFFFFDFB), Color(0xFF151326)))
            .border(1.dp, purrFectColor(Color(0xFFF2E5E2), Color(0xFF211E35)), RoundedCornerShape(25.dp))
            .padding(horizontal = 14.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(26.dp))
        }
        Spacer(modifier = Modifier.width(15.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(3.dp))
            Text(value, color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)), fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

/* =========================================================
PRIVACY SCREEN
========================================================= */
@Composable
fun PrivacyScreen(
    onlineStatusEnabled: Boolean,
    onOnlineStatusChange: (Boolean) -> Unit,
    onAccountSecurity: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                    modifier = Modifier.size(25.dp)
                )
            }
            Text(
                text = "Privacy",
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.Shield,
                contentDescription = null,
                tint = purrFectColor(Color(0xFF7952C8), Color(0xFF8A5BD1)),
                modifier = Modifier.padding(end = 12.dp).size(25.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(bottom = 28.dp)
        ) {
            Text(
                text = "Control what PurrFect shares and how your presence appears to other users.",
                color = purrFectColor(Color(0xFF6F6870), Color(0xFFB8B3C5)),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))
            SettingsSectionTitle("Visibility")
            SettingsCard {
                SettingsSwitchRow(
                    icon = Icons.Outlined.Visibility,
                    iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)),
                    title = "Online Status",
                    subtitle = if (onlineStatusEnabled) "Others can see when you are online" else "Your online status is hidden",
                    checked = onlineStatusEnabled,
                    onCheckedChange = onOnlineStatusChange
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("Location & Discovery")
            SettingsCard {
                SettingsNavigationRow(
                    icon = Icons.Outlined.LocationOn,
                    iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)),
                    title = "Location Access",
                    subtitle = "PurrFect currently does not request device location permission",
                    onClick = { }
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.Visibility,
                    iconColor = purrFectColor(Color(0xFF7952C8), Color(0xFF8A5BD1)),
                    iconBackground = purrFectColor(Color(0xFFF0E5FF), Color(0xFF211E35)),
                    title = "Profile Visibility",
                    subtitle = "Discover profiles are controlled by the existing cat-profile system",
                    onClick = { }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("Account & Data")
            SettingsCard {
                SettingsNavigationRow(
                    icon = Icons.Outlined.Lock,
                    iconColor = purrFectColor(Color(0xFFE88B4A), Color(0xFFE08648)),
                    iconBackground = purrFectColor(Color(0xFFFFEBDC), Color(0xFF211E35)),
                    title = "Account & Security",
                    subtitle = "Review your account information and security",
                    onClick = onAccountSecurity
                )
            }

        }
    }
}

@Composable
fun HelpSupportScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val background = purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326))
    val cardBackground = purrFectColor(Color(0xFFFFFDFB), Color(0xFF1D1A30))
    val primaryText = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA))
    val secondaryText = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5))
    val border = purrFectColor(Color(0xFFFFD8DF), Color(0xFF2A2640))
    val accent = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A))
    val accentSoft = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35))

    var expandedFaq by remember { mutableStateOf<String?>(null) }

    val faqs = listOf(
        "How do I create or edit a cat profile?" to
                "Open My Cat from the bottom navigation or Settings → My Cat Profiles. You can add a cat, select an existing profile, and edit its information and photos.",
        "How do matches work?" to
                "Use Discover to explore cat profiles and like cats you are interested in. When the relevant like relationship is created, PurrFect can show the connection in Matches.",
        "Why am I not receiving notifications?" to
                "Open Settings → Notifications and make sure notifications are enabled. You can also review the individual notification categories from the notification settings screen.",
        "How can I control my online status?" to
                "Open Settings → Privacy. The Online Status control manages whether your cat's presence is sent as online or offline through the existing presence feature.",
        "How do I change between light and dark mode?" to
                "Open Settings and use the Dark Mode switch. Your preference is saved locally and applied when the app is used again.",
        "How do I change the distance unit?" to
                "Open Settings → Distance Unit and choose Kilometres (km) or Miles (mi). The selected preference is saved locally."
    )

    fun openEmailComposer() {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("mailto:")
        ).apply {
            putExtra(Intent.EXTRA_SUBJECT, "PurrFect Support Request")
            putExtra(
                Intent.EXTRA_TEXT,
                "Hello PurrFect Team,\n\nI need help with:\n\nDevice/Android version:\n\nWhat happened:\n\nSteps to reproduce:\n\nThank you."
            )
        }
        try {
            context.startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(
                context,
                "No email app is available on this device",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = primaryText,
                    modifier = Modifier.size(25.dp)
                )
            }
            Text(
                text = "Help & Support",
                color = primaryText,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.HelpOutline,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(27.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(bottom = 28.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(accentSoft)
                    .border(1.dp, border, RoundedCornerShape(28.dp))
                    .padding(horizontal = 20.dp, vertical = 22.dp)
            ) {
                Column {
                    Text(
                        text = "How can we help?",
                        color = primaryText,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = "Find quick answers to common PurrFect questions or prepare a support request.",
                        color = secondaryText,
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            SettingsSectionTitle("Frequently Asked Questions")

            SettingsCard {
                faqs.forEachIndexed { index, faq ->
                    val question = faq.first
                    val answer = faq.second
                    val expanded = expandedFaq == question

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedFaq = if (expanded) null else question
                            }
                            .padding(horizontal = 17.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(accentSoft),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "?",
                                color = accent,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = question,
                                color = primaryText,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 20.sp
                            )
                            if (expanded) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = answer,
                                    color = secondaryText,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (expanded) "Collapse" else "Expand",
                            tint = secondaryText,
                            modifier = Modifier.size(23.dp)
                        )
                    }

                    if (index < faqs.lastIndex) {
                        SettingsDivider()
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            SettingsSectionTitle("Contact Support")

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(cardBackground)
                    .border(1.dp, border, RoundedCornerShape(24.dp))
                    .clickable { openEmailComposer() }
                    .padding(horizontal = 18.dp, vertical = 18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(accentSoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                            tint = accent,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Email Support",
                            color = primaryText,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "Open your email app with a ready-to-fill support request.",
                            color = secondaryText,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = secondaryText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Tip: When reporting a problem, include what you were doing, what happened, and your Android version. Avoid sharing passwords, verification codes, API keys or other private credentials.",
                color = secondaryText,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 6.dp)
            )
        }
    }
}

/* =========================================================
ABOUT PURRFECT SCREEN
========================================================= */
@Composable
fun AboutPurrFectScreen(
    onBack: () -> Unit
) {
    var showTermsDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                    modifier = Modifier.size(25.dp)
                )
            }
            Text(
                text = "About PurrFect",
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                modifier = Modifier.padding(end = 12.dp).size(25.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(purrFectColor(Color(0xFFFFFDFB), Color(0xFF211E35)))
                    .border(1.dp, purrFectColor(Color(0xFFF2E5E2), Color(0xFF3A344D)), RoundedCornerShape(28.dp))
                    .padding(horizontal = 22.dp, vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(R.drawable.purrfect_logo),
                        contentDescription = "PurrFect logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth(0.68f)
                            .height(145.dp)
                    )
                    Text(
                        text = "Version 1.0",
                        color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("About")
            SettingsCard {
                Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp)) {
                    Text(
                        text = "PurrFect is a cat-focused social and adoption app designed to help cat lovers discover cats, create cat profiles, find matches, chat and explore adoption opportunities.",
                        color = purrFectColor(Color(0xFF6F6870), Color(0xFFB8B3C5)),
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("Features")
            SettingsCard {
                AboutFeatureRow(Icons.Outlined.Search, "Discover Cats", "Explore cat profiles around you")
                SettingsDivider()
                AboutFeatureRow(Icons.Outlined.FavoriteBorder, "Matches & Likes", "Connect with cats you like")
                SettingsDivider()
                AboutFeatureRow(Icons.Outlined.ChatBubbleOutline, "Chat", "Talk with other cat lovers")
                SettingsDivider()
                AboutFeatureRow(Icons.Outlined.Home, "Adoption", "Discover cats looking for a home")
                SettingsDivider()
                AboutFeatureRow(Icons.Outlined.Pets, "My Cat Profiles", "Create and manage your cat profiles")
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("Legal & Information")
            SettingsCard {
                SettingsNavigationRow(
                    icon = Icons.Outlined.Description,
                    iconColor = purrFectColor(Color(0xFFE88B4A), Color(0xFFE08648)),
                    iconBackground = purrFectColor(Color(0xFFFFEBDC), Color(0xFF211E35)),
                    title = "Terms & Conditions",
                    subtitle = "App usage terms",
                    onClick = { showTermsDialog = true }
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.Shield,
                    iconColor = purrFectColor(Color(0xFF7952C8), Color(0xFF8A5BD1)),
                    iconBackground = purrFectColor(Color(0xFFF0E5FF), Color(0xFF211E35)),
                    title = "Privacy Policy",
                    subtitle = "How app data is handled",
                    onClick = { showPrivacyDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Developed by PurrFect Team",
                color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "© 2026 PurrFect",
                color = purrFectColor(Color(0xFF9A9298), Color(0xFF777185)),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Made with ❤️ for cat lovers",
                color = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }

    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            title = { Text("Terms & Conditions", fontWeight = FontWeight.Bold) },
            text = { Text("PurrFect is intended for responsible use by its users. App features, content and availability may change as the application develops.") },
            confirmButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text("Close", color = Pink)
                }
            }
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy Policy", fontWeight = FontWeight.Bold) },
            text = { Text("PurrFect uses account and cat-profile information to provide its features. You can review available privacy controls from Settings → Privacy.") },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Close", color = Pink)
                }
            }
        )
    }
}

@Composable
private fun AboutFeatureRow(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35))),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitle,
                color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                fontSize = 13.sp
            )
        }
    }
}

/* =========================================================
SETTINGS SCREEN
========================================================= */
@Composable
fun SettingsScreen(
    profile: CatProfile,
    user: BackendUser?,
    onBack: () -> Unit,
    onEditProfile: () -> Unit,
    onMyCatProfiles: () -> Unit,
    onAccountSecurity: () -> Unit,
    notificationsEnabled: Boolean,
    onNotificationsEnabledChange: (Boolean) -> Unit,
    darkModeEnabled: Boolean,
    onDarkModeEnabledChange: (Boolean) -> Unit,
    distanceUnit: String,
    onDistanceUnitChange: (String) -> Unit,
    language: String,
    onLanguageChange: (String) -> Unit,
    onNotificationsPage: () -> Unit,
    onPrivacyPage: () -> Unit,
    onHelpSupportPage: () -> Unit,
    onAboutPage: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    var showDistanceDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val displayName = user?.name?.takeIf { it.isNotBlank() } ?: "PurrFect User"
    val username = user?.name
        ?.trim()
        ?.lowercase(Locale.getDefault())
        ?.replace("[^a-z0-9]".toRegex(), "")
        ?.takeIf { it.isNotBlank() }
        ?.let { "@$it" }
        ?: "@user"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326)))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                    modifier = Modifier.size(25.dp)
                )
            }
            Text(
                text = "Settings",
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "♥",
                color = purrFectColor(Color(0xFFFFB5C2), Color(0xFFFF8FC0)),
                fontSize = 25.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(bottom = 28.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(purrFectColor(Color(0xFFFFFDFB), Color(0xFF151326)))
                    .border(1.dp, purrFectColor(Color(0xFFFFD8DF), Color(0xFF211E35)), RoundedCornerShape(28.dp))
                    .clickable(onClick = onEditProfile)
                    .padding(horizontal = 18.dp, vertical = 18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(78.dp)
                            .clip(CircleShape)
                            .border(3.dp, purrFectColor(Color(0xFFFF6684), Color(0xFFF45A9A)), CircleShape)
                    ) {
                        if (profile.photoBitmap != null) {
                            Image(
                                bitmap = profile.photoBitmap.asImageBitmap(),
                                contentDescription = "Profile photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.simba),
                                contentDescription = "Profile photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = displayName,
                            color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = username,
                            color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                            fontSize = 14.sp
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        tint = purrFectColor(Color(0xFF73798A), Color(0xFFA8A3B5)),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("Account")
            SettingsCard {
                SettingsNavigationRow(
                    icon = Icons.Outlined.PersonOutline,
                    iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)),
                    title = "Edit Profile",
                    subtitle = "Update your name, bio and photos",
                    onClick = onEditProfile
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.Pets,
                    iconColor = purrFectColor(Color(0xFF7952C8), Color(0xFF8A5BD1)),
                    iconBackground = purrFectColor(Color(0xFFF0E5FF), Color(0xFF211E35)),
                    title = "My Cat Profiles",
                    subtitle = "Manage your cats",
                    onClick = onMyCatProfiles
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.Lock,
                    iconColor = purrFectColor(Color(0xFFE88B4A), Color(0xFFE08648)),
                    iconBackground = purrFectColor(Color(0xFFFFEBDC), Color(0xFF211E35)),
                    title = "Account & Security",
                    subtitle = "Email, password and security",
                    onClick = onAccountSecurity
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("Preferences")
            SettingsCard {
                SettingsNavigationRow(
                    icon = Icons.Outlined.Notifications,
                    iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)),
                    title = "Notifications",
                    subtitle = "Manage notification preferences",
                    onClick = onNotificationsPage
                )
                SettingsDivider()
                SettingsSwitchRow(
                    icon = Icons.Outlined.DarkMode,
                    iconColor = purrFectColor(Color(0xFF7952C8), Color(0xFF8A5BD1)),
                    iconBackground = purrFectColor(Color(0xFFF0E5FF), Color(0xFF211E35)),
                    title = "Dark Mode",
                    subtitle = "Switch between light and dark theme",
                    checked = darkModeEnabled,
                    onCheckedChange = onDarkModeEnabledChange
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.Language,
                    iconColor = purrFectColor(Color(0xFFE88B4A), Color(0xFFE08648)),
                    iconBackground = purrFectColor(Color(0xFFFFEBDC), Color(0xFF211E35)),
                    title = "Language",
                    subtitle = language,
                    onClick = { showLanguageDialog = true }
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.LocationOn,
                    iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)),
                    title = "Distance Unit",
                    subtitle = distanceUnit,
                    onClick = { showDistanceDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            SettingsSectionTitle("App")
            SettingsCard {
                SettingsNavigationRow(
                    icon = Icons.Outlined.Shield,
                    iconColor = purrFectColor(Color(0xFF7952C8), Color(0xFF8A5BD1)),
                    iconBackground = purrFectColor(Color(0xFFF0E5FF), Color(0xFF211E35)),
                    title = "Privacy",
                    subtitle = "Manage your data and visibility",
                    onClick = onPrivacyPage
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.HelpOutline,
                    iconColor = purrFectColor(Color(0xFFE88B4A), Color(0xFFE08648)),
                    iconBackground = purrFectColor(Color(0xFFFFEBDC), Color(0xFF211E35)),
                    title = "Help & Support",
                    subtitle = "FAQs, troubleshooting and contact",
                    onClick = onHelpSupportPage
                )
                SettingsDivider()
                SettingsNavigationRow(
                    icon = Icons.Outlined.Info,
                    iconColor = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    iconBackground = purrFectColor(Color(0xFFFFE4E9), Color(0xFF211E35)),
                    title = "About PurrFect",
                    subtitle = "App version, features and information",
                    onClick = onAboutPage
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(purrFectColor(Color(0xFFFFE7EC), Color(0xFF211E35)))
                    .clickable { showLogoutDialog = true }
                    .padding(horizontal = 20.dp, vertical = 17.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Logout,
                    contentDescription = "Logout",
                    tint = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    modifier = Modifier.size(27.dp)
                )
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    text = "Logout",
                    color = purrFectColor(Color(0xFFE95270), Color(0xFFF45A9A)),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = purrFectColor(Color(0xFF73798A), Color(0xFFA8A3B5)),
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Language", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onLanguageChange("English")
                                showLanguageDialog = false
                            }
                            .padding(horizontal = 14.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = language == "English",
                            onClick = {
                                onLanguageChange("English")
                                showLanguageDialog = false
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Pink
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "English",
                                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Currently supported",
                                color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text("Close", color = Pink)
                }
            }
        )
    }

    if (showDistanceDialog) {
        AlertDialog(
            onDismissRequest = { showDistanceDialog = false },
            title = { Text("Distance Unit", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    TextButton(onClick = {
                        onDistanceUnitChange("Kilometres (km)")
                        showDistanceDialog = false
                    }) { Text("Kilometres (km)", color = Pink) }
                    TextButton(onClick = {
                        onDistanceUnitChange("Miles (mi)")
                        showDistanceDialog = false
                    }) { Text("Miles (mi)", color = Pink) }
                }
            },
            confirmButton = {
                TextButton(onClick = { showDistanceDialog = false }) {
                    Text("Close", color = TextGrey)
                }
            }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout?", fontWeight = FontWeight.Bold) },
            text = { Text("Are you sure you want to logout from PurrFect?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) { Text("Logout", color = Pink, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = TextGrey)
                }
            }
        )
    }
}

@Composable
private fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        color = purrFectColor(Color(0xFF72798B), Color(0xFFA8A3B5)),
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(27.dp))
            .background(purrFectColor(Color(0xFFFFFDFB), Color(0xFF151326)))
            .border(1.dp, purrFectColor(Color(0xFFF2E5E2), Color(0xFF211E35)), RoundedCornerShape(27.dp)),
        content = content
    )
}

@Composable
private fun SettingsDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 72.dp, end = 18.dp)
            .height(1.dp)
            .background(purrFectColor(Color(0xFFF0E6E3), Color(0xFF211E35)))
    )
}

@Composable
private fun SettingsNavigationRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBackground: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(27.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = purrFectColor(Color(0xFF73798A), Color(0xFFA8A3B5)),
            modifier = Modifier.size(25.dp)
        )
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBackground: Color,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(iconBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(27.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)),
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = purrFectColor(Color(0xFF7C8294), Color(0xFFA8A3B5)),
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
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
    var adoptionEnabled by remember {
        mutableStateOf(profile.adoptionIntent.equals("offer", ignoreCase = true))
    }

    // Edit Profile entrance animation: photo, form content and save button
    // animate independently without changing any existing functionality.
    val photoEntrance = remember { Animatable(0f) }
    val formEntrance = remember { Animatable(0f) }
    val saveEntrance = remember { Animatable(0f) }
    val savePressScale = remember { Animatable(1f) }
    val savePressScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        launch {
            photoEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500)
            )
        }
        launch {
            formEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 90
                )
            )
        }
        launch {
            saveEntrance.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 450,
                    delayMillis = 220
                )
            )
        }
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
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = formEntrance.value
                    translationY = (1f - formEntrance.value) * 18.dp.toPx()
                },
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
                            .graphicsLayer {
                                alpha = photoEntrance.value
                                val scale = 0.88f + (0.12f * photoEntrance.value)
                                scaleX = scale
                                scaleY = scale
                                translationY = (1f - photoEntrance.value) * 14.dp.toPx()
                            }
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
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = photoEntrance.value
                                translationY = (1f - photoEntrance.value) * 8.dp.toPx()
                            }
                            .clickable {
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Available for adoption",
                                color = TextDark,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = if (adoptionEnabled) "Your cat will appear in Adopt a cat." else "Your cat will not appear in adoption listings.",
                                color = TextGrey,
                                fontSize = 12.sp
                            )
                        }
                        Switch(
                            checked = adoptionEnabled,
                            onCheckedChange = { adoptionEnabled = it }
                        )
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        savePressScope.launch {
                            savePressScale.snapTo(0.96f)
                            savePressScale.animateTo(1f, spring(dampingRatio = 0.6f, stiffness = 650f))
                        }
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
                                adoptionIntent = if (adoptionEnabled) "offer" else "none",
                                photoBitmap =
                                    selectedPhotoBitmap
                            )
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .graphicsLayer {
                                alpha = saveEntrance.value
                                val entranceScale = 0.96f + (0.04f * saveEntrance.value)
                                scaleX = entranceScale * savePressScale.value
                                scaleY = entranceScale * savePressScale.value
                                translationY = (1f - saveEntrance.value) * 10.dp.toPx()
                            },
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
    val scope = rememberCoroutineScope()
    val pressScale = remember { Animatable(1f) }

    Box(
        modifier = Modifier
            .graphicsLayer {
                scaleX = pressScale.value
                scaleY = pressScale.value
            }
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
            onClick = {
                onClick()
                scope.launch {
                    pressScale.snapTo(0.88f)
                    pressScale.animateTo(1f, spring(dampingRatio = 0.55f, stiffness = 700f))
                }
            }
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
    val scope = rememberCoroutineScope()
    val pressScale = remember { Animatable(1f) }
    Box(
        modifier = Modifier
            .graphicsLayer {
                scaleX = pressScale.value
                scaleY = pressScale.value
            }
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
                scope.launch {
                    pressScale.snapTo(0.88f)
                    pressScale.animateTo(1f, spring(dampingRatio = 0.55f, stiffness = 700f))
                }
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
    val iconScale by animateFloatAsState(
        targetValue = if (selected) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = 0.65f,
            stiffness = 500f
        ),
        label = "bottom_nav_icon_scale"
    )

    val labelAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0.72f,
        animationSpec = tween(durationMillis = 180),
        label = "bottom_nav_label_alpha"
    )

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
                    purrFectColor(Color(0xFF81787B), Color(0xFFA8A3B5))
                },
            modifier = Modifier
                .size(21.dp)
                .graphicsLayer {
                    scaleX = iconScale
                    scaleY = iconScale
                }
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
                    purrFectColor(Color(0xFF81787B), Color(0xFFA8A3B5))
                },
            fontSize =
                9.sp,
            fontWeight =
                if (selected) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                },
            modifier = Modifier.graphicsLayer {
                alpha = labelAlpha
            }
        )
    }
}

/* =========================================================
ADOPTION SCREEN
========================================================= */
@Composable
fun AdoptionScreen(
    referenceCatId: Int,
    onBack: () -> Unit,
    onCatClick: (AdoptionCat) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableIntStateOf(0) }
    var adoptionCats by remember { mutableStateOf<List<AdoptionCat>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var hasLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(referenceCatId, searchQuery) {
        isLoading = true
        adoptionCats = PurrFectApi.getAdoptionCats(
            referenceCatId = referenceCatId,
            search = searchQuery
        )
        hasLoaded = true
        isLoading = false
    }

    val filteredCats = adoptionCats.filter { cat ->
        when (selectedCategory) {
            1 -> {
                val age = cat.age.toDoubleOrNull() ?: 0.0
                age < 1.0
            }
            2 -> {
                val age = cat.age.toDoubleOrNull() ?: 0.0
                age >= 1.0 && age < 8.0
            }
            3 -> {
                cat.gender.trim().equals("male", ignoreCase = true)
            }
            else -> true
        }
    }

    val categories = listOf(
        "All cats" to "🐾",
        "Kittens" to "🐾",
        "Adults" to "🐱",
        "Male" to "♂"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 22.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Back",
                    tint = TextDark,
                    modifier = Modifier.size(28.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Adopt a cat",
                    color = TextDark,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "🐾", fontSize = 20.sp, color = Pink)
            }
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = TextDark)) { append("Give a Cat\na ") }
                    withStyle(style = SpanStyle(color = Pink)) { append("Loving") }
                    withStyle(style = SpanStyle(color = TextDark)) { append(" Home. ❤️") }
                },
                fontSize = 32.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Adopt. Love. Create a Purrfect Bond",
                color = TextGrey,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    "Search cats, breeds, or locations...",
                    color = TextGrey,
                    fontSize = 14.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BorderColor,
                unfocusedBorderColor = BorderColor,
                focusedContainerColor = purrFectColor(Color(0xFFF9F2F0), Color(0xFF211E35)),
                unfocusedContainerColor = purrFectColor(Color(0xFFF9F2F0), Color(0xFF211E35))
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            categories.forEachIndexed { index, (label, emoji) ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { selectedCategory = index }
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(if (selectedCategory == index) LightPink else purrFectColor(Color(0xFFF1EAE8), Color(0xFF211E35)))
                            .border(
                                1.dp,
                                if (selectedCategory == index) Pink.copy(alpha = 0.3f) else Color.Transparent,
                                RoundedCornerShape(18.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (emoji.isNotBlank()) {
                            Text(text = emoji, fontSize = 28.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = label,
                        color = TextGrey,
                        fontSize = 12.sp,
                        fontWeight = if (selectedCategory == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Cats looking for homes",
            color = TextDark,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading && !hasLoaded -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Pink)
                }
            }
            filteredCats.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🐱", fontSize = 52.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (searchQuery.isBlank()) "No cats are available for adoption yet" else "No cats found",
                        color = TextDark,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (searchQuery.isBlank()) "Cats listed for adoption will appear here." else "Try another name, breed, or search term.",
                        color = TextGrey,
                        fontSize = 13.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filteredCats.size) { index ->
                        val cat = filteredCats[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .clip(RoundedCornerShape(22.dp))
                                .background(purrFectColor(Color(0xFFFAF1F0), Color(0xFF211E35)))
                                .clickable { onCatClick(cat) }
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(purrFectColor(Color(0xFFE9E0DE), Color(0xFF211E35))),
                                contentAlignment = Alignment.Center
                            ) {
                                if (cat.photoBitmap != null) {
                                    Image(
                                        bitmap = cat.photoBitmap.asImageBitmap(),
                                        contentDescription = cat.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Text("🐱", fontSize = 24.sp)
                                }
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = cat.name,
                                    color = TextDark,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${cat.breed} • ${cat.age} years",
                                    color = TextGrey,
                                    fontSize = 13.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (cat.distanceKm != null) {
                                    Text(
                                        text = if (cat.distanceKm < 1.0) "${(cat.distanceKm * 1000).toInt()} m away" else String.format("%.1f km away", cat.distanceKm),
                                        color = Pink,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "View cat",
                                tint = Pink,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


/* =========================================================
ADOPTION DETAILS SCREEN
========================================================= */
@Composable
fun AdoptionDetailsScreen(
    cat: AdoptionCat,
    currentCatId: Int,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var showRequestDialog by remember { mutableStateOf(false) }
    var showMessageDialog by remember { mutableStateOf(false) }
    var requestMessage by remember { mutableStateOf("") }
    var isSendingRequest by remember { mutableStateOf(false) }
    var actionMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.weight(0.1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }
            Text(
                text = "Cat details",
                color = TextDark,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.8f).padding(end = 32.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(purrFectColor(Color(0xFFE0D8D6), Color(0xFF302A46)))
            ) {
                if (cat.photoBitmap != null) {
                    Image(
                        bitmap = cat.photoBitmap.asImageBitmap(),
                        contentDescription = cat.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = "🐱",
                        fontSize = 120.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(20.dp)
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = TextDark,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = cat.name,
                color = TextDark,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(cat.breed, "${cat.age} years", cat.gender).forEach { value ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(LightPink)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = value,
                            color = Pink,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .border(1.dp, BorderColor, RoundedCornerShape(28.dp))
                    .padding(24.dp)
            ) {
                Text("About", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(cat.about, color = TextGrey, fontSize = 15.sp, lineHeight = 22.sp)

                Spacer(modifier = Modifier.height(22.dp))

                Text("Location", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cat.distanceKm?.let {
                        if (it < 1.0) "${(it * 1000).toInt()} m away" else String.format("%.1f km away", it)
                    } ?: "Location not available",
                    color = TextGrey,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text("Posted by", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Cat owner", color = TextGrey, fontSize = 15.sp)

                if (cat.createdAt.isNotBlank()) {
                    Spacer(modifier = Modifier.height(22.dp))
                    Text("Posted on", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(cat.createdAt, color = TextGrey, fontSize = 15.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text("Personality", color = TextDark, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(cat.personality, color = TextGrey, fontSize = 14.sp)
                Text("Favorite Activities", color = TextDark, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(cat.activities, color = TextGrey, fontSize = 14.sp)
                Text("Health Info", color = TextDark, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(cat.health, color = TextGrey, fontSize = 14.sp)
                Text("Looking For", color = TextDark, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(cat.lookingFor, color = TextGrey, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(62.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .border(2.dp, Pink, RoundedCornerShape(32.dp))
                        .clickable { showMessageDialog = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Message", color = Pink, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(62.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(Pink)
                        .clickable {
                            requestMessage = "I would like to adopt ${cat.name}."
                            showRequestDialog = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Request to Adopt", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    if (showMessageDialog) {
        AlertDialog(
            onDismissRequest = { showMessageDialog = false },
            title = { Text("Message ${cat.name}", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Direct messaging is available after an adoption request is accepted. You can send your request below to start the adoption process.",
                    color = TextGrey
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showMessageDialog = false
                    requestMessage = "Hi! I am interested in adopting ${cat.name}."
                    showRequestDialog = true
                }) { Text("Send adoption request", color = Pink) }
            },
            dismissButton = {
                TextButton(onClick = { showMessageDialog = false }) { Text("Cancel", color = TextGrey) }
            }
        )
    }

    if (showRequestDialog) {
        AlertDialog(
            onDismissRequest = { if (!isSendingRequest) showRequestDialog = false },
            title = { Text("Request to adopt ${cat.name}", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Send a short message to the owner.", color = TextGrey, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = requestMessage,
                        onValueChange = { if (it.length <= 1000) requestMessage = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        maxLines = 6,
                        enabled = !isSendingRequest,
                        placeholder = { Text("Write a message...") },
                        supportingText = { Text("${requestMessage.length}/1000") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    enabled = !isSendingRequest && currentCatId > 0 && requestMessage.isNotBlank(),
                    onClick = {
                        scope.launch {
                            isSendingRequest = true
                            try {
                                val response = PurrFectApi.createAdoptionRequest(cat.id, currentCatId, requestMessage)
                                actionMessage = response.optString("message", "Adoption request sent successfully")
                                showRequestDialog = false
                            } catch (error: Exception) {
                                actionMessage = error.message ?: "Could not send adoption request"
                            } finally {
                                isSendingRequest = false
                            }
                        }
                    }
                ) {
                    Text(if (isSendingRequest) "Sending..." else "Send Request", color = Pink)
                }
            },
            dismissButton = {
                TextButton(enabled = !isSendingRequest, onClick = { showRequestDialog = false }) {
                    Text("Cancel", color = TextGrey)
                }
            }
        )
    }

    actionMessage?.let { message ->
        AlertDialog(
            onDismissRequest = { actionMessage = null },
            title = { Text("Adoption", fontWeight = FontWeight.Bold) },
            text = { Text(message) },
            confirmButton = { TextButton(onClick = { actionMessage = null }) { Text("OK", color = Pink) } }
        )
    }
}





/* =========================================================
NOTIFICATIONS SCREEN
========================================================= */
@Composable
private fun formatNotificationTime(raw: String): String {
    if (raw.isBlank()) return ""

    return try {
        val normalized = raw.replace("Z", "+0000")
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)
        val date = input.parse(normalized) ?: return raw
        val now = System.currentTimeMillis()
        val diff = now - date.time

        val timeExact = SimpleDateFormat("h:mm a", Locale.US).format(Date(date.time))
        val timeAgo = when {
            diff < 60_000L -> "Just now"
            diff < 60 * 60_000L -> "${diff / 60_000L} minutes ago"
            diff < 24 * 60 * 60_000L -> "${diff / (60 * 60_000L)} hours ago"
            diff < 7 * 24 * 60 * 60_000L -> "${diff / (24 * 60 * 60_000L)} days ago"
            else -> SimpleDateFormat("dd MMM", Locale.US).format(Date(date.time))
        }
        "$timeAgo • $timeExact"
    } catch (_: Exception) {
        raw
    }
}

@Composable
private fun SleepingCatIllustration(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_caught_up_cat),
        contentDescription = "Sleeping cat illustration",
        modifier = modifier.size(310.dp, 220.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun NotificationEmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFCF9F9), Color(0xFF151326))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SleepingCatIllustration()

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "You're all caught up!",
                color = purrFectColor(Color(0xFF1C192B), Color(0xFFF7F3FA)),
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "No more notifications for now.",
                color = purrFectColor(Color(0xFF5A5868), Color(0xFFA8A3B5)),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "We'll let you know when something new happens.",
                color = purrFectColor(Color(0xFF5A5868), Color(0xFFA8A3B5)),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun NotificationPreferenceDivider() {
    androidx.compose.material3.HorizontalDivider(
        color = purrFectColor(Color(0xFFF0E4E5), Color(0xFF211E35)),
        thickness = 1.dp
    )
}

@Composable
private fun NotificationPreferenceRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = if (enabled) purrFectColor(Color(0xFF1C192B), Color(0xFFF7F3FA)) else purrFectColor(Color(0xFF9B9699), Color(0xFFA8A3B5)),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = if (enabled) purrFectColor(Color(0xFF8E8E93), Color(0xFFA8A3B5)) else purrFectColor(Color(0xFFB8B3B5), Color(0xFFC5C0D0)),
                fontSize = 11.sp
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}

@Composable
fun NotificationSettingsScreen(
    notificationsEnabled: Boolean,
    matchesEnabled: Boolean,
    messagesEnabled: Boolean,
    likesEnabled: Boolean,
    adoptionEnabled: Boolean,
    generalEnabled: Boolean,
    onMasterNotificationChange: (Boolean) -> Unit,
    onMatchesChange: (Boolean) -> Unit,
    onMessagesChange: (Boolean) -> Unit,
    onLikesChange: (Boolean) -> Unit,
    onAdoptionChange: (Boolean) -> Unit,
    onGeneralChange: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(purrFectColor(Color(0xFFFFF9F6), Color(0xFF151326)))) {
        Row(
            modifier = Modifier.fillMaxWidth().height(62.dp).padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back to Settings", tint = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)))
            }
            Text("Notification Settings", color = purrFectColor(Color(0xFF20243A), Color(0xFFF7F3FA)), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Notification preferences",
                color = purrFectColor(Color(0xFF1C192B), Color(0xFFF7F3FA)),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(1.dp, purrFectColor(Color(0xFFF0E4E5), Color(0xFF211E35)), RoundedCornerShape(20.dp))
            ) {
                NotificationPreferenceRow(
                    title = "Master notifications",
                    subtitle = "Enable or disable all PurrFect notifications",
                    checked = notificationsEnabled,
                    onCheckedChange = onMasterNotificationChange
                )
                NotificationPreferenceDivider()
                NotificationPreferenceRow(
                    title = "Matches",
                    subtitle = "New mutual matches",
                    checked = matchesEnabled && notificationsEnabled,
                    enabled = notificationsEnabled,
                    onCheckedChange = onMatchesChange
                )
                NotificationPreferenceDivider()
                NotificationPreferenceRow(
                    title = "Messages",
                    subtitle = "New messages from matches",
                    checked = messagesEnabled && notificationsEnabled,
                    enabled = notificationsEnabled,
                    onCheckedChange = onMessagesChange
                )
                NotificationPreferenceDivider()
                NotificationPreferenceRow(
                    title = "Likes / Stars",
                    subtitle = "Likes and starred-cat activity",
                    checked = likesEnabled && notificationsEnabled,
                    enabled = notificationsEnabled,
                    onCheckedChange = onLikesChange
                )
                NotificationPreferenceDivider()
                NotificationPreferenceRow(
                    title = "Adoption activity",
                    subtitle = "Adoption requests and updates",
                    checked = adoptionEnabled && notificationsEnabled,
                    enabled = notificationsEnabled,
                    onCheckedChange = onAdoptionChange
                )
                NotificationPreferenceDivider()
                NotificationPreferenceRow(
                    title = "General activity",
                    subtitle = "Other PurrFect activity",
                    checked = generalEnabled && notificationsEnabled,
                    enabled = notificationsEnabled,
                    onCheckedChange = onGeneralChange
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Composable
fun NotificationsScreen(
    notifications: List<NotificationItem>,
    unreadCount: Int,
    onBack: () -> Unit,
    onMarkRead: (Int) -> Unit,
    onMarkAllRead: () -> Unit,
    onDelete: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(purrFectColor(Color(0xFFFCF9F9), Color(0xFF151326)))
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 16.dp, top = 14.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = purrFectColor(Color(0xFF1C192B), Color(0xFFF7F3FA)),
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Notifications",
                color = purrFectColor(Color(0xFF1C192B), Color(0xFFF7F3FA)),
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )

            if (unreadCount > 0) {
                TextButton(
                    onClick = onMarkAllRead,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Mark all read",
                        color = purrFectColor(Color(0xFFE94057), Color(0xFFFF4F79)),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        if (notifications.isEmpty()) {
            Box(modifier = Modifier.weight(1f)) {
                NotificationEmptyState()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(top = 8.dp, bottom = 28.dp)
            ) {
                items(notifications, key = { it.id }) { notification ->
                    val isUnread = !notification.isRead

                    val cardBackground = if (isUnread) purrFectColor(Color(0xFFFFF0F3), Color(0xFF211E35)) else Color.White
                    val borderColor = if (isUnread) purrFectColor(Color(0xFFFDE0E7), Color(0xFF211E35)) else purrFectColor(Color(0xFFEEEEEE), Color(0xFF211E35))

                    val categoryIcon = when (notification.type.lowercase(Locale.US)) {
                        "match", "like" -> Icons.Outlined.Favorite
                        "message" -> Icons.Outlined.ChatBubbleOutline
                        else -> Icons.Outlined.Email
                    }

                    val titleText = notification.title
                        .replace(" n", "")
                        .replace("¤n", "")
                        .trim()

                    val messageText = notification.message
                        .replace(" n", "")
                        .replace("¤n", "")
                        .trim()

                    val timeText = formatNotificationTime(notification.createdAt)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(22.dp))
                            .background(cardBackground)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(22.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            // Pink Category Icon Box
                            Box(
                                modifier = Modifier
                                    .padding(top = 2.dp)
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(purrFectColor(Color(0xFFFFE3E8), Color(0xFF211E35))),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = categoryIcon,
                                    contentDescription = null,
                                    tint = purrFectColor(Color(0xFFE94057), Color(0xFFFF4F79)),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Main Text Content
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = if (titleText.isNotBlank()) titleText else "New Notification",
                                    color = purrFectColor(Color(0xFF1C192B), Color(0xFFF7F3FA)),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(3.dp))

                                Text(
                                    text = messageText,
                                    color = purrFectColor(Color(0xFF2C283B), Color(0xFFF7F3FA)),
                                    fontSize = 14.sp,
                                    lineHeight = 19.sp
                                )

                                if (timeText.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = timeText,
                                        color = purrFectColor(Color(0xFF8E8E93), Color(0xFFA8A3B5)),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // Unread Red/Pink Dot at Top Right
                            if (isUnread) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(purrFectColor(Color(0xFFE94057), Color(0xFFFF4F79)))
                                )
                            }
                        }

                        // Bottom Action Buttons (Mark read / Delete)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isUnread) {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(purrFectColor(Color(0xFFECE6F6), Color(0xFF211E35)))
                                        .clickable { onMarkRead(notification.id) }
                                        .padding(horizontal = 14.dp, vertical = 7.dp)
                                ) {
                                    Text(
                                        text = "Mark read",
                                        color = purrFectColor(Color(0xFF7C4DFF), Color(0xFF613CC7)),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(purrFectColor(Color(0xFFFFE3E8), Color(0xFF211E35)))
                                    .clickable { onDelete(notification.id) }
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                Text(
                                    text = "Delete",
                                    color = purrFectColor(Color(0xFFE94057), Color(0xFFFF4F79)),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
