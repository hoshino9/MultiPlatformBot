package org.hoshino9

import org.hoshino9.api.Api

const val apiRoot = "./build/resources"

fun propertyInit() {
    Api.root = apiRoot
}