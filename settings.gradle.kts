rootProject.name = "robot"

fun process(prefix: String, list: List<String>): Array<String> {
    return list.map { ":$prefix:$it" }
        .toTypedArray()
}

val commons = listOf("base", "handler", "multi")
    .map { ":common:$it" }
    .toTypedArray()

include(":common")
include(* commons)

val platforms = process(
    "platform",
    listOf("pansyqq")
)

include(":platform")
include(* platforms)