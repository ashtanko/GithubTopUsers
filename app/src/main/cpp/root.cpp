//
// Created by Alexey on 3/21/19.
//

#include "root.h"

#include <jni.h>
#include <android/log.h>

// String / file headers
#include <cstdio>
#include <iostream>
// LOGCAT
#define TAG "GITHUB_ROOT"

#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,    TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,     TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,     TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,    TAG, __VA_ARGS__)

int exists(const char *fname) {
    FILE *file;
    if ((file = fopen(fname, "r"))) {
        LOGD("LOOKING FOR BINARY: %s PRESENT!!!", fname);
        fclose(file);
        return 1;
    }
    LOGD("LOOKING FOR BINARY: %s Absent :(", fname);
    return 0;
}


int Java_me_shtanko_topgithub_root_RootNative_checkForRoot(JNIEnv *env, jobject thizz) {

    int binariesFound = 0;

    char *knownRootAppsPackages[7] = {
            "com.noshufou.android.su",
            "com.noshufou.android.su.elite",
            "eu.chainfire.supersu",
            "com.koushikdutta.superuser",
            "com.thirdparty.superuser",
            "com.yellowes.su",
            "com.topjohnwu.magisk"
    };

    for (int i = 0; i < 7; i++) {
        binariesFound += exists(knownRootAppsPackages[i]);
        //LOGD("LOOKING FOR BINARY: %s", knownRootAppsPackages[i]);
        //LOGD("");//knownRootAppsPackages[i]
    }


    return binariesFound > 0;
}

int Java_me_shtanko_topgithub_root_RootNative_checkSuPaths(JNIEnv *env, jobject thizz){

}