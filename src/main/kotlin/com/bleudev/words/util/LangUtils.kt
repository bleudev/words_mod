package com.bleudev.words.util

fun <T> List<T>.forEachEntry(action: (Int, T) -> Unit) {
    Array(this.size){ it }.forEach { action(it, this[it]) }
}
fun <T> List<T>.forEachIndex(action: (Int) -> Unit) {
    this.forEachEntry { i, _ -> action(i) }
}

fun <T> Collection<T>.forEachEntry(action: (Int, T) -> Unit) {
    this.toList().forEachEntry(action)
}
fun <T> Collection<T>.forEachIndex(action: (Int) -> Unit) {
    this.toList().forEachIndex(action)
}

fun <K, V> Collection<K>.toPairMapWith(other: List<V>): HashMap<K, V> {
    assert(this.size == other.size)
    return HashMap<K, V>().apply {
        this@toPairMapWith.forEachEntry { i, v ->
            this@apply[v] = other[i]
        }
    }
}