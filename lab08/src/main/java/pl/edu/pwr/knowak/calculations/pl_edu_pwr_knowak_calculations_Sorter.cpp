#include <jni.h>
#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include <algorithm>
#include "pl_edu_pwr_knowak_calculations_Sorter.h"

static int compare(const void* a, const void* b) {
    double da = *(const double*)a;
    double db = *(const double*)b;
    if (da < db) return -1;
    if (da > db) return 1;
    return 0;
}

static int compareDesc(const void* a, const void* b) {
    double da = *(const double*)a;
    double db = *(const double*)b;
    if (da > db) return -1;
    if (da < db) return 1;
    return 0;
}

JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_knowak_calculations_Sorter_sort01
  (JNIEnv *env, jobject obj, jobjectArray a, jobject order) {
    if (a == NULL || order == NULL) {
        return NULL;
    }

    jclass doubleClass = env->FindClass("java/lang/Double");
    if (doubleClass == NULL) {
        return NULL;
    }

    jmethodID doubleValueMethod = env->GetMethodID(doubleClass, "doubleValue", "()D");
    if (doubleValueMethod == NULL) {
        return NULL;
    }

    jmethodID doubleConstructor = env->GetMethodID(doubleClass, "<init>", "(D)V");
    if (doubleConstructor == NULL) {
        return NULL;
    }

    jsize len = env->GetArrayLength(a);
    if (len <= 0) {
        return a;
    }

    double* nativeArray = (double*)malloc(len * sizeof(double));
    if (nativeArray == NULL) {
        return NULL;
    }

    for (jsize i = 0; i < len; i++) {
        jobject doubleObj = env->GetObjectArrayElement(a, i);
        if (doubleObj == NULL) {
            free(nativeArray);
            return NULL;
        }

        nativeArray[i] = env->CallDoubleMethod(doubleObj, doubleValueMethod);
        env->DeleteLocalRef(doubleObj);
    }

    jclass booleanClass = env->FindClass("java/lang/Boolean");
    if (booleanClass == NULL) {
        free(nativeArray);
        return NULL;
    }

    jmethodID booleanValueMethod = env->GetMethodID(booleanClass, "booleanValue", "()Z");
    if (booleanValueMethod == NULL) {
        free(nativeArray);
        return NULL;
    }

    jboolean orderBool = env->CallBooleanMethod(order, booleanValueMethod);

    qsort(nativeArray, len, sizeof(double), orderBool ? compare : compareDesc);

    jobjectArray result = env->NewObjectArray(len, doubleClass, NULL);
    if (result == NULL) {
        free(nativeArray);
        return NULL;
    }

    for (jsize i = 0; i < len; i++) {
        jobject doubleObj = env->NewObject(doubleClass, doubleConstructor, nativeArray[i]);
        if (doubleObj == NULL) {
            free(nativeArray);
            return NULL;
        }

        env->SetObjectArrayElement(result, i, doubleObj);
        env->DeleteLocalRef(doubleObj);
    }

    free(nativeArray);
    return result;
}

JNIEXPORT jobjectArray JNICALL Java_pl_edu_pwr_knowak_calculations_Sorter_sort02
  (JNIEnv *env, jobject obj, jobjectArray input) {

    if (input == NULL) {
        return NULL;
    }

    jclass sorterClass = env->GetObjectClass(obj);
    if (sorterClass == NULL) {
        return NULL;
    }

    jfieldID orderFieldID = env->GetFieldID(sorterClass, "order", "Ljava/lang/Boolean;");
    if (orderFieldID == NULL) {
        return NULL;
    }

    jobject orderObj = env->GetObjectField(obj, orderFieldID);
    if (orderObj == NULL) {
        return NULL;
    }

    jclass booleanClass = env->FindClass("java/lang/Boolean");
    if (booleanClass == NULL) {
        return NULL;
    }

    jmethodID booleanValueMethod = env->GetMethodID(booleanClass, "booleanValue", "()Z");
    if (booleanValueMethod == NULL) {
        return NULL;
    }

    jboolean ascendingOrder = env->CallBooleanMethod(orderObj, booleanValueMethod);

    jsize len = env->GetArrayLength(input);
    if (len <= 0) {
        return input;
    }

    jclass doubleClass = env->FindClass("java/lang/Double");
    if (doubleClass == NULL) {
        return NULL;
    }

    jmethodID doubleValueMethod = env->GetMethodID(doubleClass, "doubleValue", "()D");
    if (doubleValueMethod == NULL) {
        return NULL;
    }

    jmethodID doubleConstructor = env->GetMethodID(doubleClass, "<init>", "(D)V");
    if (doubleConstructor == NULL) {
        return NULL;
    }

    double* nativeArray = (double*)malloc(len * sizeof(double));
    if (nativeArray == NULL) {
        return NULL;
    }

    for (jsize i = 0; i < len; i++) {
        jobject doubleObj = env->GetObjectArrayElement(input, i);
        if (doubleObj == NULL) {
            free(nativeArray);
            return NULL;
        }

        nativeArray[i] = env->CallDoubleMethod(doubleObj, doubleValueMethod);
        env->DeleteLocalRef(doubleObj);
    }

    qsort(nativeArray, len, sizeof(double), ascendingOrder ? compare : compareDesc);

    jobjectArray result = env->NewObjectArray(len, doubleClass, NULL);
    if (result == NULL) {
        free(nativeArray);
        return NULL;
    }

    for (jsize i = 0; i < len; i++) {
        jobject doubleObj = env->NewObject(doubleClass, doubleConstructor, nativeArray[i]);
        if (doubleObj == NULL) {
            free(nativeArray);
            return NULL;
        }

        env->SetObjectArrayElement(result, i, doubleObj);
        env->DeleteLocalRef(doubleObj);
    }

    free(nativeArray);

    return result;
}

