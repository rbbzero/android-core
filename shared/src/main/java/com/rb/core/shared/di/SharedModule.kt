package com.rb.core.shared.di

import com.rb.core.shared.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {

    @Provides
    @Singleton
    fun provideEnvironment(): Environment {
        return if (BuildConfig.DEBUG) {
            Environment.DEV
        } else {
            Environment.PROD
        }
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            encodeDefaults = true  // 在序列化时包含默认值。

            ignoreUnknownKeys = true  // 在反序列化时，如果 JSON 数据包含未知的键（与类定义不匹配），这些键将被忽略而不会抛出异常。

            coerceInputValues = true  // 尝试将输入值强制转换为期望类型，例如将字符串 "123" 转换为整数 123。
        }
    }
}

enum class Environment(val baseUrl: String) {
    DEV("https://api.wmdb.tv/api/v1/"),
    PROD("https://api.wmdb.tv/api/v1/")
}