package baas_lib.parse

/**
 * Created by KD on 2015-06-19.
 */
class ParseBackendObject {

    fun get(thisRef: Any?, prop: PropertyMetadata): String {
        return "ParseBackendObject on Android 5: $thisRef, thank you for delegating '${prop.name}' to me!"
    }

    fun set(thisRef: Any?, prop: PropertyMetadata, value: String) {
        println("ParseBackendObject on Android 5: $value has been assigned to '${prop.name} in $thisRef.'")
    }
}