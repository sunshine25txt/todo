package com.todapp.tod.data

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

data class User(
    val name: String,
    val email: String,
    val password: String
)

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val done: Boolean = false
)

class AppStore(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("tod_store", Context.MODE_PRIVATE)

    fun getCurrentUser(): User? {
        val email = prefs.getString("session_email", null) ?: return null
        return findUser(email)
    }

    fun findUser(email: String): User? {
        val users = usersJson()
        for (i in 0 until users.length()) {
            val obj = users.getJSONObject(i)
            if (obj.getString("email").equals(email, ignoreCase = true)) {
                return User(
                    name = obj.getString("name"),
                    email = obj.getString("email"),
                    password = obj.getString("password")
                )
            }
        }
        return null
    }

    fun register(name: String, email: String, password: String): String? {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            return "Please fill in every field."
        }
        if (!email.contains("@") || !email.contains(".")) {
            return "Enter a valid email."
        }
        if (password.length < 6) {
            return "Password must be at least 6 characters."
        }
        if (findUser(email) != null) {
            return "An account with this email already exists."
        }
        val users = usersJson()
        users.put(
            JSONObject()
                .put("name", name.trim())
                .put("email", email.trim())
                .put("password", password)
        )
        prefs.edit()
            .putString("users", users.toString())
            .putString("session_email", email.trim())
            .apply()
        seedTasksIfNeeded()
        return null
    }

    fun login(email: String, password: String): String? {
        val user = findUser(email) ?: return "No account found for this email."
        if (user.password != password) return "Incorrect password."
        prefs.edit().putString("session_email", user.email).apply()
        seedTasksIfNeeded()
        return null
    }

    fun logout() {
        prefs.edit().remove("session_email").apply()
    }

    fun resetPassword(email: String, newPassword: String): String? {
        if (newPassword.length < 6) return "Password must be at least 6 characters."
        val users = usersJson()
        var found = false
        for (i in 0 until users.length()) {
            val obj = users.getJSONObject(i)
            if (obj.getString("email").equals(email, ignoreCase = true)) {
                obj.put("password", newPassword)
                found = true
            }
        }
        if (!found) return "No account found for this email."
        prefs.edit().putString("users", users.toString()).apply()
        return null
    }

    fun tasks(): List<Task> {
        seedTasksIfNeeded()
        val arr = JSONArray(prefs.getString("tasks", "[]"))
        return buildList {
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                add(
                    Task(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        done = obj.getBoolean("done")
                    )
                )
            }
        }
    }

    fun addTask(title: String) {
        if (title.isBlank()) return
        val list = tasks().toMutableList()
        list.add(Task(title = title.trim()))
        saveTasks(list)
    }

    fun toggleTask(id: String) {
        saveTasks(tasks().map { if (it.id == id) it.copy(done = !it.done) else it })
    }

    fun deleteTask(id: String) {
        saveTasks(tasks().filterNot { it.id == id })
    }

    private fun seedTasksIfNeeded() {
        if (prefs.contains("tasks")) return
        val defaults = listOf(
            Task(title = "Learning Programming by 12PM", done = true),
            Task(title = "Learn how to cook by 1PM"),
            Task(title = "Learn how to play at 3PM"),
            Task(title = "Have lunch at 4PM"),
            Task(title = "Going to travel 6PM")
        )
        saveTasks(defaults)
    }

    private fun saveTasks(list: List<Task>) {
        val arr = JSONArray()
        list.forEach { task ->
            arr.put(
                JSONObject()
                    .put("id", task.id)
                    .put("title", task.title)
                    .put("done", task.done)
            )
        }
        prefs.edit().putString("tasks", arr.toString()).apply()
    }

    private fun usersJson(): JSONArray {
        return JSONArray(prefs.getString("users", "[]"))
    }
}
