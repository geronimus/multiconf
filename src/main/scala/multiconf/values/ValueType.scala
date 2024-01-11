package net.geronimus.multiconf.values

sealed trait ValueType


object NumberType extends ValueType


object SimpleDateType extends ValueType


object TextLineType extends ValueType


object TimestampType extends ValueType

