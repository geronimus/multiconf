package net.geronimus.multiconf.values

sealed trait TextRule:
  def check( text : String ) : Boolean
  def validate( text : String ) : Unit


object TextNonNull extends TextRule:

  def check( text : String ) : Boolean = text != null
  
  def validate( text : String ) =
    if !check( text ) then
      throw IllegalArgumentException( "Text must not be null." )


object TextNonEmpty extends TextRule:

  def check( text : String ) : Boolean =
    TextNonNull.check( text ) && text.size > 0

  def validate( text : String ) =
    if !check( text ) then
      throw IllegalArgumentException( "Text must not be empty." )


object TextLessThan256 extends TextRule:

  def check( text : String ) : Boolean =
    TextNonNull.check( text ) && text.size < 256

  def validate( text : String ) =
    if !check( text ) then
      throw IllegalArgumentException(
        "Text must have fewer than 256 characters."
      )


object TextSingleLine extends TextRule:

  def check( text : String ) : Boolean =
    val lineEndPattern = s"(?s).*[${ Text.lineEndSet.mkString( "" ) }].*".r
    TextNonNull.check( text ) && !lineEndPattern.matches( text )

  def validate( text : String ) =
    if !check( text ) then
      throw IllegalArgumentException(
        "Text must not contain line end characters."
      )


object TextNoTabs extends TextRule:

  def check( text : String ) : Boolean =
    TextNonNull.check( text ) && !"""(?s).*\t.*""".r.matches( text )

  def validate ( text : String ) =
    if !check( text ) then
      throw IllegalArgumentException(
        "Text must not contain tab characters."
      )

