package net.geronimus.multiconf.values

class TextLine private ( text : String ) extends TextContainer ( text ):

  def canEqual( a : Any ) = a.isInstanceOf[ TextLine ]

  override def equals( that : Any ) : Boolean =
    that match
      case that : TextLine => {
        that.canEqual( this ) &&
          this.text == that.text
      }
      case _ => false


object TextLine:

  def apply( text : String ) =
    TextSingleLine.validate( text )
    new TextLine( text )

