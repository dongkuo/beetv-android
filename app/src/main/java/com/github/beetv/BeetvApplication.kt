package com.github.beetv

import android.app.Application
import com.github.beetv.data.dao.BeetvDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BeetvApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { BeetvDatabase.getDatabase(this, applicationScope) }
}