JNIEXPORT void JNICALL Java_pl_edu_pwr_knowak_calculations_Sorter_sort03
  (JNIEnv *env, jobject obj) {

    jclass cls = env->GetObjectClass(obj);

    jmethodID showGui = env->GetMethodID(cls, "showGuiDialog", "()V");
    env->CallVoidMethod(obj, showGui);

    jmethodID sortMethod = env->GetMethodID(cls, "multi04", "()V");
    env->CallVoidMethod(obj, sortMethod);
}

JNIEXPORT void JNICALL Java_pl_edu_pwr_knowak_calculations_Sorter_sort04
  (JNIEnv *env, jobject obj) {

    jclass sorterClass = env->GetObjectClass(obj);
    if (sorterClass == NULL) {
        return;
    }

    jfieldID aFieldID = env->GetFieldID(sorterClass, "a", "[Ljava/lang/Double;");
    jfieldID bFieldID = env->GetFieldID(sorterClass, "b", "[Ljava/lang/Double;");
    jfieldID orderFieldID = env->GetFieldID(sorterClass, "order", "Ljava/lang/Boolean;");

    if (aFieldID == NULL || bFieldID == NULL || orderFieldID == NULL) {
        return;
    }

    jobjectArray aArray = (jobjectArray)env->GetObjectField(obj, aFieldID);
    jobject orderObj = env->GetObjectField(obj, orderFieldID);

    if (aArray == NULL) {
        return;
    }

    jsize len = env->GetArrayLength(aArray);
    if (len <= 0) {
        return;
    }

    jclass doubleClass = env->FindClass("java/lang/Double");
    if (doubleClass == NULL) {
        return;
    }

    jmethodID doubleValueMethod = env->GetMethodID(doubleClass, "doubleValue", "()D");
    jmethodID doubleConstructor = env->GetMethodID(doubleClass, "<init>", "(D)V");

    if (doubleValueMethod == NULL || doubleConstructor == NULL) {
        return;
    }

    jboolean ascendingOrder = false;

    if (orderObj != NULL) {
        jclass booleanClass = env->FindClass("java/lang/Boolean");
        if (booleanClass != NULL) {
            jmethodID booleanValueMethod = env->GetMethodID(booleanClass, "booleanValue", "()Z");
            if (booleanValueMethod != NULL) {
                ascendingOrder = env->CallBooleanMethod(orderObj, booleanValueMethod);
            }
        }
    }

    double* nativeArray = (double*)malloc(len * sizeof(double));
    if (nativeArray == NULL) {
        return;
    }

    for (jsize i = 0; i < len; i++) {
        jobject doubleObj = env->GetObjectArrayElement(aArray, i);
        if (doubleObj == NULL) {
            free(nativeArray);
            return;
        }

        nativeArray[i] = env->CallDoubleMethod(doubleObj, doubleValueMethod);
        env->DeleteLocalRef(doubleObj);
    }

    qsort(nativeArray, len, sizeof(double), ascendingOrder ? compare : compareDesc);

    jobjectArray bArray = env->NewObjectArray(len, doubleClass, NULL);
    if (bArray == NULL) {
        free(nativeArray);
        return;
    }

    for (jsize i = 0; i < len; i++) {
        jobject doubleObj = env->NewObject(doubleClass, doubleConstructor, nativeArray[i]);
        if (doubleObj == NULL) {
            free(nativeArray);
            return;
        }

        env->SetObjectArrayElement(bArray, i, doubleObj);
        env->DeleteLocalRef(doubleObj);
    }

    env->SetObjectField(obj, bFieldID, bArray);

    free(nativeArray);
}