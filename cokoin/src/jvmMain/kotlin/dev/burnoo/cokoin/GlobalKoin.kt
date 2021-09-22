package dev.burnoo.cokoin

import org.koin.core.context.GlobalContext

internal actual fun getGlobalKoin() = GlobalContext.get()