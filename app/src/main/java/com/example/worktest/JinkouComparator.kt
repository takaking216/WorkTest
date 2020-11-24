package com.example.worktest

import java.util.*

class JinkouComparator : Comparator<Tdfk> {
    override fun compare(t1: Tdfk, t2: Tdfk): Int {
        return if (t1.jinkoumitudo < t2.jinkoumitudo) 1 else -1
    }
}