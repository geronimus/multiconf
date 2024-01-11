package net.geronimus.multiconf.values

class Name private ( text : String ) extends TextContainer( text ):

  def canEqual( a : Any ) = a.isInstanceOf[ Name ]

  override def equals( that : Any ) : Boolean =
    that match
      case that : Name => {
        that.canEqual( this ) &&
          this.text == that.text
      }
      case _ => false


object Name:

  def apply( text : String ) =
    val trimmedText = Text.closeShave( text )

    rules.foreach( rule => rule.validate( trimmedText ) )

    new Name( trimmedText )

  def isValid( text : String ) =
    
    def findFirstFalse(
      testSeq : Seq[ () => Boolean ],
      result : Boolean = true
    ) : Boolean =
      if result == false || testSeq.isEmpty then result
      else findFirstFalse( testSeq.tail, testSeq.head() )

    findFirstFalse(
      rules.map( rule =>
        () => rule.check( text ) 
      )
    )

  private val rules = Vector(
    TextNonNull,
    TextNonEmpty,
    TextLessThan256,
    TextSingleLine,
    TextNoTabs
  )

