package dev.burnoo.cokoin

import org.koin.core.context.GlobalContext

internal fun getGlobalKoin() = GlobalContext.get()