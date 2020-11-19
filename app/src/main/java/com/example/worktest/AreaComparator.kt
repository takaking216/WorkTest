package com.example.worktest

import java.util.*

class AreaComparator : Comparator<Tdfk> {
    override fun compare(t1: Tdfk, t2: Tdfk): Int {
        return if (t1.area < t2.area) 1 else -1
    }
}