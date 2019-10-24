//
// Created by Alexey on 3/21/19.
//

#ifndef GITHUBTOPUSERS_ROOT_H
#define GITHUBTOPUSERS_ROOT_H

extern "C" {

#include <jni.h>

int Java_dev_shtanko_topgithub_root_RootNative_checkForRoot(JNIEnv *env, jobject thizz);

int Java_dev_shtanko_topgithub_root_RootNative_checkSuPaths(JNIEnv *env, jobject thizz);

};

#endif //GITHUBTOPUSERS_ROOT_H
