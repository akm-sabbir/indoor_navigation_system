#include "Log.hpp"

#include <stdarg.h>
#include <android/log.h>

namespace sbrc {
    void Log::info(const char* pMessage, ...) {
        va_list lVarArgs;
        va_start(lVarArgs, pMessage);
        __android_log_vprint(ANDROID_LOG_INFO, "Indoor Navigation", pMessage,
            lVarArgs);
        __android_log_print(ANDROID_LOG_INFO, "Indoor Navigation", "\n");
        va_end(lVarArgs);
    }

    void Log::error(const char* pMessage, ...) {
        va_list lVarArgs;
        va_start(lVarArgs, pMessage);
        __android_log_vprint(ANDROID_LOG_ERROR, "Indoor Navigation", pMessage,
            lVarArgs);
        __android_log_print(ANDROID_LOG_ERROR, "Indoor Navigation", "\n");
        va_end(lVarArgs);
    }

    void Log::warn(const char* pMessage, ...) {
        va_list lVarArgs;
        va_start(lVarArgs, pMessage);
        __android_log_vprint(ANDROID_LOG_WARN, "Indoor Navigation", pMessage,
            lVarArgs);
        __android_log_print(ANDROID_LOG_WARN, "Indoor Navigation", "\n");
        va_end(lVarArgs);
    }

    void Log::debug(const char* pMessage, ...) {
        va_list lVarArgs;
        va_start(lVarArgs, pMessage);
        __android_log_vprint(ANDROID_LOG_DEBUG, "Indoor Navigation", pMessage,
            lVarArgs);
        __android_log_print(ANDROID_LOG_DEBUG, "Indoor Navigation", "\n");
        va_end(lVarArgs);
    }
}
