package net.geronimus.multiconf.settings

import net.geronimus.multiconf.values.{
  Name,
  TextLineType,
  ValueType
}

case class Attribute(
  attrName : Name,
  attrType : ValueType = TextLineType,
)

