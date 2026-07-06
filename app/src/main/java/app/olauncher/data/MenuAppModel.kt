package app.olauncher.data

import org.json.JSONArray
import org.json.JSONObject

data class MenuAppModel(
    val appLabel: String,
    val appPackage: String,
    val activityClassName: String?,
    val userString: String,
    val isShortcut: Boolean = false,
    val shortcutId: String = "",
) {
    private fun toJson(): JSONObject = JSONObject().apply {
        put("appLabel", appLabel)
        put("appPackage", appPackage)
        put("activityClassName", activityClassName ?: "")
        put("userString", userString)
        put("isShortcut", isShortcut)
        put("shortcutId", shortcutId)
    }

    companion object {
        fun listToString(apps: List<MenuAppModel>): String =
            JSONArray().apply { apps.forEach { put(it.toJson()) } }.toString()

        fun listFromString(json: String): MutableList<MenuAppModel> {
            val apps = mutableListOf<MenuAppModel>()
            if (json.isBlank()) return apps
            try {
                val array = JSONArray(json)
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    apps.add(
                        MenuAppModel(
                            appLabel = obj.optString("appLabel"),
                            appPackage = obj.optString("appPackage"),
                            activityClassName = obj.optString("activityClassName"),
                            userString = obj.optString("userString"),
                            isShortcut = obj.optBoolean("isShortcut", false),
                            shortcutId = obj.optString("shortcutId"),
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return apps
        }
    }
}
