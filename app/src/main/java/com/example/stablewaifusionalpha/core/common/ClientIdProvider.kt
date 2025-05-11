package com.example.stablewaifusionalpha.core.common

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientIdProvider @Inject constructor() {
    val clientId: String = UUID.randomUUID().toString()
}
