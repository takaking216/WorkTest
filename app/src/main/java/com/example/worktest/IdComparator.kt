package com.example.worktest

import java.util.*

class IdComparator : Comparator<Tdfk> {
    override fun compare(t1: Tdfk, t2: Tdfk): Int {
        return if (t1.id < t2.id) -1 else 1
    }
}